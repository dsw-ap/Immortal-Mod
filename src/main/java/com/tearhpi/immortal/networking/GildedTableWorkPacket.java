package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class GildedTableWorkPacket {
    private final BlockPos pos; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public GildedTableWorkPacket(BlockPos pos) {
        this.pos = pos;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public GildedTableWorkPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos(); // 按发送时写入的顺序读出 BlockPos
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(GildedTableWorkPacket msg,FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }
    public static GildedTableWorkPacket decode(FriendlyByteBuf buf) {return new GildedTableWorkPacket(buf.readBlockPos());}

    public static void handle(GildedTableWorkPacket msg, java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 确认方向是 S -> C
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel level = mc.level;
                if (level == null) return;

                BlockPos pos = msg.pos; // 假设你在消息类中存了这个位置
                if (!level.hasChunkAt(pos)) return;

                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof GildedTableBlockEntity table) {
                    // 客户端表现：启动动画/进度条等，仅视觉用
                    table.Start_Crafting();

                    // 标脏并请求渲染刷新
                    table.setChanged();
                    BlockState state = table.getBlockState();
                    level.sendBlockUpdated(pos, state, state, 7);
                }
            });
        }

        ctx.setPacketHandled(true);
    }
}
