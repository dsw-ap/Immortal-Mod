package com.tearhpi.immortal.event.tick;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.Chatbox;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ChatboxSyncToClientPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillCDInfoSyncToClientPacket;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ChatboxCDRemoveTick {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;
        if (event.player != null) {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) event.player;
            if (iImmortalPlayer.getChatbox() != null) {
                iImmortalPlayer.getChatbox().removeCountdown();
                Chatbox c = iImmortalPlayer.getChatbox();
                ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) event.player)),new ChatboxSyncToClientPacket(c.Chatbox_id,c.Chatbox_num,c.Chatbox_countdown));
            }
        }
    }
}
