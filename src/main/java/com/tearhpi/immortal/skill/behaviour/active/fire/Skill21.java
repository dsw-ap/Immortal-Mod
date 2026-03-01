package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill21_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncPacket;
import com.tearhpi.immortal.networking.skill.C2SSkillDownState;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill21 {
    //该技能为持续技模板
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkillInUse().skill21Lasting > 1) {

        } else if (ModSkills.getCDbyNumAndPlayer(21,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(21,player);
            //SP消耗设置
            ModSkills.consumeSP(21,player);
            //吟唱设置(技能号/吟唱时间/玩家)
            //ModSkills.setCharmingTime(9,20,player);
            iImmortalPlayer.getSkillInUse().skill21Lasting = 59;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            ModNetworking.CHANNEL.sendToServer(new C2SSkillDownState(true));
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(9,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.swing(InteractionHand.MAIN_HAND,true);
        ServerLevel level = player.serverLevel();

        Vec3 dir3D_mid  = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
        Vec3 dir3D_right  = Vec3.directionFromRotation(player.getXRot(), player.getYRot()+30);
        Vec3 dir3D_left  = Vec3.directionFromRotation(player.getXRot(), player.getYRot()-30);
        Vec3 p3d_mid = player.getEyePosition().add(new Vec3(0.0,-0.5,0.0)).add(dir3D_mid);
        Vec3 p3d_right = player.getEyePosition().add(new Vec3(0.0,-0.5,0.0)).add(dir3D_right);
        Vec3 p3d_left = player.getEyePosition().add(new Vec3(0.0,-0.5,0.0)).add(dir3D_left);

        Skill21_Entity e_mid = Skill21_Entity.spawn(level, player, p3d_mid, dir3D_mid.scale(3.0), 1.0f);
        BoundEntityManager_SkillAttack.bind(player, e_mid);
        level.addFreshEntity(e_mid);
        Skill21_Entity e_left = Skill21_Entity.spawn(level, player, p3d_left, dir3D_left.scale(3.0), 1.0f);
        BoundEntityManager_SkillAttack.bind(player, e_left);
        level.addFreshEntity(e_left);
        Skill21_Entity e_right = Skill21_Entity.spawn(level, player, p3d_right, dir3D_right.scale(3.0), 1.0f);
        BoundEntityManager_SkillAttack.bind(player, e_right);
        level.addFreshEntity(e_right);
    }
}
