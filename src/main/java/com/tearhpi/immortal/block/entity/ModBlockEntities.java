package com.tearhpi.immortal.block.entity;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Immortal.MODID);

    public static final RegistryObject<BlockEntityType<EnhancementTableBlockEntity>> EnhancementTable_BE =
            BLOCK_ENTITIES.register("enhancement_table_be", () -> BlockEntityType.Builder.of(
                    EnhancementTableBlockEntity::new, ModBlocks.EnhancementTable.get()).build(null));
    public static final RegistryObject<BlockEntityType<ForgingTableBlockEntity>> ForgingTable_BE =
            BLOCK_ENTITIES.register("forging_table_be", () -> BlockEntityType.Builder.of(
                    ForgingTableBlockEntity::new, ModBlocks.ForgingTable.get()).build(null));
    public static final RegistryObject<BlockEntityType<GildedTableBlockEntity>> GildedTable_BE =
            BLOCK_ENTITIES.register("gilded_table_be", () -> BlockEntityType.Builder.of(
                    GildedTableBlockEntity::new, ModBlocks.GildedTable.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
