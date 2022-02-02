/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.file.files;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.FileManager;
import cn.CX.Cloud.settings.Setting;

public class SettingsSliderFile {
    private static FileManager SliderValue = new FileManager("slider", "Cloud");

    public SettingsSliderFile() {
        try {
            SettingsSliderFile.loadState();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveState() {
        try {
            SliderValue.clear();
            for (Setting setting : Cloud.instance.settingsManager.getSettings()) {
                String line = setting.getName() + ":" + setting.getParentMod().getName() + ":" + String.valueOf(setting.getValDouble());
                SliderValue.write(line);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadState() {
        try {
            for (String s : SliderValue.read()) {
                for (Setting setting : Cloud.instance.settingsManager.getSettings()) {
                    String name = s.split(":")[0];
                    String modname = s.split(":")[1];
                    double value = Double.parseDouble(s.split(":")[2]);
                    if (!setting.getName().equalsIgnoreCase(name) || !setting.getParentMod().getName().equalsIgnoreCase(modname)) continue;
                    setting.setValDouble(value);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

