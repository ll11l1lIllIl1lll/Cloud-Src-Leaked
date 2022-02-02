/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.utils;

import cn.CX.Cloud.Cloud;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ReflectUtil {
    public static MethodHandles.Lookup lookup;

    public static Object invoke(Object target, String methodName, String obfName, Class[] methodArgs, Object[] args) {
        String[] stringArray;
        Class<?> clazz = target.getClass();
        if (Cloud.isObfuscate) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = obfName;
        } else {
            String[] stringArray3 = new String[1];
            stringArray = stringArray3;
            stringArray3[0] = methodName;
        }
        Method method = ReflectionHelper.findMethod(clazz, (Object)target, (String[])stringArray, (Class[])methodArgs);
        method.setAccessible(true);
        try {
            return method.invoke(target, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getField(String field, String obfName, Class clazz) {
        Field fField = ReflectionHelper.findField((Class)clazz, (String[])new String[]{field, obfName});
        fField.setAccessible(true);
        try {
            return fField.get(clazz);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getField(String field, String obfName, Object instance) {
        String[] stringArray;
        Class<?> clazz = instance.getClass();
        if (Cloud.isObfuscate) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = obfName;
        } else {
            String[] stringArray3 = new String[1];
            stringArray = stringArray3;
            stringArray3[0] = field;
        }
        Field fField = ReflectionHelper.findField(clazz, (String[])stringArray);
        fField.setAccessible(true);
        try {
            return fField.get(instance);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class class_, String ... arrstring) {
        for (Field field : class_.getDeclaredFields()) {
            field.setAccessible(true);
            for (String string : arrstring) {
                if (!field.getName().equals(string)) continue;
                return field;
            }
        }
        return null;
    }

    public static MethodHandles.Lookup lookup() {
        return lookup;
    }

    public static void setField(Class targetClass, Object instance, Object newValue, boolean isFinal, String ... arrstring) {
        Field strField = ReflectionHelper.findField((Class)targetClass, (String[])arrstring);
        strField.setAccessible(true);
        try {
            if (isFinal) {
                Field modField = Field.class.getDeclaredField("modifiers");
                modField.setAccessible(true);
                modField.setInt(strField, strField.getModifiers() & 0xFFFFFFEF);
            }
            strField.set(instance, newValue);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setField(String string, String obfName, Object instance, Object newValue, boolean isFinal) {
        Field strField = ReflectionHelper.findField(instance.getClass(), (String[])new String[]{string, obfName});
        strField.setAccessible(true);
        try {
            if (isFinal) {
                Field modField = Field.class.getDeclaredField("modifiers");
                modField.setAccessible(true);
                modField.setInt(strField, strField.getModifiers() & 0xFFFFFFEF);
            }
            strField.set(instance, newValue);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setField(String string, String obfName, Class targetClass, Object instance, Object newValue, boolean isFinal) {
        Field strField = ReflectionHelper.findField((Class)targetClass, (String[])new String[]{string, obfName});
        strField.setAccessible(true);
        try {
            if (isFinal) {
                Field modField = Field.class.getDeclaredField("modifiers");
                modField.setAccessible(true);
                modField.setInt(strField, strField.getModifiers() & 0xFFFFFFEF);
            }
            strField.set(instance, newValue);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

