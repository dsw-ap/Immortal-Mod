package com.tearhpi.immortal.effect;

import com.tearhpi.immortal.Immortal;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumSet;

public class _ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Immortal.MODID);
    /**敌人侧*/
    //灼烧
    public static final RegistryObject<MobEffect> FIRING_EFFECT = MOB_EFFECTS.register("firing",
            ()->new FiringEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/10));
    //湮灭
    public static final RegistryObject<MobEffect> ANNIHILATE_EFFECT = MOB_EFFECTS.register("annihilate",
            ()->new AnnihilateEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/4));
    //禁锢
    public static final RegistryObject<MobEffect> IMPRISIONED_EFFECT = MOB_EFFECTS.register("imprisioned",
            ()->new ImprisonEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/10));
    //侵蚀
    public static final RegistryObject<MobEffect> EROSION_EFFECT = MOB_EFFECTS.register("erosion",
            ()->new ErosionEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/10));
    //重伤
    public static final RegistryObject<MobEffect> INJURY_EFFECT = MOB_EFFECTS.register("injury",
            ()->new InjuryEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/10));
    //沉默
    public static final RegistryObject<MobEffect> MUTE_EFFECT = MOB_EFFECTS.register("mute",
            ()->new MuteEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/10));
    //噩梦
    public static final RegistryObject<MobEffect> NIGHTMARE_EFFECT = MOB_EFFECTS.register("nightmare",
            ()->new NightMareEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy), /*weight*/10));
    //原型诅咒
    public static final RegistryObject<MobEffect> RAWCURSE_EFFECT = MOB_EFFECTS.register("rawcurse",
            ()->new RawCurseEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    //Skill15_减伤
    public static final RegistryObject<MobEffect> SKILL15_EFFECT_REMOVE_DEF = MOB_EFFECTS.register("skill15_effect_remove_def",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    //Skill18_加伤
    public static final RegistryObject<MobEffect> SKILL18_ADD_DMG_EFFECT = MOB_EFFECTS.register("skill18_add_dmg",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    public static final RegistryObject<MobEffect> SKILL18_ADD_DMG_EFFECT_Spec = MOB_EFFECTS.register("skill18_add_dmg_spec",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    //Skill19_加水气值
    public static final RegistryObject<MobEffect> SKILL19_ADD_WATER_AMOUNT = MOB_EFFECTS.register("skill19",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    //Skill54_光之侵蚀
    public static final RegistryObject<MobEffect> SKILL54_LIGHT_EROSION = MOB_EFFECTS.register("skill54_light_erosion",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    //Skill63_混沌催化
    public static final RegistryObject<MobEffect> SKILL63_CHAOS_CATALYST = MOB_EFFECTS.register("skill63_chaos_catalyst",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));
    //Skill66_蔽日暗影
    public static final RegistryObject<MobEffect> SKILL66_DEBUFF = MOB_EFFECTS.register("skill66_debuff",
            ()->new NullEffect(MobEffectCategory.NEUTRAL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Enemy_Spec), /*weight*/0));


    /**玩家侧*/
    //Skill12
    public static final RegistryObject<MobEffect> SKILL12_EFFECT = MOB_EFFECTS.register("skill12_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill15_加伤
    public static final RegistryObject<MobEffect> SKILL15_EFFECT_ADD_DMG = MOB_EFFECTS.register("skill15_effect_add_dmg",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill30_加伤
    public static final RegistryObject<MobEffect> SKILL30_EFFECT_ADD_DMG = MOB_EFFECTS.register("skill30_effect_add_dmg",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill34_加伤
    public static final RegistryObject<MobEffect> SKILL34_EFFECT = MOB_EFFECTS.register("skill34_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill40_减伤
    public static final RegistryObject<MobEffect> SKILL40_EFFECT = MOB_EFFECTS.register("skill40_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill44_本体
    public static final RegistryObject<MobEffect> SKILL44_EFFECT_SELF = MOB_EFFECTS.register("skill44_effect_self",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill44_附属
    public static final RegistryObject<MobEffect> SKILL44_EFFECT_OTHERS = MOB_EFFECTS.register("skill44_effect_others",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill45_本体
    public static final RegistryObject<MobEffect> SKILL45_EFFECT_SELF = MOB_EFFECTS.register("skill45_effect_self",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill45_附属
    public static final RegistryObject<MobEffect> SKILL45_EFFECT_OTHERS = MOB_EFFECTS.register("skill45_effect_others",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill47_附属
    public static final RegistryObject<MobEffect> SKILL47_EFFECT = MOB_EFFECTS.register("skill47",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill51(奶系buff)
    public static final RegistryObject<MobEffect> SKILL51_BUFF_AMOUNT = MOB_EFFECTS.register("skill51_buff_amount",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill52(急行咒)
    public static final RegistryObject<MobEffect> SKILL52_EFFECT = MOB_EFFECTS.register("skill52_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill55(圣光闪耀)
    public static final RegistryObject<MobEffect> SKILL55_EFFECT = MOB_EFFECTS.register("skill55_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill57(救赎圣阵)
    public static final RegistryObject<MobEffect> SKILL57_EFFECT = MOB_EFFECTS.register("skill57_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Skill59(永恒天国)
    public static final RegistryObject<MobEffect> SKILL59_EFFECT = MOB_EFFECTS.register("skill59_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    public static final RegistryObject<MobEffect> SKILL59_EFFECT_ = MOB_EFFECTS.register("skill59_effect_",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Weapon_1_2
    public static final RegistryObject<MobEffect> WEAPON1_2_EFFECT = MOB_EFFECTS.register("weapon1_2_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Weapon_1_3
    //同源[光]
    public static final RegistryObject<MobEffect> WEAPON1_3_EFFECT = MOB_EFFECTS.register("weapon1_3_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //闪耀
    public static final RegistryObject<MobEffect> WEAPON1_3_EFFECT_ = MOB_EFFECTS.register("weapon1_3_effect_",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Weapon_2_2
    //夯岩
    public static final RegistryObject<MobEffect> WEAPON2_2_EFFECT = MOB_EFFECTS.register("weapon2_2_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //庇护
    public static final RegistryObject<MobEffect> WEAPON2_2_EFFECT_ = MOB_EFFECTS.register("weapon2_2_effect_",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));
    //Weapon_2_4
    //同化
    public static final RegistryObject<MobEffect> WEAPON2_4_EFFECT = MOB_EFFECTS.register("weapon2_4_effect",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/0));


    //禁疗
    public static final RegistryObject<MobEffect> ANTI_HEAL_EFFECT = MOB_EFFECTS.register("anti_heal",
            ()->new AntiHealEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Player), /*weight*/0));
    //失衡
    public static final RegistryObject<MobEffect> UNBALANCE_EFFECT_1 = MOB_EFFECTS.register("unbalance_1",
            ()->new UnBalanceEffect_1(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Player), /*weight*/0));
    public static final RegistryObject<MobEffect> UNBALANCE_EFFECT_2 = MOB_EFFECTS.register("unbalance_2",
            ()->new UnBalanceEffect_2(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Player), /*weight*/0));
    //虚化
    public static final RegistryObject<MobEffect> UNREAL_EFFECT = MOB_EFFECTS.register("unreal",
            ()->new NullEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Player), /*weight*/0));
    //混乱
    public static final RegistryObject<MobEffect> CHAOS_EFFECT = MOB_EFFECTS.register("chaos",
            ()->new ChaosEffect(MobEffectCategory.HARMFUL,0x9932CC, EnumSet.of(_EffectTag.DEBUFF_Player), /*weight*/0));
    //净化
    public static final RegistryObject<MobEffect> PURIFICATION_EFFECT = MOB_EFFECTS.register("purification",
            ()->new NullEffect(MobEffectCategory.BENEFICIAL,0x9932CC, EnumSet.of(_EffectTag.BUFF_Player), /*weight*/3));



    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
