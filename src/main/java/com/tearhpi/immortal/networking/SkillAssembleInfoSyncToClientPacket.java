package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillAssembleInfoSyncToClientPacket {
    private final CompoundTag skillInfo;

    public SkillAssembleInfoSyncToClientPacket(CompoundTag skillInfo) {
        this.skillInfo = skillInfo;
    }

    // ---------- 编码 / 解码（1.20.1 模板） ----------
    public static void encode(SkillAssembleInfoSyncToClientPacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.skillInfo);
    }

    public static SkillAssembleInfoSyncToClientPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) tag = new CompoundTag();
        return new SkillAssembleInfoSyncToClientPacket(tag);
    }

    // ---------- 处理器：S -> C ----------
    public static void handle(SkillAssembleInfoSyncToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    immortal.getSkillAssemble().read(msg.skillInfo);
                }
            });
        }

        ctx.setPacketHandled(true);
    }
}
