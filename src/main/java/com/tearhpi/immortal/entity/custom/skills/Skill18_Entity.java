package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill18_Entity extends Entity {
    //status:表示领域状态;0:普通;1.凝结;2.冻结
    //public int status = 0;
    private int LIFE_TICKS = 200;
    private int life;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill18_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> status = SynchedEntityData.defineId(Skill18_Entity.class, EntityDataSerializers.INT);

    public Skill18_Entity(EntityType<? extends Skill18_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill18_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius,int LifeTicks) {
        Skill18_Entity e = new Skill18_Entity(ModEntityTypes.SKILL18_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        e.LIFE_TICKS = LifeTicks;
        level.addFreshEntity(e);
        return e;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        setRadius(tag.getFloat("radius"));
        setStatus(tag.getInt("status"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putFloat("radius", getRadius());
        tag.putInt("status",getStatus());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(radius, 0.0f);
        this.entityData.define(status, 0);
    }

    @Override
    public void tick() {
        super.tick();
        //客户端 新增粒子
        if (this.level().isClientSide) {
            Random rand = new Random();
            if (true) {
                for (int i = 0; i < 10; i++) {
                    float base = rand.nextFloat(-0.1f,getRadius());
                    float value = (float) Math.toRadians(rand.nextInt(1,360));
                    if (getStatus() == 0) {
                        level().addParticle(ModParticles.SKILL18_Particle_FOG.get(),this.getX() + base * Mth.cos(value), this.getY()+rand.nextFloat(-0.2f,0.2f), this.getZ() + base * Mth.sin(value), 0.0D, 0.0D, 0.0D);
                    } else if  (getStatus() == 1) {
                        level().addParticle(ModParticles.SKILL18_Particle_WATER.get(),this.getX() + base * Mth.cos(value), this.getY()+rand.nextFloat(-0.2f,0.2f), this.getZ() + base * Mth.sin(value), 0.0D, 0.0D, 0.0D);
                    } else if  (getStatus() == 2) {
                        level().addParticle(ModParticles.SKILL18_Particle_ICE.get(),this.getX() + base * Mth.cos(value), this.getY()+rand.nextFloat(-0.2f,0.2f), this.getZ() + base * Mth.sin(value), 0.0D, 0.0D, 0.0D);
                    } else if  (getStatus() == 3) {
                        level().addParticle(ModParticles.SKILL18_Particle_ICE.get(),this.getX() + base * Mth.cos(value), this.getY()+rand.nextFloat(-0.2f,0.2f), this.getZ() + base * Mth.sin(value), 0.0D, 0.0D, 0.0D);
                        level().addParticle(ParticleTypes.END_ROD,this.getX() + base * Mth.cos(value), this.getY()+rand.nextFloat(-0.2f,0.2f), this.getZ() + base * Mth.sin(value), 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            //凝结态 每5s一次重伤4s
            if (getStatus() == 1) {
                if (life % 100 == 0) {
                    float r = getRadius();
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                    );
                    for (Mob mob : mobs) {
                        ImposeEffect.ApplyEffectLayer(mob, _ModEffects.INJURY_EFFECT.get(),80);
                    }
                }
            }
            //冻结态:每次受水系伤害时,额外附带(水气值/3)%的真实伤害,且有5%概率施加一层侵蚀(3s)
            if (getStatus() == 2) {
                if (life % 20 == 0) {
                    float r = getRadius();
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                    );
                    for (Mob mob : mobs) {
                        ImposeEffect.ApplyEffectLayer(mob, _ModEffects.SKILL18_ADD_DMG_EFFECT.get(),30);
                    }
                }
            }
            //坚硬的冻结态:每次受水系伤害时,额外附带(水气值/2)%的真实伤害,且有15%概率施加一层侵蚀(3s)
            if (getStatus() == 3) {
                if (life % 20 == 0) {
                    float r = getRadius();
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                    );
                    for (Mob mob : mobs) {
                        ImposeEffect.ApplyEffectLayer(mob, _ModEffects.SKILL18_ADD_DMG_EFFECT_Spec.get(),30);
                    }
                }
            }
            if (++life >= LIFE_TICKS) {
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
    public int getStatus() { return entityData.get(status); }
    public void setStatus(int v) { entityData.set(status,v); }

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
