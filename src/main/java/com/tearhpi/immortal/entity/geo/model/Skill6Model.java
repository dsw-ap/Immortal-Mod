package com.tearhpi.immortal.entity.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.skills.Skill6_Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Skill6Model extends GeoModel<Skill6_Entity> {

    @Override
    public ResourceLocation getModelResource(Skill6_Entity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/entity/skill6_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Skill6_Entity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/skill6_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Skill6_Entity animatable) {
        return null;
    }
}
