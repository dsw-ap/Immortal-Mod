package com.tearhpi.immortal.block._decoration;

import com.tearhpi.immortal.block._decoration._custom.*;
import com.tearhpi.immortal.block._decoration.mine.IronBucketBlock;
import com.tearhpi.immortal.block._decoration.mine.RefinedBarrelBlock;
import com.tearhpi.immortal.block._decoration.swamp.CustomSwampBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import static com.tearhpi.immortal.block.ModBlocks.BLOCKS;
import static com.tearhpi.immortal.item.ModItems.ITEMS;

public class DecoBlockRegister{
    public static void register() {}
    //矿井部分
    //矿井-干燥土径
    public static final RegistryObject<Block> MINE_DIRT_PATH = BLOCKS.register("deco_mine_dirt_path", () -> new CustomDirtPathBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.ROOTED_DIRT)));
    //矿井-干燥土块
    public static final RegistryObject<Block> MINE_DIRT_PATH_BLOCK = BLOCKS.register("deco_mine_dirt_path_block", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.ROOTED_DIRT)));
    //矿井-干燥土径半砖
    public static final RegistryObject<Block> MINE_DIRT_PATH_SLAB = BLOCKS.register("deco_mine_dirt_path_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.ROOTED_DIRT)));
    //矿井-掺土石砖半砖
    public static final RegistryObject<Block> MINE_STONE_BRICK_WITH_DIRT_SLAB = BLOCKS.register("deco_mine_stone_brick_with_dirt_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()));
    //矿井-掺土圆石半砖
    public static final RegistryObject<Block> MINE_COBBLESTONE_WITH_DIRT_SLAB = BLOCKS.register("deco_mine_cobblestone_with_dirt_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()));
    //矿井-掺土石砖
    public static final RegistryObject<Block> MINE_STONE_BRICK_WITH_DIRT = BLOCKS.register("deco_mine_stone_brick_with_dirt", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()));
    //矿井-掺土圆石
    public static final RegistryObject<Block> MINE_COBBLESTONE_WITH_DIRT = BLOCKS.register("deco_mine_cobblestone_with_dirt", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()));
    //矿井-木板条箱
    public static final RegistryObject<Block> MINE_OAK_CRATE = BLOCKS.register("deco_mine_oak_crate", () -> new BarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.5F)
            .sound(SoundType.WOOD)
            .ignitedByLava()));
    //矿井-精制木桶
    public static final RegistryObject<Block> MINE_REFINED_BARREL = BLOCKS.register("deco_mine_refined_barrel", () -> new RefinedBarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.5F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
            .noOcclusion()
            .isSuffocating((s, l, p) -> false)
            .isViewBlocking((s, l, p) -> false)));
    //矿井-精制木桶(空)
    public static final RegistryObject<Block> MINE_REFINED_BARREL_EMPTY = BLOCKS.register("deco_mine_refined_barrel_empty", () -> new RefinedBarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.5F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
            .noOcclusion()
            .isSuffocating((s, l, p) -> false)
            .isViewBlocking((s, l, p) -> false)));
    //矿井-铁桶(空)
    public static final RegistryObject<Block> MINE_IRON_BUCKET_EMPTY = BLOCKS.register("deco_mine_iron_bucket_empty", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(2.5F)
            .sound(SoundType.METAL)));
    //矿井-铁桶(满)
    public static final RegistryObject<Block> MINE_IRON_BUCKET = BLOCKS.register("deco_mine_iron_bucket", () -> new IronBucketBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(2.5F)
            .sound(SoundType.METAL)));
    //矿井-打翻的铁桶
    public static final RegistryObject<Block> MINE_FALLEN_IRON_BUCKET = BLOCKS.register("deco_mine_fallen_iron_bucket", () -> new IronBucketBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(2.5F)
            .sound(SoundType.METAL)));
    //矿井-蘑菇
    public static final RegistryObject<Block> MINE_MUSHROOM_A = BLOCKS.register("deco_mine_mushroom_a", () -> new CustomMushroomBlock(BlockBehaviour.Properties.of()
            .noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(zUtils::always).pushReaction(PushReaction.DESTROY), TreeFeatures.OAK));
    public static final RegistryObject<Block> MINE_MUSHROOM_B = BLOCKS.register("deco_mine_mushroom_b", () -> new CustomMushroomBlock(BlockBehaviour.Properties.of()
            .noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(zUtils::always).pushReaction(PushReaction.DESTROY), TreeFeatures.OAK));
    public static final RegistryObject<Block> MINE_MUSHROOM_C = BLOCKS.register("deco_mine_mushroom_c", () -> new CustomMushroomBlock(BlockBehaviour.Properties.of()
            .noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(zUtils::always).pushReaction(PushReaction.DESTROY), TreeFeatures.OAK));
    public static final RegistryObject<Block> MINE_MUSHROOM_D = BLOCKS.register("deco_mine_mushroom_d", () -> new CustomMushroomBlock(BlockBehaviour.Properties.of()
            .noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(zUtils::always).pushReaction(PushReaction.DESTROY), TreeFeatures.OAK));

    //森林部分
    //森林-贫瘠草块
    public static final RegistryObject<Block> FOREST_GRASS = BLOCKS.register("deco_forest_forest_grass", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-贫瘠草块半砖
    public static final RegistryObject<Block> FOREST_GRASS_SLAB = BLOCKS.register("deco_forest_forest_grass_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-繁茂草皮
    public static final RegistryObject<Block> FOREST_FLOURISHING_GRASS = BLOCKS.register("deco_forest_flourishing_grass", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-繁茂草皮半砖
    public static final RegistryObject<Block> FOREST_FLOURISHING_GRASS_SLAB = BLOCKS.register("deco_forest_flourishing_grass_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-半绿草皮
    public static final RegistryObject<Block> FOREST_SEMI_GREEN_GRASS = BLOCKS.register("deco_forest_semi_green_grass", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-半绿草皮半砖
    public static final RegistryObject<Block> FOREST_SEMI_GREEN_GRASS_SLAB = BLOCKS.register("deco_forest_semi_green_grass_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-橡树叶
    public static final RegistryObject<Block> FOREST_OAK_LEAVES = BLOCKS.register("deco_forest_oak_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //森林-白桦叶
    public static final RegistryObject<Block> FOREST_BIRCH_LEAVES = BLOCKS.register("deco_forest_birch_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //森林-盛开的橡树叶
    public static final RegistryObject<Block> FOREST_FLOWERING_OAK_LEAVES = BLOCKS.register("deco_forest_flowering_oak_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //森林-结果的橡树叶
    public static final RegistryObject<Block> FOREST_APPLE_OAK_LEAVES = BLOCKS.register("deco_forest_apple_oak_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //森林-翠绿草皮
    public static final RegistryObject<Block> FOREST_FRESH_GRASS = BLOCKS.register("deco_forest_fresh_grass", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //森林-落叶堆
    public static final RegistryObject<Block> FOREST_LEAVES_PILE = BLOCKS.register("deco_forest_leaves_pile",  () -> new PinkPetalsBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .sound(SoundType.PINK_PETALS)
            .pushReaction(PushReaction.DESTROY)));

    //雪地部分
    //雪地-寒带云杉树叶
    public static final RegistryObject<Block> SNOWY_SPRUCE_LEAVES = BLOCKS.register("deco_snowy_spruce_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //雪地-寒带云杉树叶
    public static final RegistryObject<Block> SNOWY_OAK_LEAVES = BLOCKS.register("deco_snowy_oak_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //雪地-覆雪草块A
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_A = BLOCKS.register("deco_snowy_grassblock_a", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块B
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_B = BLOCKS.register("deco_snowy_grassblock_b", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块C
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_C = BLOCKS.register("deco_snowy_grassblock_c", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块A_台阶
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_A_SLAB = BLOCKS.register("deco_snowy_grassblock_a_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块B_台阶
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_B_SLAB = BLOCKS.register("deco_snowy_grassblock_b_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块C_台阶
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_C_SLAB = BLOCKS.register("deco_snowy_grassblock_c_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK = BLOCKS.register("deco_snowy_grassblock", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪草块台阶
    public static final RegistryObject<Block> SNOWY_GRASSBLOCK_SLAB = BLOCKS.register("deco_snowy_grassblock_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    //雪地-覆雪石头
    public static final RegistryObject<Block> SNOWY_SNOWY_STONE = BLOCKS.register("deco_snowy_snowy_stone", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)));
    //雪地-覆雪石头台阶
    public static final RegistryObject<Block> SNOWY_SNOWY_STONE_SLAB = BLOCKS.register("deco_snowy_snowy_stone_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)));
    //雪地-覆雪圆石
    public static final RegistryObject<Block> SNOWY_SNOWY_COBBLESTONE = BLOCKS.register("deco_snowy_snowy_cobblestone", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)));
    //雪地-覆雪圆石台阶
    public static final RegistryObject<Block> SNOWY_SNOWY_COBBLESTONE_SLAB = BLOCKS.register("deco_snowy_snowy_cobblestone_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)));
    //雪地-冻土
    public static final RegistryObject<Block> SNOWY_FROZEN_EARTH = BLOCKS.register("deco_snowy_frozen_earth", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> SNOWY_FROZEN_EARTH_PATH = BLOCKS.register("deco_snowy_frozen_earth_path", () -> new DirtPathBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> SNOWY_FROZEN_EARTH_SLAB = BLOCKS.register("deco_snowy_frozen_earth_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRAVEL)));
    //雪地-木头
    public static final RegistryObject<Block> SNOWY_STRIPPED_SPRUCE_LOG = BLOCKS.register("deco_snowy_stripped_spruce_log", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SNOWY_SPRUCE_LOG = BLOCKS.register("deco_snowy_spruce_log", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.WOOD)));

    //沼泽部分
    //沼泽-橡树树叶
    public static final RegistryObject<Block> SWAMP_OAK_LEAVES = BLOCKS.register("deco_swamp_oak_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .noOcclusion()
            .isValidSpawn(zUtils::ocelotOrParrot)
            .isSuffocating(zUtils::never)
            .isViewBlocking(zUtils::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(zUtils::never)));
    //沼泽-水培植物
    public static final RegistryObject<Block> SWAMP_WATER_PLANT_1 = BLOCKS.register("deco_swamp_water_plant_1", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_WATER_PLANT_2 = BLOCKS.register("deco_swamp_water_plant_2", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_WATER_PLANT_3 = BLOCKS.register("deco_swamp_water_plant_3", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_WATER_PLANT_REED = BLOCKS.register("deco_swamp_water_plant_reed", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_WATER_PLANT_EMPTY = BLOCKS.register("deco_swamp_water_plant_empty", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_REED_PLANT = BLOCKS.register("deco_swamp_reed_plant", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_DIRT = BLOCKS.register("deco_swamp_dirt", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_DIRT_PATH = BLOCKS.register("deco_swamp_dirt_path", () -> new CustomDirtPathBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_GRASS_BLOCK = BLOCKS.register("deco_swamp_grass_block", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_GRASS_BLOCK_A = BLOCKS.register("deco_swamp_grass_block_a", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_GRASS_BLOCK_B = BLOCKS.register("deco_swamp_grass_block_b", () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_DIRT_A_SLAB = BLOCKS.register("deco_swamp_dirt_a_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_DIRT_B_SLAB = BLOCKS.register("deco_swamp_dirt_b_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_DIRT_SLAB = BLOCKS.register("deco_swamp_dirt_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_GRASS_SLAB = BLOCKS.register("deco_swamp_grass_slab", () -> new CustomSlabBlock(BlockBehaviour.Properties.of()
            .strength(2.0f, 6.0f)
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_PLANT_1 = BLOCKS.register("deco_swamp_plant_1", () -> new BushBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_PLANT_2 = BLOCKS.register("deco_swamp_plant_2", () -> new BushBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_PLANT_3 = BLOCKS.register("deco_swamp_plant_3", () -> new BushBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_PLANT_4 = BLOCKS.register("deco_swamp_plant_4", () -> new CustomSwampBushBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_PLANT_5 = BLOCKS.register("deco_swamp_plant_5", () -> new CustomSwampBushBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_PLANT_6 = BLOCKS.register("deco_swamp_plant_6", () -> new CustomSwampBushBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SWAMP_BUBBLE_1 = BLOCKS.register("deco_swamp_bubble_1", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> SWAMP_BUBBLE_2 = BLOCKS.register("deco_swamp_bubble_2", () -> new CustomPlantBlock(BlockBehaviour.Properties.of()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.WET_GRASS)));
}
