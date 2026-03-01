package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill15_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.option.OrbitParticleOptions;
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

public class Skill15 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(15,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(15,player);
            //SP消耗设置
            ModSkills.consumeSP(15,player);
            //吟唱设置
            ModSkills.setCharmingTime(15,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(15,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 13.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);
        //生成实体
        Skill15_Entity e = Skill15_Entity.spawn(level, player,player.position().add(new Vec3(0.0,10.0,0.0)),new Vec3(0.0,0.0,0.0),r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
        //粒子
        double double_base = player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get())*r/10;
        //单位长度:r/20
        Vec3 center = new Vec3(player.getX(), player.getY(), player.getZ());
        double pi = Math.PI;
        //外6圆
        drawCircle(level,0.25,player.position(),player.position().add(0.0*double_base,0.0,8.0*double_base),2*double_base,50,200);
        drawCircle(level,0.25,player.position(),player.position().add(6.8*double_base,0.0,4.0*double_base),2*double_base,50,200);
        drawCircle(level,0.25,player.position(),player.position().add(6.8*double_base,0.0,-4.0*double_base),2*double_base,50,200);
        drawCircle(level,0.25,player.position(),player.position().add(0.0*double_base,0.0,-8.0*double_base),2*double_base,50,200);
        drawCircle(level,0.25,player.position(),player.position().add(-6.8*double_base,0.0,4.0*double_base),2*double_base,50,200);
        drawCircle(level,0.25,player.position(),player.position().add(-6.8*double_base,0.0,-4.0*double_base),2*double_base,50,200);
        //外6圆中八角形
        drawPolynomial(level,0.25,player.position(),player.position().add(0.0*double_base,0.0,8.0*double_base),200,200,3,-5,1.3*double_base,0.7*double_base);
        drawPolynomial(level,0.25,player.position(),player.position().add(6.8*double_base,0.0,4.0*double_base),200,200,3,-5,1.3*double_base,0.7*double_base);
        drawPolynomial(level,0.25,player.position(),player.position().add(6.8*double_base,0.0,-4.0*double_base),200,200,3,-5,1.3*double_base,0.7*double_base);
        drawPolynomial(level,0.25,player.position(),player.position().add(0.0*double_base,0.0,-8.0*double_base),200,200,3,-5,1.3*double_base,0.7*double_base);
        drawPolynomial(level,0.25,player.position(),player.position().add(-6.8*double_base,0.0,4.0*double_base),200,200,3,-5,1.3*double_base,0.7*double_base);
        drawPolynomial(level,0.25,player.position(),player.position().add(-6.8*double_base,0.0,-4.0*double_base),200,200,3,-5,1.3*double_base,0.7*double_base);
        //外圆弧线
        //r=9
        drawArc(level,0.25,player.position(),player.position(),9*double_base,40,200,-pi/6+0.205,pi/6-0.205);
        drawArc(level,0.25,player.position(),player.position(),9*double_base,40,200,pi/6+0.205,pi/2-0.205);
        drawArc(level,0.25,player.position(),player.position(),9*double_base,40,200,pi/2+0.205,5*pi/6-0.205);
        drawArc(level,0.25,player.position(),player.position(),9*double_base,40,200,5*pi/6+0.205,7*pi/6-0.205);
        drawArc(level,0.25,player.position(),player.position(),9*double_base,40,200,7*pi/6+0.205,9*pi/6-0.205);
        drawArc(level,0.25,player.position(),player.position(),9*double_base,40,200,9*pi/6+0.205,11*pi/6-0.205);
        //r=8.5
        drawArc(level,0.25,player.position(),player.position(),8.5*double_base,35,200,-pi/6+0.238,pi/6-0.238);
        drawArc(level,0.25,player.position(),player.position(),8.5*double_base,35,200,pi/6+0.238,pi/2-0.238);
        drawArc(level,0.25,player.position(),player.position(),8.5*double_base,35,200,pi/2+0.238,5*pi/6-0.238);
        drawArc(level,0.25,player.position(),player.position(),8.5*double_base,35,200,5*pi/6+0.238,7*pi/6-0.238);
        drawArc(level,0.25,player.position(),player.position(),8.5*double_base,35,200,7*pi/6+0.238,9*pi/6-0.238);
        drawArc(level,0.25,player.position(),player.position(),8.5*double_base,35,200,9*pi/6+0.238,11*pi/6-0.238);
        //r=8
        drawArc(level,0.25,player.position(),player.position(),8*double_base,30,200,-pi/6+0.251,pi/6-0.251);
        drawArc(level,0.25,player.position(),player.position(),8*double_base,30,200,pi/6+0.251,pi/2-0.251);
        drawArc(level,0.25,player.position(),player.position(),8*double_base,30,200,pi/2+0.251,5*pi/6-0.251);
        drawArc(level,0.25,player.position(),player.position(),8*double_base,30,200,5*pi/6+0.251,7*pi/6-0.251);
        drawArc(level,0.25,player.position(),player.position(),8*double_base,30,200,7*pi/6+0.251,9*pi/6-0.251);
        drawArc(level,0.25,player.position(),player.position(),8*double_base,30,200,9*pi/6+0.251,11*pi/6-0.251);
        //内双六芒星
        drawPolynomial(level,0.25,player.position(),player.position(),150,200,1,-2,6.5*double_base,1.5*double_base);
        drawPolynomialWithRot(level,0.25,player.position(),player.position(),150,200,1,-2,6.5*double_base,1.5*double_base, (float) (pi/3));
        drawPolynomialWithRot(level,0.25,player.position(),player.position(),150,200,1,-2,4.2*double_base,1.5*double_base, (float) (pi/6));
        drawPolynomialWithRot(level,0.25,player.position(),player.position(),150,200,1,-2,4.2*double_base,1.5*double_base, (float) (pi/2));
        //线条
        drawLine(level,0.25,player.position(),player.position(),50,200,-8*double_base,8*double_base, (float) (0));
        drawLine(level,0.25,player.position(),player.position(),50,200,-8*double_base,8*double_base, (float) (pi/3));
        drawLine(level,0.25,player.position(),player.position(),50,200,-8*double_base,8*double_base, (float) (2*pi/3));
        drawLine(level,0.25,player.position(),player.position(),50,200,-5.7*double_base,5.7*double_base, (float) (pi));
        drawLine(level,0.25,player.position(),player.position(),50,200,-5.7*double_base,5.7*double_base, (float) (pi/6));
        drawLine(level,0.25,player.position(),player.position(),50,200,-5.7*double_base,5.7*double_base, (float) (-pi/6));
    }
    public static void drawCircle(ServerLevel level,double omega,Vec3 center_rot,Vec3 circleCenter,double radius,int count,int lifetime) {
        //参数备注:level:世界;omega:角速度;center_rot:旋转中心;circleCenter:圆心;radius:圆半径;count:生成粒子数量;lifetime:存在时间
        for (int i = 0; i < count; i++) {
            float step = (float) (i * Math.PI * 2 / count);
            level.sendParticles(new OrbitParticleOptions(center_rot,omega,lifetime),circleCenter.x+radius*Mth.cos(step), circleCenter.y+0.5, circleCenter.z+radius*Mth.sin(step),1, 0, 0, 0, 0);
        }
    }
    public static void drawArc(ServerLevel level,double omega,Vec3 center_rot,Vec3 circleCenter,double radius,int count,int lifetime,double start,double end) {
        //参数备注:level:世界;omega:角速度;center_rot:旋转中心;circleCenter:圆心;radius:圆半径;count:生成粒子数量;lifetime:存在时间;start/end:开始/结束时间点
        for (int i = 0; i < count; i++) {
            double interval = end - start;
            float step = (float) (i * interval / count+start);
            level.sendParticles(new OrbitParticleOptions(center_rot,omega,lifetime),circleCenter.x+radius*Mth.cos(step), circleCenter.y+0.5, circleCenter.z+radius*Mth.sin(step),1, 0, 0, 0, 0);
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
            level.sendParticles(new OrbitParticleOptions(center_rot,omega,lifetime),circleCenter.x+r1*Mth.cos(omega1*step+rot)+r2*Mth.cos(omega2*step+rot), circleCenter.y+0.5, circleCenter.z+r1*Mth.sin(omega1*step+rot)+r2*Mth.sin(omega2*step+rot),1, 0, 0, 0, 0);
        }
    }
    public static void drawLine(ServerLevel level,double omega,Vec3 center_rot,Vec3 Center,int count,int lifetime,double start,double end,float slope) {
        for (int i = 0; i < count; i++) {
            float step = (float) (start + i * (end-start) / count);
            level.sendParticles(new OrbitParticleOptions(center_rot,omega,lifetime),Center.x+step*Mth.cos(slope), Center.y, Center.z+step*Mth.sin(slope),1, 0, 0, 0, 0);
        }
    }
}
