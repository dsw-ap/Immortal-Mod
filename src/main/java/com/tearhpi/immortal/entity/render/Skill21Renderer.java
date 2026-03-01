package com.tearhpi.immortal.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.skills.Skill21_Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class Skill21Renderer extends EntityRenderer<Skill21_Entity> {

    // 箭本体贴图
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/skill21_entity.png");

    public Skill21Renderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(Skill21_Entity entity) {
        return TEXTURE;
    }

    @Override
    public void render(Skill21_Entity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        //正常箭体
        poseStack.pushPose();
        applyArrowTransforms(entity, partialTicks, poseStack, 1.0F);
        float time = (entity.tickCount + partialTicks) * 0.15F;
        float rf = 0.2F + 0.1F * Mth.sin(time);
        float gf = 0.4F + 0.3F * Mth.sin(time + 1.0F);
        float bf = 1.0F;
        float af = 1.0F;

        int r = (int) (rf * 255.0F);
        int g = (int) (gf * 255.0F);
        int b = (int) (bf * 255.0F);
        int a = (int) (af * 255.0F);
        drawArrowGeometry(poseStack, buffer, packedLight,
                rf, gf, bf, af,
                RenderType.entityCutout(getTextureLocation(entity)));
        poseStack.popPose();

        //蓝色附魔光效
        int fullBright = 0xF000F0;
        poseStack.pushPose();
        applyArrowTransforms(entity, partialTicks, poseStack, 1.03F);
        drawArrowGeometry(poseStack, buffer, fullBright,
                0.25F, 0.70F, 1.0F, 1.0F,      // 蓝色，几乎不透明
                RenderType.entityGlintDirect());
        poseStack.popPose();

        poseStack.pushPose();
        applyArrowTransforms(entity, partialTicks, poseStack, 1.07F);
        drawArrowGeometry(poseStack, buffer, fullBright,
                0.15F, 0.50F, 1.0F, 0.8F,
                RenderType.entityGlintDirect());
        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void applyArrowTransforms(Skill21_Entity entity, float partialTicks,
                                      PoseStack poseStack, float scaleMul) {

        poseStack.mulPose(Axis.YP.rotationDegrees(
                Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(
                Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        float shake = (float) entity.shakeTime - partialTicks;
        if (shake > 0.0F) {
            float shakeRot = -Mth.sin(shake * 3.0F) * shake;
            poseStack.mulPose(Axis.ZP.rotationDegrees(shakeRot));
        }

        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        float s = 0.05625F * scaleMul;
        poseStack.scale(s, s, s);
        poseStack.translate(-4.0F, 0.0F, 0.0F);
    }

    private void drawArrowGeometry(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                   float r, float g, float b, float a,
                                   RenderType renderType) {

        VertexConsumer vc = buffer.getBuffer(renderType);
        PoseStack.Pose last = poseStack.last();
        Matrix4f mat4 = last.pose();
        Matrix3f mat3 = last.normal();

        // 这里直接用原版的常量，不再自己命名 f/f1/f2… 避免出错
        // 尾翼 UV 使用 [0, 0.15625] 和 [0.15625, 0.3125]
        // 箭身 UV 使用 [0,0] 到 [0.5,0.15625]

        int ri = (int)(r * 255.0F);
        int gi = (int)(g * 255.0F);
        int bi = (int)(b * 255.0F);
        int ai = (int)(a * 255.0F);

        // ===== 尾翼两面（完全照原版 ArrowRenderer） =====
        // 后面这 8 个点：两个方片，对应贴图的两块区域

        // 面 1（法线 -X）
        vertex(mat4, mat3, vc, -7, -2, -2, 0.0F,      0.15625F, -1, 0, 0, packedLight, ri, gi, bi, ai);
        vertex(mat4, mat3, vc, -7, -2,  2, 0.15625F,  0.15625F, -1, 0, 0, packedLight, ri, gi, bi, ai);
        vertex(mat4, mat3, vc, -7,  2,  2, 0.15625F,  0.31250F, -1, 0, 0, packedLight, ri, gi, bi, ai);
        vertex(mat4, mat3, vc, -7,  2, -2, 0.0F,      0.31250F, -1, 0, 0, packedLight, ri, gi, bi, ai);

        // 面 2（法线 +X）
        vertex(mat4, mat3, vc, -7,  2, -2, 0.0F,      0.15625F,  1, 0, 0, packedLight, ri, gi, bi, ai);
        vertex(mat4, mat3, vc, -7,  2,  2, 0.15625F,  0.15625F,  1, 0, 0, packedLight, ri, gi, bi, ai);
        vertex(mat4, mat3, vc, -7, -2,  2, 0.15625F,  0.31250F,  1, 0, 0, packedLight, ri, gi, bi, ai);
        vertex(mat4, mat3, vc, -7, -2, -2, 0.0F,      0.31250F,  1, 0, 0, packedLight, ri, gi, bi, ai);

        // ===== 箭身四面（同原版） =====
        for (int j = 0; j < 4; ++j) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            PoseStack.Pose pose2 = poseStack.last();
            Matrix4f mat4b = pose2.pose();
            Matrix3f mat3b = pose2.normal();

            // 这里的 UV 是 [0,0]~[0.5,0.15625]，照原版
            vertex(mat4b, mat3b, vc, -8, -2, 0, 0.0F, 0.0F,      0, 1, 0, packedLight, ri, gi, bi, ai);
            vertex(mat4b, mat3b, vc,  8, -2, 0, 0.5F, 0.0F,      0, 1, 0, packedLight, ri, gi, bi, ai);
            vertex(mat4b, mat3b, vc,  8,  2, 0, 0.5F, 0.15625F,  0, 1, 0, packedLight, ri, gi, bi, ai);
            vertex(mat4b, mat3b, vc, -8,  2, 0, 0.0F, 0.15625F,  0, 1, 0, packedLight, ri, gi, bi, ai);
        }
    }

    private void vertex(Matrix4f poseMat, Matrix3f normalMat, VertexConsumer vc,
                        int x, int y, int z,
                        float u, float v,
                        int nx, int ny, int nz,
                        int light,
                        int r, int g, int b, int a) {

        vc.vertex(poseMat, (float)x, (float)y, (float)z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalMat, (float)nx, (float)nz, (float)ny)
                .endVertex();
    }
}
