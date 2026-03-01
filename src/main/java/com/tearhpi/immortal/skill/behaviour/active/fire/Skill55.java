package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill55_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.particle.option.OrbitParticleLight;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import com.tearhpi.immortal.util.statics.MotiveParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Skill55 {
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkill().Skill_59_Level == 2) {
            if (ModSkills.getCDbyNumAndPlayer(55,player) == 0 && ModSkills.getCDbyNumAndPlayer(59,player) == 0) {
                //重设CD
                ModSkills.setCDbyNumAndPlayer(55,player);
                ModSkills.setCDbyNumAndPlayer(59,player);
                //SP消耗设置
                ModSkills.consumeSP(55,player);
                //吟唱设置
                ModSkills.setCharmingTime(55,100,player);
            } else {
                player.sendSystemMessage(Component.literal("In CD,"+Math.max(ModSkills.getCDbyNumAndPlayer(55 ,player),ModSkills.getCDbyNumAndPlayer(59 ,player))));
            }
        } else {
            if (ModSkills.getCDbyNumAndPlayer(55,player) == 0) {
                //重设CD
                ModSkills.setCDbyNumAndPlayer(55,player);
                //SP消耗设置
                ModSkills.consumeSP(55,player);
                //吟唱设置
                ModSkills.setCharmingTime(55,100,player);
            } else {
                player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(55 ,player)));
            }
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 15.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),3.0,72,player.position().add(0.0,0.5,0.0));
        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),2.0,72,player.position().add(0.0,0.5,0.0));
        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),1.0,72,player.position().add(0.0,0.5,0.0));
        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),0.9,72,player.position().add(0.0,0.5,0.0));
        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),0.8,72,player.position().add(0.0,0.5,0.0));

        float finalR_ = r*7/3;
        List<Player> mobs = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(finalR_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR_ * finalR_);
        for (Player player_ : mobs) {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player_;
            if (iImmortalPlayer.getSkill().Skill_49_Level > 0) {

            } else {
                ImposeEffect.ImposeEffectWithoutAmp(player_, _ModEffects.SKILL55_EFFECT.get(), 700);
                Vec3 pos = player_.position();
                level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y + 1, pos.z, 15, 1.0, 2.0, 1.0, 0.0);
            }
        }

        Vec3 p3d = player.position().add(0.0,0.3,0.0);
        Vec3 motion = new Vec3(0,0,0);

        MotiveParticle.drawCircle(level,0,p3d,r,100,new OrbitParticleLight(p3d,1.1,200));
        MotiveParticle.drawCircle(level,0,p3d.add(0.0,0.3,0.0),r*1.05,100,new OrbitParticleLight(p3d,1.2,200));
        MotiveParticle.drawCircle(level,0,p3d.add(0.0,0.6,0.0),r*1.1,100,new OrbitParticleLight(p3d,1.3,200));

        Skill55_Entity e = Skill55_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
