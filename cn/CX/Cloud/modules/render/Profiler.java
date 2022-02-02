/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.misc.AntiBot;
import cn.CX.Cloud.modules.misc.Target;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.BufferBuilder;
import cn.CX.Cloud.utils.ColorUtils;
import cn.CX.Cloud.utils.ETessellator;
import cn.CX.Cloud.utils.GLUtils;
import cn.CX.Cloud.utils.MathUtils;
import cn.CX.Cloud.utils.Utils;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Profiler
extends Module {
    public Setting armor = new Setting("Armor", this, true);

    public Profiler() {
        super("Profiler", 0, Category.Render);
        this.registerSetting(this.armor);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Entity object : Utils.getEntityList()) {
            if (!(object instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)object;
            RenderManager renderManager = mc.getRenderManager();
            double renderPosX = renderManager.viewerPosX;
            double renderPosY = renderManager.viewerPosY;
            double renderPosZ = renderManager.viewerPosZ;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.partialTicks - renderPosX;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.partialTicks - renderPosY;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.partialTicks - renderPosZ;
            this.renderNameTag(entity, entity.getName(), xPos, yPos, zPos);
        }
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        float var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GLUtils.glColor(color);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }

    public void renderArmor(EntityPlayer player, int x, int y) {
        InventoryPlayer items = player.inventory;
        ItemStack inHand = player.getHeldItem();
        ItemStack boots = items.armorItemInSlot(0);
        ItemStack leggings = items.armorItemInSlot(1);
        ItemStack body = items.armorItemInSlot(2);
        ItemStack helm = items.armorItemInSlot(3);
        ItemStack[] stuff = null;
        stuff = inHand != null ? new ItemStack[]{inHand, helm, body, leggings, boots} : new ItemStack[]{helm, body, leggings, boots};
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (int j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null || i.getItem() == null) continue;
            stacks.add(i);
        }
        int width = 16 * stacks.size() / 2;
        x -= width;
        GlStateManager.disableDepth();
        for (ItemStack stack : stacks) {
            this.renderItem(stack, x, y);
            x += 16;
        }
        GlStateManager.enableDepth();
    }

    public void renderItem(ItemStack stack, int x, int y) {
        FontRenderer fontRenderer = Profiler.mc.fontRendererObj;
        RenderItem renderItem = mc.getRenderItem();
        EnchantEntry[] enchants = new EnchantEntry[]{new EnchantEntry(Enchantment.protection, "Pro"), new EnchantEntry(Enchantment.thorns, "Th"), new EnchantEntry(Enchantment.sharpness, "Shar"), new EnchantEntry(Enchantment.fireAspect, "Fire"), new EnchantEntry(Enchantment.knockback, "Kb"), new EnchantEntry(Enchantment.unbreaking, "Unb"), new EnchantEntry(Enchantment.power, "Pow"), new EnchantEntry(Enchantment.infinity, "Inf"), new EnchantEntry(Enchantment.punch, "Punch")};
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        float scale1 = 0.3f;
        GlStateManager.translate((float)(x - 3), (float)(y + 10), (float)0.0f);
        GlStateManager.scale((float)0.3f, (float)0.3f, (float)0.3f);
        GlStateManager.popMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.zLevel = -100.0f;
        GlStateManager.disableDepth();
        renderItem.renderItemIntoGUI(stack, x, y);
        renderItem.renderItemOverlayIntoGUI(fontRenderer, stack, x, y, null);
        GlStateManager.enableDepth();
        EnchantEntry[] array = enchants;
        int length = enchants.length;
        for (int i = 0; i < length; ++i) {
            EnchantEntry enchant = array[i];
            int level = EnchantmentHelper.getEnchantmentLevel((int)enchant.getEnchant().effectId, (ItemStack)stack);
            String levelDisplay = "" + level;
            if (level > 10) {
                levelDisplay = "10+";
            }
            if (level <= 0) continue;
            float scale2 = 0.32f;
            GlStateManager.translate((float)(x - 2), (float)(y + 1), (float)0.0f);
            GlStateManager.scale((float)0.42f, (float)0.42f, (float)0.42f);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            fontRenderer.drawString("\u00a7f" + enchant.getName() + " " + levelDisplay, (float)(20 - fontRenderer.getStringWidth("\u00a7f" + enchant.getName() + " " + levelDisplay) / 2), 0.0f, Color.WHITE.getRGB(), true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale((float)2.42f, (float)2.42f, (float)2.42f);
            GlStateManager.translate((float)(-x), (float)(-y), (float)0.0f);
            y += (int)((float)(fontRenderer.FONT_HEIGHT + 3) * 0.28f);
        }
        renderItem.zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    void renderNameTag(EntityLivingBase entity, String tag, double x, double y, double z) {
        int health;
        if (entity instanceof EntityArmorStand || Target.isValidEntity(entity) || entity == Profiler.mc.thePlayer) {
            return;
        }
        int color = ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f);
        EntityPlayerSP player = Profiler.mc.thePlayer;
        FontRenderer fontRenderer = Profiler.mc.fontRendererObj;
        y += entity.isSneaking() ? 0.5 : 0.7;
        float distance = player.getDistanceToEntity((Entity)entity) / 4.0f;
        if (distance < 1.6f) {
            distance = 1.6f;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            String ID = entityPlayer.getName();
            if (AntiBot.bot((Entity)entityPlayer)) {
                tag = "\u00a7e" + ID;
                color = ColorUtils.color(200, 200, 0, 160);
            }
        }
        if ((double)(health = (int)entity.getHealth()) <= (double)entity.getMaxHealth() * 0.25) {
            tag = tag + "\u00a74";
        } else if ((double)health <= (double)entity.getMaxHealth() * 0.5) {
            tag = tag + "\u00a76";
        } else if ((double)health <= (double)entity.getMaxHealth() * 0.75) {
            tag = tag + "\u00a7e";
        } else if ((float)health <= entity.getMaxHealth()) {
            tag = tag + "\u00a72";
        }
        tag = String.valueOf(tag) + " " + Math.round(health);
        RenderManager renderManager = mc.getRenderManager();
        float scale = distance;
        scale /= 30.0f;
        scale = (float)((double)scale * 0.3);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)x), (float)((float)y + 1.4f), (float)((float)z));
        GL11.glNormal3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        ETessellator var14 = ETessellator.getInstance();
        BufferBuilder var15 = var14.getBuffer();
        int width = fontRenderer.getStringWidth(tag) / 2;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        Profiler.drawRect(-width - 2, -(fontRenderer.FONT_HEIGHT + 1), width + 2, 2.0f, ColorUtils.color(0.0f, 0.0f, 0.0f, 0.0f));
        Profiler.drawRect((float)(-width) - 3.0f, (float)(-(fontRenderer.FONT_HEIGHT + 1)) - 1.0f, (float)width + 3.0f, -(fontRenderer.FONT_HEIGHT + 1), color);
        fontRenderer.drawString(tag, (float)(MathUtils.getMiddle(-width - 2, width + 2) - width), (float)(-(fontRenderer.FONT_HEIGHT - 1)), Color.RED.getRGB(), true);
        if (entity instanceof EntityPlayer && this.armor.isEnabled()) {
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            GlStateManager.translate((float)0.0f, (float)1.0f, (float)0.0f);
            this.renderArmor(entityPlayer, 0, -(fontRenderer.FONT_HEIGHT + 1) - 20);
            GlStateManager.translate((float)0.0f, (float)-1.0f, (float)0.0f);
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public static class EnchantEntry {
        private Enchantment enchant;
        private String name;

        public EnchantEntry(Enchantment enchant, String name) {
            this.enchant = enchant;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public Enchantment getEnchant() {
            return this.enchant;
        }
    }
}

