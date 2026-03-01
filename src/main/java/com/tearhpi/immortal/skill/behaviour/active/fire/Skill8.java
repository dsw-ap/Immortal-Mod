package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.custom.Utils.BezierEntity;
import com.tearhpi.immortal.entity.custom.skills.Skill8_Entity;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.entity.custom.skills.Skill8_Entity_;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.skill.Skill8SyncPacket;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.sound.ModSounds;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

import static com.tearhpi.immortal.util.statics.SkillTagRelated.storeVec3;

public class Skill8 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(8,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(8,player);
            //SP消耗设置
            ModSkills.consumeSP(8,player);
            //吟唱设置
            ModSkills.setCharmingTime(8,60,player);
            //附加效果
            float r = 7.0f;
            r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
            Level level = player.level();
            float finalR = r;
            List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                    _ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
            );
            for (_ImmortalMob mob : mobs) {
                //System.out.println(mob.distanceToSqr(player)+"/1/"+finalR*finalR);
                ModNetworking.CHANNEL.sendToServer(new Skill8SyncPacket(mob.getId(),80));
            }
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(8,player)));
        }
    }
    public static void Prep(ServerPlayer player) {
        /*
        float r = 10.0f;
        if (player == null) return;
        ServerLevel level = player.serverLevel();

        Vec3 dir3D  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 pos = player.position().add(dir3D.scale(r*1.25));
        storeVec3(player,pos);
        Skill8_Entity_.spawn(level, player, player.getEyePosition(),pos.add(0.0,1.0,0.0), 59, ParticleTypes.FLAME,false);

         */
        SoundBefore(player);
    }
    public static void SoundBefore(ServerPlayer player) {
        player.playNotifySound(ModSounds.SKILL_6_SUMMON.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 7.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        float r_ = 3.0f;
        r_ *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);
        //需求
        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        List<_ImmortalMob> weakestMobs = mobs.stream()
                .sorted(Comparator.comparingDouble(Mob::getHealth)).limit(3).toList();
        ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
        for (_ImmortalMob mob : weakestMobs) {
            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,player);
            mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 2.5f));
            Summon(mob,level,player,r_);
            level.sendParticles(ParticleTypes.LAVA,mob.position().x,mob.position().y,mob.position().z,20,0,0,0,0.3);
        }
    }
    public static void Summon(Entity entity, Level level,ServerPlayer player,float Radius) {
        //以该Entity为中心生成相关Entity8
        final double speedBlocksPerSec = 20;
        final double speedPerTick = speedBlocksPerSec / 20.0;

        // 中心坐标
        double cx = entity.getX();
        double cy = entity.getY() + entity.getBbHeight() * 0.5;
        double cz = entity.getZ();

        // 八个方向（平面上）
        int[] anglesDeg = {0, 45, 90, 135, 180, 225, 270, 315};

        for (int deg : anglesDeg) {
            double rad = Math.toRadians(deg);

            // 水平面方向向量
            double dx = Math.cos(rad);
            double dz = Math.sin(rad);

            // 归一化后乘以速度
            double vx = dx * speedPerTick;
            double vz = dz * speedPerTick;

            // 实例化实体
            Skill8_Entity e = Skill8_Entity.spawn((ServerLevel) level, player,new Vec3(cx,cy,cz),new Vec3(vx,0,vz),Radius, (_ImmortalMob) entity);
            BoundEntityManager_SkillAttack.bind(player, e);

            level.addFreshEntity(e);
        }
    }
}
