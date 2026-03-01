package com.tearhpi.immortal.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static final String KEY_Category_Immortal = "key.categories.immortal"; // 可以用于语言文件
    public static KeyMapping OPEN_SKILLGUI_KEY = new KeyMapping("key.immortal.open_skill_gui", KeyConflictContext.IN_GAME,InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K,KEY_Category_Immortal);
    public static KeyMapping OPEN_SETTING_KEY = new KeyMapping("key.immortal.open_setting_gui", KeyConflictContext.IN_GAME,InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L,KEY_Category_Immortal);
    public static KeyMapping OPEN_PLOT_KEY = new KeyMapping("key.immortal.open_plot_gui", KeyConflictContext.IN_GAME,InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O,KEY_Category_Immortal);
    public static final KeyMapping SKILL1 = new KeyMapping("key.immortal.skill1", KeyConflictContext.IN_GAME, net.minecraftforge.client.settings.KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_1, KEY_Category_Immortal);
    public static final KeyMapping SKILL2 = new KeyMapping("key.immortal.skill2", KeyConflictContext.IN_GAME, net.minecraftforge.client.settings.KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_2, KEY_Category_Immortal);
    public static final KeyMapping SKILL3 = new KeyMapping("key.immortal.skill3", KeyConflictContext.IN_GAME, net.minecraftforge.client.settings.KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_3, KEY_Category_Immortal);
    public static final KeyMapping SKILL4 = new KeyMapping("key.immortal.skill4", KeyConflictContext.IN_GAME, net.minecraftforge.client.settings.KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_4, KEY_Category_Immortal);
    public static final KeyMapping SKILL5 = new KeyMapping("key.immortal.skill5", KeyConflictContext.IN_GAME, net.minecraftforge.client.settings.KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_5, KEY_Category_Immortal);
    public static final KeyMapping CHANGE_SKILL = new KeyMapping("key.immortal.change_skill", KeyConflictContext.IN_GAME, net.minecraftforge.client.settings.KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_Category_Immortal);
}
