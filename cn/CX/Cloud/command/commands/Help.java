/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package cn.CX.Cloud.command.commands;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.command.Command;
import net.minecraft.util.EnumChatFormatting;

public class Help
extends Command {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            for (Command c : Cloud.instance.commandManager.getCommands()) {
                Help.msg(c.getSyntax() + " " + EnumChatFormatting.AQUA + "- " + c.getDesc());
            }
        }
    }

    @Override
    public String getDesc() {
        return "Gives you the syntax of all commands and what they do.";
    }

    @Override
    public String getSyntax() {
        return ".help";
    }
}

