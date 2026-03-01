package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom.skills.Skill33_Entity;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Skill33 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(33,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(33,player);
            //SP消耗设置
            ModSkills.consumeSP(33,player);
            //吟唱设置
            ModSkills.setCharmingTime(33,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(33 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 12.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        level.sendParticles(ModParticles.AIR.get(),player.position().x,player.position().y+5,player.position().z,500,r,10,r,0.05);
        
        float finalR = r;
        List<_ImmortalMob> mobs = player.level().getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            if (player != null){
                ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob),7.0F));
                ImposeEffect.ApplyEffectLayer(mob, _ModEffects.ANNIHILATE_EFFECT.get(),200);
                ImposeEffect.ApplyEffectLayer(mob, _ModEffects.ANNIHILATE_EFFECT.get(),200);
            }
            Vec3 dir3D_mid  = Vec3.directionFromRotation(0, player.getYRot());
            Vec3 p3d = mob.position().add(new Vec3(0, 0.5, 0));
            Vec3 motion = new Vec3(0,0,0);
            Skill33_Entity e = Skill33_Entity.spawn(level, player, p3d, motion, r/4);
            BoundEntityManager_SkillAttack.bind(player, e);
            level.addFreshEntity(e);
        }
    }
}
