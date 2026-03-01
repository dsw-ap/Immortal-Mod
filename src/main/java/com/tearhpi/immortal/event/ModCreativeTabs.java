package com.tearhpi.immortal.event;

import com.tearhpi.immortal.fluid.ModFluids;
import com.tearhpi.immortal.item.ModCreativeModTabs;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tearhpi.immortal.Immortal.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCreativeTabs {

    @SubscribeEvent
    public static void addToCreativeTabs(net.minecraftforge.event.BuildCreativeModeTabContentsEvent event) {
        // 举例：把桶加到“功能方块/材料”之类，按你想要的 tab 改
        if (event.getTabKey() == ModCreativeModTabs.DECO_BLOCK_SWAMP.getKey()) {
            event.accept(ModFluids.SWAMP_WATER_BUCKET);
        }

        // 如果你有自定义tab key，也可以：
        // if (event.getTabKey() == YOUR_TAB_KEY) event.accept(ModFluids.SWAMP_WATER_BUCKET);
    }
}
