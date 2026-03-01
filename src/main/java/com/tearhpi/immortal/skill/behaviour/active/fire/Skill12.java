package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncPacket;
import com.tearhpi.immortal.networking.skill.C2SSkillDownState;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class Skill12 {
    //该技能为持续技模板
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        //System.out.println(iImmortalPlayer.getSkillInUse().skill12Lasting);
        if (iImmortalPlayer.getSkillInUse().skill12Lasting > 1) {

        } else if (ModSkills.getCDbyNumAndPlayer(12,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(12,player);
            //SP消耗设置
            ModSkills.consumeSP(12,player);
            //吟唱设置(技能号/吟唱时间/玩家)
            //ModSkills.setCharmingTime(9,20,player);
            iImmortalPlayer.getSkillInUse().skill12Lasting = 180;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            ModNetworking.CHANNEL.sendToServer(new C2SSkillDownState(true));
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(12,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.swing(InteractionHand.MAIN_HAND,true);
        //
        MobEffectInstance effectInstance = player.getEffect(_ModEffects.SKILL12_EFFECT.get());
        int val = 0;
        if (effectInstance != null) {
            val = effectInstance.getAmplifier()+1;
        }
        val += 1;
        //燃火效果
        ImposeEffect.ApplyEffectLayer(player,_ModEffects.SKILL12_EFFECT.get(),val*40);
        ImposeEffect.ImposeEffectWithoutAmp(player,_ModEffects.ANTI_HEAL_EFFECT.get(),val*20);
        ServerLevel level = (ServerLevel) player.level();
        level.sendParticles(ParticleTypes.FLAME,player.getX(),player.getY(),player.getZ(),50, 3, 3, 3, 0.2);
        level.sendParticles(ParticleTypes.LAVA,player.getX(),player.getY()+1,player.getZ(),10, 0, 0, 0, 0.2);
        //扣血
        ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL);
        player.hurt(damagesource, (float) (player.getMaxHealth()*0.1));
    }
}
