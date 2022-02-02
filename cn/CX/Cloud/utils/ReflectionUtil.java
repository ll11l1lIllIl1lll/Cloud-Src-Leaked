/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.client.shader.ShaderGroup
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.Cloud;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ReflectionUtil {
    public static final Field delayTimer = ReflectionHelper.findField(Minecraft.class, (String[])new String[]{"field_71467_ac", "rightClickDelayTimer"});
    public static final Field running = ReflectionHelper.findField(Minecraft.class, (String[])new String[]{"field_71425_J", "running"});
    public static final Field pressed = ReflectionHelper.findField(KeyBinding.class, (String[])new String[]{"field_74513_e", "pressed"});
    public static final Field theShaderGroup = ReflectionHelper.findField(EntityRenderer.class, (String[])new String[]{"field_147707_d", "theShaderGroup"});
    public static final Field listShaders = ReflectionHelper.findField(ShaderGroup.class, (String[])new String[]{"field_148031_d", "listShaders"});

    static {
        delayTimer.setAccessible(true);
        running.setAccessible(true);
        pressed.setAccessible(true);
        theShaderGroup.setAccessible(true);
        listShaders.setAccessible(true);
    }

    public static void rightClickMouse() {
        try {
            String s = !Cloud.isObfuscate ? "rightClickMouse" : "func_147121_ag";
            Minecraft mc = Minecraft.getMinecraft();
            Class<?> c = mc.getClass();
            Method m = c.getDeclaredMethod(s, new Class[0]);
            m.setAccessible(true);
            m.invoke(mc, new Object[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

