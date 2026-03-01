package com.tearhpi.immortal.particle;

import com.tearhpi.immortal.particle.option.OrbitParticleAir_vertical;
import com.tearhpi.immortal.particle.option.OrbitParticleWater;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.phys.Vec3;

public class Skill36_particle_vert extends TextureSheetParticle {
    private final Vec3 center;
    private final double omegaPerTick;
    private double angle;
    private final double radius;
    private final SpriteSet sprites;

    private Vec3 basisU;   // 垂直平面内的单位向量1
    private Vec3 basisV;   // 垂直平面内的单位向量2

    protected Skill36_particle_vert(ClientLevel level, double x, double y, double z, OrbitParticleAir_vertical opts, SpriteSet sprites) {
        super(level, x, y, z, 0, 0, 0);
        this.sprites = sprites;
        this.pickSprite(sprites);

        this.center = opts.center;
        this.omegaPerTick = opts.omegaRadPerSec / 20.0;
        Vec3 r = new Vec3(x - center.x, y-center.y, z - center.z);
        this.radius = Math.max(1.0e-6, r.length());
        this.angle = Math.atan2(r.z, r.x);

        this.lifetime = Math.max(1, opts.lifetimeTicks);
        this.quadSize = 0.2f;
        this.gravity = 0.0f;
        this.hasPhysics = false;
        initOrbit(center, omegaPerTick);
    }
    public void initOrbit(Vec3 center, double omegaPerTick) {
        Vec3 offset0 = this.getPos().subtract(center);
        if (this.radius < 1.0e-6) {
            this.basisU = new Vec3(1, 0, 0);
        } else {
            this.basisU = offset0.normalize(); // 单位向量
        }
        Vec3 up = new Vec3(0, 1, 0);
        Vec3 v = up.subtract(this.basisU.scale(up.dot(this.basisU)));
        if (v.lengthSqr() < 1.0e-6) {
            v = new Vec3(1, 0, 0).subtract(this.basisU.scale(this.basisU.dot(new Vec3(1, 0, 0))));
        }
        this.basisV = v.normalize();
        if (basisU.x > 0) {
            basisV = basisV.scale(-1);
        }
        //特殊化处理
        //System.out.println(basisV+",TEST,"+basisU+","+this.getPos()+","+this.center);
        this.angle = 0.0;
    }

    @Override
    public void tick() {
        this.xo = this.x; this.yo = this.y; this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
            return;
        }

        // 角速度推进
        this.angle += this.omegaPerTick;

        double cos = Math.cos(this.angle);
        double sin = Math.sin(this.angle);

        // 垂直平面内绕圈
        Vec3 offset = basisU.scale(radius * cos).add(basisV.scale(radius * sin));
        Vec3 pos = center.add(offset);

        this.setPos(pos.x, pos.y, pos.z);
    }



    @Override public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class OrbitParticleProvider implements ParticleProvider<OrbitParticleAir_vertical> {
        private final SpriteSet sprites;
        public OrbitParticleProvider(SpriteSet sprites) { this.sprites = sprites; }

        @Override
        public Particle createParticle(OrbitParticleAir_vertical opts, ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
            return new Skill36_particle_vert(level, x, y, z, opts, sprites);
        }
    }
}
//((ServerLevel) level).sendParticles(new OrbitParticleOptions(new Vec3(cx, cy, cz),2.0,60),startX, startY, startZ,1, 0, 0, 0, 0);
//Minecraft.getInstance().level.addParticle(new OrbitParticleOptions(new Vec3(cx, cy, cz), 2.0, 60),startX, startY, startZ, 0,0,0);