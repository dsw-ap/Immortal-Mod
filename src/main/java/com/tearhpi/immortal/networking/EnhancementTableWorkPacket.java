package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class EnhancementTableWorkPacket {
    private final BlockPos pos; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public EnhancementTableWorkPacket(BlockPos pos) {
        this.pos = pos;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static EnhancementTableWorkPacket decode(FriendlyByteBuf buf) {
        //this.pos = buf.readBlockPos(); // 按发送时写入的顺序读出 BlockPos
        return new EnhancementTableWorkPacket(buf.readBlockPos());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(EnhancementTableWorkPacket msg ,FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    /*
    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            // 获取服务端玩家
            ServerPlayer player = context.getSender();
            if (player == null) return;

            ServerLevel level = player.serverLevel();
            if (!level.hasChunkAt(pos)) return;

            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof EnhancementTableBlockEntity table) {
                //玩家金币消耗
                int[] ConsumeMaterial = table.EnhancementMaterialCalculate();
                ModNetworking.CHANNEL.send(new CoinSyncPacket(-ConsumeMaterial[3]), PacketDistributor.SERVER.noArg());
                //开始工作
                table.Start_Crafting();
            }
        });
        context.setPacketHandled(true); // 标记包已处理
    }
    */
    public static void handle(EnhancementTableWorkPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 仅处理 C->S 的包
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player == null) return;

                // 1.20.1：拿到 ServerLevel
                ServerLevel level = (ServerLevel) player.level();
                BlockPos pos = msg.pos; // ← 你的消息类中应包含该字段

                if (!level.hasChunkAt(pos)) return;

                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof EnhancementTableBlockEntity table) {
                    // 计算材料与金币消耗
                    int[] consume = table.EnhancementMaterialCalculate();
                    if (consume == null) return;

                    int coinCost = consume[3];

                    // ★ 关键修正：已经在服务端，不要再发「到服务器」的包扣钱
                    // 直接改服务端数据，然后回发客户端同步
                    if (player instanceof IImmortalPlayer immortal) {
                        immortal.getCoin().add(-coinCost);                 // 服务端扣款
                        int confirmed = immortal.getCoin().get();          // 若有校正，拿校正后值

                        // 回发客户端同步包（示例）
                        ModNetworking.CHANNEL.send(
                                PacketDistributor.PLAYER.with(() -> player),
                                new CoinSyncToClientPacket(confirmed)
                        );
                    }

                    // 启动工作
                    table.Start_Crafting();
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
