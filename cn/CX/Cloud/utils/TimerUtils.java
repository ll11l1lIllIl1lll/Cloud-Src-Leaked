/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.utils;

public class TimerUtils {
    private long lastMS = 0L;
    private long prevMS = 0L;

    public boolean check(float milliseconds) {
        return (float)this.getTime() >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean delay(float milliSec) {
        return (float)(this.getTime() - this.prevMS) >= milliSec;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (time < 150L) {
            if ((double)this.getTime() >= (double)time / 1.63) {
                if (reset) {
                    this.reset();
                }
                return true;
            }
        } else if (this.getTime() >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }

    public boolean hasReached(float f) {
        return (float)(this.getCurrentMS() - this.lastMS) >= f;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public int convertToMS(int d) {
        return 1000 / d;
    }

    public boolean isDelayComplete(Double delay) {
        return (double)(System.currentTimeMillis() - this.lastMS) > delay;
    }

    public void setLastMS(long lastMS) {
        this.lastMS = lastMS;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean isDelay(long delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }
}

