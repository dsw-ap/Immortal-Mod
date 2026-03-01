package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class SettingPacket {
    private final CompoundTag Setting; // 方块实体的位置

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public SettingPacket(CompoundTag setting) {
        this.Setting = setting;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static SettingPacket decode(FriendlyByteBuf buf) {
        return new SettingPacket(buf.readNbt());
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(SettingPacket msg , FriendlyByteBuf buf) {
        buf.writeNbt(msg.Setting);
    }

    public static void handle(SettingPacket msg,
                              java.util.function.Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        // 只在客户端接收侧处理
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if (player != null) {
                    IImmortalPlayer immortal = (IImmortalPlayer) player;
                    immortal.getSetting().read(msg.Setting);
                    //System.out.println(immortal.getSetting().Setting1+","+immortal.getSetting().Setting2+","+immortal.getSetting().Setting3);
                    CompoundTag confirmed = immortal.getSetting().getSkillCompoundTag(immortal.getSetting().getNumInfo());
                    ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(ctx::getSender), new SettingToClientPacket(confirmed));
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
