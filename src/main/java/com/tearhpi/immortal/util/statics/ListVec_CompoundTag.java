package com.tearhpi.immortal.util.statics;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ListVec_CompoundTag {
    public static CompoundTag ListVecToCompoundTag(List<Vec3> v) {
        CompoundTag tag = new CompoundTag();
        int i = 0;
        for (Vec3 vec : v) {
            tag.putDouble(String.valueOf(i)+"x", vec.x);
            tag.putDouble(String.valueOf(i)+"y", vec.y);
            tag.putDouble(String.valueOf(i)+"z", vec.z);
            i += 1;
        }
        tag.putInt("index",i);
        return tag;
    }
    public static List<Vec3> CompoundTagToListVec(CompoundTag tag) {
        int index = tag.getInt("index");
        //System.out.println(index);
        List<Vec3> lv = new ArrayList<Vec3>();
        for (int i = 0;i < index;i++){
            lv.add(new Vec3(tag.getDouble(String.valueOf(i)+"x"),tag.getDouble(String.valueOf(i)+"y"),tag.getDouble(String.valueOf(i)+"z")));
        }
        return lv;
    }
}
