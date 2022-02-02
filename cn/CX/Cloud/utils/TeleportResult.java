/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Vec3
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.utils.NodeProcessor;
import java.util.ArrayList;
import net.minecraft.util.Vec3;

public class TeleportResult {
    public ArrayList<Vec3> positions;
    public ArrayList<Vec3> positionsBack;
    public ArrayList<NodeProcessor.Node> triedPaths;
    public Vec3 lastPos;
    public ArrayList<NodeProcessor.Node> path;
    public boolean foundPath;

    public TeleportResult(ArrayList<Vec3> positions, ArrayList<Vec3> positionsBack, ArrayList<NodeProcessor.Node> triedPaths, ArrayList<NodeProcessor.Node> path, Vec3 lastPos, boolean foundPath) {
        this.positions = positions;
        this.positionsBack = positionsBack;
        this.triedPaths = triedPaths;
        this.path = path;
        this.foundPath = foundPath;
        this.lastPos = lastPos;
    }
}

