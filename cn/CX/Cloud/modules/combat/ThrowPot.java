/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.ReflectionUtil;
import cn.CX.Cloud.utils.TimerUtils;
import cn.CX.Cloud.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ThrowPot
extends Module {
    private int stage = 0;
    private TimerUtils timer = new TimerUtils();
    private int oldSlot;
    private int potSlot;

    @Override
    public void enable() {
        super.enable();
        this.timer.reset();
        if (ThrowPot.mc.thePlayer == null) {
            this.setState(false);
        } else {
            this.oldSlot = ThrowPot.mc.thePlayer.inventory.currentItem;
            this.potSlot = this.getPotionSlot();
            if (this.potSlot == -1) {
                this.setState(false);
            }
        }
    }

    public ThrowPot() {
        super("ThrowPot", 0, Category.Combat);
        this.registerSetting(new Setting("Health", this, 8.0, 1.0, 20.0, true));
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        this.oldSlot = ThrowPot.mc.thePlayer.inventory.currentItem;
        this.potSlot = this.getPotionSlot();
        if (this.potSlot == -1) {
            this.setState(false);
        }
        float f = (float)Cloud.instance.settingsManager.getSettingByName(this, "Health").getValDouble();
        if (ThrowPot.mc.thePlayer.getHealth() <= ThrowPot.mc.thePlayer.getMaxHealth() - f) {
            ThrowPot.mc.thePlayer.inventory.currentItem = this.potSlot;
            ReflectionUtil.rightClickMouse();
            ThrowPot.mc.thePlayer.inventory.currentItem = this.oldSlot;
        }
    }

    public int getPotionSlot() {
        for (int i = 0; i < 8; ++i) {
            Item item;
            ItemStack itemstack = ThrowPot.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemstack == null || itemstack.isStackable() || !((item = itemstack.getItem()) instanceof ItemPotion)) continue;
            ItemPotion itempotion = (ItemPotion)item;
            if (!ItemPotion.isSplash((int)itemstack.getMetadata())) continue;
            return i;
        }
        return -1;
    }
}

