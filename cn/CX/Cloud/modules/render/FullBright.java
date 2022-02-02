/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullBright
extends Module {
    private float defaultGamma;
    private float clientGamma;

    @Override
    public void disable() {
        super.enable();
        FullBright.mc.gameSettings.gammaSetting = this.defaultGamma;
    }

    @Override
    public void enable() {
        this.defaultGamma = FullBright.mc.gameSettings.gammaSetting;
        super.enable();
    }

    public FullBright() {
        super("FullBright", 0, Category.Render);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Utils.isPlayerInGame()) {
            this.disable();
            return;
        }
        if (FullBright.mc.gameSettings.gammaSetting != this.clientGamma) {
            FullBright.mc.gameSettings.gammaSetting = this.clientGamma;
        }
    }
}

