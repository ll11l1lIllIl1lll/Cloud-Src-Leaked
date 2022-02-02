/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package cn.CX.Cloud.modules.render;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.ColorUtils;
import cn.CX.Cloud.utils.font.FontLoaders;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD
extends Module {
    public ColorUtils c = new ColorUtils();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    public static Setting FONT;

    public HUD() {
        super("HUD", 0, Category.Render);
        FONT = new Setting("Font", this, false);
        this.registerSetting(FONT);
    }

    @SubscribeEvent
    public void onRenderRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (FONT.isEnabled()) {
            HUD hUD = this;
            HUD hUD2 = this;
            HUD hUD3 = this;
            FontLoaders.default20.drawStringWithShadow("C", 2.0, 2.0, new Color(hUD.c.rainbow().getRed(), hUD2.c.rainbow().getGreen(), hUD3.c.rainbow().getBlue()).getRGB());
            FontLoaders.default20.drawStringWithShadow("loud(" + this.sdf.format(new Date()) + ")", FontLoaders.default20.getStringWidth("C") + 2, 2.0, -1);
        } else {
            HUD hUD = this;
            HUD hUD4 = this;
            HUD hUD5 = this;
            HUD.mc.fontRendererObj.drawStringWithShadow("C", 2.0f, 2.0f, new Color(hUD.c.rainbow().getRed(), hUD4.c.rainbow().getGreen(), hUD5.c.rainbow().getBlue()).getRGB());
            HUD.mc.fontRendererObj.drawStringWithShadow("loud(" + this.sdf.format(new Date()) + ")", (float)(HUD.mc.fontRendererObj.getStringWidth("C") + 2), 2.0f, -1);
        }
        int rainbowTickc = 0;
        ScaledResolution s = new ScaledResolution(mc);
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();
        int y = 3;
        if (HUD.mc.currentScreen != null) {
            return;
        }
        ArrayList<Module> enabledModules = new ArrayList<Module>();
        Cloud cloud = Cloud.instance;
        for (Module m : cloud.moduleManager.getModules()) {
            if (!m.state) continue;
            enabledModules.add(m);
        }
        enabledModules.sort(new Comparator<Module>(){

            @Override
            public int compare(Module o1, Module o2) {
                if (FONT.isEnabled()) {
                    return FontLoaders.default16.getStringWidth(o2.getName()) - FontLoaders.default16.getStringWidth(o1.getName());
                }
                return Module.mc.fontRendererObj.getStringWidth(o2.getName()) - Module.mc.fontRendererObj.getStringWidth(o1.getName());
            }
        });
        for (Module m : enabledModules) {
            int moduleWidth;
            if (m == null || !m.getState()) continue;
            if (++rainbowTickc > 100) {
                rainbowTickc = 0;
            }
            Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / 40.0 * 1.4)) % 1.0f, 1.0f, 1.0f));
            int n = moduleWidth = FONT.isEnabled() ? FontLoaders.default16.getStringWidth(m.name) : HUD.mc.fontRendererObj.getStringWidth(m.name);
            if (FONT.isEnabled()) {
                FontLoaders.default16.drawString(m.name, width - moduleWidth - 1, y, rainbow.getRGB());
            } else {
                HUD.mc.fontRendererObj.drawString(m.name, width - moduleWidth - 1, y, rainbow.getRGB());
            }
            y += HUD.mc.fontRendererObj.FONT_HEIGHT;
        }
    }
}

