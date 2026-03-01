package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class Skill46 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(46,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(46,player);
            //SP消耗设置
            ModSkills.consumeSP(46,player);
            //吟唱设置
            ModSkills.setCharmingTime(46,2,player);
        } else {player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(46 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 10.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        float finalR_ = r;
        List<_ImmortalMob> mobs = player.level().getEntitiesOfClass(_ImmortalMob.class, player.getBoundingBox().inflate(finalR_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR_ * finalR_);
        for (_ImmortalMob mob : mobs) {
            mob.setskill46Time(100);
            mob.setskill46Level(3);
            mob.setskill46Atker(player.getUUID());
        }
    }
}
