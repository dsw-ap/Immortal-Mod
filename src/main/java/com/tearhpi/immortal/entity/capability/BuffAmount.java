package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

public class BuffAmount {
    private int BuffAmount = 0;

        public BuffAmount() {
        }

        public void add(int p_335153_) {
            this.BuffAmount = p_335153_ + this.BuffAmount;
        }
        public void set(int p_335153_) {
            this.BuffAmount = p_335153_;
        }
        public boolean remove(int p_335153_) {
            if (this.BuffAmount >= p_335153_) {
                this.BuffAmount -= p_335153_;
                return true;
            } else {
                return false;
            }
        }

        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("BuffAmount", 99)) {
                this.BuffAmount = p_38716_.getInt("BuffAmount");
            }
        }

        public void addAdditionalSaveData(CompoundTag p_38720_,int number) {
            p_38720_.putInt("BuffAmount", number);
        }

        public int get() {
            return this.BuffAmount;
        }

}
