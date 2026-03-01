package com.tearhpi.immortal.util.statics;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class StaticParticle {
    public static void spawnParticleLine(ServerLevel level, Vec3 start, Vec3 end, ParticleOptions particleType, int count) {
        if (count <= 0) return;

        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double dz = end.z - start.z;

        for (int i = 0; i < count; i++) {
            double t = count == 1 ? 0.0 : (double) i / (count - 1); // 均匀分布 [0,1]
            double px = start.x + dx * t;
            double py = start.y + dy * t;
            double pz = start.z + dz * t;

            // 无速度，无扩散，静止粒子
            level.sendParticles(particleType, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }
    public static void spawnParticleLineWithSpeed(ServerLevel level, Vec3 start, Vec3 end, ParticleOptions particleType, int count,Vec3 dir,double speed) {
        if (count <= 0) return;

        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double dz = end.z - start.z;

        for (int i = 0; i < count; i++) {
            double t = count == 1 ? 0.0 : (double) i / (count - 1); // 均匀分布 [0,1]
            double px = start.x + dx * t;
            double py = start.y + dy * t;
            double pz = start.z + dz * t;

            // 无速度，无扩散，静止粒子
            level.sendParticles(particleType, px, py, pz, 0, dir.x, dir.y, dir.z, speed);
        }
    }
    public static void spawnEdgeCage(ServerLevel level, AABB box, ParticleOptions particle, double spacing, int particlesPerPoint) {
        // 轻微扩大
        AABB b = box.inflate(0.05);

        // 8 corners
        Vec3 c000 = new Vec3(b.minX, b.minY, b.minZ);
        Vec3 c001 = new Vec3(b.minX, b.minY, b.maxZ);
        Vec3 c010 = new Vec3(b.minX, b.maxY, b.minZ);
        Vec3 c011 = new Vec3(b.minX, b.maxY, b.maxZ);

        Vec3 c100 = new Vec3(b.maxX, b.minY, b.minZ);
        Vec3 c101 = new Vec3(b.maxX, b.minY, b.maxZ);
        Vec3 c110 = new Vec3(b.maxX, b.maxY, b.minZ);
        Vec3 c111 = new Vec3(b.maxX, b.maxY, b.maxZ);

        Vec3[][] edges = new Vec3[][]{
                {c000, c001}, {c001, c101}, {c101, c100}, {c100, c000},
                {c010, c011}, {c011, c111}, {c111, c110}, {c110, c010},
                {c000, c010}, {c001, c011}, {c101, c111}, {c100, c110}
        };

        for (Vec3[] e : edges) {
            spawnAlongEdge(level, e[0], e[1], particle, spacing, particlesPerPoint);
        }
    }

    /** 沿线段 AB 按 spacing 均匀撒粒子 */
    private static void spawnAlongEdge(ServerLevel level, Vec3 a, Vec3 b,
                                       ParticleOptions particle, double spacing, int particlesPerPoint) {
        Vec3 ab = b.subtract(a);
        double len = ab.length();
        if (len <= 1.0e-6) return;

        int steps = Math.max(1, (int) Math.ceil(len / spacing));

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / (double) steps; // 0..1
            double x = a.x + ab.x * t;
            double y = a.y + ab.y * t;
            double z = a.z + ab.z * t;

            level.sendParticles(particle, x, y, z,
                    particlesPerPoint, 0.0, 0.0, 0.0, 0.0);
        }
    }
    //蝴蝶线
    public static Vec2 butterfly(float t) {
        float r = (float)Math.exp(Math.cos(t)) - 2f * (float)Math.cos(4f * t) - (float)Math.pow(Math.sin(t / 12f), 5);
        float x = (float)Math.sin(t) * r;
        float y = (float)Math.cos(t) * r;
        return new Vec2(x, y);
    }
}
