package com.tearhpi.immortal.screen._skill.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tearhpi.immortal.skill.PassiveSkillAttribute;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class ShadowImageButton extends ImageButton {
    private final int shadowDx, shadowDy; // 投影偏移像素
    private final float shadowAlpha;      // 投影透明度 0~1
    //private int length;
    public ShadowImageButton(int x, int y, int w, int h,
                             ResourceLocation resourceLocation,
                             OnPress onPress,
                             int shadowDx, int shadowDy, float shadowAlpha,int tw,int th) {
        super(x, y, w, h,0,0,0, resourceLocation,tw,th,onPress);
        //this.length = length;
        this.shadowDx = shadowDx;
        this.shadowDy = shadowDy;
        this.shadowAlpha = shadowAlpha;
    }

    @Override
    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        // 1) 画阴影：同一张图，偏移+染成黑色半透明
        //ResourceLocation rl = this.sprites.get(this.isActive(), this.isHoveredOrFocused());
        // 若你的版本是 active() / isHoveredOrFocused()：
        // ResourceLocation rl = this.sprites.get(this.active(), this.isHoveredOrFocused());

        // 2) 先画投影：同一张精灵，偏移 + 染成黑色
        RenderSystem.enableBlend();
        // 有些版本有 tint 重载：blitSprite(rl, x, y, z, w, h, tintARGB)
        // 如果你有这个重载，推荐用下面这一行：
        //g.blit(this.resourceLocation, this.getX() + shadowDx, this.getY() + shadowDy, this.getWidth(), this.getHeight(),0,0, textureWidth, textureHeight, textureWidth, textureHeight);

        // 否则用 setShaderColor 染色：
        RenderSystem.setShaderColor(0f, 0f, 0f, this.shadowAlpha);
        //System.out.println(textureWidth+"/"+textureHeight+"/"+this.getWidth()+"/"+this.getHeight());
        g.blit(this.resourceLocation, this.getX() + shadowDx, this.getY() + shadowDy,this.getWidth(), this.getHeight(),0,0, textureWidth, textureHeight, textureWidth, textureHeight);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        // 2) 画本体（父类会根据 hover/active 状态选择正确图片）
        g.blit(this.resourceLocation, this.getX(), this.getY(), this.getWidth(), this.getHeight(),0,0, textureWidth, textureHeight, textureWidth, textureHeight);
        //super.renderWidget(g, mouseX, mouseY, partialTick);
    }
}
