package com.tearhpi.immortal.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class Skill31_particle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public Skill31_particle(ClientLevel level, double x, double y, double z,
                            double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
        this.lifetime = 5 + this.random.nextInt(2);
        this.gravity = 0.0F;
        this.hasPhysics = false;

        this.quadSize = 0.08F + random.nextFloat() * 0.08F;
        this.alpha = 0.0F; // 先淡入

        // 轻微向内收缩
        this.xd = vx * 0.02;
        this.yd = vy * 0.02;
        this.zd = vz * 0.02;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);
        float f = (float)this.age / (float)this.lifetime;
        // 前10%淡入，后90%淡出
        if (f < 0.0f) this.alpha = Mth.clamp(f / 0.1f, 0f, 1f);
        else this.alpha = Mth.clamp(1f - (f - 0.1f) / 0.9f, 0f, 1f);

        // 轻微缩放
        this.quadSize *= 0.9f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double vx, double vy, double vz) {
            return new Skill9_particle_fire(level, x, y, z, vx, vy, vz, sprites);
        }
    }
}
