package com.tearhpi.immortal.entity.capability;

import net.minecraft.nbt.CompoundTag;

public class Shield {
    private int shield = 0;

    public Shield() {
    }

    public void add(int p_335153_) {
        this.shield = p_335153_ + this.shield;
        if (this.shield > 3) {
            this.shield = 3;
        }
    }
    public void set(int p_335153_) {
        this.shield = p_335153_;
        if (this.shield > 3) {
            this.shield = 3;
        }
    }
    public boolean remove(int p_335153_) {
        if (this.shield >= p_335153_) {
            this.shield -= p_335153_;
            return true;
        } else {
            return false;
        }
    }

    public void readAdditionalSaveData(CompoundTag p_38716_) {
        if (p_38716_.contains("Shield", 99)) {
            this.shield = p_38716_.getInt("Shield");
        }
    }

    public void addAdditionalSaveData(CompoundTag p_38720_,int number) {
        p_38720_.putInt("Shield", number);
    }

    public int get() {
        return this.shield;
    }

}
