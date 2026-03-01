package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.task.ModMainTask;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.function.Supplier;

public class MainTaskSyncToMaxOnePacket {

    public MainTaskSyncToMaxOnePacket() {
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public void encode(FriendlyByteBuf buf) {

    }

    public static MainTaskSyncToMaxOnePacket decode(FriendlyByteBuf buf) {
        return new MainTaskSyncToMaxOnePacket();
    }

    public static void handle(MainTaskSyncToMaxOnePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在服务端主线程执行
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    int max_level = 0;
                    List<ServerPlayer> l = player.level().getServer().getPlayerList().getPlayers();
                    for (ServerPlayer splayer: l){
                        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) splayer;
                        if (iImmortalPlayer.getMainTask().get() >= max_level){
                            max_level = iImmortalPlayer.getMainTask().get();
                        }
                    }
                    for (ServerPlayer splayer: l){
                        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) splayer;
                        iImmortalPlayer.getMainTask().set(max_level);
                        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> splayer), new MainTaskSyncToClientPacket(iImmortalPlayer.getMainTask().get()));
                        splayer.sendSystemMessage(Component.translatable("util.tab2.line1_Sync_output",player.getName(), ModMainTask.getStaticMainTask(max_level).Name));
                    }
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
