package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.capability.SkillAssemble;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class SkillAssembleInfoSyncPacket {
    private final CompoundTag SkillInfo; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public SkillAssembleInfoSyncPacket(CompoundTag skillInfo) {
        this.SkillInfo = skillInfo;
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(SkillAssembleInfoSyncPacket msg,FriendlyByteBuf buf) {
        buf.writeNbt( msg.SkillInfo);
    }
    public static SkillAssembleInfoSyncPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) tag = new CompoundTag();
        return new SkillAssembleInfoSyncPacket(tag);
    }

    public static void handle(SkillAssembleInfoSyncPacket msg,
                              java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 只在客户端接收侧处理
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getSkillAssemble().read(msg.SkillInfo);
                    CompoundTag confirmed = immortal.getSkillAssemble().getSkillCompoundTag(immortal.getSkillAssemble().getNumInfo());
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(ctx::getSender), new SkillAssembleInfoSyncToClientPacket(confirmed));
                }
            });
        }

        ctx.setPacketHandled(true);
    }
}
//技能数据同步手段
//ModNetworking.CHANNEL.send(new SkillInfoSyncPacket(iImmortalPlayer.getSkill().getSkillCompoundTag(iImmortalPlayer.getSkill().getNumInfo())), PacketDistributor.SERVER.noArg());
