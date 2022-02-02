/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  pub.nextsense.gc.LoadClient
 */
package cn.CX.Cloud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import javax.swing.JOptionPane;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pub.nextsense.gc.LoadClient;
import sun.misc.Unsafe;

@Mod(modid="Cloud", name="Cloud", version="1.0.0", acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {
    @Mod.EventHandler
    public void Mod(FMLPreInitializationEvent event) {
        try {
            Socket socket = new Socket("1.14.47.82", 9999);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops, "GBK");
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write("1102934764");
            bw.flush();
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips, "GBK");
            BufferedReader br = new BufferedReader(ipsr);
            String s = null;
            block13: while ((s = br.readLine()) != null) {
                switch (s.hashCode()) {
                    case 1324820516: {
                        JOptionPane.showMessageDialog(null, "Failed To Process The Injection", "QianHeJ", 0);
                        return;
                    }
                    case 1634150652: {
                        LoadClient.L((String)"", (String)"");
                        continue block13;
                    }
                }
                JOptionPane.showMessageDialog(null, "Failed To Process The Injection", "QianHeJ", 0);
                return;
            }
            socket.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed To Process The Injection", "QianHeJ", 0);
            try {
                Field F = Unsafe.class.getDeclaredField("theUnsafe");
                F.setAccessible(true);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
            }
            catch (NoSuchFieldException e1) {
                try {
                    new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                }
                catch (NoSuchFieldException noSuchFieldException) {}
            }
            catch (IllegalAccessException e2) {
                try {
                    new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    // empty catch block
                }
            }
        }
    }
}

