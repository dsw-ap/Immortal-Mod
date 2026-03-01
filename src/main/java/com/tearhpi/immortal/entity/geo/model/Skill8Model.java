package com.tearhpi.immortal.entity.geo.model;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.custom.skills.Skill8_Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Skill8Model extends GeoModel<Skill8_Entity> {

    @Override
    public ResourceLocation getModelResource(Skill8_Entity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "geo/entity/skill8_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Skill8_Entity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/entity/skill8_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Skill8_Entity animatable) {
        return null;
    }
}
