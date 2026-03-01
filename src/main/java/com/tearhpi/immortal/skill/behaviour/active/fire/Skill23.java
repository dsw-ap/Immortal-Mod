package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill23_Entity;
import com.tearhpi.immortal.entity.custom.Utils.TimeUuidPair;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SkillInUseSyncPacket;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

import static com.tearhpi.immortal.skill.ModSkills.*;

public class Skill23 {
    private static final Logger log = LoggerFactory.getLogger(Skill23.class);

    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        if (iImmortalPlayer.getSkillInUse().skill23Lasting > 1) {
            //若技能期间再次点击该技能按钮
            //触发终结段
            iImmortalPlayer.getSkillInUse().skill23Lasting = 1;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            return;
        }
        if (ModSkills.getCDbyNumAndPlayer(23, player) == 0) {
            setCDbyNumValueAndPlayer(23,10000,player);
            iImmortalPlayer.getSkillInUse().skill23Lasting = 220;
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            //SP消耗设置
            ModSkills.consumeSP(23, player);
            //吟唱设置
            ModSkills.setCharmingTime(23, 2, player);
        } else {
            player.sendSystemMessage(Component.literal("In CD," + ModSkills.getCDbyNumAndPlayer(23, player)));
        }
    }

    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float d = 7.0f;
        d *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        player.swing(InteractionHand.MAIN_HAND, true);
        ServerLevel level = player.serverLevel();
        //吟唱刷新
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getSkillInUse().skill23Lasting = 220;
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));

        float finalR = d;
        List<_ImmortalMob> mobs = level.getEntitiesOfClass(
                _ImmortalMob.class, player.getBoundingBox().inflate(d), mob -> mob.isAlive() && mob.distanceToSqr(player) <= finalR * finalR
        );
        List<_ImmortalMob> weakestMobs = mobs.stream()
                .sorted(Comparator.comparingDouble(Mob::getHealth)).limit(4).toList();
        //ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
        for (_ImmortalMob mob : weakestMobs) {
            mob.setTimeUuidPair(new TimeUuidPair(60, player.getUUID()));
        }
    }

    public static void Summon(Entity entity, Level level, ServerPlayer player, float Radius) {
        //以该Entity为中心生成相关Entity8
        final double speedBlocksPerSec = 8;
        final double speedPerTick = speedBlocksPerSec / 20.0;

        // 中心坐标
        double cx = entity.getX();
        double cy = entity.getY() + entity.getBbHeight() * 0.5;
        double cz = entity.getZ();

        // 八个方向（平面上）
        int[] anglesDeg = {0, 45, 90, 135, 180, 225, 270, 315};

        for (int deg : anglesDeg) {
            double rad = Math.toRadians(deg);

            // 水平面方向向量
            double dx = Math.cos(rad);
            double dz = Math.sin(rad);

            // 归一化后乘以速度
            double vx = dx * speedPerTick;
            double vz = dz * speedPerTick;

            // 实例化实体
            Skill23_Entity e = Skill23_Entity.spawn((ServerLevel) level, player, new Vec3(cx, cy, cz), new Vec3(vx, 0, vz), Radius, (_ImmortalMob) entity);
            BoundEntityManager_SkillAttack.bind(player, e);

            level.addFreshEntity(e);
        }
    }
}