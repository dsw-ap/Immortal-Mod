package com.tearhpi.immortal.util.statics;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Entity_Utils {
    public static void moveToward(Entity mover, Entity target, double speed) {
        if (mover == null || target == null) return;

        Vec3 from = mover.position();
        Vec3 to = target.position();

        // 计算方向向量
        Vec3 dir = to.subtract(from);

        // 防止除以零
        if (dir.lengthSqr() < 1e-8) return;

        // 归一化方向并乘速度
        Vec3 motion = dir.normalize().scale(speed);

        // 设置新的位置或速度
        mover.setDeltaMovement(motion);

        // 如果是非物理实体，推荐直接移动位置
        mover.move(MoverType.SELF, motion);

        mover.setYRot((float)(Mth.atan2(dir.z, dir.x) * (180F / Math.PI)) - 90F);
        mover.setXRot((float)(-(Mth.atan2(dir.y, dir.horizontalDistance()) * (180F / Math.PI))));
    }
    public static void moveToward(Entity mover, Vec3 target, double speed) {
        if (mover == null || target == null) return;

        Vec3 from = mover.position();
        Vec3 to = target;

        // 计算方向向量
        Vec3 dir = to.subtract(from);

        // 防止除以零
        if (dir.lengthSqr() < 1e-8) return;

        // 归一化方向并乘速度
        Vec3 motion = dir.normalize().scale(speed);

        // 设置新的位置或速度
        mover.setDeltaMovement(motion);

        // 如果是非物理实体，推荐直接移动位置
        mover.move(MoverType.SELF, motion);

        mover.setYRot((float)(Mth.atan2(dir.z, dir.x) * (180F / Math.PI)) - 90F);
        mover.setXRot((float)(-(Mth.atan2(dir.y, dir.horizontalDistance()) * (180F / Math.PI))));
    }

    public static <T extends Entity> boolean isEntityTypeWithinDistance(
            Entity origin, Class<T> targetType, double distance) {
        if (origin == null || origin.level() == null) return false;
        Level level = origin.level();

        List<T> list = level.getEntitiesOfClass(
                targetType,
                origin.getBoundingBox().inflate(distance),
                e -> e != null && e.isAlive()
        );
        if (list.isEmpty()) {
            return false;
        }
        double distanceSq = distance * distance;
        for (T entity : list) {
            if (entity.distanceToSqr(origin) <= distanceSq) {
                return true; // 找到一个在球内的
            }
        }
        return false;
    }
}

