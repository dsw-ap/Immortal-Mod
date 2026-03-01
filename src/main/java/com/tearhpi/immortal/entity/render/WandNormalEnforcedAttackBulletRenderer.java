package com.tearhpi.immortal.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.entity.custom.WandNormalAttackBullet;
import com.tearhpi.immortal.entity.custom.WandNormalAttackBullet_Enforced;
import com.tearhpi.immortal.entity.render.renderTypes.WandNormalAttackBulletRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
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

public class WandNormalEnforcedAttackBulletRenderer extends EntityRenderer<WandNormalAttackBullet_Enforced> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("immortal", "textures/entity/skill8_entity_.png");


    public WandNormalEnforcedAttackBulletRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(WandNormalAttackBullet_Enforced entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        long tick = entity.level().getGameTime();
        int scale = entity.getGrade();
        float f = ((float) Math.floorMod(tick, 36) + partialTick)*10;
        float f_ = (float) Math.toRadians(f);
        Vec3 normal_line = new Vec3(Mth.sin(f_),1.0f,Mth.sin(f_));
        int attr = entity.getWeaponAttribute();
        if (attr == 1) {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,1.0f,0.0f,0.0f);
        } else if (attr == 2) {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,0.0f,0.0f,1.0f);
        } else if (attr == 3) {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,1.0f,1.0f,1.0f);
        } else if (attr == 4) {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,0.58f,0.58f,0.286f);
        } else if (attr == 5) {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,1.0f,1.0f,0f);
        } else if (attr == 6) {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,0.4f,0.0f,0.43f);
        } else {
            renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f+0.1f*scale,0.25f+0.11f*scale,normal_line,0.724f,0.657f,0.501f);
        }
        //renderSinglePart(poseStack,buffer,TEXTURE,partialTick,0.1F,tick,0.2f,0.25f,new Vec3(0.0f,1.0f,0.0f));
    }

    public static void renderSinglePart(PoseStack poseStack, MultiBufferSource multiBufferSource, ResourceLocation resourceLocation, float partialTick, float rollingSpeed, long tick, float outer_Line, float inner_line,Vec3 dir,float r,float g,float b) {
        //计算中点，锚定方块中心
        poseStack.pushPose();
        Quaternionf rot = new Quaternionf().rotationTo(new Vector3f(0, 1, 0), new Vector3f((float) dir.x, (float) dir.y, (float) dir.z));
        poseStack.mulPose(rot);
        float f = (float) Math.floorMod(tick, 40) + partialTick;
        float phase = f * rollingSpeed;
        float v0 = -1.0f + phase;
        float v1 = v0 + (float) outer_Line;

        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(f * 2.25F - 45.0F)));
        poseStack.pushPose();
        poseStack.translate(0.0f, -outer_Line*0.732f, 0.0f);
        renderPart(poseStack, multiBufferSource.getBuffer(
                        WandNormalAttackBulletRenderTypes.OVEREXPOSED_BEAM.apply(resourceLocation, true)),
                r, g, b, 1.0F,
                0,outer_Line*1.414f, //高度y0y1[内层]
                0.0F, outer_Line, //x1z1
                outer_Line, 0.0F, //x2z2
                -outer_Line, 0.0F, //x3z3
                0.0F, -outer_Line, //x4z4
                0.0F, 1.0F, v0, v1);
        poseStack.popPose();
        poseStack.translate(0.0f, -inner_line*0.732f, 0.0f);
        renderPart(poseStack, multiBufferSource.getBuffer(
                        WandNormalAttackBulletRenderTypes.OVEREXPOSED_BEAM.apply(resourceLocation, true)),
                1.0f, 1.0f, 1.0f, 0.125F,
                0.0f,inner_line*1.414f, //高度y0y1[外层]
                0.0F, inner_line, //x1z1
                inner_line, 0.0F, //x2z2
                -inner_line, 0.0F, //x3z3
                0.0F, -inner_line, //x4z4
                0.0F, 1.0F, v0, v1);
        poseStack.popPose();
    }
    private static void renderPart(PoseStack poseStack, VertexConsumer vertexConsumer, float r, float g, float b, float a, float y0, float y1, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u0, float u1, float v0, float v1) {
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x1, z1, x2, z2, u0, u1, v0, v1);
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x4, z4, x3, z3, u0, u1, v0, v1);
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x2, z2, x4, z4, u0, u1, v0, v1);
        renderQuad(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, y1, x3, z3, x1, z1, u0, u1, v0, v1);
        renderQuadTopBottom(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y0, x1, z1,x2,z2,x3,z3, x4, z4, u0, u1, v0, v1,true);
        renderQuadTopBottom(matrix4f, matrix3f, vertexConsumer, r, g, b, a, y1, x1, z1,x2,z2,x3,z3, x4, z4, u0, u1, v0, v1,false);
    }
    private static void renderQuad(Matrix4f p_253960_, Matrix3f p_254005_, VertexConsumer p_112122_, float R, float G, float B, float A, float yStart, float yEnd, float x1, float z1, float x2, float z2, float u0, float u1, float v0, float v1) {
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yEnd, x1, z1, u1, v0);
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yStart, x1, z1, u1, v1);
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yStart, x2, z2, u0, v1);
        addVertex(p_253960_, p_254005_, p_112122_, R,G,B,A, yEnd, x2, z2, u0, v0);
    }
    private static void renderQuadTopBottom(
            Matrix4f pose, Matrix3f normalMat, VertexConsumer vc, float R, float G, float B, float A, float y, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u0, float u1, float v0, float v1, boolean top) {
        float cx = (x1 + x2 + x3 + x4) * 0.25f;
        float cz = (z1 + z2 + z3 + z4) * 0.25f;
        float[][] p = {
                {x1, z1}, {x2, z2}, {x3, z3}, {x4, z4}
        };
        java.util.Arrays.sort(p, (a, b) -> {
            double aa = Math.atan2(a[1] - cz, a[0] - cx);
            double bb = Math.atan2(b[1] - cz, b[0] - cx);
            return Double.compare(aa, bb);
        });
        float[][] q = p;
        if (!top) {
            q = new float[][]{p[0], p[3], p[2], p[1]};
        }
        addVertex(pose, normalMat, vc, R, G, B, A, y, q[0][0], q[0][1], u0, v0);
        addVertex(pose, normalMat, vc, R, G, B, A, y, q[1][0], q[1][1], u0, v1);
        addVertex(pose, normalMat, vc, R, G, B, A, y, q[2][0], q[2][1], u1, v1);
        addVertex(pose, normalMat, vc, R, G, B, A, y, q[3][0], q[3][1], u1, v0);
    }
    private static void addVertex(Matrix4f p_253955_, Matrix3f p_253713_, VertexConsumer p_253894_, float r, float g, float b, float a, float y, float x, float z, float u, float v) {
        p_253894_.vertex(p_253955_, x, (float)y, z).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253713_, 0.0F, 1.0F, 0.0F).endVertex();
    }
    private static void addVertex(Matrix4f p_253955_, Matrix3f p_253713_, VertexConsumer p_253894_, float r, float g, float b, float a, float y, float x, float z, float u, float v, float nx, float ny, float nz) {
        p_253894_.vertex(p_253955_, x, (float)y, z).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253713_, nx, ny, nz).endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WandNormalAttackBullet_Enforced p_114482_) {
        return TEXTURE;
    }
}
