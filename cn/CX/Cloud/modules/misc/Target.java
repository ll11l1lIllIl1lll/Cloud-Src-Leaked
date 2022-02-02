/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemStack
 */
package cn.CX.Cloud.modules.misc;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import java.util.ArrayList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class Target
extends Module {
    public static Setting Team;
    public static Setting Player;
    public static Setting Mobs;
    ArrayList<String> sites = new ArrayList();
    ArrayList<String> sites2 = new ArrayList();

    public Target() {
        super("Teams", 0, Category.Misc);
        this.sites.add("Base");
        this.sites.add("ArmorColor");
        this.sites.add("NameColor");
        Player = new Setting("Player", this, true);
        Mobs = new Setting("Mobs", this, true);
        Team = new Setting("Teams", this, "Base", this.sites);
        this.registerSetting(Team);
    }

    public static boolean checkEnemyNameColor(EntityLivingBase entity) {
        String name = entity.getDisplayName().getFormattedText();
        return !Target.getEntityNameColor((EntityLivingBase)Target.mc.thePlayer).equals(Target.getEntityNameColor(entity));
    }

    public static String getEntityNameColor(EntityLivingBase entity) {
        String name = entity.getDisplayName().getFormattedText();
        if (name.contains("\u00a7")) {
            if (name.contains("\u00a71")) {
                return "\u00a71";
            }
            if (name.contains("\u00a72")) {
                return "\u00a72";
            }
            if (name.contains("\u00a73")) {
                return "\u00a73";
            }
            if (name.contains("\u00a74")) {
                return "\u00a74";
            }
            if (name.contains("\u00a75")) {
                return "\u00a75";
            }
            if (name.contains("\u00a76")) {
                return "\u00a76";
            }
            if (name.contains("\u00a77")) {
                return "\u00a77";
            }
            if (name.contains("\u00a78")) {
                return "\u00a78";
            }
            if (name.contains("\u00a79")) {
                return "\u00a79";
            }
            if (name.contains("\u00a70")) {
                return "\u00a70";
            }
            if (name.contains("\u00a7e")) {
                return "\u00a7e";
            }
            if (name.contains("\u00a7d")) {
                return "\u00a7d";
            }
            if (name.contains("\u00a7a")) {
                return "\u00a7a";
            }
            if (name.contains("\u00a7b")) {
                return "\u00a7b";
            }
            if (name.contains("\u00a7c")) {
                return "\u00a7c";
            }
            if (name.contains("\u00a7f")) {
                return "\u00a7f";
            }
        }
        return "null";
    }

    public static int getPlayerArmorColor(EntityPlayer player, ItemStack stack) {
        if (player == null || stack == null || stack.getItem() == null || !(stack.getItem() instanceof ItemArmor)) {
            return -1;
        }
        ItemArmor itemArmor = (ItemArmor)stack.getItem();
        if (itemArmor == null || itemArmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER) {
            return -1;
        }
        return itemArmor.getColor(stack);
    }

    public static boolean checkEnemyColor(EntityPlayer enemy) {
        int colorEnemy0 = Target.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(0));
        int colorEnemy1 = Target.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(1));
        int colorEnemy2 = Target.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(2));
        int colorEnemy3 = Target.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(3));
        int colorPlayer0 = Target.getPlayerArmorColor((EntityPlayer)Target.mc.thePlayer, Target.mc.thePlayer.inventory.armorItemInSlot(0));
        int colorPlayer1 = Target.getPlayerArmorColor((EntityPlayer)Target.mc.thePlayer, Target.mc.thePlayer.inventory.armorItemInSlot(1));
        int colorPlayer2 = Target.getPlayerArmorColor((EntityPlayer)Target.mc.thePlayer, Target.mc.thePlayer.inventory.armorItemInSlot(2));
        int colorPlayer3 = Target.getPlayerArmorColor((EntityPlayer)Target.mc.thePlayer, Target.mc.thePlayer.inventory.armorItemInSlot(3));
        return !(colorEnemy0 == colorPlayer0 && colorPlayer0 != -1 && colorEnemy0 != 1 || colorEnemy1 == colorPlayer1 && colorPlayer1 != -1 && colorEnemy1 != 1 || colorEnemy2 == colorPlayer2 && colorPlayer2 != -1 && colorEnemy2 != 1) && (colorEnemy3 != colorPlayer3 || colorPlayer3 == -1 || colorEnemy3 == 1);
    }

    public static boolean isValidEntity(EntityLivingBase e) {
        if (Player.isEnabled() && e instanceof EntityPlayer) {
            return false;
        }
        return !Mobs.isEnabled() || !(e instanceof EntityLiving);
    }

    public static boolean isTeam(EntityLivingBase entity) {
        Module Target2 = Cloud.instance.moduleManager.getModule("Target");
        if (Target2.getState() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (Team.getValString() == "Base" && player.getTeam() != null && Target.mc.thePlayer.getTeam() != null && player.getTeam().isSameTeam(Target.mc.thePlayer.getTeam())) {
                return false;
            }
            if (Team.getValString() == "ArmorColor" && !Target.checkEnemyColor(player)) {
                return false;
            }
            if (Team.getValString() == "NameColor" && !Target.checkEnemyNameColor((EntityLivingBase)player)) {
                return false;
            }
        }
        return false;
    }
}

