/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBed
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.HUDUtils;
import cn.CX.Cloud.utils.TimerUtils;
import cn.CX.Cloud.utils.Utils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class FuckBed
extends Module {
    public static BlockPos blockBreaking;
    TimerUtils timer = new TimerUtils();
    List<BlockPos> beds = new ArrayList<BlockPos>();
    public Setting instant = new Setting("Instant", this, false);

    public FuckBed() {
        super("FuckBed", 0, Category.World);
        this.registerSetting(this.instant);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (blockBreaking != null) {
            GlStateManager.pushMatrix();
            GlStateManager.disableDepth();
            HUDUtils.drawBoundingBox((double)blockBreaking.getX() - FuckBed.mc.getRenderManager().viewerPosX + 0.5, (double)blockBreaking.getY() - FuckBed.mc.getRenderManager().viewerPosY, (double)blockBreaking.getZ() - FuckBed.mc.getRenderManager().viewerPosZ + 0.5, 0.5, 0.5625, 1.0f, 0.0f, 0.0f, 0.25f);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
    }

    private EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        if (!FuckBed.mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isFullCube() && !(FuckBed.mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.UP;
        } else if (!FuckBed.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isFullCube() && !(FuckBed.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.DOWN;
        } else if (!FuckBed.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isFullCube() && !(FuckBed.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.EAST;
        } else if (!FuckBed.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isFullCube() && !(FuckBed.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.WEST;
        } else if (!FuckBed.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube() && !(FuckBed.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.SOUTH;
        } else if (!FuckBed.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube() && !(FuckBed.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.NORTH;
        }
        MovingObjectPosition rayResult = FuckBed.mc.theWorld.rayTraceBlocks(new Vec3(FuckBed.mc.thePlayer.posX, FuckBed.mc.thePlayer.posY + (double)FuckBed.mc.thePlayer.getEyeHeight(), FuckBed.mc.thePlayer.posZ), new Vec3((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5));
        if (rayResult != null && rayResult.getBlockPos() == pos) {
            return rayResult.sideHit;
        }
        return direction;
    }

    public float[] getRotations(BlockPos block, EnumFacing face) {
        double x = (double)block.getX() + 0.5 - FuckBed.mc.thePlayer.posX;
        double z = (double)block.getZ() + 0.5 - FuckBed.mc.thePlayer.posZ;
        double d1 = FuckBed.mc.thePlayer.posY + (double)FuckBed.mc.thePlayer.getEyeHeight() - ((double)block.getY() + 0.5);
        double d3 = MathHelper.sqrt_double((double)(x * x + z * z));
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    private EnumFacing getClosestEnum(BlockPos pos) {
        EnumFacing closestEnum = EnumFacing.UP;
        float rotations = MathHelper.wrapAngleTo180_float((float)this.getRotations(pos, EnumFacing.UP)[0]);
        if (rotations >= 45.0f && rotations <= 135.0f) {
            closestEnum = EnumFacing.EAST;
        } else if (rotations >= 135.0f && rotations <= 180.0f || rotations <= -135.0f && rotations >= -180.0f) {
            closestEnum = EnumFacing.SOUTH;
        } else if (rotations <= -45.0f && rotations >= -135.0f) {
            closestEnum = EnumFacing.WEST;
        } else if (rotations >= -45.0f && rotations <= 0.0f || rotations <= 45.0f && rotations >= 0.0f) {
            closestEnum = EnumFacing.NORTH;
        }
        if (MathHelper.wrapAngleTo180_float((float)this.getRotations(pos, EnumFacing.UP)[1]) > 75.0f || MathHelper.wrapAngleTo180_float((float)this.getRotations(pos, EnumFacing.UP)[1]) < -75.0f) {
            closestEnum = EnumFacing.UP;
        }
        return closestEnum;
    }

    private boolean blockChecks(Block block) {
        return block == Blocks.bed;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        int reach;
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        for (int y = reach = 6; y >= -reach; --y) {
            for (int x = -reach; x <= reach; ++x) {
                for (int z = -reach; z <= reach; ++z) {
                    if (FuckBed.mc.thePlayer.isSneaking()) {
                        return;
                    }
                    BlockPos pos = new BlockPos(FuckBed.mc.thePlayer.posX + (double)x, FuckBed.mc.thePlayer.posY + (double)y, FuckBed.mc.thePlayer.posZ + (double)z);
                    if (!this.blockChecks(FuckBed.mc.theWorld.getBlockState(pos).getBlock()) || !(FuckBed.mc.thePlayer.getDistance(FuckBed.mc.thePlayer.posX + (double)x, FuckBed.mc.thePlayer.posY + (double)y, FuckBed.mc.thePlayer.posZ + (double)z) < (double)FuckBed.mc.playerController.getBlockReachDistance() - 0.2) || this.beds.contains(pos)) continue;
                    this.beds.add(pos);
                }
            }
        }
        BlockPos closest = null;
        if (!this.beds.isEmpty()) {
            for (int i = 0; i < this.beds.size(); ++i) {
                BlockPos bed = this.beds.get(i);
                if (FuckBed.mc.thePlayer.getDistance((double)bed.getX(), (double)bed.getY(), (double)bed.getZ()) > (double)FuckBed.mc.playerController.getBlockReachDistance() - 0.2 || FuckBed.mc.theWorld.getBlockState(bed).getBlock() != Blocks.bed) {
                    this.beds.remove(i);
                }
                if (closest != null && (!(FuckBed.mc.thePlayer.getDistance((double)bed.getX(), (double)bed.getY(), (double)bed.getZ()) < FuckBed.mc.thePlayer.getDistance((double)closest.getX(), (double)closest.getY(), (double)closest.getZ())) || FuckBed.mc.thePlayer.ticksExisted % 50 != 0)) continue;
                closest = bed;
            }
        }
        if (closest != null) {
            float[] rot = this.getRotations(closest, this.getClosestEnum(closest));
            blockBreaking = closest;
            return;
        }
        blockBreaking = null;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (blockBreaking != null) {
            if (this.instant.isEnabled()) {
                mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockBreaking, EnumFacing.DOWN));
                FuckBed.mc.thePlayer.swingItem();
                mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockBreaking, EnumFacing.DOWN));
            } else {
                Field field = ReflectionHelper.findField(PlayerControllerMP.class, (String[])new String[]{"curBlockDamageMP", "field_78770_f"});
                Field blockdelay = ReflectionHelper.findField(PlayerControllerMP.class, (String[])new String[]{"blockHitDelay", "field_78781_i"});
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (!blockdelay.isAccessible()) {
                        blockdelay.setAccessible(true);
                    }
                    if (field.getFloat(FuckBed.mc.playerController) > 1.0f) {
                        blockdelay.setInt(FuckBed.mc.playerController, 1);
                    }
                    FuckBed.mc.thePlayer.swingItem();
                    EnumFacing direction = this.getClosestEnum(blockBreaking);
                    if (direction != null) {
                        FuckBed.mc.playerController.onPlayerDamageBlock(blockBreaking, direction);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

