package com.tearhpi.immortal.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Either;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.client.textrender.RainbowColor;
import com.tearhpi.immortal.item.custom.CustomArmorItem;
import com.tearhpi.immortal.item.custom.WeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.List;

@Mod.EventBusSubscriber(modid = Immortal.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ToolTipFrameReg {
    //渲染边框
    @SubscribeEvent
    public static void onRenderTooltip(RenderTooltipEvent.Color event) {
        Item item = event.getItemStack().getItem();
        if (item instanceof WeaponItem weapon) {
            weapon.Tooltip_width = event.getComponents().stream().mapToInt(c -> c.getWidth(event.getFont())).max().orElse(0) + 8;
            weapon.Tooltip_height = event.getComponents().stream().mapToInt(ClientTooltipComponent::getHeight).sum() + 8;
            weapon.Tooltip_x = event.getX()-4;
            weapon.Tooltip_y = event.getY()-4;
            if (weapon.weapon_level == 1){
                event.setBorderStart(0xA0C9C9C9);
                event.setBorderEnd(0x70797979);
                event.setBackground(0xF018181a);
            } else if (weapon.weapon_level == 2) {
                event.setBorderStart(0xA03c66a3);
                event.setBorderEnd(0x70305182);
                event.setBackground(0xF00a051a);
            } else if (weapon.weapon_level == 3) {
                event.setBorderStart(0xA04b229c);
                event.setBorderEnd(0x703d178d);
                event.setBackground(0xF008051a);
            } else if (weapon.weapon_level == 4) {
                event.setBorderStart(0xA0752054);
                event.setBorderEnd(0x70692464);
                event.setBackground(0xF017091a);
            } else if (weapon.weapon_level == 5) {
                event.setBorderStart(0xA09c4121);
                event.setBorderEnd(0x707d3833);
                event.setBackground(0xF01a0e05);
            } else if (weapon.weapon_level == 6) {
                event.setBorderStart(0xA0fbc02d);
                event.setBorderEnd(0x70d4a226);
                event.setBackground(0xF01a1405);
            } else if (weapon.weapon_level == 7) {
                //event.setBorderStart(RainbowColor.getRainbowColor_Spec_Level7_Up());
                //event.setBorderEnd(RainbowColor.getRainbowColor_Spec_Level7_Down());
                //event.setBackground((new RainbowColor().getRainbowColor())& 0xF0777777);
                event.setBorderStart(0x00C9C9C9);
                event.setBorderEnd(0x00797979);
                event.setBackground(0x0018181a);
            } else if (weapon.weapon_level == 8) {
                event.setBorderStart(0xA0571a25);
                event.setBorderEnd(0x7040131b);
                event.setBackground(0xF01a080b);
            }
        }
        if (item instanceof CustomArmorItem armor) {
            armor.Tooltip_width = event.getComponents().stream().mapToInt(c -> c.getWidth(event.getFont())).max().orElse(0) + 8;
            armor.Tooltip_height = event.getComponents().stream().mapToInt(ClientTooltipComponent::getHeight).sum() + 8;
            armor.Tooltip_x = event.getX()-4;
            armor.Tooltip_y = event.getY()-4;
            if (armor.armor_level == 1){
                event.setBorderStart(0xA0C9C9C9);
                event.setBorderEnd(0x70797979);
                event.setBackground(0xF018181a);
            } else if (armor.armor_level == 2) {
                event.setBorderStart(0xA03c66a3);
                event.setBorderEnd(0x70305182);
                event.setBackground(0xF00a051a);
            } else if (armor.armor_level == 3) {
                event.setBorderStart(0xA04b229c);
                event.setBorderEnd(0x703d178d);
                event.setBackground(0xF008051a);
            } else if (armor.armor_level == 4) {
                event.setBorderStart(0xA0752054);
                event.setBorderEnd(0x70692464);
                event.setBackground(0xF017091a);
            } else if (armor.armor_level == 5) {
                event.setBorderStart(0xA09c4121);
                event.setBorderEnd(0x707d3833);
                event.setBackground(0xF01a0e05);
            } else if (armor.armor_level == 6) {
                event.setBorderStart(0xA0fbc02d);
                event.setBorderEnd(0x70d4a226);
                event.setBackground(0xF01a1405);
            } else if (armor.armor_level == 7) {
                //event.setBorderStart(RainbowColor.getRainbowColor_Spec_Level7_Up());
                //event.setBorderEnd(RainbowColor.getRainbowColor_Spec_Level7_Down());
                //event.setBackground((new RainbowColor().getRainbowColor())& 0xF0777777);
                event.setBorderStart(0x00C9C9C9);
                event.setBorderEnd(0x00797979);
                event.setBackground(0x0018181a);
            } else if (armor.armor_level == 8) {
                event.setBorderStart(0xA0571a25);
                event.setBorderEnd(0x7040131b);
                event.setBackground(0xF01a080b);
            }
        }
    }

}
