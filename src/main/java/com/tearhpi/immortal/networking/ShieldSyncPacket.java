package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class ShieldSyncPacket {
    private final int shieldAmount; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public ShieldSyncPacket(int shieldAmount) {
        this.shieldAmount = shieldAmount;
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.shieldAmount);
    }

    public static ShieldSyncPacket decode(FriendlyByteBuf buf) {
        return new ShieldSyncPacket(buf.readInt());
    }
    /*
    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            // 获取服务端玩家
            ServerPlayer player = context.getSender();
            if (player != null) {
                IImmortalPlayer immortal = (IImmortalPlayer) player;
                immortal.getCoin().add(this.coinAmount);  // 服务端更新
                int confirmed = immortal.getCoin().get(); // 若服务端做校正，这里返回校正后值
                ModNetworking.CHANNEL.send(new CoinSyncToClientPacket(confirmed),PacketDistributor.PLAYER.with(player));
            }
        });
        context.setPacketHandled(true); // 标记包已处理
    }
     */
    public static void handle(ShieldSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getShield().set(msg.shieldAmount);        // 服务端更新
                    int confirmed = immortal.getShield().get();      // 读取校正后数值
                    // 回发客户端同步包
                    ModNetworking.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> player), new ShieldSyncToClientPacket(confirmed)
                    );
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
