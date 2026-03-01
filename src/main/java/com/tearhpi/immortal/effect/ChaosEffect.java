package com.tearhpi.immortal.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;

public class ChaosEffect extends _TaggedMobEffect {

    public ChaosEffect(MobEffectCategory category, int color, Set<_EffectTag> tags, int weight) {
        super(category, color, tags, weight);
    }

    /**
     * 每 tick 作用的逻辑
     */
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        //entity.hurt(DamageSource.MAGIC, 1.0F);
        super.applyEffectTick(entity, amplifier);
    }

    /**
     * 是否在当前 tick 执行 applyEffectTick。
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return super.isDurationEffectTick(duration, amplifier);
    }
}
