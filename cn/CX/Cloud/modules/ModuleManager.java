/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.modules;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.combat.AimAssist;
import cn.CX.Cloud.modules.combat.AutoClicker;
import cn.CX.Cloud.modules.combat.Criticals;
import cn.CX.Cloud.modules.combat.HitBox;
import cn.CX.Cloud.modules.combat.LegitAura;
import cn.CX.Cloud.modules.combat.ThrowPot;
import cn.CX.Cloud.modules.combat.Velocity;
import cn.CX.Cloud.modules.misc.AntiBot;
import cn.CX.Cloud.modules.misc.CommandsGetter;
import cn.CX.Cloud.modules.misc.Target;
import cn.CX.Cloud.modules.movement.GuiWalk;
import cn.CX.Cloud.modules.movement.KeepSprint;
import cn.CX.Cloud.modules.movement.NoSlow;
import cn.CX.Cloud.modules.movement.Sprint;
import cn.CX.Cloud.modules.movement.WTAP;
import cn.CX.Cloud.modules.render.ESP;
import cn.CX.Cloud.modules.render.FullBright;
import cn.CX.Cloud.modules.render.HUD;
import cn.CX.Cloud.modules.render.Nametags;
import cn.CX.Cloud.modules.render.Profiler;
import cn.CX.Cloud.modules.render.Search;
import cn.CX.Cloud.modules.render.Tracers;
import cn.CX.Cloud.modules.world.AntiFireBall;
import cn.CX.Cloud.modules.world.AutoArmor;
import cn.CX.Cloud.modules.world.AutoMLG;
import cn.CX.Cloud.modules.world.AutoTool;
import cn.CX.Cloud.modules.world.FastPlace;
import cn.CX.Cloud.modules.world.FuckBed;
import cn.CX.Cloud.modules.world.Nofall;
import cn.CX.Cloud.modules.world.Scaffold;
import cn.CX.Cloud.modules.world.Timer;
import cn.CX.Cloud.ui.clickgui.ClickGuiModule;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static ArrayList<Module> list = new ArrayList();
    public static volatile ModuleManager INSTANCE = new ModuleManager();

    static {
        list.add(new AutoClicker());
        list.add(new LegitAura());
        list.add(new AimAssist());
        list.add(new Velocity());
        list.add(new Criticals());
        list.add(new HitBox());
        list.add(new ThrowPot());
        list.add(new HUD());
        list.add(new ClickGuiModule());
        list.add(new ESP());
        list.add(new Tracers());
        list.add(new FullBright());
        list.add(new Nametags());
        list.add(new Profiler());
        list.add(new Search());
        list.add(new FastPlace());
        list.add(new Scaffold());
        list.add(new AutoTool());
        list.add(new AutoMLG());
        list.add(new AutoArmor());
        list.add(new FuckBed());
        list.add(new Nofall());
        list.add(new Timer());
        list.add(new AntiFireBall());
        list.add(new Sprint());
        list.add(new GuiWalk());
        list.add(new WTAP());
        list.add(new NoSlow());
        list.add(new KeepSprint());
        list.add(new AntiBot());
        list.add(new CommandsGetter());
        list.add(new Target());
    }

    public Module getModule(String name) {
        for (Module m : list) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }

    public final List<Module> getModulesForCategory(Category category) {
        ArrayList<Module> localModules = new ArrayList<Module>();
        ArrayList<Module> modules = list;
        int modulesSize = modules.size();
        for (int i = 0; i < modulesSize; ++i) {
            Module module = modules.get(i);
            if (module.getCategory() != category) continue;
            localModules.add(module);
        }
        return localModules;
    }

    public static ArrayList<Module> getList() {
        return list;
    }

    public static ArrayList<Module> getModules() {
        return list;
    }
}

