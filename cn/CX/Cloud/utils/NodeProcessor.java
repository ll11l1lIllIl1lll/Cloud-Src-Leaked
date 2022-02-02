/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLadder
 *  net.minecraft.block.BlockLilyPad
 *  net.minecraft.block.BlockSign
 *  net.minecraft.block.BlockWallSign
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 */
package cn.CX.Cloud.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class NodeProcessor {
    public ArrayList<Node> path = new ArrayList();
    public ArrayList<Node> triedPaths = new ArrayList();
    ArrayList<Node> openNodes = new ArrayList();
    HashMap<Integer, Node> hashOpenNodes = new HashMap();
    HashMap<Integer, Node> hashClosedNodes = new HashMap();

    public void getPath(BlockPos start, BlockPos finish) {
        Node startNode = this.createNode(start);
        Node endNode = this.createNode(finish);
        ArrayList<Node> openNodes = new ArrayList<Node>();
        ArrayList<Node> closedNodes = new ArrayList<Node>();
        openNodes.add(startNode);
        int count = 0;
        while (openNodes.size() > 0) {
            Node currentNode = (Node)openNodes.get(0);
            if (count > 2000) {
                return;
            }
            double minFCost = 1.0E8;
            for (int i = 1; i < openNodes.size(); ++i) {
                double FCost = ((Node)openNodes.get(i)).getF_Cost(currentNode, endNode);
                if (!(FCost < minFCost)) continue;
                minFCost = FCost;
                currentNode = (Node)openNodes.get(i);
            }
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);
            this.triedPaths.add(currentNode);
            if (currentNode.getBlockpos().equals((Object)endNode.getBlockpos())) {
                endNode.parent = currentNode;
                this.retracePath(startNode, endNode);
                return;
            }
            for (Node neighbor : this.getNeighbors(currentNode)) {
                double hCost;
                if (!neighbor.isWalkable() || this.isNodeClosed(neighbor, closedNodes) || !((hCost = currentNode.getH_Cost(endNode)) >= neighbor.getH_Cost(endNode)) && this.isNodeClosed(neighbor, openNodes)) continue;
                neighbor.parent = currentNode;
                if (this.isNodeClosed(neighbor, openNodes)) continue;
                openNodes.add(neighbor);
            }
            ++count;
        }
    }

    public Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public Node createNode(BlockPos pos) {
        return new Node(this.getBlock(pos).getMaterial() == Material.air && this.getBlock(pos.up()).getMaterial() == Material.air, pos);
    }

    private void retracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = endNode;
        while (!currentNode.equals(startNode)) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        this.path = path;
    }

    private boolean isNodeClosed(Node node, ArrayList<Node> nodes) {
        for (Node n : nodes) {
            if (!n.getBlockpos().equals((Object)node.getBlockpos())) continue;
            return true;
        }
        return false;
    }

    private ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        BlockPos b1 = node.getBlockpos();
        BlockPos b2 = node.getBlockpos();
        b1 = b1.add(1, 1, 1);
        b2 = b2.add(-1, -1, -1);
        for (BlockPos pos : BlockPos.getAllInBox((BlockPos)b1, (BlockPos)b2)) {
            if (pos.equals((Object)node.getBlockpos()) || pos.getX() > node.getBlockpos().getX() && pos.getZ() > node.getBlockpos().getZ() || pos.getX() < node.getBlockpos().getX() && pos.getZ() < node.getBlockpos().getZ() || pos.getX() < node.getBlockpos().getX() && pos.getZ() > node.getBlockpos().getZ() || pos.getX() > node.getBlockpos().getX() && pos.getZ() < node.getBlockpos().getZ()) continue;
            neighbors.add(this.createNode(pos));
        }
        return neighbors;
    }

    public static boolean isPassable(Block block) {
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants && !(block instanceof BlockLilyPad) || block.getMaterial() == Material.vine || block.getMaterial() == Material.water || block.getMaterial() == Material.circuits || block instanceof BlockSign || block instanceof BlockWallSign || block instanceof BlockLadder;
    }

    public static class Node {
        private boolean walkable;
        private BlockPos blockPos;
        public Node parent;

        public Node(boolean walkable, BlockPos blockPos) {
            this.walkable = walkable;
            this.blockPos = blockPos;
        }

        public double getG_Cost(Node startNode) {
            return this.distance(this.blockPos, startNode.getBlockpos());
        }

        public double getF_Cost(Node startNode, Node endNode) {
            return this.getG_Cost(startNode) + this.getH_Cost(endNode);
        }

        public double getH_Cost(Node endNode) {
            return this.distance(this.blockPos, endNode.getBlockpos());
        }

        public boolean isWalkable() {
            return this.walkable;
        }

        public BlockPos getBlockpos() {
            return this.blockPos;
        }

        public double distance(BlockPos b1, BlockPos b2) {
            float f = b1.getX() - b2.getX();
            float f1 = b1.getY() - b2.getY();
            float f2 = b1.getZ() - b2.getZ();
            return MathHelper.sqrt_float((float)(f * f + f1 * f1 + f2 * f2));
        }
    }
}

