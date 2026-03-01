package com.tearhpi.immortal.event.removeboundbullet;

import com.tearhpi.immortal.Immortal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class HotbarSwitchKiller {
    // 记录每个玩家上次选择的热键栏槽位（0~8）
    private static final Map<UUID, Integer> LAST_SELECTED = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END || e.player.level().isClientSide()) return;

        ServerPlayer sp = (ServerPlayer) e.player;
        int current = sp.getInventory().selected;
        UUID id = sp.getUUID();

        Integer last = LAST_SELECTED.putIfAbsent(id, current);
        if (last == null) return;            // 首次记录
        if (current == last) return;         // 未改变

        // ★ 槽位改变：删除该玩家“全部”绑定实体
        BoundEntityManager_NormalAttack.discardAll(sp);

        LAST_SELECTED.put(id, current);
    }

    public static void resetTracker(UUID id) {
        LAST_SELECTED.remove(id);
    }
}
