package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill66_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.option.OrbitParticleDarkness;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill66 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(66,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(66,player);
            //SP消耗设置
            ModSkills.consumeSP(66,player);
            //吟唱设置
            ModSkills.setCharmingTime(66,2,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(50 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 15.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);
        Vec3 center = player.position().add(0.0,0.3,0.0);
        int max_count = 500;
        for (int i = 0; i < max_count; i++) {
            float step = (float) (2 * Math.PI * i / 47);
            level.sendParticles(new OrbitParticleDarkness(center,1.8,400),center.x+r*i/max_count* Mth.cos((float) (step)), center.y, center.z+r*i/max_count*Mth.sin((float) (step)),1, 0, 0, 0, 0);
        }

        Vec3 dir3D  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 p3d = player.position().add(0.0,0.3,0.0);
        Vec3 motion = new Vec3(0,0,0);
        Skill66_Entity e = Skill66_Entity.spawn(level, player, p3d, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
