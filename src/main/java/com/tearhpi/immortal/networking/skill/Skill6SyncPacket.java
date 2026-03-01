package com.tearhpi.immortal.networking.skill;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
// import your.mod.api.IImmortalPlayer; // 不需要就删

public class Skill6SyncPacket {
    private final float radius; // 想生成的黑洞半径

    // 客户端发送用
    public Skill6SyncPacket(float radius) {
        this.radius = radius;
    }

    // 1.20.1: 静态编解码
    public static void encode(Skill6SyncPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.radius);
    }

    public static Skill6SyncPacket decode(FriendlyByteBuf buf) {
        return new Skill6SyncPacket(buf.readFloat());
    }

    // 处理：客户端 -> 服务端，在主线程生成实体
    public static void handle(Skill6SyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 仅处理 C->S
        if (ctx.getDirection() != NetworkDirection.PLAY_TO_SERVER) {
            ctx.setPacketHandled(true);
            return;
        }

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ServerLevel level = (ServerLevel) player.level();
            float r = msg.radius;

            // 以玩家视线方向，在眼睛前 r*1.5 的位置生成
            Vec3 dir = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
            Vec3 spawnPos = player.getEyePosition().add(dir.scale(r * 1.5));

        });

        ctx.setPacketHandled(true);
    }
}