package com.tearhpi.immortal.item.item_register;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.item.custom.CustomArmorItem;
import com.tearhpi.immortal.item.custom.armor.Test2ArmorItem;
import com.tearhpi.immortal.item.custom.armor.TestArmorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ArmorItemRegister {
    public static void register() {}
    public static final RegistryObject<Item> TestArmorHelmet = ModItems.ITEMS.register("test_armor_helmet", () -> new TestArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.HELMET,8,1,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_1",300)
            .addSkillInfo_suit(0,"tooltips.armor.test_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_3")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_4")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_5")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_7")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_8")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_9")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_10")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_11")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_12"));
    public static final RegistryObject<Item> TestArmorChestplate = ModItems.ITEMS.register("test_armor_chestplate", () -> new TestArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.CHESTPLATE,8,1,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_2",400)
            .addSkillInfo_passive("tooltips.skill.test_item_1.1")
            .addSkillInfo_passive("tooltips.skill.test_item_1.2")
            .addSkillInfo_active("tooltips.skill.test_item_1.3")
            .addSkillInfo_active("tooltips.skill.test_item_1.4")
            .addSkillInfo_suit(0,"tooltips.armor.test_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_3")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_4")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_5")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_7")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_8")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_9")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_10")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_11")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_12"));
    public static final RegistryObject<Item> TestArmorLeggings = ModItems.ITEMS.register("test_armor_leggings", () -> new TestArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.LEGGINGS,8,1,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_3",300)
            .addSkillInfo_suit(0,"tooltips.armor.test_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_3")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_4")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_5")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_7")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_8")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_9")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_10")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_11")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_12"));
    public static final RegistryObject<Item> TestArmorBoots = ModItems.ITEMS.register("test_armor_boots", () -> new TestArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.BOOTS,8,1,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_4",200)
            .addSkillInfo_suit(0,"tooltips.armor.test_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test_armor.suit_lore_3")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_4")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_5")
            .addSkillInfo_suit(2,"tooltips.armor.test_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_7")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_8")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_9")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_10")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_11")
            .addSkillInfo_suit(3,"tooltips.armor.test_armor.suit_lore_12"));
    public static final RegistryObject<Item> Test2ArmorHelmet = ModItems.ITEMS.register("test2_armor_helmet", () -> new Test2ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.HELMET,5,2,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_1",200)
            .addSkillInfo_suit(0,"tooltips.armor.test2_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_3")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_4")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_5")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_7"));
    public static final RegistryObject<Item> Test2ArmorChestplate = ModItems.ITEMS.register("test2_armor_chestplate", () -> new Test2ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.CHESTPLATE,5,2,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_2",300)
            .addSkillInfo_passive("tooltips.skill.test_item_1.1")
            .addSkillInfo_passive("tooltips.skill.test_item_1.2")
            .addSkillInfo_active("tooltips.skill.test_item_1.3")
            .addSkillInfo_active("tooltips.skill.test_item_1.4")
            .addSkillInfo_suit(0,"tooltips.armor.test2_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_3")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_4")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_5")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_7"));
    public static final RegistryObject<Item> Test2ArmorLeggings = ModItems.ITEMS.register("test2_armor_leggings", () -> new Test2ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.LEGGINGS,5,2,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_3",200)
            .addSkillInfo_suit(0,"tooltips.armor.test2_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_3")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_4")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_5")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_7"));
    public static final RegistryObject<Item> Test2ArmorBoots = ModItems.ITEMS.register("test2_armor_boots", () -> new Test2ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.BOOTS,5,2,new CustomArmorItem.ArmorItemProperties(3))
            .addAttribute(ModAttributes.IMMORTAL_DEFENSE.get(),"immortal_defense_4",150)
            .addSkillInfo_suit(0,"tooltips.armor.test2_armor.suit_name")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_1")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_2")
            .addSkillInfo_suit(1,"tooltips.armor.test2_armor.suit_lore_3")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_4")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_5")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_6")
            .addSkillInfo_suit(3,"tooltips.armor.test2_armor.suit_lore_7"));
}
