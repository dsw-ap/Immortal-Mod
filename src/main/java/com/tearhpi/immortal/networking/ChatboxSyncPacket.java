package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ChatboxSyncPacket {
    private final int Chatbox_id;
    private final int Chatbox_num;
    private final int Chatbox_countdown;

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public ChatboxSyncPacket(int Chatbox_id, int Chatbox_num, int Chatbox_countdown) {
        this.Chatbox_id = Chatbox_id;
        this.Chatbox_num = Chatbox_num;
        this.Chatbox_countdown = Chatbox_countdown;
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.Chatbox_id);
        buf.writeInt(this.Chatbox_num);
        buf.writeInt(this.Chatbox_countdown);
    }

    public static ChatboxSyncPacket decode(FriendlyByteBuf buf) {
        return new ChatboxSyncPacket(buf.readInt(),buf.readInt(),buf.readInt());
    }

    public static void handle(ChatboxSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getChatbox().set(msg.Chatbox_id, msg.Chatbox_num, msg.Chatbox_countdown);
                    int confirmed_1 = immortal.getChatbox().getID();
                    int confirmed_2 = immortal.getChatbox().getNum();
                    int confirmed_3 = immortal.getChatbox().getCountdown();
                    // 回发客户端同步包
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ChatboxSyncToClientPacket(confirmed_1,confirmed_2,confirmed_3));
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
