package com.tearhpi.immortal.block.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GildedTableModel extends GeoModel<GildedTableBlockEntity> {

    @Override
    public ResourceLocation getModelResource(GildedTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/gilded_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GildedTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/block/gilded_table.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GildedTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "animations/gilded_table.animation.json");
    }
}
