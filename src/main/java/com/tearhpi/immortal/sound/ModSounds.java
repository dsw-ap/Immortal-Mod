package com.tearhpi.immortal.sound;

import com.tearhpi.immortal.Immortal;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Immortal.MODID);
    public static void register(IEventBus bus) {SOUND_EVENTS.register(bus);}

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, name);
        return SOUND_EVENTS.register(name,() -> SoundEvent.createVariableRangeEvent(location));
    };
    public static final RegistryObject<SoundEvent> ENHANCEMENT_TABLE_DO = registerSoundEvent("enhancement_table_do");
    public static final RegistryObject<SoundEvent> MAGIC_EXPLODE = registerSoundEvent("magic_explode");
    public static final RegistryObject<SoundEvent> SKILL_6_SUMMON = registerSoundEvent("skill_6_summon");
    public static final RegistryObject<SoundEvent> SKILL_7_SUMMON = registerSoundEvent("skill_7_summon");
    public static final RegistryObject<SoundEvent> NORMAL_ATTACK = registerSoundEvent("normal_attack");
}
