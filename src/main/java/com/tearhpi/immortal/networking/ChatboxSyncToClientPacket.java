package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChatboxSyncToClientPacket {
    private final int Chatbox_id;
    private final int Chatbox_num;
    private final int Chatbox_countdown;

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public ChatboxSyncToClientPacket(int Chatbox_id, int Chatbox_num, int Chatbox_countdown) {
        this.Chatbox_id = Chatbox_id;
        this.Chatbox_num = Chatbox_num;
        this.Chatbox_countdown = Chatbox_countdown;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static ChatboxSyncToClientPacket decode(FriendlyByteBuf buf) {
        return new ChatboxSyncToClientPacket(buf.readInt(),buf.readInt(),buf.readInt());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(ChatboxSyncToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.Chatbox_id);
        buf.writeInt(msg.Chatbox_num);
        buf.writeInt(msg.Chatbox_countdown);
    }

    public static void handle(ChatboxSyncToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getChatbox().set(msg.Chatbox_id, msg.Chatbox_num, msg.Chatbox_countdown);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
