package com.tearhpi.immortal.entity.geo.render;

import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.geo.model.EnhancementTableModel;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import com.tearhpi.immortal.entity.geo.model.TrainerDummyModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TrainerDummyRenderer extends GeoEntityRenderer<TrainerDummy> {
    public TrainerDummyRenderer(EntityRendererProvider.Context context) {
        super(context,new TrainerDummyModel());
    }
}
