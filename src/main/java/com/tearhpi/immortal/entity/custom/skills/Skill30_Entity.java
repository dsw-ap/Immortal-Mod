package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill30_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 200;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill30_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill30_Entity.class, EntityDataSerializers.INT);


    public Skill30_Entity(EntityType<? extends Skill30_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill30_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill30_Entity e = new Skill30_Entity(ModEntityTypes.SKILL30_ENTITY.get(), level);
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
            Random rand = new Random();
            Vec3 pos = this.position();
            level().addParticle(ModParticles.AIR.get(), pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()),rand.nextFloat(-0.05f,0.05f), 0.1, rand.nextFloat(-0.05f,0.05f));
            level().addParticle(ModParticles.AIR.get(), pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()+Mth.PI/2),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()+Mth.PI/2),rand.nextFloat(-0.05f,0.05f), 0.1, rand.nextFloat(-0.05f,0.05f));
            level().addParticle(ModParticles.AIR.get(), pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()+Mth.PI),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()+Mth.PI),rand.nextFloat(-0.05f,0.05f), 0.1, rand.nextFloat(-0.05f,0.05f));
            level().addParticle(ModParticles.AIR.get(), pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()+3*Mth.PI/2),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()+3*Mth.PI/2),rand.nextFloat(-0.05f,0.05f), 0.1, rand.nextFloat(-0.05f,0.05f));

            level().addParticle(ParticleTypes.FLAME, pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()),rand.nextFloat(-0.05f,0.05f), 0.2, rand.nextFloat(-0.05f,0.05f));
            level().addParticle(ModParticles.AIR.get(), pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()+Mth.PI/2),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()+Mth.PI/2),rand.nextFloat(-0.05f,0.05f), 0.2, rand.nextFloat(-0.05f,0.05f));
            level().addParticle(ParticleTypes.FLAME, pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()+Mth.PI),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()+Mth.PI),rand.nextFloat(-0.05f,0.05f), 0.2, rand.nextFloat(-0.05f,0.05f));
            level().addParticle(ModParticles.AIR.get(), pos.x+getRadius()*Mth.cos(Mth.PI/32*getLife()+3*Mth.PI/2),pos.y+0.5,pos.z+getRadius()*Mth.sin(Mth.PI/32*getLife()+3*Mth.PI/2),rand.nextFloat(-0.05f,0.05f), 0.2, rand.nextFloat(-0.05f,0.05f));
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            double r = getRadius();
            List<Player> mobs = this.level().getEntitiesOfClass(
                    Player.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
            );
            for (Player p : mobs) {
                ImposeEffect.ApplyEffectLayer(p, _ModEffects.SKILL30_EFFECT_ADD_DMG.get(),30);
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
