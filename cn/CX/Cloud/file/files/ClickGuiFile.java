/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.file.files;

import cn.CX.Cloud.file.FileManager;
import cn.CX.Cloud.ui.clickgui.ClickGui;
import cn.CX.Cloud.ui.clickgui.component.Frame;

public class ClickGuiFile {
    private static FileManager clickGuiCoord = new FileManager("clickgui", "Cloud");

    public ClickGuiFile() {
        try {
            ClickGuiFile.loadClickGui();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveClickGui() {
        try {
            clickGuiCoord.clear();
            for (Frame frame : ClickGui.frames) {
                clickGuiCoord.write(frame.category.name() + ":" + frame.getX() + ":" + frame.getY() + ":" + frame.isOpen());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadClickGui() {
        try {
            for (String s : clickGuiCoord.read()) {
                String panelName = s.split(":")[0];
                float panelCoordX = Float.parseFloat(s.split(":")[1]);
                float panelCoordY = Float.parseFloat(s.split(":")[2]);
                boolean extended = Boolean.parseBoolean(s.split(":")[3]);
                for (Frame frame : ClickGui.frames) {
                    if (!frame.category.name().equalsIgnoreCase(panelName)) continue;
                    frame.setX((int)panelCoordX);
                    frame.setY((int)panelCoordY);
                    frame.setOpen(extended);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

