package com.tearhpi.immortal.block.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.item.custom.geoitem.ForgingTableItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ForgingTableItemModel extends GeoModel<ForgingTableItem> {

    @Override
    public ResourceLocation getModelResource(ForgingTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/forging_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ForgingTableItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/block/forging_table.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ForgingTableItem animatable) {
        return null;
    }
}
