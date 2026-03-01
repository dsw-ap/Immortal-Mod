package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._EffectQueries;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalBossMob;
import com.tearhpi.immortal.entity.custom._ImmortalEliteMob;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.entity.custom._ImmortalNormalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class Skill70_Entity extends Entity {
    //最大生命上限
    private static final int LIFE_TICKS = 240;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Float> radius = SynchedEntityData.defineId(Skill70_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill70_Entity.class, EntityDataSerializers.INT);


    public Skill70_Entity(EntityType<? extends Skill70_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill70_Entity spawn(ServerLevel level, ServerPlayer owner, Vec3 pos, Vec3 motion, float radius) {
        Skill70_Entity e = new Skill70_Entity(ModEntityTypes.SKILL70_ENTITY.get(), level);
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
            //外环圆
            Vec3 pos = this.position();
            Random random = new Random();
            for (int i = 0;i < 72;i++) {
                double step =  2 * Math.PI * i/72+getLife();
                level().addParticle(ModParticles.DARKNESS.get(),pos.x + getRadius() * Mth.cos((float) step),pos.y,pos.z + getRadius() * Mth.sin((float) step),0,0.5,0);
            }
            for (int i = 0;i < 50;i++) {
                double step = random.nextFloat(0f, (float) (2*Math.PI));
                double r = Math.sqrt(random.nextDouble(0d,getRadius()*getRadius()));
                level().addParticle(ParticleTypes.LARGE_SMOKE,pos.x + r * Mth.cos((float) step),pos.y,pos.z + r * Mth.sin((float) step),0,random.nextFloat(0.05f,0.2f),0);
            }
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            if (getLife() % 10 == 0) {
                Random rand = new Random();
                float r = getRadius();
                List<_ImmortalMob> mobs = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r
                );
                for (_ImmortalMob mob : mobs) {
                    Player player = getOwner();
                    if (player != null){
                        ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                        DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,player);
                        mob.hurt(damagesource, MainDamage.getDamage(player,damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 0.8F));
                    }
                    if (rand.nextInt(5) == 1) {
                        ImposeEffect.ApplyEffectLayer(mob, _EffectQueries.randomByEnemyDebuffTag(), 160);
                    }
                }
            }

            addLife();
            if (getLife() >= LIFE_TICKS) {
                Random rand_ = new Random();
                for (int i = 0; i < 200; i++) {
                    double rot = rand_.nextInt(1,360)*2*Mth.PI/360;
                    int scale = rand_.nextInt(1,20);
                    ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,this.getX()+scale*Mth.cos((float) rot),this.getY()-10,this.getZ()+scale*Mth.sin((float) rot),1,0,0,0,0);
                }
                final double R = getRadius();

                List<_ImmortalMob> mobs_ = this.level().getEntitiesOfClass(
                        _ImmortalMob.class, this.getBoundingBox().inflate(R), _mob_ -> _mob_.isAlive() && _mob_.distanceToSqr(this) <= R * R
                );
                for (_ImmortalMob mob : mobs_) {
                    ModDamageSources modDamageSources = new ModDamageSources(this.level().registryAccess());
                    DamageSource damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,getOwner());
                    //百分比伤害
                    Collection<MobEffectInstance> effects = mob.getActiveEffects();
                    List<MobEffectInstance> toUpdate = new ArrayList<>();
                    double val = 0;
                    for (MobEffectInstance inst : effects) {
                        MobEffect effect = inst.getEffect();
                        if (effect.getCategory() != MobEffectCategory.HARMFUL) continue;
                        ResourceLocation id = this.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.MOB_EFFECT).getKey(effect);
                        if (id == null) continue;
                        int dur = inst.getDuration();
                        val += (double) dur / 200;
                    }
                    if (mob instanceof _ImmortalBossMob) {
                        val = Math.min(val,40);
                    } else if (mob instanceof _ImmortalEliteMob) {
                        val = Math.min(val,80);
                    } else if (mob instanceof _ImmortalNormalMob) {
                        val = Math.min(val,80);
                    }
                    mob.hurt(damagesource, (float) (MainDamage.getDamage(getOwner(),damagesource, OutPutDamageInfo.getOutPutDamageInfo(mob), 8f)+mob.getHealth()*val/100f));
                }
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
