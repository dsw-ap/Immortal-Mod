package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class NormalAttackEnergySyncPacket {
    private final int energy; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public NormalAttackEnergySyncPacket(int energy) {
        this.energy = energy;
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.energy);
    }

    public static NormalAttackEnergySyncPacket decode(FriendlyByteBuf buf) {
        return new NormalAttackEnergySyncPacket(buf.readInt());
    }

    public static void handle(NormalAttackEnergySyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getNormalAttackEnergy().set(msg.energy);
                    int confirmed = immortal.getNormalAttackEnergy().get();
                    // 回发客户端同步包
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new NormalAttackEnergySyncToClientPacket(confirmed));
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
