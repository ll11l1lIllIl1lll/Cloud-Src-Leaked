/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 */
package cn.CX.Cloud.ui.clickgui.component;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.ui.clickgui.component.Component;
import cn.CX.Cloud.ui.clickgui.component.components.Button;
import cn.CX.Cloud.utils.FontUtil;
import cn.CX.Cloud.utils.HUDUtils;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;

public class Frame {
    public ArrayList<Component> components = new ArrayList();
    public Category category;
    public boolean open;
    public int width;
    public int y;
    public int x;
    public int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private int padding;

    public Frame(Category cat) {
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (Module mod : Cloud.instance.moduleManager.getModulesForCategory(this.category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }

    public boolean isOpen() {
        return this.open;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public int getWidth() {
        return this.width;
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        Module cgui = Cloud.instance.moduleManager.getModule("ClickGUI");
        int color = new Color((int)Cloud.instance.settingsManager.getSettingByName(cgui, "Red").getValDouble(), (int)Cloud.instance.settingsManager.getSettingByName(cgui, "Green").getValDouble(), (int)Cloud.instance.settingsManager.getSettingByName(cgui, "Blue").getValDouble(), (int)Cloud.instance.settingsManager.getSettingByName(cgui, "Alpha").getValDouble()).getRGB();
        HUDUtils.rect(this.x - 2, this.y - 2, this.x + this.width + 2, this.y + this.barHeight, color);
        try {
            FontUtil.drawTotalCenteredStringWithShadow(this.category.name(), this.x + this.width / 2, this.y + 7 - 1, -1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (this.open && !this.components.isEmpty()) {
            for (Component component : this.components) {
                component.renderComponent();
            }
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }
}

