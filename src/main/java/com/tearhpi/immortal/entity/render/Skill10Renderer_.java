package com.tearhpi.immortal.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.entity.custom.Weapons.Weapon1_3_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill10_Entity;
import com.tearhpi.immortal.entity.render.renderTypes.Weapon1_3RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class Skill10Renderer_ extends EntityRenderer<Skill10_Entity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("immortal", "textures/entity/skill/skill10_entity.png");
    //private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("immortal", "textures/entity/weapon/weapon1_3.png");
    //1-(x-1)^2


    public Skill10Renderer_(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(Skill10_Entity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        long tick = entity.level().getGameTime();
        float ratio = 1.0f;
        //renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,0.5f,r*4f);
        renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,1.0f,2.2f,0f);
        renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,1.0f,2.4f,0.5f);
        renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,1.0f,2.6f,1.0f);
        renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,1.0f,2.8f,1.5f);
        renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,1.0f,3.0f,2.0f);
        renderSegment(poseStack, buffer, TEXTURE, partialTick,0.1F,tick,1.0f,3.2f,2.5f);
    }
    public static void renderSegment(PoseStack poseStack, MultiBufferSource multiBufferSource, ResourceLocation texture, float partialTick, float rollingSpeed, long tick, float height, float length, float vy) {
        poseStack.pushPose();
        poseStack.translate(0, vy, 0);
        float f = (float) Math.floorMod(tick, 40) + partialTick;
        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(f * 22.5F - 45.0F)));
        renderOctPart(poseStack, multiBufferSource.getBuffer(Weapon1_3RenderType.W1_3_RT.apply(texture, true)),
                1.0f, 0.2f, 0.0f, 0.8F,
                0.0F, (float) height,
                length,
                0.0F, 1.0F, 0.0F, 1.0F);
        renderOctPart(poseStack, multiBufferSource.getBuffer(Weapon1_3RenderType.W1_3_RT.apply(texture, true)),
                1.0f, 0.2f, 0.0f, 0.125F,
                0.0F, (float) height,
                length*1.2f,
                0.0F, 1.0F, 0.0F, 1.0F);
        poseStack.popPose();
    }

    private static void renderOctPart(
            PoseStack poseStack, VertexConsumer vc,
            float r, float g, float b, float a,
            float y0, float y1,
            float radius,              // 八边形外接圆半径
            float u0, float u1, float v0, float v1
    ) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f m4 = pose.pose();
        Matrix3f m3 = pose.normal();

        // 正八边形：8 个点，角度步进 45°
        // 为了让形状“更像 Minecraft 的对齐风格”，可以让它相对坐标轴旋转 22.5°
        final int N = 8;
        final float rot = (float) Math.toRadians(22.5); // 可改成 0 看你想让哪条边朝前
        float[] xs = new float[N];
        float[] zs = new float[N];

        for (int i = 0; i < N; i++) {
            float ang = rot + i * (float) (Math.PI * 2.0 / N);
            xs[i] = (float) (Math.cos(ang) * radius);
            zs[i] = (float) (Math.sin(ang) * radius);
        }

        // 画 8 个侧面：点 i -> 点 i+1（环绕）
        for (int i = 0; i < N; i++) {
            int j = (i + 1) % N;
            renderQuad(m4, m3, vc, r, g, b, a, y0, y1, xs[i], zs[i], xs[j], zs[j], u0, u1, v0, v1);
        }
    }
    private static void renderQuad(Matrix4f p_253960_, Matrix3f p_254005_, VertexConsumer p_112122_, float R, float G, float B, float A, float yStart, float yEnd, float x1, float z1, float x2, float z2, float u0, float u1, float v0, float v1) {
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yEnd, x1, z1, u1, v0);
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yStart, x1, z1, u1, v1);
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yStart, x2, z2, u0, v1);
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yEnd, x2, z2, u0, v0);
    }
    private static void addVertex(Matrix4f p_253955_, Matrix3f p_253713_, VertexConsumer p_253894_, float r, float g, float b, float a, float y, float x, float z, float u, float v) {
        p_253894_.vertex(p_253955_, x, (float)y, z).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253713_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Skill10_Entity p_114482_) {
        return TEXTURE;
    }
}