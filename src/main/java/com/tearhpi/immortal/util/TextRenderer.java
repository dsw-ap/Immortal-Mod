package com.tearhpi.immortal.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class TextRenderer {
    /*
    public static List<Component> splitTextByWidth(Font font, Component text, int maxWidth) {
        List<Component> lines = new ArrayList<>();
        MutableComponent currentLine = Component.empty();
        String full = text.getString();
        for (int i = 0; i < full.length(); i++) {
            char c = full.charAt(i);
            MutableComponent testLine =
                    currentLine.copy().append(String.valueOf(c));
            if (font.width(testLine) > maxWidth) {
                lines.add(currentLine);
                currentLine = Component.literal(String.valueOf(c));
            } else {
                currentLine = testLine;
            }
        }
        if (!currentLine.getString().isEmpty()) {
            lines.add(currentLine);
        }
        return lines;
    }
    public static int drawWrappedText(GuiGraphics gg, Font font, Component text, int x, int y, int maxWidth, int lineHeight, int color, boolean shadow) {
        List<Component> lines = splitTextByWidth(font, text, maxWidth);
        int currentY = y;
        for (Component line : lines) {
            gg.drawString(font, line, x, currentY, color, shadow);
            currentY += lineHeight;
        }
        return lines.size() * lineHeight;
    }*/
    /** 保留颜色/样式/不同字体（Style.font） */
    public static List<FormattedCharSequence> splitTextByWidth(Font font, Component text, int maxWidth) {
        if (text == null) return List.of();
        if (maxWidth <= 0) return List.of(text.getVisualOrderText());
        return font.split(text, maxWidth);
    }
    public static float getMaxLengthOfsplitTextByWidth(Font font, Component text, int maxWidth) {
        List<FormattedCharSequence> list = new ArrayList<>();
        if (text == null){
            list = List.of();
        } else if (maxWidth <= 0) {
            list = List.of(text.getVisualOrderText());
        } else {
            list = font.split(text, maxWidth);
        }
        float return_value = 0.0f;
        if (!list.isEmpty()){
            for (FormattedCharSequence formattedCharSequence : list) {
                float val = font.width(formattedCharSequence);
                if (val > return_value) {
                    return_value = val;
                }
            }
        }
        return return_value;
    }
    /**
     * 渲染带样式的换行文本
     * @return 实际占用高度（像素）
     */
    public static int drawWrappedText(GuiGraphics gg, Font font, Component text, int x, int y, int maxWidth, int lineHeight, int defaultColor, boolean shadow) {
        List<FormattedCharSequence> lines = splitTextByWidth(font, text, maxWidth);
        int currentY = y;
        for (FormattedCharSequence line : lines) {
            gg.drawString(font, line, x, currentY, defaultColor, shadow);
            currentY += lineHeight;
        }
        return lines.size() * lineHeight;
    }



    public static int drawWrappedTextAutoLineHeight(GuiGraphics gg, Font font, Component text, int x, int y, int maxWidth, int defaultColor, boolean shadow) {
        return drawWrappedText(gg, font, text, x, y, maxWidth, font.lineHeight, defaultColor, shadow);
    }
}
