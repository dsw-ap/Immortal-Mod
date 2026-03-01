package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AchievementToClientPacket {
    private final CompoundTag Achievement;

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public AchievementToClientPacket(CompoundTag Achievement) {
        this.Achievement = Achievement;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static AchievementToClientPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) tag = new CompoundTag();
        return new AchievementToClientPacket(tag);
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(AchievementToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.Achievement);
    }

    // ---------- 处理器：S -> C ----------
    public static void handle(AchievementToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getAchievement().read(msg.Achievement);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
