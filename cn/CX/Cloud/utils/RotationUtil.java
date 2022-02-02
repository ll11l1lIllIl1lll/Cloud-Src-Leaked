/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 */
package cn.CX.Cloud.utils;

import java.lang.reflect.Field;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
    static Minecraft mc = Minecraft.getMinecraft();
    static float cacheyaw;
    static float cachepitch;

    public static float a(double motionX, double motionZ) {
        double v1 = motionX - Minecraft.getMinecraft().thePlayer.posX;
        double v32 = motionZ - Minecraft.getMinecraft().thePlayer.posZ;
        double v5 = MathHelper.ceiling_float_int((float)((float)(v1 * v1 + v32 * v32)));
        return (float)(Math.atan2(v32, v1) * 180.0 / Math.PI) - 90.0f;
    }

    public static void yaw(float yaw) {
        RotationUtil.mc.thePlayer.rotationYaw = yaw;
    }

    public static float yaw() {
        return RotationUtil.mc.thePlayer.rotationYaw;
    }

    public static float pitch() {
        return RotationUtil.mc.thePlayer.rotationPitch;
    }

    public static void pitch(float pitch) {
        RotationUtil.mc.thePlayer.rotationPitch = pitch;
    }

    public static float[] getNeededFacing(Vec3 target, Vec3 from) {
        double diffX = target.xCoord - from.xCoord;
        double diffY = target.yCoord - from.yCoord;
        double diffZ = target.zCoord - from.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapAngleTo180_float((float)yaw), MathHelper.wrapAngleTo180_float((float)pitch)};
    }

    public static void setYaw(float yaw) {
        cacheyaw = RotationUtil.mc.thePlayer.rotationYaw;
        RotationUtil.mc.thePlayer.rotationYaw = yaw;
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var6;
        double var4 = target.posX - RotationUtil.mc.thePlayer.posX;
        double var8 = target.posZ - RotationUtil.mc.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase)target;
            var6 = var10.posY + (double)var10.getEyeHeight() - (RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double((double)(var4 * var4 + var8 * var8));
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / Math.PI) - 90.0f;
        float var13 = (float)(-Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14) * 180.0 / Math.PI);
        float pitch = RotationUtil.changeRotation(RotationUtil.mc.thePlayer.rotationPitch, var13, p_706253);
        float yaw = RotationUtil.changeRotation(RotationUtil.mc.thePlayer.rotationYaw, var12, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float((float)(p_706632 - p_706631));
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static float[] getRotation(Entity entity) {
        double xPos = entity.posX - RotationUtil.mc.thePlayer.posX;
        double zPos = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        double yPos = entity.posY - RotationUtil.mc.thePlayer.posY;
        double dist = MathHelper.sqrt_double((double)(xPos * xPos + zPos * zPos));
        float yaw = (float)(Math.atan2(zPos, xPos) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yPos, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static void setCYaw() {
        RotationUtil.mc.thePlayer.rotationYaw = cacheyaw;
    }

    public static void setCPitch() {
        RotationUtil.mc.thePlayer.rotationPitch = cachepitch;
    }

    public static float getYawChangeGiven(double posX, double posZ, double posX2) {
        return 0.0f;
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = RotationUtil.mc.thePlayer.posX;
        double pY = RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight();
        double pZ = RotationUtil.mc.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)(entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[]{yaw, 90.0 - pitch};
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        Minecraft.getMinecraft();
        double xDiff = x - RotationUtil.mc.thePlayer.posX;
        Minecraft.getMinecraft();
        double zDiff = z - RotationUtil.mc.thePlayer.posZ;
        Minecraft.getMinecraft();
        double yDiff = y - RotationUtil.mc.thePlayer.posY - 1.2;
        double dist = MathHelper.sqrt_double((double)(xDiff * xDiff + zDiff * zDiff));
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static float[] getNeededRotations(Vec3 vec) {
        Vec3 playerVector = new Vec3(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight(), RotationUtil.mc.thePlayer.posZ);
        double y = vec.yCoord - playerVector.yCoord;
        double x = vec.xCoord - playerVector.xCoord;
        double z = vec.zCoord - playerVector.zCoord;
        double dff = Math.sqrt(x * x + z * z);
        float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(y, dff)));
        return new float[]{MathHelper.wrapAngleTo180_float((float)yaw), MathHelper.wrapAngleTo180_float((float)pitch)};
    }

    public static float[] getRotationToEntityF(Entity entity) {
        double pX = Minecraft.getMinecraft().thePlayer.posX;
        double pY = Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight();
        double pZ = Minecraft.getMinecraft().thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)entity.getEyeHeight();
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        float yaw = 0.0f;
        float pitch = 0.0f;
        yaw = (float)(Math.toDegrees(Math.atan2(dZ, dX)) + 90.0);
        pitch = (float)Math.toDegrees(Math.atan2(dH, dY));
        return new float[]{yaw, 90.0f - pitch};
    }

    public static int wrapAngleToDirection(float yaw, int zones) {
        int angle = (int)((double)(yaw + (float)(360 / (2 * zones))) + 0.5) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle / (360 / zones);
    }

    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - RotationUtil.mc.thePlayer.posX;
        double diffZ = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + ((double)elb.getEyeHeight() - 0.4) - (RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight());
        } else {
            diffY = 0.0;
            Field fBB = null;
            try {
                fBB = Entity.class.getDeclaredField("boundingBox");
                fBB.setAccessible(true);
                AxisAlignedBB ebb = (AxisAlignedBB)fBB.get(entity);
                diffY = (ebb.minY + ebb.maxY) / 2.0 - (RotationUtil.mc.thePlayer.posY + (double)RotationUtil.mc.thePlayer.getEyeHeight());
                fBB.setAccessible(false);
            }
            catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-Math.atan2(diffY, dist) * 180.0 / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static void setPitch(float pitch) {
        cachepitch = RotationUtil.mc.thePlayer.rotationPitch;
        RotationUtil.mc.thePlayer.rotationPitch = pitch;
    }
}

