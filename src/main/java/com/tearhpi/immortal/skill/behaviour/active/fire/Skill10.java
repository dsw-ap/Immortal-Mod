package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill10_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill10 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(10,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(10,player);
            //SP消耗设置
            ModSkills.consumeSP(10,player);
            //吟唱设置(技能号/吟唱时间/玩家)
            ModSkills.setCharmingTime(10,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(10,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        double rangeMultiplier = player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get());
        float d = (float) (6 * rangeMultiplier);
        float r = (float) (3.5 * rangeMultiplier);

        ServerLevel level = player.serverLevel();

        // 在玩家朝向前方 1 格、略抬高 1 格处生成（可按需调整）
        //double yawRad = Math.toRadians(player.getYRot());
        //double x = player.getX() + Math.cos(yawRad) * 1.0;
        //double y = player.getY() + 1.0;
        //double z = player.getZ() + Math.sin(yawRad) * 1.0;
        //System.out.println("TEST:player.getYRot()"+player.getYRot());
        Vec3 look = player.getLookAngle(); // 相当于前方单位向量
        Vec3 dir3D = (new Vec3(look.x(), 0, look.z())).normalize();
        Vec3 dir3D_ = look.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 p3d = player.getPosition(0.5F).add(dir3D.scale(d));
        Vec3 p3d2 = player.getPosition(0.5F).add(dir3D.scale(-d));
        Vec3 p3d_ = player.getPosition(0.5F).add(dir3D_.scale(d));
        Vec3 p3d2_ = player.getPosition(0.5F).add(dir3D_.scale(-d));
        Skill10_Entity e = Skill10_Entity.spawn(level, player,p3d,new Vec3(0.0,0.0,0.0),r);
        Skill10_Entity e1 = Skill10_Entity.spawn(level, player,p3d2,new Vec3(0.0,0.0,0.0),r);
        Skill10_Entity e2 = Skill10_Entity.spawn(level, player,p3d_,new Vec3(0.0,0.0,0.0),r);
        Skill10_Entity e3 = Skill10_Entity.spawn(level, player,p3d2_,new Vec3(0.0,0.0,0.0),r);
        BoundEntityManager_SkillAttack.bind(player, e);
        BoundEntityManager_SkillAttack.bind(player, e1);
        BoundEntityManager_SkillAttack.bind(player, e2);
        BoundEntityManager_SkillAttack.bind(player, e3);
        level.addFreshEntity(e);
        level.addFreshEntity(e1);
        level.addFreshEntity(e2);
        level.addFreshEntity(e3);
    }
}
