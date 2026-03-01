package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.Entity_Utils;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill37_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 161;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill37_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill37_Entity.class, EntityDataSerializers.INT);
    private List<_ImmortalMob> mobsHaveBeenAttacked = new ArrayList<>();

    public Skill37_Entity(EntityType<? extends Skill37_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill37_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill37_Entity e = new Skill37_Entity(ModEntityTypes.SKILL37_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        level.addFreshEntity(e);
        return e;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        setLife(tag.getInt("life"));
        setRadius(tag.getFloat("radius"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", getLife());
        tag.putFloat("radius", getRadius());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(radius, 0.0f);
        this.entityData.define(life, 0);
    }

    @Override
    public void tick() {
        super.tick();
        //System.out.println(Mth.cos((float) Math.toRadians(getLife()*4.5)));
        Vec3 mot = this.getDeltaMovement().scale(Mth.cos((float) Math.toRadians(getLife()*1.5)));
        this.move(MoverType.SELF, mot);
        //客户端
        if (this.level().isClientSide) {

        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            Random random1 = new Random();
            if (getLife() % 20 == 0) {
                double r = getRadius();
                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= r * r
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob),2f));
                    Entity_Utils.moveToward(mob,this,0.1);

                    if (random1.nextInt(1,10) <= 5) {
                        DamageSource damagesource_ = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_REAL,getOwner());
                        mob.hurt(damagesource_, mob.getHealth()/20);
                    }
                    if (random1.nextInt(1,40) <= 5) {
                        mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob),4.5f));
                    }
                    Vec3 pos = mob.position();
                    ((ServerLevel) level()).sendParticles(ModParticles.AIR.get(),pos.x,pos.y,pos.z,10,1,2,1,0.05);
                }
            }

            addLife();
            if (getLife() >= LIFE_TICKS) {
                //爆炸
                Random rand_ = new Random();
                for (int i = 0; i < 200; i++) {
                    double rot = rand_.nextInt(1,360)*2*Mth.PI/360;
                    int scale = rand_.nextInt(1,(int) getRadius());
                    ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,this.getX()+scale*Mth.cos((float) rot),this.getY()-2,this.getZ()+scale*Mth.sin((float) rot),1,0,0,0,0);
                }
                final double R = getRadius();

                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(R), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= R * R
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob),6.5f));
                    Entity_Utils.moveToward(mob,this,0.5);
                }
                this.discard();
            }
            if (this.isInsideSolid()) {
                this.discard();
            }
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
    public int getLife() { return entityData.get(life); }
    public void setLife(int r) { entityData.set(life,r); }
    public void addLife() { entityData.set(life,getLife()+1); }

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
