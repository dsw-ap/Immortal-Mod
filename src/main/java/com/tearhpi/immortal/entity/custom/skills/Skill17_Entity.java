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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill17_Entity extends Entity {
    private static final int LIFE_TICKS = 20;
    private int life;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill17_Entity.class, EntityDataSerializers.FLOAT);
    //记录攻击过的敌人
    private List<_ImmortalMob> mobsHaveBeenAttacked = new ArrayList<>();

    public Skill17_Entity(EntityType<? extends Skill17_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill17_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill17_Entity e = new Skill17_Entity(ModEntityTypes.SKILL17_ENTITY.get(), level);
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
        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);
        //客户端 新增粒子
        if (this.level().isClientSide) {
            Random rand = new Random();
            for (int i = 0; i < 5; i++) {
                level().addParticle(ModParticles.WATER.get(),this.getX()+rand.nextFloat(-0.2f,0.2f), this.getY()+rand.nextFloat(-0.2f,0.2f), this.getZ()+rand.nextFloat(-0.2f,0.2f), 0.0D, 0.0D, 0.0D);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            double r = getRadius();
            // 搜索指定范围内的所有 Mob
            List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                    _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive()
            );
            for (_ImmortalMob mob : mobs) {
                if (!mobsHaveBeenAttacked.contains(mob)) {
                    Player player = getOwner();
                    if (player != null){
                        ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
                        mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 1.5F));
                        mob.addWaterAmount(10);
                    }
                    mobsHaveBeenAttacked.add(mob);
                }
            }
            if (++life >= LIFE_TICKS) {
                this.discard();
            }
            if (this.isInsideSolid()) {
                this.discard();
            }
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
}
