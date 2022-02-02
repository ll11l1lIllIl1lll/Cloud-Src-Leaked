/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBarrier
 *  net.minecraft.block.BlockGlass
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.BlockStainedGlass
 *  net.minecraft.block.BlockStairs
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.inventory.Container
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.util.BlockPos
 */
package cn.CX.Cloud.utils.font;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Utils {
    public static boolean fuck = true;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static int add(int number, int add, int max) {
        return number + add > max ? max : number + add;
    }

    public static int remove(int number, int remove, int min) {
        return number - remove < min ? min : number - remove;
    }

    public static int check(int number) {
        return number <= 0 ? 1 : (number > 255 ? 255 : number);
    }

    public static void breakAnticheats() {
        Utils.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Utils.mc.thePlayer.posX + Utils.mc.thePlayer.motionX, Utils.mc.thePlayer.posY - 110.0, Utils.mc.thePlayer.posZ + Utils.mc.thePlayer.motionZ, true));
    }

    public static boolean isContainerEmpty(Container container) {
        int slotAmount;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            return false;
        }
        return true;
    }

    public static double getDist() {
        double distance = 0.0;
        for (double i = Utils.mc.thePlayer.posY; i > 0.0 && !(i < 0.0); i -= 0.1) {
            Block block = Utils.mc.theWorld.getBlockState(new BlockPos(Utils.mc.thePlayer.posX, i, Utils.mc.thePlayer.posZ)).getBlock();
            if (block.getMaterial() == Material.air || !block.isCollidable() || !block.isFullBlock() && !(block instanceof BlockSlab) && !(block instanceof BlockBarrier) && !(block instanceof BlockStairs) && !(block instanceof BlockGlass) && !(block instanceof BlockStainedGlass)) continue;
            if (block instanceof BlockSlab) {
                i -= 0.5;
            }
            distance = i;
            break;
        }
        return Utils.mc.thePlayer.posY - distance;
    }

    public static boolean canBlock() {
        if (mc == null) {
            mc = Minecraft.getMinecraft();
        }
        if (Utils.mc.thePlayer.getHeldItem() == null) {
            return false;
        }
        if (Utils.mc.thePlayer.isBlocking() || Utils.mc.thePlayer.isUsingItem() && Utils.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            return true;
        }
        return Utils.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isPressed();
    }

    public static Minecraft getMinecraft() {
        return mc;
    }

    public static String getMD5(String input) {
        StringBuilder res = new StringBuilder();
        try {
            byte[] md5;
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(input.getBytes());
            for (byte aMd5 : md5 = algorithm.digest()) {
                String tmp = Integer.toHexString(0xFF & aMd5);
                if (tmp.length() == 1) {
                    res.append("0").append(tmp);
                    continue;
                }
                res.append(tmp);
            }
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        return res.toString();
    }
}

