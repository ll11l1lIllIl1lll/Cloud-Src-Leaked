/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.network.play.client.C0BPacketEntityAction
 *  net.minecraft.network.play.client.C0BPacketEntityAction$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.utils.NodeProcessor;
import cn.CX.Cloud.utils.TeleportResult;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class Infinite {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static Random rand = new Random();
    public static boolean spectator;
    public static ArrayList<Entity> blackList;
    static double x;
    static double y;
    static double z;
    static double xPreEn;
    static double yPreEn;
    static double zPreEn;
    static double xPre;
    static double yPre;
    static double zPre;

    static {
        blackList = new ArrayList();
    }

    public static Block getBlock(BlockPos pos) {
        return Infinite.mc.theWorld.getBlockState(pos).getBlock();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean infiniteReach(double range, double maxXZTP, double maxYTP, ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions, EntityLivingBase en) {
        int i;
        int ind = 0;
        xPreEn = en.posX;
        yPreEn = en.posY;
        zPreEn = en.posZ;
        xPre = Infinite.mc.thePlayer.posX;
        yPre = Infinite.mc.thePlayer.posY;
        zPre = Infinite.mc.thePlayer.posZ;
        boolean attack = true;
        boolean up = false;
        boolean tpUpOneBlock = false;
        boolean hit = false;
        boolean tpStraight = false;
        boolean sneaking = Infinite.mc.thePlayer.isSneaking();
        positions.clear();
        positionsBack.clear();
        double step = maxXZTP / range;
        int steps = 0;
        int i2 = 0;
        while ((double)i2 < range && !(maxXZTP * (double)(++steps) > range)) {
            ++i2;
        }
        MovingObjectPosition rayTrace = null;
        MovingObjectPosition rayTrace1 = null;
        Object rayTraceCarpet = null;
        if (Infinite.rayTraceWide(new Vec3(Infinite.mc.thePlayer.posX, Infinite.mc.thePlayer.posY, Infinite.mc.thePlayer.posZ), new Vec3(en.posX, en.posY, en.posZ), false, false, true) || (rayTrace1 = Infinite.rayTracePos(new Vec3(Infinite.mc.thePlayer.posX, Infinite.mc.thePlayer.posY + (double)Infinite.mc.thePlayer.getEyeHeight(), Infinite.mc.thePlayer.posZ), new Vec3(en.posX, en.posY + (double)Infinite.mc.thePlayer.getEyeHeight(), en.posZ), false, false, true)) != null) {
            rayTrace = Infinite.rayTracePos(new Vec3(Infinite.mc.thePlayer.posX, Infinite.mc.thePlayer.posY, Infinite.mc.thePlayer.posZ), new Vec3(en.posX, Infinite.mc.thePlayer.posY, en.posZ), false, false, true);
            if (rayTrace != null || (rayTrace1 = Infinite.rayTracePos(new Vec3(Infinite.mc.thePlayer.posX, Infinite.mc.thePlayer.posY + (double)Infinite.mc.thePlayer.getEyeHeight(), Infinite.mc.thePlayer.posZ), new Vec3(en.posX, Infinite.mc.thePlayer.posY + (double)Infinite.mc.thePlayer.getEyeHeight(), en.posZ), false, false, true)) != null) {
                MovingObjectPosition trace = null;
                if (rayTrace == null) {
                    trace = rayTrace1;
                }
                if (rayTrace1 == null) {
                    trace = rayTrace;
                }
                if (trace != null) {
                    if (trace.getBlockPos() == null) return false;
                    boolean fence = false;
                    BlockPos target = trace.getBlockPos();
                    up = true;
                    y = target.up().getY();
                    yPreEn = target.up().getY();
                    Block lastBlock = null;
                    Boolean found = false;
                    int i3 = 0;
                    while ((double)i3 < maxYTP) {
                        MovingObjectPosition tr = Infinite.rayTracePos(new Vec3(Infinite.mc.thePlayer.posX, (double)(target.getY() + i3), Infinite.mc.thePlayer.posZ), new Vec3(en.posX, (double)(target.getY() + i3), en.posZ), false, false, true);
                        if (tr != null && tr.getBlockPos() != null) {
                            BlockPos blockPos = tr.getBlockPos();
                            Block block = Infinite.mc.theWorld.getBlockState(blockPos).getBlock();
                            if (block.getMaterial() != Material.air) {
                                lastBlock = block;
                            } else {
                                fence = lastBlock instanceof BlockFence;
                                y = target.getY() + i3;
                                yPreEn = target.getY() + i3;
                                if (fence) {
                                    y += 1.0;
                                    yPreEn += 1.0;
                                    if ((double)(i3 + 1) > maxYTP) {
                                        found = false;
                                        break;
                                    }
                                }
                                found = true;
                                break;
                            }
                        }
                        ++i3;
                    }
                    double difX = Infinite.mc.thePlayer.posX - xPreEn;
                    double difZ = Infinite.mc.thePlayer.posZ - zPreEn;
                    double divider = step * 0.0;
                    if (!found.booleanValue()) {
                        return false;
                    }
                }
            } else {
                MovingObjectPosition ent = Infinite.rayTracePos(new Vec3(Infinite.mc.thePlayer.posX, Infinite.mc.thePlayer.posY, Infinite.mc.thePlayer.posZ), new Vec3(en.posX, en.posY, en.posZ), false, false, false);
                if (ent != null && ent.entityHit == null) {
                    y = Infinite.mc.thePlayer.posY;
                    yPreEn = Infinite.mc.thePlayer.posY;
                } else {
                    y = Infinite.mc.thePlayer.posY;
                    yPreEn = en.posY;
                }
            }
        }
        if (!attack) {
            return false;
        }
        if (sneaking) {
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (i = 0; i < steps; ++i) {
            double divider;
            double difZ;
            double difY;
            double difX;
            ++ind;
            if (i == 1 && up) {
                x = Infinite.mc.thePlayer.posX;
                y = yPreEn;
                z = Infinite.mc.thePlayer.posZ;
                Infinite.sendPacket(false, positionsBack, positions);
            }
            if (i != steps - 1) {
                difX = Infinite.mc.thePlayer.posX - xPreEn;
                difY = Infinite.mc.thePlayer.posY - yPreEn;
                difZ = Infinite.mc.thePlayer.posZ - zPreEn;
                divider = step * (double)i;
                x = Infinite.mc.thePlayer.posX - difX * divider;
                y = Infinite.mc.thePlayer.posY - difY * (up ? 1.0 : divider);
                z = Infinite.mc.thePlayer.posZ - difZ * divider;
                Infinite.sendPacket(false, positionsBack, positions);
                continue;
            }
            difX = Infinite.mc.thePlayer.posX - xPreEn;
            difY = Infinite.mc.thePlayer.posY - yPreEn;
            difZ = Infinite.mc.thePlayer.posZ - zPreEn;
            divider = step * (double)i;
            x = Infinite.mc.thePlayer.posX - difX * divider;
            y = Infinite.mc.thePlayer.posY - difY * (up ? 1.0 : divider);
            z = Infinite.mc.thePlayer.posZ - difZ * divider;
            Infinite.sendPacket(false, positionsBack, positions);
            double xDist = x - xPreEn;
            double zDist = z - zPreEn;
            double yDist = y - en.posY;
            double dist = Math.sqrt(xDist * xDist + zDist * zDist);
            if (dist > 4.0) {
                x = xPreEn;
                y = yPreEn;
                z = zPreEn;
                Infinite.sendPacket(false, positionsBack, positions);
            } else if (dist > 0.05 && up) {
                x = xPreEn;
                y = yPreEn;
                z = zPreEn;
                Infinite.sendPacket(false, positionsBack, positions);
            }
            if (Math.abs(yDist) < maxYTP && Infinite.mc.thePlayer.getDistanceToEntity((Entity)en) >= 4.0f) {
                x = xPreEn;
                y = en.posY;
                z = zPreEn;
                Infinite.sendPacket(false, positionsBack, positions);
                Infinite.doattack(en);
                continue;
            }
            attack = false;
        }
        for (i = positions.size() - 2; i > -1; --i) {
            x = positions.get((int)i).xCoord;
            y = positions.get((int)i).yCoord;
            z = positions.get((int)i).zCoord;
            Infinite.sendPacket(false, positionsBack, positions);
        }
        x = Infinite.mc.thePlayer.posX;
        y = Infinite.mc.thePlayer.posY;
        z = Infinite.mc.thePlayer.posZ;
        Infinite.sendPacket(false, positionsBack, positions);
        if (!attack) {
            if (sneaking) {
                Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            positions.clear();
            positionsBack.clear();
            return false;
        }
        if (!sneaking) return true;
        Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        return true;
    }

    public static MovingObjectPosition rayTracePos(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        float[] rots = Infinite.getFacePosRemote(vec32, vec31);
        float yaw = rots[0];
        double angleA = Math.toRadians(Infinite.normalizeAngle(yaw));
        double angleB = Math.toRadians(Infinite.normalizeAngle(yaw) + 180.0f);
        double size = 2.1;
        double size2 = 2.1;
        Vec3 left = new Vec3(vec31.xCoord + Math.cos(angleA) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size);
        Vec3 right = new Vec3(vec31.xCoord + Math.cos(angleB) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size);
        Vec3 left2 = new Vec3(vec32.xCoord + Math.cos(angleA) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size);
        Vec3 right2 = new Vec3(vec32.xCoord + Math.cos(angleB) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size);
        Vec3 leftA = new Vec3(vec31.xCoord + Math.cos(angleA) * size2, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size2);
        Vec3 rightA = new Vec3(vec31.xCoord + Math.cos(angleB) * size2, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size2);
        Vec3 left2A = new Vec3(vec32.xCoord + Math.cos(angleA) * size2, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size2);
        Vec3 right2A = new Vec3(vec32.xCoord + Math.cos(angleB) * size2, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size2);
        MovingObjectPosition trace1 = Infinite.mc.theWorld.rayTraceBlocks(left, left2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace2 = Infinite.mc.theWorld.rayTraceBlocks(vec31, vec32, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace3 = Infinite.mc.theWorld.rayTraceBlocks(right, right2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace4 = null;
        MovingObjectPosition trace5 = null;
        if (trace2 != null || trace1 != null || trace3 != null || trace4 != null || trace5 != null) {
            if (returnLastUncollidableBlock) {
                if (trace5 != null && (Infinite.getBlock(trace5.getBlockPos()).getMaterial() != Material.air || trace5.entityHit != null)) {
                    return trace5;
                }
                if (trace4 != null && (Infinite.getBlock(trace4.getBlockPos()).getMaterial() != Material.air || trace4.entityHit != null)) {
                    return trace4;
                }
                if (trace3 != null && (Infinite.getBlock(trace3.getBlockPos()).getMaterial() != Material.air || trace3.entityHit != null)) {
                    return trace3;
                }
                if (trace1 != null && (Infinite.getBlock(trace1.getBlockPos()).getMaterial() != Material.air || trace1.entityHit != null)) {
                    return trace1;
                }
                if (trace2 != null && (Infinite.getBlock(trace2.getBlockPos()).getMaterial() != Material.air || trace2.entityHit != null)) {
                    return trace2;
                }
            } else {
                if (trace5 != null) {
                    return trace5;
                }
                if (trace4 != null) {
                    return trace4;
                }
                if (trace3 != null) {
                    return trace3;
                }
                if (trace1 != null) {
                    return trace1;
                }
                if (trace2 != null) {
                    return trace2;
                }
            }
        }
        if (trace2 == null) {
            if (trace3 == null) {
                if (trace1 == null) {
                    if (trace5 == null) {
                        if (trace4 == null) {
                            return null;
                        }
                        return trace4;
                    }
                    return trace5;
                }
                return trace1;
            }
            return trace3;
        }
        return trace2;
    }

    public static void doattack(EntityLivingBase en) {
        mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C02PacketUseEntity((Entity)en, C02PacketUseEntity.Action.ATTACK));
        Infinite.mc.thePlayer.swingItem();
    }

    public static boolean rayTraceWide(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        float yaw = Infinite.getFacePosRemote(vec32, vec31)[0];
        yaw = Infinite.normalizeAngle(yaw);
        yaw += 180.0f;
        yaw = MathHelper.wrapAngleTo180_float((float)yaw);
        double angleA = Math.toRadians(yaw);
        double angleB = Math.toRadians(yaw + 180.0f);
        double size = 2.1;
        double size2 = 2.1;
        Vec3 left = new Vec3(vec31.xCoord + Math.cos(angleA) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size);
        Vec3 right = new Vec3(vec31.xCoord + Math.cos(angleB) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size);
        Vec3 left2 = new Vec3(vec32.xCoord + Math.cos(angleA) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size);
        Vec3 right2 = new Vec3(vec32.xCoord + Math.cos(angleB) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size);
        Vec3 leftA = new Vec3(vec31.xCoord + Math.cos(angleA) * size2, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size2);
        Vec3 rightA = new Vec3(vec31.xCoord + Math.cos(angleB) * size2, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size2);
        Vec3 left2A = new Vec3(vec32.xCoord + Math.cos(angleA) * size2, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size2);
        Vec3 right2A = new Vec3(vec32.xCoord + Math.cos(angleB) * size2, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size2);
        MovingObjectPosition trace1 = Infinite.mc.theWorld.rayTraceBlocks(left, left2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace2 = Infinite.mc.theWorld.rayTraceBlocks(vec31, vec32, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace3 = Infinite.mc.theWorld.rayTraceBlocks(right, right2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        Object trace4 = null;
        Object trace5 = null;
        if (returnLastUncollidableBlock) {
            return trace1 != null && Infinite.getBlock(trace1.getBlockPos()).getMaterial() != Material.air || trace2 != null && Infinite.getBlock(trace2.getBlockPos()).getMaterial() != Material.air || trace3 != null && Infinite.getBlock(trace3.getBlockPos()).getMaterial() != Material.air || trace4 != null && Infinite.getBlock(trace4.getBlockPos()).getMaterial() != Material.air || trace5 != null && Infinite.getBlock(trace5.getBlockPos()).getMaterial() != Material.air;
        }
        return trace1 != null || trace2 != null || trace3 != null || trace5 != null || trace4 != null;
    }

    public static float[] getFacePosRemote(Vec3 src, Vec3 dest) {
        double diffX = dest.xCoord - src.xCoord;
        double diffY = dest.yCoord - src.yCoord;
        double diffZ = dest.zCoord - src.zCoord;
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{MathHelper.wrapAngleTo180_float((float)yaw), MathHelper.wrapAngleTo180_float((float)pitch)};
    }

    public static BlockPos getBlockPos(Vec3 vec) {
        return new BlockPos(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public static Vec3 getVec3(BlockPos blockPos) {
        return new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
    }

    public static double normalizeAngle(double angle) {
        return (angle + 360.0) % 360.0;
    }

    public static float normalizeAngle(float angle) {
        return (angle + 360.0f) % 360.0f;
    }

    public static void sendPacket(boolean goingBack, ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions) {
        C03PacketPlayer.C04PacketPlayerPosition playerPacket = new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true);
        Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)playerPacket);
        if (goingBack) {
            positionsBack.add(new Vec3(x, y, z));
            return;
        }
        positions.add(new Vec3(x, y, z));
    }

    public static TeleportResult pathFinderTeleportTo(Vec3 from, Vec3 to) {
        boolean sneaking = Infinite.mc.thePlayer.isSneaking();
        ArrayList<Vec3> positions = new ArrayList<Vec3>();
        ArrayList<NodeProcessor.Node> triedPaths = new ArrayList();
        BlockPos targetBlockPos = new BlockPos((Vec3i)Infinite.getBlockPos(to));
        BlockPos fromBlockPos = Infinite.getBlockPos(from);
        if (!NodeProcessor.isPassable(Infinite.getBlock(fromBlockPos))) {
            float angle = (float)Math.toDegrees(Math.atan2(to.zCoord - from.zCoord, to.xCoord - from.xCoord));
            if ((angle += 180.0f) < 0.0f) {
                angle += 360.0f;
            }
            System.out.println(angle);
            from = Infinite.getVec3(fromBlockPos.offset(EnumFacing.fromAngle((double)Infinite.normalizeAngle(angle))).add(0.5, 0.0, 0.5));
        }
        BlockPos finalBlockPos = targetBlockPos;
        boolean passable = true;
        if (!NodeProcessor.isPassable(Infinite.getBlock(targetBlockPos))) {
            finalBlockPos = targetBlockPos.up();
            boolean lastIsPassable = NodeProcessor.isPassable(Infinite.getBlock(targetBlockPos.up()));
            if (!lastIsPassable) {
                finalBlockPos = targetBlockPos.up(2);
                if (!lastIsPassable) {
                    passable = false;
                }
            }
        }
        if (!passable) {
            float angle = (float)Math.toDegrees(Math.atan2(to.zCoord - (double)finalBlockPos.getZ(), to.xCoord - (double)finalBlockPos.getX()));
            if ((angle += 180.0f) < 0.0f) {
                angle += 360.0f;
            }
            finalBlockPos = targetBlockPos.offset(EnumFacing.fromAngle((double)Infinite.normalizeAngle(angle)));
        }
        NodeProcessor processor = new NodeProcessor();
        processor.getPath(new BlockPos(from.xCoord, from.yCoord, from.zCoord), finalBlockPos);
        triedPaths = processor.triedPaths;
        if (processor.path == null) {
            return new TeleportResult(positions, null, triedPaths, null, null, false);
        }
        Vec3 lastPos = null;
        if (sneaking) {
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (NodeProcessor.Node node : processor.path) {
            BlockPos pos = node.getBlockpos();
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((double)node.getBlockpos().getX() + 0.5, (double)node.getBlockpos().getY(), (double)node.getBlockpos().getZ() + 0.5, true));
            lastPos = new Vec3((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5);
            positions.add(lastPos);
        }
        if (sneaking) {
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return new TeleportResult(positions, null, triedPaths, processor.path, lastPos, true);
    }

    public static TeleportResult pathFinderTeleportBack(ArrayList<Vec3> positions) {
        boolean sneaking = Infinite.mc.thePlayer.isSneaking();
        ArrayList<Vec3> positionsBack = new ArrayList<Vec3>();
        if (sneaking) {
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (int i = positions.size() - 1; i > -1; --i) {
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(positions.get((int)i).xCoord, positions.get((int)i).yCoord, positions.get((int)i).zCoord, true));
            positionsBack.add(positions.get(i));
        }
        if (sneaking) {
            Infinite.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Infinite.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return new TeleportResult(positions, positionsBack, null, null, null, false);
    }
}

