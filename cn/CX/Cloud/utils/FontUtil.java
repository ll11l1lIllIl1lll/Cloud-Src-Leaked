/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.util.StringUtils
 */
package cn.CX.Cloud.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

public class FontUtil {
    private static FontRenderer fontRenderer;

    public static void drawString(String text, double x, double y, int color) {
        fontRenderer.drawString(text, (int)x, (int)y, color);
    }

    public static void setupFontUtils() {
        fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }

    public static int getFontHeight() {
        return FontUtil.fontRenderer.FONT_HEIGHT;
    }

    public static void drawCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawTotalCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y - (double)(FontUtil.fontRenderer.FONT_HEIGHT / 2), color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2), y - (double)((float)Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2.0f), color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    public static int getStringWidth(String text) {
        return fontRenderer.getStringWidth(StringUtils.stripControlCodes((String)text));
    }
}

