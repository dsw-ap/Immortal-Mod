package com.tearhpi.immortal.entity;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.*;
import com.tearhpi.immortal.entity.custom.Utils.BezierEntity;
import com.tearhpi.immortal.entity.custom.Weapons.Weapon1_3_Entity;
import com.tearhpi.immortal.entity.custom.skills.*;
import com.tearhpi.immortal.entity.custom.zSpecialWandBullet.WandBullet1_1;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Immortal.MODID);
    //生物-训练人偶
    public static final RegistryObject<EntityType<TrainerDummy>> TRAINER_DUMMY =
            ENTITIES_TYPES.register("trainer_dummy", () -> EntityType.Builder.of(TrainerDummy::new, MobCategory.MISC).sized(0.6f, 1.95f).build("trainer_dummy"));


    //法杖-普通攻击粒子
    public static final RegistryObject<EntityType<WandNormalAttackBullet>> WAND_NORMAL_ATTACK_BULLET =
            ENTITIES_TYPES.register("wand_normal_attack_bullet", () -> EntityType.Builder.of(WandNormalAttackBullet::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("wand_normal_attack_bullet"));
    //法杖-强化攻击粒子
    public static final RegistryObject<EntityType<WandNormalAttackBullet_Enforced>> WAND_NORMAL_ATTACK_BULLET_ENFORCED =
            ENTITIES_TYPES.register("wand_normal_attack_bullet_enforced", () -> EntityType.Builder.of(WandNormalAttackBullet_Enforced::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("wand_normal_attack_bullet_enforced"));
    //武器 1-1
    public static final RegistryObject<EntityType<WandBullet1_1>> WAND_BULLET_1_1 =
            ENTITIES_TYPES.register("wand_bullet_1_1", () -> EntityType.Builder.of(WandBullet1_1::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("wand_bullet_1_1"));
    //武器 1-3
    public static final RegistryObject<EntityType<Weapon1_3_Entity>> WEAPON1_3_ENTITY =
            ENTITIES_TYPES.register("weapon1_3_entity", () -> EntityType.Builder.of(Weapon1_3_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("weapon1_3_entity"));



    //辅助-贝塞尔
    public static final RegistryObject<EntityType<BezierEntity>> BEZIER_ =
            ENTITIES_TYPES.register("bezier_", () -> EntityType.Builder.of(BezierEntity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("bezier_"));

    //技能6-实体
    public static final RegistryObject<EntityType<Skill6_Entity>> SKILL6_ENTITY = ENTITIES_TYPES.register("skill6_entity", () -> EntityType.Builder.of(Skill6_Entity::new, MobCategory.MISC).sized(1.0f, 1.0f).clientTrackingRange(64).updateInterval(1).build("skill6_entity"));
    //技能7-实体
    public static final RegistryObject<EntityType<Skill7_Entity>> SKILL7_ENTITY = ENTITIES_TYPES.register("skill7_entity", () -> EntityType.Builder.of(Skill7_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill7_entity"));
    public static final RegistryObject<EntityType<Skill7_Entity_>> SKILL7_ENTITY_ = ENTITIES_TYPES.register("skill7_entity_", () -> EntityType.Builder.of(Skill7_Entity_::new, MobCategory.MISC).sized(1f, 3.0f).clientTrackingRange(64).updateInterval(1).build("skill7_entity_"));
    //技能8-实体
    public static final RegistryObject<EntityType<Skill8_Entity_>> SKILL8_ENTITY_ = ENTITIES_TYPES.register("skill8_entity_", () -> EntityType.Builder.of(Skill8_Entity_::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill8_entity_"));
    public static final RegistryObject<EntityType<Skill8_Entity>> SKILL8_ENTITY = ENTITIES_TYPES.register("skill8_entity", () -> EntityType.Builder.of(Skill8_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill8_entity"));
    //技能9-实体
    public static final RegistryObject<EntityType<Skill9_Entity>> SKILL9_ENTITY = ENTITIES_TYPES.register("skill9_entity", () -> EntityType.Builder.of(Skill9_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill9_entity"));
    //技能10-实体
    public static final RegistryObject<EntityType<Skill10_Entity>> SKILL10_ENTITY = ENTITIES_TYPES.register("skill10_entity", () -> EntityType.Builder.of(Skill10_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill10_entity"));
    //技能11-实体
    public static final RegistryObject<EntityType<Skill11_Entity>> SKILL11_ENTITY = ENTITIES_TYPES.register("skill11_entity", () -> EntityType.Builder.of(Skill11_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill11_entity"));
    public static final RegistryObject<EntityType<Skill11_Entity_>> SKILL11_ENTITY_ = ENTITIES_TYPES.register("skill11_entity_", () -> EntityType.Builder.of(Skill11_Entity_::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill11_entity_"));
    //技能15-实体
    public static final RegistryObject<EntityType<Skill15_Entity>> SKILL15_ENTITY = ENTITIES_TYPES.register("skill15_entity", () -> EntityType.Builder.of(Skill15_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill15_entity"));
    //技能17-实体
    public static final RegistryObject<EntityType<Skill17_Entity>> SKILL17_ENTITY = ENTITIES_TYPES.register("skill17_entity", () -> EntityType.Builder.of(Skill17_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill17_entity"));
    //技能18-实体
    public static final RegistryObject<EntityType<Skill18_Entity>> SKILL18_ENTITY = ENTITIES_TYPES.register("skill18_entity", () -> EntityType.Builder.of(Skill18_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill18_entity"));
    //技能19-实体
    public static final RegistryObject<EntityType<Skill19_Entity>> SKILL19_ENTITY = ENTITIES_TYPES.register("skill19_entity", () -> EntityType.Builder.of(Skill19_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill19_entity"));
    //技能20-实体
    public static final RegistryObject<EntityType<Skill20_Entity>> SKILL20_ENTITY = ENTITIES_TYPES.register("skill20_entity", () -> EntityType.Builder.of(Skill20_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill20_entity"));
    //技能21-实体
    public static final RegistryObject<EntityType<Skill21_Entity>> SKILL21_ENTITY = ENTITIES_TYPES.register("skill21_entity", () -> EntityType.Builder.of(Skill21_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill21_entity"));
    //技能22-实体
    public static final RegistryObject<EntityType<Skill22_Entity>> SKILL22_ENTITY = ENTITIES_TYPES.register("skill22_entity", () -> EntityType.Builder.of(Skill22_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill22_entity"));
    //技能23-实体
    public static final RegistryObject<EntityType<Skill23_Entity>> SKILL23_ENTITY = ENTITIES_TYPES.register("skill23_entity", () -> EntityType.Builder.of(Skill23_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill23_entity"));
    //技能24-实体
    public static final RegistryObject<EntityType<Skill24_Entity>> SKILL24_ENTITY = ENTITIES_TYPES.register("skill24_entity", () -> EntityType.Builder.of(Skill24_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill24_entity"));
    //技能26-实体
    public static final RegistryObject<EntityType<Skill26_Entity>> SKILL26_ENTITY = ENTITIES_TYPES.register("skill26_entity", () -> EntityType.Builder.of(Skill26_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill26_entity"));
    //技能28-实体
    public static final RegistryObject<EntityType<Skill28_Entity>> SKILL28_ENTITY = ENTITIES_TYPES.register("skill28_entity", () -> EntityType.Builder.of(Skill28_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill28_entity"));
    //技能29-实体
    public static final RegistryObject<EntityType<Skill29_Entity>> SKILL29_ENTITY = ENTITIES_TYPES.register("skill29_entity", () -> EntityType.Builder.of(Skill29_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill29_entity"));
    //技能30-实体
    public static final RegistryObject<EntityType<Skill30_Entity>> SKILL30_ENTITY = ENTITIES_TYPES.register("skill30_entity", () -> EntityType.Builder.of(Skill30_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill30_entity"));
    //技能31-实体
    public static final RegistryObject<EntityType<Skill31_Entity>> SKILL31_ENTITY = ENTITIES_TYPES.register("skill31_entity", () -> EntityType.Builder.of(Skill31_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill31_entity"));
    public static final RegistryObject<EntityType<Skill31_Entity_>> SKILL31_ENTITY_ = ENTITIES_TYPES.register("skill31_entity_", () -> EntityType.Builder.of(Skill31_Entity_::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill31_entity_"));
    //技能32-实体
    public static final RegistryObject<EntityType<Skill32_Entity>> SKILL32_ENTITY = ENTITIES_TYPES.register("skill32_entity", () -> EntityType.Builder.of(Skill32_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill32_entity"));
    //技能33-实体
    public static final RegistryObject<EntityType<Skill33_Entity>> SKILL33_ENTITY = ENTITIES_TYPES.register("skill33_entity", () -> EntityType.Builder.of(Skill33_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill33_entity"));
    //技能34-实体
    public static final RegistryObject<EntityType<Skill34_Entity>> SKILL34_ENTITY = ENTITIES_TYPES.register("skill34_entity", () -> EntityType.Builder.of(Skill34_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill34_entity"));
    //技能35-实体
    public static final RegistryObject<EntityType<Skill35_Entity>> SKILL35_ENTITY = ENTITIES_TYPES.register("skill35_entity", () -> EntityType.Builder.of(Skill35_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill35_entity"));
    //技能37-实体
    public static final RegistryObject<EntityType<Skill37_Entity>> SKILL37_ENTITY = ENTITIES_TYPES.register("skill37_entity", () -> EntityType.Builder.of(Skill37_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill37_entity"));
    //技能39-实体
    public static final RegistryObject<EntityType<Skill39_Entity>> SKILL39_ENTITY = ENTITIES_TYPES.register("skill39_entity", () -> EntityType.Builder.of(Skill39_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill39_entity"));
    //技能40-实体
    public static final RegistryObject<EntityType<Skill40_Entity>> SKILL40_ENTITY = ENTITIES_TYPES.register("skill40_entity", () -> EntityType.Builder.of(Skill40_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill40_entity"));
    //技能41-实体
    public static final RegistryObject<EntityType<Skill41_Entity>> SKILL41_ENTITY = ENTITIES_TYPES.register("skill41_entity", () -> EntityType.Builder.of(Skill41_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill41_entity"));
    //技能42-实体
    public static final RegistryObject<EntityType<Skill42_Entity>> SKILL42_ENTITY = ENTITIES_TYPES.register("skill42_entity", () -> EntityType.Builder.of(Skill42_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill42_entity"));
    //技能43-实体
    public static final RegistryObject<EntityType<Skill43_Entity>> SKILL43_ENTITY = ENTITIES_TYPES.register("skill43_entity", () -> EntityType.Builder.of(Skill43_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill43_entity"));
    //技能44-实体
    public static final RegistryObject<EntityType<Skill44_Entity>> SKILL44_ENTITY = ENTITIES_TYPES.register("skill44_entity", () -> EntityType.Builder.of(Skill44_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill44_entity"));
    //技能45-实体
    public static final RegistryObject<EntityType<Skill45_Entity>> SKILL45_ENTITY = ENTITIES_TYPES.register("skill45_entity", () -> EntityType.Builder.of(Skill45_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill45_entity"));
    //技能46-实体
    //public static final RegistryObject<EntityType<Skill46_Entity>> SKILL46_ENTITY = ENTITIES_TYPES.register("skill46_entity", () -> EntityType.Builder.of(Skill46_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill46_entity"));
    //技能48-实体
    public static final RegistryObject<EntityType<Skill48_Entity>> SKILL48_ENTITY = ENTITIES_TYPES.register("skill48_entity", () -> EntityType.Builder.of(Skill48_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill48_entity"));
    //技能50-实体
    public static final RegistryObject<EntityType<Skill50_Entity>> SKILL50_ENTITY = ENTITIES_TYPES.register("skill50_entity", () -> EntityType.Builder.of(Skill50_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill50_entity"));
    //技能51-实体
    public static final RegistryObject<EntityType<Skill51_Entity>> SKILL51_ENTITY = ENTITIES_TYPES.register("skill51_entity", () -> EntityType.Builder.of(Skill51_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill51_entity"));
    //技能52-实体
    public static final RegistryObject<EntityType<Skill52_Entity>> SKILL52_ENTITY = ENTITIES_TYPES.register("skill52_entity", () -> EntityType.Builder.of(Skill52_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill52_entity"));
    //技能53-实体
    public static final RegistryObject<EntityType<Skill53_Entity>> SKILL53_ENTITY = ENTITIES_TYPES.register("skill53_entity", () -> EntityType.Builder.of(Skill53_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill53_entity"));
    //技能54-实体
    public static final RegistryObject<EntityType<Skill54_Entity>> SKILL54_ENTITY = ENTITIES_TYPES.register("skill54_entity", () -> EntityType.Builder.of(Skill54_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill54_entity"));
    //技能55-实体
    public static final RegistryObject<EntityType<Skill55_Entity>> SKILL55_ENTITY = ENTITIES_TYPES.register("skill55_entity", () -> EntityType.Builder.of(Skill55_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill55_entity"));
    //技能56-实体
    public static final RegistryObject<EntityType<Skill56_Entity>> SKILL56_ENTITY = ENTITIES_TYPES.register("skill56_entity", () -> EntityType.Builder.of(Skill56_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill56_entity"));
    //技能57-实体
    public static final RegistryObject<EntityType<Skill57_Entity>> SKILL57_ENTITY = ENTITIES_TYPES.register("skill57_entity", () -> EntityType.Builder.of(Skill57_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill57_entity"));
    //技能59-实体
    public static final RegistryObject<EntityType<Skill59_Entity>> SKILL59_ENTITY = ENTITIES_TYPES.register("skill59_entity", () -> EntityType.Builder.of(Skill59_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill59_entity"));
    public static final RegistryObject<EntityType<Skill59_Entity_>> SKILL59_ENTITY_ = ENTITIES_TYPES.register("skill59_entity_", () -> EntityType.Builder.of(Skill59_Entity_::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill59_entity_"));
    //技能61-实体
    public static final RegistryObject<EntityType<Skill61_Entity>> SKILL61_ENTITY = ENTITIES_TYPES.register("skill61_entity", () -> EntityType.Builder.of(Skill61_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill61_entity"));
    //技能62-实体
    public static final RegistryObject<EntityType<Skill62_Entity>> SKILL62_ENTITY = ENTITIES_TYPES.register("skill62_entity", () -> EntityType.Builder.of(Skill62_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill62_entity"));
    //技能63-实体
    public static final RegistryObject<EntityType<Skill63_Entity>> SKILL63_ENTITY = ENTITIES_TYPES.register("skill63_entity", () -> EntityType.Builder.of(Skill63_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill63_entity"));
    //技能64-实体
    public static final RegistryObject<EntityType<Skill64_Entity>> SKILL64_ENTITY = ENTITIES_TYPES.register("skill64_entity", () -> EntityType.Builder.of(Skill64_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill64_entity"));
    public static final RegistryObject<EntityType<Skill64_Entity_>> SKILL64_ENTITY_ = ENTITIES_TYPES.register("skill64_entity_", () -> EntityType.Builder.of(Skill64_Entity_::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill64_entity_"));
    //技能65-实体
    public static final RegistryObject<EntityType<Skill65_Entity>> SKILL65_ENTITY = ENTITIES_TYPES.register("skill65_entity", () -> EntityType.Builder.of(Skill65_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill65_entity"));
    //技能66-实体
    public static final RegistryObject<EntityType<Skill66_Entity>> SKILL66_ENTITY = ENTITIES_TYPES.register("skill66_entity", () -> EntityType.Builder.of(Skill66_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill66_entity"));
    //技能68-实体
    public static final RegistryObject<EntityType<Skill68_Entity>> SKILL68_ENTITY = ENTITIES_TYPES.register("skill68_entity", () -> EntityType.Builder.of(Skill68_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill68_entity"));
    //技能69-实体
    public static final RegistryObject<EntityType<Skill69_Entity>> SKILL69_ENTITY = ENTITIES_TYPES.register("skill69_entity", () -> EntityType.Builder.of(Skill69_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill69_entity"));
    //技能70-实体
    public static final RegistryObject<EntityType<Skill70_Entity>> SKILL70_ENTITY = ENTITIES_TYPES.register("skill70_entity", () -> EntityType.Builder.of(Skill70_Entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(64).updateInterval(1).build("skill70_entity"));



    public static void register(IEventBus eventBus) {
        ENTITIES_TYPES.register(eventBus);
    }
}
