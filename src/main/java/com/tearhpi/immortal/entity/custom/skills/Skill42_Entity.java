package com.tearhpi.immortal.entity.custom.skills;

import com.mojang.math.Transformation;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill42_Entity extends Display.ItemDisplay {
    //最大生命上限
    private static final int LIFE_TICKS = 200;
    @Nullable
    public UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill42_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill42_Entity.class, EntityDataSerializers.INT);
    private List<_ImmortalMob> mobsHaveBeenAttacked = new ArrayList<>();


    public Skill42_Entity(EntityType<? extends Skill42_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill42_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill42_Entity e = new Skill42_Entity(ModEntityTypes.SKILL42_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        //level.addFreshEntity(e);
        return e;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setLife(tag.getInt("life"));
        setRadius(tag.getFloat("radius"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("life", getLife());
        tag.putFloat("radius", getRadius());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(radius, 0.0f);
        this.entityData.define(life, 0);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 mot = new Vec3(0.0f,-1.0f,0.0f);
        if (getLife() >= 38) {
            this.move(MoverType.SELF, mot);
        }
        //客户端
        if (this.level().isClientSide) {

        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (getLife() == 1) {
                ((com.tearhpi.immortal.mixin.DisplayMixin)(Object)this).immortal$setInterpolationDelay(1);
                ((com.tearhpi.immortal.mixin.DisplayMixin)(Object)this).immortal$setInterpolationDuration(33);
                ((com.tearhpi.immortal.mixin.DisplayMixin)(Object)this).immortal$setTransformation(new Transformation(
                        new Vector3f(0f, 0f, 0f), new Quaternionf(), new Vector3f(getRadius(), 0.2f, getRadius()), new Quaternionf()
                ));
            }
            if (getLife() >= 38) {
                double r = getRadius();
                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), _mob_ -> _mob_.isAlive() && !mobsHaveBeenAttacked.contains(_mob_) && Math.abs(_mob_.position().y - this.position().y) < 1.0f
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob),5.5f));
                    mobsHaveBeenAttacked.add(mob);
                    Random rand = new Random();
                    int val = rand.nextInt(1,100);
                    if (val <= 30) {
                        ImposeEffect.ApplyEffectLayer(mob, _ModEffects.ANNIHILATE_EFFECT.get(), 100);
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
