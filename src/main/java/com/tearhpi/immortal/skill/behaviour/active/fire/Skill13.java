package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class Skill13 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(13,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(13,player);
            //SP消耗设置
            ModSkills.consumeSP(13,player);
            //吟唱设置
            ModSkills.setCharmingTime(13,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(13,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 10.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);
        //----------------------粒子----------------------
        ExplosionParticle.horizonParticleExpServerLevel(level, ParticleTypes.FLAME,1.0,90,player.position().add(new Vec3(0.0,0.10,0.0)));
        ExplosionParticle.horizonParticleExpServerLevel(level, ParticleTypes.FLAME,0.9,80,player.position().add(new Vec3(0.0,0.20,0.0)));

        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,0.8,50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(1.0,0.0,0.0));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,0.8,50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(0.0,0.0,1.0));

        Random rand = new Random();
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,rand.nextFloat(0.0f,1.0f),50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f)));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,rand.nextFloat(0.0f,1.0f),50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f)));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,rand.nextFloat(0.0f,1.0f),50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f)));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,rand.nextFloat(0.0f,1.0f),50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f)));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,rand.nextFloat(0.0f,1.0f),50,player.position().add(new Vec3(0.0,0.30,0.0)),new Vec3(rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f),rand.nextFloat(0.0f,1.0f)));
        //----------------------伤害和外推----------------------
        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            //伤害
            ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,player);
            mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3.0f));
            //两层缓慢 五层灼烧
            ImposeEffect.ImposeLayer(mob, MobEffects.MOVEMENT_SLOWDOWN,100,4);
            ImposeEffect.ImposeLayer(mob,_ModEffects.FIRING_EFFECT.get(),120,4);
            //外推
            // 向量：从玩家指向mob
            if (mob.isAttractable()){
                double rInv = 1.0 / r;
                double maxStrength = 1.5;
                double verticalBoost = 0.5;
                Vec3 toMob = mob.position().subtract(player.position());
                double dist = toMob.length();
                if (dist < 1.0e-6 || dist > r) continue;

                //---强度计算---
                //线性衰减
                double falloff = 1.0 - dist * rInv;
                //抗击退修正
                double resist = 1.0;
                var inst = mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                if (inst != null) {
                    // vanilla里该属性越大越“不容易被击退”
                    resist = 1.0 - Mth.clamp(inst.getValue(), 0.0, 1.0);
                }
                //计算最终强度
                double strength = maxStrength * falloff * resist;

                Vec3 dir = toMob.scale(1.0 / dist);
                Vec3 impulse = new Vec3(dir.x * strength, dir.y * strength + (verticalBoost * falloff), dir.z * strength);
                mob.setDeltaMovement(mob.getDeltaMovement().add(impulse));
                mob.hasImpulse = true;
                mob.hurtMarked = true;
            }
        }
    }
}
