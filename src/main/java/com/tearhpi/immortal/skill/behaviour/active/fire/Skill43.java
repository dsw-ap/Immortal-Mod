package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.ShieldSyncPacket;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static com.tearhpi.immortal.util.statics.ExplosionParticle.horizonParticleExpServerLevel;

public class Skill43 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(43,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(43,player);
            //SP消耗设置
            ModSkills.consumeSP(43,player);
            //吟唱设置
            ModSkills.setCharmingTime(43,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(43 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 10.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 pos = player.position().add(0,0.5,0);
        horizonParticleExpServerLevel(level, ModParticles.EARTH.get(), 0.5, 80, pos);
        horizonParticleExpServerLevel(level, ModParticles.EARTH.get(), 0.45, 80, pos);

        float finalR_ = r;
        List<Player> mobs = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(finalR_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR_ * finalR_);
        for (Player mob : mobs) {
            int shieldLayers = ((IImmortalPlayer) player).getShield().get();
            shieldLayers += 1;
            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new ShieldSyncPacket(shieldLayers));

            Vec3 pos_ = mob.position();
            level.sendParticles(ModParticles.EARTH.get(),pos_.x,pos_.y,pos_.z,30,1,2,1,0.02);
        }
    }
}
