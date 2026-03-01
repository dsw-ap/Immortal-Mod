package com.tearhpi.immortal.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PassiveSkillAttribute {
    //This class define the attributes of a PASSIVE SKILL
    public int skillId;
    public PassiveSkillAttribute preconditionId;
    public ResourceLocation skillIcon;
    public Component skillName;
    public Component skillIntro;
    public Component skillInfo;
    public int maxTier;

    public PassiveSkillAttribute(int skillId,PassiveSkillAttribute preconditionId, ResourceLocation skillIcon, Component skillName, Component skillIntro, Component skillInfo, int maxTier) {
        this.skillId = skillId;
        this.preconditionId = preconditionId;
        this.skillIcon = skillIcon;
        this.skillName = skillName;
        this.skillIntro = skillIntro;
        this.skillInfo = skillInfo;
        this.maxTier = maxTier;
    }
    public PassiveSkillAttribute(int skillId, ResourceLocation skillIcon, Component skillName, Component skillIntro, Component skillInfo, int maxTier) {
        this.skillId = skillId;
        this.preconditionId = null;
        this.skillIcon = skillIcon;
        this.skillName = skillName;
        this.skillIntro = skillIntro;
        this.skillInfo = skillInfo;
        this.maxTier = maxTier;
    }
}
