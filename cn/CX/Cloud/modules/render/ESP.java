/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.misc.AntiBot;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.ColorUtils;
import cn.CX.Cloud.utils.HUDUtils;
import cn.CX.Cloud.utils.Utils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP
extends Module {
    ArrayList<String> sites = new ArrayList();
    private int rgb_c = 0;
    public static Setting Players;
    public static Setting Inv;
    public static Setting Mobs;
    public static Setting Animals;
    public Setting RainBow;

    public ESP() {
        super("ESP", 0, Category.Render);
        this.sites.add("2D");
        this.sites.add("Arrow");
        this.sites.add("Box");
        this.sites.add("Health");
        this.sites.add("Ring");
        this.sites.add("Shaded");
        Cloud.instance.settingsManager.rSetting(new Setting("Red", this, 0.0, 0.0, 255.0, true));
        Cloud.instance.settingsManager.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
        Cloud.instance.settingsManager.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
        this.RainBow = new Setting("RainBow", this, true);
        Cloud.instance.settingsManager.rSetting(this.RainBow);
        Players = new Setting("Players", this, true);
        Cloud.instance.settingsManager.rSetting(Players);
        Inv = new Setting("Inv", this, true);
        Cloud.instance.settingsManager.rSetting(Inv);
        Mobs = new Setting("Mobs", this, true);
        Cloud.instance.settingsManager.rSetting(Mobs);
        Animals = new Setting("Animals", this, true);
        Cloud.instance.settingsManager.rSetting(Animals);
        Cloud.instance.settingsManager.rSetting(new Setting("Types", this, "Box", this.sites));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        int Red = (int)Cloud.instance.settingsManager.getSettingByName(this, "Red").getValDouble();
        int Blue = (int)Cloud.instance.settingsManager.getSettingByName(this, "Blue").getValDouble();
        int Green = (int)Cloud.instance.settingsManager.getSettingByName(this, "Green").getValDouble();
        String Type2 = Cloud.instance.settingsManager.getSettingByName(this, "Types").getValString();
        int rgb = this.RainBow.isEnabled() ? ColorUtils.rainbow().getRGB() : new Color(Red, Blue, Green).getRGB();
        Iterator var3 = ESP.mc.theWorld.loadedEntityList.iterator();
        while (var3.hasNext()) {
            Entity en = (Entity)var3.next();
            if (en == ESP.mc.thePlayer || en.isDead || !Inv.isEnabled() && en.isInvisible() || AntiBot.bot(en)) continue;
            if (en instanceof EntityPlayer && Players.isEnabled()) {
                if (Type2 == "2D") {
                    HUDUtils.ee(en, 3, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Arrow") {
                    HUDUtils.ee(en, 5, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Box") {
                    HUDUtils.ee(en, 1, 0.5, 1.0, rgb, false);
                }
                if (Type2 == "Health") {
                    HUDUtils.ee(en, 4, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Ring") {
                    HUDUtils.ee(en, 6, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Shaded") {
                    HUDUtils.ee(en, 2, 0.5, 1.0, rgb, true);
                }
            }
            if (en instanceof EntityAnimal && Animals.isEnabled()) {
                if (Type2 == "2D") {
                    HUDUtils.ee(en, 3, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Arrow") {
                    HUDUtils.ee(en, 5, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Box") {
                    HUDUtils.ee(en, 1, 0.5, 1.0, rgb, false);
                }
                if (Type2 == "Health") {
                    HUDUtils.ee(en, 4, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Ring") {
                    HUDUtils.ee(en, 6, 0.5, 1.0, rgb, true);
                }
                if (Type2 == "Shaded") {
                    HUDUtils.ee(en, 2, 0.5, 1.0, rgb, true);
                }
            }
            if (!(en instanceof EntityMob) || !Mobs.isEnabled()) continue;
            if (Type2 == "2D") {
                HUDUtils.ee(en, 3, 0.5, 1.0, rgb, true);
            }
            if (Type2 == "Arrow") {
                HUDUtils.ee(en, 5, 0.5, 1.0, rgb, true);
            }
            if (Type2 == "Box") {
                HUDUtils.ee(en, 1, 0.5, 1.0, rgb, false);
            }
            if (Type2 == "Health") {
                HUDUtils.ee(en, 4, 0.5, 1.0, rgb, true);
            }
            if (Type2 == "Ring") {
                HUDUtils.ee(en, 6, 0.5, 1.0, rgb, true);
            }
            if (Type2 != "Shaded") continue;
            HUDUtils.ee(en, 2, 0.5, 1.0, rgb, true);
        }
        return;
    }
}

