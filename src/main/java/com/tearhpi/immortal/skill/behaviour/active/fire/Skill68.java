package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.networking.ManaSyncToClientPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncPacket;
import com.tearhpi.immortal.networking.skill.C2SSkillDownState;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

public class Skill68 {
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        //判断cd
        if (iImmortalPlayer.getSkillInUse().skill68Lasting > 1) {

        } else if (ModSkills.getCDbyNumAndPlayer(68,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(68,player);
            //SP消耗设置
            ModSkills.consumeSP(68,player);
            //吟唱设置(技能号/吟唱时间/玩家)
            //ModSkills.setCharmingTime(9,20,player);
            iImmortalPlayer.getSkillInUse().skill68Lasting = 40;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            ModNetworking.CHANNEL.sendToServer(new C2SSkillDownState(true));
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(68,player)));
        }
    }
    public static void Do(ServerPlayer player,float val) {
        player.playNotifySound(SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 5.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        ExplosionParticle.horizonParticleExpServerLevel(level, ModParticles.DARKNESS.get(),0.5,72,player.position().add(0.0,0.3,0.0));
        ExplosionParticle.horizonParticleExpServerLevel(level, ModParticles.DARKNESS.get(),0.4,72,player.position().add(0.0,0.3,0.0));

        float finalR = r;
        List<_ImmortalMob> mobs = player.level().getEntitiesOfClass(_ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR);
        for (_ImmortalMob mob : mobs) {
            if (player != null){
                ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), val));
            }
        }
        player.heal((float) (player.getMaxHealth()*0.01*mobs.size()));
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getManaPoint().add((int) (player.getMaxHealth()*0.02*mobs.size()),(int) player.getAttributeValue(ModAttributes.IMMORTAL_MAGIC_POINT_MAX.get()));
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new ManaSyncToClientPacket(iImmortalPlayer.getManaPoint().get()));
    }
}
