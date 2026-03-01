package com.tearhpi.immortal.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public abstract class ThrownEnderpearlTickMixin {
    /*
    // 你可以调大一些，比如 5/10，避免刷屏
    private static final int LOG_EVERY_N_TICKS = 1;

    @Inject(method = "tick", at = @At("HEAD"))
    private void immortal$afterTick(CallbackInfo ci) {
        ThrownEnderpearl self = (ThrownEnderpearl)(Object)this;

        // 建议只在服务端打印，避免客户端每个玩家都刷
        if (self.level().isClientSide) return;

        // 每 N tick 打印一次
        if (LOG_EVERY_N_TICKS > 1 && (self.tickCount % LOG_EVERY_N_TICKS != 0)) return;

        Vec3 pos = self.position();
        Vec3 delta = self.getDeltaMovement();
        AABB bb = self.getBoundingBox();

        double bbCenterX = (bb.minX + bb.maxX) * 0.5;
        double bbCenterY = (bb.minY + bb.maxY) * 0.5;
        double bbCenterZ = (bb.minZ + bb.maxZ) * 0.5;

        double dx = bbCenterX - pos.x;
        double dy = bbCenterY - pos.y;
        double dz = bbCenterZ - pos.z;

        double minYOffset = bb.minY - pos.y;
        double maxYOffset = bb.maxY - pos.y;

        // 这几个量通常能直接看出 “pos 是 AABB 的哪个参考点”
        System.out.println(
                "[PearlTest] tick=" + self.tickCount +
                        " id=" + self.getId() +
                        " pos=" + fmt(pos) +
                        " vel=" + fmt(delta) +
                        " bb=[" + fmt(bb.minX, bb.minY, bb.minZ) + " -> " + fmt(bb.maxX, bb.maxY, bb.maxZ) + "]" +
                        " bbCenter=" + fmt(bbCenterX, bbCenterY, bbCenterZ) +
                        " (bbCenter-pos)=" + fmt(dx, dy, dz) +
                        " (bb.minY-posY)=" + r3(minYOffset) +
                        " (bb.maxY-posY)=" + r3(maxYOffset) +
                        " size=" + r3(bb.getXsize()) + "," + r3(bb.getYsize()) + "," + r3(bb.getZsize())
        );
        System.out.println(
                "[PearlTest] tick=" + self.tickCount +
                        " pos=" + fmt(pos)
        );
    }

    private static String fmt(Vec3 v) {
        return fmt(v.x, v.y, v.z);
    }

    private static String fmt(double x, double y, double z) {
        return "(" + r3(x) + "," + r3(y) + "," + r3(z) + ")";
    }

    private static String r3(double d) {
        return String.format(java.util.Locale.ROOT, "%.3f", d);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void immortal$onPearlHit(HitResult hitResult, CallbackInfo ci) {

        // 只关心撞到方块
        if (hitResult.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hitResult;

        BlockPos blockPos = blockHit.getBlockPos();     // 方块格坐标
        Vec3 hitLocation = blockHit.getLocation();      // 精确命中点 (double)
        Direction face = blockHit.getDirection();       // 命中面

        ThrownEnderpearl pearl = (ThrownEnderpearl)(Object)this;

        Vec3 pearlPos = pearl.position();               // 珍珠当前pos
        Vec3 pearlVelocity = pearl.getDeltaMovement();  // 当前速度
        Vec3 bbCenter = pearl.getBoundingBox().getCenter();

        System.out.println("========== Pearl Block Collision ==========");
        System.out.println("BlockPos: " + blockPos);
        System.out.println("HitLocation: " + format(hitLocation));
        System.out.println("HitFace: " + face);
        System.out.println("PearlPos: " + format(pearlPos));
        System.out.println("PearlVelocity: " + format(pearlVelocity));
        System.out.println("BB Center: " + format(bbCenter));
        System.out.println("Pos - Hit: " + format(pearlPos.subtract(hitLocation)));
        System.out.println("==========================================");
    }

    private static String format(Vec3 v) {
        return String.format("(%.4f, %.4f, %.4f)", v.x, v.y, v.z);
    }
    */
}
