package com.tearhpi.immortal.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.client.textrender.RainbowColor;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.networking.EnhancementTableWorkPacket;
import com.tearhpi.immortal.screen._skill.button.ActiveImageButton;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import static com.tearhpi.immortal.client.textrender.RainbowColor.intToRGBFloats;

public class EnhancementTableScreen extends AbstractContainerScreen<EnhancementTableMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/enhancement_table/enhancement_table.png");
    //public WidgetSprites Start_Button = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_1"),ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_3"),ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_2"));
    public ResourceLocation Start_Button = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/start.png");
    //按钮渲染
    public ImageButton checkbox;
    //private final EnhancementTableMenu enhancementTableMenu;
    public EnhancementTableScreen(EnhancementTableMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        this.inventoryLabelX = 10000;
        this.inventoryLabelY = 10000;
        this.titleLabelX = 6;
        this.titleLabelY = 5;
        this.imageWidth = 175;
        this.imageHeight = 221;
        this.checkbox= new ActiveImageButton(
                (this.width - this.imageWidth) / 2+133, (this.height - this.imageHeight) / 2+82,
                18,18,0,0,
                Start_Button,
                btn -> {
                    menu.player.playSound(ModSounds.ENHANCEMENT_TABLE_DO.get(),0.5f,1.0f);
                    ModNetworking.CHANNEL.sendToServer(new EnhancementTableWorkPacket(this.menu.enhancementTable.getBlockPos()));
                },18,18,54);
        this.addRenderableWidget(checkbox);
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        //底渲染,文字渲染
        p_283065_.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        p_283065_.drawString(this.font,Component.literal("\uD83D\uDCB0:"+menu.getCoin()), x+102+(50-this.font.width("\uD83D\uDCB0:"+menu.getCoin()))/2, y+111+(14-this.font.lineHeight)/2,0xFFAA00);
        /*WidgetSprites Start_Button = new WidgetSprites(
                ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_1"),
                ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_3"),
                ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_2")
        );
         */
        //按钮渲染
        if (menu.enhancementTable != null) {
            if (checkbox != null){
                if (menu.IsCheckBoxActive()){
                    //this.addRenderableWidget(checkbox);
                    checkbox.visible = true;
                } else {
                    //this.addRenderableWidget(checkbox2);
                    checkbox.visible = false;
                }}

            //武器强化提示
            if (menu.enhancementTable.itemHandler.getStackInSlot(0).getItem() instanceof WeaponItem weaponItem){
                //返回值:[武器品级,武器当前强化等级,消耗强化石数量,消耗金币数量]
                int[] materials = menu.enhancementTable.EnhancementMaterialCalculate();
                //武器本身品级
                p_283065_.drawString(this.font,Component.translatable("tooltip.enhancement_table_output2",materials[1]), x+6, y+79,0x555555,false);
                //成功率
                Component text = Component.translatable("tooltip.enhancement_table_output3",menu.enhancementTable.SuccessRate());
                p_283065_.drawString(this.font,text, x+6, y+94,0x555555,false);
                //失败惩罚
                Component text_ = Component.translatable("tooltip.enhancement_table_output4");
                int tier = menu.data.get(3);
                if (tier == 1){
                    text_ = Component.translatable("tooltip.enhancement_table_output4").append(Component.translatable("tooltip.enhancement_table_failure1"));
                } else if (tier == 2){
                    text_ = Component.translatable("tooltip.enhancement_table_output4").append(Component.translatable("tooltip.enhancement_table_failure2"));
                } else if (tier == 3) {
                    text_ = Component.translatable("tooltip.enhancement_table_output4").append(Component.translatable("tooltip.enhancement_table_failure3"));
                } else if (tier == 4) {
                    text_ = Component.translatable("tooltip.enhancement_table_output4").append(Component.translatable("tooltip.enhancement_table_failure4"));
                }

                p_283065_.drawString(this.font,text_, x+6, y+109,0x555555,false);

                //消耗材料:
                ResourceLocation ENHANCEMENT_STONE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/item/weapon_enhancement_stone.png");
                ResourceLocation COIN = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/enhancement_table/coin.png");
                p_283065_.blit(ENHANCEMENT_STONE, x+50, y+20, 0, 0, 16, 16, 16, 16);
                p_283065_.blit(COIN, x+50, y+38, 0, 0, 16, 16, 16, 16);
                Component text_stone_consume = Component.literal("x"+materials[2]);
                Component text_coin_consume = Component.literal("x"+materials[3]);
                p_283065_.drawString(this.font,text_stone_consume, x+72, y+21+(16-this.font.lineHeight)/2,0xFFFFFF,false);
                p_283065_.drawString(this.font,text_coin_consume, x+72, y+39+(16-this.font.lineHeight)/2,0xFFFFFF,false);
            }
        }
    }
    public void renderAnim(GuiGraphics gui) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int imageWidth_ = 210;
        int imageHeight_ = 210;
        int x = (this.width - imageWidth_) / 2;
        int y = (this.height - imageHeight_) / 2;
        ResourceLocation GUI_EMPTY_BG = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/enhancement_table/enhancement_ui.png");
        ResourceLocation PROGRESS = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/enhancement_table/progress.png");
        ResourceLocation RUNE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/enhancement_table/rune.png");
        gui.blit(GUI_EMPTY_BG, x, y, 0, 0, imageWidth_, imageHeight_);
        //物品渲染
        ItemStack itemStack_weapon = menu.enhancementTable.itemHandler.getStackInSlot(0);
        gui.pose().pushPose();
        gui.pose().translate(x+(210/2.0)-16, y+210 / 2.0-16, 0);
        gui.pose().scale(2.0f, 2.0f, 1.0f);
        gui.renderItem(itemStack_weapon, 0, 0);
        gui.pose().popPose();
        int Max_progress = menu.data.get(1);
        int progress = menu.data.get(0);
        int progress_int = 15+(int) (90.0 * progress / Max_progress);
        //上
        gui.blit(PROGRESS, x, y, 0, 0, 210, progress_int, imageWidth_, imageHeight_);
        //下
        gui.blit(PROGRESS, x+(210-progress_int), y, (210-progress_int), 0, progress_int, 210, imageWidth_, imageHeight_);
        //左
        gui.blit(PROGRESS, x, y, 0, 0, progress_int,210,  imageWidth_, imageHeight_);
        //右
        gui.blit(PROGRESS, x, y+(210-progress_int), 0, (210-progress_int),  210,progress_int, imageWidth_, imageHeight_);
        gui.pose().pushPose();
        gui.pose().translate(x, y, 0);
        float[] color = intToRGBFloats(new RainbowColor().getRainbowColor());
        gui.setColor(color[0], color[1], color[2], 1.0f);
        gui.blit(RUNE, 0, 0, 0, 0, 210, 210, imageWidth_, imageHeight_);
        gui.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gui.pose().popPose();
        int End = menu.data.get(2);
        Component text = null;
        if (End == 1) {
            text = Component.translatable("tooltip.enhancement_table_success_1");
        } else if (End == 2)  {
            text = Component.translatable("tooltip.enhancement_table_success_2");
        }
        if (text != null) {
            if (End == 1) {
                renderCircle(gui,x,y,new RainbowColor().getRainbowColor());
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                //光圈
                ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/background_under_item.png");
                //获取相关颜色
                float scale = 1.0f;
                int width = 32;
                float scaledSize = scale * width;
                int ticks = menu.data.get(0);
                RenderSystem.setShaderColor(color[0], color[1], color[2], 0.25f);
                gui.pose().pushPose();
                gui.pose().translate(x + 105, y + 105, 0);
                // 动态旋转 + 脉冲缩放
                float rotation = (ticks % 360) * 0.5f;
                //(x*(60-x))
                int tmp = ticks - menu.data.get(1);
                float pulsate = 1.0f + 0.003f * ((tmp*(60-tmp)));
                        //0.01f * Math.min(ticks-menu.data.get(1),menu.enhancementTable.Show_Time/2)+
                        //0.02f * Math.max(ticks-menu.data.get(1)-menu.enhancementTable.Show_Time/2,0);
                gui.pose().scale(pulsate, pulsate, 1.0f);
                gui.pose().mulPose(Axis.ZP.rotationDegrees(rotation));
                // 绘制纹理：围绕中心渲染
                gui.blit(GLOW_TEXTURE, (int) (-scaledSize / 2), (int) (-scaledSize / 2), 0, 0, 32, 32, 32, 32);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                gui.pose().popPose();
            }
            gui.drawString(this.font,text, x+(210-font.width(text))/2, y+123,0xFFFF55,false);
            if (End == 2) {
                renderCircle(gui,x,y,0xFFAAAAAA);
            }
            gui.drawString(this.font,text, x+(210-font.width(text))/2, y+123,0xFFFF55,false);
        }
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        //上覆盖层
        if (menu.IsCrafting()){
            this.renderTransparentBackground_(p_283479_);
            renderAnim(p_283479_);
        } else {
            renderBackground(p_283479_);
            super.render(p_283479_, p_283661_, p_281248_, p_281886_);
            renderTooltip(p_283479_, p_283661_, p_281248_);
        }
    }

    @Override
    public void containerTick() {
        if (checkbox != null){
            this.checkbox.visible = menu.IsCheckBoxActive();
        }
         // 控制是否显示
        super.containerTick();
    }


    public void renderTransparentBackground_(GuiGraphics p_300203_) {
        p_300203_.fillGradient(0, 0, this.width, this.height, 0xEF000000, 0xf0030300);
    }
    public void renderCircle(GuiGraphics gui, float x, float y,int color) {
        ResourceLocation CIRCLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/circle_under_item.png");

        // 透明混合设置
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        // 获取颜色 (RGB, Alpha为后面动态决定)
        float[] rgb = intToRGBFloats(color);

        // 获取时间（ticks）
        long ticks = menu.data.get(0) - menu.data.get(1);

        // 光圈周期（多少tick出现一个）
        int interval = 30; // 一圈持续时间
        float progress = (ticks % interval) / (float) interval;

        // 计算大小范围（从小到大）
        float minScale = 0.5f;
        float maxScale = 6.0f;
        float scale = minScale + (maxScale - minScale) * progress;

        // 计算透明度（开始亮，慢慢淡出）
        float alpha = 1.0f - progress; // 可换成 (1 - progress)^2

        RenderSystem.setShaderColor(rgb[0], rgb[1], rgb[2], alpha);

        gui.pose().pushPose();
        gui.pose().translate(x + 105, y + 105, 0);
        gui.pose().scale(scale, scale, 1.0f);

        // 渲染纹理
        gui.blit(CIRCLE_TEXTURE, -16, -16, 0, 0, 32, 32, 32, 32);
        gui.pose().popPose();

        // 恢复颜色状态
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }
}
