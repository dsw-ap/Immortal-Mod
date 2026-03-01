package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity_;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncPacket;
import com.tearhpi.immortal.skill.ModSkills;

import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import static com.tearhpi.immortal.skill.ModSkills.*;
import static com.tearhpi.immortal.util.statics.SkillTagRelated.*;

public class Skill7 {
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkillInUse().skill7Lasting > 1) {
            //若技能期间再次点击该技能按钮
            //触发终结段
            iImmortalPlayer.getSkillInUse().skill7Lasting = 1;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            return;
        }
        if (ModSkills.getCDbyNumAndPlayer(7,player) == 0) {
            setCDbyNumValueAndPlayer(7,10000,player);
            iImmortalPlayer.getSkillInUse().skill7Lasting = 160;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            //SP消耗设置
            ModSkills.consumeSP(7,player);
            //吟唱设置
            ModSkills.setCharmingTime(7,20,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(7,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(ModSounds.SKILL_7_SUMMON.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
        float d = 3.0f;
        float r = 2.5f;
        d *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        player.swing(InteractionHand.MAIN_HAND,true);
        ServerLevel level = player.serverLevel();
        //吟唱刷新
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getSkillInUse().skill7Lasting = 160;
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) player)),new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));

        Vec3 motion = new Vec3(0.0f, 0.0f, 0.0f);
        Skill7_Entity e = Skill7_Entity.spawn(level, player, getVec3(player), motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);

        float yRot = Math.abs(player.getYRot());
        if (!(45.0f <= yRot && yRot <= 135.0f)) {
            e.setSkill7Tag(true);
        } else {
            e.setSkill7Tag(false);
        }
    }
    public static void Prep(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 2.5f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        player.swing(InteractionHand.MAIN_HAND,true);
        ServerLevel level = player.serverLevel();

        Vec3 dir3D = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 motion = new Vec3(0.0f, 0.0f, 0.0f);
        Vec3 pos = player.position().add(dir3D.scale(3));
        storeVec3(player,pos);
        storeRot(player,player.getXRot(),player.getYRot());
        Skill7_Entity_ e = Skill7_Entity_.spawn(level, player, pos, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
