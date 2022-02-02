/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 */
package cn.CX.Cloud.modules;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.file.files.ModuleFile;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Connection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public boolean state = false;
    public int key;
    public String name;
    public Category category;

    public void disable() {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return;
            }
            case 1634150652: {
                ModuleFile.saveModules();
                break;
            }
            default: {
                return;
            }
        }
    }

    public void enable() {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return;
            }
            case 1634150652: {
                ModuleFile.saveModules();
                break;
            }
            default: {
                return;
            }
        }
    }

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
    }

    public String getName() {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return null;
            }
            case 1634150652: {
                break;
            }
            default: {
                return null;
            }
        }
        return this.name;
    }

    public int getKey() {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return 0;
            }
            case 1634150652: {
                break;
            }
            default: {
                return 0;
            }
        }
        return this.key;
    }

    public boolean getState() {
        return this.state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setState(boolean state) {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return;
            }
            case 1634150652: {
                break;
            }
            default: {
                return;
            }
        }
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (state) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            FMLCommonHandler.instance().bus().register((Object)this);
            this.enable();
        } else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            FMLCommonHandler.instance().bus().unregister((Object)this);
            this.disable();
        }
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void toggle() {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return;
            }
            case 1634150652: {
                break;
            }
            default: {
                return;
            }
        }
        this.setState(!this.state);
    }

    public void registerSetting(Setting SETIN) {
        Cloud.instance.settingsManager.rSetting(SETIN);
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        return true;
    }

    public int getKeybind() {
        return this.key;
    }
}

