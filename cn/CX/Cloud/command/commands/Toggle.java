/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package cn.CX.Cloud.command.commands;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.command.Command;
import cn.CX.Cloud.file.files.ModuleFile;
import cn.CX.Cloud.modules.Module;
import net.minecraft.util.EnumChatFormatting;

public class Toggle
extends Command {
    @Override
    public String getName() {
        return "t";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Toggle.msg(this.getAll());
        } else {
            String module = args[0];
            Module mod = Cloud.instance.moduleManager.getModule(module);
            if (mod == null) {
                Toggle.msg(EnumChatFormatting.LIGHT_PURPLE + "The requested module was not found!");
            } else {
                Cloud.instance.moduleManager.getModule(module).toggle();
                Toggle.msg(String.format(EnumChatFormatting.DARK_AQUA + "%s " + EnumChatFormatting.AQUA + "has been %s", Cloud.instance.moduleManager.getModule(module).getName(), Cloud.instance.moduleManager.getModule(module).getState() ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.LIGHT_PURPLE + "disabled."));
                ModuleFile.saveModules();
            }
        }
    }

    @Override
    public String getDesc() {
        return "Toggles modules.";
    }

    public String getAll() {
        return this.getSyntax() + " - " + this.getDesc();
    }

    @Override
    public String getSyntax() {
        return ".t";
    }
}

