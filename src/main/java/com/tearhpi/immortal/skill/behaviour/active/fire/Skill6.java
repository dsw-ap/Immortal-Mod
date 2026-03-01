package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.ibm.icu.text.MessagePattern;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.Utils.BezierEntity;
import com.tearhpi.immortal.entity.custom.skills.Skill6_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.sound.ModSounds;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import static com.tearhpi.immortal.util.statics.SkillTagRelated.getVec3;
import static com.tearhpi.immortal.util.statics.SkillTagRelated.storeVec3;

public class Skill6 {
    public static void Main(Player player) {
        //判断cd
        //System.out.println("End_-1");
        if (ModSkills.getCDbyNumAndPlayer(6,player) == 0) {
            //重设CD
            //System.out.println("End_0");
            ModSkills.setCDbyNumAndPlayer(6,player);
            //System.out.println("End_1");
            //SP消耗设置
            ModSkills.consumeSP(6,player);
            //System.out.println("End_2");
            //吟唱设置
            ModSkills.setCharmingTime(6,10,player);
            //System.out.println("End_3");
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(6,player)));
        }
    }
    public static void Do(ServerPlayer player) {

        float r = 3.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        //Vec3 dir3D  = Vec3.directionFromRotation(0, player.getYRot());
        //Vec3 p3d = player.position().add(dir3D.scale(r*1.25));
        Vec3 p3d = getVec3(player);
        Vec3 motion = new Vec3(0.0f,0.0f,0.0f);
        Skill6_Entity e = Skill6_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,0.6,36,p3d.add(0.0,1.0,0.0), new Vec3(1.0,1.0,0.0));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,0.6,36,p3d.add(0.0,1.0,0.0), new Vec3(-0.5,1.0,0.732));
        ExplosionParticle.tiltParticleExpServerLevel(level, ParticleTypes.FLAME,0.6,36,p3d.add(0.0,1.0,0.0), new Vec3(-0.5,1.0,-0.732));
    }
    public static void Prep(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 3.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 dir3D  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 pos = player.position().add(dir3D.scale(r*1.25));
        storeVec3(player,pos);
        BezierEntity b1 = BezierEntity.spawn(level, player, player.getEyePosition(),() -> pos.add(0.0,1.0,0.0), 8, ParticleTypes.FLAME,false);
        BezierEntity b2 = BezierEntity.spawn(level, player, player.getEyePosition(),() -> pos.add(0.0,1.0,0.0), 8, ParticleTypes.FLAME,false);
        BezierEntity b3 = BezierEntity.spawn(level, player, player.getEyePosition(),() -> pos.add(0.0,1.0,0.0), 8, ParticleTypes.FLAME,false);
        BezierEntity b4 = BezierEntity.spawn(level, player, player.getEyePosition(),() -> pos.add(0.0,1.0,0.0), 8, ParticleTypes.FLAME,false);
    }
    public static void SoundBefore(ServerPlayer player) {
        player.playNotifySound(ModSounds.SKILL_6_SUMMON.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
