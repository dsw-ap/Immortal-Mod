package com.tearhpi.immortal.item;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.fluid.ModFluids;
import com.tearhpi.immortal.item.item_register.ArmorItemRegister;
import com.tearhpi.immortal.item.item_register.BlockItemRegister;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.item.item_register.WeaponRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Immortal.MODID);

    public static final RegistryObject<CreativeModeTab> WEAPONS = CREATIVE_MODE_TAB.register("weapons", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(WeaponRegister.TestItem7.get()))
            .title(Component.translatable("creativetab.weapons"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(WeaponRegister.TestItem.get());
                output.accept(WeaponRegister.TestItem2.get());
                output.accept(WeaponRegister.TestItem3.get());
                output.accept(WeaponRegister.TestItem4.get());
                output.accept(WeaponRegister.TestItem5.get());
                output.accept(WeaponRegister.TestItem6.get());
                output.accept(WeaponRegister.TestItem7.get());
                output.accept(WeaponRegister.TestItem8.get());
                output.accept(WeaponRegister.Weapon_0_1.get());
                output.accept(WeaponRegister.Weapon_1_1.get());
                output.accept(WeaponRegister.Weapon_1_2.get());
                output.accept(WeaponRegister.Weapon_1_3.get());
                output.accept(WeaponRegister.Weapon_1_4.get());
                output.accept(WeaponRegister.Weapon_2_1.get());
                output.accept(WeaponRegister.Weapon_2_2.get());
                output.accept(WeaponRegister.Weapon_2_3.get());
                output.accept(WeaponRegister.Weapon_2_4.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> ARMORS = CREATIVE_MODE_TAB.register("armors", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.DIAMOND_CHESTPLATE))
            .title(Component.translatable("creativetab.armors"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ArmorItemRegister.TestArmorHelmet.get());
                output.accept(ArmorItemRegister.TestArmorChestplate.get());
                output.accept(ArmorItemRegister.TestArmorLeggings.get());
                output.accept(ArmorItemRegister.TestArmorBoots.get());
                output.accept(ArmorItemRegister.Test2ArmorHelmet.get());
                output.accept(ArmorItemRegister.Test2ArmorChestplate.get());
                output.accept(ArmorItemRegister.Test2ArmorLeggings.get());
                output.accept(ArmorItemRegister.Test2ArmorBoots.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> MACHINES = CREATIVE_MODE_TAB.register("machines", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Blocks.FURNACE))
            .title(Component.translatable("creativetab.machines"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModBlocks.EnhancementTable.get());
                output.accept(ModBlocks.ForgingTable.get());
                output.accept(ModBlocks.GildedTable.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> MATERIALS = CREATIVE_MODE_TAB.register("materials", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(WeaponAddonRegister.WeaponEnhancementStone.get()))
            .title(Component.translatable("creativetab.materials"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(WeaponAddonRegister.WeaponEnhancementStone.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementSuccessScroll_1.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementSuccessScroll_2.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementSuccessScroll_3.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementSuccessScroll_4.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementSuccessScroll_5.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementProtectionScroll_1.get());
                output.accept(WeaponAddonRegister.WeaponEnhancementProtectionScroll_2.get());
                output.accept(WeaponAddonRegister.WeaponForgingStone.get());
                output.accept(WeaponAddonRegister.WeaponForgingChanceScroll_1.get());
                output.accept(WeaponAddonRegister.WeaponForgingChanceScroll_2.get());
                output.accept(WeaponAddonRegister.WeaponForgingChanceScroll_3.get());
                output.accept(WeaponAddonRegister.WeaponForgingResetScroll.get());
                output.accept(WeaponAddonRegister.GoldType1.get());
                output.accept(WeaponAddonRegister.GoldType2.get());
                output.accept(WeaponAddonRegister.GoldType3.get());
                output.accept(WeaponAddonRegister.GoldType4.get());
            })
            .build());


    public static final RegistryObject<CreativeModeTab> DECO_BLOCK_MINE = CREATIVE_MODE_TAB.register("deco_block_mine", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.RAIL))
            .title(Component.translatable("creativetab.deco_block_mine"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(BlockItemRegister.MINE_DIRT_PATH_ITEM.get());
                output.accept(BlockItemRegister.MINE_DIRT_PATH_BLOCK_ITEM.get());
                output.accept(BlockItemRegister.MINE_DIRT_PATH_SLAB_ITEM.get());
                output.accept(BlockItemRegister.MINE_COBBLESTONE_WITH_DIRT_SLAB_ITEM.get());
                output.accept(BlockItemRegister.MINE_STONE_BRICK_WITH_DIRT_SLAB_ITEM.get());
                output.accept(BlockItemRegister.MINE_COBBLESTONE_WITH_DIRT_ITEM.get());
                output.accept(BlockItemRegister.MINE_STONE_BRICK_WITH_DIRT_ITEM.get());
                output.accept(BlockItemRegister.MINE_OAK_CRATE_ITEM.get());
                output.accept(BlockItemRegister.MINE_REFINED_BARREL_ITEM.get());
                output.accept(BlockItemRegister.MINE_REFINED_BARREL_EMPTY_ITEM.get());
                output.accept(BlockItemRegister.MINE_IRON_BUCKET_EMPTY_ITEM.get());
                output.accept(BlockItemRegister.MINE_IRON_BUCKET_ITEM.get());
                output.accept(BlockItemRegister.MINE_FALLEN_IRON_BUCKET_ITEM.get());
                output.accept(BlockItemRegister.MINE_MUSHROOM_A_ITEM.get());
                output.accept(BlockItemRegister.MINE_MUSHROOM_B_ITEM.get());
                output.accept(BlockItemRegister.MINE_MUSHROOM_C_ITEM.get());
                output.accept(BlockItemRegister.MINE_MUSHROOM_D_ITEM.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> DECO_BLOCK_FOREST = CREATIVE_MODE_TAB.register("deco_block_forest", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.GRASS_BLOCK))
            .title(Component.translatable("creativetab.deco_block_forest"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(BlockItemRegister.FOREST_GRASS_ITEM.get());
                output.accept(BlockItemRegister.FOREST_GRASS_SLAB_ITEM.get());
                output.accept(BlockItemRegister.FOREST_FLOURISHING_GRASS_ITEM.get());
                output.accept(BlockItemRegister.FOREST_FLOURISHING_GRASS_SLAB_ITEM.get());
                output.accept(BlockItemRegister.FOREST_SEMI_GREEN_GRASS_ITEM.get());
                output.accept(BlockItemRegister.FOREST_SEMI_GREEN_GRASS_SLAB_ITEM.get());
                output.accept(BlockItemRegister.FOREST_OAK_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.FOREST_BIRCH_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.FOREST_FLOWERING_OAK_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.FOREST_APPLE_OAK_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.FOREST_FRESH_GRASS_ITEM.get());
                output.accept(BlockItemRegister.FOREST_LEAVES_PILE_ITEM.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> DECO_BLOCK_SNOWY = CREATIVE_MODE_TAB.register("deco_block_snowy", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.SNOW_BLOCK))
            .title(Component.translatable("creativetab.deco_block_snowy"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(BlockItemRegister.SNOWY_SPRUCE_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_OAK_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_A_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_B_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_C_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_A_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_B_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_C_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_GRASSBLOCK_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_SNOWY_STONE_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_SNOWY_STONE_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_SNOWY_COBBLESTONE_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_SNOWY_COBBLESTONE_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_FROZEN_EARTH_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_FROZEN_EARTH_PATH_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_FROZEN_EARTH_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_STRIPPED_SPRUCE_LOG_ITEM.get());
                output.accept(BlockItemRegister.SNOWY_SPRUCE_LOG_ITEM.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> DECO_BLOCK_SWAMP = CREATIVE_MODE_TAB.register("deco_block_swamp", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.BLUE_ORCHID))
            .title(Component.translatable("creativetab.deco_block_swamp"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(BlockItemRegister.SWAMP_OAK_LEAVES_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_WATER_PLANT_1_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_WATER_PLANT_2_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_WATER_PLANT_3_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_WATER_PLANT_REED_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_WATER_PLANT_EMPTY_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_REED_PLANT_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_DIRT_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_DIRT_PATH_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_GRASS_BLOCK_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_GRASS_BLOCK_A_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_GRASS_BLOCK_B_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_GRASS_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_DIRT_A_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_DIRT_B_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_DIRT_SLAB_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_PLANT_1_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_PLANT_2_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_PLANT_3_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_PLANT_4_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_PLANT_5_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_PLANT_6_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_BUBBLE_1_ITEM.get());
                output.accept(BlockItemRegister.SWAMP_BUBBLE_2_ITEM.get());


            })
            .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB.register(bus);
    }
}
