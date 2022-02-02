/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 */
package cn.CX.Cloud.utils;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Target {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean fov(Entity entity, float fov) {
        fov = (float)((double)fov * 0.5);
        double v = ((double)(Target.mc.thePlayer.rotationYaw - Target.fovToEntity(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
        return v > 0.0 && v < (double)fov || (double)(-fov) < v && v < 0.0;
    }

    public static float fovToEntity(Entity ent) {
        double x = ent.posX - Target.mc.thePlayer.posX;
        double z = ent.posZ - Target.mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.2957795;
        return (float)(yaw * -1.0);
    }

    public static boolean isSameTeam(Entity entity) {
        if (entity == Target.mc.thePlayer) {
            return true;
        }
        try {
            EntityPlayer bruhentity = (EntityPlayer)entity;
            if (Target.mc.thePlayer.isOnSameTeam((EntityLivingBase)entity) || Target.mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static Entity getEnemy(double Range, int fov) {
        EntityPlayer en;
        Iterator var2 = Target.mc.theWorld.playerEntities.iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while ((en = (EntityPlayer)var2.next()) == Target.mc.thePlayer || en.deathTime != 0 || (double)Target.mc.thePlayer.getDistanceToEntity((Entity)en) > Range);
        return en;
    }
}

