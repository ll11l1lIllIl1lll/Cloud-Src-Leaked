/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MathHelper
 */
package cn.CX.Cloud.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.util.MathHelper;

public class MathUtils {
    public static float sin(float value) {
        return MathHelper.sin((float)value);
    }

    public static float cos(float value) {
        return MathHelper.cos((float)value);
    }

    public static int ceil(float value) {
        return MathHelper.ceiling_float_int((float)value);
    }

    public static int ceil(double value) {
        return MathHelper.ceiling_double_int((double)value);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int clamp(int num, int min, int max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp(double num, double min, double max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static float clamp(float num, float min, float max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double getMiddleDouble(int i, int j) {
        return ((double)i + (double)j) / 2.0;
    }

    public static int floor_double(double value) {
        return MathHelper.floor_double((double)value);
    }

    public static int floor_double(float value) {
        return MathHelper.floor_double((double)value);
    }

    public static double wrapDegrees(double value) {
        return MathHelper.wrapAngleTo180_double((double)value);
    }

    public static float wrapDegrees(float value) {
        return MathHelper.wrapAngleTo180_float((float)value);
    }

    public static int getMiddle(int i, int j) {
        return (i + j) / 2;
    }

    public static float getAngleDifference(float direction, float rotationYaw) {
        float phi = Math.abs(rotationYaw - direction) % 360.0f;
        float distance = phi > 180.0f ? 360.0f - phi : phi;
        return distance;
    }
}

