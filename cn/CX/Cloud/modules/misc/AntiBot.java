/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package cn.CX.Cloud.modules.misc;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiBot
extends Module {
    private static final HashMap<EntityPlayer, Long> newEnt = new HashMap();
    public static Setting a;

    public AntiBot() {
        super("AntiBot", 0, Category.Misc);
    }

    public void update() {
        if (!newEnt.isEmpty()) {
            long now = System.currentTimeMillis();
            newEnt.values().removeIf(e -> e < now - 4000L);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        if (event.entity instanceof EntityPlayer && event.entity != AntiBot.mc.thePlayer) {
            newEnt.put((EntityPlayer)event.entity, System.currentTimeMillis());
        }
    }

    public void onDisable() {
        newEnt.clear();
    }

    public static boolean bot(Entity en) {
        if (!Utils.isPlayerInGame() || AntiBot.mc.currentScreen != null) {
            return false;
        }
        Module antibot = Cloud.instance.moduleManager.getModule("AntiBot");
        if (!antibot.state) {
            return false;
        }
        if (!Utils.isHyp()) {
            return false;
        }
        if (!newEnt.isEmpty() && newEnt.containsKey(en)) {
            return true;
        }
        if (en.getName().startsWith("\u951f\u65a4\u62f7c")) {
            return true;
        }
        String n = en.getDisplayName().getUnformattedText();
        if (n.contains("\u951f\u65a4\u62f7")) {
            return n.contains("[NPC] ");
        }
        if (n.isEmpty() && en.getName().isEmpty()) {
            return true;
        }
        if (n.length() == 10) {
            char[] var4;
            int num = 0;
            int let = 0;
            for (char c : var4 = n.toCharArray()) {
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        return false;
                    }
                    ++let;
                    continue;
                }
                if (!Character.isDigit(c)) {
                    return false;
                }
                ++num;
            }
            return num >= 2 && let >= 2;
        }
        return false;
    }
}

