package com.tearhpi.immortal.event;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.effect._ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Immortal.MODID)
public class HealBlocker {
    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        LivingEntity le = event.getEntity();
        if (le.hasEffect(_ModEffects.ANTI_HEAL_EFFECT.get())) {
            event.setCanceled(true);
        }
        if (le.hasEffect(_ModEffects.INJURY_EFFECT.get())) {
            event.setCanceled(true);
        }
    }
}
