package com.tearhpi.immortal.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.skills.Skill15_Entity;
import com.tearhpi.immortal.entity.render.renderTypes.Skill15RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
public class Skill15Renderer extends EntityRenderer<Skill15_Entity> {
    public Skill15Renderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.0f;
    }
    private static final ResourceLocation TEX_PHOTO = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/sun/photosphere.png");
    private static final ResourceLocation TEX_CHROMO = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/sun/chromosphere.png");
    private static final ResourceLocation TEX_CORONA = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/sun/corona_ring_.png");
    private static final ResourceLocation TEX_FLARE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/sun/flare_strip.png");
    @Override
    public void render(Skill15_Entity sun, float entityYaw, float partialTicks, PoseStack ps, MultiBufferSource buffers, int packedLight) {
        ps.pushPose();
        ps.mulPose(this.entityRenderDispatcher.cameraOrientation());
        ps.mulPose(Axis.YP.rotationDegrees(180.0F));
        float baseR = sun.radius/5;
        double eps = 1e-3;
        float t = (sun.tickCount + partialTicks) * 0.05f * sun.timeScale;
        //主体
        var rtSphere = Skill15RenderTypes.additive(TEX_PHOTO);
        ps.pushPose();
        ps.mulPose(Axis.ZP.rotationDegrees((float)Math.sin(t*0.5f + 6)*6f));
        ps.popPose();
        drawBillboard(ps, buffers, rtSphere, baseR, baseR, 0f, 0f, 1f, 1f, 1f, 0.95f, 0.75f, 1.0f);
        //色球
        float chromoR = baseR * 1.02f;
        float chromoRotU = (t * 0.03f) % 1.0f;
        ps.pushPose();
        ps.translate(0.0, 0.0, -eps);
        drawBillboard(ps, buffers, Skill15RenderTypes.translucent(TEX_CHROMO), chromoR, chromoR, chromoRotU, 1f, chromoRotU + 1f, 1f, 1f, 0.6f, 0.2f, 0.85f);
        ps.popPose();
        //多层光环
        for (int i = 0; i < 6; i++) {
            float ringR = baseR * (0.6f + i * 0.45f);
            float alpha = 0.85f / (2*i);
            ps.pushPose();
            ps.mulPose(Axis.ZP.rotationDegrees((float)Math.sin(t*0.5f + i)*6f));
            ps.translate(0.0, 0.0, -eps);
            drawBillboard(ps, buffers, Skill15RenderTypes.translucent(TEX_CORONA), ringR, ringR, 0f, 0, 1f, 1f, 1f, 1f, 0.8e5f, alpha);
            ps.popPose();
        }
        //日珥/耀斑
        int flareCount = 6;
        for (int i = 0; i < flareCount; i++) {
            float ang = (i / (float) flareCount) * 360f + t * (10f + 3f*i);
            float rOut = baseR * (1.15f + 0.15f * (float)Math.sin(t*0.9f + i));
            ps.pushPose();
            ps.mulPose(Axis.ZP.rotationDegrees(ang));
            ps.translate(0, 0, 0);
            ps.scale(1.0f, 1.0f, 1.0f);
            float halfW = baseR * 0.12f;
            float halfH = rOut - baseR * 0.95f;
            float vScroll = (t * (0.2f + 0.05f * i)) % 1.0f;
            drawBillboard(ps, buffers, Skill15RenderTypes.additive(TEX_FLARE), halfW, halfH, 0f, vScroll, 1f, vScroll + 1f, 1f, 0.5f, 0.2f, 0.9f);
            ps.popPose();
        }
        ps.popPose();
        super.render(sun, entityYaw, partialTicks, ps, buffers, packedLight);
    }
    private static void drawBillboard(PoseStack ps, MultiBufferSource buf, RenderType rt, float halfW, float halfH, float u0, float v0, float u1, float v1, float r, float g, float b, float a) {
        VertexConsumer vc = buf.getBuffer(rt);
        Matrix4f mat = ps.last().pose();
        float x0 = -halfW, x1 = +halfW;
        float y0 = -halfH, y1 = +halfH;
        vc.vertex(mat, x0, y1, 0).color(r, g, b, a).uv(u0, v0).endVertex();
        vc.vertex(mat, x1, y1, 0).color(r, g, b, a).uv(u1, v0).endVertex();
        vc.vertex(mat, x1, y0, 0).color(r, g, b, a).uv(u1, v1).endVertex();
        vc.vertex(mat, x0, y0, 0).color(r, g, b, a).uv(u0, v1).endVertex();
    }
    @Override
    public ResourceLocation getTextureLocation(Skill15_Entity e) {
        return TEX_PHOTO; // 不用
    }
    @Override
    public boolean shouldRender(Skill15_Entity e, Frustum frustum, double camX, double camY, double camZ) {
        double r = e.radius * 4.0;
        AABB box = new AABB(e.getX()-r, e.getY()-r, e.getZ()-r, e.getX()+r, e.getY()+r, e.getZ()+r);
        return super.shouldRender(e, frustum, camX, camY, camZ) || frustum.isVisible(box);
    }
}
