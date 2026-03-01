package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.tearhpi.immortal.util.statics.ExplosionParticle.horizonParticleExpServerLevel;

public class Skill41 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(41,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(41,player);
            //SP消耗设置
            ModSkills.consumeSP(41,player);
            //吟唱设置
            ModSkills.setCharmingTime(41,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(41 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 5.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 pos = player.position().add(0,0.5,0);
        horizonParticleExpServerLevel(level, ModParticles.EARTH.get(), 0.5, 80, pos);
        horizonParticleExpServerLevel(level, ParticleTypes.END_ROD, 0.45, 80, pos);
        horizonParticleExpServerLevel(level, ModParticles.EARTH.get(), 0.4, 80, pos);
        horizonParticleExpServerLevel(level, ParticleTypes.END_ROD, 0.4, 80, pos);
        for (int i = 0;i < 40;i++) {
            double step = Math.PI * 2 * i / 40;
            double step_ = Math.PI * 2 * (i+0.5) / 40;
            level.sendParticles(ModParticles.EARTH.get(),pos.x+r/2* Mth.cos((float) step),pos.y+1,pos.z+r/2* Mth.sin((float) step),1,0,0.2,0,0.2);
            level.sendParticles(ParticleTypes.END_ROD,pos.x+r/2* Mth.cos((float) step_),pos.y+1,pos.z+r/2* Mth.sin((float) step_),1,0,0.2,0,0.2);
        }
        float finalR_ = r;
        List<Player> mobs = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(finalR_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR_ * finalR_);
        for (Player mob : mobs) {
            mob.heal(mob.getMaxHealth()*0.3f);
            Vec3 pos_ = mob.position();
            level.sendParticles(ParticleTypes.HEART,pos_.x,pos_.y+1,pos_.z,10,2,4,2,0.02);
       }

        float finalR = r*8/5;
        List<_ImmortalMob> mobs_ = player.level().getEntitiesOfClass(_ImmortalMob.class, player.getBoundingBox().inflate(finalR), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR);
        for (_ImmortalMob mob : mobs_) {
            if (player != null){
                ModDamageSources modDamageSources = new ModDamageSources(level.registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3.5F));
                Vec3 pos_ = mob.position();
                level.sendParticles(ParticleTypes.SMOKE,pos_.x,pos_.y+1,pos_.z,10,2,4,2,0.02);
            }
        }
    }
}
