package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom.Utils.BezierEntity;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.entity.custom._ImmortalUtilMob;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

import static com.tearhpi.immortal.util.statics.SoundDeliver.playSoundNear;
import static com.tearhpi.immortal.util.statics.StaticParticle.butterfly;

public class Skill6_Entity extends _ImmortalUtilMob implements GeoEntity {
    public static final int LIFE_TICKS = 65; // 1s
    public int life;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill6_Entity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> SCALE =
            SynchedEntityData.defineId(Skill6_Entity.class, EntityDataSerializers.FLOAT);
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);

    public float prevRenderScale = 1.0f;
    private float renderScale = 1.0f; // 当前帧目标（来自 entityData 或计算）

    public Skill6_Entity(EntityType<? extends Skill6_Entity> type, Level level) {
        super(type, level);
        this.showHealth = false;
        this.noPhysics = true; // 不受碰撞推动
        this.setNoGravity(true);
    }

    // 便捷创建
    public static Skill6_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill6_Entity e = new Skill6_Entity(ModEntityTypes.SKILL6_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        level.addFreshEntity(e);
        return e;
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
    public void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        setRadius(tag.getFloat("radius"));
        setRenderScale(tag.getFloat("scale"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putFloat("radius", getRadius());
        tag.putFloat("scale", getRenderScale());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(radius, 0.0f);
        this.entityData.define(SCALE, 1.0f);
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
    public void tick() {
        super.tick();
        float speed = 5.0f;
        applyAllYaw(this.getYRot() + speed);
        //客户端 新增粒子
        if (this.level().isClientSide) {
            //level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            int MAX_VALUE = 4;
            float r = getRadius();
            for (int i = 0;i < MAX_VALUE;i++) {
                //level().addParticle(ParticleTypes.LAVA, this.getX()+Mth.cos(Mth.PI/2*i+life*Mth.PI/180*3)*r, this.getY()+0.2, this.getZ()+Mth.sin(Mth.PI/2*i+life*Mth.PI/180*3)*r, 0.0D, 0.0D, 0.0D);
            }
            this.prevRenderScale = this.renderScale;
            this.renderScale = this.getRenderScale();
            //图案
            //float u = life*2*Mth.PI/100;
            //float v = life*2*Mth.PI/100;
            /*
            float step = 2*Mth.PI/300;
            for (int i = 1;i <= 3;i++) {
                //每30t是一整个循环,循环细节到2pi/300,每t编号为:10(t-1)+1-10t
                float u = (10*(life-1)+i)*step;
                for (int k = 0;k < 20;k++) {
                    float step2 = 2*Mth.PI/20;
                    float v = k * step2;
                    int R1 = 4;
                    int R_ = 1;
                    int R = 7;
                    int h = -1;
                    float x = (R1 + (R - R_)*Mth.cos(v) + h*Mth.cos(((R - R_)/R_)*v)) * Mth.cos(u);
                    float y = (float) (1.5*(R - R_) * Mth.sin(v) - h * Mth.sin(((R - R_)/R_)*v));
                    float z = (R1 + (R - R_) * Mth.cos(v) + h*Mth.cos(((R - R_)/R_)*v)) * Mth.sin(u);
                    level().addParticle(ParticleTypes.SMALL_FLAME, this.getX()+x/8, this.getY()+y/8, this.getZ()+z/8, 0.0D, 0.0D, 0.0D);
                }
            }
             */
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            //float rotSpeed = 5.0f; // 每tick旋转5度
            //setYRot(getYRot() + rotSpeed);
            //setYHeadRot(getYRot());
            //setYBodyRot(getYRot());
            //if (getYRot() > 360f) setYRot(getYRot() - 360f);
            playSoundNear((ServerLevel) level(),this.position(), SoundEvents.CAMPFIRE_CRACKLE);
            if (this.life % 15 == 0 && this.life < 50) {
                playSoundNear((ServerLevel) level(),this.position(), SoundEvents.BLAZE_SHOOT,0.5f,1.0f);
                double r = getRadius();
                // 搜索指定范围内的所有 Mob
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                );
                for (Mob mob : mobs) {
                    Player player = getOwner();
                    if (player != null){
                        ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,player);
                        mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.75F));
                    }
                }
                ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(), ParticleTypes.FLAME,0.3,72,this.position().add(0,0.5,0));
            }
            if (this.life == LIFE_TICKS-5) {
                double r = getRadius();
                // 搜索指定范围内的所有 Mob
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                );
                for (Mob mob : mobs) {
                    Player player = getOwner();
                    if (player != null){
                        ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,player);
                        mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 2.0F));
                    }
                }
                ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,this.position().x,this.position().y,this.position().z,10,1,2,1,0);
                ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(), ParticleTypes.FLAME,0.7,72,this.position().add(0,0.5,0));
                ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(), ParticleTypes.FLAME,0.6,72,this.position().add(0,0.5,0));
                playSoundNear((ServerLevel) level(),this.position(), SoundEvents.GENERIC_EXPLODE);
            }
        }
        // 寿命
        if (++life > LIFE_TICKS) {
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
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double max = 256.0;
        return distance < max * max;
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
    private PlayState precidate(AnimationState<Skill6_Entity> state) {
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::precidate));
    }
    @Override
    public boolean isOnFire(){
        return true;
    }

    public float getRenderScale() { return this.entityData.get(SCALE); }
    public void setRenderScale(float s) {
        this.entityData.set(SCALE, s);
    }
}
