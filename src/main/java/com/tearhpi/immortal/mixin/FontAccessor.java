package com.tearhpi.immortal.mixin;


import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Font.class)
public interface FontAccessor {
    @Invoker("getFontSet")
    net.minecraft.client.gui.font.FontSet immortal$invokeGetFontSet(net.minecraft.resources.ResourceLocation id);
}
