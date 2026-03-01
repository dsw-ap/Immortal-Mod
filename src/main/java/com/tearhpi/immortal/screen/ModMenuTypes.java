package com.tearhpi.immortal.screen;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.screen.custom.EnhancementTableMenu;
import com.tearhpi.immortal.screen.custom.ForgingTableMenu;
import com.tearhpi.immortal.screen.custom.GildedTableMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Immortal.MODID);

    public static final RegistryObject<MenuType<EnhancementTableMenu>> ENHANCEMENT_TABLE_MENU =
            registerMenuType("enhancement_table_menu", EnhancementTableMenu::new);
    public static final RegistryObject<MenuType<ForgingTableMenu>> FORGING_TABLE_MENU =
            registerMenuType("forging_table_menu", ForgingTableMenu::new);
    public static final RegistryObject<MenuType<GildedTableMenu>> GILDED_TABLE_MENU =
            registerMenuType("gilded_table_menu", GildedTableMenu::new);



    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}