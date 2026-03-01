package com.tearhpi.immortal.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public final class ModRenderTypes {
    private static final RenderStateShard.ShaderStateShard POS_COL =
            new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader);

    // 加色透明
    private static final RenderStateShard.TransparencyStateShard ADDITIVE =
            new RenderStateShard.TransparencyStateShard("additive",
                    () -> { RenderSystem.enableBlend();
                        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE); },
                    () -> { RenderSystem.disableBlend(); RenderSystem.defaultBlendFunc(); });

    // 常规半透明
    private static final RenderStateShard.TransparencyStateShard ALPHA =
            new RenderStateShard.TransparencyStateShard("alpha",
                    () -> { RenderSystem.enableBlend(); RenderSystem.defaultBlendFunc(); },
                    () -> { RenderSystem.disableBlend(); RenderSystem.defaultBlendFunc(); });

    // 深度测试：LEQUAL (= 515)
    private static final RenderStateShard.DepthTestStateShard DEPTH_LEQUAL =
            new RenderStateShard.DepthTestStateShard("lequal", org.lwjgl.opengl.GL11C.GL_LEQUAL);

    // 只写颜色（不写深度）
    private static final RenderStateShard.WriteMaskStateShard COLOR_ONLY =
            new RenderStateShard.WriteMaskStateShard(true, false);

    // 关闭背面裁剪
    private static final RenderStateShard.CullStateShard NO_CULL =
            new RenderStateShard.CullStateShard(false);
    
    public static final RenderType LASER_CORE = RenderType.create(
            "laser_core",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.TRIANGLES, 256, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(POS_COL)
                    .setTransparencyState(ADDITIVE)
                    .setDepthTestState(DEPTH_LEQUAL)
                    .setWriteMaskState(COLOR_ONLY) // 不写深度
                    .setCullState(NO_CULL)
                    // 可选：不设置 layering，避免再踩 protected 常量
                    .createCompositeState(false));

    public static final RenderType LASER_GLOW = RenderType.create(
            "laser_glow",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.TRIANGLES, 512, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(POS_COL)
                    .setTransparencyState(ALPHA)
                    .setDepthTestState(DEPTH_LEQUAL)
                    .setWriteMaskState(COLOR_ONLY)
                    .setCullState(NO_CULL)
                    .createCompositeState(false));
    /*
    public static final RenderType LASER_CORE = RenderType.create(
            "laser_core",
            DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,
            VertexFormat.Mode.TRIANGLES, 256, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorLightmapShader))
                    .setTransparencyState(ADDITIVE)          // ONE, ONE（强加色）
                    .setDepthTestState(DEPTH_LEQUAL)
                    .setWriteMaskState(COLOR_ONLY)           // 不写深度
                    .setCullState(NO_CULL)
                    .createCompositeState(false)
    );

    // 外层“泛光”也走 POSITION_COLOR_LIGHTMAP，方便设成 FULL_BRIGHT
    public static final RenderType LASER_GLOW = RenderType.create(
            "laser_glow",
            DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,
            VertexFormat.Mode.TRIANGLES, 512, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorLightmapShader))
                    .setTransparencyState(ADDITIVE)          // 建议也用加色，会更像“泛光”
                    .setDepthTestState(DEPTH_LEQUAL)
                    .setWriteMaskState(COLOR_ONLY)
                    .setCullState(NO_CULL)
                    .createCompositeState(false)
    );
    */
    private ModRenderTypes() {}
}
