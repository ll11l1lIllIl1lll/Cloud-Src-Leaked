/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.file.files;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.FileManager;
import cn.CX.Cloud.settings.Setting;

public class SettingsComboBoxFile {
    private static FileManager ComboSetting = new FileManager("combobox", "Cloud");

    public SettingsComboBoxFile() {
        try {
            SettingsComboBoxFile.loadState();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveState() {
        try {
            ComboSetting.clear();
            for (Setting setting : Cloud.instance.settingsManager.getSettings()) {
                String line = setting.getName() + ":" + setting.getParentMod().getName() + (String.valueOf(setting.getValString()) != null ? ":" + String.valueOf(setting.getValString()) : "");
                ComboSetting.write(line);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadState() {
        try {
            for (String s : ComboSetting.read()) {
                for (Setting setting : Cloud.instance.settingsManager.getSettings()) {
                    String name = s.split(":")[0];
                    String modname = s.split(":")[1];
                    String Setting2 = String.valueOf(s.split(":")[2]);
                    if (!setting.getName().equalsIgnoreCase(name) || !setting.getParentMod().getName().equalsIgnoreCase(modname)) continue;
                    setting.setValString(Setting2);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

