/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.file.files;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.FileManager;
import cn.CX.Cloud.modules.Module;

public class ModuleFile {
    private static FileManager ModuleList = new FileManager("modules", "Cloud");

    public ModuleFile() {
        try {
            ModuleFile.loadModules();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveModules() {
        try {
            ModuleList.clear();
            Cloud cloud = Cloud.instance;
            for (Module module : cloud.moduleManager.getModules()) {
                String line = module.getName() + ":" + String.valueOf(module.getState());
                ModuleList.write(line);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadModules() {
        try {
            for (String s : ModuleList.read()) {
                Cloud cloud = Cloud.instance;
                for (Module module : cloud.moduleManager.getModules()) {
                    String name = s.split(":")[0];
                    boolean toggled = Boolean.parseBoolean(s.split(":")[1]);
                    if (!module.getName().equalsIgnoreCase(name) || !toggled) continue;
                    module.toggle();
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

