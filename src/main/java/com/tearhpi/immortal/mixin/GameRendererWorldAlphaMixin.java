package com.tearhpi.immortal.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tearhpi.immortal.effect._ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererWorldAlphaMixin {


    @Unique private static final ResourceLocation BLUR = ResourceLocation.fromNamespaceAndPath("minecraft","shaders/post/blur.json");
    @Unique private boolean immortal$blurLoaded = false;

    @Inject(method = "render", at = @At("HEAD"))
    private void immortal$ensureBlur(float partialTick, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.gameRenderer == null) return;
        if (!renderLevel || mc.level == null) {
            if (immortal$blurLoaded) {
                mc.gameRenderer.shutdownEffect();
                ((GameRendererAccessor) mc.gameRenderer).immortal$setEffectActive(false);
                immortal$blurLoaded = false;
            }
            return;
        }
        boolean ENABLED = false;
        if (mc.player != null) {
            ENABLED = mc.player.hasEffect(_ModEffects.UNREAL_EFFECT.get());
        }
        if (!ENABLED) {
            if (immortal$blurLoaded) {
                mc.gameRenderer.shutdownEffect();
                ((GameRendererAccessor) mc.gameRenderer).immortal$setEffectActive(false);
                immortal$blurLoaded = false;
            }
            return;
        }
        if (!immortal$blurLoaded || mc.gameRenderer.currentEffect() == null) {
            mc.gameRenderer.loadEffect(BLUR);
            immortal$blurLoaded = true;
        }
        ((GameRendererAccessor) mc.gameRenderer).immortal$setEffectActive(true);
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;F)V"
            )
    )
    private void immortal$dreamGlowOverlay(float partialTick, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.level == null) return;
        boolean ENABLED = false;
        if (mc.player != null) {
            ENABLED = mc.player.hasEffect(_ModEffects.UNREAL_EFFECT.get());
        }
        if (!ENABLED) return;
        int w = mc.getWindow().getGuiScaledWidth();
        int h = mc.getWindow().getGuiScaledHeight();
        float t = (mc.level.getGameTime() + partialTick);
        float a = 0.06f + 0.03f * (float)Math.sin(t * 0.04f); // 0.03~0.12 之间波动
        a = Mth.clamp(a, 0.03f, 0.14f);
        float r = 0.75f, g = 0.85f, b = 1.00f;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE
        );
        var tess = com.mojang.blaze3d.vertex.Tesselator.getInstance();
        var buf = tess.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(0, h, 0).color(r, g, b, a).endVertex();
        buf.vertex(w, h, 0).color(r, g, b, a).endVertex();
        buf.vertex(w, 0, 0).color(r, g, b, a).endVertex();
        buf.vertex(0, 0, 0).color(r, g, b, a).endVertex();
        tess.end();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}