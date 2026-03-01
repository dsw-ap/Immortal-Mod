package com.tearhpi.immortal.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.client.textrender.RainbowColor;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.networking.EnhancementTableWorkPacket;
import com.tearhpi.immortal.networking.ForgingTableResetPacket;
import com.tearhpi.immortal.networking.ForgingTableWorkPacket;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.screen._skill.button.ActiveImageButton;
import com.tearhpi.immortal.sound.ModSounds;
import com.tearhpi.immortal.util.ModTags;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.network.PacketDistributor;

import static com.tearhpi.immortal.client.textrender.RainbowColor.intToRGBFloats;

public class ForgingTableScreen extends AbstractContainerScreen<ForgingTableMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/forging_table/forging_table_ui.png");
    private static final ResourceLocation ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/gui/machine/forging_table/progress.png");
    //public WidgetSprites Start_Button = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_1"), ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_3"), ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/start_2"));
    //public WidgetSprites Reset_Button = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/reset_1"), ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"icon/reset_2"));
    public ResourceLocation Start_Button = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/start.png");
    public ResourceLocation Reset_Button = ResourceLocation.fromNamespaceAndPath(Immortal.MODID,"textures/gui/sprites/icon/reset_1");
    //按钮渲染
    public ImageButton checkbox;
    public ImageButton checkbox2;
    //private final EnhancementTableMenu enhancementTableMenu;
    public ForgingTableScreen(ForgingTableMenu p_97741_, Inventory p_97742_, Component p_97743_) {
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
                (this.width - this.imageWidth) / 2+133, (this.height - this.imageHeight) / 2+49,
                18, 18,0,0,
                Start_Button,
                btn -> {
                    menu.player.playSound(SoundEvents.ANVIL_USE,0.5f,1.0f);
                    //ModNetworking.CHANNEL.sendToServer(new ForgingTableWorkPacket(this.menu.forgingTableBlockEntity.getBlockPos()));
                    //ModNetworking.CHANNEL.sendToServer(new ForgingTableWorkPacket(this.menu.forgingTableBlockEntity.getBlockPos()));
                },18,18,54);
        this.checkbox2= new ActiveImageButton(
                (this.width - this.imageWidth) / 2+133, (this.height - this.imageHeight) / 2+69,
                18, 18,0,0,
                Reset_Button,
                btn -> {
                    menu.player.playSound(SoundEvents.ANVIL_DESTROY,0.5f,1.0f);
                    ModNetworking.CHANNEL.sendToServer(new ForgingTableResetPacket(this.menu.forgingTableBlockEntity.getBlockPos()));
                },0,18,54);
        this.addRenderableWidget(checkbox);
        this.addRenderableWidget(checkbox2);
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
        if (menu.forgingTableBlockEntity != null) {
            if (checkbox != null){
                if (menu.forgingTableBlockEntity.IsCheckBoxActive()){
                    checkbox.visible = true;
                } else {
                    checkbox.visible = false;
                }
            }
            if (checkbox2 != null){
                if (menu.forgingTableBlockEntity.IsResetActive()){
                    checkbox2.visible = true;
                } else {
                    checkbox2.visible = false;
                }
            }
            //装载武器后数值显示
            if (menu.forgingTableBlockEntity.itemHandler.getStackInSlot(0).getItem() instanceof WeaponItem weaponItem) {
                ItemStack weaponStack = menu.forgingTableBlockEntity.itemHandler.getStackInSlot(0);
                double[] show_num = menu.forgingTableBlockEntity.getShowNum();
                Component text_weapon_show_level = Component.translatable("tooltip.forging_table_output1", WeaponItem.getForgingLevel(weaponStack));
                Component text_weapon_show_num1 = Component.translatable("tooltip.forging_table_output2", show_num[0], show_num[1]);
                Component text_weapon_show_num2 = Component.translatable("tooltip.forging_table_output3", show_num[2], show_num[3]);
                p_283065_.drawString(this.font, text_weapon_show_level, x + 7, y + 60, 0x555555, false);
                p_283065_.drawString(this.font, text_weapon_show_num1, x + 7, y + 70, 0x555555, false);
                p_283065_.drawString(this.font, text_weapon_show_num2, x + 7, y + 80, 0x555555, false);
                //物品需求显示
                ResourceLocation FORGING_STONE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/item/weapon_forging_stone.png");
                p_283065_.blit(FORGING_STONE, x + 50, y + 20, 0, 0, 16, 16, 16, 16);
                Component text_stone_consume = Component.literal("x" + (WeaponItem.getForgingLevel(weaponStack) + 1) * (1 + WeaponItem.getForgingLevel(weaponStack)));
                p_283065_.drawString(this.font, text_stone_consume, x + 72, y + 21 + (16 - this.font.lineHeight) / 2, 0xFFFFFF, false);
                renderProgressArrow(p_283065_, x, y);
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
        guiGraphics.blit(ARROW_TEXTURE, mouseX+8, mouseY+95, 0, 0, menu.getScaledProgress(), 2, 162, 2);
    }
}
