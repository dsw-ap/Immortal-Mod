package com.tearhpi.immortal.mixin;

import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BakedGlyph.class)
public interface BakedGlyphAccessor {
    @Accessor("u0") float immortal$u0();
    @Accessor("u1") float immortal$u1();
    @Accessor("v0") float immortal$v0();
    @Accessor("v1") float immortal$v1();

    // 这四个是 glyph quad 的局部坐标边界（相对字符绘制原点）
    @Accessor("left") float immortal$left();
    @Accessor("right") float immortal$right();
    @Accessor("up") float immortal$up();
    @Accessor("down") float immortal$down();
}
