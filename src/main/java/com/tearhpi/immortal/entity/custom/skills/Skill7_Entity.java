package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.entity.custom._ImmortalUtilMob;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.tearhpi.immortal.util.statics.SkillTagRelated.getyRot;
import static com.tearhpi.immortal.util.statics.SoundDeliver.playSoundNear;

public class Skill7_Entity extends _ImmortalUtilMob implements GeoEntity {
    private static final int LIFE_TICKS = 160; // 8s
    public int life;
    private float fixedYaw;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill7_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> HAS_SKILL7 = SynchedEntityData.defineId(Skill7_Entity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> lifetime = SynchedEntityData.defineId(Skill7_Entity.class, EntityDataSerializers.INT);
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);

    public Skill7_Entity(EntityType<? extends Skill7_Entity> type, Level level) {
        super(type, level);
        this.showHealth = false;
        this.noPhysics = true; // 不受碰撞推动
        this.setNoGravity(true);
    }

    // 便捷创建
    public static Skill7_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill7_Entity e = new Skill7_Entity(ModEntityTypes.SKILL7_ENTITY.get(), level);
        e.setPos(pos);
        float rot = getyRot(owner)+90;
        if (rot > 180){
            rot -= 360;
        }
        e.fixedYaw = rot;
        e.setFixedYaw(e.fixedYaw);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        e.setLifetime(0);
        level.addFreshEntity(e);
        return e;
    }
    public void setFixedYaw(float yaw) {
        this.fixedYaw = yaw;
        this.applyAllYaw(yaw);
    }
    // 把一堆 yaw 一次性全写
    private void applyAllYaw(float yaw) {
        // 当前帧
        this.setYRot(yaw);
        this.setYHeadRot(yaw);
        this.yBodyRot = yaw;
        // 上一帧，用来防止插值时“慢慢歪”
        this.yRotO = yaw;
        this.yHeadRotO = yaw;
        this.yBodyRotO = yaw;
    }



    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        setSkill7Tag(tag.getBoolean("skill7"));
        setRadius(tag.getFloat("radius"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(ModAttributes.IMMORTAL_MAX_HEALTH.get(), 2147483647.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ARMOR, 0.0);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return true;
    }


    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putBoolean("skill7",hasSkill7Tag());
        tag.putFloat("radius", getRadius());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(radius, 0.0f);
        this.entityData.define(HAS_SKILL7, false);
        this.entityData.define(lifetime, 0);
        //HASSKILL7 = true:x轴/HASSKILL7 = false:z轴/
    }

    @Override
    public void tick() {
        super.tick();
        //this.applyAllYaw(this.fixedYaw);
        //客户端
        if (this.level().isClientSide) {
            //level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            //采样点:本身
            Vec3 look = this.getLookAngle();
            Random random = new Random();
            level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY()+random.nextDouble(0.0,3.0), this.getZ(), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY()+random.nextDouble(0.0,3.0), this.getZ(), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.LAVA, this.getX()+look.x*random.nextFloat(0.0f,1.0f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()+look.z*random.nextFloat(0.0f,1.0f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.FLAME, this.getX()+look.x*random.nextFloat(0.0f,1.0f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()+look.z*random.nextFloat(0.0f,1.0f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.LAVA, this.getX()+look.x*random.nextFloat(1.0f,2.5f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()+look.z*random.nextFloat(1.5f,2.5f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.FLAME, this.getX()+look.x*random.nextFloat(1.0f,2.5f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()+look.z*random.nextFloat(1.5f,2.5f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.LAVA, this.getX()-look.x*random.nextFloat(0.0f,1.0f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()-look.z*random.nextFloat(0.0f,1.0f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.FLAME, this.getX()-look.x*random.nextFloat(0.0f,1.0f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()-look.z*random.nextFloat(0.0f,1.0f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.LAVA, this.getX()-look.x*random.nextFloat(1.5f,2.5f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()-look.z*random.nextFloat(1.5f,2.5f), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.FLAME, this.getX()-look.x*random.nextFloat(1.5f,2.5f), this.getY()+random.nextDouble(0.0,3.0), this.getZ()-look.z*random.nextFloat(1.5f,2.5f), 0.0D, 0.0D, 0.0D);
            //System.out.println(getLifetime());
            //System.out.println(getLifetime());
            if (getLifetime() >= LIFE_TICKS-2) {
                for (int i = 0;i < 10;++i) {
                    level().addParticle(ParticleTypes.EXPLOSION, this.getX()+look.x*random.nextInt(-2,2), this.getY()+random.nextDouble(0.0,4.0), this.getZ()+look.z*random.nextInt(-2,2), 0.0D, 0.0D, 0.0D);
                }
            }
        }
        //服务端
        //自转
        if (!this.level().isClientSide) {
            playSoundNear((ServerLevel) level(),this.position(), SoundEvents.CAMPFIRE_CRACKLE);
            if (this.life % 20 == 0 && this.life < 150) {
                double r = this.getRadius();
                // 搜索指定范围内的所有 Mob
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                );
                for (Mob mob : mobs) {
                    ImposeEffect.ApplyEffectLayer(mob, _ModEffects.FIRING_EFFECT.get(),80);
                    ImposeEffect.ImposeLayer(mob, MobEffects.MOVEMENT_SLOWDOWN,80,0);
                }
            }
            // 寿命
            //System.out.println(getLifetime()+"Server"+life+"/"+LIFE_TICKS);
            if (life >= LIFE_TICKS) {
                //造成一次伤害
                double r = this.getRadius();
                // 搜索指定范围内的所有 Mob
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                );
                for (Mob mob : mobs) {
                    MobEffectInstance effectInstance = mob.getEffect(_ModEffects.FIRING_EFFECT.get());
                    int amp = 0;
                    if (effectInstance != null) {
                        amp = effectInstance.getAmplifier()+1;
                    }
                    float dmg = amp*0.8f;
                    ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), dmg));
                    mob.removeEffect(_ModEffects.FIRING_EFFECT.get());
                }
                playSoundNear((ServerLevel) level(),this.position(), SoundEvents.GENERIC_EXPLODE);
                this.discard();
            }
            life++;
            setLifetime(life);
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
    public int getLifetime() { return entityData.get(lifetime); }
    public void setLifetime(int value) { entityData.set(lifetime,value); }
    public void setSkill7Tag(boolean value) {
        this.entityData.set(HAS_SKILL7, value);
    }
    public boolean hasSkill7Tag() {
        return this.entityData.get(HAS_SKILL7);
    }
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
    private PlayState precidate(AnimationState<Skill7_Entity> state) {
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::precidate));
    }
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double max = 256.0;
        return distance < max * max;
    }
}
