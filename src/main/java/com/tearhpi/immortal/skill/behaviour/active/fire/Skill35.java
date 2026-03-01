package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill35_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillCDInfoSyncPacket;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill35 {
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkillCD().Skill35_Charge > 0) {
            //重设CD
            iImmortalPlayer.getSkillCD().Skill35_Charge -= 1;
            CompoundTag confirmed = iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo());
            ModNetworking.CHANNEL.sendToServer(new SkillCDInfoSyncPacket(confirmed));
            ModSkills.setCDbyNumAndPlayer(35, player);
            //SP消耗设置
            ModSkills.consumeSP(35,player);
            //吟唱设置
            ModSkills.setCharmingTime(35,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(35 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 8.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 dir3D_mid  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 dir3D_right  = Vec3.directionFromRotation(0, player.getYRot()+30);
        Vec3 dir3D_left  = Vec3.directionFromRotation(0, player.getYRot()-30);
        Vec3 dir3D_right_2 = Vec3.directionFromRotation(0, player.getYRot()+60);
        Vec3 dir3D_left_2  = Vec3.directionFromRotation(0, player.getYRot()-60);
        Vec3 p3d_mid = player.getEyePosition().add(new Vec3(0.0,-1.4,0.0)).add(dir3D_mid);
        Vec3 p3d_right = player.getEyePosition().add(new Vec3(0.0,-1.4,0.0)).add(dir3D_right);
        Vec3 p3d_left = player.getEyePosition().add(new Vec3(0.0,-1.4,0.0)).add(dir3D_left);
        Vec3 p3d_right_2 = player.getEyePosition().add(new Vec3(0.0,-1.4,0.0)).add(dir3D_right);
        Vec3 p3d_left_2 = player.getEyePosition().add(new Vec3(0.0,-1.4,0.0)).add(dir3D_left);

        Skill35_Entity e_mid = Skill35_Entity.spawn(level, player, p3d_mid, dir3D_mid.scale(0.5), 1.5f);
        BoundEntityManager_SkillAttack.bind(player, e_mid);
        level.addFreshEntity(e_mid);

        Skill35_Entity e_left = Skill35_Entity.spawn(level, player, p3d_left, dir3D_left.scale(0.5), 1.5f);
        BoundEntityManager_SkillAttack.bind(player, e_left);
        level.addFreshEntity(e_left);

        Skill35_Entity e_right = Skill35_Entity.spawn(level, player, p3d_right, dir3D_right.scale(0.5), 1.5f);
        BoundEntityManager_SkillAttack.bind(player, e_right);
        level.addFreshEntity(e_right);

        Skill35_Entity e_left_2 = Skill35_Entity.spawn(level, player, p3d_left_2, dir3D_left_2.scale(0.5), 1.5f);
        BoundEntityManager_SkillAttack.bind(player, e_left_2);
        level.addFreshEntity(e_left_2);

        Skill35_Entity e_right_2 = Skill35_Entity.spawn(level, player, p3d_right_2, dir3D_right_2.scale(0.5), 1.5f);
        BoundEntityManager_SkillAttack.bind(player, e_right_2);
        level.addFreshEntity(e_right);
    }
}
