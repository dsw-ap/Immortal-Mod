package com.tearhpi.immortal.entity.DamageNumber;

import com.tearhpi.immortal.entity.custom._ImmortalMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TextDisplayWithBindEntity extends Display.TextDisplay{
    @Nullable
    private UUID ownerId;

    public TextDisplayWithBindEntity(EntityType<?> p_270708_, Level p_270736_, _ImmortalMob owner) {
        super(p_270708_, p_270736_);
        if (owner != null) {
            this.ownerId = owner.getUUID();
        }
    }

    @Nullable
    public _ImmortalMob getOwner() {
        if (this.ownerId == null) return null;
        if (!(this.level() instanceof ServerLevel sl)) return null;
        double r = 5.0d;
        List<_ImmortalMob> mobs = sl.getEntitiesOfClass(_ImmortalMob.class, this.getBoundingBox().inflate(r), mob -> mob.isAlive() && mob.distanceToSqr(this) <= r * r);
        for (_ImmortalMob mob : mobs) {
            if (mob.getUUID().equals(this.ownerId)) return mob;
        }
        return null;
    }
    public UUID getOwnerUUID() {
        return ownerId;
    }
    public Component getText_(){
        CompoundTag tag = new CompoundTag();
        this.saveWithoutId(tag);

        if (tag.contains("text")) {
            return Component.Serializer.fromJson(tag.getString("text"));
        }
        return null;
    }
}
