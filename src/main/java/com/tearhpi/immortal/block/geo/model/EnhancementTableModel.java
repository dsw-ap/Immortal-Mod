package com.tearhpi.immortal.block.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.custom.EnhancementTable;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class
EnhancementTableModel extends GeoModel<EnhancementTableBlockEntity> {

    @Override
    public ResourceLocation getModelResource(EnhancementTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/enhancement_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EnhancementTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/block/enhancement_table.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EnhancementTableBlockEntity animatable) {
        return null;
    }
}
