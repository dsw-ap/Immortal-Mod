package com.tearhpi.immortal.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
        import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class Skill6_bh_ring extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected Skill6_bh_ring(ClientLevel level, double x, double y, double z,
                                    double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
        this.lifetime = 16;
        this.gravity = 0.0F;
        this.hasPhysics = false;

        this.quadSize = 0.05F; // 初始小
        this.alpha = 0.7F;

        this.rCol = 0.9f; this.gCol = 0.9f; this.bCol = 1.0f; // 冷白色
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);

        float f = (float)this.age / (float)this.lifetime;
        // 半径随时间扩大（用xd/yd/zd 作为朝外的法向量倍率）
        this.x += this.xd;
        this.y += this.yd * 0.2; // Y 缩小，保持“环”更平
        this.z += this.zd;

        // 粒子本身随时间放大一些
        this.quadSize = 0.05f + f * 0.18f;

        // 渐隐
        this.alpha = Mth.clamp(0.8f * (1f - f), 0f, 0.8f);
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
            return new Skill6_bh_ring(level, x, y, z, vx, vy, vz, sprites);
        }
    }
}
