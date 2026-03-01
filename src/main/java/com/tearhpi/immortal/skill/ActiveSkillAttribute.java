package com.tearhpi.immortal.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ActiveSkillAttribute extends PassiveSkillAttribute {
    //This class define the attributes of an ACTIVE SKILL
    public int[] skillCD;
    public int[] skillSP;

    public ActiveSkillAttribute(int skillId, PassiveSkillAttribute preconditionId, ResourceLocation skillIcon, Component skillName, Component skillIntro, Component skillInfo, int maxTier, int[] skillCD, int[] skillSP) {
        super(skillId,preconditionId,skillIcon,skillName,skillIntro,skillInfo,maxTier);
        this.skillCD = skillCD;
        this.skillSP = skillSP;
    }
    public ActiveSkillAttribute(int skillId, ResourceLocation skillIcon, Component skillName, Component skillIntro, Component skillInfo, int maxTier, int[] skillCD, int[] skillSP) {
        super(skillId,skillIcon,skillName,skillIntro,skillInfo,maxTier);
        this.skillCD = skillCD;
        this.skillSP = skillSP;
    }
}
