package com.tearhpi.immortal.block.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.geoitem.EnhancementTableItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EnhancementTableItemModel extends GeoModel<EnhancementTableItem> {

    @Override
    public ResourceLocation getModelResource(EnhancementTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/enhancement_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EnhancementTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/block/enhancement_table.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EnhancementTableItem animatable) {
        return null;
    }
}
