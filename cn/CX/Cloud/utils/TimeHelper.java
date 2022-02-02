/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.utils;

public class TimeHelper {
    private static long lastMS = 0L;
    private long resetMS = 0L;

    public static void reset() {
        lastMS = TimeHelper.getCurrentMS();
    }

    public static long getCurrentTime() {
        return (long)((double)System.nanoTime() / 1000000.0);
    }

    public long getDelay() {
        return System.currentTimeMillis() - lastMS;
    }

    public boolean hasReached(long milliseconds) {
        return TimeHelper.getCurrentMS() - lastMS >= milliseconds;
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public int convertToMS(int d) {
        return 1000 / d;
    }

    public static boolean hasDelayRun(long resetMS, int delay) {
        return TimeHelper.getCurrentTime() >= resetMS + (long)delay;
    }

    public boolean hasDelayRun(double d) {
        return (double)TimeHelper.getCurrentTime() >= (double)this.resetMS + d;
    }

    public void resetAndAdd(long reset) {
        this.resetMS = TimeHelper.getCurrentTime() + reset;
    }

    public boolean hasTimeReached(long delay) {
        return System.currentTimeMillis() - lastMS >= delay;
    }

    public static boolean hasTimePassedMS(long MS) {
        return TimeHelper.getCurrentMS() >= lastMS + MS;
    }

    public static boolean hasTimePassedMS(long LastMS, long MS) {
        return TimeHelper.getCurrentMS() >= LastMS + MS;
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - lastMS >= delay;
    }

    public void setLastMS() {
        lastMS = System.currentTimeMillis();
    }

    public void setLastMS(long lastMS) {
        TimeHelper.lastMS = lastMS;
    }
}

