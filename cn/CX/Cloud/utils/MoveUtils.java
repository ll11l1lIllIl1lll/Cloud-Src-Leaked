/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 */
package cn.CX.Cloud.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class MoveUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public double getX() {
        return MoveUtils.mc.thePlayer.motionX;
    }

    public double getY() {
        return MoveUtils.mc.thePlayer.motionY;
    }

    public static int getSpeedEffect() {
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (double)(MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static boolean isBlockEdge(EntityLivingBase entity) {
        return MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)entity, entity.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(0.001, 0.0, 0.001)).isEmpty() && entity.onGround;
    }

    public static void setSpeed(double speed) {
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        if (forward == 0.0 && strafe == 0.0) {
            Minecraft.getMinecraft().thePlayer.motionX = 0.0;
            Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                forward = forward > 0.0 ? 1.0 : -1.0;
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            Minecraft.getMinecraft().thePlayer.motionX = forward * speed * cos + strafe * speed * sin;
            Minecraft.getMinecraft().thePlayer.motionZ = forward * speed * sin - strafe * speed * cos;
        }
    }

    public static void setSpeed(MoveUtils moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward == 0.0 && strafe == 0.0) {
            moveEvent.setZ(0.0);
            moveEvent.setX(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            moveEvent.setX(forward * moveSpeed * cos + strafe * moveSpeed * sin);
            moveEvent.setZ(forward * moveSpeed * sin - strafe * moveSpeed * cos);
        }
    }

    public static boolean isBlockAboveHead() {
        AxisAlignedBB llllIllIIllIlll = new AxisAlignedBB(MoveUtils.mc.thePlayer.posX - 0.3, MoveUtils.mc.thePlayer.posY + (double)MoveUtils.mc.thePlayer.getEyeHeight(), MoveUtils.mc.thePlayer.posZ + 0.3, MoveUtils.mc.thePlayer.posX + 0.3, MoveUtils.mc.thePlayer.posY + 2.5, MoveUtils.mc.thePlayer.posZ - 0.3);
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIllIlll).isEmpty();
    }

    public static double defaultSpeed() {
        double llllIlllllIIIlI = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int llllIlllllIIIll = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            llllIlllllIIIlI *= 1.0 + 0.2 * (double)(llllIlllllIIIll + 1);
        }
        return llllIlllllIIIlI;
    }

    public static Block getBlockAtPosC(double llllIllIlllIlII, double llllIllIlllIlll, double llllIllIlllIIlI) {
        EntityPlayerSP llllIllIlllIlIl = Minecraft.getMinecraft().thePlayer;
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(llllIllIlllIlIl.posX + llllIllIlllIlII, llllIllIlllIlIl.posY + llllIllIlllIlll, llllIllIlllIlIl.posZ + llllIllIlllIIlI)).getBlock();
    }

    public static boolean isRealCollidedH(double llllIllIIlIllIl) {
        AxisAlignedBB llllIllIIlIllII = new AxisAlignedBB(MoveUtils.mc.thePlayer.posX - 0.3, MoveUtils.mc.thePlayer.posY + 0.5, MoveUtils.mc.thePlayer.posZ + 0.3, MoveUtils.mc.thePlayer.posX + 0.3, MoveUtils.mc.thePlayer.posY + 1.9, MoveUtils.mc.thePlayer.posZ - 0.3);
        if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIlIllII.offset(0.3 + llllIllIIlIllIl, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIlIllII.offset(-0.3 - llllIllIIlIllIl, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIlIllII.offset(0.0, 0.0, 0.3 + llllIllIIlIllIl)).isEmpty()) {
            return true;
        }
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIlIllII.offset(0.0, 0.0, -0.3 - llllIllIIlIllIl)).isEmpty();
    }

    public static int getJumpEffect() {
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            return MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }

    public static boolean checkTeleport(double llllIlllIIlIlll, double llllIlllIlIIIll, double llllIlllIlIIIlI, double llllIlllIlIIIIl) {
        double llllIlllIlIIIII = MoveUtils.mc.thePlayer.posX - llllIlllIIlIlll;
        double llllIlllIIlllll = MoveUtils.mc.thePlayer.posY - llllIlllIlIIIll;
        double llllIlllIIllllI = MoveUtils.mc.thePlayer.posZ - llllIlllIlIIIlI;
        double llllIlllIIlllIl = Math.sqrt(MoveUtils.mc.thePlayer.getDistanceSq(llllIlllIIlIlll, llllIlllIlIIIll, llllIlllIlIIIlI));
        double llllIlllIIlllII = llllIlllIlIIIIl;
        double llllIlllIIllIll = Math.round(llllIlllIIlllIl / llllIlllIIlllII + 0.49999999999) - 1L;
        double llllIlllIIllIlI = MoveUtils.mc.thePlayer.posX;
        double llllIlllIIllIIl = MoveUtils.mc.thePlayer.posY;
        double llllIlllIIllIII = MoveUtils.mc.thePlayer.posZ;
        int llllIlllIlIIlIl = 1;
        while ((double)llllIlllIlIIlIl < llllIlllIIllIll) {
            AxisAlignedBB llllIlllIlIIllI;
            double llllIlllIlIlIIl = (llllIlllIIlIlll - MoveUtils.mc.thePlayer.posX) / llllIlllIIllIll;
            double llllIlllIlIIlll = (llllIlllIlIIIll - MoveUtils.mc.thePlayer.posY) / llllIlllIIllIll;
            double llllIlllIlIlIII = (llllIlllIlIIIlI - MoveUtils.mc.thePlayer.posZ) / llllIlllIIllIll;
            if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIlllIlIIllI = new AxisAlignedBB((llllIlllIIllIlI += llllIlllIlIlIIl) - 0.3, llllIlllIIllIIl += llllIlllIlIIlll, (llllIlllIIllIII += llllIlllIlIlIII) - 0.3, llllIlllIIllIlI + 0.3, llllIlllIIllIIl + 1.8, llllIlllIIllIII + 0.3)).isEmpty()) {
                return false;
            }
            ++llllIlllIlIIlIl;
        }
        return true;
    }

    public static void setMotion(double llllIllllIIIIll) {
        double llllIllllIIIIlI = MoveUtils.mc.thePlayer.movementInput.moveForward;
        double llllIllllIIIIIl = MoveUtils.mc.thePlayer.movementInput.moveStrafe;
        float llllIllllIIIIII = MoveUtils.mc.thePlayer.rotationYaw;
        if (llllIllllIIIIlI == 0.0 && llllIllllIIIIIl == 0.0) {
            MoveUtils.mc.thePlayer.motionX = 0.0;
            MoveUtils.mc.thePlayer.motionZ = 0.0;
        } else {
            if (llllIllllIIIIlI != 0.0) {
                if (llllIllllIIIIIl > 0.0) {
                    llllIllllIIIIII += (float)(llllIllllIIIIlI > 0.0 ? -45 : 45);
                } else if (llllIllllIIIIIl < 0.0) {
                    llllIllllIIIIII += (float)(llllIllllIIIIlI > 0.0 ? 45 : -45);
                }
                llllIllllIIIIIl = 0.0;
                if (llllIllllIIIIlI > 0.0) {
                    llllIllllIIIIlI = 1.0;
                } else if (llllIllllIIIIlI < 0.0) {
                    llllIllllIIIIlI = -1.0;
                }
            }
            MoveUtils.mc.thePlayer.motionX = llllIllllIIIIlI * llllIllllIIIIll * Math.cos(Math.toRadians(llllIllllIIIIII + 90.0f)) + llllIllllIIIIIl * llllIllllIIIIll * Math.sin(Math.toRadians(llllIllllIIIIII + 90.0f));
            MoveUtils.mc.thePlayer.motionZ = llllIllllIIIIlI * llllIllllIIIIll * Math.sin(Math.toRadians(llllIllllIIIIII + 90.0f)) - llllIllllIIIIIl * llllIllllIIIIll * Math.cos(Math.toRadians(llllIllllIIIIII + 90.0f));
        }
    }

    public static boolean isCollidedH(double llllIllIIllIIIl) {
        AxisAlignedBB llllIllIIllIIlI = new AxisAlignedBB(MoveUtils.mc.thePlayer.posX - 0.3, MoveUtils.mc.thePlayer.posY + 2.0, MoveUtils.mc.thePlayer.posZ + 0.3, MoveUtils.mc.thePlayer.posX + 0.3, MoveUtils.mc.thePlayer.posY + 3.0, MoveUtils.mc.thePlayer.posZ - 0.3);
        if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIllIIlI.offset(0.3 + llllIllIIllIIIl, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIllIIlI.offset(-0.3 - llllIllIIllIIIl, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIllIIlI.offset(0.0, 0.0, 0.3 + llllIllIIllIIIl)).isEmpty()) {
            return true;
        }
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, llllIllIIllIIlI.offset(0.0, 0.0, -0.3 - llllIllIIllIIIl)).isEmpty();
    }

    public static void strafe(double speed) {
        float a = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180);
        float l = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180) - 4.712389f;
        float r = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180) + 4.712389f;
        float rf = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180) + 0.5969026f;
        float lf = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180) + -0.5969026f;
        float lb = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180) - 2.3876104f;
        float rb = MoveUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180) - -2.3876104f;
        if (MoveUtils.mc.gameSettings.keyBindForward.isPressed()) {
            if (MoveUtils.mc.gameSettings.keyBindLeft.isPressed() && !MoveUtils.mc.gameSettings.keyBindRight.isPressed()) {
                MoveUtils.mc.thePlayer.motionX -= (double)MathHelper.sin((float)lf) * speed;
                MoveUtils.mc.thePlayer.motionZ += (double)MathHelper.cos((float)lf) * speed;
            } else if (MoveUtils.mc.gameSettings.keyBindRight.isPressed() && !MoveUtils.mc.gameSettings.keyBindLeft.isPressed()) {
                MoveUtils.mc.thePlayer.motionX -= (double)MathHelper.sin((float)rf) * speed;
                MoveUtils.mc.thePlayer.motionZ += (double)MathHelper.cos((float)rf) * speed;
            } else {
                MoveUtils.mc.thePlayer.motionX -= (double)MathHelper.sin((float)a) * speed;
                MoveUtils.mc.thePlayer.motionZ += (double)MathHelper.cos((float)a) * speed;
            }
        } else if (MoveUtils.mc.gameSettings.keyBindBack.isPressed()) {
            if (MoveUtils.mc.gameSettings.keyBindLeft.isPressed() && !MoveUtils.mc.gameSettings.keyBindRight.isPressed()) {
                MoveUtils.mc.thePlayer.motionX -= (double)MathHelper.sin((float)lb) * speed;
                MoveUtils.mc.thePlayer.motionZ += (double)MathHelper.cos((float)lb) * speed;
            } else if (MoveUtils.mc.gameSettings.keyBindRight.isPressed() && !MoveUtils.mc.gameSettings.keyBindLeft.isPressed()) {
                MoveUtils.mc.thePlayer.motionX -= (double)MathHelper.sin((float)rb) * speed;
                MoveUtils.mc.thePlayer.motionZ += (double)MathHelper.cos((float)rb) * speed;
            } else {
                MoveUtils.mc.thePlayer.motionX += (double)MathHelper.sin((float)a) * speed;
                MoveUtils.mc.thePlayer.motionZ -= (double)MathHelper.cos((float)a) * speed;
            }
        } else if (MoveUtils.mc.gameSettings.keyBindLeft.isPressed() && !MoveUtils.mc.gameSettings.keyBindRight.isPressed() && !MoveUtils.mc.gameSettings.keyBindForward.isPressed() && !MoveUtils.mc.gameSettings.keyBindBack.isPressed()) {
            MoveUtils.mc.thePlayer.motionX += (double)MathHelper.sin((float)l) * speed;
            MoveUtils.mc.thePlayer.motionZ -= (double)MathHelper.cos((float)l) * speed;
        } else if (MoveUtils.mc.gameSettings.keyBindRight.isPressed() && !MoveUtils.mc.gameSettings.keyBindLeft.isPressed() && !MoveUtils.mc.gameSettings.keyBindForward.isPressed() && !MoveUtils.mc.gameSettings.keyBindBack.isPressed()) {
            MoveUtils.mc.thePlayer.motionX += (double)MathHelper.sin((float)r) * speed;
            MoveUtils.mc.thePlayer.motionZ -= (double)MathHelper.cos((float)r) * speed;
        }
    }

    public static float[] getRotationsBlock(BlockPos llllIllIlIIIIIl, EnumFacing llllIllIlIIlIIl) {
        double llllIllIlIIlIII = (double)llllIllIlIIIIIl.getX() + 0.5 - MoveUtils.mc.thePlayer.posX + (double)llllIllIlIIlIIl.getFrontOffsetX() / 2.0;
        double llllIllIlIIIlll = (double)llllIllIlIIIIIl.getZ() + 0.5 - MoveUtils.mc.thePlayer.posZ + (double)llllIllIlIIlIIl.getFrontOffsetZ() / 2.0;
        double llllIllIlIIIllI = (double)llllIllIlIIIIIl.getY() + 0.5;
        double llllIllIlIIIlIl = MoveUtils.mc.thePlayer.posY + (double)MoveUtils.mc.thePlayer.getEyeHeight() - llllIllIlIIIllI;
        double llllIllIlIIIlII = MathHelper.sqrt_double((double)(llllIllIlIIlIII * llllIllIlIIlIII + llllIllIlIIIlll * llllIllIlIIIlll));
        float llllIllIlIIIIll = (float)(Math.atan2(llllIllIlIIIlll, llllIllIlIIlIII) * 180.0 / Math.PI) - 90.0f;
        float llllIllIlIIIIlI = (float)(Math.atan2(llllIllIlIIIlIl, llllIllIlIIIlII) * 180.0 / Math.PI);
        if (llllIllIlIIIIll < 0.0f) {
            llllIllIlIIIIll += 360.0f;
        }
        return new float[]{llllIllIlIIIIll, llllIllIlIIIIlI};
    }

    public static Block getBlockUnderPlayer(EntityPlayer llllIllIllllllI, double llllIllIlllllIl) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(llllIllIllllllI.posX, llllIllIllllllI.posY - llllIllIlllllIl, llllIllIllllllI.posZ)).getBlock();
    }

    public static float getDistanceToGround(Entity llllIllIlIlllIl) {
        if (MoveUtils.mc.thePlayer.isCollidedVertically && MoveUtils.mc.thePlayer.onGround) {
            return 0.0f;
        }
        for (float llllIllIlIllllI = (float)llllIllIlIlllIl.posY; llllIllIlIllllI > 0.0f; llllIllIlIllllI -= 1.0f) {
            int[] llllIllIllIIIIl = new int[]{53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
            int[] llllIllIllIIIII = new int[]{6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177};
            Block llllIllIlIlllll = MoveUtils.mc.theWorld.getBlockState(new BlockPos(llllIllIlIlllIl.posX, (double)(llllIllIlIllllI - 1.0f), llllIllIlIlllIl.posZ)).getBlock();
            if (llllIllIlIlllll instanceof BlockAir) continue;
            if (Block.getIdFromBlock((Block)llllIllIlIlllll) == 44 || Block.getIdFromBlock((Block)llllIllIlIlllll) == 126) {
                return (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI - 0.5) < 0.0f ? 0.0f : (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI - 0.5);
            }
            int[] llllIllIllIIIll = llllIllIllIIIIl;
            int llllIllIllIIIlI = llllIllIllIIIIl.length;
            for (int llllIllIllIIllI = 0; llllIllIllIIllI < llllIllIllIIIlI; ++llllIllIllIIllI) {
                int llllIllIllIIlll = llllIllIllIIIll[llllIllIllIIllI];
                if (Block.getIdFromBlock((Block)llllIllIlIlllll) != llllIllIllIIlll) continue;
                return (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI - 1.0) < 0.0f ? 0.0f : (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI - 1.0);
            }
            llllIllIllIIIll = llllIllIllIIIII;
            llllIllIllIIIlI = llllIllIllIIIII.length;
            for (int llllIllIllIIlII = 0; llllIllIllIIlII < llllIllIllIIIlI; ++llllIllIllIIlII) {
                int llllIllIllIIlIl = llllIllIllIIIll[llllIllIllIIlII];
                if (Block.getIdFromBlock((Block)llllIllIlIlllll) != llllIllIllIIlIl) continue;
                return (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI) < 0.0f ? 0.0f : (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI);
            }
            return (float)(llllIllIlIlllIl.posY - (double)llllIllIlIllllI + llllIllIlIlllll.getBlockBoundsMaxY() - 1.0);
        }
        return 0.0f;
    }

    public static boolean isOnGround(double height) {
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes((Entity)MoveUtils.mc.thePlayer, MoveUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public void setX(double x) {
        MoveUtils.mc.thePlayer.motionX = x;
    }

    public static void setY(double y) {
        MoveUtils.mc.thePlayer.motionY = y;
    }

    public void setZ(double z) {
        MoveUtils.mc.thePlayer.motionZ = z;
    }

    public double getZ() {
        return MoveUtils.mc.thePlayer.motionZ;
    }
}

