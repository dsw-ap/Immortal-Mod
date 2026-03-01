package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill64_Entity_ extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 100;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill64_Entity_.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill64_Entity_.class, EntityDataSerializers.INT);
    public int effect1_Level; //灼烧
    public int effect1_Time; //灼烧
    public int effect2_Level; //湮灭
    public int effect2_Time; //湮灭
    public int effect3_Level; //禁锢
    public int effect3_Time; //禁锢
    public int effect4_Level; //侵蚀
    public int effect4_Time; //侵蚀
    public int effect5_Level; //重伤
    public int effect5_Time; //重伤
    public int effect6_Level; //沉默
    public int effect6_Time; //沉默
    public int effect7_Level; //噩梦
    public int effect7_Time; //噩梦
    public int effect8_Level; //缓慢
    public int effect8_Time; //缓慢
    public int effect9_Level; //浮空
    public int effect9_Time; //浮空

    public Skill64_Entity_(EntityType<? extends Skill64_Entity_> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill64_Entity_ spawn(ServerLevel level, Vec3 pos, Vec3 motion, float radius) {
        Skill64_Entity_ e = new Skill64_Entity_(ModEntityTypes.SKILL64_ENTITY_.get(), level);
        e.setPos(pos);
        e.setDeltaMovement(motion);
        //e.ownerId = owner.getUUID();
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
        this.effect1_Level = tag.getInt("e1L");
        this.effect1_Time = tag.getInt("e1T");
        this.effect2_Level = tag.getInt("e2L");
        this.effect2_Time = tag.getInt("e2T");
        this.effect3_Level = tag.getInt("e3L");
        this.effect3_Time = tag.getInt("e3T");
        this.effect4_Level = tag.getInt("e4L");
        this.effect4_Time = tag.getInt("e4T");
        this.effect5_Level = tag.getInt("e5L");
        this.effect5_Time = tag.getInt("e5T");
        this.effect6_Level = tag.getInt("e6L");
        this.effect6_Time = tag.getInt("e6T");
        this.effect7_Level = tag.getInt("e7L");
        this.effect7_Time = tag.getInt("e7T");
        this.effect8_Level = tag.getInt("e8L");
        this.effect8_Time = tag.getInt("e8T");
        this.effect9_Level = tag.getInt("e9L");
        this.effect9_Time = tag.getInt("e9T");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", getLife());
        tag.putFloat("radius", getRadius());
        //tag.putInt("wp_attribute",this.getWeaponAttribute());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);

        tag.putInt("e1L", this.effect1_Level);
        tag.putInt("e2L", this.effect2_Level);
        tag.putInt("e3L", this.effect3_Level);
        tag.putInt("e4L", this.effect4_Level);
        tag.putInt("e5L", this.effect5_Level);
        tag.putInt("e6L", this.effect6_Level);
        tag.putInt("e7L", this.effect7_Level);
        tag.putInt("e8L", this.effect8_Level);
        tag.putInt("e9L", this.effect9_Level);
        tag.putInt("e1T", this.effect1_Time);
        tag.putInt("e2T", this.effect2_Time);
        tag.putInt("e3T", this.effect3_Time);
        tag.putInt("e4T", this.effect4_Time);
        tag.putInt("e5T", this.effect5_Time);
        tag.putInt("e6T", this.effect6_Time);
        tag.putInt("e7T", this.effect7_Time);
        tag.putInt("e8T", this.effect8_Time);
        tag.putInt("e9T", this.effect9_Time);
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
            Random random = new Random();
            for (int i = 0; i < 40; i++) {
                float j = random.nextFloat(0,getRadius());
                float k = random.nextFloat(0f, (float) (2*Math.PI));
                level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + j * Mth.cos(k), this.getY(), this.getZ() + j * Mth.sin(k), 0.0D, random.nextFloat(0.05f,0.1f), 0.0D);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if ((getLife()+1) % 20 == 0) {
                Random random = new Random();
                double r = this.getRadius();
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r);
                for (_ImmortalMob mob : mobs) {
                    if (random.nextInt(1,100) <= 100) {
                        /*
                        public int effect1_Level; //灼烧
                        public int effect1_Time; //灼烧
                        public int effect2_Level; //湮灭
                        public int effect2_Time; //湮灭
                        public int effect3_Level; //禁锢
                        public int effect3_Time; //禁锢
                        public int effect4_Level; //侵蚀
                        public int effect4_Time; //侵蚀
                        public int effect5_Level; //重伤
                        public int effect5_Time; //重伤
                        public int effect6_Level; //沉默
                        public int effect6_Time; //沉默
                        public int effect7_Level; //噩梦
                        public int effect7_Time; //噩梦
                        public int effect8_Level; //缓慢
                        public int effect8_Time; //缓慢
                        public int effect9_Level; //浮空
                        public int effect9_Time; //浮空
                         */
                        if (effect1_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.FIRING_EFFECT.get(),effect1_Time/2, effect1_Level-1);
                        }
                        if (effect2_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.ANNIHILATE_EFFECT.get(),effect2_Time/2, effect2_Level-1);
                        }
                        if (effect3_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.IMPRISIONED_EFFECT.get(),effect3_Time/2, effect3_Level-1);
                        }
                        if (effect4_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.EROSION_EFFECT.get(),effect4_Time/2, effect4_Level-1);
                        }
                        if (effect5_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.INJURY_EFFECT.get(),effect5_Time/2, effect5_Level-1);
                        }
                        if (effect6_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.MUTE_EFFECT.get(),effect6_Time/2, effect6_Level-1);
                        }
                        if (effect7_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,_ModEffects.NIGHTMARE_EFFECT.get(),effect7_Time/2, effect7_Level-1);
                        }
                        if (effect8_Level != 0) {
                            ImposeEffect.ImposeLayer(mob, MobEffects.MOVEMENT_SLOWDOWN,effect8_Time/2, effect8_Level-1);
                        }
                        if (effect9_Level != 0) {
                            ImposeEffect.ImposeLayer(mob,MobEffects.LEVITATION,effect9_Time/2, effect1_Level-1);
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
