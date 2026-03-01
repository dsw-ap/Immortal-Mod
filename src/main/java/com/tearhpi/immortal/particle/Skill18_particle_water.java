package com.tearhpi.immortal.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class Skill18_particle_water extends TextureSheetParticle {
    private final SpriteSet sprites;
    public Skill18_particle_water(ClientLevel p_107631_, double p_107632_, double p_107633_, double p_107634_, double p_107635_, double p_107636_, double p_107637_, SpriteSet sprites) {
        super(p_107631_, p_107632_, p_107633_, p_107634_, p_107635_, p_107636_, p_107637_);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);

        this.friction = 0.96F;
        this.xd = this.xd * (double)0.01F + p_107635_;
        this.yd = this.yd * (double)0.01F + p_107636_;
        this.zd = this.zd * (double)0.01F + p_107637_;
        this.x += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.y += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.z += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 13;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double p_106817_, double p_106818_, double p_106819_) {
        this.setBoundingBox(this.getBoundingBox().move(p_106817_, p_106818_, p_106819_));
        this.setLocationFromBoundingbox();
    }

    public float getQuadSize(float p_106824_) {
        float f = ((float)this.age + p_106824_) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    public int getLightColor(float p_106821_) {
        float f = ((float)this.age + p_106821_) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(p_106821_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }
    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);
        // 前50%淡入，后50%淡出
        float f = (float)this.age / (float)this.lifetime;
        if (f < 0.0f) this.alpha = Mth.clamp(f / 0.5f, 0f, 1f);
        else this.alpha = Mth.clamp(1f - (f - 0.5f) / 0.5f, 0f, 1f);
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double vx, double vy, double vz) {
            return new Skill18_particle_water(level, x, y, z, vx, vy, vz,sprites);
        }
    }
}
