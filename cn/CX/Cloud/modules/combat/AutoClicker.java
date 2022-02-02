/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package cn.CX.Cloud.modules.combat;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.settings.Setting;
import cn.CX.Cloud.utils.Utils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker
extends Module {
    private static long lastMS = 0L;
    public static Setting leftMinCPS;
    public static Setting rightMinCPS;
    public static Setting breakBlocksMin;
    public static Setting breakBlocksMax;
    public static Setting leftMaxCPS;
    public static Setting rightMaxCPS;
    public static Setting jitterLeft;
    public static Setting jitterRight;
    public static Setting weaponOnly;
    public static Setting breakBlocks;
    public static Setting onlyBlocks;
    public static Setting preferFastPlace;
    public static Setting noBlockSword;
    public static Setting leftClick;
    public static Setting rightClick;
    public static Setting inventoryFill;
    public static Setting allowEat;
    public static Setting allowBow;
    public static Setting rightClickDelay;
    public static Setting clickEvent;
    public static Setting clickTimings;
    public static Setting AutoBlock;
    private Random rand = null;
    private Method playerMouseInput;
    private long leftDownTime;
    private long righti;
    private long leftUpTime;
    private long rightj;
    private long leftk;
    private long rightk;
    private long leftl;
    private long rightl;
    private double leftm;
    private double rightm;
    private boolean leftn;
    private boolean rightn;
    private boolean breakHeld;
    private boolean watingForBreakTimeout;
    private double breakBlockFinishWaitTime;
    private long lastClick;
    private long leftHold;
    private long rightHold;
    private boolean rightClickWaiting;
    private double rightClickWaitStartTime;
    private boolean allowedClick;
    public static boolean autoClickerEnabled;
    public static boolean breakTimeDone;
    public static int clickFinder;
    public static int clickCount;
    ArrayList<String> sites = new ArrayList();
    ArrayList<String> sites2 = new ArrayList();

    @Override
    public void disable() {
        this.leftDownTime = 0L;
        this.leftUpTime = 0L;
        boolean leftHeld = false;
        this.rightClickWaiting = false;
        autoClickerEnabled = false;
        super.disable();
    }

    @Override
    public void enable() {
        if (this.playerMouseInput == null) {
            this.disable();
        }
        this.rightClickWaiting = false;
        this.allowedClick = false;
        this.rand = new Random();
        autoClickerEnabled = true;
        super.enable();
    }

    public AutoClicker() {
        super("AutoClicker", 0, Category.Combat);
        this.sites.add("ClassLoader");
        this.sites.add("Normal");
        this.sites2.add("RENDER");
        this.sites2.add("TICK");
        leftClick = new Setting("Left click", this, true);
        this.registerSetting(leftClick);
        leftMinCPS = new Setting("Left Min CPS", this, 9.0, 1.0, 60.0, false);
        this.registerSetting(leftMinCPS);
        leftMaxCPS = new Setting("Left Max CPS", this, 13.0, 1.0, 60.0, false);
        this.registerSetting(leftMaxCPS);
        rightClick = new Setting("Right click", this, false);
        this.registerSetting(rightClick);
        rightMinCPS = new Setting("Right Min CPS", this, 12.0, 1.0, 60.0, false);
        this.registerSetting(rightMinCPS);
        rightMaxCPS = new Setting("Right Max CPS", this, 16.0, 1.0, 60.0, false);
        this.registerSetting(rightMaxCPS);
        inventoryFill = new Setting("Inventory fill", this, false);
        this.registerSetting(inventoryFill);
        weaponOnly = new Setting("Weapon only", this, false);
        this.registerSetting(weaponOnly);
        noBlockSword = new Setting("noBlockSword", this, true);
        this.registerSetting(noBlockSword);
        onlyBlocks = new Setting("onlyBlocks", this, false);
        this.registerSetting(onlyBlocks);
        preferFastPlace = new Setting("Prefer FastPlace", this, false);
        this.registerSetting(preferFastPlace);
        breakBlocks = new Setting("Break Blocks", this, false);
        this.registerSetting(breakBlocks);
        breakBlocksMin = new Setting("Break MinDelay", this, 20.0, 0.0, 1000.0, true);
        this.registerSetting(breakBlocksMin);
        breakBlocksMax = new Setting("Break MaxDelay", this, 50.0, 0.0, 1000.0, true);
        this.registerSetting(breakBlocksMax);
        allowEat = new Setting("Allow eat", this, true);
        this.registerSetting(allowEat);
        allowBow = new Setting("Allow bow", this, true);
        this.registerSetting(allowBow);
        jitterLeft = new Setting("JitterLeft", this, 0.0, 0.0, 3.0, false);
        this.registerSetting(jitterLeft);
        jitterRight = new Setting("JitterRight", this, 0.0, 0.0, 3.0, false);
        this.registerSetting(jitterRight);
        rightClickDelay = new Setting("RightClick Delay", this, 85.0, 0.0, 500.0, true);
        this.registerSetting(rightClickDelay);
        clickTimings = new Setting("ClickStyle", this, "Normal", this.sites);
        this.registerSetting(clickTimings);
        AutoBlock = new Setting("AutoBlock", this, false);
        this.registerSetting(AutoBlock);
        clickEvent = new Setting("Event", this, "TICK", this.sites2);
        this.registerSetting(clickEvent);
        try {
            this.playerMouseInput = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        }
        catch (Exception var4) {
            try {
                this.playerMouseInput = GuiScreen.class.getDeclaredMethod("mouseClicked", Integer.TYPE, Integer.TYPE, Integer.TYPE);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.playerMouseInput != null) {
            this.playerMouseInput.setAccessible(true);
        }
        this.rightClickWaiting = false;
        autoClickerEnabled = false;
        clickFinder = 2;
        clickCount = 1;
    }

    public void rightClickExecute(int key) {
        if (!this.rightClickAllowed()) {
            return;
        }
        if (jitterRight.getValDouble() > 0.0) {
            EntityPlayerSP entityPlayer;
            double jitterMultiplier = jitterRight.getValDouble() * 0.45;
            if (this.rand.nextBoolean()) {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * jitterMultiplier);
            } else {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * jitterMultiplier);
            }
            if (this.rand.nextBoolean()) {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * jitterMultiplier * 0.45);
            } else {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * jitterMultiplier * 0.45);
            }
        }
        if (this.rightj > 0L && this.righti > 0L) {
            if (System.currentTimeMillis() > this.rightj) {
                KeyBinding.setKeyBindState((int)key, (boolean)true);
                KeyBinding.onTick((int)key);
                Utils.setMouseButtonState(1, false);
                Utils.setMouseButtonState(1, true);
                this.genRightTimings();
            } else if (System.currentTimeMillis() > this.righti) {
                KeyBinding.setKeyBindState((int)key, (boolean)false);
            }
        } else {
            this.genRightTimings();
        }
    }

    public boolean rightClickAllowed() {
        ItemStack item = AutoClicker.mc.thePlayer.getHeldItem();
        if (item != null) {
            if (allowEat.isEnabled() && item.getItem() instanceof ItemFood) {
                return false;
            }
            if (allowBow.isEnabled() && item.getItem() instanceof ItemBow) {
                return false;
            }
            if (onlyBlocks.isEnabled() && !(item.getItem() instanceof ItemBlock)) {
                return false;
            }
            if (noBlockSword.isEnabled() && item.getItem() instanceof ItemSword) {
                return false;
            }
        }
        if (preferFastPlace.isEnabled()) {
            Module fastplace = Cloud.instance.moduleManager.getModule("FastPlace");
            if (fastplace.state) {
                return false;
            }
        }
        if (rightClickDelay.getValDouble() != 0.0) {
            if (!this.rightClickWaiting && !this.allowedClick) {
                this.rightClickWaitStartTime = System.currentTimeMillis();
                this.rightClickWaiting = true;
                return false;
            }
            if (this.rightClickWaiting && !this.allowedClick) {
                double passedTime = (double)System.currentTimeMillis() - this.rightClickWaitStartTime;
                if (passedTime >= rightClickDelay.getValDouble()) {
                    this.allowedClick = true;
                    this.rightClickWaiting = false;
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    public void leftClickExecute(int key) {
        if (this.breakBlock()) {
            return;
        }
        if (jitterLeft.getValDouble() > 0.0) {
            EntityPlayerSP entityPlayer;
            double a = jitterLeft.getValDouble() * 0.45;
            if (this.rand.nextBoolean()) {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * a);
            } else {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * a);
            }
            if (this.rand.nextBoolean()) {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * a * 0.45);
            } else {
                entityPlayer = AutoClicker.mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * a * 0.45);
            }
        }
        if (this.leftUpTime > 0L && this.leftDownTime > 0L) {
            if (System.currentTimeMillis() > this.leftUpTime) {
                KeyBinding.setKeyBindState((int)key, (boolean)true);
                KeyBinding.onTick((int)key);
                this.genLeftTimings();
            } else if (System.currentTimeMillis() > this.leftDownTime) {
                KeyBinding.setKeyBindState((int)key, (boolean)false);
            }
        } else {
            this.genLeftTimings();
        }
    }

    public void genLeftTimings() {
        double clickSpeed = Utils.ranModuleVal(leftMinCPS, leftMaxCPS, this.rand) + 0.4 * this.rand.nextDouble();
        long delay = (int)Math.round(1000.0 / clickSpeed);
        if (System.currentTimeMillis() > this.leftk) {
            if (!this.leftn && this.rand.nextInt(100) >= 85) {
                this.leftn = true;
                this.leftm = 1.1 + this.rand.nextDouble() * 0.15;
            } else {
                this.leftn = false;
            }
            this.leftk = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }
        if (this.leftn) {
            delay = (long)((double)delay * this.leftm);
        }
        if (System.currentTimeMillis() > this.leftl) {
            if (this.rand.nextInt(100) >= 80) {
                delay += 50L + (long)this.rand.nextInt(100);
            }
            this.leftl = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }
        this.leftUpTime = System.currentTimeMillis() + delay;
        this.leftDownTime = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent ev) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (clickEvent.getValString() != "TICK") {
            return;
        }
        if (clickTimings.getValString() == "Normal") {
            this.ravenClick();
        } else if (clickTimings.getValString() == "ClassLoader") {
            this.skidClick(null, ev);
        }
    }

    private void ravenClick() {
        if (AutoClicker.mc.currentScreen == null && AutoClicker.mc.inGameHasFocus) {
            if (leftClick.isEnabled() && Mouse.isButtonDown((int)0)) {
                if (weaponOnly.isEnabled() && !Utils.isPlayerHoldingWeapon()) {
                    return;
                }
                this.leftClickExecute(AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode());
            } else if (rightClick.isEnabled() && Mouse.isButtonDown((int)1)) {
                this.rightClickExecute(AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode());
            } else if (!Mouse.isButtonDown((int)1)) {
                this.rightClickWaiting = false;
                this.allowedClick = false;
                this.righti = 0L;
                this.rightj = 0L;
                this.leftDownTime = 0L;
                this.leftUpTime = 0L;
            }
        } else if (inventoryFill.isEnabled() && AutoClicker.mc.currentScreen instanceof GuiInventory) {
            if (!Mouse.isButtonDown((int)0) || !Keyboard.isKeyDown((int)54) && !Keyboard.isKeyDown((int)42)) {
                this.leftDownTime = 0L;
                this.leftUpTime = 0L;
            } else if (this.leftDownTime != 0L && this.leftUpTime != 0L) {
                if (System.currentTimeMillis() > this.leftUpTime) {
                    this.genLeftTimings();
                    this.inInvClick(AutoClicker.mc.currentScreen);
                }
            } else {
                this.genLeftTimings();
            }
        }
    }

    public void genRightTimings() {
        double clickSpeed = Utils.ranModuleVal(rightMinCPS, rightMaxCPS, this.rand) + 0.4 * this.rand.nextDouble();
        long delay = (int)Math.round(1000.0 / clickSpeed);
        if (System.currentTimeMillis() > this.rightk) {
            if (!this.rightn && this.rand.nextInt(100) >= 85) {
                this.rightn = true;
                this.rightm = 1.1 + this.rand.nextDouble() * 0.15;
            } else {
                this.rightn = false;
            }
            this.rightk = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }
        if (this.rightn) {
            delay = (long)((double)delay * this.rightm);
        }
        if (System.currentTimeMillis() > this.rightl) {
            if (this.rand.nextInt(100) >= 80) {
                delay += 50L + (long)this.rand.nextInt(100);
            }
            this.rightl = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }
        this.rightj = System.currentTimeMillis() + delay;
        this.righti = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if (!Utils.currentScreenMinecraft()) {
            return;
        }
        if (clickEvent.getValString() != "RENDER") {
            return;
        }
        if (clickTimings.getValString() == "Normal") {
            this.ravenClick();
        } else if (clickTimings.getValString() == "ClassLoader") {
            this.skidClick(ev, null);
        }
    }

    private void inInvClick(GuiScreen guiScreen) {
        int mouseInGUIPosX = Mouse.getX() * guiScreen.width / AutoClicker.mc.displayWidth;
        int mouseInGUIPosY = guiScreen.height - Mouse.getY() * guiScreen.height / AutoClicker.mc.displayHeight - 1;
        try {
            this.playerMouseInput.invoke(guiScreen, mouseInGUIPosX, mouseInGUIPosY, 0);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
    }

    private void skidClick(TickEvent.RenderTickEvent er, TickEvent.PlayerTickEvent e) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        this.guiUpdate();
        double speedLeft1 = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getValDouble() - 0.2, leftMaxCPS.getValDouble());
        double leftHoldLength = speedLeft1 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getValDouble() - 0.02, leftMaxCPS.getValDouble());
        double speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getValDouble() - 0.2, rightMaxCPS.getValDouble());
        double rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getValDouble() - 0.02, rightMaxCPS.getValDouble());
        if (Mouse.isButtonDown((int)0) && leftClick.isEnabled()) {
            if (this.breakBlock()) {
                return;
            }
            if (weaponOnly.isEnabled() && !Utils.isPlayerHoldingWeapon()) {
                return;
            }
            if (jitterLeft.getValDouble() > 0.0) {
                EntityPlayerSP entityPlayer;
                double a = jitterLeft.getValDouble() * 0.45;
                if (this.rand.nextBoolean()) {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * a);
                } else {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * a);
                }
                if (this.rand.nextBoolean()) {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * a * 0.45);
                } else {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * a * 0.45);
                }
            }
            double speedLeft = 1.0 / ThreadLocalRandom.current().nextDouble(leftMinCPS.getValDouble() - 0.2, leftMaxCPS.getValDouble());
            if ((double)(System.currentTimeMillis() - this.lastClick) > speedLeft * 1000.0) {
                this.lastClick = System.currentTimeMillis();
                if (this.leftHold < this.lastClick) {
                    this.leftHold = this.lastClick;
                }
                int key = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState((int)key, (boolean)true);
                KeyBinding.onTick((int)key);
                Utils.setMouseButtonState(0, true);
            } else if ((double)(System.currentTimeMillis() - this.leftHold) > leftHoldLength * 1000.0) {
                KeyBinding.setKeyBindState((int)AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode(), (boolean)false);
                Utils.setMouseButtonState(0, false);
            }
        }
        if (Mouse.isButtonDown((int)1) && rightClick.isEnabled()) {
            if (!this.rightClickAllowed()) {
                return;
            }
            if (jitterRight.getValDouble() > 0.0) {
                EntityPlayerSP entityPlayer;
                double jitterMultiplier = jitterRight.getValDouble() * 0.45;
                if (this.rand.nextBoolean()) {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * jitterMultiplier);
                } else {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * jitterMultiplier);
                }
                if (this.rand.nextBoolean()) {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * jitterMultiplier * 0.45);
                } else {
                    entityPlayer = AutoClicker.mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * jitterMultiplier * 0.45);
                }
            }
            if ((double)(System.currentTimeMillis() - this.lastClick) > speedRight * 1000.0) {
                this.lastClick = System.currentTimeMillis();
                if (this.rightHold < this.lastClick) {
                    this.rightHold = this.lastClick;
                }
                int key = AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState((int)key, (boolean)true);
                if (clickCount / clickFinder == 0) {
                    // empty if block
                }
                ++clickCount;
                KeyBinding.onTick((int)key);
            } else if ((double)(System.currentTimeMillis() - this.rightHold) > rightHoldLength * 1000.0) {
                KeyBinding.setKeyBindState((int)AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            }
        } else if (!Mouse.isButtonDown((int)1)) {
            this.rightClickWaiting = false;
            this.allowedClick = false;
        }
    }

    public boolean breakBlock() {
        BlockPos p;
        if (breakBlocks.isEnabled() && AutoClicker.mc.objectMouseOver != null && (p = AutoClicker.mc.objectMouseOver.getBlockPos()) != null) {
            Block bl = AutoClicker.mc.theWorld.getBlockState(p).getBlock();
            if (bl != Blocks.air && !(bl instanceof BlockLiquid)) {
                if (breakBlocksMax.getValDouble() == 0.0) {
                    if (!this.breakHeld) {
                        int e = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                        KeyBinding.setKeyBindState((int)e, (boolean)true);
                        KeyBinding.onTick((int)e);
                        this.breakHeld = true;
                    }
                    return true;
                }
                if (!breakTimeDone && !this.watingForBreakTimeout) {
                    this.watingForBreakTimeout = true;
                    this.guiUpdate();
                    this.breakBlockFinishWaitTime = ThreadLocalRandom.current().nextDouble(breakBlocksMin.getValDouble(), breakBlocksMax.getValDouble() + 1.0) + (double)System.currentTimeMillis();
                    return false;
                }
                if (!breakTimeDone && this.watingForBreakTimeout && (double)System.currentTimeMillis() > this.breakBlockFinishWaitTime) {
                    breakTimeDone = true;
                    this.watingForBreakTimeout = false;
                }
                if (breakTimeDone && !this.watingForBreakTimeout) {
                    if (!this.breakHeld) {
                        int e = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                        KeyBinding.setKeyBindState((int)e, (boolean)true);
                        KeyBinding.onTick((int)e);
                        this.breakHeld = true;
                    }
                    return true;
                }
            }
            if (this.breakHeld) {
                this.breakHeld = false;
                breakTimeDone = false;
                this.watingForBreakTimeout = false;
            }
        }
        return false;
    }

    public void guiUpdate() {
        Utils.correctSliders(leftMinCPS, leftMaxCPS);
        Utils.correctSliders(rightMinCPS, rightMaxCPS);
        Utils.correctSliders(breakBlocksMin, breakBlocksMax);
    }
}

