package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class CoinSyncToClientPacket {
    private final int coinAmount; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public CoinSyncToClientPacket(int coinAmount) {
        this.coinAmount = coinAmount;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static CoinSyncToClientPacket decode(FriendlyByteBuf buf) {
        return new CoinSyncToClientPacket(buf.readInt());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(CoinSyncToClientPacket msg,FriendlyByteBuf buf) {
        buf.writeInt(msg.coinAmount);
    }

    public static void handle(CoinSyncToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getCoin().set(msg.coinAmount); // 更新客户端Coin数据
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
