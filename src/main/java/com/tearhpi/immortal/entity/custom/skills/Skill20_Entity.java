package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill20_Entity extends Entity {
    private static final int LIFE_TICKS = 40;
    private int life;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill20_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life_ = SynchedEntityData.defineId(Skill20_Entity.class, EntityDataSerializers.INT);

    private List<_ImmortalMob> mobsHaveBeenAttacked = new ArrayList<>();

    public Skill20_Entity(EntityType<? extends Skill20_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill20_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill20_Entity e = new Skill20_Entity(ModEntityTypes.SKILL20_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        level.addFreshEntity(e);
        return e;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        setLife(tag.getInt("life_"));
        setRadius(tag.getFloat("radius"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putFloat("radius", getRadius());
        tag.putInt("life_",getLife());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(radius, 0.0f);
        this.entityData.define(life_, 0);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);
        //客户端 新增粒子
        if (this.level().isClientSide) {
            Vec3 dir3D = this.getDeltaMovement().cross(new Vec3(0.0f,1.0f,0.0f)).normalize(); // -90°
            for(int i = -1;i<getLife()*getLife()/40;i++){
                Random rand = new Random();
                level().addParticle(ModParticles.WATER.get(), this.getX() + rand.nextFloat(-0.5f,0.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() + rand.nextFloat(-0.5f,0.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() + rand.nextFloat(0.5f,1.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() + rand.nextFloat(0.5f,1.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() + rand.nextFloat(1.5f,2.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() + rand.nextFloat(1.5f,2.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() - rand.nextFloat(0.5f,1.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() - rand.nextFloat(0.5f,1.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() - rand.nextFloat(1.5f,2.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() - rand.nextFloat(1.5f,2.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            double r = Math.min(0.75, (double) getLife() /LIFE_TICKS)*2.5;
            // 搜索指定范围内的所有 Mob
            if (life % 1 == 0) {
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive()
                );
                for (_ImmortalMob mob : mobs ) {
                    if (!mobsHaveBeenAttacked.contains(mob)) {
                        //伤害
                        ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,getOwner());
                        mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 2.0f));
                        //
                        mobsHaveBeenAttacked.add(mob);
                        //外推
                        // 向量：从玩家指向mob
                        if (mob.isAttractable()){
                            double rInv = 1.0 / r;
                            double maxStrength = 1.0;
                            double verticalBoost = 0.3;
                            Vec3 toMob = mob.position().subtract(getOwner().position());
                            double dist = toMob.length();
                            //if (dist < 1.0e-6 || dist > r) continue;

                            //---强度计算---
                            //线性衰减
                            double falloff = 1.0; //- dist * rInv;
                            //抗击退修正
                            double resist = 1.0;
                            var inst = mob.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                            if (inst != null) {
                                // vanilla里该属性越大越“不容易被击退”
                                resist = 1.0 - Mth.clamp(inst.getValue(), 0.0, 1.0);
                            }
                            //计算最终强度
                            double strength = maxStrength * falloff * resist;
                            Vec3 dir = toMob.scale(1.0 / dist);
                            Vec3 impulse = new Vec3(dir.x * strength, dir.y * strength + (verticalBoost * falloff), dir.z * strength);
                            //System.out.println("TEST:"+impulse);
                            mob.setDeltaMovement(mob.getDeltaMovement().add(impulse));
                            mob.hasImpulse = true;
                            mob.hurtMarked = true;
                        }
                        ImposeEffect.ImposeEffectWithoutAmp(mob,_ModEffects.IMPRISIONED_EFFECT.get(),100);
                    }
                }
            }

            addLife();
            if (getLife() >= LIFE_TICKS) {
                this.discard();
            }
            if (this.isInsideSolid()) {
                this.discard();
            }
        }
    }

    @Nullable
    private ServerPlayer getOwner() {
        if (this.ownerId == null) return null;
        if (!(this.level() instanceof ServerLevel sl)) return null;
        return sl.getServer().getPlayerList().getPlayer(this.ownerId);
    }

    public float getRadius() { return entityData.get(radius); }
    public void setRadius(float r) { entityData.set(radius,r); }
    public int getLife() { return entityData.get(life_); }
    public void setLife(int r) { entityData.set(life_,r); }
    public void addLife() { entityData.set(life_,getLife()+1); }

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
