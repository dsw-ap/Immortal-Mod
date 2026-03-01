package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.entity.custom.skills.Skill17_Entity;
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

public class Skill17 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(17,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(17,player);
            //SP消耗设置
            ModSkills.consumeSP(17,player);
            //吟唱设置
            ModSkills.setCharmingTime(17,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(17,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 dir3D_mid  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 dir3D_right  = Vec3.directionFromRotation(0, player.getYRot()+30);
        Vec3 dir3D_left  = Vec3.directionFromRotation(0, player.getYRot()-30);
        Vec3 p3d_mid = player.getEyePosition().add(new Vec3(0.0,-0.5,0.0)).add(dir3D_mid);
        Vec3 p3d_right = player.getEyePosition().add(new Vec3(0.0,-0.5,0.0)).add(dir3D_right);
        Vec3 p3d_left = player.getEyePosition().add(new Vec3(0.0,-0.5,0.0)).add(dir3D_left);

        Skill17_Entity e_mid = Skill17_Entity.spawn(level, player, p3d_mid, dir3D_mid.scale(1.0), 1.0f);
        BoundEntityManager_SkillAttack.bind(player, e_mid);
        level.addFreshEntity(e_mid);
        Skill17_Entity e_left = Skill17_Entity.spawn(level, player, p3d_left, dir3D_left.scale(1.0), 1.0f);
        BoundEntityManager_SkillAttack.bind(player, e_left);
        level.addFreshEntity(e_left);
        Skill17_Entity e_right = Skill17_Entity.spawn(level, player, p3d_right, dir3D_right.scale(1.0), 1.0f);
        BoundEntityManager_SkillAttack.bind(player, e_right);
        level.addFreshEntity(e_right);
    }
}
