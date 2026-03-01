package com.tearhpi.immortal.entity.render.renderTypes;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class Skill15RenderTypes {
    private Skill15RenderTypes() {}

    // --- 通用 Shaders（公开 supplier） ---
    private static final RenderStateShard.ShaderStateShard POSITION_TEX_SHADER =
            new RenderStateShard.ShaderStateShard(GameRenderer::getPositionTexShader);

    private static final RenderStateShard.ShaderStateShard POSITION_COLOR_TEX_SHADER =
            new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorTexShader);

    // --- 透明度 / 混合（用构造函数替代受保护常量） ---

    // 加色混合（ONE, ONE）
    private static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY =
            new RenderStateShard.TransparencyStateShard(
                    "sun_additive",
                    () -> {
                        RenderSystem.enableBlend();
                        RenderSystem.blendFuncSeparate(
                                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE,   // 颜色
                                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);  // alpha
                    },
                    () -> {
                        RenderSystem.disableBlend();
                        RenderSystem.defaultBlendFunc();
                    });

    // 标准半透明（SRC_ALPHA, ONE_MINUS_SRC_ALPHA）
    private static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY =
            new RenderStateShard.TransparencyStateShard(
                    "sun_translucent",
                    () -> {
                        RenderSystem.enableBlend();
                        RenderSystem.blendFuncSeparate(
                                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    },
                    () -> {
                        RenderSystem.disableBlend();
                        RenderSystem.defaultBlendFunc();
                    });
    private static final RenderStateShard.TransparencyStateShard ADD_SOFT =
            new RenderStateShard.TransparencyStateShard("sun_add_soft",
                    () -> {
                        RenderSystem.enableBlend();
                        RenderSystem.blendFuncSeparate(
                                GlStateManager.SourceFactor.SRC_ALPHA,
                                GlStateManager.DestFactor.ONE,
                                GlStateManager.SourceFactor.ONE,
                                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
                        );
                    },
                    () -> { RenderSystem.disableBlend(); RenderSystem.defaultBlendFunc(); }
            );
    // --- 其它 RenderState（同理用构造器 new） ---
    private static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
    private static final RenderStateShard.LightmapStateShard NO_LIGHTMAP = new RenderStateShard.LightmapStateShard(false);
    private static final RenderStateShard.WriteMaskStateShard COLOR_WRITE = new RenderStateShard.WriteMaskStateShard(true, false);
    private static final RenderStateShard.WriteMaskStateShard COLOR_DEPTH_WRITE = new RenderStateShard.WriteMaskStateShard(true, true);
    private static final RenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST =
            new RenderStateShard.DepthTestStateShard("lequal", GL11.GL_LEQUAL);

    // ====================== 公开工厂 ======================

    /** 加色，无顶点颜色：贴图本身控制颜色（推荐给 photosphere/corona/lens）。 */
    public static RenderType additive(ResourceLocation tex) {
        var texState = new RenderStateShard.TextureStateShard(tex, false, false);
        var state = RenderType.CompositeState.builder()
                .setShaderState(POSITION_TEX_SHADER)
                .setTextureState(texState)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(NO_LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .createCompositeState(true);

        return RenderType.create("sun_add", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, true, state);
    }

    /** 半透明，无顶点颜色：用于色球层（需要 alpha）。 */
    public static RenderType translucent(ResourceLocation tex) {
        var texState = new RenderStateShard.TextureStateShard(tex, false, false);
        var state = RenderType.CompositeState.builder()
                .setShaderState(POSITION_TEX_SHADER)
                .setTextureState(texState)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(NO_LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .createCompositeState(true);

        return RenderType.create(
                "sun_translucent",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256, false, true, state);
    }
    public static RenderType additiveConora(ResourceLocation tex) {
        var texState = new RenderStateShard.TextureStateShard(tex, false, false);
        var state = RenderType.CompositeState.builder()
                // 仅此处从 POSITION_TEX_SHADER -> POSITION_COLOR_TEX_SHADER
                .setShaderState(POSITION_COLOR_TEX_SHADER)
                .setTextureState(texState)
                // 仅此处把 ADDITIVE_TRANSPARENCY -> SOFT_ADDITIVE
                .setTransparencyState(ADD_SOFT)
                .setCullState(NO_CULL)
                .setLightmapState(NO_LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .createCompositeState(true);

        return RenderType.create("sun_add_soft", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true, state);
    }

    /** 加色 + 顶点颜色：如果你在顶点写 .color(r,g,b,a) 需要这个。 */
    public static RenderType additiveColored(ResourceLocation tex) {
        var texState = new RenderStateShard.TextureStateShard(tex, false, false);
        var state = RenderType.CompositeState.builder()
                .setShaderState(POSITION_COLOR_TEX_SHADER)
                .setTextureState(texState)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(NO_LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .createCompositeState(true);

        return RenderType.create(
                "sun_add_color",
                DefaultVertexFormat.POSITION_COLOR_TEX,
                VertexFormat.Mode.QUADS,
                256, false, true, state);
    }
}
