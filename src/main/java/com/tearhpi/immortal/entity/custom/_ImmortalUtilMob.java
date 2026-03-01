package com.tearhpi.immortal.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class _ImmortalUtilMob extends PathfinderMob {
    public boolean showHealth = true;
    protected _ImmortalUtilMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }
    @Override
    public void tick() {
        super.tick();
    }
}
