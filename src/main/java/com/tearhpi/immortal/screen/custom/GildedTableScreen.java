package com.tearhpi.immortal.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.networking.ForgingTableResetPacket;
import com.tearhpi.immortal.networking.ForgingTableWorkPacket;
import com.tearhpi.immortal.networking.GildedTableWorkPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.screen._skill.button.ActiveImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class GildedTableScreen extends AbstractContainerScreen<GildedTableMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/gilded_table/gilded_table_ui.png");
    private static final ResourceLocation ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/gilded_table/arrow.png");
    private static final ResourceLocation GOLD = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/gilded_table/gold_amount.png");
    public ResourceLocation Start_Button = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/start.png");
    //按钮渲染
    public ActiveImageButton checkbox;
    //private final EnhancementTableMenu enhancementTableMenu;
    public GildedTableScreen(GildedTableMenu p_97741_, Inventory p_97742_, Component p_97743_) {
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
                (this.width - this.imageWidth) / 2+86, (this.height - this.imageHeight) / 2+50,
                18, 18,0,0,
                Start_Button,
                btn -> {
                    //menu.player.playSound(SoundEvents.ANVIL_USE,0.5f,1.0f);
                    ModNetworking.CHANNEL.sendToServer(new GildedTableWorkPacket(this.menu.gildedTableBlockEntity.getBlockPos()));
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
        //按钮渲染
        if (menu.gildedTableBlockEntity != null) {
            if (checkbox != null){
                if (menu.gildedTableBlockEntity.IsCheckBoxActive()){
                    checkbox.visible = true;
                } else {
                    checkbox.visible = false;
                }
            }
            //金量渲染
            renderProgressGold(p_283065_, x, y,14,15,2);
            renderProgressGold(p_283065_, x, y,33,15,3);
            renderProgressGold(p_283065_, x, y,52,15,4);
            renderProgressGold(p_283065_, x, y,71,15,5);
            renderProgressArrow(p_283065_, x, y);
            //装载武器后数值显示
            if (menu.gildedTableBlockEntity.itemHandler.getStackInSlot(0).getItem() instanceof WeaponItem weaponItem) {
                ItemStack weaponStack = menu.gildedTableBlockEntity.itemHandler.getStackInSlot(0);
                Component text_weapon_show_level = Component.translatable("tooltip.gilded_table_output1", WeaponItem.getGildedLevel(weaponStack));
                Component text_weapon_show_material1 = Component.translatable("tooltip.gilded_table_output2");
                Component text_weapon_show_material2 = Component.translatable("tooltip.gilded_table_output3");
                Component text_weapon_show_material3 = Component.translatable("tooltip.gilded_table_output4");
                Component text_weapon_show_material4 = Component.translatable("tooltip.gilded_table_output5");
                p_283065_.drawString(this.font, text_weapon_show_level, x + 86, y + 74, 0x555555, false);
                if (WeaponItem.getGildedLevel(weaponStack) == 0){
                    p_283065_.drawString(this.font, text_weapon_show_material1, x + 86, y + 84, 0x555555, false);
                } else if (WeaponItem.getGildedLevel(weaponStack) == 1) {
                    p_283065_.drawString(this.font, text_weapon_show_material2, x + 86, y + 84, 0x555555, false);
                } else if (WeaponItem.getGildedLevel(weaponStack) == 2) {
                    p_283065_.drawString(this.font, text_weapon_show_material3, x + 86, y + 84,0x555555, false);
                } else if (WeaponItem.getGildedLevel(weaponStack) == 3) {
                    p_283065_.drawString(this.font, text_weapon_show_material4, x + 86, y + 84, 0x555555, false);
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        renderBackground(p_283479_);
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
        renderTooltip(p_283479_, p_283661_, p_281248_);
    }
    private void renderProgressArrow(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.blit(ARROW_TEXTURE, mouseX+113, mouseY+31, 0, 0, menu.getScaledProgress(), 16, 24, 16);
    }
    private void renderProgressGold(GuiGraphics guiGraphics, int mouseX, int mouseY,int deltaX,int deltaY,int gold_type) {
        guiGraphics.blit(GOLD, mouseX+deltaX, mouseY+deltaY+44-menu.getScaledGoldAmount(gold_type), 0, 44-menu.getScaledGoldAmount(gold_type), 4, menu.getScaledGoldAmount(gold_type), 4, 44);
    }
}
