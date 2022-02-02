/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Nameplate;
import java.awt.Color;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Nametags
extends Module {
    public static Setting g;
    public static Setting unnick;
    public static Setting b;
    public static Setting r;
    private Queue<Nameplate> tags;

    public Nametags() {
        super("Nametags", 0, Category.Render);
        r = new Setting("Red", this, 150.0, 0.0, 255.0, true);
        this.registerSetting(r);
        g = new Setting("Green", this, 150.0, 0.0, 255.0, true);
        this.registerSetting(g);
        b = new Setting("Blue", this, 150.0, 0.0, 255.0, true);
        this.registerSetting(b);
        unnick = new Setting("UnNick", this, false);
        this.registerSetting(unnick);
    }

    @SubscribeEvent
    public void onPreRender(RenderPlayerEvent.Pre event) {
        double v = 0.3;
        Scoreboard sb = event.entityPlayer.getWorldScoreboard();
        ScoreObjective sbObj = sb.getObjectiveInDisplaySlot(2);
        if (sbObj != null && !event.entityPlayer.getDisplayNameString().equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString()) && event.entityPlayer.getDistanceSqToEntity((Entity)Minecraft.getMinecraft().thePlayer) < 100.0) {
            v *= 2.0;
        }
        if (!event.entityPlayer.getDisplayName().equals(Minecraft.getMinecraft().thePlayer.getDisplayName())) {
            Nameplate np = new Nameplate(event.entityPlayer.getDisplayNameString(), event.x, event.y, event.z, event.entityLiving);
            np.renderNewPlate(new Color((int)r.getValDouble(), (int)g.getValDouble(), (int)b.getValDouble()));
        }
    }
}

