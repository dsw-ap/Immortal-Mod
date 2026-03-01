package com.tearhpi.immortal.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.effect.FiringEffect;
import com.tearhpi.immortal.effect._ModEffects;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom._ImmortalMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ISystemReportExtender;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.sql.Array;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class ExtraMobNameTag {
    @SubscribeEvent
    public static void onRenderNameTag(net.minecraftforge.client.event.RenderNameTagEvent event) {
        ResourceLocation ICON_FIRING_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/1.png");
        ResourceLocation ICON_MOVEMENT_SLOWDOWN = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/2.png");
        ResourceLocation ICON_IMPRISIONED_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/3.png");
        ResourceLocation ICON_ANNIHILATE_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/4.png");
        ResourceLocation ICON_EROSION_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/5.png");
        ResourceLocation ICON_INJURY_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/6.png");
        ResourceLocation ICON_MUTE_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/7.png");
        ResourceLocation ICON_LEVITATION = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/8.png");
        ResourceLocation ICON_NIGHTMARE_EFFECT = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/debuff/9.png");

        ResourceLocation ICON_SKILL_BAR_BACKGROUND = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/skill_bar_background.png");
        ResourceLocation ICON_SKILL_BAR = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/skill_bar.png");
        ResourceLocation ICON_HEALTH_BAR_BACKGROUND = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/health_bar_background.png");
        ResourceLocation ICON_HEALTH_BAR = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/health_bar.png");
        ResourceLocation ICON_WATER_BAR_BACKGROUND = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/water_amount_background.png");
        ResourceLocation ICON_WATER_BAR = ResourceLocation.fromNamespaceAndPath("immortal", "textures/gui/sprites/mob_hud/water_amount.png");
        //对生物实体生效
        Entity entity = event.getEntity();
        if (!(entity instanceof _ImmortalMob mob)) {
            return;
        }
        if (!mob.showHealth) {
            return;
        }
        // 只在客户端玩家视角下渲染
        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();

        // 距离判定
        double dSq = dispatcher.distanceToSqr(entity);
        if (dSq > 4096.0D) return;
        if (entity.isDiscrete() && dSq > 1024.0D) return;
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();
        Font font = mc.font;
        double yOffset = entity.getBbHeight() + 0.35D;

        //Buff条
        int debuffList = mob.getHasDebuff();
        if (debuffList != 0) {
            yOffset = yOffset + 0.15D;
            Stack<String> debuff = new Stack<>();
            if (debuffList >= 256) {
                debuff.push("NIGHTMARE_EFFECT");
                debuffList -= 256;
            }
            if (debuffList >= 128) {
                debuff.push("LEVITATION");
                debuffList -= 128;
            }
            if (debuffList >= 64) {
                debuff.push("MUTE_EFFECT");
                debuffList -= 64;
            }
            if (debuffList >= 32) {
                debuff.push("INJURY_EFFECT");
                debuffList -= 32;
            }
            if (debuffList >= 16) {
                debuff.push("EROSION_EFFECT");
                debuffList -= 16;
            }
            if (debuffList >= 8) {
                debuff.push("ANNIHILATE_EFFECT");
                debuffList -= 8;
            }
            if (debuffList >= 4) {
                debuff.push("IMPRISIONED_EFFECT");
                debuffList -= 4;
            }
            if (debuffList >= 2) {
                debuff.push("MOVEMENT_SLOWDOWN");
                debuffList -= 2;
            }
            if (debuffList >= 1) {
                debuff.push("FIRING_EFFECT");
                debuffList -= 1;
            }
            float iconSize = 10.0F;
            //float iconX = -(iconSize * debuff.size()+(iconSize / 5.0F * (debuff.size())-1)) / 2.0F;
            float iconX = -(iconSize * debuff.size()) / 2.0F;
            float iconY = 0.0F;
            float zOffset = -0.1F;
            poseStack.pushPose();
            poseStack.translate(0.0D, yOffset, 0.0D);
            poseStack.mulPose(dispatcher.cameraOrientation());
            poseStack.scale(-0.032F, -0.032F, 0.032F);
            while (!debuff.isEmpty()) {
                String debuffName = debuff.pop();
                ResourceLocation res = null;
                if (debuffName.equals("FIRING_EFFECT")) res = ICON_FIRING_EFFECT;
                if (debuffName.equals("MOVEMENT_SLOWDOWN")) res = ICON_MOVEMENT_SLOWDOWN;
                if (debuffName.equals("IMPRISIONED_EFFECT")) res = ICON_IMPRISIONED_EFFECT;
                if (debuffName.equals("ANNIHILATE_EFFECT")) res = ICON_ANNIHILATE_EFFECT;
                if (debuffName.equals("EROSION_EFFECT")) res = ICON_EROSION_EFFECT;
                if (debuffName.equals("INJURY_EFFECT")) res = ICON_INJURY_EFFECT;
                if (debuffName.equals("MUTE_EFFECT")) res = ICON_MUTE_EFFECT;
                if (debuffName.equals("LEVITATION")) res = ICON_LEVITATION;
                if (debuffName.equals("NIGHTMARE_EFFECT")) res = ICON_NIGHTMARE_EFFECT;
                if (res != null) {
                    Matrix4f imgMatrix = poseStack.last().pose();
                    VertexConsumer builder = buffer.getBuffer(RenderType.text(res));
                    addTextVertex(builder, imgMatrix, iconX, iconY, zOffset, 0.0F, 0.0F, packedLight);
                    addTextVertex(builder, imgMatrix, iconX, iconY + iconSize, zOffset, 0.0F, 1.0F, packedLight);
                    addTextVertex(builder, imgMatrix, iconX + iconSize, iconY + iconSize, zOffset, 1.0F, 1.0F, packedLight);
                    addTextVertex(builder, imgMatrix, iconX + iconSize, iconY, zOffset, 1.0F, 0.0F, packedLight);
                    //iconX += iconSize*1.2f;
                    iconX += iconSize;
                };
            }
            poseStack.popPose();
            yOffset += 0.15D;
        }
        //技能条
        if (true) {
            float length = 65.0F;
            float height = 1.5F;
            float ratio = (float) mob.getSkillBarAmount() / mob.getSkillBarMax();
            float iconX = -length / 2.0F;
            float iconY = 0.0F;
            float zOffset = -0.1F;
            float zOffset_ = -0.15F;
            poseStack.pushPose();
            poseStack.translate(0.0D, yOffset, 0.0D);
            poseStack.mulPose(dispatcher.cameraOrientation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f imgMatrix = poseStack.last().pose();
            //background
            VertexConsumer builder = buffer.getBuffer(RenderType.text(ICON_SKILL_BAR_BACKGROUND));
            addTextVertex(builder, imgMatrix, iconX, iconY, zOffset, 0.0F, 0.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX, iconY + height, zOffset, 0.0F, 1.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX + length, iconY + height, zOffset, 1.0F, 1.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX + length, iconY, zOffset, 1.0F, 0.0F, packedLight);
            //bar
            VertexConsumer builder_ = buffer.getBuffer(RenderType.text(ICON_SKILL_BAR));
            addTextVertex(builder_, imgMatrix, iconX, iconY, zOffset_, 0.0F, 0.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX, iconY + height, zOffset_, 0.0F, 1.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX + length*ratio, iconY + height, zOffset_, ratio, 1.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX + length*ratio, iconY, zOffset_, ratio, 0.0F, packedLight);
            poseStack.popPose();
            yOffset += 0.15D;
        }
        //生命值
        if (true) {
            float length = 75.0F;
            float height = 4.5F;
            float ratio = mob.getHealth() / mob.getMaxHealth();
            float iconX = -length / 2.0F;
            float iconY = 0.0F;
            float zOffset = -0.1F;
            float zOffset_ = -0.15F;
            poseStack.pushPose();
            poseStack.translate(0.0D, yOffset, 0.0D);
            poseStack.mulPose(dispatcher.cameraOrientation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f imgMatrix = poseStack.last().pose();
            //background
            VertexConsumer builder = buffer.getBuffer(RenderType.text(ICON_HEALTH_BAR_BACKGROUND));
            addTextVertex(builder, imgMatrix, iconX, iconY, zOffset, 0.0F, 0.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX, iconY + height, zOffset, 0.0F, 1.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX + length, iconY + height, zOffset, 1.0F, 1.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX + length, iconY, zOffset, 1.0F, 0.0F, packedLight);
            //bar
            VertexConsumer builder_ = buffer.getBuffer(RenderType.text(ICON_HEALTH_BAR));
            addTextVertex(builder_, imgMatrix, iconX, iconY, zOffset_, 0.0F, 0.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX, iconY + height, zOffset_, 0.0F, 1.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX + length*ratio, iconY + height, zOffset_, ratio, 1.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX + length*ratio, iconY, zOffset_, ratio, 0.0F, packedLight);
            poseStack.popPose();
            yOffset += 0.15D;
        }
        //生命值
        Player player = Minecraft.getInstance().player;
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        if (immortalPlayer.getSetting().Setting1 == 1) {
            yOffset += 0.1D;
            Component health = Component.literal((int) mob.getHealth()+"/"+(int) mob.getMaxHealth()).withStyle(net.minecraft.ChatFormatting.RED, net.minecraft.ChatFormatting.BOLD);
            poseStack.pushPose();
            poseStack.translate(0.0D, yOffset, 0.0D);
            poseStack.mulPose(dispatcher.cameraOrientation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);
            float x = -font.width(health) / 2.0F;
            float y = 0;
            Matrix4f matrix = poseStack.last().pose();
            font.drawInBatch(health, x, y, 0xFFFFFF, false, matrix, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
            poseStack.popPose();
        }
        //水气值
        if (mob.getWaterAmount() > 0) {
            float size = 15.0F;
            float ratio = (float) mob.getWaterAmount() / 100;
            float iconX = 85 / 2.0F;
            float iconY = 0.0F;
            float zOffset = -0.1F;
            float zOffset_ = -0.15F;
            poseStack.pushPose();
            poseStack.translate(0.0D, yOffset-0.2, 0.0D);
            poseStack.mulPose(dispatcher.cameraOrientation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f imgMatrix = poseStack.last().pose();
            //background
            VertexConsumer builder = buffer.getBuffer(RenderType.text(ICON_WATER_BAR_BACKGROUND));
            addTextVertex(builder, imgMatrix, iconX, iconY, zOffset, 0.0F, 0.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX, iconY + size, zOffset, 0.0F, 1.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX + size, iconY + size, zOffset, 1.0F, 1.0F, packedLight);
            addTextVertex(builder, imgMatrix, iconX + size, iconY, zOffset, 1.0F, 0.0F, packedLight);
            //bar
            VertexConsumer builder_ = buffer.getBuffer(RenderType.text(ICON_WATER_BAR));
            addTextVertex(builder_, imgMatrix, iconX, iconY + size*(1-ratio), zOffset_, 0.0F, 1-ratio, packedLight);
            addTextVertex(builder_, imgMatrix, iconX, iconY + size, zOffset_, 0.0F, 1, packedLight);
            addTextVertex(builder_, imgMatrix, iconX + size, iconY + size, zOffset_, 1.0F, 1.0F, packedLight);
            addTextVertex(builder_, imgMatrix, iconX + size, iconY + size*(1-ratio), zOffset_, 1, 1-ratio, packedLight);
            poseStack.popPose();
        }
    }

    private static void addTextVertex(VertexConsumer builder, Matrix4f matrix, float x, float y, float z, float u, float v, int packedLight) {
        builder.vertex(matrix, x, y, z)
                .color(255, 255, 255, 255) // 这里的 Alpha 设为 255
                .uv(u, v)
                .uv2(packedLight)
                .endVertex();
    }
}
