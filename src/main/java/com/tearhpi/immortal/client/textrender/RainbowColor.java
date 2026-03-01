package com.tearhpi.immortal.client.textrender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class RainbowColor {
    private Component originalName;
    private GuiGraphics gui;
    private Font font;
    private int textX;
    private int textY;

    public RainbowColor() {
    }

    public RainbowColor(GuiGraphics gui, Component originalName, Font font, int textX, int textY) {
        this.gui = gui;
        this.originalName = originalName;
        this.font = font;
        this.textX = textX;
        this.textY = textY;
    }
    public void draw() {
        String text = originalName.getString();
        //long tick = Minecraft.getInstance().level.getGameTime();
        var mc = Minecraft.getInstance();
        long tick = (mc.level == null) ? 0L : mc.level.getGameTime();
        int offsetX = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // 每个字符的 hue：根据 tick + i（形成滚动）
            float hue = ((tick * 3 + i * 15) % 360) / 360.0f;
            int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
            String letter = String.valueOf(c);
            gui.drawString(font, letter, textX + offsetX, textY, rgb);
            offsetX += font.width(letter);
        }
    }
    public static Component drawComponent(Component originalName) {
        String text = originalName.getString();
        var mc = Minecraft.getInstance();
        long tick = (mc.level == null) ? 0L : mc.level.getGameTime();

        MutableComponent rainbowComponent = Component.empty();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float hue = ((tick * 3 + i * 15) % 360) / 360.0f;
            int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;

            // 每个字符一个子Component，并设置颜色Style
            MutableComponent letterComponent = Component.literal(String.valueOf(c))
                    .withStyle(style -> style.withColor(TextColor.fromRgb((red << 16) | (green << 8) | blue)));

            rainbowComponent.append(letterComponent);
        }
        return rainbowComponent;
    }

    public static float[] intToRGBFloats(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        return new float[] {
                r / 255.0f,
                g / 255.0f,
                b / 255.0f
        };
    }

    public int getRainbowColor() {
        long tick = Minecraft.getInstance().level.getGameTime();
        float hue = ((tick * 3) % 360) / 360.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return rgb;
    }
    public int getRainbowColor_transparent() {
        long tick = Minecraft.getInstance().level.getGameTime();
        float hue = ((tick * 3) % 360) / 360.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return rgb & 0x00FFFFFF;
    }

    public static int getRainbowColor_Spec_Level7_Up() {
        long tick = Minecraft.getInstance().level.getGameTime();
        float hue = ((tick) % 360) / 360.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return rgb& 0xA0FFFFFF;
    }
    public static int getRainbowColor_Spec_Level7_Down() {
        long tick = Minecraft.getInstance().level.getGameTime();
        float hue = ((tick * 3) + 180 % 360) / 360.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return rgb& 0x80FFFFFF;
    }
    public static int getSinusoidalGradientColor() {
        long time = Minecraft.getInstance().level.getGameTime();
        float time_ = (float) time / 20;
        // 正弦函数返回 [-1, 1]，我们映射为 [0, 1]
        float t = (float) (Math.sin(time_) * 0.5 + 0.5);

        // 起始颜色（ARGB）
        int startColor = 0xFF9929ea;
        int endColor = 0xFF5808fb;

        // 提取 R/G/B 分量
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;

        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;

        // 插值计算每个通道
        int r = (int) (startR + (endR - startR) * t);
        int g = (int) (startG + (endG - startG) * t);
        int b = (int) (startB + (endB - startB) * t);

        // 组合为ARGB（不变透明度FF）
        return (0xFF << 24) | (r << 16) | (g << 8) | b;
    }

    public static Component safeRainbowOrPlain(Component base) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            try {
                Class<?> c = Class.forName("com.tearhpi.immortal.client.textrender.RainbowColor");
                return (Component) c.getMethod("drawComponent", Component.class).invoke(null, base);
            } catch (Throwable t) {
                return base;
            }
        }
        return base;
    }
}
