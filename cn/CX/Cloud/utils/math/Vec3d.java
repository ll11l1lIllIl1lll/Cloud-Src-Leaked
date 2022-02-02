/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3i
 */
package cn.CX.Cloud.utils.math;

import cn.CX.Cloud.utils.math.Vec3;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class Vec3d {
    public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
    public final double xCoord;
    public final double yCoord;
    public final double zCoord;

    public Vec3d(double x, double y, double z) {
        if (x == -0.0) {
            x = 0.0;
        }
        if (y == -0.0) {
            y = 0.0;
        }
        if (z == -0.0) {
            z = 0.0;
        }
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    public Vec3d(Vec3i vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public Vec3d add(Vec3d vec) {
        return this.addVector(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Vec3d)) {
            return false;
        }
        Vec3d vec3d = (Vec3d)p_equals_1_;
        return Double.compare(vec3d.xCoord, this.xCoord) == 0 && Double.compare(vec3d.yCoord, this.yCoord) == 0 && Double.compare(vec3d.zCoord, this.zCoord) == 0;
    }

    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }

    public int hashCode() {
        long j = Double.doubleToLongBits(this.xCoord);
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.yCoord);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.zCoord);
        i = 31 * i + (int)(j ^ j >>> 32);
        return i;
    }

    public Vec3d normalize() {
        double d0 = MathHelper.sqrt_double((double)(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord));
        return d0 < 1.0E-4 ? ZERO : new Vec3d(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
    }

    public Vec3d scale(double p_186678_1_) {
        return new Vec3d(this.xCoord * p_186678_1_, this.yCoord * p_186678_1_, this.zCoord * p_186678_1_);
    }

    public Vec3d subtract(Vec3 vec3) {
        return this.subtract(vec3.x, vec3.y, vec3.x);
    }

    public Vec3d subtract(double x, double y, double z) {
        return this.addVector(-x, -y, -z);
    }

    public Vec3d rotatePitch(float pitch) {
        float f = MathHelper.cos((float)pitch);
        float f2 = MathHelper.sin((float)pitch);
        double d0 = this.xCoord;
        double d2 = this.yCoord * (double)f + this.zCoord * (double)f2;
        double d3 = this.zCoord * (double)f - this.yCoord * (double)f2;
        return new Vec3d(d0, d2, d3);
    }

    public Vec3d rotateYaw(float yaw) {
        float f = MathHelper.cos((float)yaw);
        float f2 = MathHelper.sin((float)yaw);
        double d0 = this.xCoord * (double)f + this.zCoord * (double)f2;
        double d2 = this.yCoord;
        double d3 = this.zCoord * (double)f - this.xCoord * (double)f2;
        return new Vec3d(d0, d2, d3);
    }

    public double dotProduct(Vec3d vec) {
        return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
    }

    public Vec3d crossProduct(Vec3d vec) {
        return new Vec3d(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
    }

    public double distanceTo(Vec3 vec3) {
        double d0 = vec3.x - this.xCoord;
        double d2 = vec3.y - this.yCoord;
        double d3 = vec3.z - this.zCoord;
        return MathHelper.sqrt_double((double)(d0 * d0 + d2 * d2 + d3 * d3));
    }

    public double lengthVector() {
        return MathHelper.sqrt_double((double)(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord));
    }

    public Vec3d subtractReverse(Vec3d vec) {
        return new Vec3d(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
    }

    public static Vec3d fromPitchYaw(float p_189986_0_, float p_189986_1_) {
        float f = MathHelper.cos((float)(-p_189986_1_ * ((float)Math.PI / 180) - (float)Math.PI));
        float f2 = MathHelper.sin((float)(-p_189986_1_ * ((float)Math.PI / 180) - (float)Math.PI));
        float f3 = -MathHelper.cos((float)(-p_189986_0_ * ((float)Math.PI / 180)));
        float f4 = MathHelper.sin((float)(-p_189986_0_ * ((float)Math.PI / 180)));
        return new Vec3d(f2 * f3, f4, f * f3);
    }

    public Vec3d getIntermediateWithYValue(Vec3d vec, double y) {
        double d0 = vec.xCoord - this.xCoord;
        double d2 = vec.yCoord - this.yCoord;
        double d3 = vec.zCoord - this.zCoord;
        if (d2 * d2 < (double)1.0E-7f) {
            return null;
        }
        double d4 = (y - this.yCoord) / d2;
        return d4 >= 0.0 && d4 <= 1.0 ? new Vec3d(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
    }

    public Vec3d getIntermediateWithZValue(Vec3d vec, double z) {
        double d0 = vec.xCoord - this.xCoord;
        double d2 = vec.yCoord - this.yCoord;
        double d3 = vec.zCoord - this.zCoord;
        if (d3 * d3 < (double)1.0E-7f) {
            return null;
        }
        double d4 = (z - this.zCoord) / d3;
        return d4 >= 0.0 && d4 <= 1.0 ? new Vec3d(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
    }

    public Vec3d getIntermediateWithXValue(Vec3d vec, double x) {
        double d0 = vec.xCoord - this.xCoord;
        double d2 = vec.yCoord - this.yCoord;
        double d3 = vec.zCoord - this.zCoord;
        if (d0 * d0 < (double)1.0E-7f) {
            return null;
        }
        double d4 = (x - this.xCoord) / d0;
        return d4 >= 0.0 && d4 <= 1.0 ? new Vec3d(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
    }

    public Vec3d addVector(double x, double y, double z) {
        return new Vec3d(this.xCoord + x, this.yCoord + y, this.zCoord + z);
    }

    public double squareDistanceTo(double xIn, double yIn, double zIn) {
        double d0 = xIn - this.xCoord;
        double d2 = yIn - this.yCoord;
        double d3 = zIn - this.zCoord;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }

    public double squareDistanceTo(Vec3d vec) {
        double d0 = vec.xCoord - this.xCoord;
        double d2 = vec.yCoord - this.yCoord;
        double d3 = vec.zCoord - this.zCoord;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }

    public double lengthSquared() {
        return this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord;
    }
}

