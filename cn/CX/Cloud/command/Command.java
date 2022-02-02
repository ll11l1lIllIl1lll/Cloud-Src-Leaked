/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 */
package cn.CX.Cloud.command;

import cn.CX.Cloud.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public abstract class Command {
    public abstract String getName();

    public abstract void execute(String[] var1);

    public abstract String getDesc();

    public static void msg(String msg) {
        ChatUtils.report(msg);
    }

    public void normal(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(msg));
    }

    public abstract String getSyntax();

    public String getCmd() {
        return this.getName();
    }

    public String getName1() {
        return this.getName();
    }

    public String getHelp() {
        return null;
    }
}

