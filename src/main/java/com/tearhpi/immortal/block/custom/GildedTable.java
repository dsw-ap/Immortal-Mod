package com.tearhpi.immortal.block.custom;

import com.mojang.serialization.MapCodec;
import com.tearhpi.immortal.block.entity.ModBlockEntities;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GildedTable extends BaseEntityBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public GildedTable(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49235_1_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos) {
        return Shapes.empty(); // 光照遮挡形状
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof GildedTableBlockEntity gt) {
                // 若你的 BE 自身实现了 MenuProvider，可以直接用 et 作为 provider；
                // 这里沿用你原本的 SimpleMenuProvider 写法以复用 BE 的构造器。
                MenuProvider provider = new SimpleMenuProvider(
                        gt, Component.translatable("tooltip.enhancement_table_title"));

                // 1.20.1 用 NetworkHooks 打开并主动同步方块位置信息到客户端
                NetworkHooks.openScreen((ServerPlayer) player, provider, buf -> buf.writeBlockPos(pos));
            } else {
                throw new IllegalStateException("GT is missing");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new GildedTableBlockEntity(p_153215_, p_153216_);
    }
    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (p_60518_.getBlock() != p_60515_.getBlock()) {
            BlockEntity blockEntity = p_60516_.getBlockEntity(p_60517_);
            if (blockEntity instanceof GildedTableBlockEntity gildedTableBlockEntity) {
                gildedTableBlockEntity.drops();
            }
        }
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        if (p_153212_.isClientSide()) {
            return null;
        }
        return createTickerHelper(p_153214_, ModBlockEntities.GildedTable_BE.get(),
                (level, blockpos, blockstate, gildedTableBlockEntity)-> gildedTableBlockEntity.tick(level,blockpos,blockstate));
    }
    //旋转
    /* FACING */

    @Override
    public BlockState rotate(BlockState p_60530_, Rotation p_60531_) {
        return p_60530_.setValue(FACING,p_60531_.rotate(p_60530_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_60528_, Mirror p_60529_) {
        return p_60528_.rotate(p_60529_.getRotation(p_60528_.getValue(this.FACING)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        //return this.defaultBlockState().setValue(FACING, p_49820_.getHorizontalDirection().getOpposite()).setValue(LIT,false);

        Direction playerFacing = p_49820_.getHorizontalDirection();
        Direction rotated = Rotation.COUNTERCLOCKWISE_90.rotate(playerFacing);
        return this.defaultBlockState().setValue(FACING, rotated);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,LIT);
    }
}
