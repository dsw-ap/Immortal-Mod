package com.tearhpi.immortal.item.item_register;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.item.custom.WandItem;
import com.tearhpi.immortal.item.custom.Weapon.*;
import com.tearhpi.immortal.item.custom.WeaponItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WeaponRegister {
    public static void register() {}
    //0-1勇者之杖
    public static final RegistryObject<Item> Weapon_0_1 = ModItems.ITEMS.register("weapon_0_1",
            () -> new WandItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.NONE)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",5)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.6));
    //1-1火术之杖
    public static final RegistryObject<Item> Weapon_1_1 = ModItems.ITEMS.register("weapon_1_1",
            () -> new Weapon_1_1((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.FIRE)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",5)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.6)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_1_1.1").addSkillInfo_passive("tooltips.skill.weapon_1_1.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_1_1.3").addSkillInfo_passive("tooltips.skill.weapon_1_1.4"));
    //1-2熄灭的星
    public static final RegistryObject<Item> Weapon_1_2 = ModItems.ITEMS.register("weapon_1_2",
            () -> new Weapon_1_2((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.DARKNESS)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",8)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.9)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_1_2.1").addSkillInfo_passive("tooltips.skill.weapon_1_2.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_1_2.3").addSkillInfo_passive("tooltips.skill.weapon_1_2.4"));
    //1-3星辰法杖
    public static final RegistryObject<Item> Weapon_1_3 = ModItems.ITEMS.register("weapon_1_3",
            () -> new Weapon_1_3((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.LIGHT)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",5)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.6)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_1_3.1").addSkillInfo_passive("tooltips.skill.weapon_1_3.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_1_3.3").addSkillInfo_passive("tooltips.skill.weapon_1_3.4")
                    .addSkillInfo_passive("tooltips.skill.weapon_1_3.5"));
    //1-4呼啸者
    public static final RegistryObject<Item> Weapon_1_4 = ModItems.ITEMS.register("weapon_1_4",
            () -> new Weapon_1_4((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),2,2, WeaponAttributeAttack.AIR)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",13)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.6)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_1_4.1")
                    .addSkillInfo_passive("tooltips.skill.weapon_1_4.2").addSkillInfo_passive("tooltips.skill.weapon_1_4.3")
                    .addSkillInfo_passive("tooltips.skill.weapon_1_4.4").addSkillInfo_passive("tooltips.skill.weapon_1_4.5"));
    //2-1泉眼无声
    public static final RegistryObject<Item> Weapon_2_1 = ModItems.ITEMS.register("weapon_2_1",
            () -> new Weapon_2_1((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.WATER)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.8)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_2_1.1").addSkillInfo_passive("tooltips.skill.weapon_2_1.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_2_1.3").addSkillInfo_passive("tooltips.skill.weapon_2_1.4"));
    //2-2页岩记忆
    public static final RegistryObject<Item> Weapon_2_2 = ModItems.ITEMS.register("weapon_2_2",
            () -> new Weapon_2_2((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.EARTH)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",14)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-3.1)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_2_2.1").addSkillInfo_passive("tooltips.skill.weapon_2_2.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_2_2.3").addSkillInfo_passive("tooltips.skill.weapon_2_2.4"));
    //2-3蒲公英之歌
    public static final RegistryObject<Item> Weapon_2_3 = ModItems.ITEMS.register("weapon_2_3",
            () -> new Weapon_2_3((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,2, WeaponAttributeAttack.AIR)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",8)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.6)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.35)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",5)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_2_3.1").addSkillInfo_passive("tooltips.skill.weapon_2_3.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_2_3.3").addSkillInfo_passive("tooltips.skill.weapon_2_3.4"));
    //2-4无垠吞噬
    public static final RegistryObject<Item> Weapon_2_4 = ModItems.ITEMS.register("weapon_2_4",
            () -> new Weapon_2_4((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),2,2, WeaponAttributeAttack.DARKNESS)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",17)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.7)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.1)
                    .addSkillInfo_passive("tooltips.skill.weapon_2_4.1").addSkillInfo_passive("tooltips.skill.weapon_2_4.2")
                    .addSkillInfo_passive("tooltips.skill.weapon_2_4.3").addSkillInfo_passive("tooltips.skill.weapon_2_4.4")
                    .addSkillInfo_passive("tooltips.skill.weapon_2_4.5"));














    //测试物品注册
    public static final RegistryObject<Item> TestItem = ModItems.ITEMS.register("test_item",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),1,1, WeaponAttributeAttack.FIRE)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get(),"immortal_skill_cooldown_immune",0.8)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get(),"immortal_skill_range",0.5)
                    .addSkillInfo_passive("tooltips.skill.test_item_1.1")
                    .addSkillInfo_passive("tooltips.skill.test_item_1.2")
                    .addSkillInfo_active("tooltips.skill.test_item_1.3")
                    .addSkillInfo_active("tooltips.skill.test_item_1.4"));
    public static final RegistryObject<Item> TestItem2 = ModItems.ITEMS.register("test_item_2",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),2,1, WeaponAttributeAttack.WATER)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get(),"immortal_skill_cooldown_immune",0.95)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get(),"immortal_skill_range",0.5));
    public static final RegistryObject<Item> TestItem3 = ModItems.ITEMS.register("test_item_3",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),3,1, WeaponAttributeAttack.AIR)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get(),"immortal_skill_cooldown_immune",0.95)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get(),"immortal_skill_range",0.5));
    public static final RegistryObject<Item> TestItem4 = ModItems.ITEMS.register("test_item_4",
            () -> new WandItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),4,2, WeaponAttributeAttack.FIRE)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5));
    public static final RegistryObject<Item> TestItem5 = ModItems.ITEMS.register("test_item_5",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),5,1, WeaponAttributeAttack.EARTH)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get(),"immortal_skill_cooldown_immune",0.95)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get(),"immortal_skill_range",0.5));
    public static final RegistryObject<Item> TestItem6 = ModItems.ITEMS.register("test_item_6",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),6,1, WeaponAttributeAttack.LIGHT)
            .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
            .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
            //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
            .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
            .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
            .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
            .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
            .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
            .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
            .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
            .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
            .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
            .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
            .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
            .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
            .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get(),"immortal_skill_cooldown_immune",0.95)
            .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get(),"immortal_skill_range",0.5));
    public static final RegistryObject<Item> TestItem7 = ModItems.ITEMS.register("test_item_7",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().AttrRuneMAX(3).SkillRuneMAX(3).stacksTo(1),7,1, WeaponAttributeAttack.AIR)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
                    .addSkillInfo_passive("tooltips.skill.test_item_1.1")
                    .addSkillInfo_passive("tooltips.skill.test_item_1.2")
                    .addSkillInfo_active("tooltips.skill.test_item_1.3")
                    .addSkillInfo_active("tooltips.skill.test_item_1.4"));
    public static final RegistryObject<Item> TestItem8 = ModItems.ITEMS.register("test_item_8",
            () -> new WeaponItem((WeaponItem.WeaponItemProperties) new WeaponItem.WeaponItemProperties().stacksTo(1),8,1, WeaponAttributeAttack.DARKNESS)
                    .addAttribute_base(ModAttributes.IMMORTAL_ATTACK_DAMAGE.getHolder().get(),"immortal_attack_damage",10)
                    .addAttribute_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.ATTACK_SPEED).get(),"immortal_attack_speed",-2.4)
                    //.addAttribute_base(Attributes.ENTITY_INTERACTION_RANGE,"immortal_entity_interaction_range",2)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_CHANCE.getHolder().get(),"immortal_critical_change",0.05)
                    .addAttribute(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.getHolder().get(),"immortal_critical_damage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_IGNORE_DEFENSE.getHolder().get(),"immortal_ignore_defense",50)
                    .addAttribute(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.getHolder().get(),"immortal_extra_mdamage",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.getHolder().get(),"immortal_final_damage_add",10)
                    .addAttribute_multiply_base(ForgeRegistries.ATTRIBUTES.getHolder(Attributes.MOVEMENT_SPEED).get(),"immortal_movement_speed",0.2)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get(),"immortal_element_mdamage_fire",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get(),"immortal_element_mdamage_water",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get(),"immortal_element_mdamage_earth",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get(),"immortal_element_mdamage_air",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get(),"immortal_element_mdamage_light",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get(),"immortal_element_mdamage_darkness",0.5)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get(),"immortal_skill_cooldown_immune",0.95)
                    .addAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get(),"immortal_skill_range",0.5));
}
