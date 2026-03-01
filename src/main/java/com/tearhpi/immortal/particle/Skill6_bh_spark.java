package com.tearhpi.immortal.particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
        import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class Skill6_bh_spark extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected Skill6_bh_spark(ClientLevel level, double x, double y, double z,
                                     double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
        this.lifetime = 12 + this.random.nextInt(10);
        this.gravity = 0.0F;
        this.hasPhysics = false;

        this.quadSize = 0.06F + random.nextFloat() * 0.06F;
        this.alpha = 0.0F;

        // 偏蓝紫的亮点
        this.rCol = 0.5f + random.nextFloat() * 0.3f;
        this.gCol = 0.2f + random.nextFloat() * 0.2f;
        this.bCol = 0.8f + random.nextFloat() * 0.2f;

        // 略带向外/向内的速度（由调用方注入）
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);

        float f = (float)this.age / (float)this.lifetime;
        if (f < 0.2f) this.alpha = f / 0.2f;
        else this.alpha = Mth.clamp(1f - (f - 0.2f) / 0.8f, 0f, 1f);

        // 慢慢变小
        this.quadSize *= 0.96f;
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
            return new Skill6_bh_spark(level, x, y, z, vx, vy, vz, sprites);
        }
    }
}