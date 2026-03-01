package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

public class NormalAttackEnergy {
    private int energy = 0;

        public NormalAttackEnergy() {
            this.energy = 0;
        }

        public void add(int max) {
            this.energy++;
            if (energy > max) {
                energy = max;
            }
        }
        public void set(int p_335153_) {
        this.energy = p_335153_;
    }

        public void remove() {
            this.energy--;
        }
        public boolean isEmpty() {
            return this.energy == 0;
        }
        public int get() {
            return this.energy;
        }

        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("normalAttackEnergy", 99)) {
                this.energy = p_38716_.getInt("normalAttackEnergy");
            }
        }

        public void addAdditionalSaveData(CompoundTag p_38720_,int number) {
            p_38720_.putInt("normalAttackEnergy", number);
        }

}
