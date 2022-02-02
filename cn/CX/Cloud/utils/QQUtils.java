/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.Pointer
 *  com.sun.jna.win32.StdCallLibrary
 *  com.sun.jna.win32.StdCallLibrary$StdCallCallback
 */
package cn.CX.Cloud.utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import java.util.HashMap;
import java.util.Map;

public class QQUtils {
    private static final String QQ_WINDOW_TEXT_PRE = "qqexchangewnd_shortcut_prefix_";
    private static final User32 user32 = User32.INSTANCE;

    private static boolean _filterQQInfo(String windowText) {
        return windowText.startsWith(QQ_WINDOW_TEXT_PRE);
    }

    public static Map<String, String> getLoginQQList() {
        final HashMap<String, String> map = new HashMap<String, String>(5);
        user32.EnumWindows(new User32.WNDENUMPROC(){

            @Override
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString((byte[])windowText);
                if (QQUtils._filterQQInfo(wText)) {
                    map.put(hWnd.toString(), wText.substring(wText.indexOf(QQUtils.QQ_WINDOW_TEXT_PRE) + QQUtils.QQ_WINDOW_TEXT_PRE.length()));
                }
                return true;
            }
        }, null);
        return map;
    }

    public static interface User32
    extends StdCallLibrary {
        public static final User32 INSTANCE = (User32)Native.loadLibrary((String)"user32", User32.class);

        public boolean EnumWindows(WNDENUMPROC var1, Pointer var2);

        public int GetWindowTextA(Pointer var1, byte[] var2, int var3);

        public static interface WNDENUMPROC
        extends StdCallLibrary.StdCallCallback {
            public boolean callback(Pointer var1, Pointer var2);
        }
    }
}

