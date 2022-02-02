/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.combat.AutoClicker;
import cn.CX.Cloud.modules.combat.HitBox;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Reach
extends Module {
    public static Setting min;
    public static Setting max;
    public static Setting weapon_only;
    public static Setting moving_only;
    public static Setting sprint_only;
    public static Setting hit_through_blocks;

    public Reach() {
        super("Reach", 0, Category.Combat);
        min = new Setting("Min Range", this, 3.1, 3.0, 6.0, false);
        this.registerSetting(min);
        max = new Setting("Max Range", this, 3.3, 3.0, 6.0, false);
        this.registerSetting(max);
        weapon_only = new Setting("Weapon only", this, false);
        this.registerSetting(weapon_only);
        moving_only = new Setting("Moving only", this, false);
        this.registerSetting(moving_only);
        sprint_only = new Setting("Sprint only", this, false);
        this.registerSetting(sprint_only);
        hit_through_blocks = new Setting("ThroughBlocks", this, false);
        this.registerSetting(hit_through_blocks);
    }

    public static boolean call() {
        BlockPos p;
        if (!Utils.isPlayerInGame()) {
            return false;
        }
        if (weapon_only.isEnabled() && !Utils.isPlayerHoldingWeapon()) {
            return false;
        }
        if (moving_only.isEnabled() && (double)Reach.mc.thePlayer.moveForward == 0.0 && (double)Reach.mc.thePlayer.moveStrafing == 0.0) {
            return false;
        }
        if (sprint_only.isEnabled() && !Reach.mc.thePlayer.isSprinting()) {
            return false;
        }
        if (!hit_through_blocks.isEnabled() && Reach.mc.objectMouseOver != null && (p = Reach.mc.objectMouseOver.getBlockPos()) != null && Reach.mc.theWorld.getBlockState(p).getBlock() != Blocks.air) {
            return false;
        }
        double r = Utils.ranModuleVal(min, max, Utils.rand());
        Object[] o = Reach.zz(r, 0.0);
        if (o == null) {
            return false;
        }
        Entity en = (Entity)o[0];
        Reach.mc.objectMouseOver = new MovingObjectPosition(en, (Vec3)o[1]);
        Reach.mc.pointedEntity = en;
        return true;
    }

    @SubscribeEvent
    public void onMouse(MouseEvent ev) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        Module autoclicker = Cloud.instance.moduleManager.getModule("AutoClicker");
        if (autoclicker.state && AutoClicker.leftClick.isEnabled() && Mouse.isButtonDown((int)0)) {
            return;
        }
        if (ev.button >= 0 && ev.buttonstate) {
            Reach.call();
        }
    }

    private static Object[] zz(double zzD, double zzE) {
        if (Cloud.instance.moduleManager.getModule((String)"Reach").state) {
            zzD = Reach.mc.playerController.extendedReach() ? 6.0 : 3.0;
        }
        Entity entity1 = mc.getRenderViewEntity();
        Entity entity = null;
        if (entity1 == null) {
            return null;
        }
        Reach.mc.mcProfiler.startSection("pick");
        Vec3 eyes_positions = entity1.getPositionEyes(1.0f);
        Vec3 look = entity1.getLook(1.0f);
        Vec3 new_eyes_pos = eyes_positions.addVector(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD);
        Vec3 zz6 = null;
        List zz8 = Reach.mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().addCoord(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD).expand(1.0, 1.0, 1.0));
        double zz9 = zzD;
        for (Object o : zz8) {
            double zz15;
            Entity zz11 = (Entity)o;
            if (!zz11.canBeCollidedWith()) continue;
            float ex = (float)((double)zz11.getCollisionBorderSize() * HitBox.exp(zz11));
            AxisAlignedBB zz13 = zz11.getEntityBoundingBox().expand((double)ex, (double)ex, (double)ex);
            zz13 = zz13.expand(zzE, zzE, zzE);
            MovingObjectPosition zz14 = zz13.calculateIntercept(eyes_positions, new_eyes_pos);
            if (zz13.isVecInside(eyes_positions)) {
                if (!(0.0 < zz9) && zz9 != 0.0) continue;
                entity = zz11;
                zz6 = zz14 == null ? eyes_positions : zz14.hitVec;
                zz9 = 0.0;
                continue;
            }
            if (zz14 == null || !((zz15 = eyes_positions.distanceTo(zz14.hitVec)) < zz9) && zz9 != 0.0) continue;
            if (zz11 == entity1.ridingEntity) {
                if (zz9 != 0.0) continue;
                entity = zz11;
                zz6 = zz14.hitVec;
                continue;
            }
            entity = zz11;
            zz6 = zz14.hitVec;
            zz9 = zz15;
        }
        if (zz9 < zzD && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Reach.mc.mcProfiler.endSection();
        if (entity != null && zz6 != null) {
            return new Object[]{entity, zz6};
        }
        return null;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        Module autoclicker = Cloud.instance.moduleManager.getModule("AutoClicker");
        if (!autoclicker.state || !AutoClicker.leftClick.isEnabled()) {
            return;
        }
        if (autoclicker.state && AutoClicker.leftClick.isEnabled() && Mouse.isButtonDown((int)0)) {
            Reach.call();
        }
    }
}

