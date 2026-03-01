package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.screen._utilbook.hasUtilBookPanel;
import com.tearhpi.immortal.screen._utilbook.utilbookPanel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin implements hasUtilBookPanel {
    @Shadow
    @Final
    @Mutable
    private static ResourceLocation RECIPE_BUTTON_LOCATION;
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void immortal$swapInventoryTexture(CallbackInfo ci) {
        RECIPE_BUTTON_LOCATION = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/container/recipe_button.png");
    }

    //Redirect知识之书
    @Shadow @Final private RecipeBookComponent recipeBookComponent;
    @Unique private final utilbookPanel immortal$customPanel = new utilbookPanel();
    @Unique private boolean immortal$customVisible = false;
    @Unique private ImageButton immortal$recipeButton;

    @Override
    public utilbookPanel immortal$getCustomPanel() { return immortal$customPanel; }
    @Override
    public boolean immortal$isCustomPanelVisible() { return immortal$customPanel.isVisible(); }


    @Inject(method = "init", at = @At("TAIL"))
    private void immortal$init(CallbackInfo ci) {
        InventoryScreen self = (InventoryScreen)(Object)this;
        immortal$customPanel.init(self.width, self.height, ((AbstractContainerScreenAccessor)self).immortal$getLeftPos(),
                ((AbstractContainerScreenAccessor)self).immortal$getTopPos(),
                ((AbstractContainerScreenAccessor)self).immortal$getImageWidth(),
                ((AbstractContainerScreenAccessor)self).immortal$getImageHeight());
        //((RecipeBookComponentAccessor) this.recipeBookComponent).immortal$setVisible(false);
    }

    // 2) 每 tick
    @Inject(method = "containerTick", at = @At("TAIL"))
    private void immortal$tick(CallbackInfo ci) {
        immortal$customPanel.tick();
    }

    // 3) 渲染：在原版之后画你的面板（或者之前画，看你想盖在哪层）
    @Inject(method = "render", at = @At("HEAD"))
    private void immortal$render_head(GuiGraphics gg, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ((RecipeBookComponentAccessor) this.recipeBookComponent).immortal$setVisible(false);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void immortal$render(GuiGraphics gg, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        immortal$customPanel.render(gg, mouseX, mouseY, partialTick);
    }

    // 4) 输入优先级：panel 优先吃
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void immortal$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (immortal$customPanel.mouseClicked(mouseX, mouseY, button)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void immortal$mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (immortal$customPanel.mouseReleased(mouseX, mouseY, button)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(
            method = "mouseClicked(DDI)Z",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;mouseClicked(DDI)Z"
            )
    )
    private boolean immortal$redirectRecipeMouseClicked(RecipeBookComponent instance, double x, double y, int button) {
        if (immortal$customVisible) return false;
        return instance.mouseClicked(x, y, button);
    }
    @Inject(method = "render", at = @At("HEAD"))
    private void immortal$forceLeftPos(GuiGraphics gg, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        InventoryScreen self = (InventoryScreen)(Object)this;
        //((RecipeBookComponentAccessor) recipeBookComponent).immortal$setVisible(false);
        boolean moveRecipeButton = false;
        if (immortal$isCustomPanelVisible()) {
            ((AbstractContainerScreenAccessor)self).immortal$setLeftPos(177 + (self.width - ((AbstractContainerScreenAccessor)self).immortal$getImageWidth() - 200) / 2);
            moveRecipeButton = true;
        } else {
            ((AbstractContainerScreenAccessor)self).immortal$setLeftPos((self.width - ((AbstractContainerScreenAccessor)self).immortal$getImageWidth()) / 2);
        }
        immortal_forge_1_20_1$grabRecipeButton(moveRecipeButton);
    }
    @Unique
    private void immortal_forge_1_20_1$grabRecipeButton(boolean moveRecipeButton) {
        InventoryScreen self = (InventoryScreen)(Object)this;
        if (moveRecipeButton) {
            for (var child : self.children()) {
                if (child instanceof ImageButton btn) {
                    if (btn.getWidth() == 20 && btn.getHeight() == 18) {
                        btn.setPosition(((AbstractContainerScreenAccessor) self).immortal$getLeftPos() + 104, self.height / 2 - 22);
                    }
                }
            }
        }
    }
}
