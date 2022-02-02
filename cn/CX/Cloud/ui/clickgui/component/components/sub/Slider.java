/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.EnumChatFormatting
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.ui.clickgui.component.components.sub;

import cn.CX.Cloud.file.files.SettingsSliderFile;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.ui.clickgui.component.Component;
import cn.CX.Cloud.ui.clickgui.component.components.Button;
import cn.CX.Cloud.utils.HUDUtils;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class Slider
extends Component {
    private boolean hovered;
    private Setting set;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging = false;
    private double renderWidth;

    public Slider(Setting value, Button button, int offset) {
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    public void drawTest() {
    }

    public boolean isMouseOnButtonD(int x, int y) {
        return x > this.x && x < this.x + (this.parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean isMouseOnButtonI(int x, int y) {
        return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        double diff = Math.min(88, Math.max(0, mouseX - this.x));
        double min = this.set.getMin();
        double max = this.set.getMax();
        this.renderWidth = 88.0 * (this.set.getValDouble() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.set.setValDouble(this.set.getMin());
                SettingsSliderFile.saveState();
            } else {
                double newValue = Slider.roundToPlace(diff / 88.0 * (max - min) + min, 2);
                this.set.setValDouble(newValue);
                SettingsSliderFile.saveState();
            }
        }
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        int c1 = new Color(17, 17, 17, 140).getRGB();
        int c2 = new Color(0, 0, 0, 115).getRGB();
        int c3 = new Color(34, 34, 34, 140).getRGB();
        HUDUtils.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? -1728053248 : -2013265920);
        int drag = (int)(this.set.getValDouble() / this.set.getMax() * (double)this.parent.parent.getWidth());
        HUDUtils.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 12, -2013265920);
        if (this.hovered) {
            HUDUtils.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 12, -2013265920);
        }
        HUDUtils.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -2012147439);
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.hovered ? EnumChatFormatting.AQUA + this.set.getName() + " " : this.set.getName() + " ", (float)this.parent.parent.getX() * 1.3333334f + 9.0f, (float)(this.parent.parent.getY() + this.offset + 2) * 1.3333334f + 2.0f, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.hovered ? String.format("%s%s", EnumChatFormatting.AQUA, this.set.getValDouble()) : String.valueOf(this.set.getValDouble()), (float)(this.parent.parent.getX() + 86) * 1.3333334f - (float)Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.hovered ? String.format("%s%s", EnumChatFormatting.AQUA, this.set.getValDouble()) : String.valueOf(this.set.getValDouble())), (float)(this.parent.parent.getY() + this.offset + 2) * 1.3333334f + 2.0f, -1);
        GL11.glPopMatrix();
    }
}

