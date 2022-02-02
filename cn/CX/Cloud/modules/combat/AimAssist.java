/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.combat.AutoClicker;
import cn.CX.Cloud.modules.combat.HitBox;
import cn.CX.Cloud.modules.misc.AntiBot;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AimAssist
extends Module {
    public static Setting Players;
    public static Setting Inv;
    public static Setting Mobs;
    public static Setting Animals;
    public static Setting speed;
    public static Setting compliment;
    public static Setting showaimobject;
    public static Setting fov;
    public static Setting distance;
    public static Setting clickAim;
    public static Setting weaponOnly;
    public static Setting breakBlocks;
    public static Setting blatantMode;
    public static Setting ignoreFriends;
    public static ArrayList<Entity> friends;

    public AimAssist() {
        super("AimAssist", 0, Category.Combat);
        speed = new Setting("Speed 1", this, 45.0, 5.0, 100.0, true);
        this.registerSetting(speed);
        compliment = new Setting("Speed 2", this, 15.0, 2.0, 97.0, true);
        this.registerSetting(compliment);
        fov = new Setting("FOV", this, 90.0, 15.0, 360.0, true);
        this.registerSetting(fov);
        distance = new Setting("Distance", this, 4.5, 1.0, 10.0, false);
        this.registerSetting(distance);
        clickAim = new Setting("ClickAim", this, true);
        this.registerSetting(clickAim);
        breakBlocks = new Setting("BreakBlocks", this, true);
        this.registerSetting(breakBlocks);
        ignoreFriends = new Setting("Ignore Friends", this, true);
        this.registerSetting(ignoreFriends);
        weaponOnly = new Setting("Weapon only", this, false);
        this.registerSetting(weaponOnly);
        blatantMode = new Setting("Blatant mode", this, false);
        this.registerSetting(blatantMode);
        showaimobject = new Setting("Show Entity", this, false);
        this.registerSetting(showaimobject);
        Players = new Setting("Players", this, true);
        Cloud.instance.settingsManager.rSetting(Players);
        Inv = new Setting("Inv", this, true);
        Cloud.instance.settingsManager.rSetting(Inv);
        Mobs = new Setting("Mobs", this, true);
        Cloud.instance.settingsManager.rSetting(Mobs);
        Animals = new Setting("Animals", this, true);
        Cloud.instance.settingsManager.rSetting(Animals);
    }

    static {
        friends = new ArrayList();
    }

    public static boolean autoClickerClicking() {
        Module autoclicker = Cloud.instance.moduleManager.getModule("AutoClicker");
        if (autoclicker.state) {
            return AutoClicker.leftClick.isEnabled() && Mouse.isButtonDown((int)0);
        }
        return false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        block11: {
            Entity en;
            block13: {
                Module autoclicker;
                block12: {
                    Block bl;
                    BlockPos p;
                    if (!Utils.currentScreenMinecraft()) {
                        return;
                    }
                    if (!Utils.isPlayerInGame()) {
                        return;
                    }
                    if (breakBlocks.isEnabled() && AimAssist.mc.objectMouseOver != null && (p = AimAssist.mc.objectMouseOver.getBlockPos()) != null && (bl = AimAssist.mc.theWorld.getBlockState(p).getBlock()) != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof Block) {
                        return;
                    }
                    if (weaponOnly.isEnabled() && !Utils.isPlayerHoldingWeapon()) break block11;
                    autoclicker = Cloud.instance.moduleManager.getModule("AutoClicker");
                    if (!clickAim.isEnabled()) break block12;
                    if (this.autoClickerClicking()) break block13;
                }
                if ((!Mouse.isButtonDown((int)0) || autoclicker.state) && clickAim.isEnabled()) break block11;
            }
            if ((en = this.getEnemy()) != null) {
                if (showaimobject.isEnabled()) {
                    HitBox.rh(en, Color.RED);
                }
                if (blatantMode.isEnabled()) {
                    Utils.aim(en, 0.0f, false);
                } else {
                    double n = Utils.fovFromEntity(en);
                    if (n > 1.0 || n < -1.0) {
                        double complimentSpeed = n * (ThreadLocalRandom.current().nextDouble(compliment.getValDouble() - 1.47328, compliment.getValDouble() + 2.48293) / 100.0);
                        double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble(speed.getValDouble() - 4.723847, speed.getValDouble());
                        float val = (float)(-(complimentSpeed + n / (101.0 - (double)((float)ThreadLocalRandom.current().nextDouble(speed.getValDouble() - 4.723847, speed.getValDouble())))));
                        AimAssist.mc.thePlayer.rotationYaw += val;
                    }
                }
            }
        }
    }

    public Entity getEnemy() {
        Entity en;
        int fov2 = (int)fov.getValDouble();
        Iterator var2 = AimAssist.mc.theWorld.loadedEntityList.iterator();
        do {
            if (!var2.hasNext()) {
                return null;
            }
            en = (Entity)var2.next();
        } while (ignoreFriends.isEnabled() && AimAssist.isAFriend(en) || en == AimAssist.mc.thePlayer || en.isDead || !Inv.isEnabled() && en.isInvisible() || (double)AimAssist.mc.thePlayer.getDistanceToEntity(en) > distance.getValDouble() || AntiBot.bot(en) || !blatantMode.isEnabled() && !Utils.fov(en, fov2));
        if (en instanceof EntityPlayer && Players.isEnabled()) {
            return en;
        }
        if (en instanceof EntityAnimal && Animals.isEnabled()) {
            return en;
        }
        if (en instanceof EntityMob && Mobs.isEnabled()) {
            return en;
        }
        return null;
    }

    public static void addFriend(Entity entityPlayer) {
        friends.add(entityPlayer);
    }

    public static boolean addFriend(String name) {
        boolean found = false;
        for (Entity entity : AimAssist.mc.theWorld.getLoadedEntityList()) {
            if (!entity.getName().equalsIgnoreCase(name) && !entity.getCustomNameTag().equalsIgnoreCase(name) || AimAssist.isAFriend(entity)) continue;
            AimAssist.addFriend(entity);
            found = true;
        }
        return found;
    }

    public static ArrayList<Entity> getFriends() {
        return friends;
    }

    public static boolean removeFriend(String name) {
        boolean removed = false;
        boolean found = false;
        for (NetworkPlayerInfo networkPlayerInfo : new ArrayList(mc.getNetHandler().getPlayerInfoMap())) {
            EntityPlayer entity = AimAssist.mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getDisplayName().getUnformattedText());
            if (!entity.getName().equalsIgnoreCase(name) && !entity.getCustomNameTag().equalsIgnoreCase(name)) continue;
            removed = AimAssist.removeFriend((Entity)entity);
            found = true;
        }
        return found && removed;
    }

    public static boolean removeFriend(Entity entityPlayer) {
        try {
            friends.remove(entityPlayer);
        }
        catch (Exception eeeeee) {
            eeeeee.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isAFriend(Entity entity) {
        if (entity == AimAssist.mc.thePlayer) {
            return true;
        }
        for (Entity wut : friends) {
            if (!wut.equals((Object)entity)) continue;
            return true;
        }
        try {
            EntityPlayer bruhentity = (EntityPlayer)entity;
            if (AimAssist.mc.thePlayer.isOnSameTeam((EntityLivingBase)entity) || AimAssist.mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }
}

