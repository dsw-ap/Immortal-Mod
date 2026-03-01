package com.tearhpi.immortal.mixin;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TrackingEmitter;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
    @Shadow
    @Final
    private Map<ParticleRenderType, Queue<Particle>> particles;
    @Shadow
    protected ClientLevel level;
    @Shadow
    private void tickParticleList(Collection<Particle> p_107385_){}
    @Shadow
    @Final
    private Queue<TrackingEmitter> trackingEmitters;
    @Shadow
    @Final
    private Queue<Particle> particlesToAdd;
    /**
     * @author TearHPi
     * @reason 重写粒子上限
     */
    @Overwrite
    public void tick() {
        ParticleEngine particleEngine = (ParticleEngine)(Object)this;
        this.particles.forEach((p_288249_, p_288250_) -> {
            this.level.getProfiler().push(p_288249_.toString());
            this.tickParticleList(p_288250_);
            this.level.getProfiler().pop();
        });
        if (!this.trackingEmitters.isEmpty()) {
            List<TrackingEmitter> list = Lists.newArrayList();

            for(TrackingEmitter trackingemitter : this.trackingEmitters) {
                trackingemitter.tick();
                if (!trackingemitter.isAlive()) {
                    list.add(trackingemitter);
                }
            }

            this.trackingEmitters.removeAll(list);
        }

        Particle particle;
        if (!this.particlesToAdd.isEmpty()) {
            while((particle = this.particlesToAdd.poll()) != null) {
                this.particles.computeIfAbsent(particle.getRenderType(), (p_107347_) -> {
                    return EvictingQueue.create(1048576);
                }).add(particle);
            }
        }

    }
}
