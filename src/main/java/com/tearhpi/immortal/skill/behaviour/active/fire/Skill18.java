package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.*;
import com.tearhpi.immortal.entity.custom.skills.Skill18_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill22_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncToClientPacket;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.Entity_Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

public class Skill18 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(18,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(18,player);
            //SP消耗设置
            ModSkills.consumeSP(18,player);
            //吟唱设置
            ModSkills.setCharmingTime(18,2000,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(18,player)));
        }
    }
    public static void Do_A(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (player == null) return;
        float r = 10.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(finalR), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            //造成伤害
            if (!Entity_Utils.isEntityTypeWithinDistance(mob, Skill22_Entity.class,r*2)) {
                int waterAmount = mob.getWaterAmount();
                ModDamageSources modDamageSources = new ModDamageSources(level.registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.1f*waterAmount));
                //生成领域
                Skill18_Entity e = Skill18_Entity.spawn(level, player, mob.position().add(new Vec3(0.0f,0.5f,0.0f)), new Vec3(0.0f,0.0f,0.0f), 0.1f*waterAmount, (int) Math.floor((double) waterAmount/10)*20);
                BoundEntityManager_SkillAttack.bind(player, e);
                level.addFreshEntity(e);
                mob.setWaterAmount(0);
            } else {
                int waterAmount = mob.getWaterAmount();
                ModDamageSources modDamageSources = new ModDamageSources(level.registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
                mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.15f*waterAmount));
                //生成领域
                Skill18_Entity e = Skill18_Entity.spawn(level, player, mob.position().add(new Vec3(0.0f,0.5f,0.0f)), new Vec3(0.0f,0.0f,0.0f), 0.15f*waterAmount, (int) Math.floor((double) waterAmount/10)*30);
                BoundEntityManager_SkillAttack.bind(player, e);
                level.addFreshEntity(e);
                mob.setWaterAmount(0);
            }

        }

        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getSkillInUse().IsInUseSkill = -1;
        iImmortalPlayer.getSkillInUse().IsInUseSkillNumber = 0;
        CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new SkillInUseSyncToClientPacket(confirmed));
    }
    public static void Do_W(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (player == null) return;
        float r = 10.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND, true);

        //按w:流淌;使其每秒流失4水气值
        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(finalR), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            //造成伤害
            mob.setskill18_w_Time(200);
        }
        List<Skill18_Entity> fields = level.getEntitiesOfClass(
                Skill18_Entity.class, player.getBoundingBox().inflate(finalR), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (Skill18_Entity field : fields) {
            field.setStatus(1);
        }

        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getSkillInUse().IsInUseSkill = -1;
        iImmortalPlayer.getSkillInUse().IsInUseSkillNumber = 0;
        CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new SkillInUseSyncToClientPacket(confirmed));
    }
    public static void Do_D(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (player == null) return;
        float r = 10.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND, true);

        //按d:凝结;
        float finalR = r;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(finalR), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (_ImmortalMob mob : mobs) {
            //造成伤害
            int consumeWaterAmount = mob.getWaterAmount() / 2;
            mob.setWaterAmount(consumeWaterAmount);
            ModDamageSources modDamageSources = new ModDamageSources(level.registryAccess());
            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
            if (!Entity_Utils.isEntityTypeWithinDistance(mob, Skill22_Entity.class,r*2)) {
                mob.hurt(damagesource, MainDamage.getDamage(player, damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), consumeWaterAmount * 4.5f));
            } else {
                mob.hurt(damagesource, MainDamage.getDamage(player, damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), consumeWaterAmount * 3.0f));
            }
        }
        List<Skill18_Entity> fields = level.getEntitiesOfClass(
                Skill18_Entity.class, player.getBoundingBox().inflate(finalR), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        for (Skill18_Entity field : fields) {
            if (!Entity_Utils.isEntityTypeWithinDistance(field, Skill22_Entity.class,r*2)) {
                field.setStatus(2);
            } else {
                field.setStatus(3);
            }

        }

        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getSkillInUse().IsInUseSkill = -1;
        iImmortalPlayer.getSkillInUse().IsInUseSkillNumber = 0;
        CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new SkillInUseSyncToClientPacket(confirmed));
    }
}
