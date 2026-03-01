package com.tearhpi.immortal.item.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.armor.TestArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TestArmorItemModel extends GeoModel<TestArmorItem> {

    @Override
    public ResourceLocation getModelResource(TestArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/armor/amethyst_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TestArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/armor/amethyst_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TestArmorItem animatable) {
        return null;
    }
}
