/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Mappings;
import cn.CX.Cloud.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Timer
extends Module {
    Setting timerspeed;
    net.minecraft.util.Timer timer = (net.minecraft.util.Timer)ReflectionHelper.getPrivateValue(Minecraft.class, (Object)mc, (String[])new String[]{Mappings.timer});

    @Override
    public void disable() {
        this.timer.timerSpeed = 1.0f;
        super.disable();
    }

    public Timer() {
        super("Timer", 0, Category.World);
        this.timerspeed = new Setting("TimerSpeed", this, 2.0, 1.0, 5.0, false);
        this.registerSetting(this.timerspeed);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        this.timer.timerSpeed = (float)this.timerspeed.getValDouble();
    }
}

