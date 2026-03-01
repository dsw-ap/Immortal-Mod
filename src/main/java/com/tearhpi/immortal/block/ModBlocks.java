package com.tearhpi.immortal.block;

import com.tearhpi.immortal.Immortal;

import com.tearhpi.immortal.block._decoration.DecoBlockRegister;
import com.tearhpi.immortal.block.custom.EnhancementTable;
import com.tearhpi.immortal.block.custom.ForgingTable;
import com.tearhpi.immortal.block.custom.GildedTable;
import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.item.item_register.BlockItemRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Immortal.MODID);


    public static final RegistryObject<Block> ForgingTable = BLOCKS.register("forging_table", () -> new ForgingTable(BlockBehaviour.Properties.of()
            .strength(2F, 6.0F)
            .requiresCorrectToolForDrops()
            .randomTicks()));
    public static final RegistryObject<Block> EnhancementTable = BLOCKS.register("enhancement_table", () -> new EnhancementTable(BlockBehaviour.Properties.of()
            .strength(2F, 6.0F)
            .requiresCorrectToolForDrops()
            .randomTicks()));
    public static final RegistryObject<Block> GildedTable = BLOCKS.register("gilded_table", () -> new GildedTable(BlockBehaviour.Properties.of()
            .strength(2F, 6.0F)
            .requiresCorrectToolForDrops()
            .randomTicks()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        DecoBlockRegister.register();
    }
}