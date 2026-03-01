package com.tearhpi.immortal.event.skill;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.item.custom.WandItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Passive {
    // 每 tick 检查
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        Player player = event.player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //净化
        ResourceLocation EFFECT_PURIF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "effect_purif");
        boolean effect_purif = player.hasEffect(_ModEffects.PURIFICATION_EFFECT.get());
        int valpurif = 0;
        if (effect_purif) {
            valpurif = player.getEffect(_ModEffects.PURIFICATION_EFFECT.get()).getAmplifier()+1;
        }
        addAttrWithCondition(player,ModAttributes.IMMORTAL_PLAYER_DEBUFF_RESISTANCE.get(), EFFECT_PURIF,0.1*valpurif,effect_purif);
        /** 特效 */
        //禁锢
        ResourceLocation EFFECT_IMPRISON = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "effect_imprison");
        boolean effect_imprison = player.hasEffect(_ModEffects.IMPRISIONED_EFFECT.get());
        multiplyAttrWithCondition(player,Attributes.MOVEMENT_SPEED, EFFECT_IMPRISON,-5,effect_imprison);
        /** 技能 */
        //冒险之心(Skill_0) 等级每+1,血量上限+2
        ResourceLocation Skill_0_Res = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_0");
        addAttr(player,iImmortalPlayer.getSkill().Skill_0_Level,ModAttributes.IMMORTAL_MAX_HEALTH.get(),Skill_0_Res,2.0);
        //游侠血统(Skill_1) 等级每+1,移动速度+4%;
        ResourceLocation Skill_1_Res = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_1");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_1_Level,Attributes.MOVEMENT_SPEED,Skill_1_Res,0.04);
        //战斗本能(Skill_2) 等级每+1,攻击力+2%,暴击率+2%,暴击伤害+2%;
        ResourceLocation Skill_2_Res_ATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_2_atk");
        ResourceLocation Skill_2_Res_CriChance = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_2_crichance");
        ResourceLocation Skill_2_Res_CriDmg = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_2_cridmg");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_2_Level,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_2_Res_ATK,0.02);
        addAttr(player,iImmortalPlayer.getSkill().Skill_2_Level,ModAttributes.IMMORTAL_CRITICAL_CHANCE.get(),Skill_2_Res_CriChance,0.02);
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_2_Level,ModAttributes.IMMORTAL_CRITICAL_DAMAGE.get(),Skill_2_Res_CriDmg,0.02);
        //不动如山(Skill_3) 等级每+1,防御力+2%,击退抗性+2%;
        ResourceLocation Skill_3_Res_Def = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_3_def");
        ResourceLocation Skill_3_Res_KnockR = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_3_knockr");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_3_Level,ModAttributes.IMMORTAL_DEFENSE.get(),Skill_3_Res_Def,0.02);
        addAttr(player,iImmortalPlayer.getSkill().Skill_3_Level,Attributes.KNOCKBACK_RESISTANCE,Skill_3_Res_KnockR,0.02);
        //法杖精通(Skill_4) 手持法杖时,攻击力+5%
        ResourceLocation Skill_4 = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_4");
        if (player.getMainHandItem().getItem() instanceof WandItem wandItem) {
            multiplyAttr(player,iImmortalPlayer.getSkill().Skill_4_Level,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_4,0.05);
        } else {
            AttributeInstance inst = player.getAttribute(ModAttributes.IMMORTAL_ATTACK_DAMAGE.get());
            if (inst != null) inst.removeModifier(stableUuid(Skill_4));
        }
        //火系掌握(Skill_5) 火系额外伤害+15%
        ResourceLocation Skill_5 = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_5");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_5_Level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.get(),Skill_5,0.15);
        //燃火(Skill_12) 攻击力+15x%,闪避率+5x%
        ResourceLocation Skill_12_ATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_12_atk");
        ResourceLocation Skill_12_DODGE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_12_dodge");
        MobEffectInstance effectInstance = player.getEffect(_ModEffects.SKILL12_EFFECT.get());
        if (effectInstance != null) {
            multiplyAttr(player,effectInstance.getAmplifier()+1,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_12_ATK,0.15);
            multiplyAttr(player,effectInstance.getAmplifier()+1,ModAttributes.IMMORTAL_DODGE.get(),Skill_12_DODGE,0.05);
        } else {
            AttributeInstance inst = player.getAttribute(ModAttributes.IMMORTAL_ATTACK_DAMAGE.get());
            AttributeInstance inst_ = player.getAttribute(ModAttributes.IMMORTAL_DODGE.get());
            if (inst != null) inst.removeModifier(stableUuid(Skill_12_ATK));
            if (inst_ != null) inst_.removeModifier(stableUuid(Skill_12_DODGE));
        }
        //火之亲和(Skill_14)
        //火系额外伤害+5*技能等级%
        //火系额外减伤+3*技能等级%[加算]
        //攻击力+2*技能等级%
        ResourceLocation Skill_14_EMD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_14_emd");
        ResourceLocation Skill_14_ERF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_14_erf");
        ResourceLocation Skill_14_ATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_14_atk");
        int skill14level = iImmortalPlayer.getSkill().Skill_14_Level;
        multiplyAttr(player,skill14level,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_14_ATK,0.02);
        addAttr(player,skill14level,ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE.get(),Skill_14_ERF,0.03);
        multiplyAttr(player,skill14level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.get(),Skill_14_EMD,0.05);
        //烈日(Skill_15) 火系额外伤害+10%
        ResourceLocation Skill_15_SELF_FDMG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_15_fdmg");
        boolean skill15_self = player.hasEffect(_ModEffects.SKILL15_EFFECT_ADD_DMG.get());
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.get(), Skill_15_SELF_FDMG,0.1,skill15_self);
        //三相平衡(Skill_16) 水系额外伤害+5%
        ResourceLocation Skill_16 = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_16");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_16_Level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.get(),Skill_16,0.05);
        //水之亲和(Skill_25)
        //水系额外伤害+5*技能等级%
        //水系额外减伤+3*技能等级%[加算]
        //攻击力+2*技能等级%
        ResourceLocation Skill_25_EMD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_25_emd");
        ResourceLocation Skill_25_ERF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_25_erf");
        ResourceLocation Skill_25_ATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_25_atk");
        int skill25level = iImmortalPlayer.getSkill().Skill_25_Level;
        multiplyAttr(player,skill25level,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_25_ATK,0.02);
        addAttr(player,skill25level,ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE.get(),Skill_25_ERF,0.03);
        multiplyAttr(player,skill25level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.get(),Skill_25_EMD,0.05);
        //风系纵横(Skill_27) 风系额外伤害+10% 移动速度+10%
        ResourceLocation Skill_27_SPD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_27_spd");
        ResourceLocation Skill_27_ERA = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_27_era");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_27_Level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.get(),Skill_27_ERA,0.10);
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_27_Level,Attributes.MOVEMENT_SPEED,Skill_27_SPD,0.10);
        //风场形成(Skill_30) 攻击力+10% 风系额外伤害+30% 火系额外伤害+15%
        ResourceLocation Skill_30_SELF_DMG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_30_dmg");
        ResourceLocation Skill_30_SELF_ADMG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_30_admg");
        ResourceLocation Skill_30_SELF_FDMG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_30_fdmg");
        boolean skill30_self = player.hasEffect(_ModEffects.SKILL30_EFFECT_ADD_DMG.get());
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(), Skill_30_SELF_DMG,0.1,skill30_self);
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.get(), Skill_30_SELF_ADMG,0.3,skill30_self);
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.get(), Skill_30_SELF_FDMG,0.15,skill30_self);
        //气流掌控(Skill_36)
        //风系额外伤害+7*技能等级%
        //攻击力+2*技能等级%
        ResourceLocation Skill_36_EMD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_36_emd");
        ResourceLocation Skill_36_ATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_36_atk");
        int skill36level = iImmortalPlayer.getSkill().Skill_36_Level;
        multiplyAttr(player,skill36level,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_36_ATK,0.02);
        multiplyAttr(player,skill36level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.get(),Skill_36_EMD,0.07);
        //土系坚守(Skill_38) 土系额外伤害+10% 额外减伤+5% 土系额外减伤+30%
        ResourceLocation Skill_38_ERE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_38_ere");
        ResourceLocation Skill_38_EDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_38_edef");
        ResourceLocation Skill_38_EDE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_38_ede");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_38_Level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.get(),Skill_38_ERE,0.10);
        addAttr(player,iImmortalPlayer.getSkill().Skill_38_Level,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Skill_38_EDEF,0.05);
        addAttr(player,iImmortalPlayer.getSkill().Skill_38_Level,ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_EARTH.get(),Skill_38_EDE,0.3);
        //固守待援(Skill_40) 防御力+50%,击退抗性+50%,额外减伤+20%
        ResourceLocation Skill_40_DEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_40_def");
        ResourceLocation Skill_40_KBR = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_40_kbr");
        ResourceLocation Skill_40_ERE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_40_ere");
        boolean skill40 = player.hasEffect(_ModEffects.SKILL40_EFFECT.get());
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_DEFENSE.get(),Skill_40_DEF,0.5,skill40);
        multiplyAttrWithCondition(player,Attributes.KNOCKBACK_RESISTANCE,Skill_40_KBR,0.5,skill40);
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Skill_40_ERE,0.2,skill40);
        //土地震颤(Skill44)
        //本体
        ResourceLocation Skill_44_SELF_EDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_44_edef");
        ResourceLocation Skill_44_SELF_KBR = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_44_kbr");
        boolean skill44_self = player.hasEffect(_ModEffects.SKILL44_EFFECT_SELF.get());
        FinalMultiplyAttrWithCondition(player,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Skill_44_SELF_EDEF,1,skill44_self);
        addAttrWithCondition(player,Attributes.KNOCKBACK_RESISTANCE,Skill_44_SELF_KBR,1,skill44_self);
        //附属
        ResourceLocation Skill_44_OTHERS_EDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_44_edef");
        boolean skill44_others = player.hasEffect(_ModEffects.SKILL44_EFFECT_OTHERS.get());
        FinalMultiplyAttrWithCondition(player,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Skill_44_OTHERS_EDEF,0.4,skill44_others);
        //厚土玄元(Skill_47)
        //土系额外伤害+10*技能等级%
        //额外减伤+2*技能等级%
        ResourceLocation Skill_47_EME = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_47_eme");
        ResourceLocation Skill_47_EDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_47_edef");
        ResourceLocation Skill_47_EFFECT_DEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_47_effect_def");
        int skill47level = iImmortalPlayer.getSkill().Skill_47_Level;
        multiplyAttr(player,skill47level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.get(),Skill_47_EME,0.1);
        multiplyAttr(player,skill47level,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Skill_47_EDEF,0.02);
        boolean skill47 = player.hasEffect(_ModEffects.SKILL47_EFFECT.get());
        multiplyAttrWithConditionWithLevel(player,skill47level,ModAttributes.IMMORTAL_DEFENSE.get(),Skill_47_EFFECT_DEF,0.04,skill47);
        //光系赐福(Skill_49) 光系额外伤害+10% 基础增益比率:120/max(当前游戏人数-1,2)
        ResourceLocation Skill_49_EDL = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_49_edl");
        ResourceLocation Skill_49_BA = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_49_ba");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_49_Level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.get(),Skill_49_EDL,0.10);
        int val = player.level().getServer().getPlayerList().getPlayerCount();
        addAttr(player,iImmortalPlayer.getSkill().Skill_49_Level*120/Math.max(val-1,2),ModAttributes.IMMORTAL_PLAYER_BUFF_AMOUNT.get(),Skill_49_BA,1);
        //勇气之语(Skill51)
        //本体
        ResourceLocation Skill_51_ADDATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_51_addatk");
        ResourceLocation Skill_51_ADDDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_51_adddef");
        boolean skill51 = player.hasEffect(_ModEffects.SKILL51_BUFF_AMOUNT.get());
        double v51 = iImmortalPlayer.getBuffAmount().get()/100d;
        if (player.hasEffect(_ModEffects.SKILL55_EFFECT.get())) {
            v51 *= 2;
        }
        if (player.hasEffect(_ModEffects.SKILL59_EFFECT.get())) {
            v51 *= 2.5;
        }
        if (player.hasEffect(_ModEffects.SKILL59_EFFECT_.get())) {
            v51 *= 1.3;
        }
        if (player.hasEffect(_ModEffects.SKILL57_EFFECT.get())) {
            v51 += 20;
        }

        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_51_ADDATK,v51,skill51);
        multiplyAttrWithCondition(player,ModAttributes.IMMORTAL_DEFENSE.get(),Skill_51_ADDDEF,v51,skill51);
        //急行咒(Skill52)
        //本体
        ResourceLocation Skill_52_ADDSPD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_52_addspd");
        boolean skill52 = player.hasEffect(_ModEffects.SKILL52_EFFECT.get());
        multiplyAttrWithCondition(player,Attributes.MOVEMENT_SPEED,Skill_52_ADDSPD,0.4,skill52);
        //圣光闪耀(Skill55)
        //本体
        ResourceLocation Skill_55_ADDSPD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_55_addspd");
        boolean skill55 = player.hasEffect(_ModEffects.SKILL55_EFFECT.get());
        multiplyAttrWithCondition(player,Attributes.MOVEMENT_SPEED,Skill_55_ADDSPD,0.3,skill55);
        //明光辉煌(Skill_58)
        //光系额外伤害+5*技能等级%
        //额外减伤额外+4*技能等级%
        //基础增益比率+2.5*技能等级%
        ResourceLocation Skill_58_EML = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_58_eml");
        ResourceLocation Skill_58_EDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_58_edef");
        ResourceLocation Skill_58_BUFFA = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_58_effect_buffa");
        int skill58level = iImmortalPlayer.getSkill().Skill_58_Level;
        multiplyAttr(player,skill58level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.get(),Skill_58_EML,0.05);
        multiplyAttr(player,skill58level,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Skill_58_EDEF,0.04);
        multiplyAttr(player,skill58level,ModAttributes.IMMORTAL_PLAYER_BUFF_AMOUNT.get(),Skill_58_BUFFA,0.025);
        //永恒天国(Skill59)
        //本体
        ResourceLocation Skill_59_ADDSPD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_59_addspd");
        boolean skill59 = player.hasEffect(_ModEffects.SKILL59_EFFECT.get());
        multiplyAttrWithCondition(player,Attributes.MOVEMENT_SPEED,Skill_59_ADDSPD,0.5,skill59);
        //暗之摄魂(Skill_60) 暗系额外伤害+15%
        ResourceLocation Skill_60_EDD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_60_edd");
        multiplyAttr(player,iImmortalPlayer.getSkill().Skill_60_Level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.get(),Skill_60_EDD,0.15);
        //厄运缝合(Skill_67)
        //暗系额外伤害+5*技能等级%
        ResourceLocation Skill_67_EMD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_67_emd");
        int skill67level = iImmortalPlayer.getSkill().Skill_67_Level;
        multiplyAttr(player,skill67level,ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.get(),Skill_67_EMD,0.05);

        /** 武器 */
        //Weapon1_2 同源-暗
        ResourceLocation Weapon1_2_AEP = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon1_2_adddef");
        boolean Weapon1_2_AddEP = player.hasEffect(_ModEffects.WEAPON1_2_EFFECT.get());
        addAttrWithCondition(player,ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get(),Weapon1_2_AEP,0.1,Weapon1_2_AddEP);
        //Weapon1_3
        //同源-光
        ResourceLocation Weapon1_3_AFD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon1_3_addfdmg");
        boolean Weapon1_3_AddFDMG = player.hasEffect(_ModEffects.WEAPON1_3_EFFECT.get());
        int value = 0;
        if (Weapon1_3_AddFDMG) value = player.getEffect(_ModEffects.WEAPON1_3_EFFECT.get()).getAmplifier()+1;
        addAttrWithConditionWithLevel(player,value,ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.get(),Weapon1_3_AFD,2,Weapon1_3_AddFDMG);
        //闪耀
        ResourceLocation Weapon1_3_AFD_S = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon1_3_addfdmg_s");
        boolean Weapon1_3_AddFDMG_S = player.hasEffect(_ModEffects.WEAPON1_3_EFFECT_.get());
        addAttrWithCondition(player,ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.get(),Weapon1_3_AFD_S,1,Weapon1_3_AddFDMG_S);
        //Weapon2_2
        //夯岩
        ResourceLocation Weapon2_2_ADDDEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon2_2_adddef");
        ResourceLocation Weapon2_2_ADDFinalAtk = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon2_2_addfinalatk");
        boolean Weapon2_2_AddAttr = player.hasEffect(_ModEffects.WEAPON2_2_EFFECT.get());
        addAttrWithCondition(player,ModAttributes.IMMORTAL_DEFENSE.get(),Weapon2_2_ADDDEF,200,Weapon2_2_AddAttr);
        addAttrWithCondition(player,ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.get(),Weapon2_2_ADDFinalAtk,2,Weapon2_2_AddAttr);
        //庇护
        ResourceLocation Weapon2_2_ADDDEF_ = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon2_2_adddef_");
        boolean Weapon2_2_AddAttr_ = player.hasEffect(_ModEffects.WEAPON2_2_EFFECT_.get());
        addAttrWithCondition(player,ModAttributes.IMMORTAL_DEFENSE.get(),Weapon2_2_ADDDEF_,100,Weapon2_2_AddAttr_);
        //同化
        ResourceLocation Weapon2_4_ADDATK = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "weapon2_4_addatk");
        boolean Weapon2_4_ADDATK_ = player.hasEffect(_ModEffects.WEAPON2_4_EFFECT.get());
        addAttrWithCondition(player,ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Weapon2_4_ADDATK,5,Weapon2_4_ADDATK_);
    }
    public static UUID stableUuid(ResourceLocation id) {
        // 基于 RL 生成稳定 UUID，保证同一 id 不会堆叠出多个 modifier
        return UUID.nameUUIDFromBytes(id.toString().getBytes(StandardCharsets.UTF_8));
    }
    private static void addAttr(Player player, int skillLevel, Attribute attribute, ResourceLocation modifierId, double extraAmount) {
        if (skillLevel < 0) return;
        AttributeInstance inst = player.getAttribute(attribute);
        if (inst == null) return;
        UUID uuid = stableUuid(modifierId);
        double amount = skillLevel * extraAmount;
        AttributeModifier existing = inst.getModifier(uuid);
        if (existing == null) {
            inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), amount, AttributeModifier.Operation.ADDITION));
        } else if (existing.getAmount() != amount) {
            inst.removeModifier(uuid);
            inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), amount, AttributeModifier.Operation.ADDITION));
        }
    }
    private static void multiplyAttr(Player player, int SkillLevel, Attribute attributes, ResourceLocation modifierId, double extraAmount) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        if (SkillLevel < 0) return;
        AttributeInstance inst = player.getAttribute(attributes);
        if (inst == null) return;
        UUID uuid = stableUuid(modifierId);
        double amount = SkillLevel * extraAmount;
        AttributeModifier existing = inst.getModifier(uuid);
        if (existing == null) {
            inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), amount, AttributeModifier.Operation.MULTIPLY_BASE));
        } else if (existing.getAmount() != amount) {
            inst.removeModifier(uuid);
            inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), amount, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
    private static void addAttrWithCondition(Player player, Attribute attribute, ResourceLocation modifierId, double Amount, Boolean bool) {
        addAttrWithConditionWithLevel(player,1,attribute,modifierId,Amount,bool);
    }
    private static void addAttrWithConditionWithLevel(Player player,int Level, Attribute attribute, ResourceLocation modifierId, double Amount, Boolean bool) {
        AttributeInstance inst = player.getAttribute(attribute);
        if (inst == null) return;
        UUID uuid = stableUuid(modifierId);
        AttributeModifier existing = inst.getModifier(uuid);
        if (bool) {
            if (existing == null) {
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount*Level, AttributeModifier.Operation.ADDITION));
            } else if (existing.getAmount() != Amount*Level) {
                inst.removeModifier(uuid);
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount*Level, AttributeModifier.Operation.ADDITION));
            }
        } else {
            if (existing != null) {
                inst.removeModifier(uuid);
            }
        }
    }
    private static void multiplyAttrWithCondition(Player player, Attribute attribute, ResourceLocation modifierId, double Amount, Boolean bool) {
        multiplyAttrWithConditionWithLevel(player,1,attribute,modifierId,Amount,bool);
    }
    private static void multiplyAttrWithConditionWithLevel(Player player,int Level, Attribute attribute, ResourceLocation modifierId, double Amount, Boolean bool) {
        AttributeInstance inst = player.getAttribute(attribute);
        if (inst == null) return;
        UUID uuid = stableUuid(modifierId);
        AttributeModifier existing = inst.getModifier(uuid);
        if (bool) {
            if (existing == null) {
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount*Level, AttributeModifier.Operation.MULTIPLY_BASE));
            } else if (existing.getAmount() != Amount*Level) {
                inst.removeModifier(uuid);
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount*Level, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        } else {
            if (existing != null) {
                inst.removeModifier(uuid);
            }
        }
    }
    private static void FinalMultiplyAttrWithCondition(Player player, Attribute attribute, ResourceLocation modifierId, double Amount, Boolean bool) {
        AttributeInstance inst = player.getAttribute(attribute);
        if (inst == null) return;
        UUID uuid = stableUuid(modifierId);
        AttributeModifier existing = inst.getModifier(uuid);
        if (bool) {
            if (existing == null) {
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount, AttributeModifier.Operation.MULTIPLY_TOTAL));
            } else if (existing.getAmount() != Amount) {
                inst.removeModifier(uuid);
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            if (existing != null) {
                inst.removeModifier(uuid);
            }
        }
    }
}
