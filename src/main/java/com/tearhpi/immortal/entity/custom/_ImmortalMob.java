package com.tearhpi.immortal.entity.custom;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.custom.Utils.TimeUuidPair;
import com.tearhpi.immortal.entity.custom.skills.Skill22_Entity;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.skill.behaviour.active.fire.Skill23;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.Entity_Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

import static com.tearhpi.immortal.event.skill.Passive.stableUuid;
import static com.tearhpi.immortal.util.statics.ExplosionParticle.horizonParticleExpServerLevel;
import static com.tearhpi.immortal.util.statics.StaticParticle.spawnEdgeCage;

public class _ImmortalMob extends PathfinderMob {
    public boolean showHealth = true;
    public boolean Attractable = true;
    public TimeUuidPair TimeUuidPair = new TimeUuidPair(-1,null);
    private int life_count;
    private static final EntityDataAccessor<Integer> SkillBar_Amount = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SkillBar_Max = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> hasDebuff = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> skill8PreLasting = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> waterAmount = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill18_w_Time = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill33_BeAtkedPerSec = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill46_Time = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill46_Level = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill54_Amount = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill62_Amount = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Skill69_Time = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> Skill69_Amount = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.FLOAT);
    //private static final EntityDataAccessor<Optional<UUID>> Skill46Attacker = SynchedEntityData.defineId(_ImmortalMob.class, EntityDataSerializers.OPTIONAL_UUID);
    private UUID Skill46Attacker;


    protected _ImmortalMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.setskill8PreLasting(0);
        this.Attractable = true;
        this.setPersistenceRequired(); // 不会被自然移除
        this.setNoGravity(false);
        this.noPhysics = false;
        this.life_count = 0;
        this.Skill46Attacker = null;
    }
    @Override
    public void tick() {
        super.tick();
        //float hp = this.getHealth();
        //float maxHp = this.getMaxHealth();
        //this.setCustomName(Component.literal(" "));

        //System.out.println(getHasDebuff());
        if (this.level().isClientSide()){
            //skill8相关配置
            int s8pl = getskill8PreLasting();
            if (s8pl > 0){
                if (s8pl % 15 == 0){
                    //灼烧特效
                    level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY()+1, this.getZ(), 0.0D, 0.0D, 0.0D);
                    level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY()+1, this.getZ(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
        if (!this.level().isClientSide()){
            setHasDebuff(hasDebuff());
            //生命计时
            life_count++;
            //移动
            // 1) 应用重力（无反重力效果时）
            if (!this.isNoGravity()) {
                setDeltaMovement(getDeltaMovement().add(0.0, -0.08, 0.0)); // 原版重力常数
            }
            // 2) 先移动
            Vec3 v = getDeltaMovement();
            if (v.lengthSqr() > 1.0E-7) {
                this.move(MoverType.SELF, v);
                this.hasImpulse = true;
            }
            // 3) 空气阻力/地面摩擦
            float groundFriction = this.onGround() ? 0.91F * this.level().getBlockState(blockPosition().below())
                    .getBlock().getFriction() : 0.91F;
            setDeltaMovement(getDeltaMovement().multiply(groundFriction, 0.98, groundFriction));
            //skill8相关配置
            int s8pl = getskill8PreLasting();
            if (s8pl > 0){
                if (s8pl % 15 == 0){
                    //灼烧
                    ImposeEffect.ApplyEffectLayer(this, _ModEffects.FIRING_EFFECT.get(),80);
                }
                setskill8PreLasting(s8pl - 1);
            }
            //水系-水气值相关配置
            if (life_count % 20 == 0){
                this.addWaterAmount(-1);
                if (this.getskill18_w_Time() > 1){
                    //判断逻辑
                    //施加缓慢(2s一次,1层5s)
                    if (life_count % 40 == 0) {
                        ImposeEffect.ApplyEffectLayer(this, MobEffects.MOVEMENT_SLOWDOWN,100);
                        //20%概率禁锢
                        Random rand = new Random();
                        int randomVal = rand.nextInt(1,5);
                        if (randomVal == 1){
                            ImposeEffect.ImposeEffectWithoutAmp(this, _ModEffects.IMPRISIONED_EFFECT.get(),60);
                        }
                        //50%概率侵蚀 被强化则100
                        if (!Entity_Utils.isEntityTypeWithinDistance(this, Skill22_Entity.class,20)){
                            ImposeEffect.ImposeEffectWithoutAmp(this, _ModEffects.EROSION_EFFECT.get(),60);
                        } else {
                            int randomVal_ = rand.nextInt(1,2);
                            if (randomVal_ == 1){
                                ImposeEffect.ImposeEffectWithoutAmp(this, _ModEffects.EROSION_EFFECT.get(),60);
                            }
                        }
                    }
                    //减少水量
                    if (!Entity_Utils.isEntityTypeWithinDistance(this, Skill22_Entity.class,20)){
                        this.addWaterAmount(-3);
                    } else {
                        this.addWaterAmount(-2);
                    }

                    this.removeskill18_w_Time();
                    //水量为0直接提前中断
                    if (this.getWaterAmount() == 0){
                        this.setskill18_w_Time(0);
                    }
                }
            }
            //Skill23相关配置
            if (hasSkill23()){
                //粒子
                Vec3 center = this.position();
                for(int i = 0; i < 15; i++){
                    double step = 2*Mth.PI/15;
                    ServerLevel level = (ServerLevel) level();
                    level.sendParticles(ModParticles.SKILL22_Particle.get(),center.x+Mth.cos((float) (step*i))*2,center.y+1,center.z+Mth.sin((float) (step*i))*2,1,0,0,0,0);
                }
                //减速
                ImposeEffect.ImposeLayer(this,MobEffects.MOVEMENT_SLOWDOWN,20,1);
                //时间减少
                TimeUuidPair timeUuidPair = this.getTimeUuidPair();
                if (timeUuidPair.getTime()-1 > 0){
                    this.setTimeUuidPair(new TimeUuidPair(timeUuidPair.getTime()-1,timeUuidPair.getUuid()));
                } else {
                    //爆发
                    //1.增加水汽
                    this.addWaterAmount(20);
                    //2.禁锢
                    ImposeEffect.ImposeEffectWithoutAmp(this,_ModEffects.IMPRISIONED_EFFECT.get(), 60);
                    //3.传导
                    if (this.TimeUuidPair.getUuid() == null);
                    if (!(this.level() instanceof ServerLevel sl)) {
                    } else {
                        ServerPlayer player = sl.getServer().getPlayerList().getPlayer(this.TimeUuidPair.getUuid());
                        if (player != null) {
                            Skill23.Summon(this,level(),player,1);
                        }
                        //4.伤害
                        final double R = 3.0;
                        List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                                _ImmortalMob.class, this.getBoundingBox().inflate(R), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= R * R
                        );
                        for (_ImmortalMob mob : mobs_) {
                            ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
                            mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3.0f));
                        }
                    }
                    this.setTimeUuidPair(new TimeUuidPair(-1,null));
                }
            }
            //Skill33相关配置
            if (life_count % 20 == 0){
                setskill33(0);
            }
            //Skill46相关配置
            if (getskill46Time() > 0){
                spawnEdgeCage((ServerLevel) this.level(),this.getBoundingBox(),ModParticles.EARTH.get(),0.5,1);
                ImposeEffect.ImposeEffectWithoutAmp(this,_ModEffects.IMPRISIONED_EFFECT.get(), 20);
                ImposeEffect.ImposeEffectWithoutAmp(this,_ModEffects.INJURY_EFFECT.get(), 20);

                setskill46Time(getskill46Time()-1);
                if (getskill46Time() == 0){
                    //触发爆炸
                    double r = 3.0f;
                    // 搜索指定范围内的所有 Mob
                    horizonParticleExpServerLevel((ServerLevel) this.level(),ModParticles.EARTH.get(),0.2,36,this.position().add(new Vec3(0.0,1.0,0.0)));
                            //(ServerLevel level, SimpleParticleType particle, double speed, int count, Vec3 center){
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive()
                    );
                    for (_ImmortalMob mob : mobs) {
                        Player player = getskill46Atker();
                        if (player != null){
                            ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH,player);
                            mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 4F));
                        }
                    }
                    setskill46Atker(null);
                }
            }
            //Skill54相关设置
            if (true) {
                ResourceLocation Skill_54_LE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_54_le");
                ResourceLocation Skill_54_LE_DEF = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "skill_54_le_def");
                boolean skill54 = this.hasEffect(_ModEffects.SKILL54_LIGHT_EROSION.get());
                multiplyAttrWithConditionWithLevel(this,1, ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(),Skill_54_LE,-getskill54Amount()/100d,skill54);
                multiplyAttrWithConditionWithLevel(this,1, ModAttributes.IMMORTAL_DEFENSE.get(),Skill_54_LE_DEF,-getskill54Amount()/100d,skill54);
            }
            //Skill62相关设置
            if (getskill62Amount() > 0 && life_count % 10 == 0) {
                Collection<MobEffectInstance> effects = this.getActiveEffects();
                if (effects.isEmpty()) return;
                List<MobEffectInstance> toUpdate = new ArrayList<>();
                for (MobEffectInstance inst : effects) {
                    MobEffect effect = inst.getEffect();
                    if (effect.getCategory() != MobEffectCategory.HARMFUL) continue;
                    ResourceLocation id = this.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.MOB_EFFECT).getKey(effect);
                    if (id == null) continue;
                    int dur = inst.getDuration();
                    //进入阈值区间（<=20tick）且还没触发过:消耗能量并延长
                    if (dur > 0 && dur <= 20) {
                        int dark = this.getskill62Amount();
                        this.setskill62Amount(dark - 1);
                        MobEffectInstance newInst = new MobEffectInstance(effect, dur + 60, inst.getAmplifier(), inst.isAmbient(), inst.isVisible(), inst.showIcon());
                        toUpdate.add(newInst);
                    }
                }
                for (MobEffectInstance ni : toUpdate) {
                    this.addEffect(ni);
                }
            }
            //Skill63相关设置
            if (this.hasEffect(_ModEffects.SKILL63_CHAOS_CATALYST.get())) {
                MobEffectInstance effectInstance = this.getEffect(_ModEffects.SKILL63_CHAOS_CATALYST.get());
                if ((effectInstance.getDuration()-1) % 20 == 0) {
                    Collection<MobEffectInstance> effects = this.getActiveEffects();
                    List<MobEffectInstance> toUpdate = new ArrayList<>();
                    for (MobEffectInstance inst : effects) {
                        MobEffect effect = inst.getEffect();
                        if (effect.getCategory() != MobEffectCategory.HARMFUL) continue;
                        if (effect.equals(effectInstance.getEffect())) continue;
                        ResourceLocation id = this.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.MOB_EFFECT).getKey(effect);
                        if (id == null) continue;
                        int dur = inst.getDuration();
                        if (true) {
                            Random rand = new Random();
                            MobEffectInstance newInst;
                            if (rand.nextInt(1, 100) < 40) {
                                newInst = new MobEffectInstance(effect, dur + 40, inst.getAmplifier() + 1, inst.isAmbient(), inst.isVisible(), inst.showIcon());
                            } else {
                                newInst = new MobEffectInstance(effect, dur + 40, inst.getAmplifier(), inst.isAmbient(), inst.isVisible(), inst.showIcon());
                            }

                            toUpdate.add(newInst);
                        }
                    }
                    for (MobEffectInstance ni : toUpdate) {
                        this.addEffect(ni);
                    }
                }
            }
            //Skill66相关设置
            if (this.hasEffect(_ModEffects.SKILL66_DEBUFF.get())) {
                MobEffectInstance effectInstance = this.getEffect(_ModEffects.SKILL66_DEBUFF.get());
                if (this.life_count % 20 == 0) {
                    Collection<MobEffectInstance> effects = this.getActiveEffects();
                    List<MobEffectInstance> toUpdate = new ArrayList<>();
                    for (MobEffectInstance inst : effects) {
                            MobEffect effect = inst.getEffect();
                            if (effect.getCategory() != MobEffectCategory.HARMFUL) continue;
                            if (effect.equals(effectInstance.getEffect())) continue;
                            ResourceLocation id = this.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.MOB_EFFECT).getKey(effect);
                            if (id == null) continue;
                            int dur = inst.getDuration();
                            MobEffectInstance newInst = new MobEffectInstance(effect, dur + 40, inst.getAmplifier(), inst.isAmbient(), inst.isVisible(), inst.showIcon());
                            toUpdate.add(newInst);
                    }
                    for (MobEffectInstance ni : toUpdate) {
                        this.addEffect(ni);
                    }
                }
            }
            //Skill69相关设置
            if (getskill69Time() > 0) {
                Vec3 pos = this.position();
                ((ServerLevel) level()).sendParticles(ParticleTypes.SMOKE,pos.x,pos.y,pos.z,10,0.5,1,0.5,0);
                setskill69Time(getskill69Time() - 1);

                if (getskill69Time() == 0) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_NONE);
                    this.hurt(damagesource,getskill69_Amount());
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION,pos.x,pos.y+1,pos.z,5,0.1,0.2,0.1,0);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SkillBar_Amount,0);
        this.entityData.define(SkillBar_Max,0);
        this.entityData.define(hasDebuff,0);
        this.entityData.define(skill8PreLasting, 0);
        this.entityData.define(waterAmount, 0);
        this.entityData.define(Skill18_w_Time, 0);
        this.entityData.define(Skill33_BeAtkedPerSec, 0);
        this.entityData.define(Skill46_Time, 0);
        this.entityData.define(Skill46_Level, 0);
        this.entityData.define(Skill54_Amount, 0);
        this.entityData.define(Skill62_Amount, 0);
        this.entityData.define(Skill69_Amount, 0f);
        this.entityData.define(Skill69_Time, 0);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.life_count = tag.getInt("life_count");
        if (tag.contains("time_uuid_pair_time")){
            this.TimeUuidPair.time = tag.getLong("time_uuid_pair_time");
            this.TimeUuidPair.uuid = tag.getUUID("time_uuid_pair_uuid");
        }
        setSkillBarAmount(tag.getInt("skill_bar_amount"));
        setSkillBarMax(tag.getInt("skill_bar_max"));
        setHasDebuff(tag.getInt("hasDebuff"));
        setskill8PreLasting(tag.getInt("skill8PreLasting"));
        setWaterAmount(tag.getInt("waterAmount"));
        setskill18_w_Time(tag.getInt("skill18_w_Time"));
        setskill33(tag.getInt("skill33"));
        setskill46Level(tag.getInt("skill46Level"));
        setskill46Time(tag.getInt("skill46Time"));
        if (tag.hasUUID("skill46atker")) setskill46Atker(tag.getUUID("skill46atker"));
        setskill46Time(tag.getInt("skill46Time"));
        setskill54Amount(tag.getInt("skill54Amount"));
        setskill62Amount(tag.getInt("skill62Amount"));
        setskill69_Amount(tag.getInt("skill69_Amount"));
        setskill69Time(tag.getInt("skill69_Time"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("life_count",life_count);
        if (this.TimeUuidPair.uuid != null){
            tag.putLong("time_uuid_pair_time", TimeUuidPair.time);
            tag.putUUID("time_uuid_pair_uuid",TimeUuidPair.uuid);
        }
        tag.putInt("skill_bar_amount",getSkillBarAmount());
        tag.putInt("skill_bar_max",getSkillBarMax());
        tag.putInt("hasDebuff",getHasDebuff());
        tag.putInt("skill8PreLasting", getskill8PreLasting());
        tag.putInt("waterAmount", getWaterAmount());
        tag.putInt("skill18_w_Time", getskill18_w_Time());
        tag.putInt("skill33", getskill33());
        tag.putInt("skill46Level", getskill46Level());
        tag.putInt("skill46Time", getskill46Time());
        if (this.Skill46Attacker != null) tag.putUUID("skill46atker", getskill46Atker().getUUID());
        tag.putInt("skill54Amount", getskill54Amount());
        tag.putFloat("skill69_Amount", getskill69_Amount());
        tag.putInt("skill69Tick", getskill69Time());
    }

    public boolean isAttractable() {
        return Attractable;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return List.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
    }

    public int getHasDebuff() { return entityData.get(hasDebuff); }
    public void setHasDebuff(int value) {
        entityData.set(hasDebuff,value);
    }
    public int getSkillBarAmount() { return entityData.get(SkillBar_Amount); }
    public void setSkillBarAmount(int value) {
        entityData.set(SkillBar_Amount,value);
    }
    public int getSkillBarMax() { return entityData.get(SkillBar_Max); }
    public void setSkillBarMax(int value) {
        entityData.set(SkillBar_Max,value);
    }
    public int getskill8PreLasting() { return entityData.get(skill8PreLasting); }
    public void setskill8PreLasting(int value) { entityData.set(skill8PreLasting,value); }
    public int getWaterAmount() { return entityData.get(waterAmount); }
    public void setWaterAmount(int value) {
        entityData.set(waterAmount,Math.max(Math.min(value,100),0));
    }
    public void addWaterAmount(int value) {
        setWaterAmount(getWaterAmount() + value);
    }

    public int getskill18_w_Time() { return entityData.get(Skill18_w_Time); }
    public void setskill18_w_Time(int value) { entityData.set(Skill18_w_Time,value); }
    public void removeskill18_w_Time() { entityData.set(Skill18_w_Time,getskill18_w_Time()-1); }

    public int getskill33() { return entityData.get(Skill33_BeAtkedPerSec); }
    public void setskill33(int value) { entityData.set(Skill33_BeAtkedPerSec,value); }
    public void addskill33() { entityData.set(Skill33_BeAtkedPerSec,getskill33()+1); }

    public int getskill46Time() { return entityData.get(Skill46_Time); }
    public void setskill46Time(int value) { entityData.set(Skill46_Time,value); }
    public void addskill46Time() { entityData.set(Skill46_Time,getskill46Time()+1); }

    public int getskill46Level() { return entityData.get(Skill46_Level); }
    public void setskill46Level(int value) { entityData.set(Skill46_Level,value); }
    public void addskill46Level() { entityData.set(Skill46_Level,getskill46Level()+1); }

    public ServerPlayer getskill46Atker() {
        if (this.Skill46Attacker == null) return null;
        if (!(this.level() instanceof ServerLevel sl)) return null;
        //return sl.getServer().getPlayerList().getPlayer(entityData.get(Skill46Attacker).get());
        return sl.getServer().getPlayerList().getPlayer(Skill46Attacker);
    }
    public void setskill46Atker(UUID uuid) {
        //entityData.set(Skill46Attacker,java.util.Optional.of(uuid));
        Skill46Attacker = uuid;
        //System.out.println(Skill46Attacker+"TEST");
    }

    public int getskill54Amount() { return entityData.get(Skill54_Amount); }
    public void setskill54Amount(int value) { entityData.set(Skill54_Amount,value); }

    public int getskill62Amount() { return entityData.get(Skill62_Amount); }
    public void setskill62Amount(int value) { entityData.set(Skill62_Amount,value); }

    public int getskill69Time() { return entityData.get(Skill69_Time); }
    public void setskill69Time(int value) { entityData.set(Skill69_Time,value); }

    public float getskill69_Amount() { return entityData.get(Skill69_Amount); }
    public void setskill69_Amount(float value) { entityData.set(Skill69_Amount,value); }

    public void setTimeUuidPair(TimeUuidPair timeUuidPair) {
        TimeUuidPair = timeUuidPair;
    }
    public TimeUuidPair getTimeUuidPair() { return TimeUuidPair; }
    public void resetTimeUuidPair() {
        TimeUuidPair = new TimeUuidPair(-1,null);
    }
    public boolean hasSkill23() {
        return (!(this.getTimeUuidPair().getUuid() == null));
    }

    private static void multiplyAttrWithConditionWithLevel(_ImmortalMob mob, int Level, Attribute attribute, ResourceLocation modifierId, double Amount, Boolean bool) {
        AttributeInstance inst = mob.getAttribute(attribute);
        if (inst == null) return;
        UUID uuid = stableUuid(modifierId);
        AttributeModifier existing = inst.getModifier(uuid);
        if (bool) {
            if (existing == null) {
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount*Level, AttributeModifier.Operation.MULTIPLY_BASE));
            } else if (existing.getAmount() != Amount) {
                inst.removeModifier(uuid);
                inst.addTransientModifier(new AttributeModifier(uuid, modifierId.toString(), Amount*Level, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        } else {
            if (existing != null) {
                inst.removeModifier(uuid);
            }
        }
    }
    public int hasDebuff() {
        //System.out.println("test"+effects.size());
        int return_value = 0;
        if (this.hasEffect(_ModEffects.FIRING_EFFECT.get())) return_value += 1;
        if (this.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) return_value += 2;
        if (this.hasEffect(_ModEffects.IMPRISIONED_EFFECT.get())) return_value += 4;
        if (this.hasEffect(_ModEffects.ANNIHILATE_EFFECT.get())) return_value += 8;
        if (this.hasEffect(_ModEffects.EROSION_EFFECT.get())) return_value += 16;
        if (this.hasEffect(_ModEffects.INJURY_EFFECT.get())) return_value += 32;
        if (this.hasEffect(_ModEffects.MUTE_EFFECT.get())) return_value += 64;
        if (this.hasEffect(MobEffects.LEVITATION)) return_value += 128;
        if (this.hasEffect(_ModEffects.NIGHTMARE_EFFECT.get())) return_value += 256;
        return return_value;
    }
}
