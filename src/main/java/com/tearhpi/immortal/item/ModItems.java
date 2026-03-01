package com.tearhpi.immortal.item;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.item.item_register.ArmorItemRegister;
import com.tearhpi.immortal.item.item_register.BlockItemRegister;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.item.item_register.WeaponRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Immortal.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        WeaponRegister.register();
        WeaponAddonRegister.register();
        BlockItemRegister.register();
        ArmorItemRegister.register();
    }
}