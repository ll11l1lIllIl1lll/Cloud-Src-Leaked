/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Velocity
extends Module {
    public static Setting a;
    public static Setting b;
    public static Setting c;
    public static Setting d;
    public static Setting e;

    public Velocity() {
        super("Velocity", 0, Category.Combat);
        a = new Setting("Horizontal", this, 90.0, 0.0, 100.0, true);
        this.registerSetting(a);
        b = new Setting("Vertical", this, 100.0, 0.0, 100.0, true);
        this.registerSetting(b);
        c = new Setting("Chance", this, 100.0, 0.0, 100.0, true);
        this.registerSetting(c);
        d = new Setting("On Targeting", this, false);
        this.registerSetting(d);
        e = new Setting("No Sword", this, false);
        this.registerSetting(e);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent ev) {
        if (Utils.isPlayerInGame() && Velocity.mc.thePlayer.maxHurtTime > 0 && Velocity.mc.thePlayer.hurtTime == Velocity.mc.thePlayer.maxHurtTime) {
            double ch;
            if (d.isEnabled() && (Velocity.mc.objectMouseOver == null || Velocity.mc.objectMouseOver.entityHit == null)) {
                return;
            }
            if (e.isEnabled() && Keyboard.isKeyDown((int)Velocity.mc.gameSettings.keyBindBack.getKeyCode())) {
                return;
            }
            if (c.getValDouble() != 100.0 && (ch = Math.random()) >= c.getValDouble() / 100.0) {
                return;
            }
            if (a.getValDouble() != 100.0) {
                Velocity.mc.thePlayer.motionX *= a.getValDouble() / 100.0;
                Velocity.mc.thePlayer.motionZ *= a.getValDouble() / 100.0;
            }
            if (b.getValDouble() != 100.0) {
                Velocity.mc.thePlayer.motionY *= b.getValDouble() / 100.0;
            }
        }
    }
}

