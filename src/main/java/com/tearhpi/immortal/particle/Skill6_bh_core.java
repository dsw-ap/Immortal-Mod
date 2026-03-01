package com.tearhpi.immortal.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class Skill6_bh_core extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected Skill6_bh_core(ClientLevel level, double x, double y, double z,
                                    double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
        this.lifetime = 30 + this.random.nextInt(8); // 10~17 tick
        this.gravity = 0.0F;
        this.hasPhysics = false;

        this.quadSize = 0.08F + random.nextFloat() * 0.08F;
        this.alpha = 0.0F; // 先淡入

        // 颜色：深紫→近黑
        //float shade = 0.15F + random.nextFloat() * 0.1F;
        //this.rCol = shade * 0.6f;
        //this.gCol = shade * 0.2f;
        //this.bCol = shade;

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
        // 前30%淡入，后70%淡出
        if (f < 0.3f) this.alpha = Mth.clamp(f / 0.3f, 0f, 1f);
        else this.alpha = Mth.clamp(1f - (f - 0.3f) / 0.7f, 0f, 1f);

        // 轻微缩放
        this.quadSize *= 0.985f;
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
            return new Skill6_bh_core(level, x, y, z, vx, vy, vz, sprites);
        }
    }
}
