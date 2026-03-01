package com.tearhpi.immortal.entity.custom.skills;

import com.tearhpi.immortal.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class Skill8_Entity_ extends Entity {
    //最大生命上限
    private int LIFE_TICKS = 200;
    @Nullable
    private UUID ownerId;
    private static final EntityDataAccessor<Integer> life = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Rot = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> Range = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> Start_Point_x = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Start_Point_y = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Start_Point_z = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> End_Point_x = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> End_Point_y = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> End_Point_z = SynchedEntityData.defineId(Skill8_Entity_.class,
            EntityDataSerializers.FLOAT);
    //贝塞尔
    private Vec3 Start_Pos_before;
    private Vec3 End_Pos_before;
    private Vec3 MidPoint_1;//靠近开始点
    private Vec3 MidPoint_2;//靠近结束点
    public List<Vec3> RenderPointList;
    private SimpleParticleType ParticleT;
    private Vec3 HistoryPos;
    private boolean CanInsideSolid;
    //private

    public Skill8_Entity_(EntityType<? extends Skill8_Entity_> type, Level level) {
        super(type, level);
        this.noPhysics = true; // 不受碰撞推动
    }

    // 便捷创建
    public static Skill8_Entity_ spawn(ServerLevel level, ServerPlayer owner, Vec3 Start_Point, Vec3 Final_Point,
                                       int Life_Max, SimpleParticleType s, boolean CanInsideSolid) {
        Skill8_Entity_ e = new Skill8_Entity_(ModEntityTypes.SKILL8_ENTITY_.get(), level);
        e.setPos(Start_Point);
        e.setStartPos(Start_Point);
        e.setEndPos(Final_Point);
        e.UpdateMidPoint();
        Random rand = new Random();
        e.setRot(rand.nextInt(10, 170));
        e.setRange(rand.nextInt(1, 8));
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
        Vec3 Start_Point = getStartPos();
        Vec3 Final_Point = getEndPos();
        if (Start_Pos_before != Start_Point || End_Pos_before != Final_Point) {
            //System.out.println(Start_Point + "," + Final_Point);
            Vec3 towards = Final_Point.subtract(Start_Point);
            Vec3 Up = new Vec3(0.0, 1.0, 0.0);
            Vec3 Side;
            if (towards.x != 0.0 && towards.z != 0.0) {
                Side = towards.cross(Up).normalize();
            } else {
                Side = new Vec3(1.0, 0.0, 0.0);
            }
            Vec3 UP_ = towards.cross(Side).normalize().scale(-1);
            double x = Mth.cos((float) Math.toRadians(getRot())) * getRange();
            double y = Mth.sin((float) Math.toRadians(getRot())) * getRange();
            this.MidPoint_1 = Start_Point.add(towards.scale(0.33)).add(Side.scale(x)).add(UP_.scale(y));
            this.MidPoint_2 = Start_Point.add(towards.scale(0.66)).add(Side.scale(x)).add(UP_.scale(y));
            this.RenderPointList = new ArrayList<>();
            int Max_value = 100;
            for (int i = 0; i < Max_value; i++) {
                float t = (float) i / Max_value;
                this.RenderPointList.add(Start_Point.scale(Math.pow((1 - t), 3)).add(MidPoint_1.scale(3 * t * (1 - t) * (1 - t))).add(MidPoint_2.scale(3 * t * t * (1 - t))).add(Final_Point.scale(t * t * t)).subtract(Start_Point));
            }
            //System.out.println(this.RenderPointList);
            Start_Pos_before = Start_Point;
            End_Pos_before = Final_Point;
        }
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
        this.entityData.define(Start_Point_x, 0.0f);
        this.entityData.define(Start_Point_y, 0.0f);
        this.entityData.define(Start_Point_z, 0.0f);
        this.entityData.define(End_Point_x, 0.0f);
        this.entityData.define(End_Point_y, 0.0f);
        this.entityData.define(End_Point_z, 0.0f);
        this.entityData.define(Rot, 0);
        this.entityData.define(Range, 0);
    }

    @Override
    public void tick() {
        super.tick();
        //客户端
        if (this.level().isClientSide) {
            UpdateMidPoint();
        }
        //服务端 运行逻辑
        //自转
        if (!this.level().isClientSide) {
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

    public int getLife() {
        return entityData.get(life);
    }

    public void setLife(int r) {
        entityData.set(life, r);
    }

    public void addLife() {
        entityData.set(life, getLife() + 1);
    }

    public Vec3 getEndPos() {
        return new Vec3(entityData.get(End_Point_x), entityData.get(End_Point_y), entityData.get(End_Point_z));
    }

    public void setEndPos(Vec3 vec) {
        entityData.set(End_Point_x, (float) vec.x);
        entityData.set(End_Point_y, (float) vec.y);
        entityData.set(End_Point_z, (float) vec.z);
    }

    public Vec3 getStartPos() {
        return new Vec3(entityData.get(Start_Point_x), entityData.get(Start_Point_y), entityData.get(Start_Point_z));
    }

    public void setStartPos(Vec3 vec) {
        entityData.set(Start_Point_x, (float) vec.x);
        entityData.set(Start_Point_y, (float) vec.y);
        entityData.set(Start_Point_z, (float) vec.z);
    }

    public int getRot() {
        return entityData.get(Rot);
    }

    public void setRot(int r) {
        entityData.set(Rot, r);
    }

    public int getRange() {
        return entityData.get(Range);
    }

    public void setRange(int r) {
        entityData.set(Range, r);
    }

    private boolean isInsideSolid() {
        BlockPos bp = BlockPos.containing(this.position().add(0.0, 1.7, 0.0));
        return this.level().getBlockState(bp).isSolidRender(this.level(), bp);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double max = 256.0;
        return distance < max * max;
    }
}
