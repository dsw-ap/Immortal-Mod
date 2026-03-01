package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
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

public class Skill63_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 40;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill63_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill63_Entity.class, EntityDataSerializers.INT);


    public Skill63_Entity(EntityType<? extends Skill63_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill63_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill63_Entity e = new Skill63_Entity(ModEntityTypes.SKILL63_ENTITY.get(), level);
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

        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);

        //客户端
        if (this.level().isClientSide) {
            Vec3 pos = this.position();
            Random rand = new Random();
            for (int i = 0; i < 3; i++) {
                level().addParticle(ModParticles.DARKNESS.get(), pos.x+rand.nextFloat(-0.3f,0.3f),pos.y+rand.nextFloat(-0.3f,0.3f)+1.5,pos.z+rand.nextFloat(-0.3f,0.3f),0,0,0);
                level().addParticle(ParticleTypes.SMOKE, pos.x+rand.nextFloat(-0.3f,0.3f),pos.y+rand.nextFloat(-0.3f,0.3f)+1.5,pos.z+rand.nextFloat(-0.3f,0.3f),0,rand.nextFloat(0.05f,0.1f),0);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            double r = this.getRadius();
            List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r);
            if (!mobs.isEmpty()) {
                _ImmortalMob mob = mobs.get(0);
                Player player = getOwner();
                if (player != null){
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,player);
                    mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3.0F));
                    ImposeEffect.ApplyEffectLayer(mob, _ModEffects.SKILL63_CHAOS_CATALYST.get(), 102);

                    ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(),ParticleTypes.SMOKE,0.1,72,mob.position().add(0.0,1.0,0.0));
                    ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(),ParticleTypes.SMOKE,0.1,72,mob.position().add(0.0,1.0,0.0),new Vec3(random.nextFloat(),random.nextFloat(),random.nextFloat()));
                    ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(),ParticleTypes.SMOKE,0.1,72,mob.position().add(0.0,1.0,0.0),new Vec3(random.nextFloat(),random.nextFloat(),random.nextFloat()));
                }
                Vec3 p = mob.position();
                ((ServerLevel) level()).sendParticles(ModParticles.DARKNESS.get(),p.x,p.y,p.z,10,1,2,1,0.05);
                this.discard();
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
        BlockPos bp = BlockPos.containing(this.position().add(0.0,1.7,0.0));
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
