/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package cn.CX.Cloud.eventapi.eventhandlers;

import cn.CX.Cloud.Cloud;
import cn.CX.Cloud.eventapi.EventManager;
import cn.CX.Cloud.modules.Module;
import cn.CX.Cloud.ui.clickgui.ClickGui;
import cn.CX.Cloud.utils.Connection;
import cn.CX.Cloud.utils.HUDUtils;
import cn.CX.Cloud.utils.QQUtils;
import cn.CX.Cloud.utils.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventsHandler {
    private static long lastMS = 0L;
    private boolean initialized = false;
    private long lastFrame;
    private int tips;
    public static String UnSuppotIPs = "join.mchycraft.com:25565";

    public EventsHandler() {
        EventManager.register(this);
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        Cloud cloud = Cloud.instance;
        for (Module hack : cloud.moduleManager.getModules()) {
            if (!hack.getState() || Minecraft.getMinecraft().theWorld == null) continue;
            suc &= hack.onPacket(packet, side);
        }
        return suc;
    }

    public void NativesLoader() {
        try {
            Socket socket = new Socket("106.12.254.120", 8848);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops, "GBK");
            BufferedWriter bw = new BufferedWriter(opsw);
            String pathname = "C:\\Cloud.User";
            String LicenceCode = null;
            FileReader reader = new FileReader(pathname);
            BufferedReader BUUFEER = new BufferedReader(reader);
            while ((LicenceCode = BUUFEER.readLine()) != null) {
                bw.write(LicenceCode + EventsHandler.getQQ() + "=~CNMB123~=" + Cloud.getHWID(false) + "=~CNMB123~=" + Cloud.RankS);
                bw.flush();
                InputStream ips = socket.getInputStream();
                InputStreamReader ipsr = new InputStreamReader(ips, "GBK");
                BufferedReader br = new BufferedReader(ipsr);
                String s = null;
                block7: while ((s = br.readLine()) != null) {
                    Cloud.Data = EventsHandler.Send("106.12.254.120", 8848, LicenceCode + EventsHandler.getQQ() + "=~CNMB123~=" + Cloud.getHWID(false) + "=~CNMB123~=" + Cloud.RankS);
                    switch (s.hashCode()) {
                        case 1324820516: {
                            Cloud.Crash();
                            Cloud.instance = null;
                            MinecraftForge.EVENT_BUS.unregister((Object)this);
                            FMLCommonHandler.instance().bus().unregister((Object)this);
                            return;
                        }
                        case 1634150652: {
                            continue block7;
                        }
                    }
                    Cloud.Crash();
                    Cloud.instance = null;
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

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - lastMS >= delay;
    }

    public void setLastMS() {
        lastMS = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Utils.nullCheck()) {
            this.initialized = false;
            return;
        }
        if (this.isDelayComplete(300000L)) {
            this.NativesLoader();
            this.setLastMS();
        }
        try {
            if (!this.initialized) {
                new Connection(this);
                ClickGui.ISCN = Cloud.instance.settingsManager.getSettingByName(Cloud.instance.moduleManager.getModule("ClickGUI"), "Chinese Mode").isEnabled();
                if (!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hycraft")) {
                    long currentTimeMillis = System.currentTimeMillis();
                    HUDUtils.delta = (float)(currentTimeMillis - this.lastFrame) / 500.0f;
                    this.lastFrame = currentTimeMillis;
                    Module Reach2 = Cloud.instance.moduleManager.getModule("Reach");
                    Module HitBox2 = Cloud.instance.moduleManager.getModule("HitBox");
                    Module autoclicker = Cloud.instance.moduleManager.getModule("AutoClicker");
                    Minecraft.getMinecraft().entityRenderer.getMouseOver(1.0f);
                    Utils.su();
                    this.initialized = true;
                } else {
                    JOptionPane.showMessageDialog(null, "LOSER CAN'T USE IT IN HYCRAFT");
                }
            }
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
        }
    }

    public static String getCpuId() throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        System.out.println(property + ": " + serial);
        return serial;
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        Cloud cloud = Cloud.instance;
        for (Module m : cloud.moduleManager.getModules()) {
            if (Keyboard.isKeyDown((int)0) || !Keyboard.isKeyDown((int)m.key)) continue;
            m.toggle();
        }
    }
}

