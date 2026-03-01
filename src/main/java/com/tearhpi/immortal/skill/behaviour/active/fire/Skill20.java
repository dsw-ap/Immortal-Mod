package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill20_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill20 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(20,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(20,player);
            //SP消耗设置
            ModSkills.consumeSP(20,player);
            //吟唱设置
            ModSkills.setCharmingTime(20,20,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(20 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 3.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 dir3D  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 p3d = player.getEyePosition().add(dir3D.scale(2)).add(new Vec3(0,-1.0f,0));
        Vec3 motion = Vec3.directionFromRotation(0, player.getYRot()).normalize().scale(0.1);
        Skill20_Entity e = Skill20_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
