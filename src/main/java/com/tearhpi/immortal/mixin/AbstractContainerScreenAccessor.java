package com.tearhpi.immortal.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {
    @Accessor("leftPos") int immortal$getLeftPos();
    @Accessor("topPos") int immortal$getTopPos();
    @Accessor("imageWidth") int immortal$getImageWidth();
    @Accessor("imageHeight") int immortal$getImageHeight();
    @Accessor("leftPos") void immortal$setLeftPos(int v);

}