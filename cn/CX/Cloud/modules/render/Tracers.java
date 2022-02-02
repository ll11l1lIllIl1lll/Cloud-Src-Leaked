/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.HUDUtils;
import cn.CX.Cloud.utils.Wrapper;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Tracers
extends Module {
    private Setting width = new Setting("Width", this, 1.0, 0.1f, 10.0, true);
    private Setting players = new Setting("Players", this, true);
    private Setting mobs = new Setting("Mobs", this, true);
    private Setting animals = new Setting("Animal", this, true);
    private Setting invisible = new Setting("Invisible", this, true);
    private Setting passives = new Setting("Passives", this, true);

    public Tracers() {
        super("Tracers", 0, Category.Render);
        this.registerSetting(this.width);
        this.registerSetting(this.players);
        this.registerSetting(this.mobs);
        this.registerSetting(this.animals);
        this.registerSetting(this.invisible);
        this.registerSetting(this.passives);
    }

    private boolean isValid(EntityLivingBase entity) {
        return Minecraft.getMinecraft().thePlayer != entity && this.isValidType(entity) && entity.isEntityAlive() && (!entity.isInvisible() || this.invisible.isEnabled());
    }

    private void trace(Entity entity, float width, Color color, float partialTicks) {
        float r = 0.003921569f * (float)color.getRed();
        float g = 0.003921569f * (float)color.getGreen();
        float b = 0.003921569f * (float)color.getBlue();
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Wrapper.orientCamera(partialTicks);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        double x = HUDUtils.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - Tracers.mc.getRenderManager().viewerPosX;
        double y = HUDUtils.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - Tracers.mc.getRenderManager().viewerPosY;
        double z = HUDUtils.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - Tracers.mc.getRenderManager().viewerPosZ;
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)3);
        GL11.glColor3d((double)r, (double)g, (double)b);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glVertex3d((double)0.0, (double)Tracers.mc.thePlayer.getEyeHeight(), (double)0.0);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent e) {
        for (Entity entity : Tracers.mc.theWorld.getLoadedEntityList()) {
            if (!(entity instanceof EntityLivingBase) || !this.isValid((EntityLivingBase)entity)) continue;
            this.trace(entity, (float)this.width.getValDouble(), new Color(255, 255, 255), Wrapper.getTimer((Minecraft)Tracers.mc).renderPartialTicks);
        }
    }

    private boolean isValidType(EntityLivingBase entity) {
        return this.players.isEnabled() && entity instanceof EntityPlayer || this.mobs.isEnabled() && (entity instanceof EntityMob || entity instanceof EntitySlime) || this.animals.isEnabled() && (entity instanceof EntityVillager || entity instanceof EntityGolem) || this.animals.isEnabled() && entity instanceof EntityAnimal;
    }
}

