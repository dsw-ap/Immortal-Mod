package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill11_Entity;
import com.tearhpi.immortal.entity.custom.skills.Skill11_Entity_;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity_;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.particle.option.OrbitParticleOptions;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import static com.tearhpi.immortal.util.statics.SkillTagRelated.*;

public class Skill11 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(11,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(11,player);
            //SP消耗设置
            ModSkills.consumeSP(11,player);
            //吟唱设置(技能号/吟唱时间/玩家)
            ModSkills.setCharmingTime(11,80,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(11,player)));
        }
    }
    public static void Prep(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 20.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        player.swing(InteractionHand.MAIN_HAND,true);
        ServerLevel level = player.serverLevel();

        Vec3 dir3D = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 motion = new Vec3(0.0f, 0.0f, 0.0f);
        Vec3 pos = player.position().add(dir3D.scale(3)).add(0.0f,0.2f,0.0f);
        storeVec3(player,pos);
        storeRot(player,player.getXRot(),player.getYRot());
        Skill11_Entity_ e = Skill11_Entity_.spawn(level, player, pos, motion, r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        double rangeMultiplier = player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.getHolder().get());
        float r = (float) (20 * rangeMultiplier);
        //单位长度:r/20
        Vec3 center = getVec3(player);
        ServerLevel level = player.serverLevel();
        PlayerList pl = level.getServer().getPlayerList();
        for (ServerPlayer p : pl.getPlayers()) {
            //外圆
            for (int i = 0; i < 300; i++) {
                float step = (float) (i * Math.PI * 2 / 300);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.25,200),true,center.x+r*Mth.cos(step), center.y+0.5, center.z+r*Mth.sin(step),1, 0, 0, 0, 0);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.21,200),true,center.x+r*0.9*Mth.cos(step), center.y+0.5, center.z+r*0.9*Mth.sin(step),1, 0, 0, 0, 0);
                //x(t)=9.5*cos(1*t)+0.5*cos(-11*t), y(t)=9.5*sin(1*t)+0.5*sin(-11*t), t=[0, 2*pi]
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.23,200),true,center.x+r*0.95*Mth.cos(step)+0.05*r*Mth.cos(-11*step), center.y+0.5, center.z+r*0.95*Mth.sin(step)+0.05*r*Mth.sin(-11*step),1, 0, 0, 0, 0);
            }
            //内大圆
            for (int i = 0; i < 200; i++) {
                float step = (float) (i * Math.PI * 2 / 200);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.6*Mth.cos(step)-r*0.3, center.y+0.5, center.z+r*0.6*Mth.sin(step),1, 0, 0, 0, 0);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.575*Mth.cos(step)-r*0.3, center.y+0.5, center.z+r*0.575*Mth.sin(step),1, 0, 0, 0, 0);
                //x(t)=3.75*cos(6*t)+2*cos(-9*t)-3, y(t)=3.75*sin(6*t)+2*sin(-9*t), t=[0, 2*pi]
                //x(t)=3.45*cos(6*t)+1.76*cos(-9*t)-3, y(t)=3.45*sin(6*t)+1.76*sin(-9*t), t=[0, 2*pi]
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.375*Mth.cos(6*step)+r*0.2*Mth.cos(-9*step)-r*0.3, center.y+0.5, center.z+r*0.375*Mth.sin(6*step)+r*0.2*Mth.sin(-9*step),1, 0, 0, 0, 0);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.345*Mth.cos(6*step)+r*0.176*Mth.cos(-9*step)-r*0.3, center.y+0.5, center.z+r*0.345*Mth.sin(6*step)+r*0.176*Mth.sin(-9*step),1, 0, 0, 0, 0);
            }
            //内小圆
            for (int i = 0; i < 150; i++) {
                float step = (float) (i * Math.PI * 2 / 150);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.3*Mth.cos(step)+r*0.6, center.y+0.5, center.z+r*0.3*Mth.sin(step),1, 0, 0, 0, 0);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.275*Mth.cos(step)+r*0.6, center.y+0.5, center.z+r*0.275*Mth.sin(step),1, 0, 0, 0, 0);
                //x(t)=2*cos(3*t)+0.75*cos(-6*t)+6, y(t)=2*sin(3*t)+0.75*sin(-6*t), t=[0, 2*pi]
                //x(t)=2*cos(3*t+pi/3)+0.75*cos(-6*t+pi/3)+6, y(t)=2*sin(3*t+pi/3)+0.75*sin(-6*t+pi/3), t=[0, 2*pi]
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.2*Mth.cos(3*step)+r*0.075*Mth.cos(-6*step)+0.6*r, center.y+0.5, center.z+r*0.2*Mth.sin(3*step)+r*0.075*Mth.sin(-6*step),1, 0, 0, 0, 0);
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*0.2*Mth.cos(3*step+Mth.PI/3)+r*0.075*Mth.cos(-6*step+Mth.PI/3)+0.6*r, center.y+0.5, center.z+r*0.2*Mth.sin(3*step+Mth.PI/3)+r*0.075*Mth.sin(-6*step+Mth.PI/3),1, 0, 0, 0, 0);
            }
            //斜线
            for (int i = -87; i < 88; i++) {
                float step = (float) (i * Math.PI / 360);
                //x(t)=13*cos(t)-10, y(t)=13*sin(t), t=[-87*pi/360, 87*pi/360]
                level.sendParticles(p,new OrbitParticleOptions(new Vec3(center.x, center.y+0.5, center.z),0.8,200),true,center.x+r*1.3*Mth.cos(step)-r, center.y+0.5, center.z+r*1.3*Mth.sin(step),1, 0, 0, 0, 0);
            }
        }
        Skill11_Entity e = Skill11_Entity.spawn(level, player,player.position(),new Vec3(0.0,0.0,0.0),r);
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
