package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
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
import java.util.UUID;

public class Skill10_Entity extends Entity {
    private static final int LIFE_TICKS = 160; // 8s
    private int life;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill10_Entity.class, EntityDataSerializers.FLOAT); // 剩余
    //private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR = SynchedEntityData.defineId(_EntityTemplate.class, EntityDataSerializers.INT);

    public Skill10_Entity(EntityType<? extends Skill10_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill10_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill10_Entity e = new Skill10_Entity(ModEntityTypes.SKILL10_ENTITY.get(), level);
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
            //level().addParticle(ModParticles.SKILL10_Particle.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.3D, 0.0D);
            /*
            int MAX_VALUE = 120;
            float r = getRadius();
            for (int i = 0;i < MAX_VALUE;i++) {
                level().addParticle(ParticleTypes.FLAME, this.getX()+Mth.cos(Mth.PI/8*i+life*Mth.PI/180*5)*r/1.5*(0.6+0.4*i/MAX_VALUE), this.getY()+0.05*i, this.getZ()+Mth.sin(Mth.PI/8*i+life*Mth.PI/180*5)*r/1.5*(0.6+0.4*i/MAX_VALUE), 0.0D, 0.0D, 0.0D);
            }

             */
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            final double R = getRadius();
            final double V_MAX = 0.25;
            final double LERP = 0.25;          // 速度插值
            final double STOP_DIST = 0.25;     // 强力减速
            final double Y_SCALE = 0.35;       // 垂直分量衰减

            var box = this.getBoundingBox().inflate(R);
            var mobs = this.level().getEntitiesOfClass(
                    _ImmortalMob.class, box,
                    m -> m.isAlive() && m.isAttractable() && m.distanceToSqr(this) <= R * R
            );
            for (_ImmortalMob m : mobs) {
                // 终止AI导航
                if (m.getNavigation().isInProgress()) m.getNavigation().stop();
                // 指向中心的向量
                var center = this.position();
                var pos    = m.position();
                var toC    = center.subtract(pos);
                double dist = toC.length();
                if (dist < 1e-6) continue;
                // 水平
                var dir = new Vec3(toC.x, toC.y * Y_SCALE, toC.z);
                double len = dir.length();
                if (len < 1e-6) continue;
                dir = dir.scale(1.0 / len);
                // 距离越近目标速度越小
                double vTarget = Math.min(V_MAX, Math.max(0.0, (dist - STOP_DIST) * 0.5));
                var desiredVel = dir.scale(vTarget);
                // 平滑过渡
                var curVel = m.getDeltaMovement();
                var newVel = curVel.lerp(desiredVel, LERP);
                // 地面单位向上
                if (m.onGround() && newVel.y < 0.06) {
                    newVel = new Vec3(newVel.x, 0.06, newVel.z);
                }
                // 防止在中心抖动
                if (dist <= STOP_DIST) {
                    newVel = new Vec3(0, 0, 0);
                }
                // 应用速度并强制同步
                m.setDeltaMovement(newVel);
                //System.out.println(m.getDeltaMovement());
                m.hasImpulse = true;
                m.hurtMarked = true;
                //造成伤害
                if (this.tickCount % 5 == 0) {
                    Vec3 v1 = m.position();
                    Vec3 v1_ = new Vec3(v1.x, 0, v1.z);
                    Vec3 v2 = this.position();
                    Vec3 v2_ = new Vec3(v2.x, 0, v2.z);
                    double value = Mth.sqrt((float) v1_.distanceToSqr(v2_));
                    ModDamageSources modDamageSources = new ModDamageSources(m.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                    m.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(m), (float)Math.max(1-0.15*value,0.1)));
                }
            }

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
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double max = 256.0;
        return distance < max * max;
    }
}
