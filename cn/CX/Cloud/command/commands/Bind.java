/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 *  org.lwjgl.input.Keyboard
 */
package cn.CX.Cloud.command.commands;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.command.Command;
import cn.CX.Cloud.file.files.KeybindFile;
import cn.CX.Cloud.modules.Module;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    @Override
    public String getName() {
        return "bind";
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            Bind.msg(this.getAll());
        } else if (args.length == 2) {
            String key = args[0];
            String value = args[1];
            if (key.equalsIgnoreCase("reset")) {
                Module mod = Cloud.instance.moduleManager.getModule(value);
                if (mod == null) {
                    Bind.msg(String.format(EnumChatFormatting.LIGHT_PURPLE + "The module " + EnumChatFormatting.DARK_AQUA + "%s " + EnumChatFormatting.RED + "was not found!", value));
                } else {
                    mod.key = 0;
                    Bind.msg("Succesfully reset the bind for " + Keyboard.getKeyName((int)mod.key) + "!");
                }
                return;
            }
            Module mod = Cloud.instance.moduleManager.getModule(key);
            if (mod == null) {
                Bind.msg(String.format(EnumChatFormatting.LIGHT_PURPLE + "The specified key or module was not found!", value));
            } else {
                mod.key = Keyboard.getKeyIndex((String)value);
                Bind.msg(String.format("Bound %s to " + EnumChatFormatting.DARK_AQUA + "%s !", mod.getName(), value));
            }
        } else {
            Bind.msg(EnumChatFormatting.LIGHT_PURPLE + "Syntax Error.");
        }
        KeybindFile.saveKeybinds();
    }

    @Override
    public String getDesc() {
        return "Sets binds for modules.";
    }

    public String getAll() {
        return this.getSyntax() + " - " + this.getDesc();
    }

    @Override
    public String getSyntax() {
        return ".bind <module> <key>";
    }
}

