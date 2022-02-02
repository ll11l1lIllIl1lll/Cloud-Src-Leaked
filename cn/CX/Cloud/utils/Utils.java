/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.input.Mouse
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C05PacketPlayerLook
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Timer
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.settings.Setting;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class Utils {
    public static Minecraft mc = Minecraft.getMinecraft();
    private static final Random rand = new Random();
    private static Field timerField = null;
    private static Field mouseButton = null;
    private static Field mouseButtonState = null;
    private static Field mouseButtons = null;
    private static final Random RANDOM = new Random();

    public static void copy(String content) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
    }

    public static boolean nullCheck() {
        return Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null;
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static boolean fov(Entity entity, float fov) {
        fov = (float)((double)fov * 0.5);
        double v = ((double)(Utils.mc.thePlayer.rotationYaw - Utils.fovToEntity(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
        return v > 0.0 && v < (double)fov || (double)(-fov) < v && v < 0.0;
    }

    public static boolean isPlayerHoldingWeapon() {
        if (Utils.mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        }
        Item item = Utils.mc.thePlayer.getCurrentEquippedItem().getItem();
        return item instanceof ItemSword || item instanceof ItemAxe;
    }

    public static boolean currentScreenMinecraft() {
        return Utils.mc.currentScreen == null;
    }

    public static void setMouseButtonState(int mouseButton, boolean held) {
        if (Utils.mouseButton != null && mouseButtonState != null && mouseButtons != null) {
            MouseEvent m = new MouseEvent();
            try {
                Utils.mouseButton.setAccessible(true);
                Utils.mouseButton.set(m, mouseButton);
                mouseButtonState.setAccessible(true);
                mouseButtonState.set(m, held);
                MinecraftForge.EVENT_BUS.post((Event)m);
                mouseButtons.setAccessible(true);
                ByteBuffer bf = (ByteBuffer)mouseButtons.get(null);
                mouseButtons.setAccessible(false);
                bf.put(mouseButton, (byte)(held ? 1 : 0));
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
    }

    public static Random rand() {
        return rand;
    }

    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        if (entity.width == width && entity.height == height) {
            return;
        }
        entity.width = width;
        entity.height = height;
        double d0 = (double)width / 2.0;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0, entity.posY + (double)entity.height, entity.posZ + d0));
    }

    public static boolean playerOverAir() {
        double x = Utils.mc.thePlayer.posX;
        double y = Utils.mc.thePlayer.posY - 1.0;
        double z = Utils.mc.thePlayer.posZ;
        BlockPos p = new BlockPos(MathHelper.floor_double((double)x), MathHelper.floor_double((double)y), MathHelper.floor_double((double)z));
        return Utils.mc.theWorld.isAirBlock(p);
    }

    public static float[] gr(Entity q) {
        double diffY;
        if (q == null) {
            return null;
        }
        double diffX = q.posX - Utils.mc.thePlayer.posX;
        if (q instanceof EntityLivingBase) {
            EntityLivingBase en = (EntityLivingBase)q;
            diffY = en.posY + (double)en.getEyeHeight() * 0.9 - (Utils.mc.thePlayer.posY + (double)Utils.mc.thePlayer.getEyeHeight());
        } else {
            diffY = (q.getEntityBoundingBox().minY + q.getEntityBoundingBox().maxY) / 2.0 - (Utils.mc.thePlayer.posY + (double)Utils.mc.thePlayer.getEyeHeight());
        }
        double diffZ = q.posZ - Utils.mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{Utils.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(yaw - Utils.mc.thePlayer.rotationYaw)), Utils.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(pitch - Utils.mc.thePlayer.rotationPitch))};
    }

    public static float fovToEntity(Entity ent) {
        double x = ent.posX - Utils.mc.thePlayer.posX;
        double z = ent.posZ - Utils.mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.2957795;
        return (float)(yaw * -1.0);
    }

    public static List<Entity> getEntityList() {
        return Utils.mc.theWorld.getLoadedEntityList();
    }

    public static Timer getTimer() {
        try {
            return (Timer)timerField.get(mc);
        }
        catch (IllegalAccessException | IndexOutOfBoundsException var1) {
            return null;
        }
    }

    public static int getCurrentPlayerSlot() {
        return Utils.mc.thePlayer.inventory.currentItem;
    }

    public static int getBlockAmountInCurrentStack(int currentItem) {
        if (Utils.mc.thePlayer.inventory.getStackInSlot(currentItem) == null) {
            return 0;
        }
        ItemStack itemStack = Utils.mc.thePlayer.inventory.getStackInSlot(currentItem);
        if (itemStack.getItem() instanceof ItemBlock) {
            return itemStack.stackSize;
        }
        return 0;
    }

    public static float getDirection() {
        float var1 = Utils.mc.thePlayer.rotationYaw;
        if (Utils.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Utils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Utils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Utils.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Utils.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    public static boolean isHyp() {
        if (!Utils.isPlayerInGame()) {
            return false;
        }
        try {
            return !mc.isSingleplayer() && Utils.mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
        }
        catch (Exception welpBruh) {
            welpBruh.printStackTrace();
            return false;
        }
    }

    public static void hotkeyToSlot(int slot) {
        if (!Utils.isPlayerInGame()) {
            return;
        }
        Utils.mc.thePlayer.inventory.currentItem = slot;
    }

    public static boolean isPlayerInGame() {
        return Utils.mc.thePlayer != null && Utils.mc.theWorld != null;
    }

    public static void su() {
        try {
            timerField = Minecraft.class.getDeclaredField("field_71428_T");
        }
        catch (Exception var4) {
            try {
                timerField = Minecraft.class.getDeclaredField("timer");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (timerField != null) {
            timerField.setAccessible(true);
        }
        try {
            mouseButton = MouseEvent.class.getDeclaredField("button");
            mouseButtonState = MouseEvent.class.getDeclaredField("buttonstate");
            mouseButtons = Mouse.class.getDeclaredField("buttons");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void aim(Entity en, float ps, boolean pc) {
        float[] t;
        if (en != null && (t = Utils.gr(en)) != null) {
            float y = t[0];
            float p = t[1] + 4.0f + ps;
            if (pc) {
                mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(y, p, Utils.mc.thePlayer.onGround));
            } else {
                Utils.mc.thePlayer.rotationYaw = y;
                Utils.mc.thePlayer.rotationPitch = p;
            }
        }
    }

    public static double fovFromEntity(Entity en) {
        return ((double)(Utils.mc.thePlayer.rotationYaw - Utils.fovToEntity(en)) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    public static void correctSliders(Setting c, Setting d) {
        if (c.getValDouble() > d.getValDouble()) {
            double p = c.getValDouble();
            c.setValDouble(d.getValDouble());
            d.setValDouble(p);
        }
    }

    public static double ranModuleVal(Setting a, Setting b, Random r) {
        return a.getValDouble() == b.getValDouble() ? a.getValDouble() : a.getValDouble() + r.nextDouble() * (b.getValDouble() - a.getValDouble());
    }

    public static enum ClickEvents {
        RENDER,
        TICK;

    }
}

