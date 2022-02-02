/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.projectile.EntityFireball
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.RotationUtil;
import cn.CX.Cloud.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiFireBall
extends Module {
    public Setting range = new Setting("Range", this, 4.5, 1.0, 6.0, true);
    public Setting rotate = new Setting("Rotate", this, false);

    public AntiFireBall() {
        super("AntiFireBall", 0, Category.Combat);
        this.registerSetting(this.range);
        this.registerSetting(this.rotate);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        for (Entity entity : AntiFireBall.mc.theWorld.loadedEntityList) {
            double rangeToEntity;
            if (!(entity instanceof EntityFireball) || !((rangeToEntity = (double)AntiFireBall.mc.thePlayer.getDistanceToEntity(entity)) <= this.range.getValDouble())) continue;
            AntiFireBall.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
            if (!this.rotate.isEnabled()) continue;
            float[] rotation = RotationUtil.getRotations(entity);
            AntiFireBall.mc.thePlayer.rotationYaw = rotation[0];
            AntiFireBall.mc.thePlayer.rotationPitch = rotation[1];
        }
    }
}

