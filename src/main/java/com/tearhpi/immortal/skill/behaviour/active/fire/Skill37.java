package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom.skills.Skill37_Entity;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.option.OrbitParticleAir;
import com.tearhpi.immortal.particle.option.OrbitParticleAir_vertical_;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Skill37 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(37,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(37,player);
            //SP消耗设置
            ModSkills.consumeSP(37,player);
            //吟唱设置
            ModSkills.setCharmingTime(37,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(37 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 7.5f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 center = player.position();

        drawCircle(level,0,center.add(new Vec3(0.0,4.0f,0.0)),r*1.1,100,new OrbitParticleAir(center.add(new Vec3(0.0,3.0f,0.0)),1.5,160));
        drawCircle(level,0,center.add(new Vec3(0.0,3.5f,0.0)),r*1.05,100,new OrbitParticleAir(center.add(new Vec3(0.0,3.0f,0.0)),1.5,160));
        drawCircle(level,0,center.add(new Vec3(0.0,3.0f,0.0)),r,100,new OrbitParticleAir(center.add(new Vec3(0.0,3.0f,0.0)),1.5,160));
        drawCircle(level,0,center.add(new Vec3(0.0,3.5f,0.0)),r*1.05,100,new OrbitParticleAir(center.add(new Vec3(0.0,3.0f,0.0)),1.5,160));
        drawCircle(level,0,center.add(new Vec3(0.0,2.0f,0.0)),r*1.1,100,new OrbitParticleAir(center.add(new Vec3(0.0,3.0f,0.0)),1.5,160));
        for (int i = 0; i < 32; i++) {
             double r_ = r * 0.75;
             float omega = (float) (Math.PI * 2 * i / 32);
             for (int j = 0; j < 100; j++) {
                 float first_rot = (float) (Math.PI * 2 * j / 100);
                 Vec3 pos = center.add(new Vec3((r-r_)*Mth.cos(omega),3.0f,(r-r_)*Mth.sin(omega)));
                 Vec3 center_ = center.add(new Vec3(r*Mth.cos(omega),3.0f,r*Mth.sin(omega)));
                 level.sendParticles(new OrbitParticleAir_vertical_(center_,first_rot,160), pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
             }
        }

        float _r_ = r * 4 / 3 * 2;
        List<_ImmortalMob> mobs = player.level().getEntitiesOfClass(_ImmortalMob.class, player.getBoundingBox().inflate(_r_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= _r_ * _r_);
        for (_ImmortalMob mob : mobs) {
            if (player != null){
                ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 8.0F));
                ImposeEffect.ImposeEffectWithoutAmp(mob, _ModEffects.IMPRISIONED_EFFECT.get(), 160);
                ImposeEffect.ImposeEffectWithoutAmp(mob, MobEffects.LEVITATION, 160);
            }
        }

        Vec3 p3d = player.position().add(new Vec3(0.0,3.0,0.0));
        Vec3 motion = new Vec3(0,0,0);
        Skill37_Entity e = Skill37_Entity.spawn(level, player, p3d, motion, r*2);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }

    public static void drawCircle(ServerLevel level, double first_rot, Vec3 circleCenter, double radius, int count, ParticleOptions particleOptions) {
        //ParticleType传入参数示例:new OrbitParticleOptions(center_rot,omega,lifetime)
        //参数备注:level:世界;first_rot:初始相位;omega:角速度;center_rot:旋转中心;circleCenter:圆心;radius:圆半径;count:生成粒子数量;lifetime:存在时间
        for (int i = 0; i < count; i++) {
            float step = (float) (i * Math.PI * 2 / count);
            level.sendParticles(particleOptions,circleCenter.x+radius * Mth.cos((float) (step+first_rot)), circleCenter.y+0.5, circleCenter.z+radius*Mth.sin((float) (step+first_rot)),1, 0, 0, 0, 0);
        }
    }
    public static void drawCircleVert(ServerLevel level, double first_rot, Vec3 center_,Vec3 circleCenter,double radius, int count, ParticleOptions particleOptions) {
        // 1. 计算平面内的两个正交基向量：u, v
        // -----------------------------------
        // u：从圆心指向 center_ 的方向（单位向量）
        Vec3 offset = center_.subtract(circleCenter);
        double len = offset.length();

        Vec3 u;
        if (len < 1.0e-6) {
            // center_ 和 circleCenter 太接近：随便取一个水平方向
            u = new Vec3(1, 0, 0);
        } else {
            u = offset.normalize(); // 单位向量
        }

        // v：使用 up=(0,1,0) 做 Gram-Schmidt，使得 v 在“垂直平面”里
        Vec3 up = new Vec3(0, 1, 0);
        // 先把 up 在 u 方向上的分量减掉，得到与 u 垂直的分量
        Vec3 vCandidate = up.subtract(u.scale(up.dot(u)));

        if (vCandidate.lengthSqr() < 1.0e-6) {
            // 如果 u 和 up 共线（即 offset 竖直），则换一个水平方向来构造 v
            Vec3 w = new Vec3(1, 0, 0);
            vCandidate = w.subtract(u.scale(w.dot(u)));
        }

        Vec3 v = vCandidate.normalize();

        // 此时：
        // - u、v 都在同一个“垂直平面”里（包含 world up）
        // - 这个平面经过 circleCenter 和 center_

        // 2. 按均分角度在这个平面上画圆
        // -----------------------------------
        for (int i = 0; i < count; i++) {
            double step = 2.0 * Math.PI * i / count;       // 均分角
            double theta = first_rot + step;               // 加上初始相位
            double cos = Math.cos(theta);
            double sin = Math.sin(theta);
            // 圆上的点：circleCenter + radius * (cosθ·u + sinθ·v)
            Vec3 pos = circleCenter.add(u.scale(radius * cos)).add(v.scale(radius * sin));
            // 这里不做扩散和速度偏移，只是精确在圆上丢粒子
            level.sendParticles(particleOptions, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }
}
