package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class ForgingTableResetPacket {
    private final BlockPos pos; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public ForgingTableResetPacket(BlockPos pos) {
        this.pos = pos;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static ForgingTableResetPacket decode(FriendlyByteBuf buf) {
        //this.pos = buf.readBlockPos(); // 按发送时写入的顺序读出 BlockPos
        return new ForgingTableResetPacket(buf.readBlockPos());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(ForgingTableResetPacket msg,FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static void handle(ForgingTableResetPacket msg, java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player == null) return;

                ServerLevel level = (ServerLevel) player.level();
                BlockPos pos = msg.pos;

                if (!level.hasChunkAt(pos)) return;

                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof ForgingTableBlockEntity table) {
                    table.reset_num();

                    // 可选：标脏+推送方块更新，便于客户端界面/渲染即时刷新
                    table.setChanged();
                    BlockState state = table.getBlockState();
                    level.sendBlockUpdated(pos, state, state, 7);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
