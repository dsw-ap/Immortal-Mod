package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MainTaskSyncToClientPacket {
    private final int mainTask; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public MainTaskSyncToClientPacket(int mainTask) {
        this.mainTask = mainTask;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static MainTaskSyncToClientPacket decode(FriendlyByteBuf buf) {
        return new MainTaskSyncToClientPacket(buf.readInt());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(MainTaskSyncToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.mainTask);
    }

    public static void handle(MainTaskSyncToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getMainTask().set(msg.mainTask);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
