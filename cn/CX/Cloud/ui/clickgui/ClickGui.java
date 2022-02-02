/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 */
package cn.CX.Cloud.ui.clickgui;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.files.ClickGuiFile;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.render.HUD;
import cn.CX.Cloud.ui.clickgui.component.Component;
import cn.CX.Cloud.ui.clickgui.component.Frame;
import cn.CX.Cloud.utils.HUDUtils;
import cn.CX.Cloud.utils.ParticleGenerator;
import cn.CX.Cloud.utils.ReflectionUtil;
import cn.CX.Cloud.utils.font.FontLoaders;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui
extends GuiScreen {
    public static ArrayList<Frame> frames;
    public static int color;
    public static boolean ISCN;
    private ParticleGenerator particleGen;

    public ClickGui() {
        frames = new ArrayList();
        this.particleGen = new ParticleGenerator(60, HUDUtils.getScaledRes().getScaledWidth(), HUDUtils.getScaledRes().getScaledHeight());
        int frameX = 5;
        for (Category category : Category.values()) {
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }

    static {
        color = -1714430721;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void onGuiClosed() {
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            try {
                ReflectionUtil.theShaderGroup.set(Minecraft.getMinecraft().entityRenderer, null);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ClickGuiFile.saveClickGui();
        super.onGuiClosed();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ClickGui.func_73734_a((int)0, (int)0, (int)this.width, (int)this.height, (int)0x66101010);
        for (Frame frame : frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            for (Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            try {
                ReflectionUtil.theShaderGroup.set(Minecraft.getMinecraft().entityRenderer, null);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (!HUD.FONT.isEnabled()) {
            this.mc.fontRendererObj.drawStringWithShadow("[" + ChatFormatting.RED + "ClassLoader CustomHacker" + ChatFormatting.WHITE + "]" + ChatFormatting.GRAY + Cloud.QQ, 0.0f, (float)(this.height - 8), Color.WHITE.getRGB());
            this.mc.fontRendererObj.drawStringWithShadow(Cloud.NAME, (float)(this.width - this.mc.fontRendererObj.getStringWidth(Cloud.NAME) - 2), (float)(this.height - 8), new Color(50, 240, 145).getRGB());
        } else {
            FontLoaders.default16.drawStringWithShadow("[" + ChatFormatting.RED + "ClassLoader CustomHacker" + ChatFormatting.WHITE + "]" + ChatFormatting.GRAY + Cloud.QQ, 0.0, this.height - 8, Color.WHITE.getRGB());
            FontLoaders.default16.drawStringWithShadow(Cloud.NAME, this.width - FontLoaders.default16.getStringWidth(Cloud.NAME) - 2, this.height - 8, new Color(50, 240, 145).getRGB());
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        for (Frame frame : frames) {
            if (!frame.isOpen() || keyCode == 1 || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.keyTyped(typedChar, keyCode);
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException {
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) {
            frame.setDrag(false);
        }
        for (Frame frame : frames) {
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component : frame.getComponents()) {
                component.mouseReleased(mouseX, mouseY, state);
            }
        }
    }
}

