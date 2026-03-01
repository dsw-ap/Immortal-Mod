package com.tearhpi.immortal.networking;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillInfoSyncToClientPacket {
    private final CompoundTag SkillInfo;

    // 构造函数：客户端发送时使用，指定目标方块实体位置
    public SkillInfoSyncToClientPacket(CompoundTag skillInfo) {
        this.SkillInfo = skillInfo;
    }

    // 解码构造函数：从收到的数据包中读出方块位置（供SimpleChannel自动调用）
    public static SkillInfoSyncToClientPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) tag = new CompoundTag();
        return new SkillInfoSyncToClientPacket(tag);
    }

    // 将数据写入缓冲区（客户端发送时调用）
    public static void encode(SkillInfoSyncToClientPacket msg, FriendlyByteBuf buf) {
        //System.out.println("321"+msg.SkillInfo.getCompound("Skill_Point_Related").getInt("Skill_Point"));
        buf.writeNbt(msg.SkillInfo);
    }

    // ---------- 处理器：S -> C ----------
    public static void handle(SkillInfoSyncToClientPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player instanceof IImmortalPlayer immortal) {
                    //System.out.println("6789"+msg.SkillInfo.getCompound("Skill_Point_Related").getInt("Skill_Point"));
                    immortal.getSkill().read(msg.SkillInfo);
                    //System.out.println("/"+immortal.getSkill().Skill_Point);
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}
