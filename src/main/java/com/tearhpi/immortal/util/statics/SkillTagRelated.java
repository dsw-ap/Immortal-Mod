package com.tearhpi.immortal.util.statics;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SkillTagRelated {
    public static void storeVec3(ServerPlayer player, Vec3 vec3) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag skill;

        if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
            skill = new CompoundTag();
            tag.put(Player.PERSISTED_NBT_TAG, skill);
        } else {
            skill = tag.getCompound(Player.PERSISTED_NBT_TAG);
        }

        skill.putDouble("skill_vec3_x", vec3.x);
        skill.putDouble("skill_vec3_y", vec3.y);
        skill.putDouble("skill_vec3_z", vec3.z);
    }
    public static Vec3 getVec3(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag skill = tag.getCompound(Player.PERSISTED_NBT_TAG);

        return new Vec3(skill.getDouble("skill_vec3_x"),skill.getDouble("skill_vec3_y"),skill.getDouble("skill_vec3_z"));
    }
    public static void storeRot(ServerPlayer player, float xRot, float yRot) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag skill;
        if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
            skill = new CompoundTag();
            tag.put(Player.PERSISTED_NBT_TAG, skill);
        } else {
            skill = tag.getCompound(Player.PERSISTED_NBT_TAG);
        }
        skill.putFloat("skill_xRot", xRot);
        skill.putFloat("skill_yRot", yRot);
    }
    public static float getxRot(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag skill = tag.getCompound(Player.PERSISTED_NBT_TAG);
        return skill.getFloat("skill_xRot");
    }
    public static float getyRot(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag skill = tag.getCompound(Player.PERSISTED_NBT_TAG);
        return skill.getFloat("skill_yRot");
    }
}
