package com.tearhpi.immortal.event;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom.skills.Skill6_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity;
import com.tearhpi.immortal.entity.custom.TrainerDummy;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Immortal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitySpawn {
    @SubscribeEvent
    public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.TRAINER_DUMMY.get(), TrainerDummy.createAttributes().build());
        event.put(ModEntityTypes.SKILL6_ENTITY.get(), Skill6_Entity.createAttributes().build());
        event.put(ModEntityTypes.SKILL7_ENTITY.get(), Skill7_Entity.createAttributes().build());
    }
}
