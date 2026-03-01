package com.tearhpi.immortal.networking.skill;

import com.tearhpi.immortal.networking.CoinSyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SSkillDownState {
    private final boolean down;

    public C2SSkillDownState(boolean down) { this.down = down; }
    //public C2SSkillDownState(FriendlyByteBuf buf) { this.down = buf.readBoolean(); }
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(down);
    }
    public static C2SSkillDownState decode(FriendlyByteBuf buf) {
        return new C2SSkillDownState(buf.readBoolean());
    }
    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            player.getPersistentData().putBoolean("skillDown", down);
        });
        ctx.setPacketHandled(true);
    }
}
