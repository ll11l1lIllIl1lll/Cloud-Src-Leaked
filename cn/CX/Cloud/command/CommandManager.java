/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.util.EnumChatFormatting
 */
package cn.CX.Cloud.command;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.command.Command;
import cn.CX.Cloud.command.commands.Bind;
import cn.CX.Cloud.command.commands.Help;
import cn.CX.Cloud.command.commands.Toggle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.EnumChatFormatting;

public class CommandManager {
    public NetHandlerPlayClient sendQueue;
    private static CommandManager me = new CommandManager();
    private List<Command> commands = new ArrayList<Command>();
    private String prefix = ".";

    public CommandManager() {
        this.add(new Bind());
        this.add(new Help());
        this.add(new Toggle());
    }

    public void add(Command command) {
        this.commands.add(command);
    }

    public static CommandManager get() {
        return me;
    }

    public boolean execute(String text) {
        switch (Cloud.Data.hashCode()) {
            case 1324820516: {
                return false;
            }
            case 1634150652: {
                break;
            }
            default: {
                return false;
            }
        }
        if (!text.startsWith(this.prefix)) {
            return false;
        }
        text = text.substring(1);
        String[] arguments = text.split(" ");
        String ranCmd = arguments[0];
        for (Command cmd : this.commands) {
            if (!cmd.getName().equalsIgnoreCase(arguments[0])) continue;
            String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
            String[] args1 = text.split(" ");
            cmd.execute(args);
            return true;
        }
        Command.msg("The command" + EnumChatFormatting.AQUA + ranCmd + EnumChatFormatting.GREEN + " has not been found!");
        return false;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}

