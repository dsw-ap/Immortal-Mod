package com.tearhpi.immortal.entity.geo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tearhpi.immortal.entity.custom.skills.Skill7_Entity;
import com.tearhpi.immortal.entity.geo.model.Skill7Model;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Objects;

public class Skill7Renderer extends GeoEntityRenderer<Skill7_Entity> {
    private static final String WALL_BONE_NAME = "bone";
    public Skill7Renderer(EntityRendererProvider.Context context) {
        super(context,new Skill7Model());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new GlowLayer(this));
    }
    @Override
    public RenderType getRenderType(Skill7_Entity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
    @Override
    public void render(Skill7_Entity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = entity;
        // 这里你原来传的是 null，我保持不变，真正的 renderType 在 actuallyRender 里会被覆盖
        this.defaultRender(poseStack, entity, bufferSource, null, null, entityYaw, partialTick, packedLight);
    }
    /*
    @Override
    protected void applyRotations(Skill7_Entity anim, PoseStack poseStack, float ageInTicks, float unusedRotationYaw, float partialTick) {
        // 你真正想要的朝向
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        float yaw = anim.getYRot();              // 当前帧朝向
        float prevYaw = anim.yRotO;              // 上一帧朝向
        float lerped = Mth.lerp(partialTick, prevYaw, yaw);
        // GeckoLib/MC 的模型默认是面向南 or 东，看你模型导出方向，必要时 +180
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-lerped));
    }
     */
    @Override
    public void actuallyRender(PoseStack poseStack,
                               Skill7_Entity animatable,
                               BakedGeoModel model,
                               RenderType renderType,
                               MultiBufferSource bufferSource,
                               VertexConsumer buffer,
                               boolean isReRender,
                               float partialTick,
                               int packedLight,
                               int packedOverlay,
                               float red, float green, float blue, float alpha) {

        poseStack.pushPose();

        LivingEntity livingEntity = null;
        boolean shouldSit = animatable.isPassenger()
                && animatable.getVehicle() != null
                && animatable.getVehicle().shouldRiderSit();

        float lerpBodyRot = livingEntity == null ? 0.0F : Mth.rotLerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
        float lerpHeadRot = livingEntity == null ? 0.0F : Mth.rotLerp(partialTick, livingEntity.yHeadRotO, livingEntity.yHeadRot);
        float netHeadYaw = lerpHeadRot - lerpBodyRot;

        if (shouldSit) {
            Entity vehicle = animatable.getVehicle();
            if (vehicle instanceof LivingEntity livingvehicle) {
                lerpBodyRot = Mth.rotLerp(partialTick, livingvehicle.yBodyRotO, livingvehicle.yBodyRot);
                netHeadYaw = lerpHeadRot - lerpBodyRot;
                float clampedHeadYaw = Mth.clamp(Mth.wrapDegrees(netHeadYaw), -85.0F, 85.0F);
                lerpBodyRot = lerpHeadRot - clampedHeadYaw;
                if (clampedHeadYaw * clampedHeadYaw > 2500.0F) {
                    lerpBodyRot += clampedHeadYaw * 0.2F;
                }
                netHeadYaw = lerpHeadRot - lerpBodyRot;
            }
        }

        if (animatable.getPose() == Pose.SLEEPING && livingEntity != null) {
            Direction bedDirection = livingEntity.getBedOrientation();
            if (bedDirection != null) {
                float eyePosOffset = livingEntity.getEyeHeight(Pose.STANDING) - 0.1F;
                poseStack.translate(-bedDirection.getStepX() * eyePosOffset, 0.0F, -bedDirection.getStepZ() * eyePosOffset);
            }
        }

        float ageInTicks = animatable.tickCount + partialTick;
        float limbSwingAmount = 0.0F;
        float limbSwing = 0.0F;

        this.applyRotations(animatable, poseStack, ageInTicks, lerpBodyRot, partialTick);

        if (!shouldSit && animatable.isAlive() && livingEntity != null) {
            limbSwingAmount = livingEntity.walkAnimation.speed(partialTick);
            limbSwing = livingEntity.walkAnimation.position(partialTick);
            if (livingEntity.isBaby()) {
                limbSwing *= 3.0F;
            }
            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }

        if (!isReRender) {
            float headPitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
            float motionThreshold = this.getMotionAnimThreshold(animatable);
            Vec3 velocity = animatable.getDeltaMovement();
            float avgVelocity = (float) ((Math.abs(velocity.x) + Math.abs(velocity.z)) / 2.0F);

            AnimationState<Skill7_Entity> animationState =
                    new AnimationState<>(animatable, limbSwing, limbSwingAmount, partialTick,
                            avgVelocity >= motionThreshold && limbSwingAmount != 0.0F);

            long instanceId = this.getInstanceId(animatable);
            GeoModel<Skill7_Entity> currentModel = this.getGeoModel();
            animationState.setData(DataTickets.TICK, ((GeoAnimatable) animatable).getTick(animatable));
            animationState.setData(DataTickets.ENTITY, animatable);
            animationState.setData(DataTickets.ENTITY_MODEL_DATA,
                    new EntityModelData(shouldSit, livingEntity != null && livingEntity.isBaby(), -netHeadYaw, -headPitch));
            Objects.requireNonNull(animationState);
            currentModel.addAdditionalStateData(animatable, instanceId, animationState::setData);
            currentModel.handleAnimations(animatable, instanceId, animationState);
        }

        poseStack.translate(0.0F, 0.01F, 0.0F);
        this.modelRenderTranslations = new Matrix4f(poseStack.last().pose());

        // ② 这里我们把 renderType 固定成透明的
        if (renderType == null) {
            renderType = RenderType.entityTranslucent(this.getTextureLocation(animatable));
        }

        // 玩家让它高亮的情况
        if (animatable.isInvisibleTo(Minecraft.getInstance().player)) {
            if (Minecraft.getInstance().shouldEntityAppearGlowing(animatable)) {
                buffer = bufferSource.getBuffer(RenderType.outline(this.getTextureLocation(animatable)));
            } else {
                renderType = null;
            }
        }

        // 先正常画一遍整只墙（柱子 + 墙体）
        if (renderType != null) {
            super.actuallyRender(poseStack, animatable, model,
                    renderType, bufferSource, buffer,
                    isReRender, partialTick,
                    packedLight, packedOverlay,
                    red, green, blue, alpha);
        }

        // ③ 然后我们再单独画一次“中间那块”，这一次用 UV 滚动
        //    这样就不会影响两根柱子
        float yaw = Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot());
        // 你主体里如果是 mulPose(Axis.YP.rotationDegrees(-yaw))，这里也要一样
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw+90));
        float speed = 0.2F;                                    // 流动速度
        float vOffset = ((animatable.tickCount + partialTick) * speed) % 1.0F;
        float dv = -vOffset;                                    // 往上流

        RenderType wallRT = RenderType.eyes(this.getTextureLocation(animatable));
        VertexConsumer wallVC = bufferSource.getBuffer(wallRT);
        VertexConsumer scrollingVC = new ScrollingVertexConsumer(wallVC, 0.0F, dv);

        //poseStack.pushPose();
        //poseStack.translate(0, 0, 0.001f);
        poseStack.scale(1.0f,1.0f,1.001f);
        // GeckoLib 4.x 的单骨渲染

        model.getBone(WALL_BONE_NAME).ifPresent(bone -> {
            this.renderRecursively(poseStack, animatable, bone, wallRT, bufferSource, scrollingVC,true , partialTick, 0xF000F0, packedOverlay, 1f, 1f, 1f, 1f);
        });

        poseStack.popPose();
    }
    private static class GlowLayer extends GeoRenderLayer<Skill7_Entity> {
        public GlowLayer(Skill7Renderer renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, Skill7_Entity animatable, BakedGeoModel bakedModel, RenderType baseRenderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            RenderType glowType = RenderType.eyes(getTextureResource(animatable));
            VertexConsumer vc = bufferSource.getBuffer(glowType);
            int fullBright = 0xF000F0;
            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, glowType, vc, partialTick, fullBright, packedOverlay, 1f, 1f, 1f, 1f);
        }
    }
    private static class ScrollingVertexConsumer implements VertexConsumer {

        private final VertexConsumer delegate;
        private final float du;
        private final float dv;

        public ScrollingVertexConsumer(VertexConsumer delegate, float du, float dv) {
            this.delegate = delegate;
            this.du = du;
            this.dv = dv;
        }

        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            return delegate.vertex(x, y, z);
        }

        @Override
        public VertexConsumer color(int r, int g, int b, int a) {
            return delegate.color(r, g, b, a);
        }

        @Override
        public VertexConsumer uv(float u, float v) {
            return delegate.uv(u + du, v + dv);
        }

        @Override
        public VertexConsumer overlayCoords(int u, int v) {
            return delegate.overlayCoords(u, v);
        }

        @Override
        public VertexConsumer uv2(int u, int v) {
            return delegate.uv2(u, v);
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            return delegate.normal(x, y, z);
        }

        @Override
        public void endVertex() {
            delegate.endVertex();
        }

        @Override
        public void defaultColor(int r, int g, int b, int a) {
            delegate.defaultColor(r, g, b, a);
        }

        @Override
        public void unsetDefaultColor() {
            delegate.unsetDefaultColor();
        }
    }
}
