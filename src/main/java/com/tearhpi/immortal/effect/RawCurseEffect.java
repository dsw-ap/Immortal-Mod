package com.tearhpi.immortal.effect;

import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class RawCurseEffect extends _TaggedMobEffect {

    public RawCurseEffect(MobEffectCategory category, int color, Set<_EffectTag> tags, int weight) {
        super(category, color, tags, weight);
    }

    /**
     * 每 tick 作用的逻辑
     */
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int t = 0;
        MobEffectInstance effectInstance = entity.getEffect(_ModEffects.RAWCURSE_EFFECT.get());
        if (effectInstance != null) {
            t = effectInstance.getDuration();
            entity.removeEffect(_ModEffects.RAWCURSE_EFFECT.get());
            //增加时长
            Collection<MobEffectInstance> effects = entity.getActiveEffects();

            if (effects.isEmpty()) return;

            //筛选出要修改的效果
            ArrayList<MobEffectInstance> toUpdate = new ArrayList<>();
            for (MobEffectInstance inst : effects) {
                MobEffect effect = inst.getEffect();
                if (effect.getCategory() == MobEffectCategory.HARMFUL) {
                    toUpdate.add(inst);
                }
            }
            //addEffect
            for (MobEffectInstance inst : toUpdate) {
                int newDuration = inst.getDuration() + t;

                MobEffectInstance newInst = new MobEffectInstance(
                        inst.getEffect(),
                        newDuration,
                        inst.getAmplifier(),
                        inst.isAmbient(),
                        inst.isVisible(),
                        inst.showIcon()
                );

                entity.addEffect(newInst);
            }
        }
    }

    /**
     * 是否在当前 tick 执行 applyEffectTick。
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
