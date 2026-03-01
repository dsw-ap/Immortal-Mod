package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill26_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.option.OrbitParticleWater;
import com.tearhpi.immortal.particle.option.OrbitParticleWater_;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class Skill26 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(26,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(26,player);
            //SP消耗设置
            ModSkills.consumeSP(26,player);
            //吟唱设置
            ModSkills.setCharmingTime(26,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(26 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 15.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        //粒子
        double double_base = player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get());
        //单位长度:r/20
        Vec3 dir3D = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 center = player.position().add(dir3D.normalize().scale(9));
        float pi = (float) Math.PI;
        //外6圆
        //主环
        drawCircle(level,0.25,center,center,15*double_base,200,200);
        drawPolynomial(level,0.25,center,center,350,200,1,-2,12*double_base,3*double_base);
        drawPolynomialWithRot(level,0.25,center,center,350,200,1,-2,12*double_base,3*double_base, pi/3);

        //漩涡
        Random rand = new Random();
        int MAX_VALUE = 600;
        for (int i = 0;i < MAX_VALUE;i++) {
            level.sendParticles(new OrbitParticleWater_(center,1.8,200),center.x+Mth.cos(Mth.PI/64*i)*r/1.5*(0.4+0.6*i/MAX_VALUE), center.y+0.05*i, center.z+Mth.sin(Mth.PI/64*i)*r/1.5*(0.4+0.6*i/MAX_VALUE),1, 0, 0, 0, 0);
            level.sendParticles(new OrbitParticleWater_(center,1.8,200),center.x+Mth.cos(Mth.PI/64*i+Mth.PI/2)*r/1.5*(0.4+0.6*i/MAX_VALUE), center.y+0.05*i, center.z+Mth.sin(Mth.PI/64*i+Mth.PI/2)*r/1.5*(0.4+0.6*i/MAX_VALUE),1, 0, 0, 0, 0);
            level.sendParticles(new OrbitParticleWater_(center,1.8,200),center.x+Mth.cos(Mth.PI/64*i+Mth.PI)*r/1.5*(0.4+0.6*i/MAX_VALUE), center.y+0.05*i, center.z+Mth.sin(Mth.PI/64*i+Mth.PI)*r/1.5*(0.4+0.6*i/MAX_VALUE),1, 0, 0, 0, 0);
            level.sendParticles(new OrbitParticleWater_(center,1.8,200),center.x+Mth.cos(Mth.PI/64*i+Mth.PI*3/2)*r/1.5*(0.4+0.6*i/MAX_VALUE), center.y+0.05*i, center.z+Mth.sin(Mth.PI/64*i+Mth.PI*3/2)*r/1.5*(0.4+0.6*i/MAX_VALUE),1, 0, 0, 0, 0);
            //level.sendParticles(ParticleTypes.FLAME,center.x+Mth.cos(Mth.PI/64*i)*r/1.5*(0.4+0.6*i/MAX_VALUE), center.y+0.05*i, center.z+Mth.sin(Mth.PI/64*i)*r/1.5*(0.4+0.6*i/MAX_VALUE),1, 0, 0, 0, 0);
            //外围线条
            //level.sendParticles(new OrbitParticleWater(center,3.6,200),center.x+Mth.cos(Mth.PI/64*i)*r/1.2*(0.8+0.2*i/MAX_VALUE), center.y+0.005*i, center.z+Mth.sin(Mth.PI/64*i)*r/1.2*(0.8+0.2*i/MAX_VALUE),1, 0, 0, 0, 0);
        }

        Vec3 motion = new Vec3(0,0,0);
        Skill26_Entity e = Skill26_Entity.spawn(level, player, center, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }

    public static void drawCircle(ServerLevel level,double omega,Vec3 center_rot,Vec3 circleCenter,double radius,int count,int lifetime) {
        //参数备注:level:世界;omega:角速度;center_rot:旋转中心;circleCenter:圆心;radius:圆半径;count:生成粒子数量;lifetime:存在时间
        for (int i = 0; i < count; i++) {
            float step = (float) (i * Math.PI * 2 / count);
            level.sendParticles(new OrbitParticleWater(center_rot,omega,lifetime),circleCenter.x+radius* Mth.cos(step), circleCenter.y+0.5, circleCenter.z+radius*Mth.sin(step),1, 0, 0, 0, 0);
        }
    }
    public static void drawArc(ServerLevel level,double omega,Vec3 center_rot,Vec3 circleCenter,double radius,int count,int lifetime,double start,double end) {
        //参数备注:level:世界;omega:角速度;center_rot:旋转中心;circleCenter:圆心;radius:圆半径;count:生成粒子数量;lifetime:存在时间;start/end:开始/结束时间点
        for (int i = 0; i < count; i++) {
            double interval = end - start;
            float step = (float) (i * interval / count+start);
            level.sendParticles(new OrbitParticleWater(center_rot,omega,lifetime),circleCenter.x+radius*Mth.cos(step), circleCenter.y+0.5, circleCenter.z+radius*Mth.sin(step),1, 0, 0, 0, 0);
        }
    }
    public static void drawPolynomial(ServerLevel level,double omega,Vec3 center_rot,Vec3 circleCenter,int count,int lifetime,int omega1,int omega2,double r1,double r2) {
        //参数备注:level:世界;omega:角速度;center_rot:旋转中心;circleCenter:圆心;count:生成粒子数量;lifetime:存在时间;omega1,omega2:角速度乘区1,2;r1,r2:子母圆半径r1,r2
        drawPolynomialWithRot(level,omega,center_rot,circleCenter,count,lifetime,omega1,omega2,r1,r2,0);
    }
    public static void drawPolynomialWithRot(ServerLevel level,double omega,Vec3 center_rot,Vec3 circleCenter,int count,int lifetime,int omega1,int omega2,double r1,double r2,float rot) {
        //参数备注:level:世界;omega:角速度;center_rot:旋转中心;circleCenter:圆心;count:生成粒子数量;lifetime:存在时间;omega1,omega2:角速度乘区1,2;r1,r2:子母圆半径r1,r2
        for (int i = 0; i < count; i++) {
            float step = (float) (i * Math.PI * 2 / count);
            level.sendParticles(new OrbitParticleWater(center_rot,omega,lifetime),circleCenter.x+r1*Mth.cos(omega1*step+rot)+r2*Mth.cos(omega2*step+rot), circleCenter.y+0.5, circleCenter.z+r1*Mth.sin(omega1*step+rot)+r2*Mth.sin(omega2*step+rot),1, 0, 0, 0, 0);
        }
    }
    public static void drawLine(ServerLevel level,double omega,Vec3 center_rot,Vec3 Center,int count,int lifetime,double start,double end,float slope) {
        for (int i = 0; i < count; i++) {
            float step = (float) (start + i * (end-start) / count);
            level.sendParticles(new OrbitParticleWater(center_rot,omega,lifetime),Center.x+step*Mth.cos(slope), Center.y, Center.z+step*Mth.sin(slope),1, 0, 0, 0, 0);
        }
    }
}
