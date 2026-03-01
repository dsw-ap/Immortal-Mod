package com.tearhpi.immortal.client.textrender;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tearhpi.immortal.mixin.BakedGlyphAccessor;
import com.tearhpi.immortal.mixin.FontAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FastColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class GradientGlyphRenderer {
    //字内部左右渐变
    public static float drawGradientChar(GuiGraphics gg, Font font, char ch, float x, float y, int colorLeft, int colorRight, int packedLight,boolean bold) {
        FontSet set = ((FontAccessor)font).immortal$invokeGetFontSet(Style.DEFAULT_FONT);
        BakedGlyph glyph = set.getGlyph(ch);
        if (glyph == null) return 0;
        BakedGlyphAccessor a = (BakedGlyphAccessor) glyph;
        drawGradientGlyphQuad(gg, font, glyph, x, y, colorLeft, colorRight, packedLight);
        if (bold) {
            drawGradientGlyphQuad(gg, font, glyph, x + 0.5f, y, colorLeft, colorRight, packedLight);
        }
        return font.width(String.valueOf(ch));
    }
    private static void drawGradientGlyphQuad(GuiGraphics gg, Font font, BakedGlyph glyph, float x, float y, int colorLeft, int colorRight, int packedLight) {
        BakedGlyphAccessor a = (BakedGlyphAccessor) glyph;
        float left = a.immortal$left();
        float right = a.immortal$right();
        float up = a.immortal$up();
        float down = a.immortal$down();
        float u0 = a.immortal$u0();
        float u1 = a.immortal$u1();
        float v0 = a.immortal$v0();
        float v1 = a.immortal$v1();
        RenderType rt = glyph.renderType(Font.DisplayMode.NORMAL);
        VertexConsumer vc = gg.bufferSource().getBuffer(rt);
        Matrix4f mat = gg.pose().last().pose();
        int aL = FastColor.ARGB32.alpha(colorLeft);
        int rL = FastColor.ARGB32.red(colorLeft);
        int gL = FastColor.ARGB32.green(colorLeft);
        int bL = FastColor.ARGB32.blue(colorLeft);
        int aR = FastColor.ARGB32.alpha(colorRight);
        int rR = FastColor.ARGB32.red(colorRight);
        int gR = FastColor.ARGB32.green(colorRight);
        int bR = FastColor.ARGB32.blue(colorRight);
        vc.vertex(mat, x + left,  y + up,   0).color(rL,gL,bL,aL).uv(u0,v0).uv2(packedLight).endVertex();
        vc.vertex(mat, x + left,  y + down, 0).color(rL,gL,bL,aL).uv(u0,v1).uv2(packedLight).endVertex();
        vc.vertex(mat, x + right, y + down, 0).color(rR,gR,bR,aR).uv(u1,v1).uv2(packedLight).endVertex();
        vc.vertex(mat, x + right, y + up,   0).color(rR,gR,bR,aR).uv(u1,v0).uv2(packedLight).endVertex();
    }
    //绘制渐变字符串
    public static void drawGradientString(GuiGraphics gg, Font font, String s, float x, float y, int startColor, int endColor, int packedLight,boolean bold) {
        if (s == null || s.isEmpty()) return;
        float cx = x;
        int n = s.length();
        float inv = (n <= 1) ? 0.0f : 1.0f / (n - 1);
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            float t = i * inv;
            float t0 = t;
            float t1 = Math.min(1.0f, (i + 1) * inv);
            int leftColor  = lerpARGB(startColor, endColor, t0);
            int rightColor = lerpARGB(startColor, endColor, t1);
            cx += GradientGlyphRenderer.drawGradientChar(gg, font, ch, cx, y, leftColor, rightColor, packedLight,bold);
        }
    }

    /** ARGB 线性插值*/
    private static int lerpARGB(int c0, int c1, float t) {
        int a0 = FastColor.ARGB32.alpha(c0), r0 = FastColor.ARGB32.red(c0),
                g0 = FastColor.ARGB32.green(c0), b0 = FastColor.ARGB32.blue(c0);
        int a1 = FastColor.ARGB32.alpha(c1), r1 = FastColor.ARGB32.red(c1),
                g1 = FastColor.ARGB32.green(c1), b1 = FastColor.ARGB32.blue(c1);
        int a = (int)(a0 + (a1 - a0) * t);
        int r = (int)(r0 + (r1 - r0) * t);
        int g = (int)(g0 + (g1 - g0) * t);
        int b = (int)(b0 + (b1 - b0) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}

