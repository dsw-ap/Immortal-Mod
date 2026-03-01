package com.tearhpi.immortal.particle;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import com.tearhpi.immortal.particle.option.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, Immortal.MODID);
    //水系通用
    public static final RegistryObject<SimpleParticleType> WATER =
            PARTICLES.register("water", () -> new SimpleParticleType(true));
    //风系通用
    public static final RegistryObject<SimpleParticleType> AIR =
            PARTICLES.register("air", () -> new SimpleParticleType(true));
    //土系通用
    public static final RegistryObject<SimpleParticleType> EARTH =
            PARTICLES.register("earth", () -> new SimpleParticleType(true));
    //光系通用
    public static final RegistryObject<SimpleParticleType> LIGHT =
            PARTICLES.register("light", () -> new SimpleParticleType(true));
    //暗系通用
    public static final RegistryObject<SimpleParticleType> DARKNESS =
            PARTICLES.register("darkness", () -> new SimpleParticleType(true));
    //WandNormalAttack
    public static final RegistryObject<SimpleParticleType> WandNormalAttack_Fire =
            PARTICLES.register("wandnormalattack_fire", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WandNormalAttack_Water =
            PARTICLES.register("wandnormalattack_water", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WandNormalAttack_Air =
            PARTICLES.register("wandnormalattack_air", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WandNormalAttack_Earth =
            PARTICLES.register("wandnormalattack_earth", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WandNormalAttack_Light =
            PARTICLES.register("wandnormalattack_light", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WandNormalAttack_Darkness =
            PARTICLES.register("wandnormalattack_darkness", () -> new SimpleParticleType(true));

    //Skill_6
    public static final RegistryObject<SimpleParticleType> SKILL6_BH_CORE =
            PARTICLES.register("skill6_bh_core", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SKILL6_BH_SPARK =
            PARTICLES.register("skill6_bh_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SKILL6_BH_RING =
            PARTICLES.register("skill6_bh_ring", () -> new SimpleParticleType(true));
    //Skill_7
    public static final RegistryObject<SimpleParticleType> SKILL7_Particle =
            PARTICLES.register("skill7_particle", () -> new SimpleParticleType(true));
    //Skill_9
    public static final RegistryObject<SimpleParticleType> SKILL9_Particle_Fire =
            PARTICLES.register("skill9_particle_fire", () -> new SimpleParticleType(true));
    //Skill_10
    public static final RegistryObject<SimpleParticleType> SKILL10_Particle =
            PARTICLES.register("skill10_particle", () -> new SimpleParticleType(true));
    //Skill11
    public static final RegistryObject<ParticleType<OrbitParticleOptions>> SKILL11_Particle =
            PARTICLES.register("skill11_particle", () -> new ParticleType<>(false, OrbitParticleOptions.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleOptions> codec() {
                    return OrbitParticleOptions.CODEC;
                }
            });
    //Skill18_particle
    public static final RegistryObject<SimpleParticleType> SKILL18_Particle_FOG =
            PARTICLES.register("skill18_particle_fog", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SKILL18_Particle_ICE =
            PARTICLES.register("skill18_particle_ice", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SKILL18_Particle_WATER =
            PARTICLES.register("skill18_particle_water", () -> new SimpleParticleType(true));
    //Skill21_particle
    public static final RegistryObject<ParticleType<OrbitParticleWater>> SKILL21_Particle =
            PARTICLES.register("skill21_particle", () -> new ParticleType<>(false, OrbitParticleWater.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleWater> codec() {
                    return OrbitParticleWater.CODEC;
                }
            });
    //Skill22_particle
    public static final RegistryObject<SimpleParticleType> SKILL22_Particle =
            PARTICLES.register("skill22_particle", () -> new SimpleParticleType(true));
    //Skill26_particle
    public static final RegistryObject<ParticleType<OrbitParticleWater_>> SKILL26_Particle =
            PARTICLES.register("skill26_particle", () -> new ParticleType<>(false, OrbitParticleWater_.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleWater_> codec() {
                    return OrbitParticleWater_.CODEC;
                }
            });
    //Skill31_particle
    public static final RegistryObject<SimpleParticleType> SKILL31_Particle =
            PARTICLES.register("skill31_particle", () -> new SimpleParticleType(true));
    //Skill35_particle
    public static final RegistryObject<SimpleParticleType> SKILL35_Particle =
            PARTICLES.register("skill35_particle", () -> new SimpleParticleType(true));
    //Skill36_particle * 2
    public static final RegistryObject<ParticleType<OrbitParticleAir>> SKILL36_Particle =
            PARTICLES.register("skill36_particle", () -> new ParticleType<>(false, OrbitParticleAir.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleAir> codec() {
                    return OrbitParticleAir.CODEC;
                }
            });
    public static final RegistryObject<ParticleType<OrbitParticleAir_vertical>> SKILL36_Particle_vert =
            PARTICLES.register("skill36_particle_vert", () -> new ParticleType<>(false, OrbitParticleAir_vertical.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleAir_vertical> codec() {
                    return OrbitParticleAir_vertical.CODEC;
                }
            });
    public static final RegistryObject<ParticleType<OrbitParticleAir_vertical_>> SKILL36_Particle_vert_ =
            PARTICLES.register("skill36_particle_vert_", () -> new ParticleType<>(false, OrbitParticleAir_vertical_.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleAir_vertical_> codec() {
                    return OrbitParticleAir_vertical_.CODEC;
                }
            });
    //Skill48_particle
    public static final RegistryObject<ParticleType<OrbitParticleEarth>> SKILL48_Particle =
            PARTICLES.register("skill48_particle", () -> new ParticleType<>(false, OrbitParticleEarth.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleEarth> codec() {
                    return OrbitParticleEarth.CODEC;
                }
            });
    //Skill55_particle
    public static final RegistryObject<ParticleType<OrbitParticleLight>> SKILL55_Particle =
            PARTICLES.register("skill55_particle", () -> new ParticleType<>(false, OrbitParticleLight.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleLight> codec() {
                    return OrbitParticleLight.CODEC;
                }
            });
    //Skill66_particle
    public static final RegistryObject<ParticleType<OrbitParticleDarkness>> SKILL66_Particle =
            PARTICLES.register("skill66_particle", () -> new ParticleType<>(false, OrbitParticleDarkness.DESERIALIZER) {
                @Override
                public com.mojang.serialization.Codec<OrbitParticleDarkness> codec() {
                    return OrbitParticleDarkness.CODEC;
                }
            });
    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
