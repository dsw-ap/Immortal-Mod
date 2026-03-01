package com.tearhpi.immortal.screen._skill.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class ImageButtonModify extends ImageButton { // 投影透明度 0~1
    //private int length;
    public ImageButtonModify(int x, int y, int w, int h,
                             ResourceLocation resourceLocation,
                             OnPress onPress, int tw, int th) {
        super(x, y, w, h,0,0,0, resourceLocation,tw,th,onPress);
    }

    @Override
    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        g.blit(this.resourceLocation, this.getX(), this.getY(),this.getWidth(), this.getHeight(),0,0, textureWidth, textureHeight, textureWidth, textureHeight);
        RenderSystem.disableBlend();
    }
}
