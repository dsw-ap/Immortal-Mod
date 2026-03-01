package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.Immortal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(CreativeModeTab.Builder.class)
public abstract class CreativeTabBuilderMixin {

    @Accessor(value = "backgroundLocation",remap = false) @Nullable abstract ResourceLocation immortal$getBackgroundLocation();
    @Accessor(value = "backgroundLocation",remap = false) abstract void immortal$setBackgroundLocation(@Nullable ResourceLocation v);
    @Accessor("backgroundSuffix") abstract String immortal$getBackgroundSuffix();
    @Accessor("backgroundSuffix") abstract void immortal$setBackgroundSuffix(String v);

    @Shadow(remap = false)
    @Final
    @Mutable
    private static ResourceLocation CREATIVE_INVENTORY_TABS_IMAGE;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void immortal$swapTabsTexture(CallbackInfo ci) {
        CREATIVE_INVENTORY_TABS_IMAGE =
                ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/container/creative_inventory/tabs.png");
    }

    @Inject(method = "build", at = @At("RETURN"), cancellable = true)
    private void immortal$forceNamespaceForBackground(CallbackInfoReturnable<CreativeModeTab> cir) {
        CreativeModeTab tab = cir.getReturnValue();
        CreativeModeTabAccess access = (CreativeModeTabAccess) tab;

        ResourceLocation current = access.immortal$getBackgroundLocation();
        String suffix = access.immortal$getBackgroundSuffix();

        // 只要是 vanilla 默认那条路径，就替换到你的命名空间
        if (current != null && "minecraft".equals(current.getNamespace()) && current.getPath().startsWith("textures/gui/container/creative_inventory/tab_")) {
            ResourceLocation mine = ResourceLocation.fromNamespaceAndPath(
                    Immortal.MODID, "textures/gui/container/creative_inventory/tab_" + suffix
            );
            access.immortal$setBackgroundLocation(mine);
            cir.setReturnValue(tab);
        }
    }

    /**
     * @author TearHPi
     * @reason 修改后缀
     */
    @Overwrite
    public CreativeModeTab.Builder backgroundSuffix(String p_259981_) {
        CreativeModeTab.Builder tab = (CreativeModeTab.Builder) (Object) this;
        return tab.withBackgroundLocation(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/container/creative_inventory/tab_" + p_259981_));
    }
}
