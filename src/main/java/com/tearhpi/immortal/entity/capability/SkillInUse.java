package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class SkillInUse {
        //技能吟唱时间
        public int IsInUseSkill = -1;
        //正在使用几号技能
        public int IsInUseSkillNumber = 0;
        //可主动结束技能的冷却长度
        public int skill7Lasting = -1;
        public int skill22Lasting = -1;
        public int skill23Lasting = -1;
        public int skill45Lasting = -1;
        //蓄力技能持续时间
        public int skill9Lasting = -1;
        public int skill12Lasting = -1;
        public int skill21Lasting = -1;
        public int skill44Lasting = -1;
        public int skill68Lasting = -1;

        public SkillInUse() {
        }
        public void readAdditionalSaveData(CompoundTag p_38716_) {
            if (p_38716_.contains("SkillInUse")) {
                //NBT信息读取
                read(p_38716_.getCompound("SkillInUse"));
            }
        }

        public void read(CompoundTag skillInUse) {
            //NBT信息读取
            //1.技能点信息读取
            //技能点
            this.IsInUseSkill = skillInUse.getInt("IsInUseSkill");
            this.IsInUseSkillNumber = skillInUse.getInt("IsInUseSkillNumber");
            this.skill7Lasting = skillInUse.getInt("skill7Lasting");
            this.skill9Lasting = skillInUse.getInt("skill9Lasting");
            this.skill12Lasting = skillInUse.getInt("skill12Lasting");
            this.skill21Lasting = skillInUse.getInt("skill21Lasting");
            this.skill22Lasting = skillInUse.getInt("skill22Lasting");
            this.skill23Lasting = skillInUse.getInt("skill23Lasting");
            this.skill44Lasting = skillInUse.getInt("skill44Lasting");
            this.skill45Lasting = skillInUse.getInt("skill45Lasting");
            this.skill68Lasting = skillInUse.getInt("skill68Lasting");
        }

        public void addAdditionalSaveData(CompoundTag p_38720_, ArrayList<Integer> p_38719_) {
            CompoundTag skillInUse = getSkillCompoundTag(p_38719_);
            p_38720_.put("SkillInUse", skillInUse);
        }
        public ArrayList<Integer> getNumInfo(){
            ArrayList<Integer> SkillInfo = new ArrayList<Integer>();
            SkillInfo.add(this.IsInUseSkill);
            SkillInfo.add(this.IsInUseSkillNumber);
            SkillInfo.add(this.skill7Lasting);
            SkillInfo.add(this.skill9Lasting);
            SkillInfo.add(this.skill12Lasting);
            SkillInfo.add(this.skill21Lasting);
            SkillInfo.add(this.skill22Lasting);
            SkillInfo.add(this.skill23Lasting);
            SkillInfo.add(this.skill44Lasting);
            SkillInfo.add(this.skill45Lasting);
            SkillInfo.add(this.skill68Lasting);
            return SkillInfo;
        }
        public CompoundTag getSkillCompoundTag(ArrayList<Integer> p_38719_){
            //NBT组 技能
            CompoundTag SkillInUse = new CompoundTag();
            //NBT信息添加
            //1.技能点信息添加
            SkillInUse.putInt("IsInUseSkill", p_38719_.get(0));
            SkillInUse.putInt("IsInUseSkillNumber", p_38719_.get(1));
            SkillInUse.putInt("skill7Lasting", p_38719_.get(2));
            SkillInUse.putInt("skill9Lasting", p_38719_.get(3));
            SkillInUse.putInt("skill12Lasting", p_38719_.get(4));
            SkillInUse.putInt("skill21Lasting", p_38719_.get(5));
            SkillInUse.putInt("skill22Lasting", p_38719_.get(6));
            SkillInUse.putInt("skill23Lasting", p_38719_.get(7));
            SkillInUse.putInt("skill44Lasting", p_38719_.get(8));
            SkillInUse.putInt("skill45Lasting", p_38719_.get(9));
            SkillInUse.putInt("skill68Lasting", p_38719_.get(10));
            return SkillInUse;
        }
}
