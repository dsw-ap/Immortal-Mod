package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Skill21_Entity extends AbstractArrow {
    private static final int LIFE_TICKS = 200;
    private int life;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill21_Entity.class, EntityDataSerializers.FLOAT);

    private List<_ImmortalMob> mobsHaveBeenAttacked = new ArrayList<>();

    public Skill21_Entity(EntityType<? extends Skill21_Entity> type, Level level) {
        super(type, level);
        //this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill21_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill21_Entity e = new Skill21_Entity(ModEntityTypes.SKILL21_ENTITY.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        level.addFreshEntity(e);
        return e;
    }


    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        setRadius(tag.getFloat("radius"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        //this.setWeaponAttribute(tag.getInt("wp_attribute"));
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putFloat("radius", getRadius());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(radius, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        //Vec3 mot = this.getDeltaMovement();
        //this.move(MoverType.SELF, mot);
        //客户端 新增粒子
        if (this.level().isClientSide) {
            /*
            Vec3 dir3D = this.getDeltaMovement().cross(new Vec3(0.0f,1.0f,0.0f)).normalize(); // -90°
            for(int i = -1;i<getLife()*getLife()/40;i++){
                Random rand = new Random();
                level().addParticle(ModParticles.WATER.get(), this.getX() + rand.nextFloat(-0.5f,0.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() + rand.nextFloat(-0.5f,0.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() + rand.nextFloat(0.5f,1.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() + rand.nextFloat(0.5f,1.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() + rand.nextFloat(1.5f,2.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() + rand.nextFloat(1.5f,2.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() - rand.nextFloat(0.5f,1.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() - rand.nextFloat(0.5f,1.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
                level().addParticle(ModParticles.WATER.get(), this.getX() - rand.nextFloat(1.5f,2.5f)*dir3D.x, this.getY()+i*0.2, this.getZ() - rand.nextFloat(1.5f,2.5f)*dir3D.z, 0.0D, 0.0D, 0.0D);
            }

             */
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {

            if (++life >= LIFE_TICKS) {
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

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }

    @Override
    protected void onHitEntity(net.minecraft.world.phys.EntityHitResult hitResult) {
        // 不调用 super，避免原版箭的伤害、音效、附加效果
        // 可以选择在这里直接 discard()，或者啥也不做：
        Entity enemy = hitResult.getEntity();
        if (enemy instanceof _ImmortalMob immortalMob) {
            immortalMob.addWaterAmount(10);
            ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
            DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,getOwner());
            immortalMob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(immortalMob), 2.5F));
        }
        this.discard();
    }
}
