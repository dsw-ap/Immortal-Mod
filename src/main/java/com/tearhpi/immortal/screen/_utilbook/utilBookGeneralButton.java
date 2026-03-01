package com.tearhpi.immortal.screen._utilbook;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public class utilBookGeneralButton extends StateSwitchingButton {
    public int max_width;
    public int max_height;
    public utilBookGeneralButton(int p_94615_, int p_94616_, int p_94617_, int p_94618_, boolean p_94619_, int max_width, int max_height) {
        super(p_94615_, p_94616_, p_94617_, p_94618_, p_94619_);
        this.max_height = max_height;
        this.max_width = max_width;
    }
    @Override
    public void renderWidget(GuiGraphics gg, int p_283010_, int p_281379_, float p_283453_) {
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        gg.blit(this.resourceLocation, this.getX(), this.getY(), this.width, this.height, i, j, this.width, this.height,max_width,max_height);
        RenderSystem.enableDepthTest();
    }
}
