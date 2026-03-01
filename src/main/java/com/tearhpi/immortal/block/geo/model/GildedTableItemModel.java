package com.tearhpi.immortal.block.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.geoitem.EnhancementTableItem;
import com.tearhpi.immortal.item.custom.geoitem.GildedTableItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GildedTableItemModel extends GeoModel<GildedTableItem> {

    @Override
    public ResourceLocation getModelResource(GildedTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/gilded_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GildedTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/block/gilded_table.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GildedTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "animations/gilded_table.animation.json");
    }
}
