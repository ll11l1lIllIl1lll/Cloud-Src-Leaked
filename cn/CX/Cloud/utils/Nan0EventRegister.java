/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.MinecraftDummyContainer
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.common.eventhandler.ASMEventHandler
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.EventBus
 *  net.minecraftforge.fml.common.eventhandler.IEventListener
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package cn.CX.Cloud.utils;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MinecraftDummyContainer;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.ASMEventHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Nan0EventRegister {
    public static void register(EventBus bus, Object target) {
        ConcurrentHashMap listeners = (ConcurrentHashMap)ReflectionHelper.getPrivateValue(EventBus.class, (Object)bus, (String[])new String[]{"listeners"});
        Map listenerOwners = (Map)ReflectionHelper.getPrivateValue(EventBus.class, (Object)bus, (String[])new String[]{"listenerOwners"});
        if (listeners.containsKey(target)) {
            return;
        }
        MinecraftDummyContainer activeModContainer = Loader.instance().getMinecraftModContainer();
        listenerOwners.put(target, activeModContainer);
        ReflectionHelper.setPrivateValue(EventBus.class, (Object)bus, (Object)listenerOwners, (String[])new String[]{"listenerOwners"});
        Set supers = TypeToken.of(target.getClass()).getTypes().rawTypes();
        block2: for (Method method : target.getClass().getMethods()) {
            for (Class cls : supers) {
                try {
                    Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    if (!real.isAnnotationPresent(SubscribeEvent.class)) continue;
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> eventType = parameterTypes[0];
                    Nan0EventRegister.register(bus, eventType, target, method, (ModContainer)activeModContainer);
                    continue block2;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                }
            }
        }
    }

    private static void register(EventBus bus, Class<?> eventType, Object target, Method method, ModContainer owner) {
        try {
            int busID = (Integer)ReflectionHelper.getPrivateValue(EventBus.class, (Object)bus, (String[])new String[]{"busID"});
            ConcurrentHashMap listeners = (ConcurrentHashMap)ReflectionHelper.getPrivateValue(EventBus.class, (Object)bus, (String[])new String[]{"listeners"});
            Constructor<?> ctr = eventType.getConstructor(new Class[0]);
            ctr.setAccessible(true);
            Event event = (Event)ctr.newInstance(new Object[0]);
            ASMEventHandler listener = new ASMEventHandler(target, method, owner);
            event.getListenerList().register(busID, listener.getPriority(), (IEventListener)listener);
            ArrayList<ASMEventHandler> others = (ArrayList<ASMEventHandler>)listeners.get(target);
            if (others == null) {
                others = new ArrayList<ASMEventHandler>();
                listeners.put(target, others);
                ReflectionHelper.setPrivateValue(EventBus.class, (Object)bus, (Object)listeners, (String[])new String[]{"listeners"});
            }
            others.add(listener);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

