/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.file.files;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.FileManager;
import cn.CX.Cloud.settings.Setting;

public class SettingsButtonFile {
    private static FileManager ButtonList = new FileManager("button", "ClassLoader");

    public SettingsButtonFile() {
        try {
            SettingsButtonFile.loadState();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveState() {
        try {
            ButtonList.clear();
            for (Setting setting : Cloud.instance.settingsManager.getSettings()) {
                String line = setting.getName() + ":" + setting.getParentMod().getName() + ":" + String.valueOf(setting.isEnabled());
                ButtonList.write(line);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadState() {
        try {
            for (String s : ButtonList.read()) {
                for (Setting setting : Cloud.instance.settingsManager.getSettings()) {
                    String name = s.split(":")[0];
                    String modname = s.split(":")[1];
                    boolean toggled = Boolean.parseBoolean(s.split(":")[2]);
                    if (!setting.getName().equalsIgnoreCase(name) || !setting.getParentMod().getName().equalsIgnoreCase(modname)) continue;
                    setting.setValBoolean(toggled);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

