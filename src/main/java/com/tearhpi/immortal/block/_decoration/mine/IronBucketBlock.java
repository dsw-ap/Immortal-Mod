package com.tearhpi.immortal.block._decoration.mine;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IronBucketBlock extends Block {
    private static final int SIDE_THICKNESS = 1;
    private static final int LEG_WIDTH = 4;
    private static final int LEG_HEIGHT = 3;
    private static final int LEG_DEPTH = 2;
    protected static final int FLOOR_LEVEL = 4;
    private static final VoxelShape INSIDE = box(1.0D, 4.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final int WALL = 1; // 壁厚：2/16
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(4, 0, 4, 12, 1, 12),
            Block.box(4, 0, 4, 12, 7, 5),
            Block.box(4, 0, 11, 12, 7, 12),
            Block.box(4, 0, 4, 5, 7, 12),
            Block.box(11, 1, 5, 12, 7, 12),
            Block.box(3.5, 7, 3.5, 12.5, 9, 4.5),
            Block.box(3.5, 7, 3.5, 4.5, 9, 12.5),
            Block.box(11.5, 7, 4.5, 12.5, 9, 12.5),
            Block.box(4.5, 7, 11.5, 12.5, 9, 12.5)
    );

    public IronBucketBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE;
    }
}
