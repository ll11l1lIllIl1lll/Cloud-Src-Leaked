/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.utils.BufferBuilder;
import cn.CX.Cloud.utils.WorldVertexBufferUploader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class ETessellator {
    private final BufferBuilder buffer;
    private final WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
    private static final ETessellator INSTANCE = new ETessellator(0x200000);

    public ETessellator(int bufferSize) {
        this.buffer = new BufferBuilder(bufferSize);
    }

    public static ETessellator getInstance() {
        return INSTANCE;
    }

    public BufferBuilder getBuffer() {
        return this.buffer;
    }

    public void draw() {
        this.buffer.finishDrawing();
        this.vboUploader.draw(this.buffer);
    }
}

