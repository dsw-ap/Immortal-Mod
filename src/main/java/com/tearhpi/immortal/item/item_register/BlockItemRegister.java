package com.tearhpi.immortal.item.item_register;

import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.block._decoration.DecoBlockRegister;
import com.tearhpi.immortal.block._decoration._custom.CustomDirtPathBlock;
import com.tearhpi.immortal.block._decoration._custom.CustomSlabBlock;
import com.tearhpi.immortal.block._decoration.zUtils;
import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.item.custom.geoitem.EnhancementTableItem;
import com.tearhpi.immortal.item.custom.geoitem.ForgingTableItem;
import com.tearhpi.immortal.item.custom.geoitem.GildedTableItem;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import static com.tearhpi.immortal.block.ModBlocks.BLOCKS;
import static com.tearhpi.immortal.item.ModItems.ITEMS;

public class BlockItemRegister {
    public static void register() {}
    public static final RegistryObject<Item> EnhancementTableItem = ITEMS.register("enhancement_table", () -> new EnhancementTableItem(ModBlocks.EnhancementTable.get(),new Item.Properties()));
    public static final RegistryObject<Item> ForgingTableItem = ITEMS.register("forging_table", () -> new ForgingTableItem(ModBlocks.ForgingTable.get(),new Item.Properties()));
    public static final RegistryObject<Item> GildedTableItem = ITEMS.register("gilded_table", () -> new GildedTableItem(ModBlocks.GildedTable.get(),new Item.Properties()));

    //装饰-矿井
    //矿井-干燥土径
    public static final RegistryObject<Item> MINE_DIRT_PATH_ITEM =
            ITEMS.register("deco_mine_dirt_path", () -> new BlockItem(DecoBlockRegister.MINE_DIRT_PATH.get(), new Item.Properties()));
    //矿井-干燥土块
    public static final RegistryObject<Item> MINE_DIRT_PATH_BLOCK_ITEM =
            ITEMS.register("deco_mine_dirt_path_block", () -> new BlockItem(DecoBlockRegister.MINE_DIRT_PATH_BLOCK.get(), new Item.Properties()));
    //矿井-干燥土径半砖
    public static final RegistryObject<Item> MINE_DIRT_PATH_SLAB_ITEM =
            ITEMS.register("deco_mine_dirt_path_slab", () -> new BlockItem(DecoBlockRegister.MINE_DIRT_PATH_SLAB.get(), new Item.Properties()));
    //矿井-掺土石砖半砖
    public static final RegistryObject<Item> MINE_STONE_BRICK_WITH_DIRT_SLAB_ITEM =
            ITEMS.register("deco_mine_stone_brick_with_dirt_slab", () -> new BlockItem(DecoBlockRegister.MINE_STONE_BRICK_WITH_DIRT_SLAB.get(), new Item.Properties()));
    //矿井-掺土圆石半砖
    public static final RegistryObject<Item> MINE_COBBLESTONE_WITH_DIRT_SLAB_ITEM =
            ITEMS.register("deco_mine_cobblestone_with_dirt_slab", () -> new BlockItem(DecoBlockRegister.MINE_COBBLESTONE_WITH_DIRT_SLAB.get(), new Item.Properties()));
    //矿井-掺土石砖
    public static final RegistryObject<Item> MINE_STONE_BRICK_WITH_DIRT_ITEM =
            ITEMS.register("deco_mine_stone_brick_with_dirt", () -> new BlockItem(DecoBlockRegister.MINE_STONE_BRICK_WITH_DIRT.get(), new Item.Properties()));
    //矿井-掺土圆石
    public static final RegistryObject<Item> MINE_COBBLESTONE_WITH_DIRT_ITEM =
            ITEMS.register("deco_mine_cobblestone_with_dirt", () -> new BlockItem(DecoBlockRegister.MINE_COBBLESTONE_WITH_DIRT.get(), new Item.Properties()));
    //矿井-木板条箱
    public static final RegistryObject<Item> MINE_OAK_CRATE_ITEM =
            ITEMS.register("deco_mine_oak_crate", () -> new BlockItem(DecoBlockRegister.MINE_OAK_CRATE.get(), new Item.Properties()));
    //矿井-精制木桶
    public static final RegistryObject<Item> MINE_REFINED_BARREL_ITEM =
            ITEMS.register("deco_mine_refined_barrel", () -> new BlockItem(DecoBlockRegister.MINE_REFINED_BARREL.get(), new Item.Properties()));
    //矿井-精制木桶(空)
    public static final RegistryObject<Item> MINE_REFINED_BARREL_EMPTY_ITEM =
            ITEMS.register("deco_mine_refined_barrel_empty", () -> new BlockItem(DecoBlockRegister.MINE_REFINED_BARREL_EMPTY.get(), new Item.Properties()));
    //矿井-铁桶(空)
    public static final RegistryObject<Item> MINE_IRON_BUCKET_EMPTY_ITEM =
            ITEMS.register("deco_mine_iron_bucket_empty", () -> new BlockItem(DecoBlockRegister.MINE_IRON_BUCKET_EMPTY.get(), new Item.Properties()));
    //矿井-铁桶
    public static final RegistryObject<Item> MINE_IRON_BUCKET_ITEM =
            ITEMS.register("deco_mine_iron_bucket", () -> new BlockItem(DecoBlockRegister.MINE_IRON_BUCKET.get(), new Item.Properties()));
    //矿井-打翻的铁桶
    public static final RegistryObject<Item> MINE_FALLEN_IRON_BUCKET_ITEM =
            ITEMS.register("deco_mine_fallen_iron_bucket", () -> new BlockItem(DecoBlockRegister.MINE_FALLEN_IRON_BUCKET.get(), new Item.Properties()));
    //矿井-蘑菇
    public static final RegistryObject<Item> MINE_MUSHROOM_A_ITEM =
            ITEMS.register("deco_mine_mushroom_a", () -> new BlockItem(DecoBlockRegister.MINE_MUSHROOM_A.get(), new Item.Properties()));
    public static final RegistryObject<Item> MINE_MUSHROOM_B_ITEM =
            ITEMS.register("deco_mine_mushroom_b", () -> new BlockItem(DecoBlockRegister.MINE_MUSHROOM_B.get(), new Item.Properties()));
    public static final RegistryObject<Item> MINE_MUSHROOM_C_ITEM =
            ITEMS.register("deco_mine_mushroom_c", () -> new BlockItem(DecoBlockRegister.MINE_MUSHROOM_C.get(), new Item.Properties()));
    public static final RegistryObject<Item> MINE_MUSHROOM_D_ITEM =
            ITEMS.register("deco_mine_mushroom_d", () -> new BlockItem(DecoBlockRegister.MINE_MUSHROOM_D.get(), new Item.Properties()));
    //装饰-森林
    //森林-丛林草块
    public static final RegistryObject<Item> FOREST_GRASS_ITEM =
            ITEMS.register("deco_forest_forest_grass", () -> new BlockItem(DecoBlockRegister.FOREST_GRASS.get(), new Item.Properties()));
    //森林-丛林草块半砖
    public static final RegistryObject<Item> FOREST_GRASS_SLAB_ITEM =
            ITEMS.register("deco_forest_forest_grass_slab", () -> new BlockItem(DecoBlockRegister.FOREST_GRASS_SLAB.get(), new Item.Properties()));
    //森林-繁茂草皮
    public static final RegistryObject<Item> FOREST_FLOURISHING_GRASS_ITEM =
            ITEMS.register("deco_forest_flourishing_grass", () -> new BlockItem(DecoBlockRegister.FOREST_FLOURISHING_GRASS.get(), new Item.Properties()));
    //森林-繁茂草皮半砖
    public static final RegistryObject<Item> FOREST_FLOURISHING_GRASS_SLAB_ITEM =
            ITEMS.register("deco_forest_flourishing_grass_slab", () -> new BlockItem(DecoBlockRegister.FOREST_FLOURISHING_GRASS_SLAB.get(), new Item.Properties()));
    //森林-半绿草皮
    public static final RegistryObject<Item> FOREST_SEMI_GREEN_GRASS_ITEM =
            ITEMS.register("deco_forest_semi_green_grass", () -> new BlockItem(DecoBlockRegister.FOREST_SEMI_GREEN_GRASS.get(), new Item.Properties()));
    //森林-半绿草皮半砖
    public static final RegistryObject<Item> FOREST_SEMI_GREEN_GRASS_SLAB_ITEM =
            ITEMS.register("deco_forest_semi_green_grass_slab", () -> new BlockItem(DecoBlockRegister.FOREST_SEMI_GREEN_GRASS_SLAB.get(), new Item.Properties()));
    //森林-橡树叶
    public static final RegistryObject<Item> FOREST_OAK_LEAVES_ITEM =
            ITEMS.register("deco_forest_oak_leaves", () -> new BlockItem(DecoBlockRegister.FOREST_OAK_LEAVES.get(), new Item.Properties()));
    //森林-白桦叶
    public static final RegistryObject<Item> FOREST_BIRCH_LEAVES_ITEM =
            ITEMS.register("deco_forest_birch_leaves", () -> new BlockItem(DecoBlockRegister.FOREST_BIRCH_LEAVES.get(), new Item.Properties()));
    //森林-盛开的橡树叶
    public static final RegistryObject<Item> FOREST_FLOWERING_OAK_LEAVES_ITEM =
            ITEMS.register("deco_forest_flowering_oak_leaves", () -> new BlockItem(DecoBlockRegister.FOREST_FLOWERING_OAK_LEAVES.get(), new Item.Properties()));
    //森林-结果的橡树叶
    public static final RegistryObject<Item> FOREST_APPLE_OAK_LEAVES_ITEM =
            ITEMS.register("deco_forest_apple_oak_leaves", () -> new BlockItem(DecoBlockRegister.FOREST_APPLE_OAK_LEAVES.get(), new Item.Properties()));
    //森林-翠绿草皮
    public static final RegistryObject<Item> FOREST_FRESH_GRASS_ITEM =
            ITEMS.register("deco_forest_fresh_grass", () -> new BlockItem(DecoBlockRegister.FOREST_FRESH_GRASS.get(), new Item.Properties()));
    //森林-落叶堆
    public static final RegistryObject<Item> FOREST_LEAVES_PILE_ITEM =
            ITEMS.register("deco_forest_leaves_pile", () -> new BlockItem(DecoBlockRegister.FOREST_LEAVES_PILE.get(), new Item.Properties()));

    //装饰-雪地
    //雪地-深云杉叶
    public static final RegistryObject<Item> SNOWY_SPRUCE_LEAVES_ITEM =
            ITEMS.register("deco_snowy_spruce_leaves", () -> new BlockItem(DecoBlockRegister.SNOWY_SPRUCE_LEAVES.get(), new Item.Properties()));
    //雪地-深橡树叶
    public static final RegistryObject<Item> SNOWY_OAK_LEAVES_ITEM =
            ITEMS.register("deco_snowy_oak_leaves", () -> new BlockItem(DecoBlockRegister.SNOWY_OAK_LEAVES.get(), new Item.Properties()));
    //雪地-覆雪草块A
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_A_ITEM =
            ITEMS.register("deco_snowy_grassblock_a", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_A.get(), new Item.Properties()));
    //雪地-覆雪草块B
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_B_ITEM =
            ITEMS.register("deco_snowy_grassblock_b", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_B.get(), new Item.Properties()));
    //雪地-覆雪草块C
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_C_ITEM =
            ITEMS.register("deco_snowy_grassblock_c", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_C.get(), new Item.Properties()));
    //雪地-覆雪草块A_台阶
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_A_SLAB_ITEM =
            ITEMS.register("deco_snowy_grassblock_a_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_A_SLAB.get(), new Item.Properties()));
    //雪地-覆雪草块B_台阶
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_B_SLAB_ITEM =
            ITEMS.register("deco_snowy_grassblock_b_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_B_SLAB.get(), new Item.Properties()));
    //雪地-覆雪草块C_台阶
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_C_SLAB_ITEM =
            ITEMS.register("deco_snowy_grassblock_c_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_C_SLAB.get(), new Item.Properties()));
    //雪地-覆雪草块
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_ITEM =
            ITEMS.register("deco_snowy_grassblock", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK.get(), new Item.Properties()));
    //雪地-覆雪草块台阶
    public static final RegistryObject<Item> SNOWY_GRASSBLOCK_SLAB_ITEM =
            ITEMS.register("deco_snowy_grassblock_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_GRASSBLOCK_SLAB.get(), new Item.Properties()));
    //雪地-覆雪石头
    public static final RegistryObject<Item> SNOWY_SNOWY_STONE_ITEM =
            ITEMS.register("deco_snowy_snowy_stone", () -> new BlockItem(DecoBlockRegister.SNOWY_SNOWY_STONE.get(), new Item.Properties()));
    //雪地-覆雪石头台阶
    public static final RegistryObject<Item> SNOWY_SNOWY_STONE_SLAB_ITEM =
            ITEMS.register("deco_snowy_snowy_stone_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_SNOWY_STONE_SLAB.get(), new Item.Properties()));
    //雪地-覆雪圆石
    public static final RegistryObject<Item> SNOWY_SNOWY_COBBLESTONE_ITEM =
            ITEMS.register("deco_snowy_snowy_cobblestone", () -> new BlockItem(DecoBlockRegister.SNOWY_SNOWY_COBBLESTONE.get(), new Item.Properties()));
    //雪地-覆雪圆石台阶
    public static final RegistryObject<Item> SNOWY_SNOWY_COBBLESTONE_SLAB_ITEM =
            ITEMS.register("deco_snowy_snowy_cobblestone_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_SNOWY_COBBLESTONE_SLAB.get(), new Item.Properties()));
    //雪地-冻土
    public static final RegistryObject<Item> SNOWY_FROZEN_EARTH_ITEM =
            ITEMS.register("deco_snowy_frozen_earth", () -> new BlockItem(DecoBlockRegister.SNOWY_FROZEN_EARTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> SNOWY_FROZEN_EARTH_PATH_ITEM =
            ITEMS.register("deco_snowy_frozen_earth_path", () -> new BlockItem(DecoBlockRegister.SNOWY_FROZEN_EARTH_PATH.get(), new Item.Properties()));
    public static final RegistryObject<Item> SNOWY_FROZEN_EARTH_SLAB_ITEM =
            ITEMS.register("deco_snowy_frozen_earth_slab", () -> new BlockItem(DecoBlockRegister.SNOWY_FROZEN_EARTH_SLAB.get(), new Item.Properties()));
    //雪地-木头
    public static final RegistryObject<Item> SNOWY_STRIPPED_SPRUCE_LOG_ITEM =
            ITEMS.register("deco_snowy_stripped_spruce_log", () -> new BlockItem(DecoBlockRegister.SNOWY_STRIPPED_SPRUCE_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> SNOWY_SPRUCE_LOG_ITEM =
            ITEMS.register("deco_snowy_spruce_log", () -> new BlockItem(DecoBlockRegister.SNOWY_SPRUCE_LOG.get(), new Item.Properties()));

    //装饰-沼泽
    //沼泽-橡树叶
    public static final RegistryObject<Item> SWAMP_OAK_LEAVES_ITEM =
            ITEMS.register("deco_swamp_oak_leaves", () -> new BlockItem(DecoBlockRegister.SWAMP_OAK_LEAVES.get(), new Item.Properties()));
    //沼泽-水培植物
    public static final RegistryObject<Item> SWAMP_WATER_PLANT_1_ITEM =
            ITEMS.register("deco_swamp_water_plant_1", () -> new BlockItem(DecoBlockRegister.SWAMP_WATER_PLANT_1.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_WATER_PLANT_2_ITEM =
            ITEMS.register("deco_swamp_water_plant_2", () -> new BlockItem(DecoBlockRegister.SWAMP_WATER_PLANT_2.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_WATER_PLANT_3_ITEM =
            ITEMS.register("deco_swamp_water_plant_3", () -> new BlockItem(DecoBlockRegister.SWAMP_WATER_PLANT_3.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_WATER_PLANT_REED_ITEM =
            ITEMS.register("deco_swamp_water_plant_reed", () -> new BlockItem(DecoBlockRegister.SWAMP_WATER_PLANT_REED.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_WATER_PLANT_EMPTY_ITEM =
            ITEMS.register("deco_swamp_water_plant_empty", () -> new BlockItem(DecoBlockRegister.SWAMP_WATER_PLANT_EMPTY.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_REED_PLANT_ITEM =
            ITEMS.register("deco_swamp_reed_plant", () -> new BlockItem(DecoBlockRegister.SWAMP_REED_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_DIRT_ITEM =
            ITEMS.register("deco_swamp_dirt", () -> new BlockItem(DecoBlockRegister.SWAMP_DIRT.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_DIRT_PATH_ITEM =
            ITEMS.register("deco_swamp_dirt_path", () -> new BlockItem(DecoBlockRegister.SWAMP_DIRT_PATH.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_GRASS_BLOCK_ITEM =
            ITEMS.register("deco_swamp_grass_block", () -> new BlockItem(DecoBlockRegister.SWAMP_GRASS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_GRASS_BLOCK_A_ITEM =
            ITEMS.register("deco_swamp_grass_block_a", () -> new BlockItem(DecoBlockRegister.SWAMP_GRASS_BLOCK_A.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_GRASS_BLOCK_B_ITEM =
            ITEMS.register("deco_swamp_grass_block_b", () -> new BlockItem(DecoBlockRegister.SWAMP_GRASS_BLOCK_B.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_GRASS_SLAB_ITEM =
            ITEMS.register("deco_swamp_grass_slab", () -> new BlockItem(DecoBlockRegister.SWAMP_GRASS_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_DIRT_A_SLAB_ITEM =
            ITEMS.register("deco_swamp_dirt_a_slab", () -> new BlockItem(DecoBlockRegister.SWAMP_DIRT_A_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_DIRT_B_SLAB_ITEM =
            ITEMS.register("deco_swamp_dirt_b_slab", () -> new BlockItem(DecoBlockRegister.SWAMP_DIRT_B_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_DIRT_SLAB_ITEM =
            ITEMS.register("deco_swamp_dirt_slab", () -> new BlockItem(DecoBlockRegister.SWAMP_DIRT_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_PLANT_1_ITEM =
            ITEMS.register("deco_swamp_plant_1", () -> new BlockItem(DecoBlockRegister.SWAMP_PLANT_1.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_PLANT_2_ITEM =
            ITEMS.register("deco_swamp_plant_2", () -> new BlockItem(DecoBlockRegister.SWAMP_PLANT_2.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_PLANT_3_ITEM =
            ITEMS.register("deco_swamp_plant_3", () -> new BlockItem(DecoBlockRegister.SWAMP_PLANT_3.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_PLANT_4_ITEM =
            ITEMS.register("deco_swamp_plant_4", () -> new BlockItem(DecoBlockRegister.SWAMP_PLANT_4.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_PLANT_5_ITEM =
            ITEMS.register("deco_swamp_plant_5", () -> new BlockItem(DecoBlockRegister.SWAMP_PLANT_5.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_PLANT_6_ITEM =
            ITEMS.register("deco_swamp_plant_6", () -> new BlockItem(DecoBlockRegister.SWAMP_PLANT_6.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_BUBBLE_1_ITEM =
            ITEMS.register("deco_swamp_bubble_1", () -> new BlockItem(DecoBlockRegister.SWAMP_BUBBLE_1.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_BUBBLE_2_ITEM =
            ITEMS.register("deco_swamp_bubble_2", () -> new BlockItem(DecoBlockRegister.SWAMP_BUBBLE_2.get(), new Item.Properties()));
}
