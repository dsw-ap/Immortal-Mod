package com.tearhpi.immortal.entity.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TrainerDummyModel extends GeoModel<TrainerDummy> {

    @Override
    public ResourceLocation getModelResource(TrainerDummy animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/entity/trainer_dummy.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TrainerDummy animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/trainer_dummy.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TrainerDummy animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "animations/entity/trainer_dummy.animation.json");
    }
}
