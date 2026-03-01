package com.tearhpi.immortal.event.weapon;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.item.custom.WeaponItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Immortal.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class    ForgingTick {
    // 创建一个唯一的UUID用于识别该属性修改器
    public static final ResourceLocation weaponForgingNormal = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon_forging_normal");
    public static final ResourceLocation weaponForgingAdvanced = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon_forging_advanced");

    // 每 tick 检查
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        Player player = event.player;
        ItemStack heldItem = player.getMainHandItem();
        addAttr(player, heldItem, ModAttributes.IMMORTAL_CRITICAL_CHANCE.get(), (double) WeaponItem.getForgingNum_1(heldItem) / 10000, weaponForgingNormal);
        addAttr(player, heldItem, ModAttributes.IMMORTAL_EXTRA_MDAMAGE.get(), (double) WeaponItem.getForgingNum_2(heldItem) / 10000, weaponForgingNormal);
        addAttrAfterFive(player, heldItem, ModAttributes.IMMORTAL_IGNORE_DEFENSE.get(), 0.1, weaponForgingAdvanced);
    }
    private static UUID stableUuid(ResourceLocation id) {
        return UUID.nameUUIDFromBytes(id.toString().getBytes(StandardCharsets.UTF_8));
    }
    private static void addAttr(Player player, ItemStack heldItem,
                                Attribute attribute, double amount, ResourceLocation key) {
        AttributeInstance inst = player.getAttribute(attribute);
        if (inst == null) return;
        UUID id = stableUuid(key);
        boolean holdingWeapon = heldItem.getItem() instanceof WeaponItem;
        AttributeModifier existing = inst.getModifier(id);

        if (holdingWeapon) {
            // 需要存在且数值正确：MULTIPLY_TOTAL（1.20.1 枚举）
            if (existing == null
                    || existing.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL
                    || existing.getAmount() != amount) {
                if (existing != null) inst.removeModifier(id);
                inst.addTransientModifier(new AttributeModifier(
                        id,
                        key.toString(),                // 名称，仅用于显示/调试
                        amount,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                ));
            }
        } else {
            // 没有持有武器则移除
            if (existing != null) {
                inst.removeModifier(id);
            }
        }
    }
    private static void addAttrAfterFive(Player player, ItemStack heldItem,
                                        Attribute attribute, double amount, ResourceLocation key) {
        AttributeInstance inst = player.getAttribute(attribute);
        if (inst == null) return;
        UUID id = stableUuid(key);
        boolean holdingWeapon = (heldItem.getItem() instanceof WeaponItem) && (WeaponItem.getForgingLevel(heldItem) == 5);
        AttributeModifier existing = inst.getModifier(id);

        if (holdingWeapon) {
            // 需要存在且数值正确：MULTIPLY_TOTAL（1.20.1 枚举）
            if (existing == null
                    || existing.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL
                    || existing.getAmount() != amount) {
                if (existing != null) inst.removeModifier(id);
                inst.addTransientModifier(new AttributeModifier(
                        id,
                        key.toString(),                // 名称，仅用于显示/调试
                        amount,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                ));
            }
        } else {
            // 没有持有武器则移除
            if (existing != null) {
                inst.removeModifier(id);
            }
        }
    }
}
