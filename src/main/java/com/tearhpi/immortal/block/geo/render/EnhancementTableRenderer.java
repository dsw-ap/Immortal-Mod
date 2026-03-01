package com.tearhpi.immortal.block.geo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.geo.model.EnhancementTableModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EnhancementTableRenderer extends GeoBlockRenderer<EnhancementTableBlockEntity> {
    public EnhancementTableRenderer(BlockEntityRendererProvider.Context context) {
        super(new EnhancementTableModel());
    }
}
