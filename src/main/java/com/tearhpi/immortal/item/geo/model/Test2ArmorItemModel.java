package com.tearhpi.immortal.item.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.armor.Test2ArmorItem;
import com.tearhpi.immortal.item.custom.armor.TestArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Test2ArmorItemModel extends GeoModel<Test2ArmorItem> {

    @Override
    public ResourceLocation getModelResource(Test2ArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/armor/test2_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Test2ArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/armor/test2_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Test2ArmorItem animatable) {
        return null;
    }
}
