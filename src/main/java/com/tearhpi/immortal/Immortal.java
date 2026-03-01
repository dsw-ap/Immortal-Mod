package com.tearhpi.immortal;

import com.mojang.logging.LogUtils;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.block.entity.ModBlockEntities;
import com.tearhpi.immortal.block.geo.render.EnhancementTableRenderer;
import com.tearhpi.immortal.block.geo.render.ForgingTableRenderer;
import com.tearhpi.immortal.block.geo.render.GildedTableRenderer;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.geo.render.Skill6Renderer;
import com.tearhpi.immortal.entity.geo.render.Skill7Renderer;
import com.tearhpi.immortal.entity.geo.render.Skill8Renderer;
import com.tearhpi.immortal.entity.geo.render.TrainerDummyRenderer;
import com.tearhpi.immortal.entity.render.*;
import com.tearhpi.immortal.entity.render.Weapons.Weapon1_3Renderer;
import com.tearhpi.immortal.event.ExtraMobNameTag;
import com.tearhpi.immortal.fluid.ModFluids;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.item.ModCreativeModTabs;
import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.particle.*;
import com.tearhpi.immortal.screen.ModMenuTypes;
import com.tearhpi.immortal.screen.custom.EnhancementTableScreen;
import com.tearhpi.immortal.screen.custom.ForgingTableScreen;
import com.tearhpi.immortal.screen.custom.GildedTableScreen;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Immortal.MODID)
public class Immortal
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "immortal";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public Immortal(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        _ModEffects.register(modEventBus);
        ModAttributes.register(modEventBus);
        ModSounds.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModFluids.register(modEventBus);
        //ModNetworking.register();
        //ModDamageTypes.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // 初始化 INSTANCE
        event.enqueueWork(ModNetworking::register);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            //MenuScreens.register(ModMenuTypes.POLISHING_MACHINE_MENU.get(), PolishingMachineScreen::new);
            MenuScreens.register(ModMenuTypes.ENHANCEMENT_TABLE_MENU.get(), EnhancementTableScreen::new);
            MenuScreens.register(ModMenuTypes.FORGING_TABLE_MENU.get(), ForgingTableScreen::new);
            MenuScreens.register(ModMenuTypes.GILDED_TABLE_MENU.get(), GildedTableScreen::new);

            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SWAMP_WATER_SOURCE.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SWAMP_WATER_FLOWING.get(), RenderType.translucent());
            });

            MinecraftForge.EVENT_BUS.addListener(ExtraMobNameTag::onRenderNameTag);
        }
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            // 绑定注册
            event.registerBlockEntityRenderer(ModBlockEntities.EnhancementTable_BE.get(), EnhancementTableRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.ForgingTable_BE.get(), ForgingTableRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.GildedTable_BE.get(), GildedTableRenderer::new);

            event.registerEntityRenderer(ModEntityTypes.TRAINER_DUMMY.get(), TrainerDummyRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.BEZIER_.get(), NoopRenderer::new);
            //武器
            event.registerEntityRenderer(ModEntityTypes.WAND_BULLET_1_1.get(), WandNormalAttackBulletRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.WEAPON1_3_ENTITY.get(), Weapon1_3Renderer::new);
            //技能
            event.registerEntityRenderer(ModEntityTypes.WAND_NORMAL_ATTACK_BULLET.get(), WandNormalAttackBulletRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.WAND_NORMAL_ATTACK_BULLET_ENFORCED.get(), WandNormalEnforcedAttackBulletRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL6_ENTITY.get(), Skill6Renderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL7_ENTITY.get(), Skill7Renderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL7_ENTITY_.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL8_ENTITY.get(), Skill8Renderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL8_ENTITY_.get(), Skill8Renderer_::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL9_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL10_ENTITY.get(), Skill10Renderer_::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL11_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL11_ENTITY_.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL15_ENTITY.get(), Skill15Renderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL17_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL18_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL19_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL20_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL21_ENTITY.get(), Skill21Renderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL22_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL23_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL24_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL26_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL28_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL29_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL30_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL31_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL31_ENTITY_.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL32_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL33_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL34_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL35_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL37_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL39_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL40_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL41_ENTITY.get(), NoopRenderer::new);
            //event.registerEntityRenderer(ModEntityTypes.SKILL42_ENTITY.get(), DisplayRenderer.ItemDisplayRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL42_ENTITY.get(), Skill42Renderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL43_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL44_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL45_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL48_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL50_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL51_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL52_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL53_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL54_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL55_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL56_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL57_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL59_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL59_ENTITY_.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL61_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL62_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL63_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL64_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL64_ENTITY_.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL65_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL66_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL68_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL69_ENTITY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKILL70_ENTITY.get(), NoopRenderer::new);
            //event.registerEntityRenderer(ModEntityTypes.SKILL46_ENTITY.get(), NoopRenderer::new);event.registerEntityRenderer(ModEntityTypes.SKILL48_ENTITY.get(), NoopRenderer::new);

        }
        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.WATER.get(), _WATER.Provider::new);
            event.registerSpriteSet(ModParticles.AIR.get(), _AIR.Provider::new);
            event.registerSpriteSet(ModParticles.EARTH.get(), _EARTH.Provider::new);
            event.registerSpriteSet(ModParticles.LIGHT.get(), _LIGHT.Provider::new);
            event.registerSpriteSet(ModParticles.DARKNESS.get(), _DARKNESS.Provider::new);
            event.registerSpriteSet(ModParticles.WandNormalAttack_Fire.get(), WandNormalAttack_Fire.Provider::new);
            event.registerSpriteSet(ModParticles.WandNormalAttack_Water.get(), WandNormalAttack_Water.Provider::new);
            event.registerSpriteSet(ModParticles.WandNormalAttack_Air.get(), WandNormalAttack_Air.Provider::new);
            event.registerSpriteSet(ModParticles.WandNormalAttack_Earth.get(), WandNormalAttack_Earth.Provider::new);
            event.registerSpriteSet(ModParticles.WandNormalAttack_Light.get(), WandNormalAttack_Light.Provider::new);
            event.registerSpriteSet(ModParticles.WandNormalAttack_Darkness.get(), WandNormalAttack_Darkness.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL6_BH_CORE.get(), Skill6_bh_core.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL6_BH_SPARK.get(), Skill6_bh_spark.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL6_BH_RING.get(), Skill6_bh_ring.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL7_Particle.get(), Skill7_particle.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL9_Particle_Fire.get(), Skill9_particle_fire.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL10_Particle.get(), Skill10_particle.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL11_Particle.get(), Skill11_particle.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL18_Particle_FOG.get(), Skill18_particle_fog.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL18_Particle_ICE.get(), Skill18_particle_ice.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL18_Particle_WATER.get(), Skill18_particle_water.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL21_Particle.get(), Skill21_particle.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL22_Particle.get(), Skill22_particle.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL26_Particle.get(), Skill26_particle.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL31_Particle.get(), Skill31_particle.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL35_Particle.get(), Skill35_particle.Provider::new);
            event.registerSpriteSet(ModParticles.SKILL36_Particle.get(), Skill36_particle.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL36_Particle_vert.get(), Skill36_particle_vert.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL36_Particle_vert_.get(), Skill36_particle_vert_.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL48_Particle.get(), Skill48_particle.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL55_Particle.get(), Skill55_particle.OrbitParticleProvider::new);
            event.registerSpriteSet(ModParticles.SKILL66_Particle.get(), Skill66_particle.OrbitParticleProvider::new);
        }
        @SubscribeEvent
        public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
            //GeoArmorRenderer.registerArmorRenderer(new Test2ArmorItemRenderer(), com.tearhpi.immortal.item.ModItems.TEST2_ARMOR.get());
        }
    }

}
