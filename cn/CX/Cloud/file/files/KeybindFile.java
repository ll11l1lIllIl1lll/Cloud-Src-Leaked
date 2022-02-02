/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.file.files;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.FileManager;
import cn.CX.Cloud.modules.Module;

public class KeybindFile {
    private static FileManager bindList = new FileManager("binds", "Cloud");

    public KeybindFile() {
        try {
            KeybindFile.loadKeybinds();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadKeybinds() {
        try {
            for (String s : bindList.read()) {
                Cloud cloud = Cloud.instance;
                for (Module module : cloud.moduleManager.getModules()) {
                    String name = s.split(":")[0];
                    int key = Integer.parseInt(s.split(":")[1]);
                    if (!module.getName().equalsIgnoreCase(name)) continue;
                    module.setKey(key);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveKeybinds() {
        try {
            bindList.clear();
            Cloud cloud = Cloud.instance;
            for (Module module : cloud.moduleManager.getModules()) {
                String line = module.getName() + ":" + String.valueOf(module.getKeybind());
                bindList.write(line);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

