package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

public class Coin {
    private int Coin = 100;

        public Coin() {
        }

        public void add(int p_335153_) {
            this.Coin = p_335153_ + this.Coin;
        }
        public void set(int p_335153_) {
            this.Coin = p_335153_;
        }
        public boolean remove(int p_335153_) {
            if (this.Coin >= p_335153_) {
                this.Coin -= p_335153_;
                return true;
            } else {
                return false;
            }
        }

        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("Coin", 99)) {
                this.Coin = p_38716_.getInt("Coin");
            }
        }

        public void addAdditionalSaveData(CompoundTag p_38720_,int number) {
            p_38720_.putInt("Coin", number);
        }

        public int get() {
            return this.Coin;
        }

}
