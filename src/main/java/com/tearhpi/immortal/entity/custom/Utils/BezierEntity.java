package com.tearhpi.immortal.entity.custom.Utils;

import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.effect.imposeEffect.ImposeEffect;
import com.tearhpi.immortal.entity.ModEntityTypes;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import com.tearhpi.immortal.particle.ModParticles;
import com.tearhpi.immortal.util.OutPutDamageInfo;
import com.tearhpi.immortal.util.statics.ExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class BezierEntity extends Entity {
    //最大生命上限
    private int LIFE_TICKS = 200;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(BezierEntity.class, EntityDataSerializers.INT);
    //贝塞尔
    private Vec3 Start_Point;
    private Vec3 Final_Point;
    private Supplier<Vec3> Final_Point_Sup;
    private Vec3 MidPoint_1;//靠近开始点
    private Vec3 MidPoint_2;//靠近结束点
    private int Rot;//Rot:旋转角,限定为(10,170);
    private int Range;//Range:范围(1,8)
    private SimpleParticleType ParticleT;
    private Vec3 HistoryPos;
    private boolean CanInsideSolid;
    //private

    public BezierEntity(EntityType<? extends BezierEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static BezierEntity spawn(ServerLevel level, ServerPlayer owner, Vec3 Start_Point, Supplier<Vec3> Final_Point, int Life_Max, SimpleParticleType s,boolean CanInsideSolid) {
        BezierEntity e = new BezierEntity(ModEntityTypes.BEZIER_.get(), level);
        e.setPos(Start_Point);
        e.Start_Point = Start_Point;
        e.Final_Point_Sup = Final_Point;
        e.UpdateMidPoint();
        Random rand = new Random();
        e.Rot = rand.nextInt(10,170);
        e.Range = rand.nextInt(1,8);
        e.LIFE_TICKS = Life_Max;
        e.ParticleT = s;
        e.CanInsideSolid = CanInsideSolid;
        if (owner != null) {
            e.ownerId = owner.getUUID();
        }
        //System.out.println(e.Start_Point);
        //System.out.println(e.Final_Point);
        //System.out.println(e.MidPoint_1);
        //System.out.println(e.MidPoint_2);
        level.addFreshEntity(e);
        return e;
    }
    private void UpdateMidPoint() {
        this.Final_Point = this.Final_Point_Sup.get();
        Vec3 towards = this.Final_Point.subtract(this.Start_Point);
        Vec3 Up = new Vec3(0.0,1.0,0.0);
        Vec3 Side;
        if (towards.x != 0.0 && towards.z != 0.0) {
            Side = towards.cross(Up).normalize();
        } else {
            Side = new Vec3(1.0,0.0,0.0);
        }
        Vec3 UP_ = towards.cross(Side).normalize().scale(-1);
        double x = Mth.cos((float) Math.toRadians(this.Rot))*this.Range;
        double y = Mth.sin((float) Math.toRadians(this.Rot))*this.Range;
        this.MidPoint_1 = Start_Point.add(towards.scale(0.33)).add(Side.scale(x)).add(UP_.scale(y));
        this.MidPoint_2 = Start_Point.add(towards.scale(0.66)).add(Side.scale(x)).add(UP_.scale(y));
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        setLife(tag.getInt("life"));
        if (tag.hasUUID("owner")) this.ownerId = tag.getUUID("owner");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life", getLife());
        if (this.ownerId != null) tag.putUUID("owner", this.ownerId);
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(life, 0);
    }

    @Override
    public void tick() {
        super.tick();
        //客户端
        if (this.level().isClientSide) {
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
            this.HistoryPos = this.position();
            UpdateMidPoint();
            float t = (float) getLife() /LIFE_TICKS;
            //System.out.println(t);
            this.setPos(Start_Point.scale(Math.pow((1-t),3)).add(MidPoint_1.scale(3*t*(1-t)*(1-t))).add(MidPoint_2.scale(3*t*t*(1-t))).add(Final_Point.scale(t*t*t)));
            //System.out.println(this.position());
            //0.2格生成一个粒子
            Vec3 dist = this.position().subtract(this.HistoryPos);
            int count = (int) (dist.length()/0.2);
            double scale_base = dist.length()/count;
            for (int i = 0; i < count; i++) {
                Vec3 pos = this.position().add(dist.scale(i*scale_base));
                ((ServerLevel) level()).sendParticles(ParticleT,pos.x,pos.y,pos.z,1,0,0,0,0);
            }
            addLife();
            if (getLife() >= LIFE_TICKS) {
                this.discard();
            }
            if (this.isInsideSolid() && !this.CanInsideSolid) {
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

    public int getLife() { return entityData.get(life); }
    public void setLife(int r) { entityData.set(life,r); }
    public void addLife() { entityData.set(life,getLife()+1); }

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position().add(0.0,1.7,0.0));
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }
}
