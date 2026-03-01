package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.particle.option.OrbitParticleWater;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.MotiveParticle;
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
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill22_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 600;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill22_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill22_Entity.class, EntityDataSerializers.INT);

    //三体运动常数表

    public Skill22_Entity(EntityType<? extends Skill22_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill22_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill22_Entity e = new Skill22_Entity(ModEntityTypes.SKILL22_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        level.addFreshEntity(e);
        //----出场特效 以pos为中心生成----
        MotiveParticle.drawCircle(level,-8*Math.PI/12,pos.add(new Vec3(0.0f,-0.4f,0.0f)), radius*0.4, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-7*Math.PI/12,pos.add(new Vec3(0.0f,-0.2f,0.0f)), radius*0.45, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-6*Math.PI/12,pos.add(new Vec3(0.0f,0.0f,0.0f)), radius*0.5, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-5*Math.PI/12,pos.add(new Vec3(0.0f,0.2f,0.0f)), radius*0.55, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-4*Math.PI/12,pos.add(new Vec3(0.0f,0.4f,0.0f)), radius*0.6, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-3*Math.PI/12,pos.add(new Vec3(0.0f,0.6f,0.0f)), radius*0.65, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-2*Math.PI/12,pos.add(new Vec3(0.0f,0.8f,0.0f)), radius*0.7, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,-1*Math.PI/12,pos.add(new Vec3(0.0f,1.0f,0.0f)), radius*0.75, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,0*Math.PI/12,pos.add(new Vec3(0.0f,1.2f,0.0f)), radius*0.8, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,1*Math.PI/12,pos.add(new Vec3(0.0f,1.4f,0.0f)), radius*0.85, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
        MotiveParticle.drawCircle(level,2*Math.PI/12,pos.add(new Vec3(0.0f,1.6f,0.0f)), radius*0.9, 100,new OrbitParticleWater(pos,0.4,LIFE_TICKS));
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
        /*
        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);
        */
        //客户端
        if (this.level().isClientSide) {
            if (getLife() % 1 == 0) {
                double omega = 0.05;
                double omega2 = 0.03;
                int rawLine_tick = getLife() % 600;
                float theta = (float) (omega2 * rawLine_tick);
                //球1
                Vec3 center = this.position();
                if (true) {
                    //原始点获取
                    //Vec3 rawLine = Skill22_static.BODY1_PATH[rawLine_tick];
                    Vec3 rawLine = new Vec3(Mth.cos(theta), Mth.sin(theta), 0f);
                    Vec3 x_ = new Vec3(1.0,0.0,0.0);
                    Vec3 y_ = new Vec3(0.0,Mth.cos((float) (Mth.PI/2+omega*rawLine_tick)),Mth.sin((float) (Mth.PI/2+omega*rawLine_tick)));
                    Vec3 z_ = new Vec3(0.0,Mth.cos((float) (omega*rawLine_tick)),Mth.sin((float) (omega*rawLine_tick)));
                    //T,B,N为最终获取的向量
                    for (Vec3 vec3 : Skill22_static.BALL_PATH) {
                        //Vec3 finalVec = new Vec3(T.dot(vec3),B.dot(vec3),N.dot(vec3));
                        level().addParticle(ModParticles.SKILL22_Particle.get(),center.x+rawLine.x*6+x_.scale(2).dot(vec3),center.y+rawLine.y*6+y_.scale(2).dot(vec3)+10,center.z+rawLine.z*6+z_.scale(2).dot(vec3),0,0,0);
                    }
                }
                //球2
                if (true) {
                    //原始点获取
                    Vec3 rawLine = new Vec3(0,Mth.cos(theta),Mth.sin(theta));
                    Vec3 x_ = new Vec3(1.0,0.0,0.0);
                    Vec3 y_ = new Vec3(0.0,Mth.cos((float) (Mth.PI/2+omega*rawLine_tick)),Mth.sin((float) (Mth.PI/2+omega*rawLine_tick)));
                    Vec3 z_ = new Vec3(0.0,Mth.cos((float) (omega*rawLine_tick)),Mth.sin((float) (omega*rawLine_tick)));
                    //T,B,N为最终获取的向量
                    for (Vec3 vec3 : Skill22_static.BALL_PATH) {
                        //Vec3 finalVec = new Vec3(T.dot(vec3),B.dot(vec3),N.dot(vec3));
                        level().addParticle(ModParticles.SKILL22_Particle.get(),center.x+rawLine.x*6+x_.scale(2).dot(vec3),center.y+rawLine.y*6+y_.scale(2).dot(vec3)+10,center.z+rawLine.z*6+z_.scale(2).dot(vec3),0,0,0);
                    }
                }
                //球2
                if (true) {
                    //原始点获取
                    Vec3 rawLine = new Vec3(Mth.cos(theta),0,Mth.sin(theta));
                    Vec3 x_ = new Vec3(1.0,0.0,0.0);
                    Vec3 y_ = new Vec3(0.0,Mth.cos((float) (Mth.PI/2+omega*rawLine_tick)),Mth.sin((float) (Mth.PI/2+omega*rawLine_tick)));
                    Vec3 z_ = new Vec3(0.0,Mth.cos((float) (omega*rawLine_tick)),Mth.sin((float) (omega*rawLine_tick)));
                    //T,B,N为最终获取的向量
                    for (Vec3 vec3 : Skill22_static.BALL_PATH) {
                        //Vec3 finalVec = new Vec3(T.dot(vec3),B.dot(vec3),N.dot(vec3));
                        level().addParticle(ModParticles.SKILL22_Particle.get(),center.x+rawLine.x*6+x_.scale(2).dot(vec3),center.y+rawLine.y*6+y_.scale(2).dot(vec3)+10,center.z+rawLine.z*6+z_.scale(2).dot(vec3),0,0,0);
                    }
                }
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            double r = getRadius();
            if (getLife() % 20 == 0) {
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive()
                );
                for (_ImmortalMob mob : mobs ) {
                    mob.addWaterAmount(5);
                    ImposeEffect.ImposeEffectWithoutAmp(mob,_ModEffects.MUTE_EFFECT.get(),40);
                    Random rand = new Random();
                    for (int i = 0; i < 10; i++) {
                        ((ServerLevel) level()).sendParticles(ModParticles.WATER.get(),mob.position().x+rand.nextFloat(-1,1),mob.position().y+rand.nextFloat(0,2),mob.position().z+rand.nextFloat(-1,1),1,0,0,0,0);
                    }
                }
            }

            addLife();
            if (getLife() >= LIFE_TICKS) {
                Random rand_ = new Random();
                for (int i = 0; i < 200; i++) {
                    double rot = rand_.nextInt(1,360)*2*Mth.PI/360;
                    int scale = rand_.nextInt(1,20);
                    ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,this.getX()+scale*Mth.cos((float) rot),this.getY()+1,this.getZ()+scale*Mth.sin((float) rot),1,0,0,0,0);
                }
                final double R = getRadius();

                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(R), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= R * R
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 6.0f));
                    mob.addWaterAmount(100);
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
