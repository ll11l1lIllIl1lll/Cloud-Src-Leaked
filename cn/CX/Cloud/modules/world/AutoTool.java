/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemShears
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemTool
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Mouse
 */
package cn.CX.Cloud.modules.world;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.modules.combat.AutoClicker;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class AutoTool
extends Module {
    private final Setting hotkeyBack = new Setting("Hotkey back", this, true);
    private Setting doDelay;
    private Setting minDelay;
    private Setting maxDelay;
    public static int previousSlot;
    public static boolean justFinishedMining;
    public static boolean mining;

    public AutoTool() {
        super("AutoTool", 0, Category.World);
        this.registerSetting(this.hotkeyBack);
        this.doDelay = new Setting("Random delay", this, true);
        this.registerSetting(this.doDelay);
        this.minDelay = new Setting("Min delay", this, 25.0, 0.0, 600.0, true);
        this.registerSetting(this.minDelay);
        this.maxDelay = new Setting("Max delay", this, 100.0, 0.0, 600.0, true);
        this.registerSetting(this.maxDelay);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Utils.isPlayerInGame() || AutoTool.mc.currentScreen != null) {
            return;
        }
        if (Mouse.isButtonDown((int)0)) {
            Block stateBlock;
            if (AutoClicker.autoClickerEnabled && !AutoClicker.breakBlocks.isEnabled()) {
                return;
            }
            BlockPos lookingAtBlock = AutoTool.mc.objectMouseOver.getBlockPos();
            if (lookingAtBlock != null && (stateBlock = AutoTool.mc.theWorld.getBlockState(lookingAtBlock).getBlock()) != Blocks.air && !(stateBlock instanceof BlockLiquid) && stateBlock instanceof Block) {
                if (!mining) {
                    previousSlot = Utils.getCurrentPlayerSlot();
                    mining = true;
                }
                int index = -1;
                double speed = 1.0;
                for (int slot = 0; slot <= 8; ++slot) {
                    Block bl;
                    BlockPos p;
                    ItemStack itemInSlot = AutoTool.mc.thePlayer.inventory.getStackInSlot(slot);
                    if (itemInSlot != null && itemInSlot.getItem() instanceof ItemTool) {
                        p = AutoTool.mc.objectMouseOver.getBlockPos();
                        bl = AutoTool.mc.theWorld.getBlockState(p).getBlock();
                        if (!((double)itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed)) continue;
                        speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                        index = slot;
                        continue;
                    }
                    if (itemInSlot == null || !(itemInSlot.getItem() instanceof ItemShears)) continue;
                    p = AutoTool.mc.objectMouseOver.getBlockPos();
                    bl = AutoTool.mc.theWorld.getBlockState(p).getBlock();
                    if (!((double)itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed)) continue;
                    speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                    index = slot;
                }
                if (index != -1 && !(speed <= 1.1) && speed != 0.0) {
                    Utils.hotkeyToSlot(index);
                }
            }
        } else if (mining) {
            this.finishMining();
        }
    }

    public void finishMining() {
        if (this.hotkeyBack.isEnabled()) {
            Utils.hotkeyToSlot(previousSlot);
        }
        justFinishedMining = false;
        mining = false;
    }

    public static void hotkeyToPickAxe() {
        for (int slot = 0; slot <= 8; ++slot) {
            ItemStack itemInSlot = AutoTool.mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot == null || !(itemInSlot.getItem() instanceof ItemPickaxe)) continue;
            BlockPos p = AutoTool.mc.objectMouseOver.getBlockPos();
            Block block = AutoTool.mc.theWorld.getBlockState(p).getBlock();
        }
    }
}

