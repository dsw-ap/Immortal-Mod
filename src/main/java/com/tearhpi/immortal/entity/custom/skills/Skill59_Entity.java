package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.StaticParticle;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill59_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 205;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill59_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill59_Entity.class, EntityDataSerializers.INT);


    public Skill59_Entity(EntityType<? extends Skill59_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建

    public static Skill59_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill59_Entity e = new Skill59_Entity(ModEntityTypes.SKILL59_ENTITY.get(), level);
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
        /*
        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);
        */
        //客户端
        if (this.level().isClientSide) {
            Vec3 pos = this.position();
            Random random = new Random();
            for(int i=0;i<20;i++){
                double length = random.nextDouble(0.0,getRadius());
                float angle = (float) Math.toRadians(random.nextInt(1,360));

                level().addParticle(ModParticles.LIGHT.get(), pos.x+length*Mth.cos(angle),pos.y+12, pos.z+length*Mth.sin(angle),0.0,-0.2*length/getRadius(),0.0);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (getLife() == 1) {
                ServerLevel level = (ServerLevel) this.level();
                Vec3 pos = this.position();
                for (int i=0; i<100; i++) {
                    float step = (float) (2 * Math.PI * i / 100f);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*0.90 * Mth.cos(step),pos.y-0.4,pos.z + getRadius()*0.90 * Mth.sin(step),1,0,0,0,0);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*0.94 * Mth.cos(step),pos.y-0.3,pos.z + getRadius()*0.94 * Mth.sin(step),1,0,0,0,0);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*0.98 * Mth.cos(step),pos.y-0.2,pos.z + getRadius()*0.98 * Mth.sin(step),1,0,0,0,0);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*1.02 * Mth.cos(step),pos.y-0.1,pos.z + getRadius()*1.02 * Mth.sin(step),1,0,0,0,0);
                }
                Vec3 pos1 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(10.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(10.0)));
                Vec3 pos2 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-10.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-10.0)));
                Vec3 pos3 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(170.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(170.0)));
                Vec3 pos4 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-170.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-170.0)));
                Vec3 pos5 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(80.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(80.0)));
                Vec3 pos6 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(100.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(100.0)));
                Vec3 pos7 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-80.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-800.0)));
                Vec3 pos8 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-100.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-100.0)));
                StaticParticle.spawnParticleLine(level,pos1,pos2,ModParticles.LIGHT.get(),20);
                StaticParticle.spawnParticleLine(level,pos3,pos4,ModParticles.LIGHT.get(),20);
                StaticParticle.spawnParticleLine(level,pos1,pos3,ModParticles.LIGHT.get(),150);
                StaticParticle.spawnParticleLine(level,pos2,pos4,ModParticles.LIGHT.get(),150);
                StaticParticle.spawnParticleLine(level,pos5,pos6,ModParticles.LIGHT.get(),20);
                StaticParticle.spawnParticleLine(level,pos7,pos8,ModParticles.LIGHT.get(),20);
                StaticParticle.spawnParticleLine(level,pos5,pos7,ModParticles.LIGHT.get(),150);
                StaticParticle.spawnParticleLine(level,pos6,pos8,ModParticles.LIGHT.get(),150);
            }
            if (getLife() % 20 == 0 && getLife() > 0) {
                ServerLevel level = (ServerLevel) this.level();
                Vec3 pos = this.position();
                for (int i=0; i<100; i++) {
                    float step = (float) (2 * Math.PI * i / 100f);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*0.90 * Mth.cos(step),pos.y-0.4,pos.z + getRadius()*0.90 * Mth.sin(step),0,0,0.2,0,1.0);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*0.94 * Mth.cos(step),pos.y-0.3,pos.z + getRadius()*0.94 * Mth.sin(step),0,0,0.2,0,1.0);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*0.98 * Mth.cos(step),pos.y-0.2,pos.z + getRadius()*0.98 * Mth.sin(step),0,0,0.2,0,1.0);
                    level.sendParticles(ModParticles.LIGHT.get(),pos.x + getRadius()*1.02 * Mth.cos(step),pos.y-0.1,pos.z + getRadius()*1.02 * Mth.sin(step),0,0,0.2,0,1.0);
                }
                Vec3 pos1 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(10.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(10.0)));
                Vec3 pos2 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-10.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-10.0)));
                Vec3 pos3 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(170.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(170.0)));
                Vec3 pos4 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-170.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-170.0)));
                Vec3 pos5 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(80.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(80.0)));
                Vec3 pos6 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(100.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(100.0)));
                Vec3 pos7 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-80.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-800.0)));
                Vec3 pos8 = pos.add(getRadius()*0.75 * Mth.cos((float) Math.toRadians(-100.0)),0,getRadius()*0.75 * Mth.sin((float) Math.toRadians(-100.0)));
                StaticParticle.spawnParticleLineWithSpeed(level,pos1,pos2,ModParticles.LIGHT.get(),20,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos3,pos4,ModParticles.LIGHT.get(),20,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos1,pos3,ModParticles.LIGHT.get(),150,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos2,pos4,ModParticles.LIGHT.get(),150,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos5,pos6,ModParticles.LIGHT.get(),20,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos7,pos8,ModParticles.LIGHT.get(),20,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos5,pos7,ModParticles.LIGHT.get(),150,new Vec3(0,0.2,0),1.0);
                StaticParticle.spawnParticleLineWithSpeed(level,pos6,pos8,ModParticles.LIGHT.get(),150,new Vec3(0,0.2,0),1.0);

                double r = this.getRadius();
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r);
                for (_ImmortalMob mob : mobs) {
                    Player player = getOwner();
                    if (player != null){
                        ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_LIGHT,player);
                        mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 3F));
                    }
                }
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
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
