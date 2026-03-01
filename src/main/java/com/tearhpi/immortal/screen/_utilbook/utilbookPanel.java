package com.tearhpi.immortal.screen._utilbook;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.damage.MainDamage;
import com.tearhpi.immortal.damage_type.ModDamageSources;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.mixin.RecipeBookComponentAccessor;
import com.tearhpi.immortal.networking.MainTaskSyncToClientPacket;
import com.tearhpi.immortal.networking.MainTaskSyncToMaxOnePacket;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.task.ModMainTask;
import com.tearhpi.immortal.task._MainTask;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class utilbookPanel {
    private boolean visible;
    private int leftPos; // 面板左上角 x
    private int topPos; // 面板左上角 y
    private int width = 147;
    private int height = 166;
    private int Panel_Index = 0;
    private int Page = 0;
    private Component display_damage_value_1 = Component.empty();
    private Component display_damage_value_2 = Component.empty();
    private static final int OFFSET_X_POSITION = 86;
    ResourceLocation PANEL_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/utilbook/background.png");
    ResourceLocation TAB_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/utilbook/tab.png");
    public utilBookButton tab1;
    public utilBookButton tab2;
    public utilBookButton tab3;
    public utilBookButton tab4;
    public utilBookButton tab5;
    public utilBookArrowButton button_back;
    public utilBookArrowButton button_forward;
    public Button button_tab2_page0_reset;
    public int Sync_mind;
    public List<utilBookButton> TabList = new ArrayList<>();

    public void init(int screenWidth, int screenHeight, int guiLeft, int guiTop, int imageWidth, int imageHeight) {
        //位置确定
        this.leftPos = (screenWidth - this.width) / 2 - this.OFFSET_X_POSITION;
        this.topPos = (screenHeight - this.height) / 2;
        //Tab
        tab1 = new utilBookButton(leftPos-30,topPos+2,35,27,false,35,54, Items.NETHERITE_SWORD);
        tab1.initTextureValues(0,0,0,27,TAB_BACKGROUND);
        TabList.add(tab1);
        tab2 = new utilBookButton(leftPos-30,topPos+32,35,27,false,35,54, Items.BOOK);
        tab2.initTextureValues(0,0,0,27,TAB_BACKGROUND);
        TabList.add(tab2);
        tab3 = new utilBookButton(leftPos-30,topPos+62,35,27,false,35,54, Items.APPLE);
        tab3.initTextureValues(0,0,0,27,TAB_BACKGROUND);
        TabList.add(tab3);
        tab4 = new utilBookButton(leftPos-30,topPos+92,35,27,false,35,54, Items.APPLE);
        tab4.initTextureValues(0,0,0,27,TAB_BACKGROUND);
        TabList.add(tab4);
        tab5 = new utilBookButton(leftPos-30,topPos+122,35,27,false,35,54, Items.APPLE);
        tab5.initTextureValues(0,0,0,27,TAB_BACKGROUND);
        TabList.add(tab5);
        //初始化
        tab1.setStateTriggered(true);
        Panel_Index = 1;
        //按钮
        button_back = new utilBookArrowButton(leftPos+width-40,topPos+height-20,11,10,false,11,28);
        button_back.initTextureValues(0,0,0,18,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/utilbook/_back.png"));
        button_forward = new utilBookArrowButton(leftPos+width-25,topPos+height-20,11,10,false,11,28);
        button_forward.initTextureValues(0,0,0,18,ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/utilbook/_forward.png"));
        this.button_tab2_page0_reset = Button.builder(Component.translatable("util.tab2.line1_Sync"), btn -> {

        }).bounds(leftPos + 15, topPos+height - 40,60,15).build();
    }
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        if (!visible) return;
        //背景渲染
        gg.blit(PANEL_BACKGROUND, leftPos, topPos, 1, 1, 147, 166);
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        Font font = mc.font;
        //Tab
        tab1.renderWidget(gg, mouseX, mouseY, partialTick);
        tab2.renderWidget(gg, mouseX, mouseY, partialTick);
        tab3.renderWidget(gg, mouseX, mouseY, partialTick);
        tab4.renderWidget(gg, mouseX, mouseY, partialTick);
        tab5.renderWidget(gg, mouseX, mouseY, partialTick);
        button_back.renderWidget(gg, mouseX, mouseY, partialTick);
        button_forward.renderWidget(gg, mouseX, mouseY, partialTick);
        button_tab2_page0_reset.render(gg, mouseX, mouseY, partialTick);
        //Tab细节
        if (Panel_Index == 0){
            gg.drawString(font, "Custom Panel", leftPos + 10, topPos + 10, 0xFFFFFF, false);
        } else if (Panel_Index == 1){
            int x_trans = (width - font.width(Component.translatable("util.tab1.title")))/2;
            gg.drawString(font, Component.translatable("util.tab1.title"), leftPos + x_trans, topPos + 9, 0x000000, false);
            if (player != null) {
                if (this.Page == 0) {
                    gg.drawString(font, Component.translatable("util.tab1.line1",player.getName()), leftPos+15, (int) (topPos + 10+font.lineHeight*1.1), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2",String.format("%.1f", player.getAttributeValue(ModAttributes.IMMORTAL_ATTACK_DAMAGE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*2.2), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line3",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_EXTRA_MDAMAGE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*3.3), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line4",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_CRITICAL_CHANCE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*4.4), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line5",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_CRITICAL_DAMAGE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*5.5), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line6",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*6.6), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line7",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*7.7), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line8",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*8.8), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line9",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*9.9), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line10",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*11), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line11",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*12.1), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line12",String.format("%.1f",player.getAttributeValue(ModAttributes.IMMORTAL_IGNORE_DEFENSE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*13.2), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line13",String.format("%.1f",player.getAttributeValue(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*14.3), 0x000000, false);
                }
                if (this.Page == 1) {
                    gg.drawString(font, Component.translatable("util.tab1.line2_1",(int) player.getHealth(),(int) player.getMaxHealth()), leftPos+15, (int) (topPos + 10+font.lineHeight*1.1), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_2",(int) iImmortalPlayer.getManaPoint().get(),(int) player.getAttributeValue(ModAttributes.IMMORTAL_MAGIC_POINT_MAX.get())), leftPos+15, (int) (topPos + 10+font.lineHeight*2.2), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_3",String.format("%.1f",player.getAttributeValue(ModAttributes.IMMORTAL_DEFENSE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*3.3), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_4",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_EXTRA_PROTECTION.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*4.4), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_5",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_DODGE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*5.5), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_6",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_DEBUFF_RESISTANCE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*6.6), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_7",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_FIRE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*7.7), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_8",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_WATER.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*8.8), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_9",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_AIR.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*9.9), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_10",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_EARTH.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*11), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_11",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_LIGHT.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*12.1), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line2_12",String.format("%.1f",player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_RESISTANCE_DARKNESS.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*13.2), 0x000000, false);
                }
                if (this.Page == 2) {
                    gg.drawString(font, Component.translatable("util.tab1.line3_0",iImmortalPlayer.getCoin().get()), leftPos+15, (int) (topPos + 10+font.lineHeight*1.1), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line3_1",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_COOLDOWN_IMMUNE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*2.2), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line3_2",String.format("%.1f",100*player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_SKILL_RANGE.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*3.3), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line3_3",String.format("%.1f",player.getAttributeValue(ModAttributes.IMMORTAL_PLAYER_BUFF_AMOUNT.get()))), leftPos+15, (int) (topPos + 10+font.lineHeight*4.4), 0x000000, false);
                    int x__ = leftPos+15;
                    int y__ = (int) (topPos + 10+font.lineHeight*6.6);
                    gg.drawString(font, Component.translatable("util.tab1.line3_4"), x__, y__, 0x000000, false);
                    //悬浮显示
                        int w__ = font.width(Component.translatable("util.tab1.line3_4"));
                        int h__ = font.lineHeight;
                        if (mouseX >= x__ && mouseX < x__ + w__ && mouseY >= y__ && mouseY < y__ + h__) {
                            var lines = font.split(Component.translatable("util.tab1.line3_4_hover"), 150);
                            gg.renderTooltip(font,lines, mouseX, mouseY);
                        }
                    Component final_dmg_add = Component.literal(String.valueOf((int) (player.getAttributeValue(ModAttributes.IMMORTAL_FINAL_DAMAGE_ADD.get())))).withStyle(ChatFormatting.RED);
                    gg.drawString(font, Component.translatable("util.tab1.line3_5", display_damage_value_1, final_dmg_add), leftPos + 15, (int) (topPos + 10 + font.lineHeight * 7.7), 0x000000, false);
                    gg.drawString(font, Component.translatable("util.tab1.line3_6", display_damage_value_2,final_dmg_add), leftPos+15, (int) (topPos + 10+font.lineHeight*8.8), 0x000000, false);
                    if (mc.level != null && mc.level.getGameTime() % 20 == 0) {
                        display_damage_value_1 = calculateDmg(player, 0.0f);
                        display_damage_value_2 = calculateDmg(player, 1.0f);
                    }
                }
            }
        } else if (Panel_Index == 2){
            int x_trans = (width - font.width(Component.translatable("util.tab2.title")))/2;
            gg.drawString(font, Component.translatable("util.tab2.title"), leftPos + x_trans, topPos + 9, 0x000000, false);
            if (player != null) {
                if (this.Page == 0) {
                    int task_index = iImmortalPlayer.getMainTask().get();
                    if (task_index == 0) {
                        float y_ = (float) (font.lineHeight*1.2);
                        Component Name_ = Component.translatable("util.tab2.line1_null");
                        List<FormattedCharSequence> Name_list = font.split(Name_, 117);
                        for (FormattedCharSequence line :Name_list) {
                            gg.drawString(font, line, leftPos+15, topPos + 13+y_, 0x000000, false);
                            y_ += font.lineHeight*1.2f;
                        }
                    } else if (task_index >= 1) {
                        _MainTask task = ModMainTask.getStaticMainTask(iImmortalPlayer.getMainTask().get());
                        if (task != null) {
                            float y_ = (float) (font.lineHeight*1.2);
                            Component Name_ = Component.translatable("util.tab2.line1_1", task.Name);
                            List<FormattedCharSequence> Name_list = font.split(Name_, 117);
                            for (FormattedCharSequence line :Name_list) {
                                gg.drawString(font, line, leftPos+15, topPos + 13+y_, 0x000000, false);
                                y_ += font.lineHeight*1.2f;
                            }
                            Component Description_ = Component.translatable("util.tab2.line1_2", task.Description);
                            List<FormattedCharSequence> Descripton_list = font.split(Description_, 117);
                            for (FormattedCharSequence line : Descripton_list) {
                                gg.drawString(font, line, leftPos+15, topPos + 13+y_, 0x000000, false);
                                y_ += font.lineHeight*1.2f;
                            }
                            gg.drawString(font, Component.translatable("util.tab2.line1_3", (int) task.Target.x, (int) task.Target.y, (int) task.Target.z), leftPos+15, (int) (topPos + 13+y_), 0x000000, false);
                            y_ += font.lineHeight*1.2f;
                            gg.drawString(font, Component.translatable("util.tab2.line1_4", task.Reward_Coin), leftPos+15, (int) (topPos + 13+y_), 0x000000, false);
                            y_ += font.lineHeight*1.2f;
                            gg.drawString(font, Component.translatable("util.tab2.line1_5"), leftPos+15, (int) (topPos + 13+y_), 0x000000, false);
                            y_ += font.lineHeight*1.2f;
                            int x_side = 0;
                            for (ItemStack itemStack: task.Reward){
                                gg.renderItem(itemStack,leftPos+15+x_side, (int) (topPos + 13+y_));
                                gg.renderItemDecorations(font,itemStack,leftPos+15+x_side, (int) (topPos + 13+y_));
                                x_side += 16;
                            }
                        } else {
                            float y_ = (float) (font.lineHeight*1.2);
                            Component Name_ = Component.translatable("util.tab2.line1_complete");
                            List<FormattedCharSequence> Name_list = font.split(Name_, 117);
                            for (FormattedCharSequence line :Name_list) {
                                gg.drawString(font, line, leftPos+15, topPos + 13+y_, 0x000000, false);
                                y_ += font.lineHeight*1.2f;
                            }
                        }
                    }
                    //跳过按钮
                    if (Sync_mind == 1){
                        gg.drawString(font, Component.translatable("util.tab2.line1_Sync_Set"), leftPos+15, topPos + height-font.lineHeight-15, 0x000000, false);
                    }
                }
            }
        } else if (Panel_Index == 3){
            gg.drawString(Minecraft.getInstance().font, "测试3", leftPos + 10, topPos + 10, 0xFFFFFF, false);
        } else if (Panel_Index == 4){
            gg.drawString(Minecraft.getInstance().font, "测试4", leftPos + 10, topPos + 10, 0xFFFFFF, false);
        } else if (Panel_Index == 5){
            gg.drawString(Minecraft.getInstance().font, "测试5", leftPos + 10, topPos + 10, 0xFFFFFF, false);
        }
        //左右按钮更新
        this.button_forward.isHovering = this.button_forward.isMouseOver(mouseX, mouseY);
        this.button_back.isHovering = this.button_back.isMouseOver(mouseX, mouseY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!visible) return false;
        if (tab1 != null && tab1.visible && tab1.active && tab1.isMouseOver(mouseX, mouseY) && tab1.mouseClicked(mouseX, mouseY, button)) {
            resetTab();
            tab1.setStateTriggered(true);
            Panel_Index = 1;
            return true;
        }
        if (tab2 != null && tab2.visible && tab2.active && tab2.isMouseOver(mouseX, mouseY) && tab2.mouseClicked(mouseX, mouseY, button)) {
            resetTab();
            tab2.setStateTriggered(true);
            Panel_Index = 2;
            return true;
        }
        if (tab3 != null && tab3.visible && tab3.active && tab3.isMouseOver(mouseX, mouseY) && tab3.mouseClicked(mouseX, mouseY, button)) {
            resetTab();
            tab3.setStateTriggered(true);
            Panel_Index = 3;
            return true;
        }
        if (tab4 != null && tab4.visible && tab4.active && tab4.isMouseOver(mouseX, mouseY) && tab4.mouseClicked(mouseX, mouseY, button)) {
            resetTab();
            tab4.setStateTriggered(true);
            Panel_Index = 4;
            return true;
        }
        if (tab5 != null && tab5.visible && tab5.active && tab5.isMouseOver(mouseX, mouseY) && tab5.mouseClicked(mouseX, mouseY, button)) {
            resetTab();
            tab5.setStateTriggered(true);
            Panel_Index = 5;
            return true;
        }
        if (button_forward != null && button_forward.visible && button_forward.active && button_forward.isMouseOver(mouseX, mouseY) && button_forward.mouseClicked(mouseX, mouseY, button)) {
            addPage();
            return true;
        }
        if (button_back != null && button_back.visible && button_back.active && button_back.isMouseOver(mouseX, mouseY) && button_back.mouseClicked(mouseX, mouseY, button)) {
            removePage();
            return true;
        }
        if (button_tab2_page0_reset != null && button_tab2_page0_reset.visible && button_tab2_page0_reset.active && button_tab2_page0_reset.isMouseOver(mouseX, mouseY) && button_tab2_page0_reset.mouseClicked(mouseX, mouseY, button)) {
            Sync_mind++;
            if (Sync_mind == 2){
                ModNetworking.CHANNEL.sendToServer(new MainTaskSyncToMaxOnePacket());
                Sync_mind = 0;
            }
            return true;
        }
        return false;
    }
    public void tick() {
        this.button_forward.visible=false;
        this.button_back.visible=false;
        this.button_tab2_page0_reset.visible=false;
        if (Panel_Index == 1 || Panel_Index == 2) {
            this.button_forward.visible=true;
            this.button_back.visible=true;
        }
        if (Panel_Index == 2 && Page == 0) {
            this.button_tab2_page0_reset.visible=true;
        }
    }
    public void addPage() {
        Sync_mind = 0;
        if (this.Panel_Index == 1){
            this.Page++;
            if (this.Page >= 2) this.Page = 2;
        }
        if (this.Panel_Index == 2){
            this.Page++;
            if (this.Page >= 1) this.Page = 1;
        }
    }
    public void removePage() {
        this.Page--;
        if (this.Page <= 0) this.Page = 0;
    }
    public void setVisible(boolean v) { this.visible = v; }
    public boolean isVisible() { return visible; }
    public void toggle() { this.visible = !this.visible; }
    private void resetTab() {
        for (utilBookButton b : TabList) {
            b.setStateTriggered(false);
        }
        this.Page = 0;
    }
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!visible) return false;
        return isMouseOver(mouseX, mouseY);
    }
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!visible) return false;
        return false;
    }
    private boolean isMouseOver(double mx, double my) {
        return mx >= leftPos && mx < leftPos + width && my >= topPos && my < topPos + height;
    }
    private Component calculateDmg(Player player, float critical_chance_adj) {
        //寻找最大的元素伤害
        float FireElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_FIRE.getHolder().get());
        float WaterElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_WATER.getHolder().get());
        float AirElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_AIR.getHolder().get());
        float EarthElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_EARTH.getHolder().get());
        float LightElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_LIGHT.getHolder().get());
        float DarknessElementDamageAdd = (float)player.getAttributeValue(ModAttributes.IMMORTAL_ELEMENT_MDAMAGE_DARKNESS.getHolder().get());
        float max_value = FireElementDamageAdd;
        if (max_value < WaterElementDamageAdd) max_value = WaterElementDamageAdd;
        if (max_value < AirElementDamageAdd) max_value = AirElementDamageAdd;
        if (max_value < EarthElementDamageAdd) max_value = EarthElementDamageAdd;
        if (max_value < LightElementDamageAdd) max_value = LightElementDamageAdd;
        if (max_value < DarknessElementDamageAdd) max_value = DarknessElementDamageAdd;
        ModDamageSources modDamageSources = new ModDamageSources(player.level().registryAccess());
        DamageSource damagesource = null;
        if (max_value == FireElementDamageAdd) {
            damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_FIRE,player);
            return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj))).withStyle(ChatFormatting.RED);
        } else if (max_value == WaterElementDamageAdd) {
            damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_WATER,player);
            return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj))).withStyle(ChatFormatting.BLUE);
        } else if (max_value == AirElementDamageAdd) {
            damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_AIR,player);
            return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj))).withStyle(ChatFormatting.DARK_GREEN);
        } else if (max_value == EarthElementDamageAdd) {
            damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_EARTH,player);
            return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj))).withStyle(ChatFormatting.DARK_GRAY);
        } else if (max_value == LightElementDamageAdd) {
            damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_LIGHT,player);
            return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj))).withStyle(ChatFormatting.GOLD);
        } else if (max_value == DarknessElementDamageAdd) {
            damagesource = modDamageSources.immortalDMGType(ModDamageSources.IMMORTAL_DARKNESS,player);
            return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj))).withStyle(ChatFormatting.BLACK);
        }
        return Component.literal(String.valueOf((int) MainDamage.getDamage(player,damagesource,false, 1.0F, critical_chance_adj)));
    }
}
