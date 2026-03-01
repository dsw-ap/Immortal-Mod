package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class SkillAssemble {
        public int Slot1 = -1;
        public int Slot2 = -1;
        public int Slot3 = -1;
        public int Slot4 = -1;
        public int Slot5 = -1;
        public int Slot6 = -1;
        public int Slot7 = -1;
        public int Slot8 = -1;
        public int Slot9 = -1;
        public int Slot10 = -1;
        //选择了哪个技能槽(左/右)
        public int UseSkill = 0;

        public SkillAssemble() {
        }
        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("SkillAssemble")) {
                //NBT信息读取
                read(p_38716_.getCompound("SkillAssemble"));
            }
        }

        public void read(CompoundTag skillAssemble) {
                //NBT信息读取
                //1.技能点信息读取
                //技能点
                this.Slot1 = skillAssemble.getInt("Slot1");
                this.Slot2 = skillAssemble.getInt("Slot2");
                this.Slot3 = skillAssemble.getInt("Slot3");
                this.Slot4 = skillAssemble.getInt("Slot4");
                this.Slot5 = skillAssemble.getInt("Slot5");
                this.Slot6 = skillAssemble.getInt("Slot6");
                this.Slot7 = skillAssemble.getInt("Slot7");
                this.Slot8 = skillAssemble.getInt("Slot8");
                this.Slot9 = skillAssemble.getInt("Slot9");
                this.Slot10 = skillAssemble.getInt("Slot10");
        }

        public void addAdditionalSaveData(CompoundTag p_38720_, ArrayList<Integer> p_38719_) {
            CompoundTag skillAssemble = getSkillCompoundTag(p_38719_);
            p_38720_.put("SkillAssemble", skillAssemble);
        }
        public ArrayList<Integer> getNumInfo(){
            ArrayList<Integer> SkillInfo = new ArrayList<Integer>();
            SkillInfo.add(this.Slot1);
            SkillInfo.add(this.Slot2);
            SkillInfo.add(this.Slot3);
            SkillInfo.add(this.Slot4);
            SkillInfo.add(this.Slot5);
            SkillInfo.add(this.Slot6);
            SkillInfo.add(this.Slot7);
            SkillInfo.add(this.Slot8);
            SkillInfo.add(this.Slot9);
            SkillInfo.add(this.Slot10);
            SkillInfo.add(this.UseSkill);
            return SkillInfo;
        }
        public CompoundTag getSkillCompoundTag(ArrayList<Integer> p_38719_){
            //NBT组 技能
            CompoundTag SkillAssemble = new CompoundTag();
            //NBT信息添加
            //1.技能点信息添加
            SkillAssemble.putInt("Slot1", p_38719_.get(0));
            SkillAssemble.putInt("Slot2", p_38719_.get(1));
            SkillAssemble.putInt("Slot3", p_38719_.get(2));
            SkillAssemble.putInt("Slot4", p_38719_.get(3));
            SkillAssemble.putInt("Slot5", p_38719_.get(4));
            SkillAssemble.putInt("Slot6", p_38719_.get(5));
            SkillAssemble.putInt("Slot7", p_38719_.get(6));
            SkillAssemble.putInt("Slot8", p_38719_.get(7));
            SkillAssemble.putInt("Slot9", p_38719_.get(8));
            SkillAssemble.putInt("Slot10", p_38719_.get(9));
            SkillAssemble.putInt("UseSkill", p_38719_.get(10));
            return SkillAssemble;
        }
        public void reset(){
            this.Slot1 = -1;
            this.Slot2 = -1;
            this.Slot3 = -1;
            this.Slot4 = -1;
            this.Slot5 = -1;
            this.Slot6 = -1;
            this.Slot7 = -1;
            this.Slot8 = -1;
            this.Slot9 = -1;
            this.Slot10 = -1;
        }
}
