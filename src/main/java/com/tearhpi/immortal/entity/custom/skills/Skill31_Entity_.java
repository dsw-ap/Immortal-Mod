package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill31_Entity_ extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 20;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill31_Entity_.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill31_Entity_.class, EntityDataSerializers.INT);
    private List<_ImmortalMob> mobsHaveBeenAttacked = new ArrayList<>();

    public Skill31_Entity_(EntityType<? extends Skill31_Entity_> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill31_Entity_ spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill31_Entity_ e = new Skill31_Entity_(ModEntityTypes.SKILL31_ENTITY_.get(), level);
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
        //System.out.println(Mth.cos((float) Math.toRadians(getLife()*4.5)));
        Vec3 mot = this.getDeltaMovement().scale(Mth.cos((float) Math.toRadians(getLife()*4.5)));
        this.move(MoverType.SELF, mot);
        //客户端
        if (this.level().isClientSide) {
            //主向量
            Random rand = new Random();
            Vec3 MainMot = this.getDeltaMovement().normalize();
            Vec3 SideMot = MainMot.cross(new Vec3(0,1,0)).normalize();
            Vec3 pos = this.position();
            level().addParticle(ModParticles.SKILL31_Particle.get(), pos.x, pos.y, pos.z, rand.nextFloat(-0.05f,0.05f), 0.05, rand.nextFloat(-0.05f,0.05f));
            for (int i = 1; i < 2*getRadius(); i++) {
                level().addParticle(ModParticles.SKILL31_Particle.get(), pos.x+SideMot.x/2*i-0.1*i*MainMot.x, pos.y, pos.z+SideMot.z/2*i-0.1*i*MainMot.z, rand.nextFloat(-0.05f,0.05f), 0.05, rand.nextFloat(-0.05f,0.05f));
            }
            for (int i = 1; i < 2*getRadius(); i++) {
                level().addParticle(ModParticles.SKILL31_Particle.get(), pos.x-SideMot.x/2*i-0.1*i*MainMot.x, pos.y, pos.z-SideMot.z/2*i-0.1*i*MainMot.z, rand.nextFloat(-0.05f,0.05f), 0.05, rand.nextFloat(-0.05f,0.05f));
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (getLife() % 1 == 0) {
                double r = getRadius();
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                );
                for (_ImmortalMob mob : mobs ) {
                    if (!mobsHaveBeenAttacked.contains(mob)) {
                        //二次判断 是否在范围内
                        Random rand = new Random();
                        Vec3 MainMot = this.getDeltaMovement().normalize();
                        Vec3 SideMot = MainMot.cross(new Vec3(0,1,0)).normalize();
                        Vec3 pos = this.position();
                        boolean b = false;
                        for (int i = (int) Math.ceil(-getRadius()); i < (int) Math.floor(getRadius());i++) {
                            if (mob.distanceToSqr(pos.add(SideMot.scale(i))) <= 2.25) b = true;
                        }
                        if (b) {
                            //伤害
                            ModDamageSources modDamageSources = new ModDamageSources(mob.level().registryAccess());
                            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,getOwner());
                            mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.8f));
                            //
                            mobsHaveBeenAttacked.add(mob);
                        }
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
