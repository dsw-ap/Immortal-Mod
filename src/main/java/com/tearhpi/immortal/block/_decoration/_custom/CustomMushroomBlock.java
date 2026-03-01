package com.tearhpi.immortal.block._decoration._custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CustomMushroomBlock extends MushroomBlock {
    public CustomMushroomBlock(Properties p_256027_, ResourceKey<ConfiguredFeature<?, ?>> p_256049_) {
        super(p_256027_, p_256049_);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.UP);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState below = level.getBlockState(belowPos);
        boolean ok = below.isFaceSturdy(level, belowPos, Direction.UP);
        return ok; // 允许任何“上表面坚固”的方块
    }
}
