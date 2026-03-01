package com.tearhpi.immortal.screen._skill.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class ActiveImageButton extends ImageButton {

    public ActiveImageButton(int x, int y, int w, int h, int u0, int v0, ResourceLocation p_169017_, OnPress p_169018_,int ydiff,int texW,int texH) {
        super(x, y, w, h,u0,v0, ydiff, p_169017_,texW,texH,p_169018_);
    }
    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int texY = this.yTexStart;

        if (!this.active) {
            // 禁用状态：向下偏移 1 个贴图行（常用）
            texY = this.yTexStart + this.yDiffTex ;
        } else if (this.isHoveredOrFocused()) {
            // 悬停状态：向下偏移 2 个贴图行
            texY = this.yTexStart + this.yDiffTex*2;
        }

        guiGraphics.blit(this.resourceLocation,
                this.getX(), this.getY(),  // 绘制位置
                this.getWidth(), this.getHeight(),
                this.xTexStart, texY,      // 贴图坐标
                this.textureWidth, yDiffTex,   // 显示尺寸
                this.textureWidth, this.textureHeight);  // 原贴图尺寸
    }
}
