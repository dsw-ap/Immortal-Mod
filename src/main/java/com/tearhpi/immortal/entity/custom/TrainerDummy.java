package com.tearhpi.immortal.entity.custom;

import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

public class TrainerDummy extends _ImmortalNormalMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isHurt = false;
    public TrainerDummy(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);

    }
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(ModAttributes.IMMORTAL_MAX_HEALTH.get(), 1000000.0)
                .add(ModAttributes.IMMORTAL_ATTACK_DAMAGE.get(), 10.0)
                .add(ModAttributes.IMMORTAL_DEFENSE.get(), 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ARMOR, 0.0);
    }

    @Override
    protected void registerGoals() {
        // 不注册任何 AI
    }

    @Override
    public boolean isPushable() {
        return false; // 不可被推
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return false; // 可被攻击
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false; // 不会因跌落受伤
    }

    @Override
    public boolean isNoAi() {
        return true; // 禁用 AI
    }

    @Override
    public void knockback(double strength, double xRatio, double zRatio) {
        // 什么都不做，禁止击退
    }

    @Override
    protected void pushEntities() {
        // 不调用 super 方法，避免实体被推挤
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::precidate));
    }

    private PlayState precidate(AnimationState<TrainerDummy> state) {
        //System.out.println(isHurt);
        if (isHurt) {
            isHurt = false;
            state.getController().forceAnimationReset();
            state.getController().setAnimation(RawAnimation.begin().then("hurt", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        // idle 或其他逻辑
        //state.getController().setAnimation(RawAnimation.begin().then("ambient", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        this.isHurt = true;
        return super.hurt(source, amount);
    }
}
