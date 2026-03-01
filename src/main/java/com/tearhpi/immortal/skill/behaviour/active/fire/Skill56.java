package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
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

import java.util.List;

public class Skill56 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(56,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(56,player);
            //SP消耗设置
            ModSkills.consumeSP(56,player);
            //吟唱设置
            ModSkills.setCharmingTime(56,60,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(56,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 5.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ParticleTypes.END_ROD,0.5,72,player.position().add(0.0,0.5,0.0));
        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),0.5,72,player.position().add(0.0,0.5,0.0));
        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) player.level(), ModParticles.LIGHT.get(),0.6,72,player.position().add(0.0,0.5,0.0));

        float finalR_ = r;
        List<Player> mobs = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(finalR_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR_ * finalR_);
        for (Player player_ : mobs) {
            Vec3 pos = player_.position();
            ((ServerLevel) player.level()).sendParticles(ModParticles.LIGHT.get(),pos.x,pos.y,pos.z,0,0,0.3,0,0.1);
            ImposeEffect.ApplyEffectLayer(player_, _ModEffects.PURIFICATION_EFFECT.get(), 200);
        }
    }
}
