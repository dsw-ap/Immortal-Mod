package com.tearhpi.immortal.mixin;

import com.mojang.math.Transformation;
import net.minecraft.world.entity.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Display.class)
public interface DisplayMixin {
    @Invoker("setInterpolationDuration")
    void immortal$setInterpolationDuration(int ticks);

    @Invoker("setInterpolationDelay")
    void immortal$setInterpolationDelay(int ticks);

    @Invoker("setTransformation")
    void immortal$setTransformation(Transformation transformation);
}
