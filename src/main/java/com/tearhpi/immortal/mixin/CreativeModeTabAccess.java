package com.tearhpi.immortal.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreativeModeTab.class)
public interface CreativeModeTabAccess {
    @Accessor(value = "backgroundLocation",remap = false)
    void immortal$setBackgroundLocation(ResourceLocation rl);

    @Accessor(value = "backgroundLocation",remap = false)
    ResourceLocation immortal$getBackgroundLocation();

    @Accessor("backgroundSuffix")
    String immortal$getBackgroundSuffix();
}
