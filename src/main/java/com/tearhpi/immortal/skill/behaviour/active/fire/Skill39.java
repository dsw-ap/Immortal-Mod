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
import java.util.Random;

import static com.tearhpi.immortal.util.statics.ExplosionParticle.horizonParticleExpServerLevel;
import static com.tearhpi.immortal.util.statics.ExplosionParticle.tiltParticleExpServerLevel;

public class Skill39 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(39,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(39,player);
            //SP消耗设置
            ModSkills.consumeSP(39,player);
            //吟唱设置
            ModSkills.setCharmingTime(39,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(39 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 4.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        //粒子环
        Random ran = new Random();
        Random rand = new Random(ran.nextLong(1,10000000));
        Vec3 pos = player.position().add(0,0.5,0);
        horizonParticleExpServerLevel(level, ModParticles.EARTH.get(), 0.5, 80, pos);
        tiltParticleExpServerLevel(level, ModParticles.EARTH.get(),0.5,50,pos,new Vec3(rand.nextFloat(-1,1),rand.nextFloat(-1,1),rand.nextFloat(-1,1)));
        tiltParticleExpServerLevel(level, ModParticles.EARTH.get(),0.5,50,pos,new Vec3(rand.nextFloat(-1,1),rand.nextFloat(-1,1),rand.nextFloat(-1,1)));
        tiltParticleExpServerLevel(level, ModParticles.EARTH.get(),0.5,50,pos,new Vec3(rand.nextFloat(-1,1),rand.nextFloat(-1,1),rand.nextFloat(-1,1)));
        for (int i = 0; i < 75; i++) {
            int rot = rand.nextInt(1,360);
            double length = rand.nextDouble(1,5);
            level.sendParticles(ParticleTypes.SMOKE,pos.x+length*Mth.cos((float) Math.toRadians(rot)),pos.y,pos.z+length*Mth.sin((float) Math.toRadians(rot)),0,0,0.2,0,rand.nextDouble(0.2,0.8));
        }
        //伤害
        float finalR = r;
        List<_ImmortalMob> mobs = player.level().getEntitiesOfClass(_ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR);
        for (_ImmortalMob mob : mobs) {
            if (player != null){
                ModDamageSources modDamageSources = new ModDamageSources(level.registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 2.0F));
            }
        }
    }
}
