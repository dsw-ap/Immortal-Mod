package com.tearhpi.immortal.block._decoration._custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CustomPlantBlock extends Block {

    // 可选：视觉/选择框（不影响碰撞）
    private static final VoxelShape OUTLINE =
            Block.box(0, 0, 0, 16, 14, 16);

    public CustomPlantBlock(Properties props) {
        super(props);
    }

    /**
     * 无碰撞箱：实体碰撞用这个
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    /**
     * 选择框/轮廓（鼠标指向时的框）
     * 想要“选中也没有框”可以直接 return Shapes.empty()
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return OUTLINE;
    }

    /**
     * 让寻路把它当成可穿过（像草）
     */
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return true;
    }

    /**
     * 让它更像植物：不阻挡视线（可选）
     */
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}
