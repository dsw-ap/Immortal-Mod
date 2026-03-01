package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Skill69 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(69,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(69,player);
            //SP消耗设置
            ModSkills.consumeSP(69,player);
            //吟唱设置
            ModSkills.setCharmingTime(69,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(69,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 6.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        ExplosionParticle.horizonParticleExpServerLevel(level, ModParticles.DARKNESS.get(),0.5,72,player.position().add(0.0,0.3,0.0));
        ExplosionParticle.horizonParticleExpServerLevel(level, ModParticles.DARKNESS.get(),0.4,72,player.position().add(0.0,0.3,0.0));

        float finalR = r;
        List<_ImmortalMob> mobs = player.level().getEntitiesOfClass(_ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR);
        for (_ImmortalMob mob : mobs) {
            if (mob.getskill69Time() <= 0) {
                mob.setskill69Time(200);
                mob.setskill69_Amount(0);
                Vec3 pos = mob.getEyePosition();
                level.sendParticles(ModParticles.DARKNESS.get(),pos.x,pos.y,pos.z,0,0,0.2,0,0.2);
            }
        }
    }
}
