/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package cn.CX.Cloud.modules.movement;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeepSprint
extends Module {
    public KeepSprint() {
        super("KeepSprint", 0, Category.Movement);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (!KeepSprint.mc.thePlayer.isSprinting()) {
            KeepSprint.mc.thePlayer.setSprinting(true);
        }
    }
}

