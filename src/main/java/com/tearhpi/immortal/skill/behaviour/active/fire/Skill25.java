package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Skill25 {
    public static void Do(ServerPlayer player, LivingEntity livingEntity) {
        player.playNotifySound(SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 3.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        //----------------------伤害----------------------
        ServerLevel level = (ServerLevel) player.level();
        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, livingEntity.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(livingEntity) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            //伤害
            ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
            mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 1.0f));
            ImposeEffect.ApplyEffectLayer(mob, _ModEffects.EROSION_EFFECT.get(), 100);
        }
        ExplosionParticle.horizonParticleExpServerLevel(level, ModParticles.WATER.get(),0.5,36,livingEntity.position());
        ExplosionParticle.tiltParticleExpServerLevel(level,ModParticles.WATER.get(),0.5,36,livingEntity.position(),new Vec3(1.0,0.0,0.0));
        ExplosionParticle.tiltParticleExpServerLevel(level,ModParticles.WATER.get(),0.5,36,livingEntity.position(),new Vec3(0.0,0.0,1.0));

    }
}
