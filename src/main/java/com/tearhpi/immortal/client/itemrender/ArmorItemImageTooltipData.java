package com.tearhpi.immortal.client.itemrender;

import com.google.common.collect.Multimap;
import com.tearhpi.immortal.client.textrender.StarParticle;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public record ArmorItemImageTooltipData(ItemStack stack, Component originalName,
                                        int whole_width, int whole_height,
                                        int whole_x, int whole_y,
                                        int armor_level, int armor_suit_number,
                                        Multimap<Attribute, AttributeModifier> attributeModifiers,
                                        LinkedList<String> armor_skill_passive,
                                        LinkedList<String> armor_skill_active,
                                        LinkedList<String> armor_skill_attach,
                                        LinkedList<LinkedList<String>> armor_skill_suit,
                                        int attached_attribute_rune_max, int attached_attribute_rune,
                                        List<StarParticle> stars) implements TooltipComponent {
}
/*
return Optional.of(new ArmorItemImageTooltipData(
                           stack,
                   name,// 使用原版名称获取逻辑
                   Tooltip_width,
                   Tooltip_height,
                   Tooltip_x,
                   Tooltip_y,
                   armor_level,
                   armor_suit_number,
                   attributeModifiers,
                   SkillInfo_passive,
                   SkillInfo_active,
                   SkillInfo_attach,
                   SkillInfo_suit,
                   getAttrRuneMax(stack),getAttrRune(stack),
stars
        ));

 */