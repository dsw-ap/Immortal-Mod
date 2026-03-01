package com.tearhpi.immortal.event;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.Skill18_A_Packet;
import com.tearhpi.immortal.networking.Skill18_D_Packet;
import com.tearhpi.immortal.networking.Skill18_W_Packet;
import com.tearhpi.immortal.screen._plot.Plot;
import com.tearhpi.immortal.screen._setting.SettingScreen;
import com.tearhpi.immortal.screen._skill.SkillScreen;
import com.tearhpi.immortal.skill.ActiveSkillAttribute;
import com.tearhpi.immortal.skill.ModSkills;
import com.tearhpi.immortal.skill.behaviour.active.fire.Skill18;
import com.tearhpi.immortal.util.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

public class KeyBind {
    @Mod.EventBusSubscriber(modid = Immortal.MODID,value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (Minecraft.getInstance().player != null) {
                Minecraft mc = Minecraft.getInstance();
                Player player = mc.player;
                //技能面板
                if (Keybinds.OPEN_SKILLGUI_KEY.consumeClick()) {
                    Minecraft.getInstance().setScreen(new SkillScreen(Component.empty()));
                }
                //设置面板
                if (Keybinds.OPEN_SETTING_KEY.consumeClick()) {
                    Minecraft.getInstance().setScreen(new SettingScreen());
                }
                //剧情面板
                if (Keybinds.OPEN_PLOT_KEY.consumeClick()) {
                    Minecraft.getInstance().setScreen(new Plot());
                }
                //释放技能
                if (Keybinds.SKILL1.consumeClick()) {
                    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) Minecraft.getInstance().player;
                    int i = iImmortalPlayer.getSkillAssemble().UseSkill == 0 ? iImmortalPlayer.getSkillAssemble().Slot1 : iImmortalPlayer.getSkillAssemble().Slot6;
                    if (i != -1 && ModSkills.getSkillbyNum(i,player) instanceof ActiveSkillAttribute activeSkillAttribute) {
                        if (iImmortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                            ModSkills.distributive(activeSkillAttribute,player);
                        }
                    }
                }
                if (Keybinds.SKILL2.consumeClick()) {
                    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) Minecraft.getInstance().player;
                    int i = iImmortalPlayer.getSkillAssemble().UseSkill == 0 ? iImmortalPlayer.getSkillAssemble().Slot2 : iImmortalPlayer.getSkillAssemble().Slot7;
                    if (i != -1 && ModSkills.getSkillbyNum(i,player) instanceof ActiveSkillAttribute activeSkillAttribute) {
                        if (iImmortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                            ModSkills.distributive(activeSkillAttribute,player);
                        }
                    }
                }
                if (Keybinds.SKILL3.consumeClick()) {
                    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) Minecraft.getInstance().player;
                    int i = iImmortalPlayer.getSkillAssemble().UseSkill == 0 ? iImmortalPlayer.getSkillAssemble().Slot3 : iImmortalPlayer.getSkillAssemble().Slot8;
                    if (i != -1 && ModSkills.getSkillbyNum(i,player) instanceof ActiveSkillAttribute activeSkillAttribute) {
                        if (iImmortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                            ModSkills.distributive(activeSkillAttribute,player);
                        }
                    }
                }
                if (Keybinds.SKILL4.consumeClick()) {
                    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) Minecraft.getInstance().player;
                    int i = iImmortalPlayer.getSkillAssemble().UseSkill == 0 ? iImmortalPlayer.getSkillAssemble().Slot4 : iImmortalPlayer.getSkillAssemble().Slot9;
                    if (i != -1 && ModSkills.getSkillbyNum(i,player) instanceof ActiveSkillAttribute activeSkillAttribute) {
                        if (iImmortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                            ModSkills.distributive(activeSkillAttribute,player);
                        }
                    }
                }
                if (Keybinds.SKILL5.consumeClick()) {
                    IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) Minecraft.getInstance().player;
                    int i = iImmortalPlayer.getSkillAssemble().UseSkill == 0 ? iImmortalPlayer.getSkillAssemble().Slot5 : iImmortalPlayer.getSkillAssemble().Slot10;
                    if (i != -1 && ModSkills.getSkillbyNum(i,player) instanceof ActiveSkillAttribute activeSkillAttribute) {
                        if (iImmortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                            ModSkills.distributive(activeSkillAttribute,player);
                        }
                    }
                }
                //技能切换
                if (Keybinds.CHANGE_SKILL.consumeClick()) {
                    ModSkills.changeActiveSkill(Minecraft.getInstance().player);
                }
                //技能18 a/w/d释放
                IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
                if ( iImmortalPlayer.getSkillInUse().IsInUseSkillNumber == 18 && iImmortalPlayer.getSkillInUse().IsInUseSkill > 0){
                    if (mc.options.keyUp.isDown())  {
                        ModNetworking.CHANNEL.sendToServer(new Skill18_W_Packet());
                    }
                    if (mc.options.keyLeft.isDown()) {
                        ModNetworking.CHANNEL.sendToServer(new Skill18_A_Packet());
                    }
                    if (mc.options.keyRight.isDown()) {
                        ModNetworking.CHANNEL.sendToServer(new Skill18_D_Packet());
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(Keybinds.OPEN_SKILLGUI_KEY);
            event.register(Keybinds.SKILL1);
            event.register(Keybinds.SKILL2);
            event.register(Keybinds.SKILL3);
            event.register(Keybinds.SKILL4);
            event.register(Keybinds.SKILL5);
            event.register(Keybinds.CHANGE_SKILL);
        }
    }
}
