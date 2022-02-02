/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityEndermite
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityGiantZombie
 *  net.minecraft.entity.monster.EntityGuardian
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.passive.EntityChicken
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.entity.passive.EntityRabbit
 *  net.minecraft.entity.passive.EntitySheep
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.misc.AntiBot;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.HUDUtils;
import cn.CX.Cloud.utils.Utils;
import java.awt.Color;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class HitBox
extends Module {
    public static Setting a;
    public static Setting b;
    public static Setting Width;
    public static Setting Height;
    private static MovingObjectPosition mv;

    public HitBox() {
        super("HitBox", 0, Category.Combat);
        a = new Setting("Multiplier", this, 1.2, 1.0, 50.0, false);
        this.registerSetting(a);
        b = new Setting("Show HitBox", this, false);
        this.registerSetting(b);
        Width = new Setting("Width", this, 1.0, 0.6, 5.0, true);
        this.registerSetting(Width);
        Height = new Setting("Height", this, 2.2, 1.8, 5.0, true);
        this.registerSetting(Height);
    }

    public static double exp(Entity en) {
        return Cloud.instance.moduleManager.getModule((String)"HitBox").state && !AntiBot.bot(en) ? a.getValDouble() : 1.0;
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        return !entity.isDead;
    }

    public EntitySize getEntitySize(Entity entity) {
        EntitySize entitySize = new EntitySize(0.6f, 1.8f);
        if (entity instanceof EntitySpider) {
            entitySize = new EntitySize(1.4f, 0.9f);
        }
        if (entity instanceof EntityBat) {
            entitySize = new EntitySize(0.5f, 0.9f);
        }
        if (entity instanceof EntityChicken) {
            entitySize = new EntitySize(0.5f, 0.9f);
        }
        if (entity instanceof EntityCow) {
            entitySize = new EntitySize(0.9f, 1.4f);
        }
        if (entity instanceof EntitySheep) {
            entitySize = new EntitySize(0.9f, 1.4f);
        }
        if (entity instanceof EntityEnderman) {
            entitySize = new EntitySize(0.6f, 2.9f);
        }
        if (entity instanceof EntityGhast) {
            entitySize = new EntitySize(4.0f, 4.0f);
        }
        if (entity instanceof EntityEndermite) {
            entitySize = new EntitySize(0.4f, 0.3f);
        }
        if (entity instanceof EntityGiantZombie) {
            entitySize = new EntitySize(3.6000001f, 10.799999f);
        }
        if (entity instanceof EntityWolf) {
            entitySize = new EntitySize(0.6f, 0.85f);
        }
        if (entity instanceof EntityGuardian) {
            entitySize = new EntitySize(0.85f, 0.85f);
        }
        if (entity instanceof EntitySquid) {
            entitySize = new EntitySize(0.8f, 0.8f);
        }
        if (entity instanceof EntityDragon) {
            entitySize = new EntitySize(16.0f, 8.0f);
        }
        if (entity instanceof EntityRabbit) {
            entitySize = new EntitySize(0.4f, 0.5f);
        }
        return entitySize;
    }

    @SubscribeEvent
    public void onDisable() {
        for (Entity object : Utils.getEntityList()) {
            if (!(object instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)object;
            EntitySize entitySize = this.getEntitySize((Entity)entity);
            Utils.setEntityBoundingBoxSize((Entity)entity, entitySize.width, entitySize.height);
        }
        this.onDisable();
    }

    public static void rh(Entity e, Color c) {
        if (HUDUtils.timerField == null) {
            return;
        }
        if (e instanceof EntityLivingBase) {
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)Utils.getTimer().renderPartialTicks - HitBox.mc.getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)Utils.getTimer().renderPartialTicks - HitBox.mc.getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)Utils.getTimer().renderPartialTicks - HitBox.mc.getRenderManager().viewerPosZ;
            float ex = (float)((double)e.getCollisionBorderSize() * a.getValDouble());
            AxisAlignedBB bbox = e.getEntityBoundingBox().expand((double)ex, (double)ex, (double)ex);
            AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)2.0f);
            GL11.glColor3d((double)c.getRed(), (double)c.getGreen(), (double)c.getBlue());
            RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)axis);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent ev) {
        for (Entity object : Utils.getEntityList()) {
            EntityLivingBase entity;
            if (!(object instanceof EntityLivingBase) || !this.check(entity = (EntityLivingBase)object)) continue;
            Utils.setEntityBoundingBoxSize((Entity)entity, (float)Width.getValDouble(), (float)Height.getValDouble());
        }
    }

    class EntitySize {
        public float width;
        public float height;

        public EntitySize(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
}

