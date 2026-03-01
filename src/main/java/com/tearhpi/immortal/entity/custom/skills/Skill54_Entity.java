package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.core.BlockPos;
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
import java.util.UUID;

public class Skill54_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 160;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill54_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill54_Entity.class, EntityDataSerializers.INT);


    public Skill54_Entity(EntityType<? extends Skill54_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建

    public static Skill54_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill54_Entity e = new Skill54_Entity(ModEntityTypes.SKILL54_ENTITY.get(), level);
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
            for (int i = 0;i<72;i++) {
                float step = (float) (2*Math.PI*i/72f+getLife());
                Vec3 pos = this.position();
                //Random rand = new Random();
                level().addParticle(ModParticles.LIGHT.get(), pos.x+getRadius()* Mth.cos(step),pos.y+0.2,pos.z+getRadius()* Mth.sin(step),0,0.2,0);
                level().addParticle(ModParticles.LIGHT.get(), pos.x+getRadius()*0.9* Mth.cos(step),pos.y+0.2,pos.z+getRadius()*0.9*Mth.sin(step),0,0.1,0);
                //level().addParticle(ModParticles.LIGHT.get(), pos.x+getRadius()* Mth.cos(step),pos.y+0.2,pos.z+getRadius()* Mth.sin(step),0,0.2,0);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (getLife() % 10 == 0) {
                if (this.getOwner() != null) {
                    Player player = getOwner();
                    double r = this.getRadius();
                    List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r);
                    for (_ImmortalMob mob : mobs) {
                        mob.setskill54Amount((int) getOwner().getAttributeValue(ModAttributes.IMMORTAL_PLAYER_BUFF_AMOUNT.get()));
                        ImposeEffect.ImposeEffectWithoutAmp(mob, _ModEffects.SKILL54_LIGHT_EROSION.get(),20);
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
