package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
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
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
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

import static com.tearhpi.immortal.util.statics.Entity_Utils.moveToward;

public class Skill9_Entity extends Entity implements GeoEntity {
    private static final int LIFE_TICKS = 200; // 1s
    private int life;
    private _ImmortalMob Target;
    private int rot_random_x;
    private int rot_random_x_scale;
    private int rot_random_y;
    private double distance;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill9_Entity.class, EntityDataSerializers.FLOAT); // 剩余
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);


    public Skill9_Entity(EntityType<? extends Skill9_Entity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    // 便捷创建
    public static Skill9_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, _ImmortalMob mob) {
        Skill9_Entity e = new Skill9_Entity(ModEntityTypes.SKILL9_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.hasImpulse = true;
        //贝塞尔
        e.Target = mob;
        Random rand = new Random();
        e.rot_random_x = rand.nextInt(0,360);
        e.rot_random_x_scale = rand.nextInt(0,6);
        e.rot_random_y = rand.nextInt(1,8);
        e.distance = Mth.sqrt((float) e.distanceToSqr(mob));

        e.ownerId = owner.getUUID();
        e.setRadius(1);
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
            level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            level().addParticle(ModParticles.SKILL9_Particle_Fire.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            level().addParticle(ModParticles.SKILL9_Particle_Fire.get(), this.getX()+mot.x, this.getY()+mot.y, this.getZ()+mot.z, 0.0D, 0.0D, 0.0D);
        }
        //服务端 运行逻辑
        if (!this.level().isClientSide) {
            //移动
            if (this.Target != null) {
                //计算位移
                //算法:scale:Max((当前距离/初始距离-0.25),0)
                //x,z平移:scale*e.rot_random_x_scale*对应三角函数(e.rot_random_x)
                //y平移:scale*e.rot_random_y
                double scale = Math.max(Mth.sqrt((float) this.distanceToSqr(this.Target))/distance-0.1,0);
                Vec3 tg = this.Target.position();
                float rad = this.rot_random_x * 2*Mth.PI/360;
                Vec3 tg_adj = new Vec3(tg.x+scale*this.rot_random_x_scale*Mth.cos(rad),tg.y+scale*this.rot_random_y,tg.z+scale*this.rot_random_x_scale*Mth.sin(rad));
                moveToward(this, tg_adj, 0.5);
            }

            // 搜索指定范围内的所有 Mob
            double r_ = this.getRadius();
            List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r_), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r_ * r_);
            if (!mobs.isEmpty()) {
                _ImmortalMob mob = mobs.get(0);
                ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.8f));
                this.discard();
            }
        }
        //是否关闭
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
    private PlayState precidate(AnimationState<Skill9_Entity> state) {
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::precidate));
    }
}
