package com.tearhpi.immortal.block.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ForgingTableModel extends GeoModel<ForgingTableBlockEntity> {

    @Override
    public ResourceLocation getModelResource(ForgingTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/forging_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ForgingTableBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/block/forging_table.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ForgingTableBlockEntity animatable) {
        return null;
    }
}
