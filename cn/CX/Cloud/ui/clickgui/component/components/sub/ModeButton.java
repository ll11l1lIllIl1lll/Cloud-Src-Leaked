/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.util.EnumChatFormatting
 *  org.lwjgl.opengl.GL11
 */
package cn.CX.Cloud.ui.clickgui.component.components.sub;

import cn.CX.Cloud.file.files.SettingsComboBoxFile;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.ui.clickgui.component.Component;
import cn.CX.Cloud.ui.clickgui.component.components.Button;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class ModeButton
extends Component {
    private boolean hovered;
    private Button parent;
    private Setting set;
    private int offset;
    private int x;
    private int y;
    private Module mod;
    private int modeIndex;

    public ModeButton(Setting set, Button button, Module mod, int offset) {
        this.set = set;
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            int maxIndex = this.set.getOptions().size();
            ++this.modeIndex;
            if (this.modeIndex + 1 > maxIndex) {
                this.modeIndex = 0;
            }
            this.set.setValString(this.set.getOptions().get(this.modeIndex));
            SettingsComboBoxFile.saveState();
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        int c1 = new Color(17, 17, 17, 140).getRGB();
        int c3 = new Color(34, 34, 34, 140).getRGB();
        Gui.drawRect((int)(this.parent.parent.getX() + 2), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + this.parent.parent.getWidth() * 1), (int)(this.parent.parent.getY() + this.offset + 12), (int)(this.hovered ? -1728053248 : -2013265920));
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + 2), (int)(this.parent.parent.getY() + this.offset + 12), (int)c1);
        GL11.glPushMatrix();
        GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.hovered ? EnumChatFormatting.AQUA + this.set.getName() + " " : this.set.getName() + " ", (float)(this.parent.parent.getX() + 7) * 1.3333334f, (float)(this.parent.parent.getY() + this.offset + 2) * 1.3333334f, Color.white.getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.set.getValString(), (float)(this.parent.parent.getX() + 86) * 1.3333334f - (float)Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.set.getValString()), (float)(this.parent.parent.getY() + this.offset + 2) * 1.3333334f, Color.white.getRGB());
        GL11.glPopMatrix();
    }
}

