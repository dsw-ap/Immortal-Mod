package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.particle.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

import static com.tearhpi.immortal.util.statics.SkillTagRelated.getyRot;

public class Skill7_Entity_ extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 19;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill7_Entity_.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill7_Entity_.class, EntityDataSerializers.INT);


    public Skill7_Entity_(EntityType<? extends Skill7_Entity_> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建

    public static Skill7_Entity_ spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill7_Entity_ e = new Skill7_Entity_(ModEntityTypes.SKILL7_ENTITY_.get(), level);
        e.setPos(pos);
        float rot = getyRot(owner)+90;
        if (rot > 180){
            rot -= 360;
        }
        e.applyAllYaw(rot);
        e.setDeltaMovement(motion);
        e.ownerId = owner.getUUID();
        e.setRadius(radius);
        level.addFreshEntity(e);
        return e;
    }
    private void applyAllYaw(float yaw) {
        this.setYRot(yaw);
        this.setYHeadRot(yaw);
        this.yRotO = yaw;
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
            Vec3 start = this.getEyePosition(1.0f);
            Vec3 dir = this.getLookAngle().normalize();
            Vec3 side = dir.cross(new Vec3(0.0,1.0,0.0)).normalize();
            if (this.getLife() <= 9) {
                constructFrame((ClientLevel) level(),dir,side,1.5f);
            } else {
                constructFrame((ClientLevel) level(),dir,side,3.0f*getLife()/LIFE_TICKS);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {

            addLife();
            if (getLife() >= LIFE_TICKS) {
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
    private void constructFrame(ClientLevel level, Vec3 Front, Vec3 Side, float height){
        Vec3 pos = this.position();
        for (int i = 0;i<15;i++){
            float s = (float) (i - 7) / 5;
            addParticleSimple(level,pos.add(Front.scale(s)).add(Side.scale(0.4)).add(0.0,height,0.0));
            addParticleSimple(level,pos.add(Front.scale(s)).add(Side.scale(-0.4)).add(0.0,height,0.0));
        }
        for (int i = 0;i<15;i++){
            float s = (float) (i - 7) / 5;
            addParticleSimple(level,pos.add(Front.scale(s)).add(Side.scale(0.4)).add(0.0,height,0.0));
            addParticleSimple(level,pos.add(Front.scale(s)).add(Side.scale(-0.4)).add(0.0,height,0.0));
        }
        for (int i = 0;i<5;i++){
            float s = ((float) i/5+1.5f);
            addParticleSimple(level,pos.add(Front.scale(s)).add(Side.scale(0.5)).add(0.0,height,0.0));
            addParticleSimple(level,pos.add(Front.scale(s)).add(Side.scale(-0.5)).add(0.0,height,0.0));
            addParticleSimple(level,pos.add(Front.scale(-s)).add(Side.scale(0.5)).add(0.0,height,0.0));
            addParticleSimple(level,pos.add(Front.scale(-s)).add(Side.scale(-0.5)).add(0.0,height,0.0));
        }
        for (int i = 0;i<5;i++){
            float s = ((float) (i-2)/5);
            addParticleSimple(level,pos.add(Front.scale(2.5)).add(Side.scale(s)).add(0.0,height,0.0));
            addParticleSimple(level,pos.add(Front.scale(-2.5)).add(Side.scale(s)).add(0.0,height,0.0));
        }
    }
    private void addParticleSimple(ClientLevel level,Vec3 pos){
        level.addParticle(ModParticles.SKILL7_Particle.get(), pos.x, pos.y, pos.z, 0, 0, 0);
    }
}
