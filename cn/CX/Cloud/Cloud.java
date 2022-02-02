/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.network.play.server.S18PacketEntityTeleport
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  org.apache.commons.codec.binary.Hex
 */
package cn.CX.Cloud;

import cn.CX.Cloud.command.CommandManager;
import cn.CX.Cloud.eventapi.eventhandlers.EventsHandler;
import cn.CX.Cloud.file.files.ClickGuiFile;
import cn.CX.Cloud.file.files.KeybindFile;
import cn.CX.Cloud.file.files.ModuleFile;
import cn.CX.Cloud.file.files.SettingsButtonFile;
import cn.CX.Cloud.file.files.SettingsComboBoxFile;
import cn.CX.Cloud.file.files.SettingsSliderFile;
import cn.CX.Cloud.modules.ModuleManager;
import cn.CX.Cloud.settings.SettingsManager;
import cn.CX.Cloud.ui.clickgui.ClickGui;
import cn.CX.Cloud.ui.external.FCommand;
import cn.CX.Cloud.utils.Nan0EventRegister;
import cn.CX.Cloud.utils.QQUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.codec.binary.Hex;
import sun.misc.Unsafe;

public class Cloud {
    public static Cloud instance;
    public static String Check_NAME;
    public static String NAME;
    public static String Version;
    public static String RankS;
    public static String Ranks1;
    public static String QQ;
    public static boolean isObfuscate;
    public static boolean state;
    public static String Data;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager = new SettingsManager();
    public CommandManager commandManager;
    public static EventsHandler eventsHandler;
    public ClickGui clickgui;
    public File directory;

    public Cloud() {
        if (state) {
            return;
        }
        state = true;
        FCommand.main(null);
    }

    static {
        Check_NAME = "ClassLoader";
        NAME = "Cloud";
        Version = "0.0.1";
        RankS = "BASE";
        Ranks1 = "Cloud";
        isObfuscate = false;
        state = false;
        Data = null;
        try {
            QQ = Cloud.getQQ();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void drawNotifications() {
        double startY;
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        double lastY = startY = (double)res.getScaledHeight() - (double)res.getScaledHeight() * 0.16;
    }

    public void setOBF() {
        instance = this;
        try {
            Field F = S18PacketEntityTeleport.class.getDeclaredField("field_149456_b");
            isObfuscate = true;
        }
        catch (NoSuchFieldException ex) {
            try {
                Field F = S18PacketEntityTeleport.class.getDeclaredField("posX");
                isObfuscate = false;
            }
            catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getHWID(boolean isMD5) throws IOException, NoSuchAlgorithmException {
        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        if (isMD5) {
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] ciphertext = messageDigest.digest(serial.getBytes());
            return Hex.encodeHexString((byte[])ciphertext);
        }
        return serial;
    }

    public void NativesLoader(boolean isRegsist) {
        Field F;
        String s;
        BufferedReader br;
        InputStreamReader ipsr;
        InputStream ips;
        BufferedWriter bw;
        OutputStreamWriter opsw2;
        OutputStream ops;
        Socket socket;
        try {
            socket = new Socket("1.14.47.82", 9999);
            ops = socket.getOutputStream();
            opsw2 = new OutputStreamWriter(ops, "GBK");
            bw = new BufferedWriter(opsw2);
            bw.write("1102934764");
            bw.flush();
            ips = socket.getInputStream();
            ipsr = new InputStreamReader(ips, "GBK");
            br = new BufferedReader(ipsr);
            s = null;
            block32: while ((s = br.readLine()) != null) {
                switch (s.hashCode()) {
                    case 1324820516: {
                        JOptionPane.showMessageDialog(null, "Failed To Process The Injection", "QianHeJ", 0);
                        return;
                    }
                    case 1634150652: {
                        continue block32;
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
                F = Unsafe.class.getDeclaredField("theUnsafe");
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
                catch (NoSuchFieldException opsw2) {}
            }
            catch (IllegalAccessException e2) {
                try {
                    new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                }
                catch (NoSuchFieldException opsw2) {
                    // empty catch block
                }
            }
        }
        try {
            socket = new Socket("106.12.254.120", 8848);
            ops = socket.getOutputStream();
            opsw2 = new OutputStreamWriter(ops, "GBK");
            bw = new BufferedWriter(opsw2);
            String pathname = "C:\\Cloud.User";
            String LicenceCode = null;
            FileReader reader = new FileReader(pathname);
            BufferedReader BUUFEER = new BufferedReader(reader);
            while ((LicenceCode = BUUFEER.readLine()) != null) {
                bw.write(LicenceCode + Cloud.getQQ() + "=~CNMB123~=" + Cloud.getHWID(false) + "=~CNMB123~=" + RankS);
                bw.flush();
                InputStream ips2 = socket.getInputStream();
                InputStreamReader ipsr2 = new InputStreamReader(ips2, "GBK");
                BufferedReader br2 = new BufferedReader(ipsr2);
                String s2 = null;
                block34: while ((s2 = br2.readLine()) != null) {
                    Data = Cloud.Send("106.12.254.120", 8848, LicenceCode + Cloud.getQQ() + "=~CNMB123~=" + Cloud.getHWID(false) + "=~CNMB123~=" + RankS);
                    switch (s2.hashCode()) {
                        case 1324820516: {
                            JOptionPane.showMessageDialog(null, "Verified Failed Please Re-Inject" + Cloud.getQQ(), "Cloud", 0);
                            instance = null;
                            return;
                        }
                        case 1634150652: {
                            if (!isRegsist) continue block34;
                            this.setOBF();
                            this.directory = new File(Minecraft.getMinecraft().mcDataDir, NAME);
                            if (!this.directory.exists()) {
                                this.directory.mkdir();
                            }
                            this.moduleManager = new ModuleManager();
                            this.commandManager = new CommandManager();
                            eventsHandler = new EventsHandler();
                            this.clickgui = new ClickGui();
                            Nan0EventRegister.register(MinecraftForge.EVENT_BUS, eventsHandler);
                            Nan0EventRegister.register(FMLCommonHandler.instance().bus(), eventsHandler);
                            MinecraftForge.EVENT_BUS.register((Object)this);
                            FMLCommonHandler.instance().bus().register((Object)this);
                            KeybindFile.loadKeybinds();
                            SettingsButtonFile.loadState();
                            SettingsComboBoxFile.loadState();
                            SettingsSliderFile.loadState();
                            ClickGuiFile.loadClickGui();
                            ModuleFile.loadModules();
                            continue block34;
                        }
                    }
                    instance = null;
                    MinecraftForge.EVENT_BUS.unregister((Object)this);
                    FMLCommonHandler.instance().bus().unregister((Object)this);
                    return;
                }
            }
            socket.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed Connect to The Server(0x66FF)", "Cloud", 0);
            e.printStackTrace();
        }
        try {
            socket = new Socket("1.14.47.82", 9999);
            ops = socket.getOutputStream();
            opsw2 = new OutputStreamWriter(ops, "GBK");
            bw = new BufferedWriter(opsw2);
            bw.write("1102934764");
            bw.flush();
            ips = socket.getInputStream();
            ipsr = new InputStreamReader(ips, "GBK");
            br = new BufferedReader(ipsr);
            s = null;
            block35: while ((s = br.readLine()) != null) {
                switch (s.hashCode()) {
                    case 1324820516: {
                        JOptionPane.showMessageDialog(null, "Failed To Process The Injection", "QianHeJ", 0);
                        return;
                    }
                    case 1634150652: {
                        continue block35;
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
                F = Unsafe.class.getDeclaredField("theUnsafe");
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

    public static String Send(String IP, int Port, String Message) {
        try {
            Socket socket = new Socket(IP, Port);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops, "GBK");
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(Message);
            bw.flush();
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips, "GBK");
            BufferedReader br = new BufferedReader(ipsr);
            String s = null;
            s = br.readLine();
            if (s != null) {
                return s;
            }
            socket.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed Connect to The Server(0x66FF)", "Cloud", 0);
            e.printStackTrace();
        }
        return null;
    }

    public static String getQQ() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String R2 = "}";
        String ZZ = new String(String.valueOf(QQUtils.getLoginQQList()));
        String[] Z = ZZ.split("=", 2);
        String QQDT = Z[1];
        String QQNum = QQDT.replaceAll(R2, "");
        return QQNum;
    }

    public static void Crash() {
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
        catch (NoSuchFieldException e) {
            try {
                new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
            }
            catch (NoSuchFieldException noSuchFieldException) {}
        }
        catch (IllegalAccessException e) {
            try {
                new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
            }
            catch (NoSuchFieldException noSuchFieldException) {
                // empty catch block
            }
        }
    }

    public static String Connect(String IP, int Port, String Message) {
        try {
            Socket socket = new Socket(IP, Port);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops, "GBK");
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(Message);
            bw.flush();
            socket.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }
}

