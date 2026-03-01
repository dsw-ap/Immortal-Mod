package com.tearhpi.immortal.entity.capability;

import net.minecraft.world.entity.LivingEntity;

public interface IImmortalPlayer {
    public Coin getCoin();
    public ManaPoint getManaPoint();
    public Skill getSkill();
    public SkillAssemble getSkillAssemble();
    public SkillCD getSkillCD();
    public void setExtraDamage(float extraDamage);
    public float getExtraDamage();
    public SkillInUse getSkillInUse();
    public Shield getShield();
    public void setShield(Shield shield);
    public BuffAmount getBuffAmount();
    public void setBuffAmount(BuffAmount buffAmount);
    public Chatbox getChatbox();
    public void setChatbox(Chatbox chatbox);
    public float getChatboxPT();
    public void setChatboxPT(float chatboxPT);
    public Setting getSetting();
    public void setSetting(Setting setting);
    public Achievement getAchievement();
    public void setAchievement(Achievement achievement);
    public MainTask getMainTask();
    public void setMainTask(int i);
    public NormalAttackEnergy getNormalAttackEnergy();

    private LivingEntity self() {
        return (LivingEntity)this;
    }
}
