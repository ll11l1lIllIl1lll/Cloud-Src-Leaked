/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.network.play.server.S0BPacketAnimation
 *  net.minecraft.potion.Potion
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.ChatUtils;
import cn.CX.Cloud.utils.Connection;
import cn.CX.Cloud.utils.JReflectUtility;
import cn.CX.Cloud.utils.TimerUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Criticals
extends Module {
    public Setting mode;
    public Setting debug;
    public TimerUtils timer;
    boolean cancelSomePackets;
    public static boolean disable;
    int targetid;
    ArrayList<String> sites = new ArrayList();

    public Criticals() {
        super("Criticals", 0, Category.Combat);
        this.sites.add("Packet");
        this.sites.add("Jump");
        this.sites.add("PJump");
        this.sites.add("AAC5");
        this.sites.add("NoGround");
        this.sites.add("Hypixel");
        this.mode = new Setting("Mode", this, "AAC5", this.sites);
        this.debug = new Setting("Debug", this, true);
        this.registerSetting(this.mode);
        this.registerSetting(this.debug);
        this.timer = new TimerUtils();
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        S0BPacketAnimation s08;
        if (this.mode.getValString().equals("NoGround")) {
            if (Criticals.mc.thePlayer.onGround && side == Connection.Side.OUT) {
                if (packet instanceof C02PacketUseEntity) {
                    C02PacketUseEntity attack = (C02PacketUseEntity)packet;
                    if (attack.getAction() == C02PacketUseEntity.Action.ATTACK) {
                        Field crit = ReflectionHelper.findField(C02PacketUseEntity.class, (String[])new String[]{"entityId", "field_149567_a"});
                        try {
                            if (!crit.isAccessible()) {
                                crit.setAccessible(true);
                            }
                            this.targetid = crit.getInt(attack);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        if (this.mode.getValString().equals("Packet")) {
                            if (Criticals.mc.thePlayer.isCollidedVertically && this.timer.isDelay(500L)) {
                                Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.0627, Criticals.mc.thePlayer.posZ, false));
                                Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                                Entity entity = attack.getEntityFromWorld((World)Criticals.mc.theWorld);
                                if (entity != null) {
                                    Criticals.mc.thePlayer.onCriticalHit(entity);
                                }
                                this.timer.setLastMS();
                                this.cancelSomePackets = true;
                            }
                        } else if (this.mode.getValString().equals("Jump")) {
                            if (this.canJump()) {
                                Criticals.mc.thePlayer.jump();
                            }
                        } else if (this.mode.getValString().equals("PJump")) {
                            if (this.canJump()) {
                                Criticals.mc.thePlayer.jump();
                                Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.0031311231111, Criticals.mc.thePlayer.posZ, false));
                            }
                        } else if (this.mode.getValString().equals("Hypixel") && Criticals.mc.thePlayer.isCollidedVertically && this.timer.isDelay(500L)) {
                            Criticals.hypixelCrit();
                            this.timer.setLastMS();
                            this.cancelSomePackets = true;
                        }
                    }
                } else if (this.mode.getValString().equals("Packet") && packet instanceof C03PacketPlayer && this.cancelSomePackets) {
                    this.cancelSomePackets = false;
                    return false;
                }
            }
        } else if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer p = (C03PacketPlayer)packet;
            Field field = ReflectionHelper.findField(C03PacketPlayer.class, (String[])new String[]{"onGround", "field_149474_g"});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.setBoolean(p, false);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        if (this.debug.isEnabled() && side == Connection.Side.IN && packet instanceof S0BPacketAnimation && (s08 = (S0BPacketAnimation)packet).getAnimationType() == 4 && s08.getEntityID() == this.targetid) {
            ChatUtils.report("Crit!");
        }
        return true;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getValString().equals("AAC5")) {
            if (Criticals.mc.thePlayer.onGround) {
                if (Criticals.mc.thePlayer.hurtTime > 0 && Criticals.mc.thePlayer.hurtTime <= 6) {
                    Criticals.mc.thePlayer.motionX *= 0.600151164;
                    Criticals.mc.thePlayer.motionZ *= 0.600151164;
                    JReflectUtility.timer().timerSpeed = 0.9f;
                }
                if (Criticals.mc.thePlayer.hurtTime > 0 && Criticals.mc.thePlayer.hurtTime <= 4) {
                    Criticals.mc.thePlayer.motionX *= 0.800151164;
                    Criticals.mc.thePlayer.motionZ *= 0.800151164;
                    JReflectUtility.timer().timerSpeed = 0.9f;
                }
            } else if (Criticals.mc.thePlayer.hurtTime > 0 && Criticals.mc.thePlayer.hurtTime <= 9) {
                Criticals.mc.thePlayer.motionX *= 0.8001421204;
                Criticals.mc.thePlayer.motionZ *= 0.8001421204;
                JReflectUtility.timer().timerSpeed = 0.9f;
            }
        }
    }

    boolean canJump() {
        if (Criticals.mc.thePlayer.isOnLadder()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isInWater()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isInLava()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isSneaking()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isRiding()) {
            return false;
        }
        if (Criticals.mc.thePlayer.isPotionActive(Potion.blindness)) {
            return false;
        }
        if (Criticals.mc.thePlayer.onGround) {
            return true;
        }
        return true;
    }

    public static void hypixelCrit() {
        for (double offset : new double[]{0.0212622959183674, 0.0, 0.0521, 0.02474, 0.01, 0.001}) {
            Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + offset, Criticals.mc.thePlayer.posZ, false));
        }
    }
}

