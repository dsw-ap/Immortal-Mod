package com.tearhpi.immortal.screen._plot;

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.SettingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

import static com.tearhpi.immortal.util.TextRenderer.drawWrappedText;

public class Plot extends Screen {
    private int UI_trigger;
    private int Show_trigger;
    private TextScrollArea textArea;
    private Button btn_trigger_1;
    private Button btn_trigger_2;
    private Button btn1_1;
    private Button btn1_2;
    private Button btn1_3;
    private Button btn1_4;
    private Button btn2_1;
    private Button btn2_2;
    private Button btn2_3;
    private Button btn2_4;

    public Plot() {
        super(Component.translatable("plot.title"));
        this.UI_trigger = 1;
        this.Show_trigger = 0;
    }

    @Override
    protected void init() {
        int font_height = font.lineHeight;
        int size_height = (int)(font_height * 2);
        int size_width = this.width / 7;

        var player = Minecraft.getInstance().player;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        int progress;
        if (iImmortalPlayer != null) {
            progress = iImmortalPlayer.getMainTask().get();
        } else {
            progress = 0;
        }
        //模式切换
        this.btn_trigger_1 = Button.builder(Component.translatable("plot.UI_name1"), b -> {
            this.UI_trigger = 1;
            this.Show_trigger = 0;
            btn_trigger_1.active = true;
            resetUI1();
            textArea.setText(Component.empty());
        }).bounds(0,0, size_width,size_height).build();
        this.btn_trigger_2 = Button.builder(Component.translatable("plot.UI_name2"), b -> {
            this.UI_trigger = 2;
            this.Show_trigger = 0;
            btn_trigger_1.active = true;
            resetUI2();
            textArea.setText(Component.empty());
        }).bounds(size_width,0, size_width,size_height).build();

        //BTN1_1
        this.btn1_1 = Button.builder(Component.translatable("plot.plot.name1"), b -> {
            this.Show_trigger = 1;
            btn1_1.active = true;
            if (progress >= 1) textArea.setText(Component.translatable("plot.plot.context_1"));
            else textArea.setText(Component.translatable("plot.plot.context_null"));
        }).bounds(this.width/20,this.height/10, size_width,size_height).build();
        //BTN1_2
        this.btn1_2 = Button.builder(Component.translatable("plot.plot.name2"), b -> {
            this.Show_trigger = 2;
            btn1_2.active = true;
            if (progress >= 2) textArea.setText(Component.translatable("plot.plot.context_2"));
            else textArea.setText(Component.translatable("plot.plot.context_null"));
        }).bounds(this.width/20,this.height/10+size_height*1, size_width,size_height).build();

        //BTN2_1
        this.btn2_1 = Button.builder(Component.translatable("plot.memory.name1"), b -> {
            this.Show_trigger = 1;
            btn1_1.active = true;
        }).bounds(-1000,this.height/10, size_width,size_height).build();
        //BTN2_2
        this.btn2_2 = Button.builder(Component.translatable("plot.memory.name2"), b -> {
            this.Show_trigger = 2;
            btn1_2.active = true;
        }).bounds(-1000,this.height/10+size_height*1, size_width,size_height).build();
        //BTN2_3
        this.btn2_3 = Button.builder(Component.translatable("plot.memory.name3"), b -> {
            this.Show_trigger = 3;
            btn1_2.active = true;
        }).bounds(-1000,this.height/10+size_height*2, size_width,size_height).build();


        this.addRenderableWidget(this.btn_trigger_1);
        this.addRenderableWidget(this.btn_trigger_2);

        this.addRenderableWidget(this.btn1_1);
        this.addRenderableWidget(this.btn1_2);
        this.addRenderableWidget(this.btn2_1);
        this.addRenderableWidget(this.btn2_2);
        this.addRenderableWidget(this.btn2_3);
        //文本渲染区域
        int text_width_start = this.width * 5 / 20;
        int text_width_end = this.width * 9 / 10;
        int text_height_start = this.height / 10;
        int text_height_end = this.height * 19 / 20;
        textArea = new TextScrollArea(this.font);
        textArea.setBounds(text_width_start, text_height_start, text_width_end, text_height_end);
        textArea.setText(Component.empty());
        textArea.setScrollSpeed(14); // 可选
    }


    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        int font_height = font.lineHeight;

        int text_width_start = this.width * 5 / 20;
        int text_width_end = this.width * 9 / 10;
        int text_height_start = this.height / 10;
        int text_height_end = this.height * 19 / 20;


        this.renderBackground(gg);
        super.render(gg, mouseX, mouseY, partialTick);
        //文本渲染
        textArea.render(gg, 0xFFFFFF);
        //图片渲染
        if (this.UI_trigger == 2) {
            if (this.Show_trigger == 1) {
                gg.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/plot/1.png"),text_width_start,text_height_start,
                        (text_width_end-text_width_start),(text_height_end-text_height_start),
                        0,0,854,480,854,480);
                gg.drawString(font,Component.translatable("plot.memory.lore1"),text_width_start,text_height_end,0xFFFFFFFF);
            } else if (this.Show_trigger == 2) {
                gg.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/plot/2.png"),text_width_start,text_height_start,
                        (text_width_end-text_width_start),(text_height_end-text_height_start),
                        0,0,854,480,854,480);
                gg.drawString(font,Component.translatable("plot.memory.lore2"),text_width_start,text_height_end,0xFFFFFFFF);
            } else if (this.Show_trigger == 3) {
                gg.blit(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/plot/3.png"),text_width_start,text_height_start,
                        (text_width_end-text_width_start),(text_height_end-text_height_start),
                        0,0,854,480,854,480);
                gg.drawString(font,Component.translatable("plot.memory.lore3"),text_width_start,text_height_end,0xFFFFFFFF);
            }
        }
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (textArea != null && textArea.mouseScrolled(mouseX, mouseY, delta)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
    private void resetUI1(){
        btn1_1.setX(this.width/20);
        btn1_2.setX(this.width/20);
        btn2_1.setX(-1000);
        btn2_2.setX(-1000);
        btn2_3.setX(-1000);
    }
    private void resetUI2(){
        btn1_1.setX(-1000);
        btn1_2.setX(-1000);
        btn2_1.setX(this.width/20);
        btn2_2.setX(this.width/20);
        btn2_3.setX(this.width/20);
    }
    public class TextScrollArea {
        private final Font font;

        // bounds
        private int x0, y0, x1, y1;
        private int innerWidth, innerHeight;

        // content
        private final List<Line> lines = new ArrayList<>();
        private int totalContentHeight = 0;

        // scroll
        private int scrollY = 0;
        private int scrollSpeed = 12; // 每格滚动像素
        private int lineGap = 1;      // 行间距
        private int paragraphGap = 6; // 段落间距

        public TextScrollArea(Font font) {
            this.font = font;
        }

        /** 设置显示区域（左上(x0,y0) 到 右下(x1,y1)，右下为“边界坐标”） */
        public void setBounds(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            this.innerWidth = Math.max(0, x1 - x0);
            this.innerHeight = Math.max(0, y1 - y0);

            // bounds变化后需要重新 clamp
            clampScroll();
        }

        /** 设置文本并重新排版（会分段 + 换行） */
        public void setText(Component component) {
            lines.clear();
            totalContentHeight = 0;
            scrollY = 0;

            if (component == null || innerWidth <= 0) return;

            // 关键：使用 font.split 直接处理 Component
            List<net.minecraft.util.FormattedCharSequence> wrapped =
                    font.split(component, innerWidth);

            int yCursor = 0;

            for (var seq : wrapped) {
                lines.add(new Line(seq, yCursor));
                yCursor += font.lineHeight + lineGap;
            }

            totalContentHeight = yCursor;
            clampScroll();
        }

        /** 渲染：只在区域内显示（无滚动条图标） */
        public void render(GuiGraphics gg, int color) {
            if (innerWidth <= 0 || innerHeight <= 0) return;

            // scissor 裁剪：只显示区域内内容
            gg.enableScissor(x0, y0, x1, y1);

            int baseY = y0 - scrollY; // 把内容整体往上推 scrollY
            for (Line line : lines) {
                int drawY = baseY + line.y;
                // 粗略剔除：避免多余 draw
                if (drawY + font.lineHeight < y0) continue;
                if (drawY > y1) break;

                if (line.seq != null) {
                    gg.drawString(font, line.seq, x0, drawY, color);
                }
            }

            gg.disableScissor();
        }

        /** 鼠标滚轮：delta通常为 +/-1（也可能有小数），这里按方向处理 */
        public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
            if (!isMouseOver(mouseX, mouseY)) return false;

            int maxScroll = getMaxScroll();
            if (maxScroll <= 0) return false;

            // delta > 0 通常表示向上滚（内容下移），我们让 scrollY 变小
            int step = (int) Math.signum(delta) * scrollSpeed;
            scrollY = Mth.clamp(scrollY - step, 0, maxScroll);
            return true;
        }

        public boolean isMouseOver(double mx, double my) {
            return mx >= x0 && mx < x1 && my >= y0 && my < y1;
        }

        public void setScrollSpeed(int pxPerNotch) {
            this.scrollSpeed = Math.max(1, pxPerNotch);
        }

        private int getMaxScroll() {
            return Math.max(0, totalContentHeight - innerHeight);
        }

        private void clampScroll() {
            scrollY = Mth.clamp(scrollY, 0, getMaxScroll());
        }

        private static class Line {
            final net.minecraft.util.FormattedCharSequence seq;
            final int y;

            Line(net.minecraft.util.FormattedCharSequence seq, int y) {
                this.seq = seq;
                this.y = y;
            }

            static Line empty(int y) {
                return new Line(null, y);
            }
        }
    }
}
