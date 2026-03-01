package com.tearhpi.immortal.attribute;

import com.tearhpi.immortal.Immortal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Immortal.MODID);
    //通用
    //自定义最大血量
    public static final RegistryObject<Attribute> IMMORTAL_MAX_HEALTH = ATTRIBUTES.register("max_health", () ->
            new RangedAttribute("attribute.name.generic.immortal_max_health", 20.0D, 1.0D, 2147483647.0D)
                    .setSyncable(true)
    );
    //自定义攻击
    public static final RegistryObject<Attribute> IMMORTAL_ATTACK_DAMAGE = ATTRIBUTES.register("attack_damage", () ->
            new RangedAttribute("attribute.name.generic.immortal_attack_damage", 0.0D, 0.0D, 2147483647.0D)
                    .setSyncable(true)
    );
    //自定义防御力
    public static final RegistryObject<Attribute> IMMORTAL_DEFENSE = ATTRIBUTES.register("immortal_defense", () ->
            new RangedAttribute("attribute.name.generic.immortal_defense", 0.0D, 0.0D, 2147483647.0D)
                    .setSyncable(true)
    );
    //自定义闪避率
    public static final RegistryObject<Attribute> IMMORTAL_DODGE = ATTRIBUTES.register("immortal_dodge", () ->
            new RangedAttribute("attribute.name.generic.immortal_dodge", 0.0D, 0.0D, 1.0D)
                    .setSyncable(true)
    );
    //元素抗性
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_RESISTANCE_FIRE = create_EResistance_Attribute("fire");
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_RESISTANCE_WATER = create_EResistance_Attribute("water");
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_RESISTANCE_EARTH = create_EResistance_Attribute("earth");
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_RESISTANCE_AIR = create_EResistance_Attribute("air");
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_RESISTANCE_LIGHT = create_EResistance_Attribute("light");
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_RESISTANCE_DARKNESS = create_EResistance_Attribute("darkness");
    //元素类抗性便捷注册
    public static RegistryObject<Attribute> create_EResistance_Attribute(String name) {
        String name1 = "element_resistance_" + name;
        String name2 = "attribute.name.generic.element_resistance_" + name;
        return ATTRIBUTES.register(name1, () ->
                new RangedAttribute(name2, 0.0D, 0.0D, 1.0D)
                        .setSyncable(true)
        );
    }

    //玩家
    //法力值最大值
    public static final RegistryObject<Attribute> IMMORTAL_MAGIC_POINT_MAX = ATTRIBUTES.register("magic_point_max", () ->
            new RangedAttribute("attribute.name.player.magic_point", 100, 0, 2147483647)
                    .setSyncable(true)
    );
    //暴击率
    public static final RegistryObject<Attribute> IMMORTAL_CRITICAL_CHANCE = ATTRIBUTES.register("critical_chance", () ->
            new RangedAttribute("attribute.name.player.critical_chance", 0.05D, 0.0D, 1.0D)
                    .setSyncable(true)
    );
    //暴击伤害
    public static final RegistryObject<Attribute> IMMORTAL_CRITICAL_DAMAGE = ATTRIBUTES.register("critical_damage", () ->
            new RangedAttribute("attribute.name.player.critical_damage", 1.5D, 1.0D, 10.0D)
                    .setSyncable(true)
    );
    //无视防御
    public static final RegistryObject<Attribute> IMMORTAL_IGNORE_DEFENSE = ATTRIBUTES.register("ignore_defense", () ->
            new RangedAttribute("attribute.name.player.ignore_defense", 0.0D, 0.0D, 2147483647.0D)
                    .setSyncable(true)
    );
    //额外倍率
    public static final RegistryObject<Attribute> IMMORTAL_EXTRA_MDAMAGE = ATTRIBUTES.register("extra_mdamage", () ->
            new RangedAttribute("attribute.name.player.extra_mdamage", 1.0D, 0.0D, 2147483647.0D)
                    .setSyncable(true)
    );
    //元素倍率-火
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_MDAMAGE_FIRE = create_Mdamage_Attribute("fire");
    //元素倍率-水
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_MDAMAGE_WATER = create_Mdamage_Attribute("water");
    //元素倍率-土
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_MDAMAGE_EARTH = create_Mdamage_Attribute("earth");
    //元素倍率-气
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_MDAMAGE_AIR = create_Mdamage_Attribute("air");
    //元素倍率-光
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_MDAMAGE_LIGHT = create_Mdamage_Attribute("light");
    //元素倍率-暗
    public static final RegistryObject<Attribute> IMMORTAL_ELEMENT_MDAMAGE_DARKNESS = create_Mdamage_Attribute("darkness");
    //元素类伤害便捷注册
    public static RegistryObject<Attribute> create_Mdamage_Attribute(String name) {
        String name1 = "element_mdamage_" + name;
        String name2 = "attribute.name.player.element_mdamage_" + name;
        return ATTRIBUTES.register(name1, () ->
                new RangedAttribute(name2, 1.0D, 0.0D, 2147483647.0D)
                        .setSyncable(true)
        );
    }
    //最终加伤
    public static final RegistryObject<Attribute> IMMORTAL_FINAL_DAMAGE_ADD = ATTRIBUTES.register("final_damage_add", () ->
            new RangedAttribute("attribute.name.player.final_damage_add", 0.0D, 0.0D, 2147483647.0D)
                    .setSyncable(true)
    );
    //额外减伤
    public static final RegistryObject<Attribute> IMMORTAL_PLAYER_EXTRA_PROTECTION = ATTRIBUTES.register("player_extra_protection", () ->
            new RangedAttribute("attribute.name.player.player_extra_protection", 0.0D, -1.0D, 1.0D)
                    .setSyncable(true)
    );
    //异常抗性
    public static final RegistryObject<Attribute> IMMORTAL_PLAYER_DEBUFF_RESISTANCE = ATTRIBUTES.register("player_debuff_resistance", () ->
            new RangedAttribute("attribute.name.player.player_debuff_resistance", 0.0D, 0.0D, 1.0D)
                    .setSyncable(true)
    );
    //技能冷却减免
    public static final RegistryObject<Attribute> IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE = ATTRIBUTES.register("skill_cooldown_immune", () ->
            new RangedAttribute("attribute.name.player.skill_cooldown_immune", 0.0D, 0.0D, 1.0D)
                    .setSyncable(true)
    );
    //技能范围倍率
    public static final RegistryObject<Attribute> IMMORTAL_PLAYER_SKILL_RANGE = ATTRIBUTES.register("skill_range", () ->
            new RangedAttribute("attribute.name.player.skill_range", 1.0D, 1.0D, 10.0D)
                    .setSyncable(true)
    );
    //基础增益比率
    public static final RegistryObject<Attribute> IMMORTAL_PLAYER_BUFF_AMOUNT = ATTRIBUTES.register("buff_amount", () ->
            new RangedAttribute("attribute.name.player.buff_amount", 0.0D, 0.0D, 1000000.0D)
                    .setSyncable(true)
    );

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}