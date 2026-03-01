package com.tearhpi.immortal.entity.render;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NoopRenderer<T extends Entity> extends EntityRenderer<T> {
    public NoopRenderer(EntityRendererProvider.Context ctx) { super(ctx); }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        // 返回 false，避免进入可见集（你只用它发粒子）
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        // 必须返回一个有效资源；用方块图集占位即可
        //return net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;
        return null;
    }
}
