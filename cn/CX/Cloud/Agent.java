/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.LaunchClassLoader
 */
package cn.CX.Cloud;

import cn.CX.Cloud.Cloud;
import java.lang.instrument.Instrumentation;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class Agent {
    public static void agentmain(String args, Instrumentation instrumentation) throws Exception {
        for (Class classes : instrumentation.getAllLoadedClasses()) {
            if (!classes.getName().startsWith("net.minecraft.client.Minecraft")) continue;
            LaunchClassLoader classLoader = (LaunchClassLoader)classes.getClassLoader();
            classLoader.addURL(Agent.class.getProtectionDomain().getCodeSource().getLocation());
            Class client = classLoader.loadClass(Cloud.class.getName());
            client.newInstance();
        }
    }
}

