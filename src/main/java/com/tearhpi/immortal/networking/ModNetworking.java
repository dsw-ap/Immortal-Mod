package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.SkillAssemble;
import com.tearhpi.immortal.networking.skill.C2SSkillDownState;
import com.tearhpi.immortal.networking.skill.Skill6SyncPacket;
import com.tearhpi.immortal.networking.skill.Skill8SyncPacket;
import com.tearhpi.immortal.networking.skill._WandNormalAttackSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModNetworking {
    //public static SimpleChannel INSTANCE;
    //public static final int PROTOCOL_VERSION = 1;
    public static final String PROTOCOL_VERSION = "1";
    public static net.minecraftforge.network.simple.SimpleChannel CHANNEL;

    public static void register() {
        /*
        INSTANCE = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "main"))
                .clientAcceptedVersions((version, status) -> true) // 可根据需要设置版本策略
                .serverAcceptedVersions((version, status) -> true)
                .networkProtocolVersion(PROTOCOL_VERSION)
                .simpleChannel();

         */
        CHANNEL = NetworkRegistry.newSimpleChannel(
                ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        int id = 0;
        CHANNEL.registerMessage(id++,
                EnhancementTableWorkPacket.class,
                EnhancementTableWorkPacket::encode,
                EnhancementTableWorkPacket::decode,
                EnhancementTableWorkPacket::handle);
        CHANNEL.registerMessage(id++,
                CoinSyncPacket.class,
                CoinSyncPacket::encode,
                CoinSyncPacket::decode,
                CoinSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                CoinSyncToClientPacket.class,
                CoinSyncToClientPacket::encode,
                CoinSyncToClientPacket::decode,
                CoinSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                ForgingTableResetPacket.class,
                ForgingTableResetPacket::encode,
                ForgingTableResetPacket::decode,
                ForgingTableResetPacket::handle);
        CHANNEL.registerMessage(id++,
                ForgingTableWorkPacket.class,
                ForgingTableWorkPacket::encode,
                ForgingTableWorkPacket::decode,
                ForgingTableWorkPacket::handle);
        CHANNEL.registerMessage(id++,
                GildedTableWorkPacket.class,
                GildedTableWorkPacket::encode,
                GildedTableWorkPacket::decode,
                GildedTableWorkPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillAssembleInfoSyncPacket.class,
                SkillAssembleInfoSyncPacket::encode,
                SkillAssembleInfoSyncPacket::decode,
                SkillAssembleInfoSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillAssembleInfoSyncToClientPacket.class,
                SkillAssembleInfoSyncToClientPacket::encode,
                SkillAssembleInfoSyncToClientPacket::decode,
                SkillAssembleInfoSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillCDInfoSyncPacket.class,
                SkillCDInfoSyncPacket::encode,
                SkillCDInfoSyncPacket::decode,
                SkillCDInfoSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillCDInfoSyncToClientPacket.class,
                SkillCDInfoSyncToClientPacket::encode,
                SkillCDInfoSyncToClientPacket::decode,
                SkillCDInfoSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillInfoSyncPacket.class,
                SkillInfoSyncPacket::encode,
                SkillInfoSyncPacket::decode,
                SkillInfoSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillInfoSyncToClientPacket.class,
                SkillInfoSyncToClientPacket::encode,
                SkillInfoSyncToClientPacket::decode,
                SkillInfoSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillInUseSyncPacket.class,
                SkillInUseSyncPacket::encode,
                SkillInUseSyncPacket::decode,
                SkillInUseSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillInUseSyncToClientPacket.class,
                SkillInUseSyncToClientPacket::encode,
                SkillInUseSyncToClientPacket::decode,
                SkillInUseSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                SkillInUseSyncToClientPacket.class,
                SkillInUseSyncToClientPacket::encode,
                SkillInUseSyncToClientPacket::decode,
                SkillInUseSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                _WandNormalAttackSyncPacket.class,
                _WandNormalAttackSyncPacket::encode,
                _WandNormalAttackSyncPacket::decode,
                _WandNormalAttackSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                Skill6SyncPacket.class,
                Skill6SyncPacket::encode,
                Skill6SyncPacket::decode,
                Skill6SyncPacket::handle);
        CHANNEL.registerMessage(id++,
                Skill8SyncPacket.class,
                Skill8SyncPacket::encode,
                Skill8SyncPacket::decode,
                Skill8SyncPacket::handle);
        CHANNEL.registerMessage(id++,
                ManaSyncPacket.class,
                ManaSyncPacket::encode,
                ManaSyncPacket::decode,
                ManaSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                ManaSyncToClientPacket.class,
                ManaSyncToClientPacket::encode,
                ManaSyncToClientPacket::decode,
                ManaSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                Skill18_A_Packet.class,
                Skill18_A_Packet::encode,
                Skill18_A_Packet::decode,
                Skill18_A_Packet::handle);
        CHANNEL.registerMessage(id++,
                Skill18_D_Packet.class,
                Skill18_D_Packet::encode,
                Skill18_D_Packet::decode,
                Skill18_D_Packet::handle);
        CHANNEL.registerMessage(id++,
                Skill18_W_Packet.class,
                Skill18_W_Packet::encode,
                Skill18_W_Packet::decode,
                Skill18_W_Packet::handle);
        CHANNEL.registerMessage(id++,
                ShieldSyncPacket.class,
                ShieldSyncPacket::encode,
                ShieldSyncPacket::decode,
                ShieldSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                ShieldSyncToClientPacket.class,
                ShieldSyncToClientPacket::encode,
                ShieldSyncToClientPacket::decode,
                ShieldSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                BuffAmountSyncPacket.class,
                BuffAmountSyncPacket::encode,
                BuffAmountSyncPacket::decode,
                BuffAmountSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                BuffAmountSyncToClientPacket.class,
                BuffAmountSyncToClientPacket::encode,
                BuffAmountSyncToClientPacket::decode,
                BuffAmountSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                ChatboxSyncPacket.class,
                ChatboxSyncPacket::encode,
                ChatboxSyncPacket::decode,
                ChatboxSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                ChatboxSyncToClientPacket.class,
                ChatboxSyncToClientPacket::encode,
                ChatboxSyncToClientPacket::decode,
                ChatboxSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                C2SSkillDownState.class,
                C2SSkillDownState::encode,
                C2SSkillDownState::decode,
                C2SSkillDownState::handle);
        CHANNEL.registerMessage(id++,
                SettingPacket.class,
                SettingPacket::encode,
                SettingPacket::decode,
                SettingPacket::handle);
        CHANNEL.registerMessage(id++,
                SettingToClientPacket.class,
                SettingToClientPacket::encode,
                SettingToClientPacket::decode,
                SettingToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                AchievementPacket.class,
                AchievementPacket::encode,
                AchievementPacket::decode,
                AchievementPacket::handle);
        CHANNEL.registerMessage(id++,
                AchievementToClientPacket.class,
                AchievementToClientPacket::encode,
                AchievementToClientPacket::decode,
                AchievementToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                MainTaskSyncPacket.class,
                MainTaskSyncPacket::encode,
                MainTaskSyncPacket::decode,
                MainTaskSyncPacket::handle);
        CHANNEL.registerMessage(id++,
                MainTaskSyncToClientPacket.class,
                MainTaskSyncToClientPacket::encode,
                MainTaskSyncToClientPacket::decode,
                MainTaskSyncToClientPacket::handle);
        CHANNEL.registerMessage(id++,
                MainTaskSyncToMaxOnePacket.class,
                MainTaskSyncToMaxOnePacket::encode,
                MainTaskSyncToMaxOnePacket::decode,
                MainTaskSyncToMaxOnePacket::handle);
        CHANNEL.registerMessage(id++,
                NormalAttackEnergySyncPacket.class,
                NormalAttackEnergySyncPacket::encode,
                NormalAttackEnergySyncPacket::decode,
                NormalAttackEnergySyncPacket::handle);
        CHANNEL.registerMessage(id++,
                NormalAttackEnergySyncToClientPacket.class,
                NormalAttackEnergySyncToClientPacket::encode,
                NormalAttackEnergySyncToClientPacket::decode,
                NormalAttackEnergySyncToClientPacket::handle);
    }
}
