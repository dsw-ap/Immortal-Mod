package com.tearhpi.immortal.entity.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Skill7Model extends GeoModel<Skill7_Entity> {

    @Override
    public ResourceLocation getModelResource(Skill7_Entity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/entity/skill7_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Skill7_Entity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/skill7_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Skill7_Entity animatable) {
        return null;
    }
}
