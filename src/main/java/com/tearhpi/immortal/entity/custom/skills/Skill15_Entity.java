package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import com.tearhpi.immortal.util.statics.StaticParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

public class Skill15_Entity extends Entity {
    private static final int LIFE_TICKS = 200; // 8s
    private int life;

    public float radius = 6.0f; // 太阳半径
    private UUID ownerId;
    public float rotationSpeed = 0.2f;
    public float timeScale = 1.0f;
    private static final EntityDataAccessor<Float> radiusAttack = SynchedEntityData.defineId(Skill15_Entity.class, EntityDataSerializers.FLOAT);

    public Skill15_Entity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;       // 太阳不参与碰撞
        this.setNoGravity(true);     // 不受重力
    }
    public static Skill15_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill15_Entity e = new Skill15_Entity(ModEntityTypes.SKILL15_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadiusAttack(radius);
        level.addFreshEntity(e);
        return e;
    }

    @Override
    public void tick() {
        super.tick();
        //客户端 新增粒子
        if (this.level().isClientSide) {
            Random r = new Random();
            for (int i = 0; i < r.nextInt(1,10); i++) {
                int d = r.nextInt(1,360);
                float rad = (float) Math.toRadians(d);
                level().addParticle(ParticleTypes.FLAME,this.getX()+Mth.cos((float) Math.toRadians(d))*3.0, this.getY(), this.getZ()+Mth.sin((float) Math.toRadians(d))*3.0, Mth.cos((float) Math.toRadians(d))/r.nextInt(1,10), r.nextFloat(-1f,-0.1f ), Mth.sin((float) Math.toRadians(d))/r.nextInt(1,10));
            }
        }
        //服务端 运行逻辑
        if (!this.level().isClientSide) {
            Vec3 center = this.position().add(new Vec3(0,-10.0f,0));
            if (life % 6 == 0) {
                Random rand = new Random();
                float r = getRadiusAttack();
                //95%概率
                int random_value = rand.nextInt(1,100);
                float r_real = (float) Math.sqrt(r*r+100);
                if (random_value >= 6) {
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r_real), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r_real * r_real
                    );
                    for (_ImmortalMob mob : mobs) {
                        StaticParticle.spawnParticleLine((ServerLevel) level(),this.position(), mob.position(),ParticleTypes.FLAME,20);
                        ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                        mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.75f));
                    }
                }
                //5%概率
                if (random_value < 5) {
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r_real), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r_real * r_real
                    );
                    for (_ImmortalMob mob : mobs) {
                        StaticParticle.spawnParticleLine((ServerLevel) level(),this.position(), mob.position(),ParticleTypes.FLAME,20);
                        ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(),ParticleTypes.FLAME,0.1,36,mob.position());
                        ImposeEffect.ApplyEffectLayer(mob,_ModEffects.ANNIHILATE_EFFECT.get(),100);
                        int r_ = 5;
                        List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                                _ImmortalMob.class, mob.getBoundingBox().inflate(r_), mob_ -> mob_.isAlive() && mob_.distanceToSqr(this) <= r_ * r_
                        );
                        for (_ImmortalMob mob_ : mobs_) {
                            ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                            mob_.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3.75f));
                        }
                    }
                }
                //添加特效
                if (life % 10 == 0) {
                    //敌人 减少30%防御
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                            _ImmortalMob.class, this.getBoundingBox().inflate(r_real), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r_real * r_real);
                    for (_ImmortalMob mob : mobs) {
                        ImposeEffect.ApplyEffectLayer(mob, _ModEffects.SKILL15_EFFECT_REMOVE_DEF.get(), 40);
                    }
                    //自身 增加10%伤害
                    List<Player> playerList = this.level().getEntitiesOfClass(
                            Player.class, this.getBoundingBox().inflate(r_real), p -> p.isAlive() && p.distanceToSqr(this) <= r_real * r_real);
                    for (Player p : playerList) {
                        ImposeEffect.ImposeEffectWithoutAmp(p, _ModEffects.SKILL15_EFFECT_ADD_DMG.get(), 40);
                    }
                }
            }
            if (++life >= LIFE_TICKS) {
                Random rand_ = new Random();
                for (int i = 0; i < 200; i++) {
                    double rot = rand_.nextInt(1,360)*2*Mth.PI/360;
                    int scale = rand_.nextInt(1,20);
                    ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,this.getX()+scale*Mth.cos((float) rot),this.getY()-10,this.getZ()+scale*Mth.sin((float) rot),1,0,0,0,0);
                }
                final double R = getRadiusAttack();

                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(R), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= R * R
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 7.5f));
                    ImposeEffect.ImposeLayer(mob, MobEffects.MOVEMENT_SLOWDOWN, 200,3);
                }
                this.discard();
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(radiusAttack, 0.0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        radius = tag.getFloat("radius");
        rotationSpeed = tag.getFloat("rot");
        timeScale = tag.getFloat("ts");
        setRadiusAttack(tag.getFloat("radiusAttack"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("radiusAttack", getRadiusAttack());
        tag.putFloat("radius", radius);
        tag.putFloat("rot", rotationSpeed);
        tag.putFloat("ts", timeScale);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        // 让裁剪盒覆盖到 corona / flares 的最大半径（按你的半径乘一个系数）
        double r = this.radius * 4.0; // 视你的L3/L4实际最大外扩，2.5~4.0之间调
        return new AABB(getX() - r, getY() - r, getZ() - r,
                getX() + r, getY() + r, getZ() + r);
    }
    public float getRadiusAttack() { return entityData.get(radiusAttack); }
    public void setRadiusAttack(float r) { entityData.set(radiusAttack,r); }
    @Nullable
    private ServerPlayer getOwner() {
        if (this.ownerId == null) return null;
        if (!(this.level() instanceof ServerLevel sl)) return null;
        return sl.getServer().getPlayerList().getPlayer(this.ownerId);
    }
}
