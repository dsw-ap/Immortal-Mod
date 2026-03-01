package com.tearhpi.immortal.util.statics;

import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExplosionParticle {
    public static void horizonParticleExpServerLevel(ServerLevel level, SimpleParticleType particle, double speed, int count, Vec3 center){
        for(int i = 0; i < count; i++){
            double step = 2*Mth.PI/count;
            level.sendParticles(particle,center.x,center.y,center.z,0,Mth.cos((float) (step*i)),0,Mth.sin((float) (step*i)),speed);
        }
    }
    public static void horizonParticleExpClientLevel(Level level, SimpleParticleType particle, double speed, int count, Vec3 center){
        for(int i = 0; i < count; i++){
            double step = 2*Mth.PI/count;
            level.addParticle(particle,center.x,center.y,center.z,Mth.cos((float) (step*i))*speed,0,Mth.sin((float) (step*i))*speed);
        }
    }
    public static void tiltParticleExpServerLevel(ServerLevel level, SimpleParticleType particle, double speed, int count, Vec3 center,Vec3 normalVector){
        //normalVector标准为方向向量,向上是(0,1,0)[不需要标准化]
        normalVector = normalVector.normalize();
        Vec3 u1 = normalVector.cross(new Vec3(0.0,1.0,0.0)).normalize();
        Vec3 u2 = u1.cross(normalVector).normalize();
        for(int i = 0; i < count; i++){
            double step = 2*Mth.PI/count;
            float val = (float) (step*i);
            Vec3 f = u1.scale(Mth.cos(val)).add(u2.scale(Mth.sin(val)));
            level.sendParticles(particle,center.x,center.y,center.z,0,f.x,f.y,f.z,speed);
        }
    }
    public static void tiltParticleExpClientLevel(Level level, SimpleParticleType particle, double speed, int count, Vec3 center,Vec3 normalVector){
        //normalVector标准为方向向量,向上是(0,1,0)[不需要标准化]
        normalVector = normalVector.normalize();
        Vec3 u1 = normalVector.cross(new Vec3(0.0,1.0,0.0)).normalize();
        Vec3 u2 = u1.cross(normalVector).normalize();
        for(int i = 0; i < count; i++){
            double step = 2*Mth.PI/count;
            float val = (float) (step*i);
            Vec3 f = u1.scale(Mth.cos(val)).add(u2.scale(Mth.sin(val)));
            level.addParticle(particle,center.x,center.y,center.z,f.x,f.y,f.z);
        }
    }
}
