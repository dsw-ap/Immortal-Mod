package com.tearhpi.immortal.particle;

import com.tearhpi.immortal.particle.option.OrbitParticleWater;
import com.tearhpi.immortal.particle.option.OrbitParticleWater_;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class Skill26_particle extends TextureSheetParticle {
    private final Vec3 center;
    private final double omegaPerTick;
    private double angle;
    private final double radius;
    private final SpriteSet sprites;

    protected Skill26_particle(ClientLevel level, double x, double y, double z, OrbitParticleWater_ opts, SpriteSet sprites) {
        super(level, x, y, z, 0, 0, 0);
        this.sprites = sprites;
        this.pickSprite(sprites);

        this.center = opts.center;
        this.omegaPerTick = opts.omegaRadPerSec / 20.0;
        Vec3 r = new Vec3(x - center.x, 0, z - center.z);
        this.radius = Math.max(1.0e-6, r.length());
        this.angle = Math.atan2(r.z, r.x);

        this.lifetime = Math.max(1, opts.lifetimeTicks);
        this.quadSize = 0.2f;
        this.gravity = 0.0f;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        this.xo = this.x; this.yo = this.y; this.zo = this.z;
        if (this.lifetime-- <= 0) { this.remove(); return; }
        this.angle += this.omegaPerTick* (Mth.sin((float) Math.toRadians(this.lifetime*15))*0.7+1.7);

        double nx = center.x + radius * Math.cos(this.angle);
        double nz = center.z + radius * Math.sin(this.angle);
        this.setPos(nx, this.y, nz);
    }

    @Override public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class OrbitParticleProvider implements ParticleProvider<OrbitParticleWater_> {
        private final SpriteSet sprites;
        public OrbitParticleProvider(SpriteSet sprites) { this.sprites = sprites; }

        @Override
        public Particle createParticle(OrbitParticleWater_ opts, ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
            return new Skill26_particle(level, x, y, z, opts, sprites);
        }
    }
}
//((ServerLevel) level).sendParticles(new OrbitParticleOptions(new Vec3(cx, cy, cz),2.0,60),startX, startY, startZ,1, 0, 0, 0, 0);
//Minecraft.getInstance().level.addParticle(new OrbitParticleOptions(new Vec3(cx, cy, cz), 2.0, 60),startX, startY, startZ, 0,0,0);