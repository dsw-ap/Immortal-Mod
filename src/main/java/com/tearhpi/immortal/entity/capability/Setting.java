package com.tearhpi.immortal.entity.capability;


import com.tearhpi.immortal.networking.ModNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;

public class Setting {
        public int Setting1 = 0;
        public int Setting2 = 0;
        public int Setting3 = 0;
        public int Setting4 = 0;
        public int Setting5 = 0;
        public int Setting6 = 0;
        public int Setting7 = 0;
        public int Setting8 = 0;
        public int Setting9 = 0;
        public int Setting10 = 0;

        public Setting() {}
        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("Setting")) {
                //NBT信息读取
                read(p_38716_.getCompound("Setting"));
            }
        }
        public void read(CompoundTag setting) {
                this.Setting1 = setting.getInt("Setting1");
                this.Setting2 = setting.getInt("Setting2");
                this.Setting3 = setting.getInt("Setting3");
                this.Setting4 = setting.getInt("Setting4");
                this.Setting5 = setting.getInt("Setting5");
                this.Setting6 = setting.getInt("Setting6");
                this.Setting7 = setting.getInt("Setting7");
                this.Setting8 = setting.getInt("Setting8");
                this.Setting9 = setting.getInt("Setting9");
                this.Setting10 = setting.getInt("Setting10");
        }
        public void addAdditionalSaveData(CompoundTag p_38720_, ArrayList<Integer> p_38719_) {
            CompoundTag setting = getSkillCompoundTag(p_38719_);
            p_38720_.put("Setting", setting);
        }
        public ArrayList<Integer> getNumInfo(){
            ArrayList<Integer> Setting = new ArrayList<Integer>();
            Setting.add(this.Setting1);
            Setting.add(this.Setting2);
            Setting.add(this.Setting3);
            Setting.add(this.Setting4);
            Setting.add(this.Setting5);
            Setting.add(this.Setting6);
            Setting.add(this.Setting7);
            Setting.add(this.Setting8);
            Setting.add(this.Setting9);
            Setting.add(this.Setting10);
            return Setting;
        }
        public CompoundTag getSkillCompoundTag(ArrayList<Integer> p_38719_){
            //NBT组 技能
            CompoundTag Setting = new CompoundTag();
            //NBT信息添加
            //1.技能点信息添加
            Setting.putInt("Setting1", p_38719_.get(0));
            Setting.putInt("Setting2", p_38719_.get(1));
            Setting.putInt("Setting3", p_38719_.get(2));
            Setting.putInt("Setting4", p_38719_.get(3));
            Setting.putInt("Setting5", p_38719_.get(4));
            Setting.putInt("Setting6", p_38719_.get(5));
            Setting.putInt("Setting7", p_38719_.get(6));
            Setting.putInt("Setting8", p_38719_.get(7));
            Setting.putInt("Setting9", p_38719_.get(8));
            Setting.putInt("Setting10", p_38719_.get(9));
            return Setting;
        }
        public void reset(){
            this.Setting1 = 0;
            this.Setting2 = 0;
            this.Setting3 = 0;
            this.Setting4 = 0;
            this.Setting5 = 0;
            this.Setting6 = 0;
            this.Setting7 = 0;
            this.Setting8 = 0;
            this.Setting9 = 0;
            this.Setting10 = 0;
        }
}
