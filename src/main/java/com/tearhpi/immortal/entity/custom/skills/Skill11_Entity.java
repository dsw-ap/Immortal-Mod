package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.tearhpi.immortal.util.statics.ExplosionParticle.horizonParticleExpServerLevel;

public class Skill11_Entity extends Entity {
    private static final int LIFE_TICKS = 200; // 10s
    private int life;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill11_Entity.class, EntityDataSerializers.FLOAT); // 剩余
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);

    public Skill11_Entity(EntityType<? extends Skill11_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill11_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill11_Entity e = new Skill11_Entity(ModEntityTypes.SKILL11_ENTITY.get(), level);
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
        setRadius(tag.getFloat("radius"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putFloat("radius", getRadius());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(radius, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        //客户端 新增粒子
        if (this.level().isClientSide) {
            Random rand = new Random();
            Vec3 pos = this.position();
            for (int i = 0; i < 10; i++) {
                float range = rand.nextFloat(0.0f,getRadius());
                int rot = rand.nextInt(360);
                float dx = range * Mth.cos((float) Math.toRadians(rot));
                float dz = range * Mth.sin((float) Math.toRadians(rot));
                level().addParticle(ParticleTypes.ENCHANT,pos.x+dx,pos.y+rand.nextFloat(0.0f,2.0f),pos.z+dz,rand.nextFloat(-0.5f,0.5f),rand.nextFloat(-0.5f,0.5f),rand.nextFloat(-0.5f,0.5f));
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (this.life % 5 == 0) {
                final double R = getRadius();
                var box = this.getBoundingBox().inflate(R);
                var mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, box,
                        m -> m.isAlive() && m.isAttractable() && m.distanceToSqr(this) <= R * R
                );
                Collections.shuffle(mobs, new Random());
                if (mobs.size() > 3) {
                    mobs = mobs.subList(0, 3);
                }
                for (_ImmortalMob m : mobs) {
                    double r_ = 4.0d;
                    var box_ = m.getBoundingBox().inflate(r_);
                    var mobs_ = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, box_,
                            m_ -> m_.isAlive() && m_.isAttractable() && m_.distanceToSqr(m) <= r_ * r_
                    );
                    //((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,m.getX(),m.getY(),m.getZ(),1,0,0,0,0);
                    horizonParticleExpServerLevel((ServerLevel) level(), ParticleTypes.FLAME,1,36,m.position());
                    for (_ImmortalMob m2 : mobs_) {
                        ModDamageSources modDamageSources = new ModDamageSources(m2.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE, getOwner());
                        m2.hurt(damagesource, MainDamage.getDamage(getOwner(), damagesource, OutPutDamageInfo.getOutPutDamageInfo(m2), 0.5f));
                        Random rand = new Random();
                        float offx = rand.nextFloat();
                        float offy = rand.nextFloat();
                        float offz = rand.nextFloat();
                        ((ServerLevel) level()).sendParticles(ParticleTypes.FLAME, m.getX(), m.getY(), m.getZ(), 10, offx, offy, offz, 0.15);
                        ((ServerLevel) level()).sendParticles(ParticleTypes.LAVA, m.getX(), m.getY(), m.getZ(), 10, offx, offy, offz, 0.15);
                    }
                }
            }
            if (++life >= LIFE_TICKS) {
                Random rand = new Random();
                for (int i = 0; i < 100; i++) {
                    double rot = rand.nextInt(1,360)*2*Mth.PI/360;
                    int scale = rand.nextInt(1,20);
                    ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,this.getX()+scale*Mth.cos((float) rot),this.getY(),this.getZ()+scale*Mth.sin((float) rot),1,0,0,0,0);
                }
                final double R = getRadius();

                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(R), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= R * R
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 5.0f));
                    ImposeEffect.ApplyEffectLayer(mob, _ModEffects.ANNIHILATE_EFFECT.get(), 200);
                }
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

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
