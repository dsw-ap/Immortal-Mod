package com.tearhpi.immortal.effect.imposeEffect;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect._TaggedMobEffect;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ImposeEffect {
    /**ApplyEffectLayer 施加一层xx
     * 运行逻辑:传入参数:特殊效果,时长(tick)
     * 读取原属性:
     * 若无原属性,则施加1级特效,时长为输入时长
     * 若有原属性,则使原属性等级+1,时长取max(原属性时长,输入时长)
     * */
    public static void ApplyEffectLayer(LivingEntity entity,MobEffect effect, int time) {
        if (!entity.hasEffect(effect)) {
            AddEffect(entity, effect, time, 0);
        } else {
            MobEffectInstance effectInstance = entity.getEffect(effect);
            int time_old = effectInstance.getDuration();
            int amp_old = effectInstance.getAmplifier();
            entity.removeEffect(effect);
            AddEffect(entity, effect, (int) Mth.absMax(time_old,time),  Math.min(amp_old+1,127));
        }
    }
    /**ImposeLayer 施加xx级xx效果
     * 运行逻辑:传入参数:特殊效果,时长(tick),特效等级
     * 读取原属性:
     * 若无原属性,则造成 特效等级 的 特殊效果,时长为输入时长
     * 若有原属性,则:
     * 若原属性等级大于输入等级,什么都不做
     * 若原属性等级小于等于特效等级,时长取max(原属性时长,输入时长)特效等级取输入等级
     * */
    public static void ImposeLayer(LivingEntity entity,MobEffect effect, int time,int amplifier) {
        if (!entity.hasEffect(effect)) {
            AddEffect(entity, effect, time, Math.min(amplifier,127));
        } else {
            MobEffectInstance effectInstance = entity.getEffect(effect);
            int time_old = effectInstance.getDuration();
            int amp_old = effectInstance.getAmplifier();
            if (amp_old <= amplifier) {
                entity.removeEffect(effect);
                AddEffect(entity, effect, (int) Mth.absMax(time_old,time), Math.min(amplifier,127));
            }
        }
    }
    /**ImposeEffectWithoutAmp 造成xx:对无等级之分负面效果使用
     * 运行逻辑:传入参数:特殊效果,时长(tick)
     * 读取原属性:
     * 若无原属性,则造成 特殊效果,时长为输入时长
     * 若有原属性,则:
     * 时长取max(原属性时长,输入时长)
     * */
    public static void ImposeEffectWithoutAmp(LivingEntity entity,MobEffect effect, int time) {
        if (!entity.hasEffect(effect)) {
            AddEffect(entity, effect, time, 0);
        } else {
            MobEffectInstance effectInstance = entity.getEffect(effect);
            int time_old = effectInstance.getDuration();
            entity.removeEffect(effect);
            AddEffect(entity, effect, (int) Mth.absMax(time_old,time), 0);
        }
    }

    //
    public static void AddEffect(LivingEntity entity,MobEffect effect, int time,int amplifier) {
        if (entity instanceof Player player) {
            if (effect.getCategory().equals(MobEffectCategory.HARMFUL)) {
                double v = 1 - player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_DEBUFF_RESISTANCE.get());
                entity.addEffect(new MobEffectInstance(effect, (int) (time * v), amplifier, false, true, true));
                return;
            }
        }
        entity.addEffect(new MobEffectInstance(effect, time, amplifier, false, true, true));
    }
}
