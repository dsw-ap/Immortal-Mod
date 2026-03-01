package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.skill.behaviour.active.fire.Skill18;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Skill18_D_Packet {

    public Skill18_D_Packet() {

    }

    // 将数据写入缓冲区（客户端发送时调用）
    public void encode(FriendlyByteBuf buf) {

    }

    public static Skill18_D_Packet decode(FriendlyByteBuf buf) {
        return new Skill18_D_Packet();
    }

    public static void handle(Skill18_D_Packet msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    Skill18.Do_D(player);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
