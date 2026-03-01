package com.tearhpi.immortal.event;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Immortal.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnHealthSet {
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof _ImmortalMob mob && mob.showHealth) {
            if (mob.getSkillBarMax() == 0) {
                mob.setSkillBarMax(100);
                mob.setSkillBarAmount(100);
            }
            mob.setCustomNameVisible(false);
            //mob.setHealth(mob.getMaxHealth());
        }
    }
}
