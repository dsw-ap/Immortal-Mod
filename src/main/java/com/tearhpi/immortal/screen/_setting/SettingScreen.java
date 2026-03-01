package com.tearhpi.immortal.screen._setting;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SettingPacket;
import com.tearhpi.immortal.networking.SettingToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import static com.tearhpi.immortal.util.TextRenderer.drawWrappedText;

public class SettingScreen extends Screen {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;


    public SettingScreen() {
        super(Component.translatable("setting.title"));
    }


    @Override
    protected void init() {
        int size_width = (this.width) / 5;
        int size_width_margin = size_width / 5;
        int button_size_width = size_width / 2;
        int size_height = (this.height) / 10;
        int button_size_height = size_height / 10;

        var player = Minecraft.getInstance().player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        //BTN1
        this.btn1 = Button.builder(Component.translatable("setting.false"), b -> {
            iImmortalPlayer.getSetting().Setting1 = (iImmortalPlayer.getSetting().Setting1+1)%2;
            if (iImmortalPlayer.getSetting().Setting1 == 0) {this.btn1.setMessage(Component.translatable("setting.false"));}
            else {this.btn1.setMessage(Component.translatable("setting.true"));}
            btn1.active = true;
            ModNetworking.CHANNEL.sendToServer(new SettingPacket(iImmortalPlayer.getSetting().getSkillCompoundTag(iImmortalPlayer.getSetting().getNumInfo())));
        }).bounds(size_width_margin+button_size_width, size_height+button_size_height, button_size_width,size_height).build();
        if (iImmortalPlayer.getSetting().Setting1 == 1) {
            this.btn1.setMessage(Component.translatable("setting.true"));
        }
        //BTN2
        this.btn2 = Button.builder(Component.translatable("setting.false"), b -> {
            iImmortalPlayer.getSetting().Setting2 = (iImmortalPlayer.getSetting().Setting2+1)%2;
            if (iImmortalPlayer.getSetting().Setting2 == 0) {this.btn2.setMessage(Component.translatable("setting.false"));}
            else {this.btn2.setMessage(Component.translatable("setting.true"));}
            btn2.active = true;
            ModNetworking.CHANNEL.sendToServer(new SettingPacket(iImmortalPlayer.getSetting().getSkillCompoundTag(iImmortalPlayer.getSetting().getNumInfo())));
        }).bounds(size_width_margin+button_size_width, 2*(size_height+button_size_height), button_size_width,size_height).build();
        if (iImmortalPlayer.getSetting().Setting2 == 1) {
            this.btn2.setMessage(Component.translatable("setting.true"));
        }
        //BTN3
        this.btn3 = Button.builder(Component.translatable("setting.false"), b -> {
            iImmortalPlayer.getSetting().Setting3 = (iImmortalPlayer.getSetting().Setting3+1)%2;
            if (iImmortalPlayer.getSetting().Setting3 == 0) {this.btn3.setMessage(Component.translatable("setting.false"));}
            else {this.btn3.setMessage(Component.translatable("setting.true"));}
            btn3.active = true;
            ModNetworking.CHANNEL.sendToServer(new SettingPacket(iImmortalPlayer.getSetting().getSkillCompoundTag(iImmortalPlayer.getSetting().getNumInfo())));
        }).bounds(size_width_margin+button_size_width, 3*(size_height+button_size_height), button_size_width,size_height).build();
        if (iImmortalPlayer.getSetting().Setting3 == 1) {
            this.btn3.setMessage(Component.translatable("setting.true"));
        }
        //BTN4
        this.btn4 = Button.builder(Component.translatable("setting.false"), b -> {
            iImmortalPlayer.getSetting().Setting4 = (iImmortalPlayer.getSetting().Setting4+1)%2;
            if (iImmortalPlayer.getSetting().Setting4 == 0) {this.btn4.setMessage(Component.translatable("setting.false"));}
            else {this.btn4.setMessage(Component.translatable("setting.true"));}
            btn4.active = true;
            ModNetworking.CHANNEL.sendToServer(new SettingPacket(iImmortalPlayer.getSetting().getSkillCompoundTag(iImmortalPlayer.getSetting().getNumInfo())));
        }).bounds(size_width_margin+button_size_width, 4*(size_height+button_size_height), button_size_width,size_height).build();
        if (iImmortalPlayer.getSetting().Setting4 == 1) {
            this.btn4.setMessage(Component.translatable("setting.true"));
        }

        this.addRenderableWidget(this.btn1);
        this.addRenderableWidget(this.btn2);
        this.addRenderableWidget(this.btn3);
        this.addRenderableWidget(this.btn4);
    }


    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        int size_width = (this.width) / 5;
        int size_width_margin = size_width / 5;
        int button_size_width = size_width / 2;
        int size_height = (this.height) / 10;
        int button_size_height = size_height / 10;

        this.renderBackground(gg);
        super.render(gg, mouseX, mouseY, partialTick);
        gg.drawString(this.font, this.title, 8, 8, 0xFFFFFF);
        drawWrappedText(gg,font,Component.translatable("setting.mind_1"),size_width_margin, size_height+button_size_height,button_size_width,font.lineHeight, 0xFFFFFF,false);
        drawWrappedText(gg,font,Component.translatable("setting.mind_2"),size_width_margin, 2*(size_height+button_size_height),button_size_width,font.lineHeight, 0xFFFFFF,false);
        drawWrappedText(gg,font,Component.translatable("setting.mind_3"),size_width_margin, 3*(size_height+button_size_height),button_size_width,font.lineHeight, 0xFFFFFF,false);
        drawWrappedText(gg,font,Component.translatable("setting.mind_4"),size_width_margin, 4*(size_height+button_size_height),button_size_width,font.lineHeight, 0xFFFFFF,false);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}