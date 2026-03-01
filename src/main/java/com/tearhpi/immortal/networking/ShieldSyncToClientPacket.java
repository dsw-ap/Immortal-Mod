package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShieldSyncToClientPacket {
    private final int shieldAmount; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public ShieldSyncToClientPacket(int shieldAmount) {
        this.shieldAmount = shieldAmount;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static ShieldSyncToClientPacket decode(FriendlyByteBuf buf) {
        return new ShieldSyncToClientPacket(buf.readInt());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(ShieldSyncToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.shieldAmount);
    }

    public static void handle(ShieldSyncToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getShield().set(msg.shieldAmount); // 更新客户端Coin数据
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
