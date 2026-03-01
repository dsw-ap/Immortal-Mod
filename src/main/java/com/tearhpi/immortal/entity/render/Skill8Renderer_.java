package com.tearhpi.immortal.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tearhpi.immortal.entity.custom.skills.Skill8_Entity_;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class Skill8Renderer_ extends EntityRenderer<Skill8_Entity_> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("immortal", "textures/entity/skill8_entity_.png");
    private static final ResourceLocation TEXTURE_ =
            ResourceLocation.fromNamespaceAndPath("immortal", "textures/entity/skill8_entity__.png");


    public Skill8Renderer_(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(Skill8_Entity_ entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        long tick = entity.level().getGameTime();
        List<Vec3> list = entity.RenderPointList;
        if (list != null && list.size() >= 2) {
            float acc = 0.0f;
            for(int k = 0; k < list.size()-1; k++) {
                Vec3 p0 = list.get(k);
                Vec3 p1 = list.get(k + 1);
                float segLen = (float) p1.subtract(p0).length();

                renderSegment(poseStack, buffer, TEXTURE,TEXTURE_, partialTick,0.1F,tick, p0, p1,acc);
                acc += segLen;
                //renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick, list.get(k),0.2f,0.3f);
            }
        }
    }
    public static void renderSegment(PoseStack poseStack, MultiBufferSource multiBufferSource, ResourceLocation texture,ResourceLocation texture_, float partialTick, float rollingSpeed, long tick, Vec3 p0, Vec3 p1, float vOffset) {
        Vec3 d = p1.subtract(p0);
        double len = d.length()*1.03;
        if (len < 1.0e-6) return;
        Vec3 dir = d.scale(1.0 / len);
        poseStack.pushPose();
        poseStack.translate(p0.x, p0.y, p0.z);
        Quaternionf rot = new Quaternionf().rotationTo(new Vector3f(0, 1, 0), new Vector3f((float) dir.x, (float) dir.y, (float) dir.z));
        poseStack.mulPose(rot);
        float f = (float) Math.floorMod(tick, 40) + partialTick;
        float phase = f * rollingSpeed;
        float v0 = -1.0f + phase + vOffset;
        float v1 = v0 + (float) len;

        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(f * 2.25F - 45.0F)));
        float r = 1.0f, g = 0.5f, b = 0.0f;
        renderOctPart(poseStack, multiBufferSource.getBuffer(RenderType.beaconBeam(texture, true)),
                1.0f, 0.5f, 0.0f, 0.8F,
                0.0F, (float) len,
                0.05f,
                0.0F, 1.0F, v0, v1);
        renderOctPart(poseStack, multiBufferSource.getBuffer(RenderType.beaconBeam(texture_, true)),
                1.0f, 0.8f, 0.3f, 0.3F,
                0.0F, (float) len,
                0.06f,
                0.0F, 1.0F, v0, v1);
        renderOctPart(poseStack, multiBufferSource.getBuffer(RenderType.beaconBeam(texture_, true)),
                1.0f, 0.0f, 0.0f, 0.125F,
                0.0F, (float) len,
                0.07f,
                0.0F, 1.0F, v0, v1);
        /*
        renderPart(poseStack, multiBufferSource.getBuffer(RenderType.beaconBeam(texture, true)), r, g, b, 1.0f,
                0.0F, (float) len,
                0.0F, 0.05f,
                0.05f, 0.0F,
                -0.05f, 0.0F,
                0.0F, -0.05f,
                0.0F, 1.0F, 0.0f, 1.0f);
        renderPart(poseStack, multiBufferSource.getBuffer(RenderType.beaconBeam(texture_, true)), r, g, b, 0.125f,
                0.0F, (float) len,
                0.0F, 0.2f,
                0.2f, 0.0F,
                -0.2f, 0.0F,
                0.0F, -0.2f,
                0.0F, 1.0F, 0.0f, 1.0f);
         */
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
    private static void renderPart(PoseStack poseStack, VertexConsumer vertexConsumer, float r, float g, float b, float a, float y0, float y1, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u0, float u1, float v0, float v1) {
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x1, z1, x2, z2, u0, u1, v0, v1);
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x4, z4, x3, z3, u0, u1, v0, v1);
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x2, z2, x4, z4, u0, u1, v0, v1);
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x3, z3, x1, z1, u0, u1, v0, v1);
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull Skill8_Entity_ p_114482_) {
        return TEXTURE;
    }
}

    /*
    public static void renderSinglePart(PoseStack poseStack, MultiBufferSource multiBufferSource, ResourceLocation resourceLocation, float partialTick, float rollingSpeed, long tick, Vec3 center, float outer_Line, float inner_line) {
        //计算中点，锚定方块中心
        poseStack.pushPose();
        poseStack.translate(center.x,center.y,center.z);
        //计算tick
        float f = (float)Math.floorMod(tick, 40) + partialTick;
        //颜色
        float r = 1.0f;
        float g = 0.0f;
        float b = 0.0f;
        //poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        renderPart(poseStack, multiBufferSource.getBuffer(RenderType.beaconBeam(resourceLocation, true)),
                r, g, b, 1.0F,
                0,0.2f, //高度y0y1
                0.0F, outer_Line, //x1z1
                outer_Line, 0.0F, //x2z2
                -outer_Line, 0.0F, //x3z3
                0.0F, -outer_Line, //x4z4
                0.0F, 1.0F, 0.0f, 1.0f);
        poseStack.popPose();
    }
     */
