package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill50_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 60;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill50_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill50_Entity.class, EntityDataSerializers.INT);


    public Skill50_Entity(EntityType<? extends Skill50_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建

    public static Skill50_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill50_Entity e = new Skill50_Entity(ModEntityTypes.SKILL50_ENTITY.get(), level);
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
        Vec3 mot = new Vec3(0.0,-1.0,0.0);
        this.move(MoverType.SELF, mot);
        //客户端
        if (this.level().isClientSide) {
            for (int i = 0;i < 10;i++) {
                Random rand = new Random();
                Vec3 pos = this.position();
                double rot = rand.nextInt(1,360) * Math.PI * 2.0D;
                level().addParticle(ModParticles.LIGHT.get(), pos.x+0.5*Mth.cos((float) rot), pos.y+rand.nextDouble(-0.5,0.5), pos.z+0.5*Mth.sin((float) rot), 0.0D, 0.0D, 0.0D);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            addLife();
            if (getLife() >= LIFE_TICKS) {
                this.discard();
            }
            if (this.isInsideSolid()) {
                double _r_ = getRadius();
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(_r_), mob -> mob.isAlive() && mob.distanceToSqr(this) <= _r_ * _r_);
                for (_ImmortalMob mob : mobs) {
                    Player player = getOwner();
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_LIGHT,player);
                    mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3.5F));
                }
                ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(), ModParticles.LIGHT.get(),0.3,72,position().add(0.0,0.5,0.0),new Vec3(Mth.cos((float) (Math.PI*2/3)),1.5,Mth.sin((float) (Math.PI*2/3))));
                ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(), ModParticles.LIGHT.get(),0.3,72,position().add(0.0,0.5,0.0),new Vec3(Mth.cos((float) (Math.PI*4/3)),1.5,Mth.sin((float) (Math.PI*4/3))));
                ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(), ModParticles.LIGHT.get(),0.3,72,position().add(0.0,0.5,0.0),new Vec3(Mth.cos((float) (Math.PI*6/3)),1.5,Mth.sin((float) (Math.PI*6/3))));
                ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(), ModParticles.LIGHT.get(),0.5,72,position().add(0.0,0.5,0.0));
                ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(), ParticleTypes.END_ROD,0.45,72,position().add(0.0,0.5,0.0));
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
