/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.C01PacketChatMessage
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.modules.misc;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.utils.Connection;
import java.lang.reflect.Field;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CommandsGetter
extends Module {
    public CommandsGetter() {
        super("CommandsGetter", 0, Category.Misc);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        boolean send = true;
        if (side == Connection.Side.OUT && packet instanceof C01PacketChatMessage) {
            Field field = ReflectionHelper.findField(C01PacketChatMessage.class, (String[])new String[]{"message", "field_149440_a"});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (packet instanceof C01PacketChatMessage) {
                    C01PacketChatMessage p = (C01PacketChatMessage)packet;
                    if (p.getMessage().subSequence(0, 1).equals(".")) {
                        send = false;
                        Cloud.instance.commandManager.execute(p.getMessage());
                        return send;
                    }
                    send = true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return send;
    }
}

