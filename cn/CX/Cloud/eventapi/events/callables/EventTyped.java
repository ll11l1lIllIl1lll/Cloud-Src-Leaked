/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.eventapi.events.callables;

import cn.CX.Cloud.eventapi.events.Event;
import cn.CX.Cloud.eventapi.events.Typed;

public abstract class EventTyped
implements Event,
Typed {
    private final byte type;

    protected EventTyped(byte eventType) {
        this.type = eventType;
    }

    @Override
    public byte getType() {
        return this.type;
    }
}

