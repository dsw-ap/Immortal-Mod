package com.tearhpi.immortal.skill.behaviour.active.fire;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.custom.skills.Skill42_Entity;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_SkillAttack;
import com.tearhpi.immortal.skill.ModSkills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Skill42 {
    public static void Main(Player player) {
        //判断cd
        if (ModSkills.getCDbyNumAndPlayer(42,player) == 0) {
            //重设CD
            ModSkills.setCDbyNumAndPlayer(42,player);
            //SP消耗设置
            ModSkills.consumeSP(42,player);
            //吟唱设置
            ModSkills.setCharmingTime(42,40,player);
        } else {
            player.sendSystemMessage(Component.literal("In CD,"+ModSkills.getCDbyNumAndPlayer(42 ,player)));
        }
    }
    public static void Do(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        float r = 4.0f;
        r *= (float) player.getAttribute(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()).getValue();
        if (player == null) return;
        ServerLevel level = player.serverLevel();
        player.swing(InteractionHand.MAIN_HAND,true);
        //召唤石板
        Vec3 dir3D  = Vec3.directionFromRotation(0, player.getYRot());
        Vec3 p3d = player.getEyePosition().add(dir3D.normalize().scale(r)).add(new Vec3(0, 5, 0));
        Vec3 motion = new Vec3(0,0,0);
        Skill42_Entity e = Skill42_Entity.spawn(level, player, p3d, motion, r);
        // 设置自定义 NBT
        CompoundTag tag = new CompoundTag();
        //放入物品
        CompoundTag item = new CompoundTag();
        item.putString("id","minecraft:stone");
        item.putInt("Count",1);
        tag.put("item",item);
        tag.putInt("teleport_duration", 1);

        ListTag translation = new ListTag();
        translation.add(FloatTag.valueOf(0.0f));
        translation.add(FloatTag.valueOf(0.0f));
        translation.add(FloatTag.valueOf(0.0f));

        ListTag scaleTag = new ListTag();
        scaleTag.add(FloatTag.valueOf(0.1f));
        scaleTag.add(FloatTag.valueOf(0.2f));
        scaleTag.add(FloatTag.valueOf(0.1f));

        ListTag leftRotation = new ListTag();
        leftRotation.add(FloatTag.valueOf(0.0f));
        leftRotation.add(FloatTag.valueOf(0.0f));
        leftRotation.add(FloatTag.valueOf(0.0f));
        leftRotation.add(FloatTag.valueOf(1.0f)); // 单位四元数

        ListTag rightRotation = new ListTag();
        rightRotation.add(FloatTag.valueOf(0.0f));
        rightRotation.add(FloatTag.valueOf(0.0f));
        rightRotation.add(FloatTag.valueOf(0.0f));
        rightRotation.add(FloatTag.valueOf(1.0f)); // 单位四元数

        CompoundTag transformation = new CompoundTag();
        transformation.put("translation", translation);
        transformation.put("scale", scaleTag);
        transformation.put("left_rotation", leftRotation);
        transformation.put("right_rotation", rightRotation);

        tag.put("transformation", transformation);

        e.load(tag);
        e.setPos(p3d);
        e.ownerId = player.getUUID();
        e.setRadius(r);

        // 添加到世界中
        BoundEntityManager_SkillAttack.bind(player, e);
        level.addFreshEntity(e);
    }
}
