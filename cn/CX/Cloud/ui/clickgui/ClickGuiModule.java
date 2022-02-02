/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package cn.CX.Cloud.ui.clickgui;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.files.ClickGuiFile;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.ui.clickgui.ClickGui;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiModule
extends Module {
    public ClickGui clickgui;
    public Setting isChinese = new Setting("Chinese Mode", this, true);

    @Override
    public void enable() {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return;
            }
            case 1634150652: {
                if (this.clickgui == null) {
                    this.clickgui = new ClickGui();
                }
                ClickGuiFile.loadClickGui();
                mc.displayGuiScreen((GuiScreen)this.clickgui);
                this.toggle();
                super.enable();
                break;
            }
            default: {
                return;
            }
        }
    }

    public ClickGuiModule() {
        super("ClickGUI", 54, Category.Render);
        this.registerSetting(this.isChinese);
        Cloud.instance.settingsManager.rSetting(new Setting("Red", this, 163.0, 0.0, 255.0, true));
        Cloud.instance.settingsManager.rSetting(new Setting("Blue", this, 223.0, 0.0, 255.0, true));
        Cloud.instance.settingsManager.rSetting(new Setting("Green", this, 255.0, 0.0, 255.0, true));
        Cloud.instance.settingsManager.rSetting(new Setting("Alpha", this, 220.0, 0.0, 255.0, true));
    }
}

