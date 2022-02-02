/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.settings;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import java.util.ArrayList;

public class SettingsManager {
    private ArrayList<Setting> settings = new ArrayList();

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<Setting>();
        for (Setting s : this.getSettings()) {
            if (!s.getParentMod().equals(mod)) continue;
            out.add(s);
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public void rSetting(Setting in) {
        this.settings.add(in);
    }

    public Setting getSettingByName(String name) {
        for (Setting set : this.getSettings()) {
            if (!set.getName().equalsIgnoreCase(name)) continue;
            return set;
        }
        System.err.println("[" + Cloud.NAME + "] Error Setting NOT found: '" + name + "'!");
        return null;
    }

    public Setting getSettingByName(Module mod, String name) {
        for (Setting set : this.getSettings()) {
            if (!set.getName().equalsIgnoreCase(name) || set.getParentMod() != mod) continue;
            return set;
        }
        System.err.println("[" + Cloud.NAME + "] Error Setting NOT found: '" + name + "'!");
        return null;
    }
}

