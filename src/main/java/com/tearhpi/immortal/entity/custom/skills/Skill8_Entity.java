package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill8_Entity extends Entity implements GeoEntity {
    private static final int LIFE_TICKS = 25; // 1s
    private int life;
    private _ImmortalMob Origin;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill8_Entity.class, EntityDataSerializers.FLOAT); // 剩余
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);


    public Skill8_Entity(EntityType<? extends Skill8_Entity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    // 便捷创建
    public static Skill8_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius,_ImmortalMob mob) {
        Skill8_Entity e = new Skill8_Entity(ModEntityTypes.SKILL8_ENTITY.get(), level);
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
            level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            Random rand = new Random();
            level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f));
            level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f));
            level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f), rand.nextFloat( -1.0f,1.0f));
        }
        //服务端 运行逻辑
        if (!this.level().isClientSide) {
            double r_ = 1.0;
            // 搜索指定范围内的所有 Mob
            List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                    _ImmortalMob.class, this.getBoundingBox().inflate(r_), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r_ * r_ && mob != this.Origin
            );
            double r = getRadius();
            if (!mobs.isEmpty()) {
                _ImmortalMob mob = mobs.get(0);
                //普通伤害
                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, mob.getBoundingBox().inflate(r), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(mob) <= r * r
                );
                for (_ImmortalMob mob2 : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                    mob2.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.75f));
                }
                //引燃额外伤害
                if (mob.hasEffect(_ModEffects.FIRING_EFFECT.get())){
                    List<_ImmortalMob> mobs__ = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, mob.getBoundingBox().inflate(r*4/3), _mob__ -> _mob__.isAlive() && _mob__.distanceToSqr(mob) <= r*4/3 * r*4/3
                    );
                    for (_ImmortalMob mob2 : mobs__) {
                        ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                        mob2.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 1.2f));
                    }
                };
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


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
    private PlayState precidate(AnimationState<Skill8_Entity> state) {
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::precidate));
    }
}
