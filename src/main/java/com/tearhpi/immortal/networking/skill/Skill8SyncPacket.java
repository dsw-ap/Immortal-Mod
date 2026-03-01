package com.tearhpi.immortal.networking.skill;

import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.entity.custom._ImmortalNormalMob;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
// import your.mod.api.IImmortalPlayer; // 不需要就删

public class Skill8SyncPacket {
    private final int entityId;
    private final int value;

    // 客户端发送用
    public Skill8SyncPacket(int entityId, int value) {
        this.entityId = entityId;
        this.value = value;
    }

    // 1.20.1: 静态编解码
    public static void encode(Skill8SyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.value);
    }

    public static Skill8SyncPacket decode(FriendlyByteBuf buf) {
        return new Skill8SyncPacket(buf.readInt(), buf.readInt());
    }

    // 处理：客户端 -> 服务端，在主线程生成实体
    public static void handle(Skill8SyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.enqueueWork(() -> {
                //System.out.println("1");
                ServerPlayer sender = ctx.getSender(); // C2S 一定要从这里拿玩家
                if (sender == null) return;
                ServerLevel level = sender.serverLevel();
                Entity e = level.getEntity(msg.entityId);
                //System.out.println(msg.entityId);
                //System.out.println(e == null);
                //System.out.println(e instanceof _ImmortalMob);
                //System.out.println(e instanceof _ImmortalNormalMob);
                if (e instanceof LivingEntity le) {
                    //System.out.println("2");
                    try {
                        if (le instanceof _ImmortalMob mob) {
                            //System.out.println("3");
                            mob.setskill8PreLasting(msg.value);
                            level.sendParticles(ParticleTypes.LAVA,mob.position().x,mob.position().y,mob.position().z,20,0,0,0,0.3);
                            sender.playNotifySound(ModSounds.SKILL_6_SUMMON.get(), SoundSource.PLAYERS, 1.0f,1.0f);
                        }
                    } catch (Exception ex) {

                    }
                }
            });
        }
        ctx.setPacketHandled(true);
    }
}