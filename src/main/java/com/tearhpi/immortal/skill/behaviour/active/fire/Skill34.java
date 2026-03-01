package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class Skill34 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(34,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(34,player);
            //SP消耗设置
            ModSkills.consumeSP(34,player);
            //吟唱设置
            ModSkills.setCharmingTime(34,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(34 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        ImposeEffect.ImposeLayer(player, MobEffects.MOVEMENT_SPEED,100,2);
        ImposeEffect.ImposeEffectWithoutAmp(player, _ModEffects.SKILL34_EFFECT.get(),160);

        ServerLevel level = (ServerLevel) player.level();
        level.sendParticles(ModParticles.AIR.get(),player.position().x,player.position().y+1.0,player.position().z,10,1,1,1,0.1);
    }
}
