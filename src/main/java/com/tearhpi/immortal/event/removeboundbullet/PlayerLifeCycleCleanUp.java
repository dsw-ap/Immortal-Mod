package com.tearhpi.immortal.event.removeboundbullet;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

import static com.tearhpi.immortal.event.attributesync.LoginSyncAttribute.SyncAttribute;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class PlayerLifeCycleCleanUp {

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        UUID id = sp.getUUID();
        // 删所有维度里的绑定实体
        BoundEntityManager_NormalAttack.discardAllAcrossLevels(sp);
        BoundEntityManager_SkillAttack.discardAllAcrossLevels(sp);
        // 清理映射
        BoundEntityManager_NormalAttack.clear(sp);
        BoundEntityManager_SkillAttack.clear(sp);
        // 清理槽位跟踪
        HotbarSwitchKiller.resetTracker(id);
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // 判断是否为死亡导致的复制
        if (event.isWasDeath()) {
            //System.out.println(123456);
            ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
            ServerPlayer newPlayer = (ServerPlayer) event.getEntity();

            // 从旧玩家读取 NBT
            CompoundTag oldTag = new CompoundTag();
            IImmortalPlayer oldPlayer1 = (IImmortalPlayer) oldPlayer;
            oldPlayer1.getManaPoint().addAdditionalSaveData(oldTag,oldPlayer1.getManaPoint().get());
            oldPlayer1.getCoin().addAdditionalSaveData(oldTag,oldPlayer1.getCoin().get());
            oldPlayer1.getSkill().addAdditionalSaveData(oldTag,oldPlayer1.getSkill().getNumInfo());
            oldPlayer1.getSkillAssemble().addAdditionalSaveData(oldTag,oldPlayer1.getSkillAssemble().getNumInfo());
            oldPlayer1.getSkillCD().addAdditionalSaveData(oldTag,oldPlayer1.getSkillCD().getNumInfo());
            oldPlayer1.getSkillInUse().addAdditionalSaveData(oldTag,oldPlayer1.getSkillInUse().getNumInfo());

            //System.out.println(oldTag.toString());
            // 从中挑选或整体写回
            IImmortalPlayer newPlayer2 = (IImmortalPlayer) newPlayer;
            newPlayer2.getManaPoint().readAdditionalSaveData(oldTag);
            newPlayer2.getCoin().readAdditionalSaveData(oldTag);
            newPlayer2.getSkill().readAdditionalSaveData(oldTag);
            newPlayer2.getSkillAssemble().readAdditionalSaveData(oldTag);
            newPlayer2.getSkillCD().readAdditionalSaveData(oldTag);
            newPlayer2.getSkillInUse().readAdditionalSaveData(oldTag);

        }
    }
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        SyncAttribute((ServerPlayer) event.getEntity());
    }
    @SubscribeEvent
    public static void onPlayerChangeDim(PlayerEvent.PlayerChangedDimensionEvent event) {
        SyncAttribute((ServerPlayer) event.getEntity());
    }
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            BoundEntityManager_NormalAttack.discardAll(player);
            BoundEntityManager_SkillAttack.discardAll(player);
        }
    }
}
