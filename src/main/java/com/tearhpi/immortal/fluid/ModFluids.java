package com.tearhpi.immortal.fluid;

import com.tearhpi.immortal.item.item_register.ArmorItemRegister;
import com.tearhpi.immortal.item.item_register.BlockItemRegister;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.item.item_register.WeaponRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.tearhpi.immortal.Immortal.MODID;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // 1) FluidType
    public static final RegistryObject<FluidType> SWAMP_WATER_TYPE =
            FLUID_TYPES.register("swamp_water_type", () -> new FluidType(FluidType.Properties.create().density(1000).viscosity(1000).canSwim(true).canDrown(true).supportsBoating(true)) {
                @Override
                public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions() {
                        private static final net.minecraft.resources.ResourceLocation STILL = ResourceLocation.fromNamespaceAndPath("immortal", "block/deco_block/swamp/water_still");
                        private static final net.minecraft.resources.ResourceLocation FLOW = ResourceLocation.fromNamespaceAndPath("immortal", "block/deco_block/swamp/water_flow");

                        @Override
                        public net.minecraft.resources.ResourceLocation getStillTexture() {
                            return STILL;
                        }

                        @Override
                        public net.minecraft.resources.ResourceLocation getFlowingTexture() {
                            return FLOW;
                        }});}});

    // 2) 两个 Fluid（Source / Flowing）
    public static final RegistryObject<FlowingFluid> SWAMP_WATER_SOURCE =
                        FLUIDS.register("swamp_water",
                                () -> new ForgeFlowingFluid.Source(props()));

    public static final RegistryObject<FlowingFluid> SWAMP_WATER_FLOWING =
                        FLUIDS.register("swamp_water_flowing",
                                () -> new ForgeFlowingFluid.Flowing(props()));

    // 3) 流体方块
    public static final RegistryObject<LiquidBlock> SWAMP_WATER_BLOCK = BLOCKS.register("swamp_water_block", () -> new LiquidBlock(SWAMP_WATER_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));

    // 4) 桶
    public static final RegistryObject<Item> SWAMP_WATER_BUCKET = ITEMS.register("swamp_water_bucket", () -> new BucketItem(SWAMP_WATER_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    // ✅ 5) 用方法延迟创建 Properties：不会触发 forward reference
    private static ForgeFlowingFluid.Properties props() {
        return new ForgeFlowingFluid.Properties(SWAMP_WATER_TYPE, SWAMP_WATER_SOURCE, SWAMP_WATER_FLOWING).bucket(SWAMP_WATER_BUCKET).block(SWAMP_WATER_BLOCK).slopeFindDistance(4).levelDecreasePerBlock(1).tickRate(5).explosionResistance(100f);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
}