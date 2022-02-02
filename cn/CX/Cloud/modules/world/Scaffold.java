/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.ReflectionUtil;
import cn.CX.Cloud.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Scaffold
extends Module {
    ArrayList<String> sites = new ArrayList();
    public int godBridgeTimer;
    public Setting Mode;

    @Override
    public void disable() {
        try {
            ReflectionUtil.pressed.set(Minecraft.getMinecraft().gameSettings.keyBindSneak, false);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        super.disable();
    }

    @Override
    public void enable() {
        super.enable();
    }

    public Scaffold() {
        super("Scaffold", 0, Category.World);
        this.sites.add("GodBridge");
        this.sites.add("Eagle");
        this.Mode = new Setting("Mode", this, "Eagle", this.sites);
        Cloud.instance.settingsManager.rSetting(this.Mode);
    }

    void Eagle() {
        try {
            if (Scaffold.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                if (!Scaffold.mc.gameSettings.keyBindJump.isPressed()) {
                    BlockPos bp = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ);
                    if (Scaffold.mc.theWorld.getBlockState(bp).getBlock() == Blocks.air) {
                        ReflectionUtil.pressed.set(Minecraft.getMinecraft().gameSettings.keyBindSneak, true);
                    } else {
                        ReflectionUtil.pressed.set(Minecraft.getMinecraft().gameSettings.keyBindSneak, false);
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    void GodBridge() {
        if (this.godBridgeTimer > 0) {
            ReflectionHelper.setPrivateValue(Minecraft.class, (Object)mc, (Object)new Integer(0), (String[])new String[]{"rightClickDelayTimer", "field_71467_ac"});
            --this.godBridgeTimer;
        }
        if (Scaffold.mc.theWorld == null || Scaffold.mc.thePlayer == null) {
            return;
        }
        WorldClient world = Scaffold.mc.theWorld;
        EntityPlayerSP player = Scaffold.mc.thePlayer;
        MovingObjectPosition movingObjectPosition = player.rayTrace((double)Scaffold.mc.playerController.getBlockReachDistance(), 1.0f);
        boolean isKeyUseDown = false;
        int keyCode = Scaffold.mc.gameSettings.keyBindUseItem.getKeyCode();
        isKeyUseDown = keyCode >= 0 ? Keyboard.isKeyDown((int)keyCode) : Mouse.isButtonDown((int)(keyCode + 100));
        if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && movingObjectPosition.sideHit == EnumFacing.UP && isKeyUseDown) {
            ItemBlock itemblock;
            int i;
            ItemStack itemstack = player.inventory.getCurrentItem();
            int n = i = itemstack != null ? itemstack.stackSize : 0;
            if (itemstack != null && itemstack.getItem() instanceof ItemBlock && !(itemblock = (ItemBlock)itemstack.getItem()).canPlaceBlockOnSide((World)world, movingObjectPosition.getBlockPos(), movingObjectPosition.sideHit, (EntityPlayer)player, itemstack)) {
                BlockPos blockPos = movingObjectPosition.getBlockPos();
                IBlockState blockState = world.getBlockState(blockPos);
                AxisAlignedBB axisalignedbb = blockState.getBlock().getSelectedBoundingBox((World)world, blockPos);
                if (axisalignedbb == null || world.isAirBlock(blockPos)) {
                    return;
                }
                Vec3 targetVec3 = null;
                Vec3 eyeVec3 = player.getPositionEyes(1.0f);
                double x1 = axisalignedbb.minX;
                double x2 = axisalignedbb.maxX;
                double y1 = axisalignedbb.minY;
                double y2 = axisalignedbb.maxY;
                double z1 = axisalignedbb.minZ;
                double z2 = axisalignedbb.maxZ;
                class Data
                implements Comparable<Data> {
                    public BlockPos blockPos;
                    public EnumFacing enumFacing;
                    public double cost;

                    public Data(BlockPos blockPos, EnumFacing enumFacing, double cost) {
                        this.blockPos = blockPos;
                        this.enumFacing = enumFacing;
                        this.cost = cost;
                    }

                    @Override
                    public int compareTo(Data data) {
                        return this.cost - data.cost > 0.0 ? -1 : (this.cost - data.cost < 0.0 ? 1 : 0);
                    }
                }
                ArrayList<Data> list = new ArrayList<Data>();
                if (!(x1 <= eyeVec3.xCoord && eyeVec3.xCoord <= x2 && y1 <= eyeVec3.yCoord && eyeVec3.yCoord <= y2 && z1 <= eyeVec3.zCoord && eyeVec3.zCoord <= z2)) {
                    double xCost = Math.abs(eyeVec3.xCoord - 0.5 * (axisalignedbb.minX + axisalignedbb.maxX));
                    double yCost = Math.abs(eyeVec3.yCoord - 0.5 * (axisalignedbb.minY + axisalignedbb.maxY));
                    double zCost = Math.abs(eyeVec3.zCoord - 0.5 * (axisalignedbb.minZ + axisalignedbb.maxZ));
                    double sumCost = xCost + yCost + zCost;
                    if (eyeVec3.xCoord < x1) {
                        list.add(new Data(blockPos.west(), EnumFacing.WEST, xCost));
                    } else if (eyeVec3.xCoord > x2) {
                        list.add(new Data(blockPos.east(), EnumFacing.EAST, xCost));
                    }
                    if (eyeVec3.zCoord < z1) {
                        list.add(new Data(blockPos.north(), EnumFacing.NORTH, zCost));
                    } else if (eyeVec3.zCoord > z2) {
                        list.add(new Data(blockPos.south(), EnumFacing.SOUTH, zCost));
                    }
                    Collections.sort(list);
                    double border = 0.05;
                    double x = MathHelper.clamp_double((double)eyeVec3.xCoord, (double)(x1 + border), (double)(x2 - border));
                    double y = MathHelper.clamp_double((double)eyeVec3.yCoord, (double)(y1 + border), (double)(y2 - border));
                    double z = MathHelper.clamp_double((double)eyeVec3.zCoord, (double)(z1 + border), (double)(z2 - border));
                    for (Data data : list) {
                        if (!world.isAirBlock(data.blockPos)) continue;
                        if (data.enumFacing == EnumFacing.WEST || data.enumFacing == EnumFacing.EAST) {
                            x = MathHelper.clamp_double((double)eyeVec3.xCoord, (double)x1, (double)x2);
                        } else if (data.enumFacing == EnumFacing.UP || data.enumFacing == EnumFacing.DOWN) {
                            y = MathHelper.clamp_double((double)eyeVec3.yCoord, (double)y1, (double)y2);
                        } else {
                            z = MathHelper.clamp_double((double)eyeVec3.zCoord, (double)z1, (double)z2);
                        }
                        targetVec3 = new Vec3(x, y, z);
                        break;
                    }
                    if (targetVec3 != null) {
                        double d0 = targetVec3.xCoord - eyeVec3.xCoord;
                        double d1 = targetVec3.yCoord - eyeVec3.yCoord;
                        double d2 = targetVec3.zCoord - eyeVec3.zCoord;
                        double d3 = MathHelper.sqrt_double((double)(d0 * d0 + d2 * d2));
                        float f = (float)(MathHelper.atan2((double)d2, (double)d0) * 180.0 / Math.PI) - 90.0f;
                        float f1 = (float)(-(MathHelper.atan2((double)d1, (double)d3) * 180.0 / Math.PI));
                        float f2 = player.rotationYaw;
                        float f3 = player.rotationPitch;
                        player.rotationYaw = f;
                        player.rotationPitch = f1;
                        MovingObjectPosition movingObjectPosition1 = player.rayTrace((double)Scaffold.mc.playerController.getBlockReachDistance(), 1.0f);
                        if (movingObjectPosition1.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && movingObjectPosition1.getBlockPos().getX() == blockPos.getX() && movingObjectPosition1.getBlockPos().getY() == blockPos.getY() && movingObjectPosition1.getBlockPos().getZ() == blockPos.getZ()) {
                            if (Scaffold.mc.playerController.onPlayerRightClick(player, Scaffold.mc.theWorld, itemstack, blockPos, movingObjectPosition1.sideHit, movingObjectPosition1.hitVec)) {
                                player.swingItem();
                            }
                            if (itemstack != null) {
                                if (itemstack.stackSize == 0) {
                                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                                } else if (itemstack.stackSize != i || Scaffold.mc.playerController.isInCreativeMode()) {
                                    Scaffold.mc.entityRenderer.itemRenderer.resetEquippedProgress();
                                }
                            }
                        }
                        player.rotationYaw = f2;
                        player.rotationPitch = f3;
                        double targetPitch = 75.5;
                        double pitchDelta = 2.5;
                        if (targetPitch - pitchDelta < (double)player.rotationPitch && (double)player.rotationPitch < targetPitch + pitchDelta) {
                            double delta;
                            double mod = (double)player.rotationYaw % 45.0;
                            if (mod < 0.0) {
                                mod += 45.0;
                            }
                            if (mod < (delta = 5.0)) {
                                player.rotationYaw = (float)((double)player.rotationYaw - mod);
                                player.rotationPitch = (float)targetPitch;
                            } else if (45.0 - mod < delta) {
                                player.rotationYaw = (float)((double)player.rotationYaw + (45.0 - mod));
                                player.rotationPitch = (float)targetPitch;
                            }
                        }
                        ReflectionHelper.setPrivateValue(Minecraft.class, (Object)mc, (Object)new Integer(1), (String[])new String[]{"rightClickDelayTimer", "field_71467_ac"});
                        this.godBridgeTimer = 10;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        if (this.Mode.getValString() == "Eagle") {
            this.godBridgeTimer = 0;
            this.Eagle();
        } else {
            this.GodBridge();
        }
    }
}

