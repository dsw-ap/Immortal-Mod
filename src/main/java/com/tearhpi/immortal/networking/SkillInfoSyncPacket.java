package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class SkillInfoSyncPacket {
    private final CompoundTag SkillInfo; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public SkillInfoSyncPacket(CompoundTag skillInfo) {
        //System.out.println("123"+skillInfo.getCompound("Skill_Point_Related").getInt("Skill_Point"));
        this.SkillInfo = skillInfo;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static SkillInfoSyncPacket decode(FriendlyByteBuf buf) {
        return new SkillInfoSyncPacket(buf.readNbt());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(SkillInfoSyncPacket msg ,FriendlyByteBuf buf) {
        //System.out.println(msg.SkillInfo.getCompound("Skill_Point_Related").getInt("Skill_Point"));
        buf.writeNbt(msg.SkillInfo);
    }

    public static void handle(SkillInfoSyncPacket msg,
                              java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        // 只在客户端接收侧处理
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            //System.out.println("4");
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getSkill().read(msg.SkillInfo);
                    CompoundTag confirmed = immortal.getSkill().getSkillCompoundTag(immortal.getSkill().getNumInfo());
                    //System.out.println("5"+confirmed.getCompound("Skill_Point_Related").getInt("Skill_Point"));
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(ctx::getSender), new SkillInfoSyncToClientPacket(confirmed));
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
//技能数据同步手段
//ModNetworking.CHANNEL.send(new SkillInfoSyncPacket(iImmortalPlayer.getSkill().getSkillCompoundTag(iImmortalPlayer.getSkill().getNumInfo())), PacketDistributor.SERVER.noArg());
