package com.tearhpi.immortal.util;


import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TextureSizeCache {
    private static final Map<ResourceLocation, int[]> CACHE = new ConcurrentHashMap<>();

    private TextureSizeCache() {}

    /**
     * @param texture RL 必须指向真实文件路径（通常是 "modid:textures/xxx.png"）
     * @return int[]{width, height}，失败返回 {0,0}
     */
    public static int[] getSize(ResourceLocation texture) {
        return CACHE.computeIfAbsent(texture, (rl) -> {
            ResourceManager rm = Minecraft.getInstance().getResourceManager();
            try {
                // 1.20.1: 用 getResource(rl) 拿到 Resource
                Resource res = rm.getResource(rl).orElse(null);
                if (res == null) return new int[]{0, 0};

                try (InputStream in = res.open()) {
                    NativeImage img = NativeImage.read(in);
                    int w = img.getWidth();
                    int h = img.getHeight();
                    img.close();
                    return new int[]{w, h};
                }
            } catch (Exception e) {
                return new int[]{0, 0};
            }
        });
    }
}
