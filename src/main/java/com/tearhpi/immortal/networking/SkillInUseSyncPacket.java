package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class SkillInUseSyncPacket {
    private final CompoundTag SkillInUse; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public SkillInUseSyncPacket(CompoundTag skillInUse) {
        this.SkillInUse = skillInUse;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static SkillInUseSyncPacket decode(FriendlyByteBuf buf) {
        return new SkillInUseSyncPacket(buf.readNbt());
    }
    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(SkillInUseSyncPacket msg ,FriendlyByteBuf buf) {
        buf.writeNbt(msg.SkillInUse);
    }
    public static void handle(SkillInUseSyncPacket msg,
                              java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        // 只在客户端接收侧处理
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getSkillInUse().read(msg.SkillInUse);
                    CompoundTag confirmed = immortal.getSkillInUse().getSkillCompoundTag(immortal.getSkillInUse().getNumInfo());
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(ctx::getSender), new SkillInUseSyncToClientPacket(confirmed));
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
//技能数据同步手段
//ModNetworking.CHANNEL.send(new SkillInfoSyncPacket(iImmortalPlayer.getSkill().getSkillCompoundTag(iImmortalPlayer.getSkill().getNumInfo())), PacketDistributor.SERVER.noArg());
