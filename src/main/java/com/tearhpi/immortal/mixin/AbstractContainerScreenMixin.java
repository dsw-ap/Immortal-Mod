package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.Immortal;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Shadow
    @Final
    @Mutable
    public static ResourceLocation INVENTORY_LOCATION;

    // 在类静态初始化完成后替换成你的贴图
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void immortal$swapInventoryTexture(CallbackInfo ci) {
        INVENTORY_LOCATION = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/container/inventory.png");
    }
}
