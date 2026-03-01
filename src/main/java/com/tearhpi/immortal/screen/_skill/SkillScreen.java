package com.tearhpi.immortal.screen._skill;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.brigadier.Command;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.Coin;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.capability.Skill;
import com.tearhpi.immortal.entity.capability.SkillAssemble;
import com.tearhpi.immortal.networking.*;
import com.tearhpi.immortal.screen._skill.button.ActiveImageButton;
import com.tearhpi.immortal.screen._skill.button.ImageButtonModify;
import com.tearhpi.immortal.screen._skill.button.ShadowImageButton;
import com.tearhpi.immortal.screen._skill.button.ShadowImageSkillButton;
import com.tearhpi.immortal.screen._skill.line.WidgetConnection;
import com.tearhpi.immortal.skill.ActiveSkillAttribute;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.skill.PassiveSkillAttribute;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jline.reader.Widget;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntSupplier;

public class SkillScreen extends Screen {
    Player player = Minecraft.getInstance().player;
    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
    PassiveSkillAttribute skillInDisplay;
    private int guiX = 100;
    private int guiY = 80;
    //屏幕拖动
    private boolean dragging = false;
    private int dragOffsetX, dragOffsetY;
    //连接线
    private final LinkedList<WidgetConnection> connections = new LinkedList<>();
    //技能装配面板
    private int ShowSkillBar;
    //---技能装配---
    private boolean picking = false;
    private int pickedSkillId = -1;
    private ResourceLocation pickedSprites = null;
    private int ghostDx = 0, ghostDy = 0;

    //技能信息演示部分
    private int ShownNumber = -1;
    private ResourceLocation ShowIcon;
    private Component SkillName = null;
    private Component SkillLore = null;
    //详细信息
    private StringWidget label;
    private Component skill_moreInfo = Component.translatable("skill.more_information");
    //升级按钮
    public ImageButton Upgrade;
    //public WidgetSprites Upgrade_info = new WidgetSprites(
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/ui/upgrade_button"),
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/ui/upgrade_button_disabled"),
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/ui/upgrade_button_press"));
    //装配按钮
    public ImageButton SkillAssemble;
    //public WidgetSprites SkillAssemble_info = new WidgetSprites(
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_decor/side"),
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_decor/side"),
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_decor/side"));
    //重置按钮
    public Button SkillReset;
    //public ImageButton SkillReset;
    //public WidgetSprites SkillReset_info = new WidgetSprites(
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_decor/reset"),
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_decor/reset"),
    //        ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_decor/reset_press"));
    private Modal resetModal;
    //技能信息
    public ImageButton Skill_0;
    public ImageButton Skill_1;
    public ImageButton Skill_2;
    public ImageButton Skill_3;
    public ImageButton Skill_4;
    public ImageButton Skill_5;
    public ImageButton Skill_6;
    public ImageButton Skill_7;
    public ImageButton Skill_8;
    public ImageButton Skill_9;
    public ImageButton Skill_10;
    public ImageButton Skill_11;
    public ImageButton Skill_12;
    public ImageButton Skill_13;
    public ImageButton Skill_14;
    public ImageButton Skill_15;
    public ImageButton Skill_16;
    public ImageButton Skill_17;
    public ImageButton Skill_18;
    public ImageButton Skill_19;
    public ImageButton Skill_20;
    public ImageButton Skill_21;
    public ImageButton Skill_22;
    public ImageButton Skill_23;
    public ImageButton Skill_24;
    public ImageButton Skill_25;
    public ImageButton Skill_26;
    public ImageButton Skill_27;
    public ImageButton Skill_28;
    public ImageButton Skill_29;
    public ImageButton Skill_30;
    public ImageButton Skill_31;
    public ImageButton Skill_32;
    public ImageButton Skill_33;
    public ImageButton Skill_34;
    public ImageButton Skill_35;
    public ImageButton Skill_36;
    public ImageButton Skill_37;
    public ImageButton Skill_38;
    public ImageButton Skill_39;
    public ImageButton Skill_40;
    public ImageButton Skill_41;
    public ImageButton Skill_42;
    public ImageButton Skill_43;
    public ImageButton Skill_44;
    public ImageButton Skill_45;
    public ImageButton Skill_46;
    public ImageButton Skill_47;
    public ImageButton Skill_48;
    public ImageButton Skill_49;
    public ImageButton Skill_50;
    public ImageButton Skill_51;
    public ImageButton Skill_52;
    public ImageButton Skill_53;
    public ImageButton Skill_54;
    public ImageButton Skill_55;
    public ImageButton Skill_56;
    public ImageButton Skill_57;
    public ImageButton Skill_58;
    public ImageButton Skill_59;
    public ImageButton Skill_60;
    public ImageButton Skill_61;
    public ImageButton Skill_62;
    public ImageButton Skill_63;
    public ImageButton Skill_64;
    public ImageButton Skill_65;
    public ImageButton Skill_66;
    public ImageButton Skill_67;
    public ImageButton Skill_68;
    public ImageButton Skill_69;
    public ImageButton Skill_70;
    //构造函数
    public SkillScreen(Component p_96550_) {
        super(p_96550_);
    }
    //屏幕初始化
    @Override
    protected void init() {
        //玩家获取
        Player player = Minecraft.getInstance().player;
        //清除连接线
        this.connections.clear();

        /**
        this.button_test = Button.builder(
                Component.literal("INCREASE"),
                       btn -> {
                        }
                ).bounds(guiX + 10, guiY + 10,100,20)
                .build();
        */
        //设置按钮
        this.Skill_0 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_0.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_0,120,40,256,256);
        this.Skill_1 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_1.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_1,90,70,256,256);
        this.Skill_2 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_2.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_2,120,70,256,256);
        this.Skill_3 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_3.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_3,150,70,256,256);
        this.Skill_4 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_4.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_4,180,40,256,256);
        //火系
        this.Skill_5 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_5.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_5,180,40,40,100,256,256);
        this.Skill_6 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_6.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_6,180,40,70,100,256,256);
        this.Skill_7 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_7.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_7,180,40,100,90,256,256);
        this.Skill_8 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_8.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_8,180,40,100,110,256,256);
        this.Skill_9 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_9.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_9,180,40,140,110,256,256);
        this.Skill_10 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_10.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_10,180,40,140,90,256,256);
        this.Skill_11 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_11_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_11,180-4,40-4,180,90,336,336);
        this.Skill_12 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_12.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_12,180,40,180,110,256,256);
        this.Skill_13 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_13.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_13,180,40,220,110,256,256);
        this.Skill_14 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_14.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_14,180,40,220,90,256,256);
        this.Skill_15 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_15_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_15,180-4,40-4,260,110,336,336);
        //水系
        this.Skill_16 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_16.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_16,180,40,40,60,256,256);
        this.Skill_17 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_17.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_17,180,40,70,60,256,256);
        this.Skill_18 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_18.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_18,180,40,100,50,256,256);
        this.Skill_19 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_19.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_19,180,40,100,70,256,256);
        this.Skill_20 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_20.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_20,180,40,140,70,256,256);
        this.Skill_21 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_21.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_21,180,40,140,50,256,256);
        this.Skill_22 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_22_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_22,180-4,40-4,180,50,336,336);
        this.Skill_23 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_23.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_23,180,40,180,70,256,256);
        this.Skill_24 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_24.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_24,180,40,220,70,256,256);
        this.Skill_25 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_25.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_25,180,40,220,50,256,256);
        this.Skill_26 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_26_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_26,180-4,40-4,260,70,336,336);
        //风系
        this.Skill_27 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_27.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_27,180,40,40,20,256,256);
        this.Skill_28 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_28.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_28,180,40,70,20,256,256);
        this.Skill_29 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_29.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_29,180,40,100,10,256,256);
        this.Skill_30 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_30.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_30,180,40,100,30,256,256);
        this.Skill_31 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_31.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_31,180,40,140,30,256,256);
        this.Skill_32 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_32.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_32,180,40,140,10,256,256);
        this.Skill_33 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_33_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_33,180-4,40-4,180,10,336,336);
        this.Skill_34 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_34.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_34,180,40,180,30,256,256);
        this.Skill_35 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_35.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_35,180,40,220,30,256,256);
        this.Skill_36 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_36.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_36,180,40,220,10,256,256);
        this.Skill_37 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_37_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_37,180-4,40-4,260,30,336,336);
        //土系
        this.Skill_38 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_38.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_38,180,40,40,-20,256,256);
        this.Skill_39 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_39.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_39,180,40,70,-20,256,256);
        this.Skill_40 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_40.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_40,180,40,100,-30,256,256);
        this.Skill_41 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_41.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_41,180,40,100,-10,256,256);
        this.Skill_42 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_42.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_42,180,40,140,-10,256,256);
        this.Skill_43 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_43.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_43,180,40,140,-30,256,256);
        this.Skill_44 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_44_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_44,180-4,40-4,180,-30,336,336);
        this.Skill_45 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_45.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_45,180,40,180,-10,256,256);
        this.Skill_46 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_46.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_46,180,40,220,-10,256,256);
        this.Skill_47 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_47.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_47,180,40,220,-30,256,256);
        this.Skill_48 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_48_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_48,180-4,40-4,260,-10,336,336);
        //光系
        this.Skill_49 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_49.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_49,180,40,40,-60,256,256);
        this.Skill_50 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_50.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_50,180,40,70,-60,256,256);
        this.Skill_51 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_51.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_51,180,40,100,-70,256,256);
        this.Skill_52 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_52.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_52,180,40,100,-50,256,256);
        this.Skill_53 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_53.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_53,180,40,140,-50,256,256);
        this.Skill_54 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_54.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_54,180,40,140,-70,256,256);
        this.Skill_55 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_55_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_55,180-4,40-4,180,-70,336,336);
        this.Skill_56 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_56.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_56,180,40,180,-50,256,256);
        this.Skill_57 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_57.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_57,180,40,220,-50,256,256);
        this.Skill_58 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_58.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_58,180,40,220,-70,256,256);
        this.Skill_59 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_59_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_59,180-4,40-4,260,-50,336,336);
        //暗系
        this.Skill_60 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_60.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_60,180,40,40,-100,256,256);
        this.Skill_61 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_61.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_61,180,40,70,-100,256,256);
        this.Skill_62 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_62.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_62,180,40,100,-110,256,256);
        this.Skill_63 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_63.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_63,180,40,100,-90,256,256);
        this.Skill_64 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_64.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_64,180,40,140,-90,256,256);
        this.Skill_65 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_65.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_65,180,40,140,-110,256,256);
        this.Skill_66 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_66_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_66,180-4,40-4,180,-110,336,336);
        this.Skill_67 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_67.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_67,180,40,180,-90,256,256);
        this.Skill_68 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_68.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_68,180,40,220,-90,256,256);
        this.Skill_69 = new ShadowImageSkillButton(guiX, guiY, 16, 16, ModSkills.Skill_69.skillIcon,
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_69,180,40,220,-110,256,256);
        this.Skill_70 = new ShadowImageSkillButton(guiX, guiY, 24, 24, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_70_spec.png"),
                btn -> {ResetSkillDisplay(((ShadowImageSkillButton)btn).passiveSkillAttribute);},1,1,0.5f,ModSkills.Skill_70,180-4,40-4,260,-90,336,336);

        //初始化 具体技能信息显示 按钮
        this.label = new StringWidget(skill_moreInfo, this.font);
        label.setColor(0xFFFFFF);
        //初始化 技能升级按钮
        this.Upgrade = new ActiveImageButton(-100,-100, this.width/10, this.width/40,0,0, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/ui/upgrade.png"), btn -> {upgradeSkillLevel();},16,64,48);
        //技能装配按钮
        this.SkillAssemble = new ImageButtonModify(this.width*69/100, this.height*67/72,this.height*12/28/18,this.height/18, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_decor/side.png"),
                btn -> {if (this.ShowSkillBar == 0){this.ShowSkillBar = 1;} else {this.ShowSkillBar = 0;}},12,28);
        this.SkillReset = Button.builder(Component.translatable("skill.Reset"),
                btn -> {openResetModal();}).bounds(this.width*35/100, this.height*67/72, this.width/7, this.height/18).build();
        //this.SkillReset = new ShadowImageButton(this.width*40/100, this.height*67/72, this.width/10, this.width/40, SkillReset_info,
        //        btn -> {resetSkillScreen();},1,1,0.5f);

        //特殊功能按钮位置判定
        if (ShownNumber == -1) {
            label.setPosition(-10000,-10000);
        } else {
            label.setPosition(this.width*73/100+(this.width/4-Math.min(this.width/4,this.font.width(skill_moreInfo)))/2,this.height*68/100-this.font.lineHeight);
            resetUpgradeButton();
        }
        //判定是否存在玩家技能栏
        ShowSkillBar = 0;
        //技能槽位配置
        registerSlots();
        //初始化连接线
        for (ShadowImageSkillButton s:getAbsWidgets()){
            PassiveSkillAttribute passiveSkillAttribute = s.passiveSkillAttribute;
            if (passiveSkillAttribute.preconditionId != null) {
                try {
                    Field f = this.getClass().getDeclaredField("Skill_"+passiveSkillAttribute.preconditionId.skillId);
                    f.setAccessible(true);
                    AbstractWidget preWid = (AbstractWidget) f.get(this);
                    connections.add(new WidgetConnection(preWid, s));
                } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //获得所有Skill列表
    public ArrayList<ShadowImageSkillButton> getAbsWidgets() {
        ArrayList<ShadowImageSkillButton> widgets = new ArrayList<>();
        int i = 0;
        while (true) {
            try {
                Field f = this.getClass().getDeclaredField("Skill_"+i);
                f.setAccessible(true);
                widgets.add((ShadowImageSkillButton) f.get(this));
                i += 1;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                break;
            }
        }
        return widgets;
    }
    //重置技能-面板
    private void openResetModal() {
        if (resetModal == null) {
            resetModal = new Modal(this.width/2-80, this.height/2-60, 160, 120);
            resetModal.add(Button.builder(Component.literal("OK"), b -> resetSkillScreen())
                    .bounds(this.width/2-60, this.height/2, 50, 20).build());
            resetModal.add(Button.builder(Component.literal("Cancel"), b -> resetModal = null)
                    .bounds(this.width/2+10, this.height/2, 50, 20).build());
            Component strwComp = Component.translatable("skill.Reset_Mind");
            StringWidget strw = new StringWidget(strwComp, this.font);
            strw.setColor(0xFFFFFF);
            strw.setPosition(this.width/2-this.font.width(strwComp)/2, this.height/2-30);
            resetModal.add(strw);
        }
    }
    //重置技能-核心
    private void resetSkillScreen() {
        iImmortalPlayer.getSkill().reset();
        iImmortalPlayer.getSkillAssemble().reset();
        ModNetworking.CHANNEL.sendToServer(new SkillInfoSyncPacket(iImmortalPlayer.getSkill().getSkillCompoundTag(iImmortalPlayer.getSkill().getNumInfo())));
        ModNetworking.CHANNEL.sendToServer(new SkillAssembleInfoSyncPacket(iImmortalPlayer.getSkillAssemble().getSkillCompoundTag(iImmortalPlayer.getSkillAssemble().getNumInfo())));
        resetModal = null;
    }
    //设置显示技能内容
    private void ResetSkillDisplay(PassiveSkillAttribute pSkillAttr) {
        skillInDisplay = pSkillAttr;
        ShownNumber = pSkillAttr.skillId;
        ShowIcon = pSkillAttr.skillIcon;
        SkillName = pSkillAttr.skillName;
        SkillLore = pSkillAttr.skillIntro;
        resetInfoButton(pSkillAttr.skillInfo);
        resetUpgradeButton();
        if (this.ShowSkillBar == 1 && pSkillAttr instanceof ActiveSkillAttribute) {
            if (ModSkills.getLevelbyNumAndPlayer(pSkillAttr.skillId,player) > 0) {
                beginPick(pSkillAttr);
            }
        }
    }
    private void resetInfoButton(Component info) {
        label.setPosition(this.width*73/100+(this.width/4-Math.min(this.width/4,this.font.width(skill_moreInfo)))/2,this.height*68/100-this.font.lineHeight);
        label.setTooltip(Tooltip.create(info));
    }
    //设置技能升级按钮
    private void resetUpgradeButton() {
        Upgrade.setPosition(this.width*705/1000+this.width/10, this.height*8/9);
    }
    //屏幕移动相关
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    private boolean isInDragSurface(double mouseX, double mouseY) {
        int leftPaneW = this.width * 2 / 3; // 左侧2/3屏幕区域
        return mouseX >= 0 && mouseX < leftPaneW && mouseY >= 0 && mouseY < this.height;
    }
    private boolean isOverAnyWidget(double mouseX, double mouseY) {
        for (GuiEventListener child : this.children()) {
            if (child.isMouseOver(mouseX, mouseY)) return true;
        }
        return false;
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //子面板
        if (resetModal != null) return resetModal.mouseClicked(mouseX,mouseY,button);
        //父面板
        if (button == 0) {
            if (isInDragSurface(mouseX, mouseY) && !isOverAnyWidget(mouseX, mouseY)) {
                dragging = true;
                this.setDragging(true);
                dragOffsetX = (int) mouseX - guiX;
                dragOffsetY = (int) mouseY - guiY;
                return true;
            }
        }
        if (picking) {
            if (button == 0) {
                tryPlaceAt(mouseX, mouseY);
            } else if (button == 1) {
                cancelPick();
                return true;
            }
        }

        if (SkillAssemble != null && SkillAssemble.visible &&SkillAssemble.active
                && SkillAssemble.isMouseOver(mouseX, mouseY)
                && SkillAssemble.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (Upgrade != null && Upgrade.visible && Upgrade.active
                && Upgrade.isMouseOver(mouseX, mouseY)
                && Upgrade.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        // 其它情况交给组件（按钮等）处理，保证按钮点击不受影响
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
            this.setDragging(false);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (dragging && button == 0) {
            guiX = (int) mouseX - dragOffsetX;
            guiY = (int) mouseY - dragOffsetY;
            //所有技能位置同步
            for (ShadowImageSkillButton s:getAbsWidgets()) {
                s.setPosition(guiX + s.dx, guiY + s.dy);
            }
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }
    //连接线绘制
    public void drawDiagonalLine(GuiGraphics g, int x1, int y1, int x2, int y2, int argbColor) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float length = Mth.sqrt(dx * dx + dy * dy);
        if (length == 0) return;

        float lineWidth = 1.5f;
        float offsetX = -(dy / length) * (lineWidth / 2);
        float offsetY =  (dx / length) * (lineWidth / 2);

        x1 = Math.round(x1); y1 = Math.round(y1);
        x2 = Math.round(x2); y2 = Math.round(y2);

        float p1x = x1 + offsetX + 0.5f, p1y = y1 + offsetY + 0.5f;
        float p2x = x2 + offsetX + 0.5f, p2y = y2 + offsetY + 0.5f;
        float p3x = x2 - offsetX + 0.5f, p3y = y2 - offsetY + 0.5f;
        float p4x = x1 - offsetX + 0.5f, p4y = y1 - offsetY + 0.5f;

        float a = (argbColor >>> 24 & 255) / 255f;
        float r = (argbColor >>> 16 & 255) / 255f;
        float gC = (argbColor >>>  8 & 255) / 255f;
        float b = (argbColor         & 255) / 255f;

        // 渲染状态设置
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // 避免遗留 tint

        Matrix4f mat = g.pose().last().pose();
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buf = tess.getBuilder();

        buf.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(mat, p1x, p1y, 0.0f).color(r, gC, b, a).endVertex();
        buf.vertex(mat, p2x, p2y, 0.0f).color(r, gC, b, a).endVertex();
        buf.vertex(mat, p3x, p3y, 0.0f).color(r, gC, b, a).endVertex();

        buf.vertex(mat, p3x, p3y, 0.0f).color(r, gC, b, a).endVertex();
        buf.vertex(mat, p4x, p4y, 0.0f).color(r, gC, b, a).endVertex();
        buf.vertex(mat, p1x, p1y, 0.0f).color(r, gC, b, a).endVertex();

        tess.end();  // 注意：1.20.1 中正确的 draw 方式
        RenderSystem.disableBlend();
    }
    //绘制侧边栏
    private void renderRightPanelBg(GuiGraphics g) {
        ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/skill/bg/right_sidebar_.png");
        int IMG_W = 160, IMG_H = 320;
        int screenW = this.width;
        int screenH = this.height;
        // 右侧 1/3 面板区域
        int panelX = (screenW * 2) / 3;
        int panelW = screenW - panelX;
        int panelH = screenH;
        float scale_w = panelW / (float) IMG_W;
        float scale_h = panelH / (float) IMG_H;
        int drawW = Mth.floor(IMG_W * scale_w);
        int drawH = Mth.floor(IMG_H * scale_h);
        int drawX = panelX + (panelW - drawW) / 2;
        int drawY = (panelH - drawH) / 2;
        RenderSystem.enableBlend();
        g.blit(BG, drawX, drawY, drawW, drawH, 0, 0, IMG_W, IMG_H,  IMG_W, IMG_H);
        RenderSystem.disableBlend();
    }
    //绘制底边栏
    private void renderDownPanelBg(GuiGraphics g) {
        ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/skill/bg/down_sidebar_.png");
        int IMG_W = 512, IMG_H = 48;
        int screenW = this.width;
        int screenH = this.height;
        // 右侧 1/3 面板区域
        int panelY = (screenH * 5) / 6;
        int panelW = screenW;
        int panelH = screenH- panelY;
        float scale_w = panelW / (float) IMG_W;
        float scale_h = panelH / (float) IMG_H;
        int drawW = Mth.floor(IMG_W * scale_w);
        int drawH = Mth.floor(IMG_H * scale_h);
        int drawX = (panelW - drawW) / 2;
        int drawY = panelY + (panelH - drawH) / 2;
        RenderSystem.enableBlend();
        g.blit(BG, drawX, drawY, drawW, drawH, 0, 0, IMG_W, IMG_H,  IMG_W, IMG_H);
        RenderSystem.disableBlend();
    }
    //自动换行文字
    private int drawWrapped(GuiGraphics g, Component text, int x, int y, int maxWidth, int color, boolean shadow,int lineHeight) {
        var lines = this.font.split(text, maxWidth); // 自动按宽度换行
        int dy = 0;
        for (var seq : lines) {
            if (shadow) {
                g.drawString(this.font, seq, x, y + dy, color, true);
            } else {
                g.drawString(this.font, seq, x, y + dy, color);
            }
            dy += this.font.lineHeight; // 下一行
        }
        return lineHeight + dy + this.font.lineHeight/2;
    }
    //初始化按钮批次1
    public void addRenderableWidget1() {
        //第一批按钮初始化(底层 技能图标)
        for (ShadowImageSkillButton s:getAbsWidgets()){
            addRenderableWidget(s);
        }
    }
    public void addRenderableWidget2() {
        //第二批按钮初始化(控件)
        if (ShowSkillBar == 0) {
            addRenderableWidget(Upgrade);
        }
        addRenderableWidget(this.SkillAssemble);
        addRenderableWidget(label);
        addRenderableWidget(this.SkillReset);
    }
    //主渲染逻辑
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        //背景
        this.renderBackground(guiGraphics);
        //底背景
        guiGraphics.fill(0, 0, this.width, this.height, 0xFF282828);
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_decor/base.png"),
                (this.width-this.height)/2,0,this.height,this.height,0,0,288,288,288,288);
        //连接线部分
        RenderSystem.enableBlend();
        for (WidgetConnection connection : connections) {
            drawDiagonalLine(guiGraphics, connection.getFromCenterX(), connection.getFromCenterY(), connection.getToCenterX(), connection.getToCenterY(), 0xFF707070);
            //drawDiagonalLine(guiGraphics, connection.getFromCenterY(), connection.getFromCenterX(), connection.getToCenterY(), connection.getToCenterX(), 0xFF707070);
        }
        RenderSystem.disableBlend();
        //技能组件部分
        addRenderableWidget1();
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTicks);
            ShadowImageSkillButton get = (ShadowImageSkillButton) renderable;
            //检测前置是否被满足,若否,则绘制淡黑覆盖层
            if (get.passiveSkillAttribute.preconditionId != null){
                if (iImmortalPlayer.getSkill().getNumInfo().get(get.passiveSkillAttribute.preconditionId.skillId + 4) == 0){
                    int x = get.getX();
                    int y = get.getY();
                    int width = get.getWidth();
                    int height = get.getHeight();
                    if (width == 16) {
                        guiGraphics.fill(x, y, x + width, y + height, 0xAA000000);
                    } else if (width == 24) {
                        //guiGraphics.blitSprite(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/skill/skill_generic/awaken_skill_black_"),x, y, 24,24);
                        guiGraphics.fill(x+2, y, x + width-2, y+1, 0xAA000000);
                        guiGraphics.fill(x+1, y+1, x + width-1, y+2, 0xAA000000);
                        guiGraphics.fill(x, y+2, x + width, y + height-2, 0xAA000000);
                        guiGraphics.fill(x+1, y + height-2, x + width-1, y + height-1, 0xAA000000);
                        guiGraphics.fill(x+2, y + height-1, x + width-2, y + height, 0xAA000000);
                        //guiGraphics.fill(x+1, y+1, x + width-2, y + height-2, 0xAA000000);
                        //guiGraphics.fill(x+2, y, x + width-4, y + height, 0xAA000000);
                    }
                }
            }
        }
        this.renderables.clear();
        //技能介绍部分
        if (true) {
            //外框
            //底
            renderDownPanelBg(guiGraphics);
            //右
            renderRightPanelBg(guiGraphics);
            //信息
            if (ShownNumber != -1) {
                //图案
                guiGraphics.blit(ShowIcon,this.width*705/1000+this.width/10, this.height/25,this.width/10,this.width/10,0,0,256,256,256,256);
                //文字
                int width_for_words = this.width / 4;
                int dy1 = drawWrapped(guiGraphics, SkillName,this.width*73/100+(width_for_words-Math.min(width_for_words,this.font.width(SkillName)))/2,this.height/25+this.width*11/100,width_for_words,0xFFFFFF,true,0);
                int dy0 = 0;
                if (skillInDisplay.preconditionId != null && iImmortalPlayer.getSkill().getNumInfo().get(skillInDisplay.preconditionId.skillId + 4) == 0) {
                    Component precondComp = Component.translatable("skill.precond").append(skillInDisplay.preconditionId.skillName);
                    dy0 = drawWrapped(guiGraphics, precondComp, this.width * 73 / 100 + (width_for_words - Math.min(width_for_words, this.font.width(precondComp))) / 2, this.height / 25 + this.width * 11 / 100 + dy1, width_for_words, 0xFFFFFF, true, 0);
                    dy1 += dy0;
                } else {
                    //玩家技能等级获取
                    int val = 0;
                    try {
                        Skill s = iImmortalPlayer.getSkill();
                        Field f = s.getClass().getDeclaredField("Skill_"+this.skillInDisplay.skillId+"_Level");
                        f.setAccessible(true);
                        val = f.getInt(s);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    Component levelShown = null;
                    if (val != skillInDisplay.maxTier) {
                        levelShown = Component.translatable("skill.Level").append(String.valueOf(val)).append(Component.literal("/").append(String.valueOf(this.skillInDisplay.maxTier)));
                    } else {
                        levelShown = Component.translatable("skill.Level").append(Component.literal("Max"));
                    }
                    dy0 = drawWrapped(guiGraphics,levelShown, this.width * 73 / 100 + (width_for_words - Math.min(width_for_words, this.font.width(levelShown))) / 2, this.height / 25 + this.width * 11 / 100 + dy1, width_for_words, 0xFFFFFF, true, 0);
                    dy1 += dy0;
                }
                int dy2 = drawWrapped(guiGraphics, SkillLore,this.width*73/100+(width_for_words-Math.min(width_for_words,this.font.width(SkillLore)))/2,this.height/25+this.width*11/100+dy1,width_for_words,0xFFFFFF,true,0);
                //消耗SP和CD
                //若为主动
                if (ShowSkillBar == 0) {
                    if (skillInDisplay instanceof ActiveSkillAttribute activeSkillAttribute) {
                        //渲染CD文字获取
                        MutableComponent CD = Component.translatable("skill.CD").append(Component.literal(String.valueOf(activeSkillAttribute.skillCD[0])));
                        for (int i = 1; i < activeSkillAttribute.skillCD.length; i++) {
                            CD.append(Component.literal("→" + activeSkillAttribute.skillCD[i]));
                        }
                        //渲染SP文字获取
                        MutableComponent SP = Component.translatable("skill.SP").append(Component.literal(String.valueOf(activeSkillAttribute.skillCD[0])));
                        for (int i = 1; i < activeSkillAttribute.skillSP.length; i++) {
                            SP.append(Component.literal("→" + activeSkillAttribute.skillSP[i]));
                        }
                        int dy10 = drawWrapped(guiGraphics, CD, this.width * 73 / 100 + (width_for_words - Math.min(width_for_words, this.font.width(CD))) / 2, this.height * 72 / 100, width_for_words, 0xFFFFFF, true, 0);
                        int dy11 = drawWrapped(guiGraphics, SP, this.width * 73 / 100 + (width_for_words - Math.min(width_for_words, this.font.width(SP))) / 2, this.height * 72 / 100 + dy10, width_for_words, 0xFFFFFF, true, 0);
                    }
                    //若为被动
                    else {
                        Component passiveSP_CD_mind = Component.translatable("skill.SP_CD_NONE");
                        drawWrapped(guiGraphics, passiveSP_CD_mind, this.width * 73 / 100 + (width_for_words - Math.min(width_for_words, this.font.width(passiveSP_CD_mind))) / 2, this.height * 72 / 100, width_for_words, 0xFFFFFF, true, 0);
                    }
                }
                //升级按钮
                //判断可否升级
                //检测该技能可否被升级 满足条件:1.前置技能,2.属性点数
                //第一次升级技能消耗技能点，第二次升级技能消耗升级点
                //检测 - 前置
                int SkillPointAmount = iImmortalPlayer.getSkill().Skill_Point;
                int SkillUpgradePointAmount = iImmortalPlayer.getSkill().Skill_Upgrade_Point;
                int CurrentSkillLevel = iImmortalPlayer.getSkill().getNumInfo().get(skillInDisplay.skillId + 4);
                if (skillInDisplay.preconditionId == null) {
                    Upgrade.active = (CurrentSkillLevel == 0 && SkillPointAmount > 0) || (CurrentSkillLevel > 0 && SkillUpgradePointAmount > 0);
                } else {
                    int levelOfPreCond = iImmortalPlayer.getSkill().getNumInfo().get(skillInDisplay.preconditionId.skillId + 4);
                    if (levelOfPreCond > 0) {
                        Upgrade.active = (CurrentSkillLevel == 0 && SkillPointAmount > 0) || (CurrentSkillLevel > 0 && SkillUpgradePointAmount > 0);
                    } else {
                        Upgrade.active = false;
                    }
                }
                //最终修正:若级别已到最高,则无法再次升级
                if (CurrentSkillLevel >= skillInDisplay.maxTier) {
                    Upgrade.active = false;
                }
            }
        }
        //按钮控件部分
        addRenderableWidget2();
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTicks);
            this.Upgrade.setFocused(false);
        }
        this.renderables.clear();
        //底栏信息
        ResourceLocation skill_point = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_decor/skill_point.png");
        ResourceLocation skill_upgrade_point = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_decor/skill_upgrade_point.png");
        guiGraphics.blit(skill_point,this.width*10/100, this.height*67/72,this.height/18,this.height/18,0,0,18,18,18,18);
        guiGraphics.drawString(font, Component.literal(String.valueOf(iImmortalPlayer.getSkill().Skill_Point)), this.width*11/100+this.height/18, this.height*67/72+(this.height/18-font.lineHeight)/2, 0xFFFFFF);
        guiGraphics.blit(skill_upgrade_point,this.width*25/100, this.height*67/72,this.height/18,this.height/18,0,0,18,18,18,18);
        guiGraphics.drawString(font, Component.literal(String.valueOf(iImmortalPlayer.getSkill().Skill_Upgrade_Point)), this.width*26/100+this.height/18, this.height*67/72+(this.height/18-font.lineHeight)/2, 0xFFFFFF);
        //标题信息
        guiGraphics.drawString(font, Component.translatable("skill.title"), 10, 10, 0xFFFFFF);
        //super.render(guiGraphics, mouseX, mouseY, partialTicks);
        //技能GUI信息
        if (ShowSkillBar == 1) {
            guiGraphics.fill(this.width*705/1000, this.height*702/1000, this.width*996/1000, this.height, 0xFF383838);
            int width_gap = 54;
            int height_gap = 85;
            //绘制框内部图像
            for (SkillSlot s : slots) {
                try {
                    SkillAssemble skillAssemble = iImmortalPlayer.getSkillAssemble();
                    Field f = skillAssemble.getClass().getDeclaredField("Slot"+s.index);
                    f.setAccessible(true);
                    int i = f.getInt(skillAssemble);
                    if (i != -1) {
                        guiGraphics.blit(ModSkills.getSkillbyNum(i,player).skillIcon, s.x, s.y, this.width/25, this.width/25,0,0,256,256,256,256); // 简单示例
                    }
                    guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/sprites/icon/skill/skill_generic/_skill_bar_side_.png"),s.x, s.y,this.width/25,this.width/25,0,0,38,38,38,38);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            //悬浮幽灵图标绘制
            if (picking && pickedSprites != null) {
                RenderSystem.enableBlend();
                guiGraphics.setColor(1f, 1f, 1f, 0.85f);
                // 让图标中心对准鼠标（用 ghostDx/Dy 控制）
                ResourceLocation rl = pickedSprites;
                guiGraphics.blit(rl, mouseX - ghostDx, mouseY - ghostDy, this.width/25, this.width/25,0,0,256,256,256,256);
                guiGraphics.setColor(1,1,1,1);
                RenderSystem.disableBlend();
            }
        }
        //重置技能树 按钮刷新
        SkillReset.setFocused(false);
        //最后 子面板渲染
        if (resetModal != null) resetModal.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
    //升级技能
    private void upgradeSkillLevel(){
        //1.检测0级技能行为
        int CurrentSkillLevel = iImmortalPlayer.getSkill().getNumInfo().get(skillInDisplay.skillId + 4);
        if (CurrentSkillLevel == 0) {
            iImmortalPlayer.getSkill().Skill_Point -= 1;
            iImmortalPlayer.getSkill().Skill_Point_Used += 1;
            try {
                Skill s = iImmortalPlayer.getSkill();
                Field f = s.getClass().getDeclaredField("Skill_"+this.skillInDisplay.skillId+"_Level");
                f.setAccessible(true);
                int val = f.getInt(s);
                val += 1;
                f.setInt(s,val);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            iImmortalPlayer.getSkill().Skill_Upgrade_Point -= 1;
            iImmortalPlayer.getSkill().Skill_Upgrade_Point_Used += 1;
            try {
                Skill s = iImmortalPlayer.getSkill();
                Field f = s.getClass().getDeclaredField("Skill_"+this.skillInDisplay.skillId+"_Level");
                f.setAccessible(true);
                int val = f.getInt(s);
                val += 1;
                f.setInt(s,val);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        //玩家相关数据同步
        ModNetworking.CHANNEL.sendToServer(new SkillInfoSyncPacket(iImmortalPlayer.getSkill().getSkillCompoundTag(iImmortalPlayer.getSkill().getNumInfo())));
    }
    //装配技能-选择技能
    private void beginPick(PassiveSkillAttribute passiveSkillAttribute) {
        this.picking = true;
        this.pickedSkillId = passiveSkillAttribute.skillId;
        this.pickedSprites = passiveSkillAttribute.skillIcon;
        // 如果想让图标中心对准鼠标：
        this.ghostDx = this.width/25/2; this.ghostDy = this.width/25/2;
    }
    //装配技能-取消技能
    private void cancelPick() {
        this.picking = false;
        this.pickedSkillId = -1;
        this.pickedSprites = null;
    }
    //装配技能-尝试放置
    private boolean tryPlaceAt(double localX, double localY) {
        for (SkillSlot s : slots) {
            if (s.contains(localX, localY)) {
                //检测是否有重复单位
                int detect = 0;
                SkillAssemble skillAssemble_ = iImmortalPlayer.getSkillAssemble();
                if (skillAssemble_.Slot1 == pickedSkillId) skillAssemble_.Slot1 = -1;
                if (skillAssemble_.Slot2 == pickedSkillId) skillAssemble_.Slot2 = -1;
                if (skillAssemble_.Slot3 == pickedSkillId) skillAssemble_.Slot3 = -1;
                if (skillAssemble_.Slot4 == pickedSkillId) skillAssemble_.Slot4 = -1;
                if (skillAssemble_.Slot5 == pickedSkillId) skillAssemble_.Slot5 = -1;
                if (skillAssemble_.Slot6 == pickedSkillId) skillAssemble_.Slot6 = -1;
                if (skillAssemble_.Slot7 == pickedSkillId) skillAssemble_.Slot7 = -1;
                if (skillAssemble_.Slot8 == pickedSkillId) skillAssemble_.Slot8 = -1;
                if (skillAssemble_.Slot9 == pickedSkillId) skillAssemble_.Slot9 = -1;
                if (skillAssemble_.Slot10 == pickedSkillId) skillAssemble_.Slot10 = -1;
                //System.out.println("123");
                //绑定玩家
                if (detect == 0) {
                    try {
                        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
                        SkillAssemble skillAssemble = iImmortalPlayer.getSkillAssemble();
                        Field f = skillAssemble.getClass().getDeclaredField("Slot" + s.index);
                        f.setAccessible(true);
                        f.setInt(skillAssemble, pickedSkillId);
                        ModNetworking.CHANNEL.sendToServer(new SkillAssembleInfoSyncPacket(iImmortalPlayer.getSkillAssemble().getSkillCompoundTag(iImmortalPlayer.getSkillAssemble().getNumInfo())));
                        //System.out.println("256");
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    // TODO 同步到服务端：slotIndex + skillId
                    cancelPick();
                    return true;
                } else {
                    cancelPick();
                    return false;
                }
            }
        }
        return false;
    }
    //装配技能-槽位类注册
    static class SkillSlot {
        int x,y,w,h; Integer skillId; ResourceLocation skillIcon;int index;

        public SkillSlot(int index, int x, int y, int cellW, int cellH,int skillId,ResourceLocation skillIcon) {
            this.index = index;
            this.skillId = skillId;
            this.skillIcon = skillIcon;
            this.x = x;
            this.y = y;
            this.w = cellW;
            this.h = cellH;
        }

        boolean contains(double lx,double ly){
            return lx>=x && lx<x+w && ly>=y && ly<y+h;
        }
    }
    //装配技能-槽位设置
    private final java.util.List<SkillSlot> slots = new java.util.ArrayList<>();
    private void registerSlots() {
        slots.clear();
        int panelY = this.height*722/1000;
        int panelY_ = this.height*(722+85)/1000;
        int panelW = this.width*40/1000;
        int panelH = this.width*40/1000;

        int panelX_ = 725;
        int width_gap_ = 54;

        slots.add(new SkillSlot(1,(panelX_+width_gap_*0)*this.width/1000, panelY, panelW, panelH,-1,null));
        slots.add(new SkillSlot(2,(panelX_+width_gap_*1)*this.width/1000, panelY, panelW, panelH,-1,null));
        slots.add(new SkillSlot(3,(panelX_+width_gap_*2)*this.width/1000, panelY, panelW, panelH,-1,null));
        slots.add(new SkillSlot(4,(panelX_+width_gap_*3)*this.width/1000, panelY, panelW, panelH,-1,null));
        slots.add(new SkillSlot(5,(panelX_+width_gap_*4)*this.width/1000, panelY, panelW, panelH,-1,null));

        slots.add(new SkillSlot(6,(panelX_+width_gap_*0)*this.width/1000, panelY_, panelW, panelH,-1,null));
        slots.add(new SkillSlot(7,(panelX_+width_gap_*1)*this.width/1000, panelY_, panelW, panelH,-1,null));
        slots.add(new SkillSlot(8,(panelX_+width_gap_*2)*this.width/1000, panelY_, panelW, panelH,-1,null));
        slots.add(new SkillSlot(9,(panelX_+width_gap_*3)*this.width/1000, panelY_, panelW, panelH,-1,null));
        slots.add(new SkillSlot(10,(panelX_+width_gap_*4)*this.width/1000, panelY_, panelW, panelH,-1,null));
    }
    //重置技能-子面板类注册
    class Modal implements Renderable, GuiEventListener {
        int x,y,w,h; final List<AbstractWidget> children = new ArrayList<>();
        boolean contains(double mx,double my){return mx>=x&&mx<x+w&&my>=y&&my<y+h;}

        Modal(int x,int y,int w,int h){this.x=x;this.y=y;this.w=w;this.h=h;}
        <T extends AbstractWidget> T add(T w){children.add(w);return w;}

        public void render(GuiGraphics g, int mouseX, int mouseY, float pt){
            RenderSystem.enableBlend();
            // 遮罩
            g.fill(0,0,Minecraft.getInstance().getWindow().getGuiScaledWidth(),Minecraft.getInstance().getWindow().getGuiScaledHeight(),0xA0000000);
            g.fill(x,y,x+w,y+h,0xFF2E2E2E);
            // 面板
            g.renderOutline(x,y,w,h,0xFFFFFFFF);
            for (Renderable r:children) r.render(g,mouseX,mouseY,pt);
            RenderSystem.disableBlend();
        }
        // 拦截事件（只传给自己的 children）
        public boolean mouseClicked(double mx,double my,int b){
            if(!contains(mx,my)) return true; // 吃掉，防止点穿
            for (int i=children.size()-1;i>=0;--i)
                if (children.get(i).mouseClicked(mx,my,b)) return true;
            return true;
        }
        public boolean mouseReleased(double mx,double my,int b){
            boolean h=false; for (var c:children) h|=c.mouseReleased(mx,my,b); return h||contains(mx,my);
        }
        public boolean mouseDragged(double mx,double my,int b,double dx,double dy){
            boolean h=false; for (var c:children) h|=c.mouseDragged(mx,my,b,dx,dy); return h||contains(mx,my);
        }
        @Override
        public void setFocused(boolean p_265728_) {}
        @Override
        public boolean isFocused() {return false;}
    }
}
