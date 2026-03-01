package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Skill64_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 40;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill64_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill64_Entity.class, EntityDataSerializers.INT);


    public Skill64_Entity(EntityType<? extends Skill64_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill64_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill64_Entity e = new Skill64_Entity(ModEntityTypes.SKILL64_ENTITY.get(), level);
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
        Vec3 mot = this.getDeltaMovement();
        this.move(MoverType.SELF, mot);
        //客户端
        if (this.level().isClientSide) {
            Vec3 pos = this.position();
            Random rand = new Random();
            for (int i = 0; i < 5; i++) {
                level().addParticle(ParticleTypes.SMOKE, pos.x+rand.nextFloat(-0.3f,0.3f),pos.y+rand.nextFloat(-0.3f,0.3f)+1.5,pos.z+rand.nextFloat(-0.3f,0.3f),0,0,0);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            double r = this.getRadius();
            List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r);
            if (!mobs.isEmpty()) {
                _ImmortalMob mob = mobs.get(0);
                Player player = getOwner();
                if (player != null){
                    Random rand = new Random();
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,player);
                    mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 5.0F));

                    ExplosionParticle.horizonParticleExpServerLevel((ServerLevel) level(),ParticleTypes.SMOKE,0.2,72,mob.position().add(0.0,1.0,0.0));
                    ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(),ParticleTypes.SMOKE,0.2,72,mob.position().add(0.0,1.0,0.0),new Vec3(random.nextFloat(),random.nextFloat(),random.nextFloat()));
                    ExplosionParticle.tiltParticleExpServerLevel((ServerLevel) level(),ParticleTypes.SMOKE,0.2,72,mob.position().add(0.0,1.0,0.0),new Vec3(random.nextFloat(),random.nextFloat(),random.nextFloat()));

                    Vec3 p3d = mob.position().add(0.0,0.1,0.0);
                    double i = 1.0;
                    if (getOwner() != null) {
                        i = getOwner().getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get());
                    }
                    Skill64_Entity_ e = Skill64_Entity_.spawn((ServerLevel) this.level(), p3d, new Vec3(0,0,0), (float) (5*i));
                    this.level().addFreshEntity(e);
                    //属性赋予
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
                    if(mob.hasEffect(_ModEffects.FIRING_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.FIRING_EFFECT.get());
                        e.effect1_Level = effect.getAmplifier()+1;
                        e.effect1_Time = effect.getDuration();
                        //System.out.println(e.effect1_Level);
                        //System.out.println(e.effect1_Time);
                    }
                    if(mob.hasEffect(_ModEffects.ANNIHILATE_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.ANNIHILATE_EFFECT.get());
                        e.effect2_Level = effect.getAmplifier()+1;
                        e.effect2_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(_ModEffects.IMPRISIONED_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.IMPRISIONED_EFFECT.get());
                        e.effect3_Level = effect.getAmplifier()+1;
                        e.effect3_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(_ModEffects.EROSION_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.EROSION_EFFECT.get());
                        e.effect4_Level = effect.getAmplifier()+1;
                        e.effect4_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(_ModEffects.INJURY_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.INJURY_EFFECT.get());
                        e.effect5_Level = effect.getAmplifier()+1;
                        e.effect5_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(_ModEffects.MUTE_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.MUTE_EFFECT.get());
                        e.effect6_Level = effect.getAmplifier()+1;
                        e.effect6_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(_ModEffects.NIGHTMARE_EFFECT.get())) {
                        MobEffectInstance effect = mob.getEffect(_ModEffects.NIGHTMARE_EFFECT.get());
                        e.effect7_Level = effect.getAmplifier()+1;
                        e.effect7_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        MobEffectInstance effect = mob.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
                        e.effect8_Level = effect.getAmplifier()+1;
                        e.effect8_Time = effect.getDuration();
                    }
                    if(mob.hasEffect(MobEffects.LEVITATION)) {
                        MobEffectInstance effect = mob.getEffect(MobEffects.LEVITATION);
                        e.effect9_Level = effect.getAmplifier()+1;
                        e.effect9_Time = effect.getDuration();
                    }
                }
                Vec3 p = mob.position();
                ((ServerLevel) level()).sendParticles(ModParticles.DARKNESS.get(),p.x,p.y,p.z,10,1,2,1,0.05);
                this.discard();
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
        BlockPos bp = BlockPos.containing(this.position().add(0.0,1.7,0.0));
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
