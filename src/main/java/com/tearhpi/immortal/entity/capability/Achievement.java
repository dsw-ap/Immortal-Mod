package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class Achievement {
    public int Achievement1 = 0;
    public int Achievement2 = 0;
    public int Achievement3 = 0;
    public int Achievement4 = 0;
    public int Achievement5 = 0;
    public int Achievement6 = 0;
    public int Achievement7 = 0;
    public int Achievement8 = 0;
    public int Achievement9 = 0;
    public int Achievement10 = 0;

    public Achievement() {}
    public void readAdditionalSaveData(CompoundTag p_38716_) {
        if (p_38716_.contains("Achievement")) {
            //NBT信息读取
            read(p_38716_.getCompound("Achievement"));
        }
    }
    public void read(CompoundTag setting) {
        this.Achievement1 = setting.getInt("Achievement1");
        this.Achievement2 = setting.getInt("Achievement2");
        this.Achievement3 = setting.getInt("Achievement3");
        this.Achievement4 = setting.getInt("Achievement4");
        this.Achievement5 = setting.getInt("Achievement5");
        this.Achievement6 = setting.getInt("Achievement6");
        this.Achievement7 = setting.getInt("Achievement7");
        this.Achievement8 = setting.getInt("Achievement8");
        this.Achievement9 = setting.getInt("Achievement9");
        this.Achievement10 = setting.getInt("Achievement10");
    }
    public void addAdditionalSaveData(CompoundTag p_38720_, ArrayList<Integer> p_38719_) {
        CompoundTag achievementCompoundTag = getAchievementCompoundTag(p_38719_);
        p_38720_.put("Achievement", achievementCompoundTag);
    }
    public ArrayList<Integer> getNumInfo(){
        ArrayList<Integer> Achievement = new ArrayList<Integer>();
        Achievement.add(this.Achievement1);
        Achievement.add(this.Achievement2);
        Achievement.add(this.Achievement3);
        Achievement.add(this.Achievement4);
        Achievement.add(this.Achievement5);
        Achievement.add(this.Achievement6);
        Achievement.add(this.Achievement7);
        Achievement.add(this.Achievement8);
        Achievement.add(this.Achievement9);
        Achievement.add(this.Achievement10);
        return Achievement;
    }
    public CompoundTag getAchievementCompoundTag(ArrayList<Integer> p_38719_){
        //NBT组 技能
        CompoundTag Achievement = new CompoundTag();
        //NBT信息添加
        //1.技能点信息添加
        Achievement.putInt("Achievement1", p_38719_.get(0));
        Achievement.putInt("Achievement2", p_38719_.get(1));
        Achievement.putInt("Achievement3", p_38719_.get(2));
        Achievement.putInt("Achievement4", p_38719_.get(3));
        Achievement.putInt("Achievement5", p_38719_.get(4));
        Achievement.putInt("Achievement6", p_38719_.get(5));
        Achievement.putInt("Achievement7", p_38719_.get(6));
        Achievement.putInt("Achievement8", p_38719_.get(7));
        Achievement.putInt("Achievement9", p_38719_.get(8));
        Achievement.putInt("Achievement10", p_38719_.get(9));
        return Achievement;
    }
    public void reset(){
        this.Achievement1 = 0;
        this.Achievement2 = 0;
        this.Achievement3 = 0;
        this.Achievement4 = 0;
        this.Achievement5 = 0;
        this.Achievement6 = 0;
        this.Achievement7 = 0;
        this.Achievement8 = 0;
        this.Achievement9 = 0;
        this.Achievement10 = 0;
    }
}
