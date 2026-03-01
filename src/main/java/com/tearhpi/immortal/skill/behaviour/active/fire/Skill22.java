package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.skills.Skill22_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill22 {
    public static void Main(Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //判断cd
        //判断cd
        /*
        if (iImmortalPlayer.getSkillInUse().skill22Lasting > 1) {
            //若技能期间再次点击该技能按钮
            //触发终结段
            iImmortalPlayer.getSkillInUse().skill22Lasting = 1;
            ModNetworking.CHANNEL.send(PacketDistributor.SERVER.noArg(),new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
            return;
        }

         */
        if (ModSkills.getCDbyNumAndPlayer(22,player) == 0) {
            //iImmortalPlayer.getSkillInUse().skill22Lasting = 600;
            //重设CD
            ModSkills.setCDbyNumAndPlayer(22,player);
            //SP消耗设置
            ModSkills.consumeSP(22,player);
            //吟唱设置
            ModSkills.setCharmingTime(22,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(22 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 20.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);

        Vec3 p3d = player.position();
        Vec3 motion = new Vec3(0.0f,0.0f,0.0f);
        Skill22_Entity e = Skill22_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
