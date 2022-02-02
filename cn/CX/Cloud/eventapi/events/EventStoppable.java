/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.eventapi.events;

import cn.CX.Cloud.eventapi.events.Event;

public abstract class EventStoppable
implements Event {
    private boolean stopped;

    protected EventStoppable() {
    }

    public void stop() {
        this.stopped = true;
    }

    public boolean isStopped() {
        return this.stopped;
    }
}

