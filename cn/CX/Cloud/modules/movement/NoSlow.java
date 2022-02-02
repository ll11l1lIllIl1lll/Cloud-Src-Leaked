/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package cn.CX.Cloud.modules.movement;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.MoveUtils;
import cn.CX.Cloud.utils.NoSlowUtils;
import cn.CX.Cloud.utils.PlayerUtil;
import cn.CX.Cloud.utils.Utils;
import cn.CX.Cloud.utils.Wrapper;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoSlow
extends Module {
    private float cacheStrafe = 0.0f;
    private float cacheForward = 0.0f;
    MovementInput origmi;
    ArrayList<String> sites = new ArrayList();
    private Setting mode;
    private Setting percent = new Setting("Percent", this, 0.0, 0.0, 100.0, true);

    @Override
    public void disable() {
        NoSlow.mc.thePlayer.movementInput = this.origmi;
    }

    @Override
    public void enable() {
        this.origmi = NoSlow.mc.thePlayer.movementInput;
        if (!(NoSlow.mc.thePlayer.movementInput instanceof NoSlowUtils)) {
            NoSlow.mc.thePlayer.movementInput = new NoSlowUtils(NoSlow.mc.gameSettings);
        }
    }

    public NoSlow() {
        super("NoSlow", 0, Category.Movement);
        this.sites.add("Vanilla");
        this.sites.add("NCP");
        this.sites.add("AAC");
        this.sites.add("Custom");
        this.mode = new Setting("Mode", this, "Vanilla", this.sites);
        this.registerSetting(this.mode);
        this.registerSetting(this.percent);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        double z;
        double y;
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (!(NoSlow.mc.thePlayer.movementInput instanceof NoSlowUtils)) {
            this.origmi = NoSlow.mc.thePlayer.movementInput;
            NoSlow.mc.thePlayer.movementInput = new NoSlowUtils(NoSlow.mc.gameSettings);
        }
        if (NoSlow.mc.thePlayer.onGround && !NoSlow.mc.gameSettings.keyBindJump.isKeyDown() || NoSlow.mc.gameSettings.keyBindSneak.isKeyDown() && NoSlow.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            NoSlowUtils move = (NoSlowUtils)NoSlow.mc.thePlayer.movementInput;
            move.setNSD(true);
        }
        if (NoSlow.mc.thePlayer.isUsingItem() && PlayerUtil.isMoving() && MoveUtils.isOnGround(0.42) && (this.mode.getValString() == "AAC" || this.mode.getValString() == "NCP")) {
            if (this.mode.getValString() == "AAC") {
                Wrapper.getTimer((Minecraft)NoSlow.mc).timerSpeed = 0.7f;
            }
            double x = NoSlow.mc.thePlayer.posX;
            y = NoSlow.mc.thePlayer.posY;
            z = NoSlow.mc.thePlayer.posZ;
            NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        if (!NoSlow.mc.thePlayer.isUsingItem() && this.mode.getValString() == "AAC") {
            Wrapper.getTimer((Minecraft)NoSlow.mc).timerSpeed = 1.0f;
        }
        if (this.mode.getValString() == "Custom") {
            try {
                if (NoSlow.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) {
                    NoSlow.mc.thePlayer.motionX *= 0.5;
                    NoSlow.mc.thePlayer.motionZ *= 0.5;
                }
                if (NoSlow.mc.thePlayer.isUsingItem()) {
                    NoSlow.mc.thePlayer.motionX *= this.percent.getValDouble() / 100.0;
                    NoSlow.mc.thePlayer.motionZ *= this.percent.getValDouble() / 100.0;
                }
            }
            catch (NullPointerException x) {
                // empty catch block
            }
        }
        if (NoSlow.mc.thePlayer.isUsingItem() && PlayerUtil.isMoving() && MoveUtils.isOnGround(0.42) && (this.mode.getValString() == "AAC" || this.mode.getValString() == "NCP")) {
            double x = NoSlow.mc.thePlayer.posX;
            y = NoSlow.mc.thePlayer.posY;
            z = NoSlow.mc.thePlayer.posZ;
            NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(NoSlow.mc.thePlayer.inventory.getCurrentItem()));
        }
    }
}

