package com.tearhpi.immortal.event.attributesync;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Immortal.MODID)
public class LoginSyncAttribute {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        SyncAttribute(serverPlayer);
    }
    public static void SyncAttribute(ServerPlayer serverPlayer) {
        IImmortalPlayer immortal = (IImmortalPlayer) serverPlayer;
        // 同步金币值给客户端
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new CoinSyncToClientPacket(immortal.getCoin().get())
        );
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new ManaSyncToClientPacket(immortal.getManaPoint().get())
        );
        // 同步SkillInfo给客户端
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new SkillInfoSyncToClientPacket(immortal.getSkill().getSkillCompoundTag(immortal.getSkill().getNumInfo()))
        );
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new SkillAssembleInfoSyncToClientPacket(immortal.getSkillAssemble().getSkillCompoundTag(immortal.getSkillAssemble().getNumInfo()))
        );
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new SkillCDInfoSyncToClientPacket(immortal.getSkillCD().getSkillCompoundTag(immortal.getSkillCD().getNumInfo()))
        );
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new SkillInUseSyncToClientPacket(immortal.getSkillInUse().getSkillCompoundTag(immortal.getSkillInUse().getNumInfo()))
        );
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new ShieldSyncToClientPacket(immortal.getShield().get())
        );
        //同步设置
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new SettingToClientPacket(immortal.getSetting().getSkillCompoundTag(immortal.getSetting().getNumInfo()))
        );
        //同步Chatbox
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new ChatboxSyncToClientPacket(immortal.getChatbox().getID(),immortal.getChatbox().getNum(),immortal.getChatbox().getCountdown())
        );
        //同步任务
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new MainTaskSyncToClientPacket(immortal.getMainTask().get())
        );
        //同步成就
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new AchievementToClientPacket(immortal.getAchievement().getAchievementCompoundTag(immortal.getAchievement().getNumInfo()))
        );
        //同步普攻能量进度
        ModNetworking.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new NormalAttackEnergySyncToClientPacket(immortal.getNormalAttackEnergy().get())
        );
    }
}
