package com.tearhpi.immortal.event.removeboundbullet;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.*;

public class BoundEntityManager_NormalAttack {
    private BoundEntityManager_NormalAttack() {}

    // 玩家UUID -> 该玩家名下所有实体UUID
    private static final Map<UUID, Set<UUID>> BOUND = new HashMap<>();

    /** 绑定*/
    public static void bind(ServerPlayer player, Entity entity) {
        BOUND.computeIfAbsent(player.getUUID(), k -> new HashSet<>())
                .add(entity.getUUID());
    }

    /** 解绑 */
    public static void unbind(ServerPlayer player, Entity entity) {
        var set = BOUND.get(player.getUUID());
        if (set != null) {
            set.remove(entity.getUUID());
            if (set.isEmpty()) BOUND.remove(player.getUUID());
        }
    }

    /** 通过实体ID直接解绑 */
    public static void unbind(UUID playerId, UUID entityId) {
        var set = BOUND.get(playerId);
        if (set != null) {
            set.remove(entityId);
            if (set.isEmpty()) BOUND.remove(playerId);
        }
    }

    /** 获取当前世界中所有仍存在的绑定实体 */
    public static List<Entity> getAll(ServerPlayer player) {
        var ids = BOUND.get(player.getUUID());
        if (ids == null || ids.isEmpty()) return List.of();
        ServerLevel lvl = player.serverLevel();
        List<Entity> out = new ArrayList<>();
        for (UUID id : ids) {
            Entity e = lvl.getEntity(id);
            if (e != null && e.isAlive()) out.add(e);
        }
        return out;
    }

    /** 删除并清空此玩家的全部绑定实体 */
    public static void discardAll(ServerPlayer player) {
        var list = getAll(player);
        for (Entity e : list) e.discard();
        BOUND.remove(player.getUUID());
    }

    /** 清空映射 */
    public static void clear(ServerPlayer player) {
        BOUND.remove(player.getUUID());
    }
    /** 跨维度清理 */
    public static void discardAllAcrossLevels(ServerPlayer player) {
        discardAllAcrossLevels(player.server, player.getUUID());
    }

    /** 也支持只给 UUID + 服务器*/
    public static void discardAllAcrossLevels(MinecraftServer server, UUID playerId) {
        var set = BOUND.get(playerId);
        if (set == null || set.isEmpty()) {
            BOUND.remove(playerId);
            return;
        }
        // 遍历所有维度，把该玩家绑定的实体全部移除
        for (ServerLevel lvl : server.getAllLevels()) {
            for (UUID id : new ArrayList<>(set)) {
                Entity e = lvl.getEntity(id);
                if (e != null && e.isAlive()) e.discard();
            }
        }
        BOUND.remove(playerId);
    }
}
