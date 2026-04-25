package com.tearhpi.immortal.client.entity;

import com.tearhpi.immortal.client.entity._Enemy2Renderer.ImmortalAbstractZombieRenderer;
import com.tearhpi.immortal.client.entity._Enemy2Renderer.ImmortalZombieModel;
import com.tearhpi.immortal.entity.custom.enemy.Enemy2;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Enemy2Renderer extends ImmortalAbstractZombieRenderer<Enemy2, ImmortalZombieModel<Enemy2>> {
    public Enemy2Renderer(EntityRendererProvider.Context p_174456_) {
        this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }


    public Enemy2Renderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
        super(p_174458_, new ImmortalZombieModel<>(p_174458_.bakeLayer(p_174459_)), new ImmortalZombieModel<>(p_174458_.bakeLayer(p_174460_)), new ImmortalZombieModel<>(p_174458_.bakeLayer(p_174461_)));
    }
}

