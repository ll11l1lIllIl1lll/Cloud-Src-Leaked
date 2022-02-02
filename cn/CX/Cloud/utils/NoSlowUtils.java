/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.util.MovementInput
 */
package cn.CX.Cloud.utils;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;

public class NoSlowUtils
extends MovementInput {
    private GameSettings gameSettings;
    boolean NoSlowBoolean = true;

    public NoSlowUtils(GameSettings par1GameSettings) {
        this.gameSettings = par1GameSettings;
    }

    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            this.moveForward += 1.0f;
        }
        if (this.gameSettings.keyBindBack.isKeyDown()) {
            this.moveForward -= 1.0f;
        }
        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            this.moveStrafe += 1.0f;
        }
        if (this.gameSettings.keyBindRight.isKeyDown()) {
            this.moveStrafe -= 1.0f;
        }
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
        if (this.NoSlowBoolean) {
            this.moveStrafe *= 5.0f;
            this.moveForward *= 5.0f;
        }
    }

    public void setNSD(boolean a) {
        this.NoSlowBoolean = a;
    }
}

