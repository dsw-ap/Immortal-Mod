package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom.skills.Skill40_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill40 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(40,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(40,player);
            //SP消耗设置
            ModSkills.consumeSP(40,player);
            //吟唱设置
            ModSkills.setCharmingTime(40,60,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(40 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 5.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        ImposeEffect.ImposeLayer(player, MobEffects.MOVEMENT_SLOWDOWN,100,2);

        Vec3 p3d = player.position();
        Vec3 motion = new Vec3(0,0,0);
        Skill40_Entity e = Skill40_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
