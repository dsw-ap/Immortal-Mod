package com.tearhpi.immortal.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;

//侵蚀效果
public class NightMareEffect extends _TaggedMobEffect {

    public NightMareEffect(MobEffectCategory category, int color, Set<_EffectTag> tags, int weight) {
        super(category, color, tags, weight);

    }

    /**
     * 每 tick 作用的逻辑
     */
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        //ImposeEffect.ImposeLayer(entity, MobEffects.MOVEMENT_SLOWDOWN,40,9);
    }

    /**
     * 是否在当前 tick 执行 applyEffectTick。
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return (duration % 20) == 0;
    }
}
