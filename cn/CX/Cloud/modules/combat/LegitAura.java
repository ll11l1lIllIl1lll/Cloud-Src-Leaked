/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.TimerUtils;
import cn.CX.Cloud.utils.Utils;
import cn.CX.Cloud.utils.Wrapper;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LegitAura
extends Module {
    static Minecraft mc = Minecraft.getMinecraft();
    TimerUtils timer = new TimerUtils();
    private static Random rand = new Random();
    public Setting minVal = new Setting("Min CPS", this, 8.0, 0.0, 20.0, true);
    public Setting maxVal = new Setting("Max CPS", this, 9.0, 0.0, 20.0, true);
    public Setting range = new Setting("Range", this, 3.5, 0.0, 10.0, true);
    public Setting aimSpeed = new Setting("AimSpeed", this, 5.0, 0.0, 30.0, true);
    public Setting yoValue = new Setting("YawOffset", this, 20.0, 0.0, 40.0, true);
    public Setting poValue = new Setting("PitchOffset", this, 20.0, 0.0, 40.0, true);
    public Setting docrit = new Setting("Critial", this, false);
    public Setting force = new Setting("Blatant", this, false);
    public Setting blockHit = new Setting("AutoBlock", this, false);
    public Setting smoothAim = new Setting("SmoothAim", this, true);
    public Setting players = new Setting("Players", this, true);
    public Setting mobs = new Setting("Mobs", this, false);
    public Setting animals = new Setting("Animal", this, false);
    public Setting invisible = new Setting("Invisible", this, false);
    public Setting useyo = new Setting("UseYawOffset", this, true);
    public Setting usepo = new Setting("UsePitchOffset", this, true);
    EntityLivingBase en = null;

    public LegitAura() {
        super("LegitAura", 0, Category.Combat);
        this.registerSetting(this.minVal);
        this.registerSetting(this.maxVal);
        this.registerSetting(this.yoValue);
        this.registerSetting(this.poValue);
        this.registerSetting(this.range);
        this.registerSetting(this.aimSpeed);
        this.registerSetting(this.smoothAim);
        this.registerSetting(this.blockHit);
        this.registerSetting(this.docrit);
        this.registerSetting(this.force);
        this.registerSetting(this.useyo);
        this.registerSetting(this.usepo);
        this.registerSetting(this.players);
        this.registerSetting(this.mobs);
        this.registerSetting(this.animals);
        this.registerSetting(this.invisible);
    }

    public double nextDouble(double startInclusive, double endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0) {
            return startInclusive;
        }
        return startInclusive + (endInclusive - startInclusive) * Math.random();
    }

    private boolean isValid(Entity entity) {
        return Minecraft.getMinecraft().thePlayer != entity && this.isValidType(entity) && entity.isEntityAlive() && (!entity.isInvisible() || this.invisible.isEnabled());
    }

    public float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f) {
            return startInclusive;
        }
        return (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    public boolean isNotItem(Object o) {
        return o instanceof EntityLivingBase;
    }

    private boolean isValidType(Entity entity) {
        return this.players.isEnabled() && entity instanceof EntityPlayer || this.mobs.isEnabled() && (entity instanceof EntityMob || entity instanceof EntitySlime) || this.animals.isEnabled() && (entity instanceof EntityVillager || entity instanceof EntityGolem) || this.animals.isEnabled() && entity instanceof EntityAnimal;
    }

    public EntityLivingBase getClosestEntityToEntity(float range, Entity ent) {
        EntityLivingBase closestEntity = null;
        float mindistance = range;
        for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            EntityLivingBase en;
            if (!this.isNotItem(o) || ent.isEntityEqual((Entity)((EntityLivingBase)o)) || !(ent.getDistanceToEntity((Entity)(en = (EntityLivingBase)o)) < mindistance)) continue;
            mindistance = ent.getDistanceToEntity((Entity)en);
            closestEntity = en;
        }
        return closestEntity;
    }

    public double getSmoothAimSpeed() {
        return 30.0 - this.aimSpeed.getValDouble();
    }

    public void facePos(Vec3 vec) {
        if (this.smoothAim.isEnabled()) {
            this.smoothFacePos(vec);
            return;
        }
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        Minecraft.getMinecraft().thePlayer.rotationYaw += MathHelper.wrapAngleTo180_float((float)(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw));
        Minecraft.getMinecraft().thePlayer.rotationPitch += MathHelper.wrapAngleTo180_float((float)(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch));
    }

    public int getAPS() {
        return (int)this.nextDouble(this.minVal.getValDouble(), this.maxVal.getValDouble());
    }

    public void stopBlock() {
        if (LegitAura.mc.thePlayer.isBlocking() || this.force.isEnabled()) {
            LegitAura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
        }
    }

    public EntityLivingBase getClosestEntity(float range) {
        EntityLivingBase closestEntity = null;
        float mindistance = range;
        for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            EntityLivingBase en;
            if (!this.isNotItem(o) || o instanceof EntityPlayerSP || !this.isValidType((Entity)(en = (EntityLivingBase)o)) || !(Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)en) < mindistance)) continue;
            mindistance = Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)en);
            closestEntity = en;
        }
        return closestEntity;
    }

    public void click() {
        Wrapper.clickMouse();
    }

    public void startBlock() {
        if (LegitAura.mc.thePlayer.isBlocking() || this.force.isEnabled()) {
            LegitAura.mc.playerController.sendUseItem((EntityPlayer)LegitAura.mc.thePlayer, (World)LegitAura.mc.theWorld, LegitAura.mc.thePlayer.getHeldItem());
            LegitAura.mc.thePlayer.setItemInUse(LegitAura.mc.thePlayer.getHeldItem(), LegitAura.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
        }
    }

    public void smoothFacePos(Vec3 vec) {
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = this.useyo.isEnabled() ? (float)(Math.atan2(diffZ, diffX) * (180.0 + this.yoValue.getValDouble() < 20.0 ? this.yoValue.getValDouble() : -this.yoValue.getValDouble() - 20.0) / Math.PI) - 90.0f : (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = this.usepo.isEnabled() ? (float)(-(Math.atan2(diffY, dist) * (180.0 + this.poValue.getValDouble() < 20.0 ? this.poValue.getValDouble() : -this.poValue.getValDouble() - 20.0) / Math.PI)) : (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        boolean aim = false;
        float max = 5.0f;
        float yawChange = 0.0f;
        if (MathHelper.wrapAngleTo180_float((float)(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) > max * 2.0f) {
            aim = true;
            yawChange = max;
        } else if (MathHelper.wrapAngleTo180_float((float)(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) < -max * 2.0f) {
            aim = true;
            yawChange = -max;
        }
        float pitchChange = 0.0f;
        if (MathHelper.wrapAngleTo180_float((float)(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)) > max * 4.0f) {
            aim = true;
            pitchChange = max;
        } else if (MathHelper.wrapAngleTo180_float((float)(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)) < -max * 4.0f) {
            aim = true;
            pitchChange = -max;
        }
        if (aim) {
            Minecraft.getMinecraft().thePlayer.rotationYaw = (float)((double)Minecraft.getMinecraft().thePlayer.rotationYaw + (double)MathHelper.wrapAngleTo180_float((float)(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) / (this.getSmoothAimSpeed() * (rand.nextDouble() * 2.0 + 1.0)));
            Minecraft.getMinecraft().thePlayer.rotationPitch = (float)((double)Minecraft.getMinecraft().thePlayer.rotationPitch + (double)MathHelper.wrapAngleTo180_float((float)(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)) / (this.getSmoothAimSpeed() * (rand.nextDouble() * 2.0 + 1.0)));
        }
    }

    public void smoothFacePos(Vec3 vec, double addSmoothing) {
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        Minecraft.getMinecraft().thePlayer.rotationYaw = (float)((double)Minecraft.getMinecraft().thePlayer.rotationYaw + (double)MathHelper.wrapAngleTo180_float((float)(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw)) / (this.getSmoothAimSpeed() * addSmoothing));
        Minecraft.getMinecraft().thePlayer.rotationPitch = (float)((double)Minecraft.getMinecraft().thePlayer.rotationPitch + (double)MathHelper.wrapAngleTo180_float((float)(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)) / (this.getSmoothAimSpeed() * addSmoothing));
    }

    public double getRange() {
        return this.range.getValDouble();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (LegitAura.mc.currentScreen != null) {
            return;
        }
        if (this.en != null && (double)LegitAura.mc.thePlayer.getDistanceToEntity((Entity)this.en) <= this.getRange()) {
            double xAim = this.en.posX - 0.5 + (this.en.posX - this.en.lastTickPosX) * 2.5;
            double yAim = this.en.posY + ((double)this.en.getEyeHeight() - (double)this.en.height / 1.5) - (this.en.posY - this.en.lastTickPosY) * 1.5;
            double zAim = this.en.posZ - 0.5 + (this.en.posZ - this.en.lastTickPosZ) * 2.5;
            this.facePos(new Vec3(xAim, yAim, zAim));
        }
        if (!this.timer.hasTimeElapsed(1000 / this.getAPS(), true)) {
            return;
        }
        this.en = this.getClosestEntity((float)this.getRange());
        if (this.en == null) {
            return;
        }
        if (this.blockHit.isEnabled()) {
            this.stopBlock();
        }
        this.click();
        if (this.blockHit.isEnabled()) {
            this.startBlock();
        }
        this.timer.reset();
    }
}

