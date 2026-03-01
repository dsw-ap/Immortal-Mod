package com.tearhpi.immortal.event.tick;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillCDInfoSyncToClientPacket;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class SkillCDRemoveTick {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;
        if (event.player != null) {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) event.player;
            if (iImmortalPlayer.getSkillCD() != null) {
                iImmortalPlayer.getSkillCD().removeCD();
                //特殊
                if (iImmortalPlayer.getSkillCD().Skill35_CD <= 0 && iImmortalPlayer.getSkillCD().Skill35_Charge < 3) {
                    iImmortalPlayer.getSkillCD().Skill35_Charge += 1;
                    ModSkills.setCDbyNumAndPlayer(35, event.player);
                }

                CompoundTag confirmed = iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo()); // 若服务端做校正，这里返回校正后值
                ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) event.player)),new SkillCDInfoSyncToClientPacket(confirmed));
            }
        }
    }
}
