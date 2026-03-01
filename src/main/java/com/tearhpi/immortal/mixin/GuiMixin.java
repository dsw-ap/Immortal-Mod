package com.tearhpi.immortal.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.capability.SkillAssemble;
import com.tearhpi.immortal.item.custom.WandItem;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.skill.PassiveSkillAttribute;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final protected Minecraft minecraft;

    /**
     * Overwrite to disable rendering of the selected item name (item tooltip above hotbar).
     * We simply return without drawing.
     * @author TearHPi
     * @reason 1
     */
    @Overwrite(remap = false)
    public void renderSelectedItemName(GuiGraphics guiGraphics, int yShift) {
        // No item name tooltip in this custom HUD
        return;
    }
    /**
     * Inject to render a number below the crosshair.
     */
    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void immortal$renderNumberBelowCrosshair(GuiGraphics p_282828_, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null || mc.options.hideGui) return;
        int num = ((IImmortalPlayer) mc.player).getSkillInUse().IsInUseSkill;
        float seconds = num / 20.0F;
        String text = String.format("%.2f", seconds);
        Font font = mc.font;
        int sw = mc.getWindow().getGuiScaledWidth();
        int sh = mc.getWindow().getGuiScaledHeight();
        int textWidth = font.width(text);
        int x = (sw - textWidth) / 2;
        int y = (sh / 2) + 8;
        if (num != -1) {
            p_282828_.drawString(font, text, x, y, 0xFFFFFF, false);
        }
        //特殊 Skill18
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) mc.player;
        if (iImmortalPlayer.getSkillInUse().IsInUseSkillNumber == 18 && iImmortalPlayer.getSkillInUse().IsInUseSkill > 0) {
            String A = "A";
            String W = "W";
            String D = "D";
            int x_W = (sw - font.width(W)) / 2;
            int y_W = (sh / 2 - font.lineHeight) - 8;
            int x_A = (sw - 20 - font.lineHeight) / 2;
            int y_A = (sh - font.lineHeight) / 2;
            int x_D = (sw + 20) / 2;
            int y_D = (sh - font.lineHeight) / 2;
            p_282828_.drawString(font, A, x_A, y_A, 0xFFFFFF, false);
            p_282828_.drawString(font, W, x_W, y_W, 0xFFFFFF, false);
            p_282828_.drawString(font, D, x_D, y_D, 0xFFFFFF, false);
        }
        //能量条绘制
        if (mc.player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof WandItem item) {
            Options options = this.minecraft.options;
            if (options.getCameraType().isFirstPerson()) {
                if (this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
                    if (!options.hideGui){
                        if (iImmortalPlayer.getSkill().Skill_4_Level > 0 && num == -1) {
                            ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/sprites/hud/energy/empty.png");
                            ResourceLocation UP = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/sprites/hud/energy/full.png");
                            int val = iImmortalPlayer.getNormalAttackEnergy().get();
                            int length = 13,interval = 2,height = 3;
                            int x_energy = sw/2-length*3-interval*5/2;
                            int y_energy = (sh / 2)+6;
                            boolean flag = true;
                            if (iImmortalPlayer.getSetting().Setting4 == 1) {
                                if (this.minecraft.gameMode.getPlayerMode() == GameType.CREATIVE) flag = false;
                                BG = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/sprites/hud/energy/empty_.png");
                                UP = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/sprites/hud/energy/full_.png");
                                length = 11;
                                interval = -2;
                                height = 5;
                                x_energy = p_282828_.guiWidth() / 2 + 35;
                                y_energy = p_282828_.guiHeight() - 47;
                            }
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.75F);
                            if (flag) {
                                for (int i=0;i<6;i++) {
                                    p_282828_.blit(BG,x_energy+i*(length+interval),y_energy,length,height,0,0,13,5,13,5);
                                }
                                for (int i=0;i<val;i++) {
                                    p_282828_.blit(UP,x_energy+i*(length+interval),y_energy,length,height,0,0,13,5,13,5);
                                }
                            }
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.disableBlend();
                        }
                    }
                }
            }
        }
    }
    /**
     * @author TearHPi
     * @reason RenderHearts
     */
    @Overwrite
    protected void renderHearts(GuiGraphics guiGraphics, Player player, int x, int y, int rowHeight, int regenIndex, float maxHealth, int lastHealthVal, int healthVal, int absorb, boolean blinking) {
        Gui gui = (Gui)(Object)this;
        int leftPos = guiGraphics.guiWidth() / 2 - 91;
        int rightPos = guiGraphics.guiWidth() / 2 + 91;
        int baseY = guiGraphics.guiHeight() - 39;
        x = rightPos;
        y = baseY;
        //渲染expBar
        this.minecraft.getProfiler().push("expBar");
        int i = this.minecraft.player.getXpNeededForNextLevel();
        ResourceLocation background = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/expbar/experience_bar_background.png");
        ResourceLocation bar = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/expbar/experience_bar_progress.png");
        if (i > 0) {
            int k = (int)(this.minecraft.player.experienceProgress * 183.0F);
            int l = this.screenHeight - 32 + 3;
            guiGraphics.blit(background, leftPos, l, 182, 5, 0, 0,172,4,172,4);
            if (k > 0) {
                guiGraphics.blit(bar, leftPos, l, k, 5, 0, 0,(int)(this.minecraft.player.experienceProgress * 172.0F),4,172,4);
            }
        }

        /**渲染血量*/
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/hp_container.png"), x - 71 - 16 - 23,y - 11 - 11,900, 0.0F,0.0F,38,36,38,36);
        float currentHP = player.getHealth();
        double maxHP = player.getAttributeValue(ModAttributes.IMMORTAL_MAX_HEALTH.getHolder().get());
        int filledHeight = (int)(4 + 29 - Math.min(currentHP / maxHP, 1.0) * 29);
        int prevFilledHeight = (int)(4 + 29 - Math.min(lastHealthVal / maxHP, 1.0) * 29);

        ResourceLocation TEX_HEART = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/hp_full.png");
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        if (iImmortalPlayer.getSetting().Setting3 == 1) {
            TEX_HEART = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/hp_full_.png");
        }
        if (!blinking) {
            if (player.hasEffect(MobEffects.WITHER)) {
                RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                guiGraphics.blit(TEX_HEART, x - 71 - 16 - 23,y - 22 + filledHeight,0.0F,(float) filledHeight,38,36 - filledHeight,38,36);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            } else if (player.hasEffect(MobEffects.POISON)) {
                RenderSystem.setShaderColor(0.78F, 3.636F, 0.54F, 1.0F);
                guiGraphics.blit(TEX_HEART, x - 71 - 16 - 23,y - 22 + filledHeight,0.0F,(float) filledHeight,38,36 - filledHeight,38,36);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                guiGraphics.blit(TEX_HEART, x - 71 - 16 - 23,y - 22 + filledHeight,0.0F,(float) filledHeight,38,36 - filledHeight,38,36);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        if (blinking) {
            guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/hp_blinking.png"), x - 71 - 16 - 23,y - 22 + prevFilledHeight,0.0F,(float) prevFilledHeight,38,36 - prevFilledHeight,38,36);
        }
        if (absorb > 0) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/hp_absorbing.png"), x - 71 - 16 - 23,y - 22 + filledHeight,0.0F,(float) filledHeight,38,36 - filledHeight,38,36);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
           guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/absorbing_blur.png"), 0, 0, 0.0F, 0.0F, this.screenWidth, this.screenHeight, this.screenWidth, this.screenHeight);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            String absorbStr = String.valueOf(absorb);
            Gui self = (Gui)(Object)this;
            int textX = (guiGraphics.guiWidth() - self.getFont().width(absorbStr)) / 2;
            guiGraphics.drawString(self.getFont(), absorbStr, textX - 1, y-8, 6372098, false);
            guiGraphics.drawString(self.getFont(), absorbStr, textX + 1, y-8, 6372098, false);
            guiGraphics.drawString(self.getFont(), absorbStr, textX - 1, y-10, 6372098, false);
            guiGraphics.drawString(self.getFont(), absorbStr, textX + 1, y-10, 6372098, false);
            guiGraphics.drawString(self.getFont(), absorbStr, textX,     y-9, 16438858, false);


        } else {
            PoseStack poseStack = guiGraphics.pose();
            int hpVal = (int) currentHP;
            int maxHpVal = (int) maxHP;
            String hpText = hpVal + "/" + maxHpVal;
            Gui self = (Gui)(Object)this;
            int textX = (guiGraphics.guiWidth() - self.getFont().width(hpText)) / 2;
            poseStack.pushPose();
            poseStack.translate(textX + self.getFont().width(hpText) * 0.25, y - 8, 0);
            poseStack.scale(0.5f, 0.5f, 1.0f);
            guiGraphics.drawString(self.getFont(), hpText, 0, 1, 6579300, false);
            guiGraphics.drawString(self.getFont(), hpText, 1, 0, 6579300, false);
            guiGraphics.drawString(self.getFont(), hpText, 1, 2, 6579300, false);
            guiGraphics.drawString(self.getFont(), hpText, 2, 1, 6579300, false);
            guiGraphics.drawString(self.getFont(), hpText, 1, 1, 16777215, false);
            poseStack.popPose();
        }
        int i3 = player.getMaxAirSupply();
        int j3 = Math.min(player.getAirSupply(), i3);
        float air_ratio = (float) j3 / i3;
        if (player.isEyeInFluid(FluidTags.WATER) || j3 < i3) {
            int height_int = (int) (38- Math.min(air_ratio,1)*36);
            guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/heart/air.png"), x - 71 - 16 - 23, y - 22 + height_int, 0.0F, (float) height_int, 38, 36 - height_int, 38, 36);
        }

        //最后 经验等级文字显示
        this.minecraft.getProfiler().pop();
        if (this.minecraft.player.experienceLevel > 0) {
            String s = "" + this.minecraft.player.experienceLevel;
            int i1 = (this.screenWidth) / 2-91;
            int j1 = this.screenHeight - 28;
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(i1, j1, 0.0f);
            poseStack.scale(0.5f, 0.5f, 1.0f);
            guiGraphics.drawString(gui.getFont(), s, 1, 0, 2368052, false);
            guiGraphics.drawString(gui.getFont(), s, -1, 0, 2368052, false);
            guiGraphics.drawString(gui.getFont(), s, 0, 1, 2368052, false);
            guiGraphics.drawString(gui.getFont(), s, 0, -1, 2368052, false);
            guiGraphics.drawString(gui.getFont(), s, 0, 0, 8453920, false);
            poseStack.popPose();
        }
    }

    @Shadow @Final @Mutable
    protected static ResourceLocation WIDGETS_LOCATION;
    static {
        // 静态初始化块（Mixin 会在类加载时执行）
        WIDGETS_LOCATION = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/widgets.png");
    }
    //@Shadow @Final
    //protected Minecraft minecraft;
    @Shadow
    protected int screenHeight;
    @Shadow
    protected int screenWidth;

    @Shadow protected abstract void renderTextureOverlay(GuiGraphics p_282304_, ResourceLocation p_281622_, float p_281504_);

    /**
     * @author TearHPi
     * @reason OverWrite RenderExperienceBar(已废弃 整合进了RenderHearts 的 Overwrite 中)
     */
    @Overwrite
    public void renderExperienceBar(GuiGraphics p_281906_, int p_282731_) {
        /*
        Gui gui = (Gui)(Object)this;
        this.minecraft.getProfiler().push("expBar");
        int i = this.minecraft.player.getXpNeededForNextLevel();
        ResourceLocation background = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/expbar/experience_bar_background.png");
        ResourceLocation bar = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/expbar/experience_bar_progress.png");
        if (i > 0) {
            int j = 182;
            int k = (int)(this.minecraft.player.experienceProgress * 183.0F);
            int l = this.screenHeight - 32 + 3;
            p_281906_.blit(background, p_282731_, l, 182, 5, 0, 0,172,4,172,4);
            if (k > 0) {
                p_281906_.blit(bar, p_282731_, l, k, 5, 0, 0,(int)(this.minecraft.player.experienceProgress * 172.0F),4,172,4);
            }
        }
        this.minecraft.getProfiler().pop();
        if (this.minecraft.player.experienceLevel > 0) {
            this.minecraft.getProfiler().push("expLevel");
            String s = "" + this.minecraft.player.experienceLevel;
            int i1 = (this.screenWidth - gui.getFont().width(s)) / 2;
            int j1 = this.screenHeight - 31;
            p_281906_.drawString(gui.getFont(), s, i1 + 1, j1, 0, false);
            p_281906_.drawString(gui.getFont(), s, i1 - 1, j1, 0, false);
            p_281906_.drawString(gui.getFont(), s, i1, j1 + 1, 0, false);
            p_281906_.drawString(gui.getFont(), s, i1, j1 - 1, 0, false);
            p_281906_.drawString(gui.getFont(), s, i1, j1, 8453920, false);
            this.minecraft.getProfiler().pop();
        }
         */
    }

    /**
     * 第1次 blit 之后
     */
    @Inject(
            method = "renderHotbar(FLnet/minecraft/client/gui/GuiGraphics;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ), require = 1, expect = 1
    )
    private void immortal$afterSelectedSlotBlit_int6(float partialTick, GuiGraphics gg, CallbackInfo ci) {
        RenderSystem.enableBlend();
        int i = this.screenWidth / 2;
        gg.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/hotbar/hotbar_2.png"),
                i - 91, this.screenHeight - 22, 182, 22, 0, 0, 182, 22, 182, 22);
        RenderSystem.disableBlend();
    }
    @Inject(method = "renderHotbar", at = @At("TAIL"))
    private void immortal$tailHotbar(float pt, GuiGraphics gg, CallbackInfo ci) {
        gg.flush();
        RenderSystem.disableDepthTest();
        gg.pose().pushPose();
        gg.pose().translate(0, 0, 500);
        int i = this.screenWidth / 2;
        gg.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/hotbar/hotbar_1.png"),
                i - 91, this.screenHeight - 22, 182, 22, 0, 0, 182, 22, 182, 22);
        gg.pose().popPose();
        RenderSystem.enableDepthTest();
        gg.flush();
    }
}