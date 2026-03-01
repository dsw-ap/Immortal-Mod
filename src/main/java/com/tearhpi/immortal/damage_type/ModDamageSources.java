package com.tearhpi.immortal.damage_type;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class ModDamageSources {
    /*
    public static final TagKey<DamageType> IMMORTAL_FIRE =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_fire"));
    public static final TagKey<DamageType> IMMORTAL_WATER =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_water"));
    public static final TagKey<DamageType> IMMORTAL_EARTH =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_earth"));
    public static final TagKey<DamageType> IMMORTAL_AIR =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_air"));
    public static final TagKey<DamageType> IMMORTAL_LIGHT =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_light"));
    public static final TagKey<DamageType> IMMORTAL_DARKNESS =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_darkness"));
     */
    private final Registry<DamageType> damageTypes;
    public ModDamageSources(RegistryAccess registryAccess) {
        this.damageTypes = registryAccess.registryOrThrow(Registries.DAMAGE_TYPE);
    }
    public static final ResourceKey<DamageType> IMMORTAL_NONE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_none"));
    public static final ResourceKey<DamageType> IMMORTAL_FIRE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_fire"));
    public static final ResourceKey<DamageType> IMMORTAL_WATER =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_water"));
    public static final ResourceKey<DamageType> IMMORTAL_EARTH =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_earth"));
    public static final ResourceKey<DamageType> IMMORTAL_AIR =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_air"));
    public static final ResourceKey<DamageType> IMMORTAL_LIGHT =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_light"));
    public static final ResourceKey<DamageType> IMMORTAL_DARKNESS =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_darkness"));
    public static final ResourceKey<DamageType> IMMORTAL_REAL =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("immortal", "immortal_real"));
    public DamageSource immortalDMGType(ResourceKey<DamageType> p_270957_,Entity entity) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(p_270957_),entity);
    }
    public DamageSource immortalDMGType(ResourceKey<DamageType> p_270957_) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(p_270957_));
    }
}
