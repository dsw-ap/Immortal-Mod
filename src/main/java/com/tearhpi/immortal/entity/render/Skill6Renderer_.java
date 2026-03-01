package com.tearhpi.immortal.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.entity.custom.skills.Skill6_Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class Skill6Renderer_ extends EntityRenderer<Skill6_Entity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/item/fire_charge.png");

    private static final int FRAME_COUNT = 1;

    private static final int TEX_W = 16;
    private static final int TEX_H = 16;

    private static final int FRAME_W = 16;
    private static final int FRAME_H = 16;

    private static UV frameUV_Vertical(int frame) {
        frame = Math.floorMod(frame, FRAME_COUNT);

        // 防止采样到相邻帧边缘（很重要，尤其开了 translucent 时）
        float epsU = 0.5f / TEX_W;
        float epsV = 0.5f / TEX_H;

        float u0 = 0f + epsU;
        float u1 = (FRAME_W / (float) TEX_W) - epsU; // 这里等于 1 - epsU（因为 FRAME_W==TEX_W）

        float v0 = (frame * FRAME_H) / (float) TEX_H + epsV;
        float v1 = ((frame + 1) * FRAME_H) / (float) TEX_H - epsV;

        return new UV(u0, v0, u1, v1);
    }

    private record UV(float u0, float v0, float u1, float v1) {

    }

    public Skill6Renderer_(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(Skill6_Entity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 1.0, 0);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        float scale = 4.5f;
        poseStack.scale(scale, scale, scale);
        int frame = getFrame(entity, partialTick);
        float u0 = frame / 16f;
        float u1 = (frame + 1) / 16f;
        int frame_ = (int)((entity.tickCount + partialTick) * 0.75f) % FRAME_COUNT;
        UV uv = frameUV_Vertical(frame_);
        drawQuad(poseStack, buffer, packedLight, TEXTURE,
                uv,
                0.5f, 255,255,255,255);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick,
                poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Skill6_Entity p_114482_) {
        return TEXTURE;
    }

    private int getFrame(Skill6_Entity e, float pt) {
        float t = e.life + pt;
        return (int)(t * 0.75f) % FRAME_COUNT;
    }
    private void drawQuad(PoseStack poseStack,
                          MultiBufferSource buffer,
                          int packedLight,
                          ResourceLocation tex,
                          UV uv,
                          float halfSize,
                          int r, int g, int b, int a) {

        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucent(tex));

        PoseStack.Pose last = poseStack.last();
        Matrix4f pose = last.pose();
        Matrix3f normal = last.normal();

        float nx = 0, ny = 0, nz = 1;

        vc.vertex(pose, -halfSize, -halfSize, 0)
                .color(r, g, b, a)
                .uv(uv.u0(), uv.v1())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();

        vc.vertex(pose,  halfSize, -halfSize, 0)
                .color(r, g, b, a)
                .uv(uv.u1(), uv.v1())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();

        vc.vertex(pose,  halfSize,  halfSize, 0)
                .color(r, g, b, a)
                .uv(uv.u1(), uv.v0())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();

        vc.vertex(pose, -halfSize,  halfSize, 0)
                .color(r, g, b, a)
                .uv(uv.u0(), uv.v0())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }
}
