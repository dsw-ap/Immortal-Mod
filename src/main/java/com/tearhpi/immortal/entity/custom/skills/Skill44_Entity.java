package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill44_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 200;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill44_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill44_Entity.class, EntityDataSerializers.INT);


    public Skill44_Entity(EntityType<? extends Skill44_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill44_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill44_Entity e = new Skill44_Entity(ModEntityTypes.SKILL44_ENTITY.get(), level);
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
            for (int i = 0; i < 40; i++) {
                double step = 2 * Math.PI * i / 40 +getLife();
                Vec3 pos = this.position().add(new Vec3(getRadius() * Mth.cos((float) step), 0, getRadius() * Mth.sin((float) step)));
                level().addParticle(ModParticles.EARTH.get(), pos.x, pos.y, pos.z, 0, 0.1, 0);
            }
            for (int i = 0; i < 20; i++) {
                Random random = new Random();
                double step = random.nextInt(1,360);
                double length = random.nextDouble(0,getRadius());
                Vec3 pos = this.position().add(new Vec3(length * Mth.cos((float) Math.toRadians(step)), 0, getRadius() * Mth.sin((float) Math.toRadians(step))));
                level().addParticle(ModParticles.EARTH.get(), pos.x, pos.y, pos.z, 0, 0.05, 0);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (getLife() % 20 == 0) {
                double r = getRadius();
                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= r * r
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH,getOwner());
                    mob.hurt(damagesource, MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob),2.0f));
                }
                List<Player> players = this.level().getEntitiesOfClass(
                        Player.class, this.getBoundingBox().inflate(r), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= r * r && !_mob_.equals(this.getOwner())
                );
                for (Player player : players) {
                    ImposeEffect.ImposeEffectWithoutAmp(player, _ModEffects.SKILL44_EFFECT_OTHERS.get(),30);
                }
                if (this.getOwner() != null) {
                    ImposeEffect.ImposeEffectWithoutAmp(this.getOwner(), _ModEffects.SKILL44_EFFECT_SELF.get(),30);
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
