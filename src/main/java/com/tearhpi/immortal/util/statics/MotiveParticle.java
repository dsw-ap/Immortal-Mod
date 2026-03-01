package com.tearhpi.immortal.util.statics;

import com.tearhpi.immortal.particle.option.OrbitParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MotiveParticle {
    public static void drawCircle(ServerLevel level, double first_rot, Vec3 circleCenter, double radius, int count, ParticleOptions particleOptions) {
        //ParticleType传入参数示例:new OrbitParticleOptions(center_rot,omega,lifetime)
        //参数备注:level:世界;first_rot:初始相位;omega:角速度;center_rot:旋转中心;circleCenter:圆心;radius:圆半径;count:生成粒子数量;lifetime:存在时间
        for (int i = 0; i < count; i++) {
            float step = (float) (i * Math.PI * 2 / count);
            level.sendParticles(particleOptions,circleCenter.x+radius* Mth.cos((float) (step+first_rot)), circleCenter.y+0.5, circleCenter.z+radius*Mth.sin((float) (step+first_rot)),1, 0, 0, 0, 0);
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
