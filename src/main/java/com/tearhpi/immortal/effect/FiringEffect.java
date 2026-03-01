package com.tearhpi.immortal.effect;

import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.custom._ImmortalNormalMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;

public class FiringEffect extends _TaggedMobEffect {

    public FiringEffect(MobEffectCategory category, int color, Set<_EffectTag> tags, int weight) {
        super(category, color, tags, weight);
    }

    /**
     * 每 tick 作用的逻辑
     */
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof _ImmortalNormalMob) {
            ModDamageSources modDamageSources = new ModDamageSources(entity.level().registryAccess());
            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL);
            entity.hurt(damagesource, entity.getMaxHealth()*(amplifier+1)*0.01f);
            super.applyEffectTick(entity, amplifier);
        }
    }

    /**
     * 是否在当前 tick 执行 applyEffectTick。
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return (duration % 20) == 0;
    }
}
