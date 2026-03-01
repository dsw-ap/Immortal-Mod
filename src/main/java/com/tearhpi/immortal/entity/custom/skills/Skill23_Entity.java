package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom.Utils.TimeUuidPair;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill23_Entity extends Entity {
    private static final int LIFE_TICKS = 40; // 1s
    private int life;
    private _ImmortalMob Origin;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill23_Entity.class, EntityDataSerializers.FLOAT); // 剩余
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);


    public Skill23_Entity(EntityType<? extends Skill23_Entity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    // 便捷创建
    public static Skill23_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius, _ImmortalMob mob) {
        Skill23_Entity e = new Skill23_Entity(ModEntityTypes.SKILL23_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.hasImpulse = true;
        e.Origin = mob;
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
        //客户端
        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);
        if (this.level().isClientSide) {
            //生成粒子
            level().addParticle(ModParticles.WATER.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            Random rand = new Random();
            level().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f));
            level().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f));
            level().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f));
        }
        //服务端 运行逻辑
        if (!this.level().isClientSide) {
            double r_ = 1.0;
            // 搜索指定范围内的所有 Mob
            List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                    _ImmortalMob.class, this.getBoundingBox().inflate(r_), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r_ * r_ && mob != this.Origin
            );

            if (!mobs.isEmpty()) {
                _ImmortalMob mob = mobs.get(0);
                ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,getOwner());
                mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 1.0f));
                if (!mob.hasSkill23() && getOwner() instanceof ServerPlayer) {
                    mob.setTimeUuidPair(new TimeUuidPair(60,getOwner().getUUID()));
                }
                this.discard();
            }
        }
        if (isInsideSolid()){
            this.discard();
        }
        // 寿命
        if (++life >= LIFE_TICKS) {
            this.discard();
        }
    }

    @Nullable
    public ServerPlayer getOwner() {
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
