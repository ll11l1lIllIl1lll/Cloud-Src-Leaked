/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 */
package cn.CX.Cloud.utils.math;

import net.minecraft.util.MathHelper;

public class Vec3 {
    public final double x;
    public final double y;
    public final double z;
    public static Vec3 instace;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 v) {
        return this.addVector(v.getX(), v.getY(), v.getZ());
    }

    public String toString() {
        return "[" + this.x + ";" + this.y + ";" + this.z + "]";
    }

    public Vec3 normalize() {
        double d0 = MathHelper.sqrt_double((double)(this.x * this.x + this.y * this.y + this.z * this.z));
        return d0 < 1.0E-4 ? new Vec3(0.0, 0.0, 0.0) : new Vec3(this.x / d0, this.y / d0, this.z / d0);
    }

    public Vec3 floor() {
        return new Vec3(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public net.minecraft.util.Vec3 mc2() {
        return new net.minecraft.util.Vec3(this.x, this.y, this.z);
    }

    public Vec3 mc() {
        return new Vec3(this.x, this.y, this.z);
    }

    public Vec3 addVector(double x, double y, double z) {
        return new Vec3(this.x + x, this.y + y, this.z + z);
    }

    public double squareDistanceTo(Vec3 v) {
        return Math.pow(v.x - this.x, 2.0) + Math.pow(v.y - this.y, 2.0) + Math.pow(v.z - this.z, 2.0);
    }

    public double getZ() {
        return this.z;
    }
}

