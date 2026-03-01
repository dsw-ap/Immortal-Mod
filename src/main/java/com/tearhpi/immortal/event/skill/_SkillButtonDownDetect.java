package com.tearhpi.immortal.event.skill;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.skill.C2SSkillDownState;
import com.tearhpi.immortal.util.Keybinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="immortal", bus=Mod.EventBusSubscriber.Bus.FORGE, value= Dist.CLIENT)
public class _SkillButtonDownDetect {
    private static boolean lastDown = false;
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;
        Player player = mc.player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        boolean down = false;

        int skill9Lasting = iImmortalPlayer.getSkillInUse().skill9Lasting;
        if (skill9Lasting >= 0) {
            //按键搜寻
            KeyMapping key = null;
            if (iImmortalPlayer.getSkillAssemble().Slot1 == 9 || iImmortalPlayer.getSkillAssemble().Slot6 == 9) {key = Keybinds.SKILL1;}
            if (iImmortalPlayer.getSkillAssemble().Slot2 == 9 || iImmortalPlayer.getSkillAssemble().Slot7 == 9) {key = Keybinds.SKILL2;}
            if (iImmortalPlayer.getSkillAssemble().Slot3 == 9 || iImmortalPlayer.getSkillAssemble().Slot8 == 9) {key = Keybinds.SKILL3;}
            if (iImmortalPlayer.getSkillAssemble().Slot4 == 9 || iImmortalPlayer.getSkillAssemble().Slot9 == 9) {key = Keybinds.SKILL4;}
            if (iImmortalPlayer.getSkillAssemble().Slot5 == 9 || iImmortalPlayer.getSkillAssemble().Slot10 == 9) {key = Keybinds.SKILL5;}
            if (key != null) {
                down = key.isDown();
            }
        }
        //skill12
        int skill12Lasting = iImmortalPlayer.getSkillInUse().skill12Lasting;
        if (skill12Lasting >= 0) {
            KeyMapping key = null;
            if (iImmortalPlayer.getSkillAssemble().Slot1 == 12 || iImmortalPlayer.getSkillAssemble().Slot6 == 12) {key = Keybinds.SKILL1;}
            if (iImmortalPlayer.getSkillAssemble().Slot2 == 12 || iImmortalPlayer.getSkillAssemble().Slot7 == 12) {key = Keybinds.SKILL2;}
            if (iImmortalPlayer.getSkillAssemble().Slot3 == 12 || iImmortalPlayer.getSkillAssemble().Slot8 == 12) {key = Keybinds.SKILL3;}
            if (iImmortalPlayer.getSkillAssemble().Slot4 == 12 || iImmortalPlayer.getSkillAssemble().Slot9 == 12) {key = Keybinds.SKILL4;}
            if (iImmortalPlayer.getSkillAssemble().Slot5 == 12 || iImmortalPlayer.getSkillAssemble().Slot10 == 12) {key = Keybinds.SKILL5;}
            if (key != null) {
                down = key.isDown();
            }
        }
        //skill21
        int skill21Lasting = iImmortalPlayer.getSkillInUse().skill21Lasting;
        if (skill21Lasting >= 0) {
            KeyMapping key = null;
            if (iImmortalPlayer.getSkillAssemble().Slot1 == 21 || iImmortalPlayer.getSkillAssemble().Slot6 == 21) {key = Keybinds.SKILL1;}
            if (iImmortalPlayer.getSkillAssemble().Slot2 == 21 || iImmortalPlayer.getSkillAssemble().Slot7 == 21) {key = Keybinds.SKILL2;}
            if (iImmortalPlayer.getSkillAssemble().Slot3 == 21 || iImmortalPlayer.getSkillAssemble().Slot8 == 21) {key = Keybinds.SKILL3;}
            if (iImmortalPlayer.getSkillAssemble().Slot4 == 21 || iImmortalPlayer.getSkillAssemble().Slot9 == 21) {key = Keybinds.SKILL4;}
            if (iImmortalPlayer.getSkillAssemble().Slot5 == 21 || iImmortalPlayer.getSkillAssemble().Slot10 == 21) {key = Keybinds.SKILL5;}
            if (key != null) {
                down = key.isDown();
            }
        }
        //skill44
        int skill44Lasting = iImmortalPlayer.getSkillInUse().skill44Lasting;
        if (skill44Lasting >= 0) {
            KeyMapping key = null;
            if (iImmortalPlayer.getSkillAssemble().Slot1 == 44 || iImmortalPlayer.getSkillAssemble().Slot6 == 44) {key = Keybinds.SKILL1;}
            if (iImmortalPlayer.getSkillAssemble().Slot2 == 44 || iImmortalPlayer.getSkillAssemble().Slot7 == 44) {key = Keybinds.SKILL2;}
            if (iImmortalPlayer.getSkillAssemble().Slot3 == 44 || iImmortalPlayer.getSkillAssemble().Slot8 == 44) {key = Keybinds.SKILL3;}
            if (iImmortalPlayer.getSkillAssemble().Slot4 == 44 || iImmortalPlayer.getSkillAssemble().Slot9 == 44) {key = Keybinds.SKILL4;}
            if (iImmortalPlayer.getSkillAssemble().Slot5 == 44 || iImmortalPlayer.getSkillAssemble().Slot10 == 44) {key = Keybinds.SKILL5;}
            if (key != null) {
                down = key.isDown();
            }
        }
        //skill68
        int skill68Lasting = iImmortalPlayer.getSkillInUse().skill68Lasting;
        if (skill68Lasting >= 0) {
            KeyMapping key = null;
            if (iImmortalPlayer.getSkillAssemble().Slot1 == 68 || iImmortalPlayer.getSkillAssemble().Slot6 == 68) {key = Keybinds.SKILL1;}
            if (iImmortalPlayer.getSkillAssemble().Slot2 == 68 || iImmortalPlayer.getSkillAssemble().Slot7 == 68) {key = Keybinds.SKILL2;}
            if (iImmortalPlayer.getSkillAssemble().Slot3 == 68 || iImmortalPlayer.getSkillAssemble().Slot8 == 68) {key = Keybinds.SKILL3;}
            if (iImmortalPlayer.getSkillAssemble().Slot4 == 68 || iImmortalPlayer.getSkillAssemble().Slot9 == 68) {key = Keybinds.SKILL4;}
            if (iImmortalPlayer.getSkillAssemble().Slot5 == 68 || iImmortalPlayer.getSkillAssemble().Slot10 == 68) {key = Keybinds.SKILL5;}
            if (key != null) {
                down = key.isDown();
            }
        }

        //System.out.println(down);
        ModNetworking.CHANNEL.sendToServer(new C2SSkillDownState(down));
    }
}
