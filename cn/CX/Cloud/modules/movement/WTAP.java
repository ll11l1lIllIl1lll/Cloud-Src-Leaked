/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package cn.CX.Cloud.modules.movement;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.misc.AntiBot;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class WTAP
extends Module {
    public static Setting range;
    public static Setting onlyPlayers;
    public static Setting minActionTicks;
    public static Setting maxActionTicks;
    public static Setting minOnceEvery;
    public static Setting maxOnceEvery;
    public static double comboLasts;
    public static boolean comboing;
    public static boolean hitCoolDown;
    public static boolean alreadyHit;
    public static int hitTimeout;
    public static int hitsWaited;

    public WTAP() {
        super("WTAP", 0, Category.Movement);
        onlyPlayers = new Setting("Players Only", this, true);
        this.registerSetting(onlyPlayers);
        minActionTicks = new Setting("Min Delay: ", this, 5.0, 1.0, 100.0, true);
        this.registerSetting(minActionTicks);
        maxActionTicks = new Setting("Man Delay: ", this, 12.0, 1.0, 100.0, true);
        this.registerSetting(maxActionTicks);
        minOnceEvery = new Setting("Min Hits: ", this, 1.0, 1.0, 10.0, true);
        this.registerSetting(minOnceEvery);
        maxOnceEvery = new Setting("Max Hits: ", this, 1.0, 1.0, 10.0, true);
        this.registerSetting(maxOnceEvery);
        range = new Setting("Range: ", this, 3.0, 1.0, 6.0, false);
        this.registerSetting(range);
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        if (comboing) {
            if ((double)System.currentTimeMillis() >= comboLasts) {
                comboing = false;
                WTAP.finishCombo();
                return;
            }
            return;
        }
        if (WTAP.mc.objectMouseOver != null && WTAP.mc.objectMouseOver.entityHit instanceof Entity && Mouse.isButtonDown((int)0)) {
            Entity target = WTAP.mc.objectMouseOver.entityHit;
            if (target.isDead) {
                return;
            }
            if ((double)WTAP.mc.thePlayer.getDistanceToEntity(target) <= range.getValDouble()) {
                if (target.hurtResistantTime >= 10) {
                    if (onlyPlayers.isEnabled() && !(target instanceof EntityPlayer)) {
                        return;
                    }
                    if (AntiBot.bot(target)) {
                        return;
                    }
                    if (hitCoolDown && !alreadyHit) {
                        if (++hitsWaited >= hitTimeout) {
                            hitCoolDown = false;
                            hitsWaited = 0;
                        } else {
                            alreadyHit = true;
                            return;
                        }
                    }
                    if (!alreadyHit) {
                        this.guiUpdate();
                        hitTimeout = minOnceEvery.getValDouble() == maxOnceEvery.getValDouble() ? (int)minOnceEvery.getValDouble() : ThreadLocalRandom.current().nextInt((int)minOnceEvery.getValDouble(), (int)maxOnceEvery.getValDouble());
                        hitCoolDown = true;
                        hitsWaited = 0;
                        comboLasts = ThreadLocalRandom.current().nextDouble(minActionTicks.getValDouble(), maxActionTicks.getValDouble() + 0.01) + (double)System.currentTimeMillis();
                        comboing = true;
                        WTAP.startCombo();
                        alreadyHit = true;
                    }
                } else {
                    if (alreadyHit) {
                        // empty if block
                    }
                    alreadyHit = false;
                }
            }
        }
    }

    private static void finishCombo() {
        if (Keyboard.isKeyDown((int)WTAP.mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState((int)WTAP.mc.gameSettings.keyBindForward.getKeyCode(), (boolean)true);
        }
    }

    private static void startCombo() {
        if (Keyboard.isKeyDown((int)WTAP.mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState((int)WTAP.mc.gameSettings.keyBindForward.getKeyCode(), (boolean)false);
            KeyBinding.onTick((int)WTAP.mc.gameSettings.keyBindForward.getKeyCode());
        }
    }

    public void guiUpdate() {
        Utils.correctSliders(minActionTicks, maxActionTicks);
        Utils.correctSliders(minOnceEvery, maxOnceEvery);
    }
}

