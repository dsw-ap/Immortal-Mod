package com.tearhpi.immortal.item.item_register;

import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.item.custom.MythItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class WeaponAddonRegister {
    public static void register() {}
    //强化相关
    public static final RegistryObject<Item> WeaponEnhancementStone = ModItems.ITEMS.register("weapon_enhancement_stone",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.weaponenhancement_stone_introduction"));
                    super.appendHoverText(stack, level, tooltip, flag);
                }
            });
    public static final RegistryObject<Item> WeaponEnhancementSuccessScroll_1 = ModItems.ITEMS.register("weapon_enhancement_success_scroll_1",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
                    tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_1_introduction_"));
                    super.appendHoverText(stack, level, tooltip, flag);
                }
            });
    public static final RegistryObject<Item> WeaponEnhancementSuccessScroll_2 = ModItems.ITEMS.register("weapon_enhancement_success_scroll_2", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_2_introduction_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }
    });
    public static final RegistryObject<Item> WeaponEnhancementSuccessScroll_3 = ModItems.ITEMS.register("weapon_enhancement_success_scroll_3", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_3_introduction_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }
    });
    public static final RegistryObject<Item> WeaponEnhancementSuccessScroll_4 = ModItems.ITEMS.register("weapon_enhancement_success_scroll_4", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_4_introduction_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }
    });
    public static final RegistryObject<Item> WeaponEnhancementSuccessScroll_5 = ModItems.ITEMS.register("weapon_enhancement_success_scroll_5", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_5_introduction_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> WeaponEnhancementProtectionScroll_1 = ModItems.ITEMS.register("weapon_enhancement_protection_scroll_1", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weaponenhancement_protection_scroll_1_introduction"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> WeaponEnhancementProtectionScroll_2 = ModItems.ITEMS.register("weapon_enhancement_protection_scroll_2", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponenhancement_success_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weaponenhancement_protection_scroll_2_introduction"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    //锻造相关
    public static final RegistryObject<Item> WeaponForgingStone = ModItems.ITEMS.register("weapon_forging_stone", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weapon_forging_stone"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> WeaponForgingChanceScroll_1 = ModItems.ITEMS.register("weapon_forging_chance_scroll_1", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponen_forging_chance_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weapon_forging_chance_scroll_1"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> WeaponForgingChanceScroll_2 = ModItems.ITEMS.register("weapon_forging_chance_scroll_2", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponen_forging_chance_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weapon_forging_chance_scroll_2"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> WeaponForgingChanceScroll_3 = ModItems.ITEMS.register("weapon_forging_chance_scroll_3", () -> new MythItem(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponen_forging_chance_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weapon_forging_chance_scroll_3"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> WeaponForgingResetScroll = ModItems.ITEMS.register("weapon_forging_reset_scroll", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.weaponen_forging_chance_scroll_introduction"));
            tooltip.add(Component.translatable("tooltip.weapon_forging_reset_scroll"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    //镀金相关
    public static final RegistryObject<Item> GoldType1 = ModItems.ITEMS.register("gold_type_1", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.gold_type_1"));
            tooltip.add(Component.translatable("tooltip.gold_type_1_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> GoldType2 = ModItems.ITEMS.register("gold_type_2", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.gold_type_2"));
            tooltip.add(Component.translatable("tooltip.gold_type_2_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> GoldType3 = ModItems.ITEMS.register("gold_type_3", () -> new Item(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.gold_type_3"));
            tooltip.add(Component.translatable("tooltip.gold_type_3_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
    public static final RegistryObject<Item> GoldType4 = ModItems.ITEMS.register("gold_type_4", () -> new MythItem(new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.gold_type_4"));
            tooltip.add(Component.translatable("tooltip.gold_type_4_"));
            super.appendHoverText(stack, level, tooltip, flag);
        }});
}
