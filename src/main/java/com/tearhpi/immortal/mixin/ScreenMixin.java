package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.screen._utilbook.hasUtilBookPanel;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void immortal$keyPressed(int keyCode, int scanCode, int modifiers,
                                     CallbackInfoReturnable<Boolean> cir) {
        Screen self = (Screen)(Object)this;
        if (self instanceof hasUtilBookPanel holder && holder.immortal$isCustomPanelVisible()) {
            if (holder.immortal$getCustomPanel().keyPressed(keyCode, scanCode, modifiers)) {
                cir.setReturnValue(true);
            }
        }
    }
}