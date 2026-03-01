package com.tearhpi.immortal.screen._skill.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tearhpi.immortal.skill.PassiveSkillAttribute;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ShadowImageSkillButton extends ShadowImageButton {
    public PassiveSkillAttribute passiveSkillAttribute;
    public int dx;
    public int dy;
    public ShadowImageSkillButton(int x, int y, int w, int h,
                                  ResourceLocation resourceLocation,
                                  OnPress onPress,
                                  int shadowDx, int shadowDy, float shadowAlpha, PassiveSkillAttribute passiveSkillAttribute, int dx, int dy,int tw,int th) {
        super(x, y, w, h, resourceLocation, onPress,shadowDx,shadowDy,shadowAlpha,tw,th);
        this.dx = dx;
        this.dy = dy;
        this.setPosition(x+this.dx, y+this.dy);
        this.passiveSkillAttribute = passiveSkillAttribute;
    }
    public ShadowImageSkillButton(int x, int y, int w, int h,
                                  ResourceLocation resourceLocation,
                                  OnPress onPress,
                                  int shadowDx, int shadowDy, float shadowAlpha, PassiveSkillAttribute passiveSkillAttribute, int dx, int dy) {
        super(x, y, w, h, resourceLocation, onPress,shadowDx,shadowDy,shadowAlpha,256,256);
        this.dx = dx;
        this.dy = dy;
        this.setPosition(x+this.dx, y+this.dy);
        this.passiveSkillAttribute = passiveSkillAttribute;
    }
    public ShadowImageSkillButton(int x, int y, int w, int h,
                                  ResourceLocation resourceLocation,
                                  OnPress onPress,
                                  int shadowDx, int shadowDy, float shadowAlpha, PassiveSkillAttribute passiveSkillAttribute, double dx, double dy,int dh,int theta) {
        super(x, y, w, h, resourceLocation, onPress,shadowDx,shadowDy,shadowAlpha,256,256);
        this.dx = (int) (dx + dh*Math.cos(Math.toRadians(theta)));
        this.dy = (int) (dy - dh*Math.sin(Math.toRadians(theta)));
        //System.out.println("TEST"+dh*Math.cos(Math.toRadians(theta))+"/"+dh*Math.sin(Math.toRadians(theta)));
        this.setPosition(x+this.dx, y+this.dy);
        this.passiveSkillAttribute = passiveSkillAttribute;
    }
    public ShadowImageSkillButton(int x, int y, int w, int h,
                                  ResourceLocation resourceLocation,
                                  OnPress onPress,
                                  int shadowDx, int shadowDy, float shadowAlpha, PassiveSkillAttribute passiveSkillAttribute, double dx, double dy,int dh,int theta,int tw,int th) {
        super(x, y, w, h, resourceLocation, onPress,shadowDx,shadowDy,shadowAlpha,tw,th);
        this.dx = (int) (dx + dh*Math.cos(Math.toRadians(theta)));
        this.dy = (int) (dy - dh*Math.sin(Math.toRadians(theta)));
        //System.out.println("TEST"+dh*Math.cos(Math.toRadians(theta))+"/"+dh*Math.sin(Math.toRadians(theta)));
        this.setPosition(x+this.dx, y+this.dy);
        this.passiveSkillAttribute = passiveSkillAttribute;
    }
    /*
    public WidgetSprites getSprites() {
        return this.sprites;
    }
     */
}
