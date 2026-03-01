package com.tearhpi.immortal.entity.render.renderTypes;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.function.BiFunction;

public class Weapon1_3RenderType {
    public static final BiFunction<ResourceLocation, Boolean, RenderType> W1_3_RT = Util.memoize((p_234330_, p_234331_) -> {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeBeaconBeamShader))
                .setTextureState(new RenderStateShard.TextureStateShard(p_234330_, false, false))
                .setTransparencyState(p_234331_ ? new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {RenderSystem.enableBlend();RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);}, () -> {RenderSystem.disableBlend();RenderSystem.defaultBlendFunc();}) : new RenderStateShard.TransparencyStateShard("no_transparency", () -> {RenderSystem.disableBlend();}, () -> {}))
                .setWriteMaskState(p_234331_ ? new RenderStateShard.WriteMaskStateShard(true, false) : new RenderStateShard.WriteMaskStateShard(false, true))
                .setCullState(new RenderStateShard.CullStateShard(false))
                .createCompositeState(false);
        return RenderType.create("beacon_beam", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, false, true, rendertype$compositestate);
    });
}
