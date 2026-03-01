package com.tearhpi.immortal.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.ClientExtension.CustomArmorClientExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.tearhpi.immortal.util.ModItemNbtKeys.*;

public class CustomArmorItem extends ArmorItem implements GeoItem {
    // ---------- NBT Keys（替代 Data Components） ----------
    //public static final String ATTACHED_ATTRIBUTE_RUNE_MAX = "immortal.att_attr_rune_max";
    //public static final String ATTACHED_ATTRIBUTE_RUNE     = "immortal.att_attr_rune";

    // ---Tooltip渲染部分---
    public int Tooltip_width;   //武器宽
    public int Tooltip_height;  //武器高
    public int Tooltip_x;       //左上角x
    public int Tooltip_y;       //左上角y

    // ---盔甲功能部分---
    public final int armor_level;        //盔甲等级
    public final int armor_suit_number;  //盔甲套装编号


    // 属性列表（1.20.1：用 Attribute 而不是 Holder）
    public final Multimap<Attribute, AttributeModifier> attributeModifiers =
            MultimapBuilder.linkedHashKeys().arrayListValues().build();

    // 主动/被动/套装描述
    public final LinkedList<String> SkillInfo_passive = new LinkedList<>();
    public final LinkedList<String> SkillInfo_active  = new LinkedList<>();
    public final LinkedList<String> SkillInfo_attach  = new LinkedList<>();

    public final LinkedList<String> SkillInfo_suit_name = new LinkedList<>();
    public final LinkedList<String> SkillInfo_suit_kit2 = new LinkedList<>();
    public final LinkedList<String> SkillInfo_suit_kit3 = new LinkedList<>();
    public final LinkedList<String> SkillInfo_suit_kit4 = new LinkedList<>();
    public final LinkedList<LinkedList<String>> SkillInfo_suit = new LinkedList<>();

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    // 1.20.1 构造：ArmorMaterial + EquipmentSlot
    public CustomArmorItem(ArmorMaterial material, Type type,
                           int armor_level, int armor_suit_number, Properties props) {
        super(material, type, props);
        this.armor_level = armor_level;
        this.armor_suit_number = armor_suit_number;
        this.Tooltip_width = 0;
        this.Tooltip_height = 0;
        this.Tooltip_x = 0;
        this.Tooltip_y = 0;

        SkillInfo_suit.add(SkillInfo_suit_name);
        SkillInfo_suit.add(SkillInfo_suit_kit2);
        SkillInfo_suit.add(SkillInfo_suit_kit3);
        SkillInfo_suit.add(SkillInfo_suit_kit4);
    }

    // ========== GeckoLib ==========
    @Override public void registerControllers(software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar controllers) {}
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }


    // ========== Tooltip 自定义渲染 ==========
    @Override
    public Component getName(ItemStack stack) {
        return Component.empty();
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,
                () -> () -> consumer.accept(new CustomArmorClientExtension(this)));
    }
    @Override
    public Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        return DistExecutor.unsafeRunForDist(
                () -> () -> (new CustomArmorClientExtension(this)).getTooltipImage(stack),
                () -> Optional::empty
        );
    }


    // ========== 属性添加（1.20.1 写法） ==========
    private static UUID stableUUID(String key) {
        return UUID.nameUUIDFromBytes((Immortal.MODID + ":" + key).getBytes(StandardCharsets.UTF_8));
    }

    /** 直接加值（ADDITION） */
    public CustomArmorItem addAttribute(Attribute attribute, String key, double value) {
        AttributeModifier modifier = new AttributeModifier(
                stableUUID(key),
                Immortal.MODID + ":" + key,
                value,
                AttributeModifier.Operation.ADDITION
        );
        attributeModifiers.put(attribute, modifier);
        return this;
    }

    /** 乘以基础值（MULTIPLY_BASE） */
    public CustomArmorItem addAttributeMultiplyBase(Attribute attribute, String key, double value) {
        AttributeModifier modifier = new AttributeModifier(
                stableUUID(key),
                Immortal.MODID + ":" + key,
                value,
                AttributeModifier.Operation.MULTIPLY_BASE
        );
        attributeModifiers.put(attribute, modifier);
        return this;
    }

    // ========== 星星动效 ==========


    // ========== 技能描述链式 API ==========
    public CustomArmorItem addSkillInfo_passive(String info) { this.SkillInfo_passive.add(info); return this; }
    public CustomArmorItem addSkillInfo_active (String info) { this.SkillInfo_active.add(info);  return this; }
    public CustomArmorItem addSkillInfo_attach (String info) { this.SkillInfo_attach.add(info);  return this; }
    public CustomArmorItem addSkillInfo_suit(int index, String info) {
        if (index == 0) this.SkillInfo_suit_name.add(info);
        if (index == 1) this.SkillInfo_suit_kit2.add(info);
        if (index == 2) this.SkillInfo_suit_kit3.add(info);
        if (index == 3) this.SkillInfo_suit_kit4.add(info);
        return this;
    }

    // ========== NBT：获取/修改（替代 Data Components） ==========
    public static int getAttrRuneMax(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(ATTACHED_ATTRIBUTE_RUNE_MAX)
                ? stack.getTag().getInt(ATTACHED_ATTRIBUTE_RUNE_MAX) : 0;
    }
    public static int getAttrRune(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(ATTACHED_ATTRIBUTE_RUNE)
                ? stack.getTag().getInt(ATTACHED_ATTRIBUTE_RUNE) : 0;
    }
    public static void addAttrRune(ItemStack stack) {
        int cur = getAttrRune(stack);
        stack.getOrCreateTag().putInt(ATTACHED_ATTRIBUTE_RUNE, cur + 1);
    }

    // ========== 默认 NBT ==========
    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        ensureDefaults(p_41404_);
        super.inventoryTick(p_41404_,p_41405_, p_41406_, p_41407_, p_41408_);
    }
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        applyDefaults(stack);
        return stack;
    }
    @Override
    public void onCraftedBy(ItemStack stack, Level level, net.minecraft.world.entity.player.Player player) {
        super.onCraftedBy(stack, level, player);
        ensureDefaults(stack);
    }
    private void applyDefaults(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(ATTACHED_ATTRIBUTE_RUNE_MAX, 2);
        tag.putInt("HideFlags",2);
        tag.putInt(ATTACHED_ATTRIBUTE_RUNE, 0);
    }
    private void ensureDefaults(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains(ATTACHED_ATTRIBUTE_RUNE_MAX)) stack.getOrCreateTag().putInt(ATTACHED_ATTRIBUTE_RUNE_MAX, 2);
        if (!stack.getTag().contains(ATTACHED_ATTRIBUTE_RUNE)) stack.getTag().putInt(ATTACHED_ATTRIBUTE_RUNE, 0);
        if (!stack.getTag().contains("HideFlags")) stack.getTag().putInt("HideFlags", 2);
    }

    //1.20.1属性签名
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST
                || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(slot));
            builder.putAll(this.attributeModifiers);
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    //Properties
    public static class ArmorItemProperties extends Item.Properties {
        public int attrRuneMax = 2;

        public ArmorItemProperties(int attr_rune_max_count) {
            super();
            this.attrRuneMax = Math.max(0, attr_rune_max_count);
        }
    }
}
