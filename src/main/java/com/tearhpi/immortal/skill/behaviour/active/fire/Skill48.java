package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom.skills.Skill48_Entity;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.option.OrbitParticleEarth;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.statics.MotiveParticle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Skill48 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(48,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(48,player);
            //SP消耗设置
            ModSkills.consumeSP(48,player);
            //吟唱设置
            ModSkills.setCharmingTime(48,60,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(48 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 8.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 p3d = player.position().add(0.0,0.2,0.0);
        Vec3 motion = new Vec3(0,0,0);
        Vec3 center = p3d;
        MotiveParticle.drawCircle(level,0,center,r*1.1,100,new OrbitParticleEarth(center,1.2,160));
        MotiveParticle.drawCircle(level,0,center,r*1.15,100,new OrbitParticleEarth(center,-0.6,160));
        MotiveParticle.drawCircle(level,0,center.add(0.0,12.0,0.0),r*1.1,100,new OrbitParticleEarth(center,-1.2,160));
        MotiveParticle.drawCircle(level,0,center.add(0.0,12.0,0.0),r*1.15,100,new OrbitParticleEarth(center,0.6,160));

        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(p3d) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            ImposeEffect.ImposeLayer(mob, MobEffects.MOVEMENT_SLOWDOWN,160,1);
            ImposeEffect.ImposeLayer(mob, _ModEffects.ANNIHILATE_EFFECT.get(), 160,0);
        }

        Skill48_Entity e = Skill48_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
