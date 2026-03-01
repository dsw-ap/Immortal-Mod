package com.tearhpi.immortal.client.textrender;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

public class RainbowColorClient {
    public static Component drawComponent(Component originalName) {
        String text = originalName.getString();
        long tick = 0L;

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
}
