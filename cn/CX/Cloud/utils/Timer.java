/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.utils;

public class Timer {
    public long lastMs = 0L;
    private long time;

    public boolean sleep(float time) {
        if ((float)(this.getCurrentMS() - this.lastMs) >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public boolean sleep(long time) {
        if (this.getCurrentMS() - this.lastMs >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.time;
    }

    public boolean delay(double nextDelay) {
        return (double)(System.currentTimeMillis() - this.lastMs) >= nextDelay;
    }

    public boolean hasReached(float timeLeft) {
        return (float)(this.getCurrentMS() - this.lastMs) >= timeLeft;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMs >= milliseconds;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMs(int i) {
        this.lastMs = System.currentTimeMillis() + (long)i;
    }

    public long getLastMs() {
        return this.lastMs;
    }

    public boolean reach(long time) {
        return this.time() >= time;
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - this.lastMs > delay;
    }
}

