package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

public class ManaPoint {
    private int manaCount = 100;
    private float manaRecoverLevel;

        public ManaPoint() {
            this.manaRecoverLevel = 5.0F;
        }

        public void add(int p_335153_,int max) {
            this.manaCount = Math.min(p_335153_ + this.manaCount,max);
        }
        public void set(int p_335153_) {
        this.manaCount = p_335153_;
    }

        public boolean remove(int p_335153_) {
            if (this.manaCount >= p_335153_) {
                this.manaCount -= p_335153_;
                return true;
            } else {
                return false;
            }
        }


        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("manaCount", 99)) {
                this.manaCount = p_38716_.getInt("manaCount");
            }
        }

        public void addAdditionalSaveData(CompoundTag p_38720_,int number) {
            p_38720_.putInt("manaCount", number);
        }

        public int get() {
            return this.manaCount;
        }

}
