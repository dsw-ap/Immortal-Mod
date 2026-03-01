package com.tearhpi.immortal.item.custom.Weapon;

import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.item.custom.WandItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class Weapon_1_4 extends WandItem {
    private static final String TAG_CD = "Weapon1_4_cd";
    public Weapon_1_4(WeaponItemProperties p_41383_, int weapon_level, int weapon_type, WeaponAttributeAttack weaponAttributeAttack) {
        super(p_41383_, weapon_level, weapon_type, weaponAttributeAttack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (level.isClientSide) return; // CD逻辑放服务端
        if (!(entity instanceof Player)) return;

        int cd = getCd(stack);
        if (cd > 0) {
            setCd(stack, cd - 1);
        }
    }

    /** 读取当前CD */
    public static int getCd(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt(TAG_CD) : 0;
    }

    /** 设置CD */
    public static void setCd(ItemStack stack, int ticks) {
        stack.getOrCreateTag().putInt(TAG_CD, Math.max(0, ticks));
    }

    /** 是否处于冷却中 */
    public static boolean isOnCd(ItemStack stack) {
        return getCd(stack) > 0;
    }
}
