package com.tearhpi.immortal.entity.custom;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.NormalAttackEnergySyncToClientPacket;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.sound.ModSounds;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class WandNormalAttackBullet_Enforced extends Entity {
    private int LIFE_TICKS; // 1s
    private int life;
    @Nullable
    private UUID ownerId; // 记录施放者
    private static final EntityDataAccessor<Integer> DATA_WEAPON_ATTR =
            SynchedEntityData.defineId(WandNormalAttackBullet_Enforced.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GRADE =
            SynchedEntityData.defineId(WandNormalAttackBullet_Enforced.class, EntityDataSerializers.INT);

    public WandNormalAttackBullet_Enforced(EntityType<? extends WandNormalAttackBullet_Enforced> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static WandNormalAttackBullet_Enforced spawn(ServerLevel level, ServerPlayer owner, int weaponAttributeAttack, Vec3 pos, Vec3 motion,int grade) {
        WandNormalAttackBullet_Enforced e = new WandNormalAttackBullet_Enforced(ModEntityTypes.WAND_NORMAL_ATTACK_BULLET_ENFORCED.get(), level);
        owner.playNotifySound(ModSounds.NORMAL_ATTACK.get(), SoundSource.PLAYERS,1.0f,1.0f);
        e.setWeaponAttribute(weaponAttributeAttack);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        e.setGrade(grade);
        e.ownerId = owner.getUUID();
        level.addFreshEntity(e);
        e.LIFE_TICKS += 10+grade*2;
        return e;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
        this.LIFE_TICKS = tag.getInt("max_life");
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
        this.setWeaponAttribute(tag.getInt("wp_attribute"));
        this.setGrade(tag.getInt("grade"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", this.life);
        tag.putInt("max_life",this.LIFE_TICKS);
        tag.putInt("wp_attribute",this.getWeaponAttribute());
        tag.putInt("grade",getGrade());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_WEAPON_ATTR,0);
        this.entityData.define(GRADE,0);
    }


    @Override
    public void tick() {
        super.tick();
        //客户端 新增粒子
        if (this.level().isClientSide) {
            return;
        }
        //服务端 运行逻辑
        // 移动
        Vec3 v = this.getDeltaMovement();
        this.setPos(this.getX() + v.x, this.getY() + v.y, this.getZ() + v.z);
        // 碰墙消失
        if (isInsideSolid()) {
            this.discard();
            return;
        }
        // 查找附近 1 格 Mob
        int val = getGrade();
        AABB box = this.getBoundingBox().inflate(0.15);
        List<_ImmortalMob> mobs = ((ServerLevel)this.level()).getEntitiesOfClass(_ImmortalMob.class, box,
                m -> m.isAlive() && !m.isRemoved());
        if (!mobs.isEmpty()) {
            List<_ImmortalMob> mobs_ = ((ServerLevel)this.level()).getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(getGrade()),
                    m -> m.isAlive() && m.distanceToSqr(this) <= val * val);
            if (mobs_.size() <= val) {

            } else {
                List<_ImmortalMob> copy = new ArrayList<>(mobs_); // 不破坏原 list
                Collections.shuffle(copy, new Random());      // 洗牌
                mobs_ = copy.subList(0, val);
            }
            ServerPlayer owner = getOwner();
            if (owner != null) {
                ModDamageSources modDamageSources = new ModDamageSources(owner.level().registryAccess());
                DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_NONE, owner);
                if (this.getWeaponAttribute() == 1) {
                    damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE, owner);
                } else if (this.getWeaponAttribute() == 2) {
                    damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER, owner);
                } else if (this.getWeaponAttribute() == 3) {
                    damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR, owner);
                } else if (this.getWeaponAttribute() == 4) {
                    damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH, owner);
                } else if (this.getWeaponAttribute() == 5) {
                    damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_LIGHT, owner);
                } else if (this.getWeaponAttribute() == 6) {
                    damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS, owner);
                }
                for (_ImmortalMob mob : mobs_) {
                    mob.hurt(damagesource, MainDamage.getDamage(owner, damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), getGrade()));
                    ((ServerLevel)level()).sendParticles(ParticleTypes.CRIT,mob.position().x,mob.position().y+1,mob.position().z,20,0.2,0.4,0.2,0.35);
                }
            }
            this.discard();
            return;
        }

        // 寿命
        if (++life >= LIFE_TICKS) {
            this.discard();
        }
    }
    public int getGrade() {
        return this.entityData.get(GRADE);
    }
    public void setGrade(int v) {
        this.entityData.set(GRADE, v);
    }
    @Nullable
    public ServerPlayer getOwner() {
        if (this.ownerId == null) return null;
        if (!(this.level() instanceof ServerLevel sl)) return null;
        return sl.getServer().getPlayerList().getPlayer(this.ownerId);
    }

    public boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position());
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
    public int getWeaponAttribute() {
        return this.entityData.get(DATA_WEAPON_ATTR);
    }
    public void setWeaponAttribute(int v) {
        this.entityData.set(DATA_WEAPON_ATTR, v);
    }
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double max = 256.0;
        return distance < max * max;
    }
}
