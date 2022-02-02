/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package cn.CX.Cloud.modules.movement;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint
extends Module {
    public Sprint() {
        super("Sprint", 0, Category.Movement);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Cloud.instance.moduleManager.getModule("KeepSprint").getState() && !Sprint.mc.thePlayer.isCollidedHorizontally && Sprint.mc.thePlayer.moveForward > 0.0f) {
            Sprint.mc.thePlayer.setSprinting(true);
        }
    }
}

