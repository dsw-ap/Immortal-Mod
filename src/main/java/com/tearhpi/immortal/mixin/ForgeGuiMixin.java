package com.tearhpi.immortal.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.chatbox.ChatBoxRegister;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.capability.SkillAssemble;
import com.tearhpi.immortal.networking.ChatboxSyncPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.skill.PassiveSkillAttribute;
import com.tearhpi.immortal.util.TextRenderer;
import com.tearhpi.immortal.util.TextureSizeCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static com.tearhpi.immortal.util.TextRenderer.getMaxLengthOfsplitTextByWidth;
import static com.tearhpi.immortal.util.TextRenderer.splitTextByWidth;

@Mixin(ForgeGui.class)
public abstract class ForgeGuiMixin {

    /**
     * Inject to draw the custom skill hotbar on the HUD.
     * This runs at the end of Gui.render (TAIL), after the normal hotbar is drawn.
     */
    private static final ResourceLocation barSide_On =
            ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/skill/skill_on.png");
    private static final ResourceLocation barSide_Off =
            ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/skill/skill_off.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void immortal$drawSkillStrip(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;
        if (mc.player == null) return;
        // Determine screen dimensions
        int screenW = guiGraphics.guiWidth();
        int screenH = guiGraphics.guiHeight();
        // Skill slot icon size and positioning
        int SIZE = 18;
        int GAP = 3;
        int MARGINX = 15;
        int MARGINY = 0;
        // Calculate base positions for the two skill bars (left and right of hotbar)
        int baseXRight = (screenW - 182) / 2 + 182 + MARGINX;          // 182 = HOTBAR_WIDTH
        int baseXLeft  = (screenW - 182) / 2 - (5 * SIZE + 4 * GAP) - MARGINX;
        int baseY      = screenH - 22 + (22 - SIZE) / 2 - MARGINY;     // 22 = HOTBAR_HEIGHT
        Player player = mc.player;
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        SkillAssemble skills = immortalPlayer.getSkillAssemble();
        // Fetch skill icons for slots 1-10 if set
        PassiveSkillAttribute[] icons1 = new PassiveSkillAttribute[5];
        PassiveSkillAttribute[] icons2 = new PassiveSkillAttribute[5];
        if (skills.Slot1 != -1) icons1[0] = ModSkills.getSkillbyNum(skills.Slot1,player);
        if (skills.Slot2 != -1) icons1[1] = ModSkills.getSkillbyNum(skills.Slot2,player);
        if (skills.Slot3 != -1) icons1[2] = ModSkills.getSkillbyNum(skills.Slot3,player);
        if (skills.Slot4 != -1) icons1[3] = ModSkills.getSkillbyNum(skills.Slot4,player);
        if (skills.Slot5 != -1) icons1[4] = ModSkills.getSkillbyNum(skills.Slot5,player);
        if (skills.Slot6 != -1) icons2[0] = ModSkills.getSkillbyNum(skills.Slot6,player);
        if (skills.Slot7 != -1) icons2[1] = ModSkills.getSkillbyNum(skills.Slot7,player);
        if (skills.Slot8 != -1) icons2[2] = ModSkills.getSkillbyNum(skills.Slot8,player);
        if (skills.Slot9 != -1) icons2[3] = ModSkills.getSkillbyNum(skills.Slot9,player);
        if (skills.Slot10 != -1) icons2[4] = ModSkills.getSkillbyNum(skills.Slot10,player);
        // Determine which skill bar is active (UseSkill = 0 or 1)
        int activeBar = skills.UseSkill;
        Font font = mc.font;
        // Draw the first skill bar (slots 1-5, on the left side of hotbar)
        for (int i = 0; i < 5; i++) {
            int x = baseXLeft + i * (SIZE + GAP);
            int y = baseY;
            if (icons1[i] != null) {
                // Draw skill icon
                guiGraphics.blit(icons1[i].skillIcon, x, y, SIZE, SIZE,0,0,256,256,256,256);
                // If skill on cooldown, overlay dark tint and cooldown time
                int cd = ModSkills.getCDbyNumAndPlayer(icons1[i].skillId, player);
                //CD显示
                if (icons1[i].skillId == 35) {
                    int count = immortalPlayer.getSkillCD().Skill35_Charge;
                    if (count == 0) {
                        int cdSeconds = cd / 20 + 1;
                        Component cdText = Component.literal(String.valueOf(cdSeconds));
                        guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x88000000);
                        guiGraphics.drawString(font, cdText, x + (SIZE - font.width(cdText)) / 2, y + (SIZE - font.lineHeight) / 2 + 1, 0xFFFFFFFF, false);
                    } else {
                        int cdSeconds = cd / 20 + 1;
                        Component cdText = Component.literal(String.valueOf(count));
                        //guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x88000000);
                        guiGraphics.drawString(font, cdText, x + 1*(SIZE - font.width(cdText)) / 4, y + 1*(SIZE - font.lineHeight) / 4 + 1, 0xFFFFFFFF, false);
                    }
                } else {
                    if (cd > 0) {
                        int cdSeconds = cd / 20 + 1;
                        Component cdText = Component.literal(String.valueOf(cdSeconds));
                        guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x88000000);
                        guiGraphics.drawString(font, cdText, x + (SIZE - font.width(cdText)) / 2, y + (SIZE - font.lineHeight) / 2 + 1, 0xFFFFFFFF, false);
                    }
                }
            } else {
                // Empty slot – draw semi-transparent background
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0xDD000000);
            }
            // Draw slot frame overlay
            guiGraphics.blit(barSide_On, x, y, SIZE, SIZE,0,0,36,36,36,36);
            // If the second skill bar is active, gray out this bar
            if (activeBar == 1 || immortalPlayer.getSkillInUse().IsInUseSkill > 0) {
                //黑
                guiGraphics.blit(barSide_Off, x, y, SIZE, SIZE,0,0,36,36,36,36);
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x77000000);
            }
        }
        // Draw the second skill bar (slots 6-10, on the right side of hotbar)
        for (int i = 0; i < 5; i++) {
            int x = baseXRight + i * (SIZE + GAP);
            int y = baseY;
            if (icons2[i] != null) {
                guiGraphics.blit(icons2[i].skillIcon, x, y, SIZE, SIZE,0,0,256,256,256,256);
                int cd = ModSkills.getCDbyNumAndPlayer(icons2[i].skillId, player);
                //CD显示
                if (icons2[i].skillId == 35) {
                    int count = immortalPlayer.getSkillCD().Skill35_Charge;
                    if (count == 0) {
                        int cdSeconds = cd / 20 + 1;
                        Component cdText = Component.literal(String.valueOf(cdSeconds));
                        guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x88000000);
                        guiGraphics.drawString(font, cdText, x + (SIZE - font.width(cdText)) / 2, y + (SIZE - font.lineHeight) / 2 + 1, 0xFFFFFFFF, false);
                    } else {
                        int cdSeconds = cd / 20 + 1;
                        Component cdText = Component.literal(String.valueOf(count));
                        //guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x88000000);
                        guiGraphics.drawString(font, cdText, x + 1*(SIZE - font.width(cdText)) / 4, y + 1*(SIZE - font.lineHeight) / 4 + 1, 0xFFFFFFFF, false);
                    }
                } else {
                    if (cd > 0) {
                        int cdSeconds = cd / 20 + 1;
                        Component cdText = Component.literal(String.valueOf(cdSeconds));
                        guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x88000000);
                        guiGraphics.drawString(font, cdText, x + (SIZE - font.width(cdText)) / 2, y + (SIZE - font.lineHeight) / 2 + 1, 0xFFFFFFFF, false);
                    }
                }
            } else {
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x77000000);
            }
            guiGraphics.blit(barSide_On, x, y, SIZE, SIZE,0,0,36,36,36,36);
            // If the first skill bar is active, gray out this bar
            if (activeBar == 0 || immortalPlayer.getSkillInUse().IsInUseSkill > 0) {
                //黑
                guiGraphics.blit(barSide_Off, x, y, SIZE, SIZE,0,0,36,36,36,36);
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + SIZE, y + SIZE, 0x77000000);
            }
        }

        //剧情渲染
        renderChatbox(screenW, screenH, guiGraphics);
    }

    @Shadow(remap = false) public int rightHeight;

    @Shadow(remap = false) public abstract Minecraft getMinecraft();

    /**
     * Overwrite to replace the food (hunger) and mana bar rendering with custom graphics.
     * (Rewritten for 1.20.1)
     * @author TearH_Pi
     * @reason Overwrite to replace the food (hunger) and mana bar rendering with custom graphics.
     */
    @Overwrite(remap=false)
    public void renderFood(int width, int height, GuiGraphics guiGraphics) {
        RenderSystem.enableBlend();
        Minecraft mc = Minecraft.getInstance();
        PoseStack poseStack = guiGraphics.pose();
        ForgeGui forgeGui = ((ForgeGui) (Object)this);
        // Food (hunger) bar rendering
        Player player = (Player) forgeGui.getMinecraft().player;
        RenderSystem.enableBlend();
        int left = width / 2 + 91;
        int top = height - rightHeight;
        rightHeight += 10;

        // Define custom textures for food bar
        ResourceLocation foodLabel      = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/food_label.png");
        ResourceLocation foodLabelEmpty = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/food_label_gray.png");
        ResourceLocation foodRemain     = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/food_full.png");
        // Define custom textures for mana bar
        ResourceLocation mpLabel        = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/mp_label.png");
        ResourceLocation mpLabelEmpty   = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/mp_label_gray.png");
        ResourceLocation mpRemain       = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/mp_full.png");
        /** 饱食度相关渲染 */
        //渲染饱食度条
        FoodData foodData = player.getFoodData();
        int foodLevel = foodData.getFoodLevel();
        int maxFood = 20;
        if (foodLevel > 0) {
            int totalLength = 76;
            float ratio = Math.min((float) foodLevel / maxFood, 1.0F);
            int filledLength = (int)(ratio * totalLength + 10);
            if (player.hasEffect(MobEffects.HUNGER)) {
                RenderSystem.setShaderColor(0.48F, 1.17F, 1.31F, 1.0F);
                guiGraphics.blit(foodRemain,left - 85+10, top - 16,96-filledLength, 0,filledLength, 32, 96, 32);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                guiGraphics.blit(foodRemain,left - 85+10, top - 16,96-filledLength, 0,filledLength, 32, 96, 32);
            }
        }
        //渲染饱食度外框
        guiGraphics.blit(foodLabelEmpty, left - 85, top - 16,96,32,0,0,96,32,96,32);
        if (foodLevel > 0) {
            guiGraphics.blit(foodLabel, left - 85, top - 16,96,32,0,0,96,32,96,32);
        }
        //渲染饱和度
        float saturation = foodData.getSaturationLevel();
        int maxSaturation = 20;
        if (saturation > 0) {
            int totalLength = 76;
            float satRatio = Math.min(saturation / maxSaturation, 1.0F);
            int satLength = (int)(satRatio * totalLength) + 10;
            guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/hud/mp_food/food_saturation.png"), left-85+9,top-14,satLength,32,10,0,satLength,32,96,32);
        }
        //渲染饱食度文字
        poseStack.pushPose();
        poseStack.translate(left-67, top-6, 0.0f);
        poseStack.scale(0.5f, 0.5f, 1.0f);
        Component s1 = Component.literal(String.valueOf(foodLevel));
        guiGraphics.drawString(mc.font, s1, 1, 0, 2368052, false);
        guiGraphics.drawString(mc.font, s1, -1, 0, 2368052, false);
        guiGraphics.drawString(mc.font, s1, 0, 1, 2368052, false);
        guiGraphics.drawString(mc.font, s1, 0, -1, 2368052, false);
        guiGraphics.drawString(mc.font,s1,0,0,0xFFFFCC33,false);
        poseStack.popPose();
        RenderSystem.disableBlend();
        /** 魔力值相关渲染 */
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        int mana = immortalPlayer.getManaPoint().get();
        int maxMana = (int) player.getAttributeValue(ModAttributes.IMMORTAL_MAGIC_POINT_MAX.getHolder().get());
        //渲染魔力值条
        if (mana > 0) {
            int totalLength = 76;
            float manaRatio = Math.min((float) mana / maxMana, 1.0F);
            int filledLength = (int)(manaRatio * totalLength + 20);
            guiGraphics.blit(mpRemain, left - 85 + (96 - filledLength) - 12 - 96,top - 15,0.0F,0.0F,filledLength-10,32,96,32);
        }
        //渲染魔力值框
        guiGraphics.blit(mpLabelEmpty, left - 85 - 96-12, top - 15,0,0, 96, 32,96,32);
        if (mana > 0) {
            guiGraphics.blit(mpLabel, left - 85 - 96-12, top - 15,0,0, 96, 32,96,32);
        }
        //渲染魔力值文字
        poseStack.pushPose();
        poseStack.translate(left-124.5, top-6, 0.0f);
        poseStack.scale(0.5f, 0.5f, 1.0f);
        Component s2 = Component.literal(String.valueOf(mana));
        guiGraphics.drawString(mc.font, s2, 1, 0, 2368052, false);
        guiGraphics.drawString(mc.font, s2, -1, 0, 2368052, false);
        guiGraphics.drawString(mc.font, s2, 0, 1, 2368052, false);
        guiGraphics.drawString(mc.font, s2, 0, -1, 2368052, false);
        guiGraphics.drawString(mc.font,s2,0,0,0xFF33CCFF,false);
        poseStack.popPose();
        RenderSystem.disableBlend();
    }

    //最大血量Redirect
    @org.spongepowered.asm.mixin.injection.Redirect(
            method = "renderHealth(IILnet/minecraft/client/gui/GuiGraphics;)V",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"),
            remap = false
    )
    private float immortal$redirectHealthMax(float a, float b) {
        Player player = (Player) Minecraft.getInstance().getCameraEntity();
        if (player != null) {
            AttributeInstance inst = player.getAttribute(ModAttributes.IMMORTAL_MAX_HEALTH.get());
            if (inst != null) a = (float) inst.getValue(); // 用自定义属性替换第一个参数
        }
        return Math.max(a, b);
    }

    /**
     * @author TearHPi
     * @reason 剧情部分渲染 挂在immortal$drawSkillStrip下
     */
    private void renderChatbox(int width, int height, GuiGraphics guiGraphics) {
        Player player = (Player) Minecraft.getInstance().player;
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        if (immortalPlayer != null) {
            int id = immortalPlayer.getChatbox().getID();
            //System.out.println(id);
            if (id != -1) {
                Font font = Minecraft.getInstance().font;
                ResourceLocation left = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/chatbox/left.png");
                ResourceLocation right = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/chatbox/right.png");
                ResourceLocation all = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/chatbox/all.png");
                ResourceLocation part = ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/chatbox/part.png");
                //横偏移
                int val_cur = immortalPlayer.getChatbox().Chatbox_countdown;
                int val_prev = val_cur == 0 ? 0 : val_cur + 1;
                float pt = Minecraft.getInstance().getFrameTime(); // 0~1
                pt = net.minecraft.util.Mth.clamp(pt, 0.0f, 1.0f);
                float cdSmooth = net.minecraft.util.Mth.lerp(pt, val_prev, val_cur);
                if (immortalPlayer.getChatboxPT() <= 0.0f) {
                    immortalPlayer.setChatboxPT(cdSmooth);
                } else {
                    float val = immortalPlayer.getChatboxPT();
                    if (cdSmooth >= val) {
                        cdSmooth = val;
                    }
                    immortalPlayer.setChatboxPT(cdSmooth);
                }
                int x_translation = (int) (cdSmooth*width/100);
                float alpha = (float) (100 - cdSmooth * 8) /100;
                /** 渲染 */
                //获取需要渲染的信息
                int num = immortalPlayer.getChatbox().getNum();
                ArrayList<?> output = ChatBoxRegister.GetChatBox(id,player);
                //话语相关渲染
                if (output != null) {
                    if (num > output.size()-1) {
                        //释放最终效果
                        ChatBoxRegister.ChatBoxEnd(id, player);
                        //刷新
                        immortalPlayer.getChatbox().reset();
                        ModNetworking.CHANNEL.sendToServer(new ChatboxSyncPacket(immortalPlayer.getChatbox().Chatbox_id,immortalPlayer.getChatbox().Chatbox_num,immortalPlayer.getChatbox().Chatbox_countdown));
                    } else {
                        String name = (String) ((ArrayList<?>)output.get(num)).get(0);
                        String text = (String) ((ArrayList<?>)output.get(num)).get(1);
                        //底层贴图(无对话者)
                        if (name.equals("None")) {
                            //占总屏幕3/4，左边占3/4的1/4，右边占3/4的3/4
                            int size = width * 3/16;
                            int margin = (width-size*3)/2;
                            int size_image = size*3/5;
                            //文本相关排版
                            int size_text = 3 * size * 9/10;
                            int next_page_margin = size/16;

                            //渲染-背景
                            guiGraphics.blit(part,margin,(int) (height-size*1.25),3*size,size,0,0,192,64,192,64);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
                            //文本渲染
                            if (text != null) {
                                Component text_display = Component.translatable(text);
                                int height_text = (size - splitTextByWidth(font, text_display, size_text).size()*font.lineHeight)/2;
                                int width_margin_text = (int) ((3 * size - getMaxLengthOfsplitTextByWidth(font, text_display, size_text))/2);
                                TextRenderer.drawWrappedText(guiGraphics,font,text_display,x_translation+margin+width_margin_text, (int) (height-size*1.25)+height_text/2,size_text,font.lineHeight,0xFFFFFF,false);
                                Component next_page = Component.translatable("ChatBox_NextPage");
                                guiGraphics.drawString(font,next_page,x_translation+margin+3*size-next_page_margin-font.width(next_page), (int) (height-size*0.25)-next_page_margin-font.lineHeight,0xFFFFFF,true);
                            }
                            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                            RenderSystem.disableBlend();
                        } else {
                            //占总屏幕3/4，左边占3/4的1/4，右边占3/4的3/4
                            int size = width * 3/16;
                            int margin = (width-size*4)/2;
                            //头像图片大小
                            int size_image = size*3/5;
                            int margin_image = (size-size_image)/2;
                            //文本相关排版
                            int size_text = 3 * size * 9/10;
                            int margin_text = (3 * size - size_text)/2;
                            int next_page_margin = size/16;
                            //渲染-背景
                            //guiGraphics.blit(left,margin, (int) (height-size*1.25),size,size,0,0,32,32,32,32);
                            guiGraphics.blit(all,margin,(int) (height-size*1.25),4*size,size,0,0,256,64,256,64);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
                            //头像名字渲染
                            //若是玩家
                            if (name.equals("Player")) {
                                ResourceLocation image = ((AbstractClientPlayer) player).getSkinTextureLocation();
                                guiGraphics.blit(image,x_translation+margin+margin_image, (int) (height-size*1.25)+margin_image,size_image,size_image,8,8,8,8,64,64);
                                guiGraphics.blit(image,x_translation+margin+margin_image, (int) (height-size*1.25)+margin_image,size_image,size_image,40,8,8,8,64,64);
                                Component name_display = player.getDisplayName();
                                double length = font.width(name_display);
                                guiGraphics.drawString(font,name_display,x_translation+(int) (margin+(size-length)/2), (int) (height-size*1.25)+size_image+margin_image+(margin_image-font.lineHeight)/2,0xFFFFFF);
                            } else {
                                ResourceLocation image = ChatBoxRegister.GetTextureWithoutPlayer(name);
                                int[] wh = TextureSizeCache.getSize(image);
                                int w = wh[0], h = wh[1];
                                Component name_display = Component.translatable(name);
                                double length = font.width(name_display);
                                if (w > 0 && h > 0) {
                                    guiGraphics.blit(image, x_translation+margin + margin_image, (int) (height - size * 1.25) + margin_image, size_image, size_image, 0, 0, w, h, w, h);
                                }
                                guiGraphics.drawString(font,name_display,x_translation+(int) (margin+(size-length)/2), (int) (height-size*1.25)+size_image+margin_image+(margin_image-font.lineHeight)/2,0xFFFFFF);
                            }
                            //文本渲染
                            if (text != null) {
                                Component text_display = Component.translatable(text);
                                TextRenderer.drawWrappedText(guiGraphics,font,text_display,x_translation+margin+size+margin_text, (int) (height-size*1.25)+margin_image/2,size_text,font.lineHeight,0xFFFFFF,false);
                                Component next_page = Component.translatable("ChatBox_NextPage");
                                guiGraphics.drawString(font,next_page,x_translation+margin+4*size-next_page_margin-font.width(next_page), (int) (height-size*0.25)-next_page_margin-font.lineHeight,0xFFFFFF,true);
                            }
                            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                            RenderSystem.disableBlend();
                        }
                    }
                }
            }
        }
    }
    /**
     * @author TearHPi
     * @reason 删除原版气泡渲染
     */
    @Overwrite(remap=false)
    protected void renderAir(int width, int height, GuiGraphics guiGraphics)
    {return;}
}