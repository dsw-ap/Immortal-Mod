package com.tearhpi.immortal.screen._utilbook;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public class utilBookButton extends StateSwitchingButton {
    public int max_width;
    public int max_height;
    public Item item;
    public boolean hoverDiff;
    public utilBookButton(int p_94615_, int p_94616_, int p_94617_, int p_94618_, boolean p_94619_, int max_width, int max_height, @Nullable Item item) {
        super(p_94615_, p_94616_, p_94617_, p_94618_, p_94619_);
        this.max_height = max_height;
        this.max_width = max_width;
        this.item = item;
    }
    @Override
    public void renderWidget(GuiGraphics gg, int p_283010_, int p_281379_, float p_283453_) {
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        if (this.isStateTriggered) {
            j += this.yDiffTex;
        }
        //选中渲染偏移
        int x = this.getX();
        if (this.isStateTriggered) {
            x -= 2;
        }
        gg.blit(this.resourceLocation, x, this.getY(), this.width, this.height, i, j, this.width, this.height,max_width,max_height);
        if (this.item != null) {
            gg.renderItem(item.getDefaultInstance(),this.getX()+(this.width-16)/2, this.getY()+(this.height-16)/2);
        }
        RenderSystem.enableDepthTest();
    }
}
