package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill9_Entity;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
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

import java.util.*;

public class Skill9 {
    //该技能为持续技模板
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkillInUse().skill9Lasting > 1) {

        } else if (ModSkills.getCDbyNumAndPlayer(9,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(9,player);
            //SP消耗设置
            ModSkills.consumeSP(9,player);
            //吟唱设置(技能号/吟唱时间/玩家)
            //ModSkills.setCharmingTime(9,20,player);
            iImmortalPlayer.getSkillInUse().skill9Lasting = 120;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            ModNetworking.CHANNEL.sendToServer(new C2SSkillDownState(true));
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(9,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.swing(InteractionHand.MAIN_HAND,true);
        double rangeMultiplier = player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get());
        float r = (float) (10 * rangeMultiplier);

        ServerLevel level = player.serverLevel();

        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= r*r
        );
        List<_ImmortalMob> result = new ArrayList<>(mobs);
        Collections.shuffle(result, new Random());
        if (result.size() > 4) {
            result = result.subList(0, 4);
        }
        for (_ImmortalMob mob : result) {
            Vec3 dir3D  = Vec3.directionFromRotation(player.getXRot(),0);
            Vec3 p3d = player.getPosition(0.5F).add(dir3D.scale(1)).add(new Vec3(0,1.7,0));
            Skill9_Entity e = Skill9_Entity.spawn(level, player,p3d,new Vec3(0.0,0.0,0.0),mob);
            BoundEntityManager_SkillAttack.bind(player, e);
            level.addFreshEntity(e);
        }
    }
}
