package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class SkillCD {
    public int Skill6_CD = 0;
    public int Skill7_CD = 0;
    public int Skill8_CD = 0;
    public int Skill9_CD = 0;
    public int Skill10_CD = 0;
    public int Skill11_CD = 0;
    public int Skill12_CD = 0;
    public int Skill13_CD = 0;
    public int Skill15_CD = 0;
    public int Skill17_CD = 0;
    public int Skill18_CD = 0;
    public int Skill19_CD = 0;
    public int Skill20_CD = 0;
    public int Skill21_CD = 0;
    public int Skill22_CD = 0;
    public int Skill23_CD = 0;
    public int Skill24_CD = 0;
    public int Skill26_CD = 0;
    public int Skill28_CD = 0;
    public int Skill29_CD = 0;
    public int Skill30_CD = 0;
    public int Skill31_CD = 0;
    public int Skill32_CD = 0;
    public int Skill33_CD = 0;
    public int Skill34_CD = 0;
    public int Skill35_CD = 0;
    public int Skill35_Charge = 0;
    public int Skill37_CD = 0;
    public int Skill39_CD = 0;
    public int Skill40_CD = 0;
    public int Skill41_CD = 0;
    public int Skill42_CD = 0;
    public int Skill43_CD = 0;
    public int Skill44_CD = 0;
    public int Skill45_CD = 0;
    public int Skill46_CD = 0;
    public int Skill48_CD = 0;
    public int Skill50_CD = 0;
    public int Skill51_CD = 0;
    public int Skill52_CD = 0;
    public int Skill53_CD = 0;
    public int Skill54_CD = 0;
    public int Skill55_CD = 0;
    public int Skill56_CD = 0;
    public int Skill57_CD = 0;
    public int Skill59_CD = 0;
    public int Skill61_CD = 0;
    public int Skill62_CD = 0;
    public int Skill63_CD = 0;
    public int Skill64_CD = 0;
    public int Skill65_CD = 0;
    public int Skill66_CD = 0;
    public int Skill68_CD = 0;
    public int Skill69_CD = 0;
    public int Skill70_CD = 0;



    public SkillCD() {
    }
    public void readAdditionalSaveData(CompoundTag p_38716_) {
        if (p_38716_.contains("SkillCD")) {
            //NBT信息读取
            read(p_38716_.getCompound("SkillCD"));
        }
    }


    public void read(CompoundTag skillCD) {
        //NBT信息读取
        //1.技能点信息读取
        //技能点
        this.Skill6_CD = skillCD.getInt("Skill6_CD");
        this.Skill7_CD = skillCD.getInt("Skill7_CD");
        this.Skill8_CD = skillCD.getInt("Skill8_CD");
        this.Skill9_CD = skillCD.getInt("Skill9_CD");
        this.Skill10_CD = skillCD.getInt("Skill10_CD");
        this.Skill11_CD = skillCD.getInt("Skill11_CD");
        this.Skill12_CD = skillCD.getInt("Skill12_CD");
        this.Skill13_CD = skillCD.getInt("Skill13_CD");
        this.Skill15_CD = skillCD.getInt("Skill15_CD");
        this.Skill17_CD = skillCD.getInt("Skill17_CD");
        this.Skill18_CD = skillCD.getInt("Skill18_CD");
        this.Skill19_CD = skillCD.getInt("Skill19_CD");
        this.Skill20_CD = skillCD.getInt("Skill20_CD");
        this.Skill21_CD = skillCD.getInt("Skill21_CD");
        this.Skill22_CD = skillCD.getInt("Skill22_CD");
        this.Skill23_CD = skillCD.getInt("Skill23_CD");
        this.Skill24_CD = skillCD.getInt("Skill24_CD");
        this.Skill26_CD = skillCD.getInt("Skill26_CD");
        this.Skill28_CD = skillCD.getInt("Skill28_CD");
        this.Skill29_CD = skillCD.getInt("Skill29_CD");
        this.Skill30_CD = skillCD.getInt("Skill30_CD");
        this.Skill31_CD = skillCD.getInt("Skill31_CD");
        this.Skill32_CD = skillCD.getInt("Skill32_CD");
        this.Skill33_CD = skillCD.getInt("Skill33_CD");
        this.Skill34_CD = skillCD.getInt("Skill34_CD");
        this.Skill35_CD = skillCD.getInt("Skill35_CD");
        this.Skill35_Charge = skillCD.getInt("Skill35_Charge");
        this.Skill37_CD = skillCD.getInt("Skill37_CD");
        this.Skill39_CD = skillCD.getInt("Skill39_CD");
        this.Skill40_CD = skillCD.getInt("Skill40_CD");
        this.Skill41_CD = skillCD.getInt("Skill41_CD");
        this.Skill42_CD = skillCD.getInt("Skill42_CD");
        this.Skill43_CD = skillCD.getInt("Skill43_CD");
        this.Skill44_CD = skillCD.getInt("Skill44_CD");
        this.Skill45_CD = skillCD.getInt("Skill45_CD");
        this.Skill46_CD = skillCD.getInt("Skill46_CD");
        this.Skill48_CD = skillCD.getInt("Skill48_CD");
        this.Skill50_CD = skillCD.getInt("Skill50_CD");
        this.Skill51_CD = skillCD.getInt("Skill51_CD");
        this.Skill52_CD = skillCD.getInt("Skill52_CD");
        this.Skill53_CD = skillCD.getInt("Skill53_CD");
        this.Skill54_CD = skillCD.getInt("Skill54_CD");
        this.Skill55_CD = skillCD.getInt("Skill55_CD");
        this.Skill56_CD = skillCD.getInt("Skill56_CD");
        this.Skill57_CD = skillCD.getInt("Skill57_CD");
        this.Skill59_CD = skillCD.getInt("Skill59_CD");
        this.Skill61_CD = skillCD.getInt("Skill61_CD");
        this.Skill62_CD = skillCD.getInt("Skill62_CD");
        this.Skill63_CD = skillCD.getInt("Skill63_CD");
        this.Skill64_CD = skillCD.getInt("Skill64_CD");
        this.Skill65_CD = skillCD.getInt("Skill65_CD");
        this.Skill66_CD = skillCD.getInt("Skill66_CD");
        this.Skill68_CD = skillCD.getInt("Skill68_CD");
        this.Skill69_CD = skillCD.getInt("Skill69_CD");
        this.Skill70_CD = skillCD.getInt("Skill70_CD");
    }

    public void addAdditionalSaveData(CompoundTag p_38720_, ArrayList<Integer> p_38719_) {
        CompoundTag skillCD = getSkillCompoundTag(p_38719_);
        p_38720_.put("SkillCD", skillCD);
    }
    public ArrayList<Integer> getNumInfo(){
        ArrayList<Integer> SkillInfo = new ArrayList<Integer>();
        SkillInfo.add(this.Skill6_CD);
        SkillInfo.add(this.Skill7_CD);
        SkillInfo.add(this.Skill8_CD);
        SkillInfo.add(this.Skill9_CD);
        SkillInfo.add(this.Skill10_CD);
        SkillInfo.add(this.Skill11_CD);
        SkillInfo.add(this.Skill12_CD);
        SkillInfo.add(this.Skill13_CD);
        SkillInfo.add(this.Skill15_CD);
        SkillInfo.add(this.Skill17_CD);
        SkillInfo.add(this.Skill18_CD);
        SkillInfo.add(this.Skill19_CD);
        SkillInfo.add(this.Skill20_CD);
        SkillInfo.add(this.Skill21_CD);
        SkillInfo.add(this.Skill22_CD);
        SkillInfo.add(this.Skill23_CD);
        SkillInfo.add(this.Skill24_CD);
        SkillInfo.add(this.Skill26_CD);
        SkillInfo.add(this.Skill28_CD);
        SkillInfo.add(this.Skill29_CD);
        SkillInfo.add(this.Skill30_CD);
        SkillInfo.add(this.Skill31_CD);
        SkillInfo.add(this.Skill32_CD);
        SkillInfo.add(this.Skill33_CD);
        SkillInfo.add(this.Skill34_CD);
        SkillInfo.add(this.Skill35_CD);
        SkillInfo.add(this.Skill35_Charge);
        SkillInfo.add(this.Skill37_CD);
        SkillInfo.add(this.Skill39_CD);
        SkillInfo.add(this.Skill40_CD);
        SkillInfo.add(this.Skill41_CD);
        SkillInfo.add(this.Skill42_CD);
        SkillInfo.add(this.Skill43_CD);
        SkillInfo.add(this.Skill44_CD);
        SkillInfo.add(this.Skill45_CD);
        SkillInfo.add(this.Skill46_CD);
        SkillInfo.add(this.Skill48_CD);
        SkillInfo.add(this.Skill50_CD);
        SkillInfo.add(this.Skill51_CD);
        SkillInfo.add(this.Skill52_CD);
        SkillInfo.add(this.Skill53_CD);
        SkillInfo.add(this.Skill54_CD);
        SkillInfo.add(this.Skill55_CD);
        SkillInfo.add(this.Skill56_CD);
        SkillInfo.add(this.Skill57_CD);
        SkillInfo.add(this.Skill59_CD);
        SkillInfo.add(this.Skill61_CD);
        SkillInfo.add(this.Skill62_CD);
        SkillInfo.add(this.Skill63_CD);
        SkillInfo.add(this.Skill64_CD);
        SkillInfo.add(this.Skill65_CD);
        SkillInfo.add(this.Skill66_CD);
        SkillInfo.add(this.Skill68_CD);
        SkillInfo.add(this.Skill69_CD);
        SkillInfo.add(this.Skill70_CD);

        return SkillInfo;
    }
    public CompoundTag getSkillCompoundTag(ArrayList<Integer> p_38719_){
        //NBT组 技能
        CompoundTag SkillAssemble = new CompoundTag();
        //NBT信息添加
        //1.技能点信息添加
        SkillAssemble.putInt("Skill6_CD", p_38719_.get(0));
        SkillAssemble.putInt("Skill7_CD", p_38719_.get(1));
        SkillAssemble.putInt("Skill8_CD", p_38719_.get(2));
        SkillAssemble.putInt("Skill9_CD", p_38719_.get(3));
        SkillAssemble.putInt("Skill10_CD", p_38719_.get(4));
        SkillAssemble.putInt("Skill11_CD", p_38719_.get(5));
        SkillAssemble.putInt("Skill12_CD", p_38719_.get(6));
        SkillAssemble.putInt("Skill13_CD", p_38719_.get(7));
        SkillAssemble.putInt("Skill15_CD", p_38719_.get(8));
        SkillAssemble.putInt("Skill17_CD", p_38719_.get(9));
        SkillAssemble.putInt("Skill18_CD", p_38719_.get(10));
        SkillAssemble.putInt("Skill19_CD", p_38719_.get(11));
        SkillAssemble.putInt("Skill20_CD", p_38719_.get(12));
        SkillAssemble.putInt("Skill21_CD", p_38719_.get(13));
        SkillAssemble.putInt("Skill22_CD", p_38719_.get(14));
        SkillAssemble.putInt("Skill23_CD", p_38719_.get(15));
        SkillAssemble.putInt("Skill24_CD", p_38719_.get(16));
        SkillAssemble.putInt("Skill26_CD", p_38719_.get(17));
        SkillAssemble.putInt("Skill28_CD", p_38719_.get(18));
        SkillAssemble.putInt("Skill29_CD", p_38719_.get(19));
        SkillAssemble.putInt("Skill30_CD", p_38719_.get(20));
        SkillAssemble.putInt("Skill31_CD", p_38719_.get(21));
        SkillAssemble.putInt("Skill32_CD", p_38719_.get(22));
        SkillAssemble.putInt("Skill33_CD", p_38719_.get(23));
        SkillAssemble.putInt("Skill34_CD", p_38719_.get(24));
        SkillAssemble.putInt("Skill35_CD", p_38719_.get(25));
        SkillAssemble.putInt("Skill35_Charge", p_38719_.get(26));
        SkillAssemble.putInt("Skill37_CD", p_38719_.get(27));
        SkillAssemble.putInt("Skill39_CD", p_38719_.get(28));
        SkillAssemble.putInt("Skill40_CD", p_38719_.get(29));
        SkillAssemble.putInt("Skill41_CD", p_38719_.get(30));
        SkillAssemble.putInt("Skill42_CD", p_38719_.get(31));
        SkillAssemble.putInt("Skill43_CD", p_38719_.get(32));
        SkillAssemble.putInt("Skill44_CD", p_38719_.get(33));
        SkillAssemble.putInt("Skill45_CD", p_38719_.get(34));
        SkillAssemble.putInt("Skill46_CD", p_38719_.get(35));
        SkillAssemble.putInt("Skill48_CD", p_38719_.get(36));
        SkillAssemble.putInt("Skill50_CD", p_38719_.get(37));
        SkillAssemble.putInt("Skill51_CD", p_38719_.get(38));
        SkillAssemble.putInt("Skill52_CD", p_38719_.get(39));
        SkillAssemble.putInt("Skill53_CD", p_38719_.get(40));
        SkillAssemble.putInt("Skill54_CD", p_38719_.get(41));
        SkillAssemble.putInt("Skill55_CD", p_38719_.get(42));
        SkillAssemble.putInt("Skill56_CD", p_38719_.get(43));
        SkillAssemble.putInt("Skill57_CD", p_38719_.get(44));
        SkillAssemble.putInt("Skill59_CD", p_38719_.get(45));
        SkillAssemble.putInt("Skill61_CD", p_38719_.get(46));
        SkillAssemble.putInt("Skill62_CD", p_38719_.get(47));
        SkillAssemble.putInt("Skill63_CD", p_38719_.get(48));
        SkillAssemble.putInt("Skill64_CD", p_38719_.get(49));
        SkillAssemble.putInt("Skill65_CD", p_38719_.get(50));
        SkillAssemble.putInt("Skill66_CD", p_38719_.get(51));
        SkillAssemble.putInt("Skill68_CD", p_38719_.get(52));
        SkillAssemble.putInt("Skill69_CD", p_38719_.get(53));
        SkillAssemble.putInt("Skill70_CD", p_38719_.get(54));

        return SkillAssemble;
    }
    public void reset(){
        this.Skill6_CD = 0;
        this.Skill7_CD = 0;
        this.Skill8_CD = 0;
        this.Skill9_CD = 0;
        this.Skill10_CD = 0;
        this.Skill11_CD = 0;
        this.Skill12_CD = 0;
        this.Skill13_CD = 0;
        this.Skill15_CD = 0;
        this.Skill17_CD = 0;
        this.Skill18_CD = 0;
        this.Skill19_CD = 0;
        this.Skill20_CD = 0;
        this.Skill21_CD = 0;
        this.Skill22_CD = 0;
        this.Skill23_CD = 0;
        this.Skill24_CD = 0;
        this.Skill26_CD = 0;
        this.Skill28_CD = 0;
        this.Skill29_CD = 0;
        this.Skill30_CD = 0;
        this.Skill31_CD = 0;
        this.Skill32_CD = 0;
        this.Skill33_CD = 0;
        this.Skill34_CD = 0;
        this.Skill35_CD = 0;
        this.Skill35_Charge = 3;
        this.Skill37_CD = 0;
        this.Skill39_CD = 0;
        this.Skill40_CD = 0;
        this.Skill41_CD = 0;
        this.Skill42_CD = 0;
        this.Skill43_CD = 0;
        this.Skill44_CD = 0;
        this.Skill45_CD = 0;
        this.Skill46_CD = 0;
        this.Skill48_CD = 0;
        this.Skill50_CD = 0;
        this.Skill51_CD = 0;
        this.Skill52_CD = 0;
        this.Skill53_CD = 0;
        this.Skill54_CD = 0;
        this.Skill55_CD = 0;
        this.Skill56_CD = 0;
        this.Skill57_CD = 0;
        this.Skill61_CD = 0;
        this.Skill62_CD = 0;
        this.Skill63_CD = 0;
        this.Skill64_CD = 0;
        this.Skill65_CD = 0;
        this.Skill66_CD = 0;
        this.Skill68_CD = 0;
        this.Skill69_CD = 0;
        this.Skill70_CD = 0;
    }
    public void removeCD(){
        if (this.Skill6_CD > 0) this.Skill6_CD -= 1;
        if (this.Skill7_CD > 0) this.Skill7_CD -= 1;
        if (this.Skill8_CD > 0) this.Skill8_CD -= 1;
        if (this.Skill9_CD > 0) this.Skill9_CD -= 1;
        if (this.Skill10_CD > 0) this.Skill10_CD -= 1;
        if (this.Skill11_CD > 0) this.Skill11_CD -= 1;
        if (this.Skill12_CD > 0) this.Skill12_CD -= 1;
        if (this.Skill13_CD > 0) this.Skill13_CD -= 1;
        if (this.Skill15_CD > 0) this.Skill15_CD -= 1;
        if (this.Skill17_CD > 0) this.Skill17_CD -= 1;
        if (this.Skill18_CD > 0) this.Skill18_CD -= 1;
        if (this.Skill19_CD > 0) this.Skill19_CD -= 1;
        if (this.Skill20_CD > 0) this.Skill20_CD -= 1;
        if (this.Skill21_CD > 0) this.Skill21_CD -= 1;
        if (this.Skill22_CD > 0) this.Skill22_CD -= 1;
        if (this.Skill23_CD > 0) this.Skill23_CD -= 1;
        if (this.Skill24_CD > 0) this.Skill24_CD -= 1;
        if (this.Skill26_CD > 0) this.Skill26_CD -= 1;
        if (this.Skill28_CD > 0) this.Skill28_CD -= 1;
        if (this.Skill29_CD > 0) this.Skill29_CD -= 1;
        if (this.Skill30_CD > 0) this.Skill30_CD -= 1;
        if (this.Skill31_CD > 0) this.Skill31_CD -= 1;
        if (this.Skill32_CD > 0) this.Skill32_CD -= 1;
        if (this.Skill33_CD > 0) this.Skill33_CD -= 1;
        if (this.Skill34_CD > 0) this.Skill34_CD -= 1;
        if (this.Skill35_CD > 0) this.Skill35_CD -= 1;
        if (this.Skill37_CD > 0) this.Skill37_CD -= 1;
        if (this.Skill39_CD > 0) this.Skill39_CD -= 1;
        if (this.Skill40_CD > 0) this.Skill40_CD -= 1;
        if (this.Skill41_CD > 0) this.Skill41_CD -= 1;
        if (this.Skill42_CD > 0) this.Skill42_CD -= 1;
        if (this.Skill43_CD > 0) this.Skill43_CD -= 1;
        if (this.Skill44_CD > 0) this.Skill44_CD -= 1;
        if (this.Skill45_CD > 0) this.Skill45_CD -= 1;
        if (this.Skill46_CD > 0) this.Skill46_CD -= 1;
        if (this.Skill48_CD > 0) this.Skill48_CD -= 1;
        if (this.Skill50_CD > 0) this.Skill50_CD -= 1;
        if (this.Skill51_CD > 0) this.Skill51_CD -= 1;
        if (this.Skill52_CD > 0) this.Skill52_CD -= 1;
        if (this.Skill53_CD > 0) this.Skill53_CD -= 1;
        if (this.Skill54_CD > 0) this.Skill54_CD -= 1;
        if (this.Skill55_CD > 0) this.Skill55_CD -= 1;
        if (this.Skill56_CD > 0) this.Skill56_CD -= 1;
        if (this.Skill57_CD > 0) this.Skill57_CD -= 1;
        if (this.Skill59_CD > 0) this.Skill59_CD -= 1;
        if (this.Skill61_CD > 0) this.Skill61_CD -= 1;
        if (this.Skill62_CD > 0) this.Skill62_CD -= 1;
        if (this.Skill63_CD > 0) this.Skill63_CD -= 1;
        if (this.Skill64_CD > 0) this.Skill64_CD -= 1;
        if (this.Skill65_CD > 0) this.Skill65_CD -= 1;
        if (this.Skill66_CD > 0) this.Skill66_CD -= 1;
        if (this.Skill68_CD > 0) this.Skill68_CD -= 1;
        if (this.Skill69_CD > 0) this.Skill69_CD -= 1;
        if (this.Skill70_CD > 0) this.Skill70_CD -= 1;
    }
}
