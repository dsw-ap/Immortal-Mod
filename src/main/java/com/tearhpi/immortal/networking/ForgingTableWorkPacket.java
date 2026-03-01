package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class ForgingTableWorkPacket {
    private final BlockPos pos; // 方块实体的位置

    public ForgingTableWorkPacket(BlockPos pos) { this.pos = pos; }

    // 编解码
    public static void encode(ForgingTableWorkPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }
    public static ForgingTableWorkPacket decode(FriendlyByteBuf buf) {
        return new ForgingTableWorkPacket(buf.readBlockPos());
    }

    public static void handle(ForgingTableWorkPacket msg, java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 仅处理客户端发到服务端的包
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player == null) return;

                ServerLevel level = (ServerLevel) player.level();
                BlockPos pos = msg.pos;

                if (!level.hasChunkAt(pos)) return;

                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof ForgingTableBlockEntity table) {
                    // 开始工作
                    table.Start_Crafting();

                    // 可选：标脏并推送方块更新，确保客户端界面/渲染即时刷新
                    table.setChanged();
                    BlockState state = table.getBlockState();
                    level.sendBlockUpdated(pos, state, state, 7);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
