package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SettingToClientPacket {
    private final CompoundTag Setting;

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public SettingToClientPacket(CompoundTag setting) {
        this.Setting = setting;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static SettingToClientPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) tag = new CompoundTag();
        return new SettingToClientPacket(tag);
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(SettingToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.Setting);
    }

    // ---------- 处理器：S -> C ----------
    public static void handle(SettingToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getSetting().read(msg.Setting);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
