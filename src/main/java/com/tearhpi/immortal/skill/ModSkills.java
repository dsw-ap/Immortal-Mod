package com.tearhpi.immortal.skill;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.capability.Skill;
import com.tearhpi.immortal.entity.capability.SkillCD;
import com.tearhpi.immortal.networking.*;
import com.tearhpi.immortal.skill.behaviour.active.fire.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class ModSkills {
    public static final PassiveSkillAttribute Skill_4 = new PassiveSkillAttribute(4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_4.png"),Component.translatable("skill_4.name"),Component.translatable("skill_4.intro"),Component.translatable("skill_4.info"),1);
    public static final PassiveSkillAttribute Skill_0 = new PassiveSkillAttribute(0,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_0.png"),Component.translatable("skill_0.name"),Component.translatable("skill_0.intro"),Component.translatable("skill_0.info"),5);
    public static final PassiveSkillAttribute Skill_1 = new PassiveSkillAttribute(1,ModSkills.Skill_0,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_1.png"),Component.translatable("skill_1.name"),Component.translatable("skill_1.intro"),Component.translatable("skill_1.info"),5);
    public static final PassiveSkillAttribute Skill_2 = new PassiveSkillAttribute(2,ModSkills.Skill_0,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_2.png"),Component.translatable("skill_2.name"),Component.translatable("skill_2.intro"),Component.translatable("skill_2.info"),5);
    public static final PassiveSkillAttribute Skill_3 = new PassiveSkillAttribute(3,ModSkills.Skill_0,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_3.png"),Component.translatable("skill_3.name"),Component.translatable("skill_3.intro"),Component.translatable("skill_3.info"),5);
    //火系0-10 对应5-15
    public static final PassiveSkillAttribute Skill_5 = new PassiveSkillAttribute(5,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_5.png"),Component.translatable("skill_5.name"),Component.translatable("skill_5.intro"),Component.translatable("skill_5.info"),1);
    public static final ActiveSkillAttribute Skill_6 = new ActiveSkillAttribute(6,ModSkills.Skill_5,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_6.png"),Component.translatable("skill_6.name"),Component.translatable("skill_6.intro"),Component.translatable("skill_6.info"),3, new int[]{12, 11, 10}, new int[]{5, 5, 5});
    public static final ActiveSkillAttribute Skill_7 = new ActiveSkillAttribute(7,ModSkills.Skill_6,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_7.png"),Component.translatable("skill_7.name"),Component.translatable("skill_7.intro"),Component.translatable("skill_7.info"),3, new int[]{25, 23, 21}, new int[]{8, 8, 8});
    public static final ActiveSkillAttribute Skill_8 = new ActiveSkillAttribute(8,ModSkills.Skill_7,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_8.png"),Component.translatable("skill_8.name"),Component.translatable("skill_8.intro"),Component.translatable("skill_8.info"),3, new int[]{25, 24, 24}, new int[]{10, 10, 10});
    public static final ActiveSkillAttribute Skill_9 = new ActiveSkillAttribute(9,ModSkills.Skill_8,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_9.png"),Component.translatable("skill_9.name"),Component.translatable("skill_9.intro"),Component.translatable("skill_9.info"),3, new int[]{30, 28, 26}, new int[]{12, 12, 12});
    public static final ActiveSkillAttribute Skill_10 = new ActiveSkillAttribute(10,ModSkills.Skill_9,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_10.png"),Component.translatable("skill_10.name"),Component.translatable("skill_10.intro"),Component.translatable("skill_10.info"),3, new int[]{30, 28, 26}, new int[]{12, 12, 12});
    public static final ActiveSkillAttribute Skill_11 = new ActiveSkillAttribute(11,ModSkills.Skill_10,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_11.png"),Component.translatable("skill_11.name"),Component.translatable("skill_11.intro"),Component.translatable("skill_11.info"),1, new int[]{100}, new int[]{30});
    public static final ActiveSkillAttribute Skill_12 = new ActiveSkillAttribute(12,ModSkills.Skill_11,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_12.png"),Component.translatable("skill_12.name"),Component.translatable("skill_12.intro"),Component.translatable("skill_12.info"),3, new int[]{10,10,10}, new int[]{0,0,0});
    public static final ActiveSkillAttribute Skill_13 = new ActiveSkillAttribute(13,ModSkills.Skill_12,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_13.png"),Component.translatable("skill_13.name"),Component.translatable("skill_13.intro"),Component.translatable("skill_13.info"),3, new int[]{15,13,11}, new int[]{15,15,15});
    public static final PassiveSkillAttribute Skill_14 = new PassiveSkillAttribute(14,ModSkills.Skill_13,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_14.png"),Component.translatable("skill_14.name"),Component.translatable("skill_14.intro"),Component.translatable("skill_14.info"),5);
    public static final ActiveSkillAttribute Skill_15 = new ActiveSkillAttribute(15, ModSkills.Skill_13, ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_15.png"), Component.translatable("skill_15.name"), Component.translatable("skill_15.intro"), Component.translatable("skill_15.info"), 1, new int[]{150}, new int[]{40});
    //水系0-10 对应16-26
    public static final PassiveSkillAttribute Skill_16 = new PassiveSkillAttribute(16,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_16.png"),Component.translatable("skill_16.name"),Component.translatable("skill_16.intro"),Component.translatable("skill_16.info"),1);
    public static final ActiveSkillAttribute Skill_17 = new ActiveSkillAttribute(17,ModSkills.Skill_16,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_17.png"),Component.translatable("skill_17.name"),Component.translatable("skill_17.intro"),Component.translatable("skill_17.info"),3, new int[]{12,11,10}, new int[]{5,5,5});
    public static final ActiveSkillAttribute Skill_18 = new ActiveSkillAttribute(18,ModSkills.Skill_17,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_18.png"),Component.translatable("skill_18.name"),Component.translatable("skill_18.intro"),Component.translatable("skill_18.info"),3, new int[]{1,1,1}, new int[]{0,0,0});
    public static final ActiveSkillAttribute Skill_19 = new ActiveSkillAttribute(19,ModSkills.Skill_18,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_19.png"),Component.translatable("skill_19.name"),Component.translatable("skill_19.intro"),Component.translatable("skill_19.info"),3, new int[]{20,19,18}, new int[]{10,10,10});
    public static final ActiveSkillAttribute Skill_20 = new ActiveSkillAttribute(20,ModSkills.Skill_19,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_20.png"),Component.translatable("skill_20.name"),Component.translatable("skill_20.intro"),Component.translatable("skill_20.info"),3, new int[]{30,28,26}, new int[]{20,20,20});
    public static final ActiveSkillAttribute Skill_21 = new ActiveSkillAttribute(21,ModSkills.Skill_20,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_21.png"),Component.translatable("skill_21.name"),Component.translatable("skill_21.intro"),Component.translatable("skill_21.info"),3, new int[]{25,24,23}, new int[]{18,18,18});
    public static final ActiveSkillAttribute Skill_22 = new ActiveSkillAttribute(22,ModSkills.Skill_21,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_22.png"),Component.translatable("skill_22.name"),Component.translatable("skill_22.intro"),Component.translatable("skill_22.info"),1, new int[]{130}, new int[]{30});
    public static final ActiveSkillAttribute Skill_23 = new ActiveSkillAttribute(23,ModSkills.Skill_22,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_23.png"),Component.translatable("skill_23.name"),Component.translatable("skill_23.intro"),Component.translatable("skill_23.info"),3, new int[]{35,33,31}, new int[]{25});
    public static final ActiveSkillAttribute Skill_24 = new ActiveSkillAttribute(24,ModSkills.Skill_23,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_24.png"),Component.translatable("skill_24.name"),Component.translatable("skill_24.intro"),Component.translatable("skill_24.info"),3, new int[]{15,13,11}, new int[]{10,10,10});
    public static final PassiveSkillAttribute Skill_25 = new PassiveSkillAttribute(25,ModSkills.Skill_24,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_25.png"),Component.translatable("skill_25.name"),Component.translatable("skill_25.intro"),Component.translatable("skill_25.info"),5);
    public static final ActiveSkillAttribute Skill_26 = new ActiveSkillAttribute(26,ModSkills.Skill_24,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_26.png"),Component.translatable("skill_26.name"),Component.translatable("skill_26.intro"),Component.translatable("skill_26.info"),1, new int[]{150}, new int[]{40});
    //风系0-10 对应27-37
    public static final PassiveSkillAttribute Skill_27 = new PassiveSkillAttribute(27,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_27.png"),Component.translatable("skill_27.name"),Component.translatable("skill_27.intro"),Component.translatable("skill_27.info"),1);
    public static final ActiveSkillAttribute Skill_28 = new ActiveSkillAttribute(28,ModSkills.Skill_27,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_28.png"),Component.translatable("skill_28.name"),Component.translatable("skill_28.intro"),Component.translatable("skill_28.info"),3, new int[]{6,5,4}, new int[]{3,3,3});
    public static final ActiveSkillAttribute Skill_29 = new ActiveSkillAttribute(29,ModSkills.Skill_28,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_29.png"),Component.translatable("skill_29.name"),Component.translatable("skill_29.intro"),Component.translatable("skill_29.info"),3, new int[]{11,10,9}, new int[]{5,5,5});
    public static final ActiveSkillAttribute Skill_30 = new ActiveSkillAttribute(30,ModSkills.Skill_29,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_30.png"),Component.translatable("skill_30.name"),Component.translatable("skill_30.intro"),Component.translatable("skill_30.info"),3, new int[]{30,28,26}, new int[]{10,10,10});
    public static final ActiveSkillAttribute Skill_31 = new ActiveSkillAttribute(31,ModSkills.Skill_30,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_31.png"),Component.translatable("skill_31.name"),Component.translatable("skill_31.intro"),Component.translatable("skill_31.info"),3, new int[]{20,19,18}, new int[]{10,10,10});
    public static final ActiveSkillAttribute Skill_32 = new ActiveSkillAttribute(32,ModSkills.Skill_31,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_32.png"),Component.translatable("skill_32.name"),Component.translatable("skill_32.intro"),Component.translatable("skill_32.info"),3, new int[]{25,23,21}, new int[]{10,10,10});
    public static final ActiveSkillAttribute Skill_33 = new ActiveSkillAttribute(33,ModSkills.Skill_32,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_33.png"),Component.translatable("skill_33.name"),Component.translatable("skill_33.intro"),Component.translatable("skill_33.info"),1, new int[]{120}, new int[]{50});
    public static final ActiveSkillAttribute Skill_34 = new ActiveSkillAttribute(34,ModSkills.Skill_33,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_34.png"),Component.translatable("skill_34.name"),Component.translatable("skill_34.intro"),Component.translatable("skill_34.info"),3, new int[]{45,43,41}, new int[]{25,25,25});
    public static final ActiveSkillAttribute Skill_35 = new ActiveSkillAttribute(35,ModSkills.Skill_34,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_35.png"),Component.translatable("skill_35.name"),Component.translatable("skill_35.intro"),Component.translatable("skill_35.info"),3, new int[]{10,9,8}, new int[]{10,10,10});
    public static final PassiveSkillAttribute Skill_36 = new PassiveSkillAttribute(36,ModSkills.Skill_35,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_36.png"),Component.translatable("skill_36.name"),Component.translatable("skill_36.intro"),Component.translatable("skill_36.info"),5);
    public static final ActiveSkillAttribute Skill_37 = new ActiveSkillAttribute(37,ModSkills.Skill_35,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_37.png"),Component.translatable("skill_37.name"),Component.translatable("skill_37.intro"),Component.translatable("skill_37.info"),1, new int[]{200}, new int[]{60});
    //土系0-10 对应38-48
    public static final PassiveSkillAttribute Skill_38 = new PassiveSkillAttribute(38,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_38.png"),Component.translatable("skill_38.name"),Component.translatable("skill_38.intro"),Component.translatable("skill_38.info"),1);
    public static final ActiveSkillAttribute Skill_39 = new ActiveSkillAttribute(39,ModSkills.Skill_38,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_39.png"),Component.translatable("skill_39.name"),Component.translatable("skill_39.intro"),Component.translatable("skill_39.info"),3, new int[]{6,5,4}, new int[]{4,4,4});
    public static final ActiveSkillAttribute Skill_40 = new ActiveSkillAttribute(40,ModSkills.Skill_39,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_40.png"),Component.translatable("skill_40.name"),Component.translatable("skill_40.intro"),Component.translatable("skill_40.info"),3, new int[]{25,24,23}, new int[]{10,10,10});
    public static final ActiveSkillAttribute Skill_41 = new ActiveSkillAttribute(41,ModSkills.Skill_40,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_41.png"),Component.translatable("skill_41.name"),Component.translatable("skill_41.intro"),Component.translatable("skill_41.info"),3, new int[]{15,14,13}, new int[]{8,8,8});
    public static final ActiveSkillAttribute Skill_42 = new ActiveSkillAttribute(42,ModSkills.Skill_41,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_42.png"),Component.translatable("skill_42.name"),Component.translatable("skill_42.intro"),Component.translatable("skill_42.info"),3, new int[]{20,19,18}, new int[]{15,15,15});
    public static final ActiveSkillAttribute Skill_43 = new ActiveSkillAttribute(43,ModSkills.Skill_42,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_43.png"),Component.translatable("skill_43.name"),Component.translatable("skill_43.intro"),Component.translatable("skill_43.info"),3, new int[]{25,24,23}, new int[]{18,18,18});
    public static final ActiveSkillAttribute Skill_44 = new ActiveSkillAttribute(44,ModSkills.Skill_43,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_44.png"),Component.translatable("skill_44.name"),Component.translatable("skill_44.intro"),Component.translatable("skill_44.info"),1, new int[]{120}, new int[]{50});
    public static final ActiveSkillAttribute Skill_45 = new ActiveSkillAttribute(45,ModSkills.Skill_44,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_45.png"),Component.translatable("skill_45.name"),Component.translatable("skill_45.intro"),Component.translatable("skill_45.info"),3, new int[]{50,45,40}, new int[]{40,40,40});
    public static final ActiveSkillAttribute Skill_46 = new ActiveSkillAttribute(46,ModSkills.Skill_45,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_46.png"),Component.translatable("skill_46.name"),Component.translatable("skill_46.intro"),Component.translatable("skill_46.info"),3, new int[]{25,23,21}, new int[]{20,20,20});
    public static final PassiveSkillAttribute Skill_47 = new PassiveSkillAttribute(47,ModSkills.Skill_46,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_47.png"),Component.translatable("skill_47.name"),Component.translatable("skill_47.intro"),Component.translatable("skill_47.info"),5);
    public static final ActiveSkillAttribute Skill_48 = new ActiveSkillAttribute(48,ModSkills.Skill_46,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_48.png"),Component.translatable("skill_48.name"),Component.translatable("skill_48.intro"),Component.translatable("skill_48.info"),1, new int[]{200}, new int[]{60});
    //光系0-10 对应49-59
    public static final PassiveSkillAttribute Skill_49 = new PassiveSkillAttribute(49,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_49.png"),Component.translatable("skill_49.name"),Component.translatable("skill_49.intro"),Component.translatable("skill_49.info"),1);
    public static final ActiveSkillAttribute Skill_50 = new ActiveSkillAttribute(50,ModSkills.Skill_49,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_50.png"),Component.translatable("skill_50.name"),Component.translatable("skill_50.intro"),Component.translatable("skill_50.info"),3, new int[]{10,9,8}, new int[]{4,4,4});
    public static final ActiveSkillAttribute Skill_51 = new ActiveSkillAttribute(51,ModSkills.Skill_50,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_51.png"),Component.translatable("skill_51.name"),Component.translatable("skill_51.intro"),Component.translatable("skill_51.info"),3, new int[]{1,1,1}, new int[]{50,50,50});
    public static final ActiveSkillAttribute Skill_52 = new ActiveSkillAttribute(52,ModSkills.Skill_51,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_52.png"),Component.translatable("skill_52.name"),Component.translatable("skill_52.intro"),Component.translatable("skill_52.info"),3, new int[]{30,28,26}, new int[]{10,10,10});
    public static final ActiveSkillAttribute Skill_53 = new ActiveSkillAttribute(53,ModSkills.Skill_52,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_53.png"),Component.translatable("skill_53.name"),Component.translatable("skill_53.intro"),Component.translatable("skill_53.info"),3, new int[]{20,18,16}, new int[]{20,20,20});
    public static final ActiveSkillAttribute Skill_54 = new ActiveSkillAttribute(54,ModSkills.Skill_53,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_54.png"),Component.translatable("skill_54.name"),Component.translatable("skill_54.intro"),Component.translatable("skill_54.info"),3, new int[]{40,38,36}, new int[]{25,25,25});
    public static final ActiveSkillAttribute Skill_55 = new ActiveSkillAttribute(55,ModSkills.Skill_54,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_55.png"),Component.translatable("skill_55.name"),Component.translatable("skill_55.intro"),Component.translatable("skill_55.info"),1, new int[]{140}, new int[]{50});
    public static final ActiveSkillAttribute Skill_56 = new ActiveSkillAttribute(56,ModSkills.Skill_55,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_56.png"),Component.translatable("skill_56.name"),Component.translatable("skill_56.intro"),Component.translatable("skill_56.info"),3, new int[]{30,28,26}, new int[]{25,25,25});
    public static final ActiveSkillAttribute Skill_57 = new ActiveSkillAttribute(57,ModSkills.Skill_56,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_57.png"),Component.translatable("skill_57.name"),Component.translatable("skill_57.intro"),Component.translatable("skill_57.info"),3, new int[]{40,38,36}, new int[]{30,30,30});
    public static final PassiveSkillAttribute Skill_58 = new PassiveSkillAttribute(58,ModSkills.Skill_57,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_58.png"),Component.translatable("skill_58.name"),Component.translatable("skill_58.intro"),Component.translatable("skill_58.info"),5);
    public static final ActiveSkillAttribute Skill_59 = new ActiveSkillAttribute(59,ModSkills.Skill_57,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_59.png"),Component.translatable("skill_59.name"),Component.translatable("skill_59.intro"),Component.translatable("skill_59.info"),2, new int[]{300,300}, new int[]{90,90});
    //暗系0-10 对应60-70
    public static final PassiveSkillAttribute Skill_60 = new PassiveSkillAttribute(60,ModSkills.Skill_4,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_60.png"),Component.translatable("skill_60.name"),Component.translatable("skill_60.intro"),Component.translatable("skill_60.info"),1);
    public static final ActiveSkillAttribute Skill_61 = new ActiveSkillAttribute(61,ModSkills.Skill_60,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_61.png"),Component.translatable("skill_61.name"),Component.translatable("skill_61.intro"),Component.translatable("skill_61.info"),3, new int[]{10,9,8}, new int[]{4,4,4});
    public static final ActiveSkillAttribute Skill_62 = new ActiveSkillAttribute(62,ModSkills.Skill_61,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_62.png"),Component.translatable("skill_62.name"),Component.translatable("skill_62.intro"),Component.translatable("skill_62.info"),3, new int[]{20,19,18}, new int[]{60,60,60});
    public static final ActiveSkillAttribute Skill_63 = new ActiveSkillAttribute(63,ModSkills.Skill_62,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_63.png"),Component.translatable("skill_63.name"),Component.translatable("skill_63.intro"),Component.translatable("skill_63.info"),3, new int[]{25,24,23}, new int[]{15,15,15});
    public static final ActiveSkillAttribute Skill_64 = new ActiveSkillAttribute(64,ModSkills.Skill_63,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_64.png"),Component.translatable("skill_64.name"),Component.translatable("skill_64.intro"),Component.translatable("skill_64.info"),3, new int[]{60,55,50}, new int[]{40,40,40});
    public static final ActiveSkillAttribute Skill_65 = new ActiveSkillAttribute(65,ModSkills.Skill_64,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_65.png"),Component.translatable("skill_65.name"),Component.translatable("skill_65.intro"),Component.translatable("skill_65.info"),3, new int[]{40,35,30}, new int[]{20,20,20});
    public static final ActiveSkillAttribute Skill_66 = new ActiveSkillAttribute(66,ModSkills.Skill_65,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_66.png"),Component.translatable("skill_66.name"),Component.translatable("skill_66.intro"),Component.translatable("skill_66.info"),1, new int[]{150}, new int[]{70});
    public static final PassiveSkillAttribute Skill_67 = new PassiveSkillAttribute(67,ModSkills.Skill_66,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_67.png"),Component.translatable("skill_67.name"),Component.translatable("skill_67.intro"),Component.translatable("skill_67.info"),3);
    public static final ActiveSkillAttribute Skill_68 = new ActiveSkillAttribute(68,ModSkills.Skill_66,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_68.png"),Component.translatable("skill_68.name"),Component.translatable("skill_68.intro"),Component.translatable("skill_68.info"),3, new int[]{20,18,16}, new int[]{20,20,20});
    public static final ActiveSkillAttribute Skill_69 = new ActiveSkillAttribute(69,ModSkills.Skill_68,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_69.png"),Component.translatable("skill_69.name"),Component.translatable("skill_69.intro"),Component.translatable("skill_69.info"),3, new int[]{30,28,26}, new int[]{25,25,25});
    public static final ActiveSkillAttribute Skill_70 = new ActiveSkillAttribute(70,ModSkills.Skill_69,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/skill/skill_generic/skill_70.png"),Component.translatable("skill_70.name"),Component.translatable("skill_70.intro"),Component.translatable("skill_70.info"),1, new int[]{250}, new int[]{70});

    //根据数字 得到对应的static Skill
    @Nullable
    public static <T extends PassiveSkillAttribute> T getSkillbyNum(int num,Player player){
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            Class<ModSkills> c = ModSkills.class;
            Field f = c.getDeclaredField("Skill_"+num);
            f.setAccessible(true);
            return (T) f.get(ModSkills.class);
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
    //根据数字 得到玩家的技能等级
    @Nullable
    public static int getLevelbyNumAndPlayer(int num,Player player){
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            Skill s = iImmortalPlayer.getSkill();
            Field f = s.getClass().getDeclaredField("Skill_"+num+"_Level");
            f.setAccessible(true);
            return f.getInt(s);
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
    //转换玩家active技能
    public static void changeActiveSkill(Player player){
        //客户端运行该函数
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        ((IImmortalPlayer) player).getSkillAssemble().UseSkill = 1-((IImmortalPlayer) player).getSkillAssemble().UseSkill;
        ModNetworking.CHANNEL.sendToServer(new SkillAssembleInfoSyncPacket(iImmortalPlayer.getSkillAssemble().getSkillCompoundTag(iImmortalPlayer.getSkillAssemble().getNumInfo())));
    }
    //输入数字 得到玩家的相关冷却
    @Nullable
    public static int getCDbyNumAndPlayer(int num,Player player){
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            SkillCD s = iImmortalPlayer.getSkillCD();
            Field f = s.getClass().getDeclaredField("Skill"+num+"_CD");
            f.setAccessible(true);
            return f.getInt(s);
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            return 0;
        }
    }
    //重设CD
    @Nullable
    public static void setCDbyNumAndPlayer(int num,Player player){
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            SkillCD s = iImmortalPlayer.getSkillCD();
            Field f = s.getClass().getDeclaredField("Skill"+num+"_CD");
            f.setAccessible(true);
            int i = (int) Math.ceil(((ActiveSkillAttribute) ModSkills.getSkillbyNum(num,player)).skillCD[Math.max(getLevelbyNumAndPlayer(num,player)-1,0)] * 20 * (1 - player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.getHolder().get())));
            f.setInt(s,i);
            if (player.level().isClientSide()) {
                ModNetworking.CHANNEL.sendToServer(new SkillCDInfoSyncPacket(iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo())));
            } else {
                //服务端-客户端
                CompoundTag confirmed = iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo());
                ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) player)),new SkillCDInfoSyncToClientPacket(confirmed));
            }
        } catch (Exception e) {
            System.out.println("ERROR");
            throw new RuntimeException(e);
        } /*catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {

        }*/
    }
    @Nullable
    public static void setCDbyNumValueAndPlayer(int num,int value,Player player){
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            SkillCD s = iImmortalPlayer.getSkillCD();
            Field f = s.getClass().getDeclaredField("Skill"+num+"_CD");
            f.setAccessible(true);
            f.setInt(s,value);
            if (player.level().isClientSide()) {
                ModNetworking.CHANNEL.sendToServer(new SkillCDInfoSyncPacket(iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo())));
            } else {
                //服务端-客户端
                CompoundTag confirmed = iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo());
                ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) player)),new SkillCDInfoSyncToClientPacket(confirmed));
            }
        } catch (Exception e) {
            System.out.println("ERROR");
            throw new RuntimeException(e);
        } /*catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {

        }*/
    }
    //消耗法力值
    public static void consumeSP(int num,Player player){
        //null
    }
    //设置吟唱时间
    public static void setCharmingTime(int numOfSkill,int time,Player player){
        //参数:技能号,吟唱时间,玩家
        //吟唱时间作为传入参数固定
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getSkillInUse().IsInUseSkill = time;
        iImmortalPlayer.getSkillInUse().IsInUseSkillNumber = numOfSkill;
        if (player.level().isClientSide()) {
            ModNetworking.CHANNEL.sendToServer(new SkillInUseSyncPacket(iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo())));
        } else {
            //CompoundTag confirmed = iImmortalPlayer.getSkillCD().getSkillCompoundTag(iImmortalPlayer.getSkillCD().getNumInfo());
            CompoundTag confirmed = iImmortalPlayer.getSkillInUse().getSkillCompoundTag(iImmortalPlayer.getSkillInUse().getNumInfo());
            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer) player)),new SkillInUseSyncToClientPacket(confirmed));
        }

    }
    //输入一个ModSkill和Player,检索释放的技能
    public static void distributive(ActiveSkillAttribute activeSkillAttribute,Player player) {
        if (activeSkillAttribute == ModSkills.Skill_6) Skill6.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_7) Skill7.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_8) Skill8.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_9) Skill9.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_10) Skill10.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_11) Skill11.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_12) Skill12.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_13) Skill13.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_15) Skill15.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_17) Skill17.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_18) Skill18.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_19) Skill19.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_20) Skill20.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_21) Skill21.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_22) Skill22.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_23) Skill23.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_24) Skill24.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_26) Skill26.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_28) Skill28.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_29) Skill29.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_30) Skill30.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_31) Skill31.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_32) Skill32.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_33) Skill33.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_34) Skill34.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_35) Skill35.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_37) Skill37.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_39) Skill39.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_40) Skill40.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_41) Skill41.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_42) Skill42.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_43) Skill43.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_44) Skill44.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_45) Skill45.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_46) Skill46.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_48) Skill48.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_50) Skill50.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_51) Skill51.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_52) Skill52.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_53) Skill53.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_54) Skill54.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_55) Skill55.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_56) Skill56.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_57) Skill57.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_59) Skill59.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_61) Skill61.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_62) Skill62.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_63) Skill63.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_64) Skill64.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_65) Skill65.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_66) Skill66.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_68) Skill68.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_69) Skill69.Main(player);
        if (activeSkillAttribute == ModSkills.Skill_70) Skill70.Main(player);
    }
}