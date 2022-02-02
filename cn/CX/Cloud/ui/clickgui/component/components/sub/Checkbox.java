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

import cn.CX.Cloud.file.files.SettingsButtonFile;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.ui.clickgui.component.Component;
import cn.CX.Cloud.ui.clickgui.component.components.Button;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class Checkbox
extends Component {
    private boolean hovered;
    private Setting op;
    private Button parent;
    private int offset;
    private int x;
    private int y;

    public Checkbox(Setting option, Button button, int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.op.setValBoolean(!this.op.isEnabled());
            SettingsButtonFile.saveState();
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
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.hovered ? EnumChatFormatting.AQUA + this.op.getName() : this.op.getName(), (float)(this.parent.parent.getX() + 3) * 1.3333334f + 5.0f, (float)(this.parent.parent.getY() + this.offset + 2) * 1.3333334f + 2.0f, -1);
        GL11.glPopMatrix();
        Gui.drawRect((int)(this.parent.parent.getX() + this.parent.parent.getWidth() - 2), (int)(this.parent.parent.getY() + this.offset + 3), (int)(this.parent.parent.getX() + this.parent.parent.getWidth() - 8), (int)(this.parent.parent.getY() + this.offset + 9), (int)-2003199591);
        if (this.op.isEnabled()) {
            Gui.drawRect((int)(this.parent.parent.getX() + this.parent.parent.getWidth() - 3), (int)(this.parent.parent.getY() + this.offset + 4), (int)(this.parent.parent.getX() + this.parent.parent.getWidth() - 7), (int)(this.parent.parent.getY() + this.offset + 8), (int)-1728053248);
        }
    }
}

