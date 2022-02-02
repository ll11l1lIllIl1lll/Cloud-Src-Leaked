/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.MathHelper
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package cn.CX.Cloud.modules.movement;

import cn.CX.Cloud.modules.Category;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.utils.Utils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class GuiWalk
extends Module {
    public GuiWalk() {
        super("GuiWalk", 0, Category.Movement);
    }

    void moveForward(double speed) {
        float direction = Utils.getDirection();
        GuiWalk.mc.thePlayer.motionX -= (double)MathHelper.sin((float)direction) * speed;
        GuiWalk.mc.thePlayer.motionZ += (double)MathHelper.cos((float)direction) * speed;
    }

    void handleLeft(double speed) {
        if (!Keyboard.isKeyDown((int)GuiWalk.mc.gameSettings.keyBindLeft.getKeyCode())) {
            return;
        }
        this.moveLeft(speed);
    }

    void handleRight(double speed) {
        if (!Keyboard.isKeyDown((int)GuiWalk.mc.gameSettings.keyBindRight.getKeyCode())) {
            return;
        }
        this.moveRight(speed);
    }

    void handleForward(double speed) {
        if (!Keyboard.isKeyDown((int)GuiWalk.mc.gameSettings.keyBindForward.getKeyCode())) {
            return;
        }
        this.moveForward(speed);
    }

    void handleJump() {
        if (GuiWalk.mc.thePlayer.onGround && Keyboard.isKeyDown((int)GuiWalk.mc.gameSettings.keyBindJump.getKeyCode())) {
            GuiWalk.mc.thePlayer.jump();
        }
    }

    void moveBack(double speed) {
        float direction = Utils.getDirection();
        GuiWalk.mc.thePlayer.motionX += (double)MathHelper.sin((float)direction) * speed;
        GuiWalk.mc.thePlayer.motionZ -= (double)MathHelper.cos((float)direction) * speed;
    }

    void moveRight(double speed) {
        float direction = Utils.getDirection();
        GuiWalk.mc.thePlayer.motionZ -= (double)MathHelper.sin((float)direction) * speed;
        GuiWalk.mc.thePlayer.motionX -= (double)MathHelper.cos((float)direction) * speed;
    }

    void moveLeft(double speed) {
        float direction = Utils.getDirection();
        GuiWalk.mc.thePlayer.motionZ += (double)MathHelper.sin((float)direction) * speed;
        GuiWalk.mc.thePlayer.motionX += (double)MathHelper.cos((float)direction) * speed;
    }

    void handleBack(double speed) {
        if (!Keyboard.isKeyDown((int)GuiWalk.mc.gameSettings.keyBindBack.getKeyCode())) {
            return;
        }
        this.moveBack(speed);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        if (GuiWalk.mc.currentScreen != null && !(GuiWalk.mc.currentScreen instanceof GuiChat)) {
            KeyBinding[] keys = new KeyBinding[]{GuiWalk.mc.gameSettings.keyBindForward, GuiWalk.mc.gameSettings.keyBindBack, GuiWalk.mc.gameSettings.keyBindLeft, GuiWalk.mc.gameSettings.keyBindRight, GuiWalk.mc.gameSettings.keyBindJump};
            int length = keys.length;
            for (int i = 0; i < length; ++i) {
                KeyBinding.setKeyBindState((int)keys[i].getKeyCode(), (boolean)Keyboard.isKeyDown((int)keys[i].getKeyCode()));
            }
        }
    }
}

