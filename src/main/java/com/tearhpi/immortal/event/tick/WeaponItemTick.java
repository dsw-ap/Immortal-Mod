package com.tearhpi.immortal.event.tick;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.WeaponItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class WeaponItemTick {
    //所有和武器相关的Tick放在这里
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;

    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        ItemStack mainhand = player.getMainHandItem();

        if (mainhand.getItem() instanceof WeaponItem weaponItem) {
            
        }
        /*
        if (holdingSpecial) {
            if (!attributeInstance.hasModifier(ModAttributeHandler.HANDHELD_ATTACK_MODIFIER)) {
                attributeInstance.addTransientModifier(ModAttributeHandler.HANDHELD_ATTACK_MODIFIER);
            }
        } else {
            if (attributeInstance.hasModifier(ModAttributeHandler.HANDHELD_ATTACK_MODIFIER)) {
                attributeInstance.removeModifier(ModAttributeHandler.HANDHELD_ATTACK_UUID);
            }
        }

         */
    }
}
