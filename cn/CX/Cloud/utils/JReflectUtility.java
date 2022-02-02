/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.Timer
 *  net.minecraft.util.Vec3
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.ClientLoader;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

public class JReflectUtility {
    private static Map<String, Field> cachedFields = new HashMap<String, Field>();
    private static Map<String, Method> cachedMethods = new HashMap<String, Method>();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static Field getField(Class<?> inClass, String name, boolean secureAccess) {
        if (cachedFields.containsKey(JReflectUtility.getUniquePath(inClass, name))) {
            return cachedFields.get(JReflectUtility.getUniquePath(inClass, name));
        }
        for (Field field : inClass.getDeclaredFields()) {
            if (!field.getName().equals(name)) continue;
            if (secureAccess && !field.isAccessible()) {
                field.setAccessible(true);
            }
            cachedFields.put(JReflectUtility.getUniquePath(inClass, name), field);
            return field;
        }
        return null;
    }

    public static Class<?>[] getInterfaces(Object clazz) {
        Class<?> c = clazz.getClass();
        Class<?>[] interfaces = c.getInterfaces();
        return interfaces;
    }

    public static Method getMethod(Class<?> inClass, String name, boolean secureAccess) {
        if (cachedMethods.containsKey(JReflectUtility.getUniquePath(inClass, name))) {
            return cachedMethods.get(JReflectUtility.getUniquePath(inClass, name));
        }
        for (Method method : inClass.getDeclaredMethods()) {
            if (!method.getName().equals(name)) continue;
            if (secureAccess && !method.isAccessible()) {
                method.setAccessible(true);
            }
            cachedMethods.put(JReflectUtility.getUniquePath(inClass, name), method);
            return method;
        }
        return null;
    }

    public static void setField(Class<?> inClass, Object instance, String name, Object to) {
        try {
            Objects.requireNonNull(JReflectUtility.getField(inClass, name, true)).set(instance, to);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Timer timer() {
        try {
            Class<Minecraft> timer = Minecraft.class;
            Field f1 = timer.getDeclaredField(new String(new char[]{'t', 'i', 'm', 'e', 'r'}));
            f1.setAccessible(true);
            return (Timer)f1.get(mc);
        }
        catch (Exception var5) {
            try {
                Class<Minecraft> time2 = Minecraft.class;
                Field f2 = time2.getDeclaredField(new String(new char[]{'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '8', '_', 'T'}));
                f2.setAccessible(true);
                return (Timer)f2.get(mc);
            }
            catch (Exception var4) {
                return null;
            }
        }
    }

    public static Class getEntityNumber() {
        try {
            return Class.forName("nianshow.nshowmod.entity.EntityNumber");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static Class getNPCEntity() {
        try {
            return Class.forName("noppes.npcs.entity.EntityNPCInterface");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static Class getDeciEntity() {
        try {
            return Class.forName("deci.ag.d");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static void setBlockHitDelay(int blockHitDelay) {
        Field field = null;
        try {
            field = PlayerControllerMP.class.getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_78781_i" : "blockHitDelay");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.setInt(JReflectUtility.mc.playerController, blockHitDelay);
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
    }

    public static Class getCorpse() {
        try {
            return Class.forName("deci.af.a");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static void setwasSprinting(boolean wasSprinting) {
        AccessibleObject field = null;
        try {
            field = JReflectUtility.mc.thePlayer.getClass().getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_71171_cn" : "wasSprinting");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
        field.setAccessible(true);
        try {
            ((Field)field).setBoolean(JReflectUtility.mc.thePlayer, wasSprinting);
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
    }

    public static Class getGunItem() {
        try {
            return Class.forName("deci.ao.b");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    private static String getUniquePath(Class klass, String name) {
        return klass.getName().replace(".", "/") + "-" + name;
    }

    public static boolean getHittingBlock() {
        Field field = null;
        try {
            field = Minecraft.getMinecraft().playerController.getClass().getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_78778_j" : "isHittingBlock");
            field.setAccessible(true);
            return field.getBoolean(Minecraft.getMinecraft().playerController.getClass());
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static Object getFieldAsObject(Class<?> inClass, Object instance, String name) {
        try {
            return Objects.requireNonNull(JReflectUtility.getField(inClass, name, true)).get(instance);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object getFieldAsObject(Class<?> inClass, Object instance, String name, boolean secureAccess) {
        try {
            return Objects.requireNonNull(JReflectUtility.getField(inClass, name, secureAccess)).get(instance);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Vec3 newInstanceVec3(double x, double y, double z) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("net.minecraft.util.Vec3");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        Constructor<?> vec3 = null;
        try {
            vec3 = clazz.getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        vec3.setAccessible(true);
        try {
            return (Vec3)vec3.newInstance(x, y, z);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getProning(EntityPlayer entityPlayer) {
        try {
            Class<?> clazz = Class.forName("com.vicmatskiv.weaponlib.ClientEventHandler");
            Method m = clazz.getDeclaredMethod("isProning", new Class[0]);
            return (Boolean)m.invoke(clazz, entityPlayer);
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static void cameraTransform(float renderPartialTicks, int pass) {
        try {
            JReflectUtility.mc.entityRenderer.getClass().getDeclaredMethod(ClientLoader.runtimeDeobfuscationEnabled ? "func_78479_a" : "setupCameraTransform", Float.TYPE).setAccessible(true);
            JReflectUtility.mc.entityRenderer.getClass().getDeclaredMethod(ClientLoader.runtimeDeobfuscationEnabled ? "func_78479_a" : "setupCameraTransform", Float.TYPE, Integer.TYPE).invoke(JReflectUtility.mc.entityRenderer, Float.valueOf(renderPartialTicks), pass);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isSRG() {
        try {
            return JReflectUtility.getField(Minecraft.class, "theMinecraft", true) == null;
        }
        catch (Exception ex) {
            return true;
        }
    }

    public static Object getMethodAsObject(Class<?> inClass, Object instance, String name, boolean secureAccess, Object ... parameter) {
        try {
            return Objects.requireNonNull(JReflectUtility.getMethod(inClass, name, secureAccess)).invoke(instance, parameter);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getMethodAsObject(Class<?> inClass, String name, boolean secureAccess, Object ... parameter) {
        try {
            return Objects.requireNonNull(JReflectUtility.getMethod(inClass, name, secureAccess)).invoke(inClass.newInstance(), parameter);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getMethodAsObject(Class<?> inClass, Object instance, String name, Object ... parameter) {
        try {
            return Objects.requireNonNull(JReflectUtility.getMethod(inClass, name, true)).invoke(instance, parameter);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AxisAlignedBB newInstanceAxisAlignedBB(double x, double y, double z) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("net.minecraft.util.AxisAlignedBB");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        Constructor<?> aabb = null;
        try {
            aabb = clazz.getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        aabb.setAccessible(true);
        try {
            return (AxisAlignedBB)aabb.newInstance(x, y, z);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cleanCachedMethodsAndFields() {
        cachedMethods.clear();
        cachedFields.clear();
    }

    public static void setRightClickDelayTimer(int i) {
        try {
            Field rightClickDelayTimer = mc.getClass().getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_71467_ac" : "rightClickDelayTimer");
            rightClickDelayTimer.setAccessible(true);
            rightClickDelayTimer.setInt(mc, 0);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCurBlockDamageMP(int i) {
        AccessibleObject field = null;
        try {
            field = PlayerControllerMP.class.getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_78770_f" : "curBlockDamageMP");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
        field.setAccessible(true);
        try {
            ((Field)field).setInt(JReflectUtility.mc.playerController, i);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static float getCurBlockDamageMP() {
        AccessibleObject field = null;
        try {
            field = PlayerControllerMP.class.getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_78770_f" : "curBlockDamageMP");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
        field.setAccessible(true);
        try {
            return ((Field)field).getFloat(JReflectUtility.mc.playerController);
        }
        catch (IllegalAccessException illegalAccessException) {
            return 0.0f;
        }
    }

    public static float getRenderPartialTicks() {
        Field fTimer = null;
        try {
            fTimer = mc.getClass().getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_71428_T" : "timer");
            fTimer.setAccessible(true);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
        Field frenderPartialTicks = null;
        try {
            frenderPartialTicks = Timer.class.getDeclaredField(ClientLoader.runtimeDeobfuscationEnabled ? "field_74281_c" : "renderPartialTicks");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
        float pTicks = 0.0f;
        try {
            frenderPartialTicks.setAccessible(true);
            pTicks = ((Float)frenderPartialTicks.get(fTimer.get(mc))).floatValue();
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        return pTicks;
    }

    public static Class getCPacketInjectDetect() {
        try {
            return Class.forName("luohuayu.anticheat.message.CPacketInjectDetect");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static AxisAlignedBB newAxisAlignedBBInstance(double x1, double y1, double z1, double x2, double y2, double z2) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("net.minecraft.util.AxisAlignedBB");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        Constructor<?> constructor = null;
        try {
            assert (clazz != null);
            constructor = clazz.getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        try {
            return (AxisAlignedBB)constructor.newInstance(x1, y1, z1, x2, y2, z2);
        }
        catch (InstantiationException instantiationException) {
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InvocationTargetException invocationTargetException) {
            // empty catch block
        }
        return null;
    }

    public static boolean getFieldAsBoolean(Class<?> inClass, Object instance, String name, boolean secureAccess) {
        try {
            return Objects.requireNonNull(JReflectUtility.getField(inClass, name, secureAccess)).getBoolean(instance);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getFieldAsBoolean(Class<?> inClass, Object instance, String name) {
        try {
            return Objects.requireNonNull(JReflectUtility.getField(inClass, name, true)).getBoolean(instance);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void orientCamera(float renderPartialTicks) {
        try {
            JReflectUtility.mc.entityRenderer.getClass().getDeclaredMethod(ClientLoader.runtimeDeobfuscationEnabled ? "func_78467_g" : "orientCamera", Float.TYPE).setAccessible(true);
            JReflectUtility.mc.entityRenderer.getClass().getDeclaredMethod(ClientLoader.runtimeDeobfuscationEnabled ? "func_78467_g" : "orientCamera", Float.TYPE).invoke(JReflectUtility.mc.entityRenderer, Float.valueOf(renderPartialTicks));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

