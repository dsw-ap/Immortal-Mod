package com.tearhpi.immortal.particle.option;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class OrbitParticleWater implements ParticleOptions {
    public final Vec3 center;
    public final double omegaRadPerSec;
    public final int    lifetimeTicks;


    public OrbitParticleWater(Vec3 center, double omegaRadPerSec, int lifetimeTicks) {
        this.center = center;
        this.omegaRadPerSec = omegaRadPerSec;
        this.lifetimeTicks = lifetimeTicks;
    }

    public static final Codec<OrbitParticleWater> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    Vec3.CODEC.fieldOf("center").forGetter(o -> o.center),
                    Codec.DOUBLE.fieldOf("omega").forGetter(o -> o.omegaRadPerSec),
                    Codec.INT.fieldOf("life").forGetter(o -> o.lifetimeTicks)
            ).apply(i, OrbitParticleWater::new));

    public static final Deserializer<OrbitParticleWater> DESERIALIZER =
            new Deserializer<>() {
                @Override
                public OrbitParticleWater fromCommand(ParticleType<OrbitParticleWater> type, StringReader reader)
                        throws CommandSyntaxException {
                    // 例：orbit <cx> <cy> <cz> <omega> <life>
                    reader.expect(' ');
                    double cx = reader.readDouble();
                    reader.expect(' ');
                    double cy = reader.readDouble();
                    reader.expect(' ');
                    double cz = reader.readDouble();
                    reader.expect(' ');
                    double omega = reader.readDouble();
                    reader.expect(' ');
                    int life = reader.readInt();
                    return new OrbitParticleWater(new Vec3(cx, cy, cz), omega, life);
                }

                @Override
                public OrbitParticleWater fromNetwork(ParticleType<OrbitParticleWater> type, FriendlyByteBuf buf) {
                    double cx = buf.readDouble();
                    double cy = buf.readDouble();
                    double cz = buf.readDouble();
                    double om = buf.readDouble();
                    int life = buf.readVarInt();
                    return new OrbitParticleWater(new Vec3(cx, cy, cz), om, life);
                }
            };

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(center.x);
        buf.writeDouble(center.y);
        buf.writeDouble(center.z);
        buf.writeDouble(omegaRadPerSec);
        buf.writeVarInt(lifetimeTicks);
    }

    @Override
    public String writeToString() {
        return String.format("orbit center=(%.3f,%.3f,%.3f) omega=%.3f life=%d",
                center.x, center.y, center.z, omegaRadPerSec, lifetimeTicks);
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.SKILL21_Particle.get();
    }
}