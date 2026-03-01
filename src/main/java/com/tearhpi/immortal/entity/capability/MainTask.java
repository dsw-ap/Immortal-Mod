package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

public class MainTask {
    public int MainTask_Progress = 0;

    public MainTask() {}

    public void set(int p) {
        this.MainTask_Progress = p;
    }
    public void add() {
        this.MainTask_Progress++;
    }
    public void reset(){
        this.MainTask_Progress = 0;
    }
    public int get() {
        return this.MainTask_Progress;
    }
    public void readAdditionalSaveData(CompoundTag p_38716_) {
        if (p_38716_.contains("maintask_progress",99)) {
            this.MainTask_Progress = p_38716_.getInt("maintask_progress");
        }
    }
    public void addAdditionalSaveData(CompoundTag p_38720_,int p) {
        p_38720_.putInt("maintask_progress", p);
    }
}
