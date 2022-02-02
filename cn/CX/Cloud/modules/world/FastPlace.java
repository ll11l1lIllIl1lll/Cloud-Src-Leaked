/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import java.lang.reflect.Field;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastPlace
extends Module {
    public static Setting a;
    public static Setting b;
    public static Field r;

    public FastPlace() {
        super("FastPlace", 0, Category.World);
        a = new Setting("Delay", this, 0.0, 0.0, 4.0, true);
        this.registerSetting(a);
        b = new Setting("Blocks only", this, true);
        this.registerSetting(b);
        try {
            r = mc.getClass().getDeclaredField("field_71467_ac");
        }
        catch (Exception var4) {
            try {
                r = mc.getClass().getDeclaredField("rightClickDelayTimer");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (r != null) {
            r.setAccessible(true);
        }
    }

    static {
        r = null;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        if (e.phase == TickEvent.Phase.END && Utils.isPlayerInGame() && FastPlace.mc.inGameHasFocus && r != null) {
            ItemStack item;
            if (b.isEnabled() && ((item = FastPlace.mc.thePlayer.getHeldItem()) == null || !(item.getItem() instanceof ItemBlock))) {
                return;
            }
            try {
                int c = (int)a.getValDouble();
                if (c == 0) {
                    r.set(mc, 0);
                } else {
                    if (c == 4) {
                        return;
                    }
                    int d = r.getInt(mc);
                    if (d == 4) {
                        r.set(mc, c);
                    }
                }
            }
            catch (IllegalAccessException illegalAccessException) {
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                // empty catch block
            }
        }
    }
}

