/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package cn.CX.Cloud.ui.clickgui.component.components;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.ui.clickgui.ClickGui;
import cn.CX.Cloud.ui.clickgui.component.Component;
import cn.CX.Cloud.ui.clickgui.component.Frame;
import cn.CX.Cloud.ui.clickgui.component.components.sub.Checkbox;
import cn.CX.Cloud.ui.clickgui.component.components.sub.Keybind;
import cn.CX.Cloud.ui.clickgui.component.components.sub.ModeButton;
import cn.CX.Cloud.ui.clickgui.component.components.sub.Slider;
import cn.CX.Cloud.utils.FontUtil;
import cn.CX.Cloud.utils.HUDUtils;
import java.util.ArrayList;
import net.minecraft.util.EnumChatFormatting;

public class Button
extends Component {
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    public ArrayList<Component> subcomponents;
    public boolean open;
    public int height;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList();
        this.open = false;
        this.height = 12;
        int opY = offset + 12;
        if (Cloud.instance.settingsManager.getSettingsByMod(mod) != null) {
            for (Setting s : Cloud.instance.settingsManager.getSettingsByMod(mod)) {
                if (s.isCombo()) {
                    this.subcomponents.add(new ModeButton(s, this, mod, opY));
                    opY += 12;
                }
                if (s.isSlider()) {
                    this.subcomponents.add(new Slider(s, this, opY));
                    opY += 12;
                }
                if (!s.isCheck()) continue;
                this.subcomponents.add(new Checkbox(s, this, opY));
                opY += 12;
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
        int opY = this.offset + 12;
        for (Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        HUDUtils.rect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, 0x33000000);
        HUDUtils.rect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, 0x33000000);
        if (this.mod.state && this.isHovered) {
            HUDUtils.rect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, 0x20000000);
        }
        if (this.mod.state) {
            HUDUtils.rect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, 0x40000000);
        }
        if (this.isHovered) {
            HUDUtils.rect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, 0x30000000);
        }
        String Name = this.mod.getName();
        FontUtil.drawTotalCenteredStringWithShadow(this.mod.state ? Name : (this.isHovered ? EnumChatFormatting.AQUA + this.mod.getName() : EnumChatFormatting.WHITE + this.mod.getName()), this.parent.getX() + this.parent.getWidth() / 2, this.parent.getY() + this.offset + 7, -5707);
        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.renderComponent();
            }
            HUDUtils.rect(this.parent.getX() + 2, this.parent.getY() + this.offset + 12, this.parent.getX() + 3, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, ClickGui.color);
        }
    }
}

