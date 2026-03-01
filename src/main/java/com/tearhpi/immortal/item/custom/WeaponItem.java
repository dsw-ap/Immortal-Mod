package com.tearhpi.immortal.item.custom;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.item.custom.ClientExtension.WeaponClientExtension;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.tearhpi.immortal.util.ItemNbtUtils.*;
import static com.tearhpi.immortal.util.ModItemNbtKeys.*;

public class WeaponItem extends Item {
    //---Tooltip渲染部分---
    public int Tooltip_width;//武器宽
    public int Tooltip_height;//武器高
    public int Tooltip_x;//左上角x
    public int Tooltip_y;//左上角y
    //---武器功能部分---
    public final int weapon_level;//武器等级
    public final int weapon_type;//武器种类
    public final WeaponAttributeAttack weapon_attribute; //武器默认属性
    //---武器数值部分---
    //原版attributeModifier相关数值
    public final Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers_base = MultimapBuilder.linkedHashKeys().arrayListValues().build(); //仅包含三种基础属性
    public final Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = MultimapBuilder.linkedHashKeys().arrayListValues().build();
    public final LinkedList<String> SkillInfo_passive = new LinkedList<>();
    public final LinkedList<String> SkillInfo_active = new LinkedList<>();
    public final LinkedList<String> SkillInfo_Attached = new LinkedList<>();
    //武器属性
    private final WeaponItemProperties defaults;

    public WeaponItem(WeaponItemProperties p_41383_, int weapon_level, int weapon_type, WeaponAttributeAttack weaponAttributeAttack) {
        super(p_41383_);
        this.defaults = p_41383_;
        this.weapon_level = weapon_level;
        this.weapon_type = weapon_type;
        this.Tooltip_width = 0;
        this.Tooltip_height = 0;
        this.Tooltip_x = 0;
        this.Tooltip_y = 0;
        this.weapon_attribute = weaponAttributeAttack;
    }

    //属性增加
    public WeaponItem addAttribute(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
                                   String key, double value) {
        net.minecraft.world.entity.ai.attributes.AttributeModifier modifier =
                new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        stableUUID(key),                      // 稳定 UUID
                        Immortal.MODID + ":" + key,          // 仅用于显示/调试的名称
                        value,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION
                );
        this.attributeModifiers.put(attribute, modifier);
        return this;
    }

    // ===== 基础属性增加（同 ADDITION，但放在 base 集合里，按你原本的区分）=====
    public WeaponItem addAttribute_base(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
                                        String key, double value) {
        net.minecraft.world.entity.ai.attributes.AttributeModifier modifier =
                new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        stableUUID(key),
                        Immortal.MODID + ":" + key,
                        value,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION
                );
        this.attributeModifiers_base.put(attribute, modifier);
        return this;
    }

    // ===== 乘以基础值（MULTIPLY_BASE）=====
    public WeaponItem addAttribute_multiply_base(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
                                                 String key, double value) {
        net.minecraft.world.entity.ai.attributes.AttributeModifier modifier =
                new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        stableUUID(key),
                        Immortal.MODID + ":" + key,
                        value,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE
                );
        this.attributeModifiers.put(attribute, modifier); // <-- 修正：放入 _base
        return this;
    }

    // 链式添加自定义信息
    public WeaponItem addSkillInfo_passive(String info) {
        this.SkillInfo_passive.add(info);
        return this;
    }
    public WeaponItem addSkillInfo_active(String info) {
        this.SkillInfo_active.add(info);
        return this;
    }
    public WeaponItem SkillInfo_Attached(String info) {
        this.SkillInfo_Attached.add(info);
        return this;
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        applyDefaultsIfMissing(p_41404_);

        super.inventoryTick(p_41404_,p_41405_, p_41406_, p_41407_, p_41408_);
    }
    @Override
    public com.google.common.collect.Multimap<net.minecraft.world.entity.ai.attributes.Attribute, net.minecraft.world.entity.ai.attributes.AttributeModifier>
    getDefaultAttributeModifiers(net.minecraft.world.entity.EquipmentSlot slot) {
        if (slot != net.minecraft.world.entity.EquipmentSlot.MAINHAND) {
            return super.getDefaultAttributeModifiers(slot);
        }

        com.google.common.collect.ImmutableMultimap.Builder<
                net.minecraft.world.entity.ai.attributes.Attribute,
                net.minecraft.world.entity.ai.attributes.AttributeModifier> builder = com.google.common.collect.ImmutableMultimap.builder();

        // this.attributeModifiers: 你现在是 Map/Multimap<Holder<Attribute>, AttributeModifier>
        for (java.util.Map.Entry<net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>,
                net.minecraft.world.entity.ai.attributes.AttributeModifier> e : this.attributeModifiers.entries()) {
            builder.put(e.getKey().value(), e.getValue()); // Holder -> Attribute
        }
        for (java.util.Map.Entry<net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>,
                net.minecraft.world.entity.ai.attributes.AttributeModifier> e : this.attributeModifiers_base.entries()) {
            builder.put(e.getKey().value(), e.getValue()); // Holder -> Attribute
        }
        return builder.build();
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.empty();
                //super.getName(stack);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,
                () -> () -> consumer.accept(new WeaponClientExtension(this)));
    }
    @Override
    public Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        return DistExecutor.unsafeRunForDist(
                () -> () -> (new WeaponClientExtension(this)).getTooltipImage(stack),
                () -> Optional::empty
        );
    }
    // 强化
    public static int getEnhanceLevel(ItemStack s){ return getNonNegativeInt(s, ENHANCE_LEVEL, 0); }
    public static void setEnhanceLevel(ItemStack s, int v){ setInt(s, ENHANCE_LEVEL, Math.max(0, v)); }
    public static void addEnhanceLevel(ItemStack s){ addIntClampMin(s, ENHANCE_LEVEL, 1, 0); }

    // 锻造(100倍存储)
    public static int getForgingLevel(ItemStack s){ return getNonNegativeInt(s, FORGING_LEVEL, 0); }
    public static void setForgingLevel(ItemStack s, int v){ setInt(s, FORGING_LEVEL, Math.max(0, v)); }
    public static void addForgingLevel(ItemStack s){ addIntClampMin(s, FORGING_LEVEL, 1, 0); }
    // 锻造暴击率(100倍存储)
    public static int getForgingNum_1(ItemStack s){ return getNonNegativeInt(s, FORGING_NUM_1, 0); }
    public static void setForgingNum_1(ItemStack s,int v){ setInt(s, FORGING_LEVEL, Math.max(0, v)); }
    public static void addForgingNum_1(ItemStack stack,int add_value) {
        setInt(stack,FORGING_NUM_1, getForgingNum_1(stack)+add_value);
    }
    public static void setForgingNum1(ItemStack s, int v){ setInt(s, FORGING_NUM_1, Math.max(0, v)); }
    // 锻造暴击伤害(100倍存储)
    public static int getForgingNum_2(ItemStack s){ return getNonNegativeInt(s, FORGING_NUM_2, 0); }
    public static void setForgingNum_2(ItemStack s,int v){ setInt(s, FORGING_LEVEL, Math.max(0, v)); }
    public static void addForgingNum_2(ItemStack stack,int add_value) {
        setInt(stack,FORGING_NUM_2, getForgingNum_2(stack)+add_value);
    }
    public static void setForgingNum2(ItemStack s, int v){ setInt(s, FORGING_NUM_2, Math.max(0, v)); }

    // 镀金与符文
    public static int getGildedLevel(ItemStack s){ return getNonNegativeInt(s, GILDED_LEVEL, 0); }
    public static void setGildedLevel(ItemStack s, int v){ setInt(s, GILDED_LEVEL, Math.max(0, v)); }
    public static void addGildedLevel(ItemStack s){ addIntClampMin(s, GILDED_LEVEL, 1, 0); }

    public static int getAttrRune(ItemStack s){ return getNonNegativeInt(s, ATTACHED_ATTRIBUTE_RUNE, 0); }
    public static void setAttrRune(ItemStack s, int v){ setInt(s, ATTACHED_ATTRIBUTE_RUNE, Math.max(0, v)); }
    public static void addAttrRune(ItemStack s){ setInt(s, ATTACHED_ATTRIBUTE_RUNE, getAttrRune(s)+1); }
    public static int getAttrRuneMax(ItemStack s){ return getNonNegativeInt(s, ATTACHED_ATTRIBUTE_RUNE_MAX, 0); }
    public static void setAttrRuneMax(ItemStack s, int v){ setInt(s, ATTACHED_ATTRIBUTE_RUNE_MAX, Math.max(0, v)); }
    public static void addAttrRuneMax(ItemStack s){ setInt(s, ATTACHED_ATTRIBUTE_RUNE_MAX, getAttrRuneMax(s)+1); }

    public static int getSkillRune(ItemStack s){ return getNonNegativeInt(s, ATTACHED_SKILL_RUNE, 0); }
    public static void setSkillRune(ItemStack s, int v){ setInt(s, ATTACHED_SKILL_RUNE, Math.max(0, v)); }
    public static void addSkillRune(ItemStack s){ setInt(s, ATTACHED_ATTRIBUTE_RUNE_MAX, getSkillRune(s)+1); }
    public static int getSkillRuneMax(ItemStack s){ return getNonNegativeInt(s, ATTACHED_SKILL_RUNE_MAX, 0); }
    public static void setSkillRuneMax(ItemStack s, int v){ setInt(s, ATTACHED_SKILL_RUNE_MAX, Math.max(0, v)); }
    public static void addSkillRuneMax(ItemStack s){ setInt(s, ATTACHED_ATTRIBUTE_RUNE_MAX, getSkillRuneMax(s)+1); }

    //*nbt数据同步*/
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        applyDefaults(stack);
        return stack;
    }
    // 合成时附加默认 NBT
    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
        applyDefaultsIfMissing(stack);
    }
    // 默认值写入 NBT
    private void applyDefaults(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(ENHANCE_LEVEL, defaults.enhanceLevel);
        tag.putInt("HideFlags",2);
        //ListTag lore = new ListTag();lore.add(StringTag.valueOf("123"));
        //CompoundTag tag_display = new CompoundTag();tag_display.put("Lore",lore);
        //tag.put("display",tag_display);
        tag.putInt(FORGING_LEVEL, defaults.forgingLevel);
        tag.putInt(FORGING_NUM_1, defaults.forgingNum1);
        tag.putInt(FORGING_NUM_2, defaults.forgingNum2);
        tag.putInt(GILDED_LEVEL, defaults.gildedLevel);
        tag.putInt(ATTACHED_ATTRIBUTE_RUNE, defaults.attrRune);
        tag.putInt(ATTACHED_ATTRIBUTE_RUNE_MAX, defaults.attrRuneMax);
        tag.putInt(ATTACHED_SKILL_RUNE, defaults.skillRune);
        tag.putInt(ATTACHED_SKILL_RUNE_MAX, defaults.skillRuneMax);
    }
    // 仅当缺失时写入默认值
    private void applyDefaultsIfMissing(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(ENHANCE_LEVEL)) tag.putInt(ENHANCE_LEVEL, defaults.enhanceLevel);
        if (!tag.contains("HideFlags")) tag.putInt("HideFlags", 2);
        //ListTag lore = new ListTag();lore.add(StringTag.valueOf("123"));
        //CompoundTag tag_display = new CompoundTag();tag_display.put("Lore",lore);
        //if (!tag.contains("display")) tag.put("display",tag_display);
        if (!tag.contains(FORGING_LEVEL)) tag.putInt(FORGING_LEVEL, defaults.forgingLevel);
        if (!tag.contains(FORGING_NUM_1)) tag.putInt(FORGING_NUM_1, defaults.forgingNum1);
        if (!tag.contains(FORGING_NUM_2)) tag.putInt(FORGING_NUM_2, defaults.forgingNum2);
        if (!tag.contains(GILDED_LEVEL)) tag.putInt(GILDED_LEVEL, defaults.gildedLevel);
        if (!tag.contains(ATTACHED_ATTRIBUTE_RUNE)) tag.putInt(ATTACHED_ATTRIBUTE_RUNE, defaults.attrRune);
        if (!tag.contains(ATTACHED_ATTRIBUTE_RUNE_MAX)) tag.putInt(ATTACHED_ATTRIBUTE_RUNE_MAX, defaults.attrRuneMax);
        if (!tag.contains(ATTACHED_SKILL_RUNE)) tag.putInt(ATTACHED_SKILL_RUNE, defaults.skillRune);
        if (!tag.contains(ATTACHED_SKILL_RUNE_MAX)) tag.putInt(ATTACHED_SKILL_RUNE_MAX, defaults.skillRuneMax);
    }

    public static class WeaponItemProperties extends Item.Properties {
        int maxStackSize = 64;
        int maxDamage;
        int enhanceLevel = 0;
        int forgingLevel = 0;
        int forgingNum1  = 0;
        int forgingNum2  = 0;
        int gildedLevel  = 0;
        int attrRune     = 0;
        int attrRuneMax  = 2;
        int skillRune    = 0;
        int skillRuneMax = 2;
        /*
        // 强化/锻造
        public static final String ENHANCE_LEVEL = "immortal.enhance_level";
        public static final String FORGING_LEVEL = "immortal.forging_level";
        public static final String FORGING_NUM_1  = "immortal.forging_num_1"; // 暴击
        public static final String FORGING_NUM_2  = "immortal.forging_num_2"; // 伤害

        // 镀金 & 符文
        public static final String GILDED_LEVEL               = "immortal.gilded_level";
        public static final String ATTACHED_ATTRIBUTE_RUNE    = "immortal.att_attr_rune";
        public static final String ATTACHED_ATTRIBUTE_RUNE_MAX= "immortal.att_attr_rune_max";
        public static final String ATTACHED_SKILL_RUNE        = "immortal.att_skill_rune";
        public static final String ATTACHED_SKILL_RUNE_MAX    = "immortal.att_skill_rune_max";
         */
        public WeaponItemProperties() {
            super();
        }

        // —— 可链式配置（保留你原来的 API 语义）——
        public WeaponItemProperties AttrRuneMAX(int num) {
            this.attrRuneMax = Math.max(0, num);
            return this;
        }
        public WeaponItemProperties SkillRuneMAX(int num) {
            this.skillRuneMax = Math.max(0, num);
            return this;
        }
        /*
        @Override
        public @NotNull WeaponItemProperties stacksTo(int p_41488_) {
            if (this.maxDamage > 0) {
                throw new RuntimeException("Unable to have damage AND stack.");
            } else {
                this.maxStackSize = p_41488_;
                return this;
            }
        }
         */
    }
}
