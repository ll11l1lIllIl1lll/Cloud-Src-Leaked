/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldSettings$GameType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.ReflectionUtil;
import cn.CX.Cloud.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoMLG
extends Module {
    public Setting delay = new Setting("Delay", this, 100.0, 1.0, 1000.0, false);

    public AutoMLG() {
        super("Auto MLG", 0, Category.World);
    }

    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }

    public static double getDistanceToFall() {
        double distance = 0.0;
        for (double i = AutoMLG.mc.thePlayer.posY; i > 0.0; i -= 1.0) {
            Block block = AutoMLG.getBlock(new BlockPos(AutoMLG.mc.thePlayer.posX, i, AutoMLG.mc.thePlayer.posZ));
            if (block.getMaterial() != Material.air && block.isBlockNormalCube() && block.isCollidable()) {
                distance = i;
                break;
            }
            if (i < 0.0) break;
        }
        double distancetofall = AutoMLG.mc.thePlayer.posY - distance - 1.0;
        return distancetofall;
    }

    private int getSlotWaterBucket() {
        for (int i = 0; i < 8; ++i) {
            if (AutoMLG.mc.thePlayer.inventory.mainInventory[i] == null || !AutoMLG.mc.thePlayer.inventory.mainInventory[i].getItem().getUnlocalizedName().contains("bucketWater")) continue;
            return i;
        }
        return -1;
    }

    private void swapToWaterBucket(int blockSlot) {
        AutoMLG.mc.thePlayer.inventory.currentItem = blockSlot;
        AutoMLG.mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(blockSlot));
    }

    @SubscribeEvent
    public void onPre(TickEvent.PlayerTickEvent e) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        if (AutoMLG.mc.thePlayer.fallDistance > 4.0f && this.getSlotWaterBucket() != -1 && this.isMLGNeeded()) {
            AutoMLG.mc.thePlayer.rotationPitch = 90.0f;
            this.swapToWaterBucket(this.getSlotWaterBucket());
        }
        if (AutoMLG.mc.thePlayer.fallDistance > 4.0f && this.isMLGNeeded() && !AutoMLG.mc.thePlayer.isOnLadder() && AutoMLG.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos pos = new BlockPos(AutoMLG.mc.thePlayer.posX, AutoMLG.mc.thePlayer.posY - AutoMLG.getDistanceToFall() - 1.0, AutoMLG.mc.thePlayer.posZ);
            this.placeWater(pos, EnumFacing.UP);
            if (AutoMLG.mc.thePlayer.getHeldItem().getItem() == Items.bucket) {
                Thread thr = new Thread(){

                    @Override
                    public void run() {
                        try {
                            Thread.sleep((long)AutoMLG.this.delay.getValDouble());
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ReflectionUtil.rightClickMouse();
                    }
                };
                thr.start();
            }
            AutoMLG.mc.thePlayer.fallDistance = 0.0f;
        }
    }

    private void placeWater(BlockPos pos, EnumFacing facing) {
        ItemStack heldItem = AutoMLG.mc.thePlayer.inventory.getCurrentItem();
        AutoMLG.mc.playerController.onPlayerRightClick(AutoMLG.mc.thePlayer, AutoMLG.mc.theWorld, AutoMLG.mc.thePlayer.inventory.getCurrentItem(), pos, facing, new Vec3((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5));
        if (heldItem != null) {
            AutoMLG.mc.playerController.sendUseItem((EntityPlayer)AutoMLG.mc.thePlayer, (World)AutoMLG.mc.theWorld, heldItem);
            AutoMLG.mc.entityRenderer.itemRenderer.resetEquippedProgress2();
        }
    }

    private boolean isMLGNeeded() {
        if (AutoMLG.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE || AutoMLG.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR || AutoMLG.mc.thePlayer.capabilities.isFlying || AutoMLG.mc.thePlayer.capabilities.allowFlying) {
            return false;
        }
        for (double y = AutoMLG.mc.getMinecraft().thePlayer.posY; y > 0.0; y -= 1.0) {
            Block block = AutoMLG.getBlock(new BlockPos(AutoMLG.mc.getMinecraft().thePlayer.posX, y, AutoMLG.mc.getMinecraft().thePlayer.posZ));
            if (block.getMaterial() == Material.water) {
                return false;
            }
            if (block.getMaterial() != Material.air) {
                return true;
            }
            if (y < 0.0) break;
        }
        return true;
    }
}

