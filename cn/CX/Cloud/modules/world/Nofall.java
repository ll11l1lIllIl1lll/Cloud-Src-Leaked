/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Connection;
import cn.CX.Cloud.utils.Utils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Nofall
extends Module {
    public Setting mode;
    public Setting fallDistance;
    ArrayList<String> sites = new ArrayList();

    public Nofall() {
        super("NoFall", 0, Category.World);
        this.sites.add("Vanilla");
        this.sites.add("Reduce");
        this.sites.add("Reflection");
        this.mode = new Setting("Mode", this, "Vanilla", this.sites);
        this.fallDistance = new Setting("Fall Distance", this, 3.0, 3.0, 10.0, false);
        this.registerSetting(this.mode);
        this.registerSetting(this.fallDistance);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (!Utils.currentScreenMinecraft()) {
            return false;
        }
        if (side == Connection.Side.OUT && packet instanceof C03PacketPlayer) {
            C03PacketPlayer p = (C03PacketPlayer)packet;
            if (this.mode.getValString() == "AAC") {
                Field field = ReflectionHelper.findField(C03PacketPlayer.class, (String[])new String[]{"onGround", "field_149474_g"});
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.setBoolean(p, true);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (Nofall.mc.thePlayer.fallDistance > 3.0f) {
            if (this.mode.getValString() == "Vanilla") {
                Nofall.mc.thePlayer.fallDistance = 0.0f;
                mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer(true));
            } else if (this.mode.getValString() == "Reduce") {
                Nofall.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(Nofall.mc.thePlayer.posX, (double)(-Nofall.mc.theWorld.getHeight()), Nofall.mc.thePlayer.posZ, Nofall.mc.thePlayer.rotationYaw, Nofall.mc.thePlayer.rotationPitch, true));
            }
        }
    }
}

