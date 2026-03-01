package com.tearhpi.immortal.chatbox;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.capability.SkillCD;
import com.tearhpi.immortal.networking.MainTaskSyncPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxRegister {
    //对话框格式
    /*ChatBox_0 = [
    {角色,话语},
    {角色,话语},
    ......
    ]
     */
    //Player代表玩家,"Etathion"代表其他任意角色
    //对话0:测试对话
    public ArrayList<?> ChatBox_0 = new ArrayList<>(List.of(
            new ArrayList<>(List.of("Player", "ChatBox_0_Line1")),
            new ArrayList<>(List.of("chatbox_character_etathion_test", "ChatBox_0_Line2")),
            new ArrayList<>(List.of("Player", "ChatBox_0_Line3")),
            new ArrayList<>(List.of("chatbox_character_etathion_test", "ChatBox_0_Line4")),
            new ArrayList<>(List.of("None", "ChatBox_0_Line5")),
            new ArrayList<>(List.of("chatbox_character_etathion_test", "ChatBox_0_Line6"))));
    //对话1:第一章-关卡开始前
    public ArrayList<?> ChatBox_1 = new ArrayList<>(List.of(
            new ArrayList<>(List.of("None", "ChatBox_1_Line1")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line2")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line3")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line4")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line5")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line6")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line7")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line8")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line9")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line10")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line11")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line12")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line13")),
            new ArrayList<>(List.of("chatbox_character_innermost_being", "ChatBox_1_Line14")),
            new ArrayList<>(List.of("None", "ChatBox_1_Line15")),
            new ArrayList<>(List.of("chatbox_character_god_in_the_past", "ChatBox_1_Line16")),
            new ArrayList<>(List.of("chatbox_character_innermost_being", "ChatBox_1_Line17")),
            new ArrayList<>(List.of("chatbox_character_god_in_the_past", "ChatBox_1_Line18")),
            new ArrayList<>(List.of("chatbox_character_innermost_being", "ChatBox_1_Line19")),
            new ArrayList<>(List.of("chatbox_character_innermost_being", "ChatBox_1_Line20"))));
    //对话1:第一章-苏醒时刻
    public ArrayList<?> ChatBox_2 = new ArrayList<>(List.of(
            new ArrayList<>(List.of("Player", "ChatBox_2_Line1")),
            new ArrayList<>(List.of("Player", "ChatBox_2_Line2")),
            new ArrayList<>(List.of("Player", "ChatBox_2_Line3")),
            new ArrayList<>(List.of("Player", "ChatBox_2_Line4"))));

    public ChatBoxRegister() {}

    public static ResourceLocation GetTextureWithoutPlayer(String string) {
        return ResourceLocation.fromNamespaceAndPath("immortal","textures/gui/chatbox/character_image/"+string+".png");
    }
    public static ArrayList<?> GetChatBox(int index, Player player) {
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            ChatBoxRegister c = new ChatBoxRegister();
            Field f = c.getClass().getField("ChatBox_"+index);
            f.setAccessible(true);
            return (ArrayList<?>) f.get(c);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
    public static void ChatBoxEnd(int index, Player player) {
        try {
            IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
            ChatBoxRegister c = new ChatBoxRegister();
            Method m = c.getClass().getDeclaredMethod("EndOfChatBox_"+index, Player.class);
            m.setAccessible(true);
            m.invoke(c,player);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
        }
    }
    public static void EndOfChatBox_0 (Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.setMainTask(1);
        ModNetworking.CHANNEL.sendToServer(new MainTaskSyncPacket(iImmortalPlayer.getMainTask().get()));
        player.sendSystemMessage(Component.translatable("Task_Accept"));
    }
    public static void EndOfChatBox_1 (Player player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.setMainTask(1);
        ModNetworking.CHANNEL.sendToServer(new MainTaskSyncPacket(iImmortalPlayer.getMainTask().get()));
        player.sendSystemMessage(Component.translatable("Task_Accept"));
    }
    public static void EndOfChatBox_2 (Player player) {
        player.sendSystemMessage(Component.literal("进入主线1"));
    }
}
