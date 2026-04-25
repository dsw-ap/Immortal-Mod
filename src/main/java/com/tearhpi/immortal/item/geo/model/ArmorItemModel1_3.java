package com.tearhpi.immortal.item.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.armor.ArmorItem1_1;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ArmorItemModel1_2 extends GeoModel<ArmorItem1_1> {
    @Override
    public ResourceLocation getModelResource(ArmorItem1_1 animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/armor/test2_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArmorItem1_1 animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/armor/test2_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArmorItem1_1 animatable) {
        return null;
    }
}
