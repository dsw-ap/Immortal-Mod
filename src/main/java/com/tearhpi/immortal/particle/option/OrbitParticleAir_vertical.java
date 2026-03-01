package com.tearhpi.immortal.particle.option;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class OrbitParticleAir_vertical implements ParticleOptions {
    public final Vec3 center;
    public final double omegaRadPerSec;
    public final int    lifetimeTicks;


    public OrbitParticleAir_vertical(Vec3 center, double omegaRadPerSec, int lifetimeTicks) {
        this.center = center;
        this.omegaRadPerSec = omegaRadPerSec;
        this.lifetimeTicks = lifetimeTicks;
    }

    public static final Codec<OrbitParticleAir_vertical> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    Vec3.CODEC.fieldOf("center").forGetter(o -> o.center),
                    Codec.DOUBLE.fieldOf("omega").forGetter(o -> o.omegaRadPerSec),
                    Codec.INT.fieldOf("life").forGetter(o -> o.lifetimeTicks)
            ).apply(i, OrbitParticleAir_vertical::new));

    public static final Deserializer<OrbitParticleAir_vertical> DESERIALIZER =
            new Deserializer<>() {
                @Override
                public OrbitParticleAir_vertical fromCommand(ParticleType<OrbitParticleAir_vertical> type, StringReader reader)
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
                    return new OrbitParticleAir_vertical(new Vec3(cx, cy, cz), omega, life);
                }

                @Override
                public OrbitParticleAir_vertical fromNetwork(ParticleType<OrbitParticleAir_vertical> type, FriendlyByteBuf buf) {
                    double cx = buf.readDouble();
                    double cy = buf.readDouble();
                    double cz = buf.readDouble();
                    double om = buf.readDouble();
                    int life = buf.readVarInt();
                    return new OrbitParticleAir_vertical(new Vec3(cx, cy, cz), om, life);
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
        return ModParticles.SKILL36_Particle_vert.get();
    }
}