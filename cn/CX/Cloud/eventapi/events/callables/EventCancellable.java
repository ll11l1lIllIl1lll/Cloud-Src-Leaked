/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.eventapi.events.callables;

import cn.CX.Cloud.eventapi.events.Cancellable;
import cn.CX.Cloud.eventapi.events.Event;

public abstract class EventCancellable
implements Event,
Cancellable {
    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

