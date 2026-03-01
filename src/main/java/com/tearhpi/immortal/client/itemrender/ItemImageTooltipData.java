package com.tearhpi.immortal.client.itemrender;

import com.google.common.collect.Multimap;
import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.client.textrender.StarParticle;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public record ItemImageTooltipData(ItemStack stack, Component originalName,
                                   int whole_width, int whole_height,
                                   int whole_x, int whole_y,
                                   int weapon_level, int weapon_type,
                                   Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers,
                                   Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers_base,
                                   WeaponAttributeAttack weaponAttributeAttack,
                                   LinkedList<String> weapon_skill_passive,
                                   LinkedList<String> weapon_skill_active,
                                   LinkedList<String> weapon_skill_attached,
                                   int attached_attribute_rune_max, int attached_attribute_rune,
                                   int attached_skill_rune_max, int attached_skill_rune,
                                   int weapon_enhancement, int weapon_forging, int weapon_forging_num_1, int weapon_forging_num_2, int weapon_gilding,
                                   List<StarParticle> stars) implements TooltipComponent {
}
