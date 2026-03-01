package com.tearhpi.immortal.block._decoration._custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBucketBlock extends Block {
    public CustomBucketBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0; // 不阻挡光照传播
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true; // 允许天光向下传播（有时对观感有帮助）
    }
}
