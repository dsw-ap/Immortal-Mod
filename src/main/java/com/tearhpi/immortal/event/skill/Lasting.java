package com.tearhpi.immortal.event.skill;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.*;
import com.tearhpi.immortal.entity.custom.skills.Skill23_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill44_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncToClientPacket;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.skill.behaviour.active.fire.*;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Lasting {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //持续技结束段在此
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        //服务端操作
        Player player = event.player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //持续技
        //skill7
        int skill7Lasting = iImmortalPlayer.getSkillInUse().skill7Lasting;
        if (skill7Lasting >= 0) {
            iImmortalPlayer.getSkillInUse().skill7Lasting -= 1;
            if (skill7Lasting == 0) {
                //刷新相关实体life
                AABB wholeLevel = new AABB(-3.0E7, -4096, -3.0E7, 3.0E7, 4096, 3.0E7);
                List<Skill7_Entity> s = player.level().getEntitiesOfClass(Skill7_Entity.class, wholeLevel, e -> {
                    Player owner = e.getOwner(); // 你实体里已有的方法
                    return owner != null && owner.getUUID().equals(player.getUUID()) && e.isAlive();
                });
                for(Skill7_Entity e : s) {
                    e.life = 159;
                    e.setLifetime(159);
                }
                //CD刷新
                ModSkills.setCDbyNumAndPlayer(7,player);
            }
        }
        int skill23Lasting = iImmortalPlayer.getSkillInUse().skill23Lasting;
        if (skill23Lasting >= 0) {
            iImmortalPlayer.getSkillInUse().skill23Lasting -= 1;
            if (skill23Lasting == 0) {
                //刷新相关实体life
                final UUID puid = player.getUUID();
                AABB wholeLevel = new AABB(-3.0E7, -4096, -3.0E7, 3.0E7, 4096, 3.0E7);
                List<_ImmortalMob> s = player.level().getEntitiesOfClass(_ImmortalMob.class, wholeLevel, e -> {
                    UUID uuid = e.getTimeUuidPair().getUuid(); // 你实体里已有的方法
                    return uuid != null && uuid.equals(puid) && e.isAlive();
                });
                for(_ImmortalMob e : s) {
                    e.resetTimeUuidPair();
                    double r_ = 5;
                    List<_ImmortalMob> mobs = e.level().getEntitiesOfClass(_ImmortalMob.class, e.getBoundingBox().inflate(r_), mob -> mob.isAlive() && mob.distanceToSqr(e) <= r_ * r_);
                    for (_ImmortalMob mob : mobs) {
                        ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
                        mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 4f));
                        mob.addWaterAmount(20);
                        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) event.player.level(), ModParticles.WATER.get(),0.1,18,e.position());
                    }
                }
                List<Skill23_Entity> s_ = player.level().getEntitiesOfClass(Skill23_Entity.class, wholeLevel, e -> {
                    UUID uuid = e.getOwner().getUUID(); // 你实体里已有的方法
                    return uuid != null && uuid.equals(puid) && e.isAlive();
                });
                for(Skill23_Entity e : s_) {
                    e.discard();
                }
                //CD刷新
                ModSkills.setCDbyNumAndPlayer(23,player);
            }
        }
        //skill45
        int skill45Lasting = iImmortalPlayer.getSkillInUse().skill45Lasting;
        if (skill45Lasting >= 0) {
            iImmortalPlayer.getSkillInUse().skill45Lasting -= 1;
            float r = 5.0f;
            r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
            float finalR = r;
            List<Player> mobs = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR && mob != player);
            for (Player player_ : mobs) {
                ImposeEffect.ImposeEffectWithoutAmp(player_,_ModEffects.SKILL45_EFFECT_OTHERS.get(), 30);
            }
            ImposeEffect.ImposeLayer(player, MobEffects.MOVEMENT_SLOWDOWN,30,2);
            if (skill45Lasting == 0) {
                //移除相关效果
                MobEffectInstance mobEffectInstance = player.getEffect(_ModEffects.SKILL45_EFFECT_SELF.get());
                if (mobEffectInstance != null) {
                    player.removeEffect(_ModEffects.SKILL45_EFFECT_SELF.get());
                    player.setAbsorptionAmount(Math.max(0,player.getAbsorptionAmount()-player.getMaxHealth()/2));
                }
                MobEffectInstance mobEffectInstance_ = player.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
                if (mobEffectInstance_ != null && mobEffectInstance_.getAmplifier() <= 2) {
                    player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    player.setAbsorptionAmount(Math.max(0,player.getAbsorptionAmount()-player.getMaxHealth()/2));
                }
                float r_ = 10.0f;
                r_ *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
                float finalR_ = r_;
                List<Player> mobs_ = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(r_), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR_ * finalR_ && mob != player && !mob.hasEffect(_ModEffects.SKILL45_EFFECT_SELF.get()));
                for (Player player_ : mobs_) {
                    MobEffectInstance mobEffectInstance__ = player_.getEffect(_ModEffects.SKILL45_EFFECT_OTHERS.get());
                    if (mobEffectInstance__ != null) {
                        player_.removeEffect(_ModEffects.SKILL45_EFFECT_OTHERS.get());
                    }
                }
                //CD刷新
                ModSkills.setCDbyNumAndPlayer(45,player);
            }
        }
        //蓄力技
        //skill9
        int skill9Lasting = iImmortalPlayer.getSkillInUse().skill9Lasting;
        if (skill9Lasting >= 0) {
            int val = iImmortalPlayer.getSkillInUse().skill9Lasting;
            //检测玩家是否按下相关按键
            //按键搜寻
            boolean down = player.getPersistentData().getBoolean("skillDown");
            //效果
            if (down) {
                if (val % 10 == 0) {
                    Skill9.Do((ServerPlayer) player);
                }
            } else {
                iImmortalPlayer.getSkillInUse().skill9Lasting = 0;
            }
            iImmortalPlayer.getSkillInUse().skill9Lasting -= 1;
        }
        //skill12
        int skill12Lasting = iImmortalPlayer.getSkillInUse().skill12Lasting;
        if (skill12Lasting >= 0) {
            int val = iImmortalPlayer.getSkillInUse().skill12Lasting;
            //检测玩家是否按下相关按键
            //按键搜寻
            boolean down = player.getPersistentData().getBoolean("skillDown");
            //效果
            if (down && player.getHealth()/player.getMaxHealth() > 0.15) {
                if (val % 20 == 0) {
                    Skill12.Do((ServerPlayer) player);
                }
            } else {
                iImmortalPlayer.getSkillInUse().skill12Lasting = 0;
            }
            iImmortalPlayer.getSkillInUse().skill12Lasting -= 1;
        }
        //skill21
        int skill21Lasting = iImmortalPlayer.getSkillInUse().skill21Lasting;
        if (skill21Lasting >= 0) {
            int val = iImmortalPlayer.getSkillInUse().skill21Lasting;
            //检测玩家是否按下相关按键
            //按键搜寻
            boolean down = player.getPersistentData().getBoolean("skillDown");
            //效果
            if (down) {
                if (val % 12 == 0) {
                    Skill21.Do((ServerPlayer) player);
                }
            } else {
                iImmortalPlayer.getSkillInUse().skill21Lasting = 0;
            }
            iImmortalPlayer.getSkillInUse().skill21Lasting -= 1;
        }
        //skill44
        int skill44Lasting = iImmortalPlayer.getSkillInUse().skill44Lasting;
        if (skill44Lasting >= 0) {
            int val = iImmortalPlayer.getSkillInUse().skill44Lasting;
            //检测玩家是否按下相关按键
            //按键搜寻
            boolean down = player.getPersistentData().getBoolean("skillDown");
            //效果
            if (down) {

            } else {
                List<Skill44_Entity> mobs = player.level().getEntitiesOfClass(Skill44_Entity.class, player.getBoundingBox().inflate(3000000), mob -> mob.isAlive());
                for (Skill44_Entity mob : mobs) {
                    if (player.getUUID().equals(mob.getOwner().getUUID())) {
                        mob.setLife(200);
                    }
                }
                iImmortalPlayer.getSkillInUse().skill44Lasting = 0;
            }
            iImmortalPlayer.getSkillInUse().skill44Lasting -= 1;
        }
        //skill68
        int skill68Lasting = iImmortalPlayer.getSkillInUse().skill68Lasting;
        if (skill68Lasting >= 0) {
            int val = iImmortalPlayer.getSkillInUse().skill68Lasting;
            //检测玩家是否按下相关按键
            //按键搜寻
            boolean down = player.getPersistentData().getBoolean("skillDown");
            //效果
            if (down) {
                if (iImmortalPlayer.getSkillInUse().skill68Lasting == 30) {
                    Skill68.Do((ServerPlayer) player,2.0f);
                }
                if (iImmortalPlayer.getSkillInUse().skill68Lasting == 17) {
                    Skill68.Do((ServerPlayer) player,4.0f);
                }
                if (iImmortalPlayer.getSkillInUse().skill68Lasting == 4) {
                    Skill68.Do((ServerPlayer) player,6.0f);
                }
            } else {
                iImmortalPlayer.getSkillInUse().skill68Lasting = 0;
            }
            iImmortalPlayer.getSkillInUse().skill68Lasting -= 1;
        }
        CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new SkillInUseSyncToClientPacket(confirmed));
    }
}