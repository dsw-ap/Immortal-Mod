package com.tearhpi.immortal.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class UnBalanceEffect_2 extends _TaggedMobEffect {
    public UnBalanceEffect_2(MobEffectCategory category, int color, Set<_EffectTag> tags, int weight) {
        super(category, color, tags, weight);
    }

        /**
         * 每 tick 作用的逻辑
         */
        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            Vec3 forward = entity.getLookAngle();
            forward = new Vec3(forward.x, 0, forward.z).normalize();
            Vec3 left = new Vec3(0, 1, 0).cross(forward).normalize();
            entity.setDeltaMovement(entity.getDeltaMovement().add(left.scale(-(double) Math.min((amplifier + 1), 10) / 50)));
        }

        /**
         * 是否在当前 tick 执行 applyEffectTick。
         */
        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true;
        }
}
