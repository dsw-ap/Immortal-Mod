package com.tearhpi.immortal.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(net.minecraft.world.effect.MobEffect.class)
public abstract class MobEffectMixin {

    @Inject(method = "getAttributeModifierValue", at = @At("HEAD"), cancellable = true)
    private void immortal$slownessScaleTo10(int amplifier,
                                            net.minecraft.world.entity.ai.attributes.AttributeModifier modifier,
                                            org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Double> cir) {
        // 当前正在计算的就是哪个效果
        net.minecraft.world.effect.MobEffect self = (net.minecraft.world.effect.MobEffect)(Object)this;

        // 只改原版“缓慢”效果
        if (self == net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN) {
            // vanilla 是 baseAmount * (amplifier+1)
            // 现在想把“每级 -20%”变成“每级 -10%”，直接 *0.5
            double base = modifier.getAmount(); // 这是注册时写在效果里的“基础系数”（负数）
            cir.setReturnValue(base * (amplifier + 1) * 0.5D);
        }
    }
}