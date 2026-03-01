package com.tearhpi.immortal.entity.geo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.entity.custom.skills.Skill8_Entity;
import com.tearhpi.immortal.entity.geo.model.Skill8Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class Skill8Renderer extends GeoEntityRenderer<Skill8_Entity> {
    public Skill8Renderer(EntityRendererProvider.Context context) {
        super(context,new Skill8Model());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new GlowLayer(this));
    }
    private static class GlowLayer extends GeoRenderLayer<Skill8_Entity> {
        public GlowLayer(Skill8Renderer renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, Skill8_Entity animatable, BakedGeoModel bakedModel, RenderType baseRenderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            RenderType glowType = RenderType.eyes(getTextureResource(animatable));
            VertexConsumer vc = bufferSource.getBuffer(glowType);
            int fullBright = 0xF000F0;
            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, glowType, vc, partialTick, fullBright, packedOverlay, 1f, 1f, 1f, 1f);
        }
    }

}
