package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.util._TiltState;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    /*
    //特效实现:失衡
    private static final float TILT_DEG = 6.0f;
    private static final float TILT_RAD = (float) Math.toRadians(-TILT_DEG);

    @Inject(method = "getProjectionMatrix", at = @At("RETURN"), cancellable = true)
    private void Immortal$tiltProjection(double fov, CallbackInfoReturnable<Matrix4f> cir) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player.hasEffect(_ModEffects.UNBALANCE_EFFECT_1.get())) {
            int val = player.getEffect(_ModEffects.UNBALANCE_EFFECT_1.get()).getAmplifier()+1;
            float deg = TILT_DEG*val;
            deg = (float) Math.toRadians(deg);
            Matrix4f original = cir.getReturnValue();
            Matrix4f m = new Matrix4f(original);
            float c = Math.abs((float) Math.cos(deg));
            float s = Math.abs((float) Math.sin(deg));
            float scale = c + s;
            m.rotateZ(deg);
            m.scale(scale, scale, 1.0f);
            cir.setReturnValue(m);
        }
        if (player.hasEffect(_ModEffects.UNBALANCE_EFFECT_2.get())) {
            int val = player.getEffect(_ModEffects.UNBALANCE_EFFECT_2.get()).getAmplifier()+1;
            float deg = -TILT_DEG*val;
            deg = (float) Math.toRadians(deg);
            Matrix4f original = cir.getReturnValue();
            Matrix4f m = new Matrix4f(original);
            float c = Math.abs((float) Math.cos(deg));
            float s = Math.abs((float) Math.sin(deg));
            float scale = c + s;
            m.rotateZ(deg);
            m.scale(scale, scale, 1.0f);
            cir.setReturnValue(m);
        }
    }
     */
    @Unique
    private static final float TILT_DEG = 6.0f;
    @Unique
    private static final Map<UUID, _TiltState> immortal$tiltStates = new ConcurrentHashMap<>();
    @Unique
    private static float immortal$expSmoothing(float cur, float target, float dtSec, float tauSec) {
        float alpha = 1.0f - (float)Math.exp(-dtSec / Math.max(1e-6f, tauSec));
        return cur + (target - cur) * alpha;
    }

    @Inject(method = "getProjectionMatrix", at = @At("RETURN"), cancellable = true)
    private void Immortal$tiltProjection(double fov, CallbackInfoReturnable<Matrix4f> cir) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        float targetDegRad = 0.0f;
        var e1 = player.getEffect(_ModEffects.UNBALANCE_EFFECT_1.get());
        var e2 = player.getEffect(_ModEffects.UNBALANCE_EFFECT_2.get());
        if (e1 != null) {
            targetDegRad = (float) Math.toRadians(TILT_DEG * (e1.getAmplifier() + 1));
        } else if (e2 != null) {
            targetDegRad = (float) Math.toRadians(-TILT_DEG * (e2.getAmplifier() + 1));
        }

        UUID id = player.getUUID();
        _TiltState st = immortal$tiltStates.computeIfAbsent(id, k -> new _TiltState());
        long now = Util.getNanos();
        if (st.lastNanos == 0L) st.lastNanos = now;
        float dtSec = (now - st.lastNanos) / 1_000_000_000.0f;
        st.lastNanos = now;
        dtSec = Math.min(dtSec, 0.05f);
        final float tauSec = 0.10f;
        st.curDegRad = immortal$expSmoothing(st.curDegRad, targetDegRad, dtSec, tauSec);
        if (targetDegRad == 0.0f && Math.abs(st.curDegRad) < 0.0001f) {
            immortal$tiltStates.remove(id);
            return;
        }
        float deg = st.curDegRad;
        if (deg == 0.0f) return;

        Matrix4f original = cir.getReturnValue();
        Matrix4f m = new Matrix4f(original);
        float c = Math.abs((float) Math.cos(deg));
        float s = Math.abs((float) Math.sin(deg));
        float scale = c + s;
        m.rotateZ(deg);
        m.scale(scale, scale, 1.0f);
        cir.setReturnValue(m);
    }
}