package com.tearhpi.immortal.effect;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Predicate;

public final class _EffectQueries {
    private _EffectQueries(){}

    /** 判断某 MobEffect 是否拥有指定标签 (只对 _TaggedMobEffect 有意义) */
    public static boolean hasTag(net.minecraft.world.effect.MobEffect eff, _EffectTag tag) {
        return eff instanceof _TaggedMobEffect t && t.hasTag(tag);
    }

    /** 获取拥有某标签的全部已注册效果 */
    public static List<_TaggedMobEffect> byTag(_EffectTag tag) {
        List<_TaggedMobEffect> out = new ArrayList<>();
        for (var eff : ForgeRegistries.MOB_EFFECTS.getValues()) {
            if (eff instanceof _TaggedMobEffect t && t.hasTag(tag)) out.add(t);
        }
        return out;
    }

    /** 按某标签 + 额外过滤进行加权随机 */
    public static Optional<_TaggedMobEffect> randomByTag(_EffectTag tag, Random rnd,
                                                         Predicate<_TaggedMobEffect> extraFilter) {
        List<_TaggedMobEffect> pool = byTag(tag).stream().filter(extraFilter).toList();
        if (pool.isEmpty()) return Optional.empty();
        int total = pool.stream().mapToInt(_TaggedMobEffect::weight).sum();
        int r = rnd.nextInt(total);
        for (_TaggedMobEffect t : pool) {
            r -= t.weight();
            if (r < 0) return Optional.of(t);
        }
        return Optional.empty();
    }

    /** 同时满足多个标签的加权随机 */
    public static Optional<_TaggedMobEffect> randomByAllTags(Set<_EffectTag> tags, RandomSource rnd) {
        List<_TaggedMobEffect> pool = new ArrayList<>();
        for (var eff : ForgeRegistries.MOB_EFFECTS.getValues()) {
            if (eff instanceof _TaggedMobEffect t && t.hasAll(tags)) pool.add(t);
        }
        if (pool.isEmpty()) return Optional.empty();
        int total = pool.stream().mapToInt(_TaggedMobEffect::weight).sum();
        int r = rnd.nextInt(total);
        for (_TaggedMobEffect t : pool) {
            r -= t.weight();
            if (r < 0) return Optional.of(t);
        }
        return Optional.empty();
    }

    public static _TaggedMobEffect randomByEnemyDebuffTag() {
        List<_TaggedMobEffect> pool = new ArrayList<>();
        pool.add((_TaggedMobEffect) _ModEffects.FIRING_EFFECT.get());
        pool.add((_TaggedMobEffect) _ModEffects.ANNIHILATE_EFFECT.get());
        pool.add((_TaggedMobEffect) _ModEffects.IMPRISIONED_EFFECT.get());
        pool.add((_TaggedMobEffect) _ModEffects.EROSION_EFFECT.get());
        pool.add((_TaggedMobEffect) _ModEffects.INJURY_EFFECT.get());
        pool.add((_TaggedMobEffect) _ModEffects.MUTE_EFFECT.get());
        pool.add((_TaggedMobEffect) _ModEffects.NIGHTMARE_EFFECT.get());

        if (pool.isEmpty()) return null;
        int total = pool.stream().mapToInt(_TaggedMobEffect::weight).sum();
        int r = (new Random()).nextInt(total);
        for (_TaggedMobEffect t : pool) {
            r -= t.weight();
            if (r < 0) return t;
        }
        return null;
    }
}
