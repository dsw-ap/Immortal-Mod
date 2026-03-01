package com.tearhpi.immortal.util.statics;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class SoundDeliver {
    public static void playSoundNear(ServerLevel level, Vec3 pos, SoundEvent sound, float volume, float pitch) {
        level.playSound(
                null, // null = 广播给附近所有玩家
                pos.x, pos.y, pos.z,
                sound,
                SoundSource.PLAYERS, // 或 SoundSource.HOSTILE / NEUTRAL / AMBIENT 等
                volume,
                pitch
        );
    }
    public static void playSoundNear(ServerLevel level, Vec3 pos, SoundEvent sound) {
        playSoundNear(level, pos, sound, 1.0F, 1.0F);
    }
}
