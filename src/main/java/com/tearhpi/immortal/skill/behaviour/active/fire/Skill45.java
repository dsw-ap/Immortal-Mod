package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncPacket;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import static com.tearhpi.immortal.skill.ModSkills.*;

public class Skill45 {
    public static void Main(Player player) {
        /*
        Player player = Minecraft.getInstance().player;
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(45,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(45,player);
            //SP消耗设置
            ModSkills.consumeSP(45,player);
            //吟唱设置
            ModSkills.setCharmingTime(45,2,player);
        } else {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(45 ,player)));
        }
         */

        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkillInUse().skill45Lasting > 1) {
            //若技能期间再次点击该技能按钮
            //触发终结段
            iImmortalPlayer.getSkillInUse().skill45Lasting = 1;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            return;
        }
        if (ModSkills.getCDbyNumAndPlayer(45,player) == 0) {
            setCDbyNumValueAndPlayer(45,10000,player);
            iImmortalPlayer.getSkillInUse().skill45Lasting = 300;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            //SP消耗设置
            ModSkills.consumeSP(45,player);
            //吟唱设置
            ModSkills.setCharmingTime(45,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(45,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.setAbsorptionAmount(player.getMaxHealth()/2);
        ImposeEffect.ImposeEffectWithoutAmp(player, _ModEffects.SKILL45_EFFECT_SELF.get(), 300);
        player.swing(InteractionHand.MAIN_HAND,true);
    }
}
