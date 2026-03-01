package com.tearhpi.immortal.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.EnumSet;
import java.util.Set;

public abstract class _TaggedMobEffect extends MobEffect {
    private final EnumSet<_EffectTag> tags;
    private final int weight;

    protected _TaggedMobEffect(MobEffectCategory category, int color, Set<_EffectTag> tags, int weight) {
        super(category, color);
        this.tags = EnumSet.copyOf(tags);
        this.weight = weight;
    }

    public boolean hasTag(_EffectTag tag)        { return tags.contains(tag); }
    public boolean hasAll(Set<_EffectTag> need)  { return tags.containsAll(need); }
    public boolean hasAny(Set<_EffectTag> any)   { return any.stream().anyMatch(tags::contains); }
    public Set<_EffectTag> tags()                { return EnumSet.copyOf(tags); }
    public int weight()                         { return weight; }
}
