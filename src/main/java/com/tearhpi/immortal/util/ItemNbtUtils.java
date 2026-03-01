package com.tearhpi.immortal.util;

import com.tearhpi.immortal.Immortal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class ItemNbtUtils {
    private ItemNbtUtils() {}

    public static int getInt(ItemStack stack, String key, int def) {
        CompoundTag tag = stack.getTag();
        return (tag != null && tag.contains(key, CompoundTag.TAG_INT)) ? tag.getInt(key) : def;
    }

    public static void setInt(ItemStack stack, String key, int value) {
        stack.getOrCreateTag().putInt(key, value);
    }

    public static int getNonNegativeInt(ItemStack stack, String key, int def) {
        int v = getInt(stack, key, def);
        return Math.max(0, v);
    }

    public static void addIntClampMin(ItemStack stack, String key, int delta, int min) {
        int cur = getInt(stack, key, 0);
        int next = Math.max(min, cur + delta);
        setInt(stack, key, next);
    }
    public static java.util.UUID stableUUID(String key) {
        // 基于固定前缀 + key 生成稳定 UUID，避免同名 modifier 叠加冲突
        return java.util.UUID.nameUUIDFromBytes((Immortal.MODID + ":" + key).getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
