package com.tearhpi.immortal.event;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.client.itemrender.ArmorItemImageTooltipData;
import com.tearhpi.immortal.client.itemrender.ArmorNameWithIconTooltipComponent;
import com.tearhpi.immortal.client.itemrender.ItemImageTooltipData;
import com.tearhpi.immortal.client.itemrender.NameWithIconTooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ToolTipRegister {
    //注册tooltip工厂
    @SubscribeEvent
    public static void onTooltipFactory(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ItemImageTooltipData.class, NameWithIconTooltipComponent::new);
        event.register(ArmorItemImageTooltipData.class, ArmorNameWithIconTooltipComponent::new);
    }
}