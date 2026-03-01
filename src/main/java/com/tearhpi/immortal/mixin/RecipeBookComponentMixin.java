package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.screen._utilbook.hasUtilBookPanel;
import com.tearhpi.immortal.screen._utilbook.utilbookPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin implements hasUtilBookPanel {


    @Inject(method = "toggleVisibility()V", at = @At("HEAD"), cancellable = true)
    private void immortal$onToggle(CallbackInfo ci) {
        var scr = Minecraft.getInstance().screen;
        if (scr instanceof hasUtilBookPanel holder) {
            holder.immortal$getCustomPanel().toggle();
            ci.cancel();
        }
    }
}
