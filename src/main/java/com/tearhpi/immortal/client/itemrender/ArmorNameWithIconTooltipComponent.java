package com.tearhpi.immortal.client.itemrender;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.client.textrender.RainbowColor;
import com.tearhpi.immortal.client.textrender.StarParticle;
import com.tearhpi.immortal.item.custom.CustomArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 物品UI主渲染逻辑
 */
@OnlyIn(Dist.CLIENT)
public class ArmorNameWithIconTooltipComponent implements ClientTooltipComponent {
    private final ItemStack stack;
    private final Component originalName;
    private final int whole_width;
    private final int whole_height;
    private final int whole_x;
    private final int whole_y;
    private final int armor_level;
    private final int armor_suit_number;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final LinkedList<String> armor_skill_passive;
    private final LinkedList<String> armor_skill_active;
    private final LinkedList<String> armor_skill_attach;
    private final LinkedList<LinkedList<String>> armor_skill_suit;
    public final int attached_attribute_rune_max; //武器附加属性符文最大值
    public final int attached_attribute_rune; //武器附加属性符文
    public final List<StarParticle> stars; //神话特殊-背景星星闪烁
    /*
    public record ArmorItemImageTooltipData(ItemStack stack, Component originalName,
                                            int whole_width, int whole_height,
                                            int whole_x, int whole_y,
                                            int armor_level, int armor_suit_number,
                                            Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers,
                                            LinkedList<String> armor_skill_passive,
                                            LinkedList<String> armor_skill_active,
                                            LinkedList<String> armor_skill_attach,
                                            LinkedList<String> armor_skill_suit,
                                            int attached_attribute_rune_max, int attached_attribute_rune,
                                            List<StarParticle> stars) implements TooltipComponent {
    }
     */

    private final float text_scale_1 = 0.8f;
    int scale = 2;
    int iconSize = 16;
    int scaledSize = iconSize * scale;

    public ArmorNameWithIconTooltipComponent(ArmorItemImageTooltipData data) {
        this.stack = data.stack();
        this.originalName = data.originalName();
        this.whole_width = data.whole_width();
        this.whole_height = data.whole_height();
        this.whole_x = data.whole_x();
        this.whole_y = data.whole_y();
        this.armor_level = data.armor_level();
        this.armor_suit_number = data.armor_suit_number();
        this.attributeModifiers = data.attributeModifiers();
        this.armor_skill_passive = data.armor_skill_passive();
        this.armor_skill_active = data.armor_skill_active();
        this.armor_skill_attach = data.armor_skill_attach();
        this.armor_skill_suit = data.armor_skill_suit();
        this.attached_attribute_rune_max = data.attached_attribute_rune_max();
        this.attached_attribute_rune = data.attached_attribute_rune();
        this.stars = data.stars();
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics gui) {
        long ticks = Minecraft.getInstance().level.getGameTime();
        int whole_width_inner = whole_width + 2;
        int whole_height_inner = whole_height + 2;
        int whole_x_inner = whole_x - 1;
        int whole_y_inner = whole_y - 1;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        // ---- 四角-上下装饰 ----
        renderCorner(gui, whole_x_inner, whole_y_inner, whole_width_inner, whole_height_inner,x,y);
        // ---- 渲染基底纯色层 ----
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.4F);
        ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/base_under_item.png");
        gui.pose().pushPose();
        gui.pose().translate(x, y - iconSize * (scale - 1) + 4, 0);
        if (this.armor_level != 7) {
            gui.blit(BASE_TEXTURE, 0 - 1, 0 - 1, 0, 0, 34, 34, 34, 34);
        }
        gui.pose().popPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        // ---- 渲染底部辉光层 ----
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float[] backgroundRGB = intToRGBFloats(getNameColor());
        RenderSystem.setShaderColor(backgroundRGB[0], backgroundRGB[1], backgroundRGB[2], 0.15F);
        ResourceLocation Mask_Texture = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/mask_under_item.png");
        gui.pose().pushPose();
        gui.pose().translate(whole_x + 1, whole_y + 1, 0);
        if (this.armor_level != 7) {
            gui.blit(Mask_Texture, 0, 0, 0, 0, whole_width - 2, whole_height - 2, whole_width - 2, whole_height - 2);
        }
        gui.pose().popPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        // ---- 渲染底部光晕层 ----
        renderCircle(gui, x, y);
        // ---- 渲染底部放射层 ----
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/background_under_item.png");
        //获取相关颜色
        RenderSystem.setShaderColor(backgroundRGB[0], backgroundRGB[1], backgroundRGB[2], 0.65f);
        gui.pose().pushPose();
        gui.pose().translate(x + scaledSize / 2f, y + scaledSize / 2f - 12, 0);
        // 动态旋转 + 脉冲缩放
        float rotation = (ticks % 360) * 0.5f;
        float pulsate = (float) (1.15f + 0.1f * Math.sin(ticks * 0.2f));
        gui.pose().scale(pulsate, pulsate, 1.0f);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(rotation));
        // 绘制纹理：围绕中心渲染
        gui.blit(GLOW_TEXTURE, -scaledSize / 2, -scaledSize / 2, 0, 0, 32, 32, 32, 32);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.pose().popPose();
        // ---- 渲染放大物品图标 ----
        gui.pose().pushPose();
        gui.pose().translate(x, y - iconSize * (scale - 1) + 3, 0);
        gui.pose().scale(scale, scale, 1.0f);
        gui.renderItem(stack, 0, 0);
        gui.renderItemDecorations(font, stack, 0, 0);
        gui.pose().popPose();
        // ---- 渲染名称 ----
        int textX = x + scaledSize + 4;
        int textY = y + (scaledSize - font.lineHeight) / 2 - iconSize * (scale - 1) - 4;
        //第一行文字
        //检测是否为神话级
        if (this.armor_level == 7) {
            RainbowColor rainbowColor = new RainbowColor(gui, originalName, font, textX, textY);
            rainbowColor.draw();
        } else {
            gui.drawString(font, originalName, textX, textY, getNameColor());
        }
        //第二行文字
        gui.pose().pushPose();
        gui.pose().translate(textX, textY - iconSize * (text_scale_1 - 1) + 9.5, 0);
        gui.pose().scale(text_scale_1, text_scale_1, 1.0f);
        gui.drawString(font, getArmorLevelComponent().copy(), 0, 0, 0x555555);
        //第三行文字
        gui.pose().scale(1.0f, 1.0f, 1.0f);
        renderFlowerText(gui,font, 0, 11);
        gui.pose().popPose();

        //---- 底分割线渲染 ----
        if (this.armor_level != 7) {
            fillGradientHorizontal(gui, whole_x + 2, whole_y + 38, (whole_width / 2) - 2, 1, getNameColor_transparent(), getNameColor());
            fillGradientHorizontal(gui, whole_x + (whole_width / 2), whole_y + 38, (whole_width / 2) - 2, 1, getNameColor(), getNameColor_transparent());
        } else {
            fillGradientHorizontal(gui, whole_x + 2, whole_y + 38, (whole_width / 2) - 2-6, 1, getNameColor_transparent(), getNameColor());
            fillGradientHorizontal(gui, whole_x + (whole_width / 2)-6, whole_y + 38, (whole_width / 2) - 2-6, 1, getNameColor(), getNameColor_transparent());
        }

        //---- 属性 渲染 ----
        int textX_AttributeRender = x;
        int textY_AttributeRender = y + 24;
        for (Map.Entry<Attribute, AttributeModifier> entry : attributeModifiers.entries()) {
            // 获取 modifier 信息
            //ResourceLocation attributeId = entry.getKey().unwrapKey().map(ResourceKey::location).orElse(ResourceLocation.fromNamespaceAndPath("minecraft", "unknown"));
            ResourceLocation attributeId = ForgeRegistries.ATTRIBUTES.getKey(entry.getKey());
            double amount = entry.getValue().getAmount();
            //匹配 绘制
            if (attributeId.toString().equals("immortal:immortal_defense")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.defense", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:immortal_dodge")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.dodge", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:player_extra_protection")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.extra_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("minecraft:knockback_resistance")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.knockback_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:element_resistance_fire")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.fire_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:element_resistance_water")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.water_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:element_resistance_earth")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.earth_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:element_resistance_air")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.air_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:element_resistance_light")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.light_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:element_resistance_darkness")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.darkness_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            if (attributeId.toString().equals("immortal:player_debuff_resistance")) {
                gui.drawString(font, Component.translatable("tooltips.name.generic.debuff_protection", String.format("%.1f",amount)), textX_AttributeRender, textY_AttributeRender, 0x55FFFF);
            }
            textY_AttributeRender += 10;
        }
        //---- 底分割线2渲染 ----
        if (this.armor_level != 7) {
            fillGradientHorizontal(gui, whole_x + 2, textY_AttributeRender - 1, (whole_width / 2) - 2, 1, getNameColor_transparent(), getNameColor());
            fillGradientHorizontal(gui, whole_x + (whole_width / 2), textY_AttributeRender - 1, (whole_width / 2) - 2, 1, getNameColor(), getNameColor_transparent());
        } else {
            fillGradientHorizontal(gui, whole_x + 2, textY_AttributeRender - 1, (whole_width / 2) - 2-6, 1, getNameColor_transparent(), getNameColor());
            fillGradientHorizontal(gui, whole_x + (whole_width / 2)-6, textY_AttributeRender - 1, (whole_width / 2) - 2-6, 1, getNameColor(), getNameColor_transparent());
        }
        textY_AttributeRender += 1;
        if (!armor_skill_passive.isEmpty() || !armor_skill_active.isEmpty()) {
            //技能渲染
            for (String info : armor_skill_passive) {
                gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender, 0xFF55FF);
                textY_AttributeRender += 10;
            }
            for (String info : armor_skill_active) {
                gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender, 0xE100E1);
                textY_AttributeRender += 10;
            }
            //---- 底分割线3渲染 ----
            if (this.armor_level != 7) {
                fillGradientHorizontal(gui, whole_x + 2, textY_AttributeRender - 1, (whole_width / 2) - 2, 1, getNameColor_transparent(), getNameColor());
                fillGradientHorizontal(gui, whole_x + (whole_width / 2), textY_AttributeRender - 1, (whole_width / 2) - 2, 1, getNameColor(), getNameColor_transparent());
            } else {
                fillGradientHorizontal(gui, whole_x + 2, textY_AttributeRender - 1, (whole_width / 2) - 2-6, 1, getNameColor_transparent(), getNameColor());
                fillGradientHorizontal(gui, whole_x + (whole_width / 2)-6, textY_AttributeRender - 1, (whole_width / 2) - 2-6, 1, getNameColor(), getNameColor_transparent());
            }
            textY_AttributeRender += 1;
        }
        //---- 附加词条渲染 ----
        /*
        for (String info : weapon_skill_attached) {
            gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender, 0xFF55FF);
            textY_AttributeRender += 10;
        }
         */
        if (this.armor_suit_number == 0) {
            gui.drawString(font, Component.translatable("tooltips.armor_suit_none"), textX_AttributeRender, textY_AttributeRender,0xFFFFFF);
            textY_AttributeRender += 10;
        } else {
            if (!Screen.hasControlDown()) {
                gui.drawString(font, Component.translatable("tooltips.armor_suit_lore"), textX_AttributeRender, textY_AttributeRender,0xFFFFFF);
                textY_AttributeRender += 10;
            } else {
                //玩家身上相应的盔甲件数获取
                int number_of_suit_on_player = 0;
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
                    Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
                    Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
                    Item feet = player.getItemBySlot(EquipmentSlot.FEET).getItem();
                    //数值匹配
                    if (head instanceof CustomArmorItem) {
                        if (((CustomArmorItem) head).armor_suit_number == this.armor_suit_number) {
                            number_of_suit_on_player++;
                        }
                    }
                    if (chest instanceof CustomArmorItem) {
                        if (((CustomArmorItem) chest).armor_suit_number == this.armor_suit_number) {
                            number_of_suit_on_player++;
                        }
                    }
                    if (legs instanceof CustomArmorItem) {
                        if (((CustomArmorItem) legs).armor_suit_number == this.armor_suit_number) {
                            number_of_suit_on_player++;
                        }
                    }
                    if (feet instanceof CustomArmorItem) {
                        if (((CustomArmorItem) feet).armor_suit_number == this.armor_suit_number) {
                            number_of_suit_on_player++;
                        }
                    }
                }
                //名字渲染
                for (String info : armor_skill_suit.get(0)) {
                    gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,getNameColor());
                    textY_AttributeRender += 10;
                }
                //[2]套装渲染
                for (String info : armor_skill_suit.get(1)) {
                    if (number_of_suit_on_player >= 2) {
                        gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,getNameColor());
                    } else {
                        gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,0x555555);
                    }
                    textY_AttributeRender += 10;
                }
                //[3]套装渲染
                for (String info : armor_skill_suit.get(2)) {
                    if (number_of_suit_on_player >= 3) {
                        gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,getNameColor());
                    } else {
                        gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,0x555555);
                    }
                    textY_AttributeRender += 10;
                }
                //[4]套装渲染
                for (String info : armor_skill_suit.get(3)) {
                    if (number_of_suit_on_player >= 4) {
                        gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,getNameColor());
                    } else {
                        gui.drawString(font, Component.translatable(info), textX_AttributeRender, textY_AttributeRender,0x555555);
                    }
                    textY_AttributeRender += 10;
                }
            }
        }

        RenderSystem.disableBlend();
    }
    @Override
    public int getHeight() {
        int text_line_count = 0;
        for (Map.Entry<Attribute, AttributeModifier> entry : attributeModifiers.entries()) {text_line_count += 10;}
        if (!armor_skill_passive.isEmpty() || !armor_skill_active.isEmpty()) {
            for (String info : armor_skill_passive) {
                text_line_count += 10;
            }
            for (String info : armor_skill_active) {
                text_line_count += 10;
            }
            text_line_count += 1;
        }
        for (String info : armor_skill_attach) {text_line_count += 10;}
        if (this.armor_suit_number == 0) {
            text_line_count += 10;
        } else {
            if (!Screen.hasControlDown()) {
                text_line_count += 10;
            } else {
                //名字渲染
                for (LinkedList<String> info : armor_skill_suit) {
                    for (String info2 : info) {text_line_count += 10;}
                }
            }
        }
        if (armor_level == 7) {
            return 33 + text_line_count;
        }
        return 25 + text_line_count; // 2倍图标高度
    }

    @Override
    public int getWidth(Font font){
        int skill_width_max = Math.max(maxLengthInLinkedListString(armor_skill_passive,font),maxLengthInLinkedListString(armor_skill_active,font));
        int skill_width_max_ = 0;
        if (!Screen.hasControlDown()){
            skill_width_max_ = skill_width_max;
        } else {
            skill_width_max_ = Math.max(maxLengthInDoubleLinkedListString(armor_skill_suit, font), skill_width_max);
        }
        int width_max = Math.max(32 + 4 + font.width(originalName),Math.max(
                        skill_width_max_, font.width("tooltips.armor_suit_lore"))
        );
        if (armor_level == 7) {
            return 11 + width_max;
        }
        return width_max; // 图标宽 + 间距 + 文本宽
    }

    //--------- 附带方法 ---------
    //获取品质文本
    private Component getArmorLevelComponent() {
        if (this.armor_level == 1) {
            return Component.translatable("tooltips.weapon_level_1");
        } else if (this.armor_level == 2) {
            return Component.translatable("tooltips.weapon_level_2");
        } else if (this.armor_level == 3) {
            return Component.translatable("tooltips.weapon_level_3");
        } else if (this.armor_level == 4) {
            return Component.translatable("tooltips.weapon_level_4");
        } else if (this.armor_level == 5) {
            return Component.translatable("tooltips.weapon_level_5");
        } else if (this.armor_level == 6) {
            return Component.translatable("tooltips.weapon_level_6");
        } else if (this.armor_level == 7) {
            return Component.translatable("tooltips.weapon_level_7");
        } else if (this.armor_level == 8) {
            return Component.translatable("tooltips.weapon_level_8");
        }
        return null;
    }
    //获取武器攻击加成
    /*
    private Component getWeaponAttackAttribute() {
        if (this.weapon_attribute.getattributeName().equals("fire")) {
            return Component.translatable("tooltips.weapon_attack_attribute_1");
        } else if (this.weapon_attribute.getattributeName().equals("water")) {
            return Component.translatable("tooltips.weapon_attack_attribute_2");
        } else if (this.weapon_attribute.getattributeName().equals("earth")) {
            return Component.translatable("tooltips.weapon_attack_attribute_3");
        } else if (this.weapon_attribute.getattributeName().equals("air")) {
            return Component.translatable("tooltips.weapon_attack_attribute_4");
        } else if (this.weapon_attribute.getattributeName().equals("light")) {
            return Component.translatable("tooltips.weapon_attack_attribute_5");
        } else if (this.weapon_attribute.getattributeName().equals("darkness")) {
            return Component.translatable("tooltips.weapon_attack_attribute_6");
        }
        return null;
    }
     */
    //获取品质和武器名字渲染颜色
    private int getNameColor() {
        if (this.armor_level == 1) {
            return 0xFFFFFFFF;
        } else if (this.armor_level == 2) {
            return 0xFF55FFFF;
        } else if (this.armor_level == 3) {
            return 0xFF9A5CC6;
        } else if (this.armor_level == 4) {
            return 0xFFFF55FF;
        } else if (this.armor_level == 5) {
            return 0xFFFFAA00;
        } else if (this.armor_level == 6) {
            return 0xFFFFFF55;
        } else if (this.armor_level == 7) {
            return new RainbowColor().getRainbowColor();
        } else if (this.armor_level == 8) {
            return 0xFFAA0000;
        }
        return 0;
    }
    private int getNameColor_transparent() {
        if (this.armor_level == 1) {
            return 0x00FFFFFF;
        } else if (this.armor_level == 2) {
            return 0x0055FFFF;
        } else if (this.armor_level == 3) {
            return 0x009A5CC6;
        } else if (this.armor_level == 4) {
            return 0x00FF55FF;
        } else if (this.armor_level == 5) {
            return 0x00FFAA00;
        } else if (this.armor_level == 6) {
            return 0x00FFFF55;
        } else if (this.armor_level == 7) {
            return new RainbowColor().getRainbowColor_transparent();
        } else if (this.armor_level == 8) {
            return 0x00AA0000;
        }
        return 0;
    }
    //RGB转int(背景图渲染)
    public static float[] intToRGBFloats(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        return new float[] {
                r / 255.0f,
                g / 255.0f,
                b / 255.0f
        };
    }
    //渲染底部光圈
    public void renderCircle(GuiGraphics gui, float x, float y) {
        ResourceLocation CIRCLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/circle_under_item.png");

        // 透明混合设置
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        // 获取颜色 (RGB, Alpha为后面动态决定)
        float[] rgb = intToRGBFloats(getNameColor());

        // 获取时间（ticks）
        long ticks = Minecraft.getInstance().level.getGameTime();

        // 光圈周期（多少tick出现一个）
        int interval = 40; // 一圈持续时间
        float progress = (ticks % interval) / (float) interval;

        // 计算大小范围（从小到大）
        float minScale = 0.5f;
        float maxScale = 2.0f;
        float scale = minScale + (maxScale - minScale) * progress;

        // 计算透明度（开始亮，慢慢淡出）
        float alpha = 1.0f - progress; // 可换成 (1 - progress)^2

        RenderSystem.setShaderColor(rgb[0], rgb[1], rgb[2], alpha);

        gui.pose().pushPose();
        gui.pose().translate(x + scaledSize / 2f, y + scaledSize / 2f - 12, 0);
        gui.pose().scale(scale, scale, 1.0f);

        // 渲染纹理
        gui.blit(CIRCLE_TEXTURE, -16, -16, 0, 0, 32, 32, 32, 32);
        gui.pose().popPose();

        // 恢复颜色状态
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }
    //水平分割线
    public static void fillGradientHorizontal(GuiGraphics gui, int x, int y, int width, int height, int leftColor, int rightColor) {
        for (int i = 0; i < width; i++) {
            float t = i / (float) width;

            int r = (int) Mth.lerp(t, FastColor.ARGB32.red(leftColor),   FastColor.ARGB32.red(rightColor));
            int g = (int) Mth.lerp(t, FastColor.ARGB32.green(leftColor), FastColor.ARGB32.green(rightColor));
            int b = (int) Mth.lerp(t, FastColor.ARGB32.blue(leftColor),  FastColor.ARGB32.blue(rightColor));
            int a = (int) Mth.lerp(t, FastColor.ARGB32.alpha(leftColor), FastColor.ARGB32.alpha(rightColor));

            int argb = FastColor.ARGB32.color(a, r, g, b);

            gui.fill(x + i, y, x + i + 1, y + height, argb);
        }
    }
    //渲染四角-顶底
    private void renderCorner(GuiGraphics gui,int whole_x_inner,int whole_y_inner,int whole_width_inner,int whole_height_inner,int x,int y) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        //初始化材质 默认为普通
        ResourceLocation FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/1_corners.png");;
        ResourceLocation ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/1_top_bottom.png");;
        if (this.armor_level == 1) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/1_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/1_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
        } else if (this.armor_level == 2) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/2_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/2_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
        } else if (this.armor_level == 3) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/3_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/3_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
        } else if (this.armor_level == 4) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/4_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/4_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
            gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner, 0, 0, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner, 16, 0, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner+whole_height_inner-16, 0, 16, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner+whole_height_inner-16, 16, 16, 16, 16, 32, 32);
            gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y-10, 0, 0, 48, 16, 48, 32);
            gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y+whole_height_inner-7, 0, 16, 48, 16, 48, 32);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
            return;
        } else if (this.armor_level == 5) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/5_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/5_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
            gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner, 0, 0, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner, 16, 0, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner+whole_height_inner-16, 0, 16, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner+whole_height_inner-16, 16, 16, 16, 16, 32, 32);
            gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y-10, 0, 0, 48, 16, 48, 32);
            gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y+whole_height_inner-7, 0, 16, 48, 16, 48, 32);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
            return;
        } else if (this.armor_level == 6) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/6_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/6_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
            gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner, 0, 0, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner, 16, 0, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner+whole_height_inner-16, 0, 16, 16, 16, 32, 32);
            gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner+whole_height_inner-16, 16, 16, 16, 16, 32, 32);
            gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y-10, 0, 0, 48, 16, 48, 32);
            gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y+whole_height_inner-7, 0, 16, 48, 16, 48, 32);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
            return;
        } else if (this.armor_level == 7) {
            ResourceLocation ALL = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/7_all.png");
            ResourceLocation MOONLIGHT = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/7_moonlight.png");
            ResourceLocation NEBULA = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/7_nebula__.png");

            //float[] backgroundRGB_ = intToRGBFloats(RainbowColor.getRainbowColor_Spec_Level7_Down());
            //RenderSystem.setShaderColor(backgroundRGB_[0], backgroundRGB_[1], backgroundRGB_[2], 1.0F);
            //底层
            //gui.fill(whole_x_inner,whole_y_inner-26,whole_x_inner+whole_width_inner-12,whole_y_inner+whole_height_inner+26,0xFF000000);
            //---星云---
            Minecraft mc = Minecraft.getInstance();
            float time = (mc.level != null ? mc.level.getGameTime() : System.currentTimeMillis() / 50L) % 200;
            float scroll = (time % 200) / 200f;
            // 渐变运动效果，偏移UV坐标实现“动感”
            float[] backgroundRGB = intToRGBFloats(RainbowColor.getSinusoidalGradientColor());
            RenderSystem.setShaderColor(backgroundRGB[0], backgroundRGB[1], backgroundRGB[2], 1.0F);
            //gui.blit(NEBULA,whole_x_inner, whole_y_inner-26, whole_width_inner-12, whole_height_inner+26,
            //       (int)(scroll * 256), 0, whole_width_inner-12, whole_height_inner+26, 256, 256);
            gui.blit(NEBULA,whole_x_inner, whole_y_inner-3, whole_width_inner-12, whole_height_inner+3,
                    (int)(scroll * 256), 0, whole_width_inner-12, whole_height_inner+26, 256, 256);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            //---星星---
            ResourceLocation STAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/7_star.png");
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, STAR_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            if (stars != null) {
                List<StarParticle> starCopy = new ArrayList<>(stars);
            for (StarParticle star : starCopy) {
                if (star != null) {
                    float alpha = star.alpha;
                    RenderSystem.setShaderColor(0.713f, 0.458f, 0.835f, alpha);
                    gui.pose().pushPose();
                    gui.pose().translate(star.x, star.y, 0);
                    gui.pose().scale((float) (star.size*0.4), (float) (star.size*0.4), 1.0f);
                    gui.blit(STAR_TEXTURE, 0,0, 0, 0, 13, 13,13, 13);
                    gui.pose().popPose();
                }
            }}
            RenderSystem.setShaderColor(1, 1, 1, 1);
            //特殊 武器底
            ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/base_under_item.png");
            gui.pose().pushPose();
            gui.pose().translate(x, y - iconSize * (scale - 1) + 4, 0);
            gui.blit(BASE_TEXTURE, 0 - 1, 0 - 1, 0, 0, 34, 34, 34, 34);
            gui.pose().popPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            //---四边线---
            //顶底
            gui.pose().pushPose();
            float factor = (float) Math.max((whole_width_inner - 43), 0) / 46.0f;
            //gui.pose().translate(whole_x_inner-11+28, whole_y_inner-27,0);
            gui.pose().translate(whole_x_inner-11+28, whole_y_inner-4,0);
            gui.pose().scale(factor, 1.0f, 1.0f);
            gui.blit(ALL, 0,0, 33, 32, 46, 5, 112, 128);
            gui.pose().popPose();
            gui.pose().pushPose();
            float factor_ = (float) Math.max((whole_width_inner-13), 0) / 17.0f;
            gui.pose().translate(whole_x_inner-9+13, whole_y_inner+whole_height_inner-19+8+2+1,0);
            gui.pose().scale(factor_, 1.0f, 1.0f);
            gui.blit(ALL, 0,0, 16, 113, 17, 8, 112, 128);
            gui.pose().popPose();

            gui.blit(ALL, whole_x_inner+whole_width_inner/2-29, whole_y_inner+whole_height_inner-19+8+1+1, 33, 112, 47, 9, 112, 128);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
            //左右
            gui.pose().pushPose();
            float factor__ = (float) Math.max((whole_height_inner)-8, 0) / 20.0f;
            gui.pose().translate(whole_x_inner-7, whole_y_inner,0);
            gui.pose().scale(1.0f,factor__,1.0f);
            gui.blit(ALL, 0,0, 4, 76, 9, 20, 112, 128);
            gui.pose().popPose();
            gui.pose().pushPose();
            gui.pose().translate(whole_x_inner+whole_width_inner-2-13, whole_y_inner,0);
            gui.pose().scale(1.0f, factor__, 1.0f);
            gui.blit(ALL, 0,0, 99, 76, 9, 20, 112, 128);
            gui.pose().popPose();
            //---四角---
            //gui.blit(ALL, whole_x_inner-11, whole_y_inner-27, 0, 32, 28, 43, 112, 128);
            gui.blit(ALL, whole_x_inner-11, whole_y_inner-4, 0, 32, 28, 43, 112, 128);
            gui.blit(ALL, whole_x_inner+whole_width_inner-17, whole_y_inner+whole_height_inner-19+8+1, 97, 102, 13, 10, 112, 128);
            //gui.blit(ALL, whole_x_inner+whole_width_inner-17-13, whole_y_inner-27, 84, 32, 28, 43, 112, 128);
            gui.blit(ALL, whole_x_inner+whole_width_inner-17-13, whole_y_inner-4, 84, 32, 28, 43, 112, 128);
            gui.blit(ALL, whole_x_inner-9, whole_y_inner+whole_height_inner-19+8+1, 2, 102, 13, 10, 112, 128);
            //RainbowColor rainbowColor = new RainbowColor();
            //float[] backgroundRGB_ = intToRGBFloats(rainbowColor.getRainbowColor());
            //RenderSystem.setShaderColor(backgroundRGB_[0], backgroundRGB_[1], backgroundRGB_[2], 1.0F);
            //gui.blit(MOONLIGHT, whole_x_inner+(whole_width_inner-11)/2-40, whole_y_inner-23, 0, 0, 79, 31, 80, 32);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
            return;
        } else if (this.armor_level == 8) {
            FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/8_corners.png");
            ICON_TOP_BOTTOM = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/8_top_bottom.png");
            RenderSystem.setShaderColor(1f, 1f, 1f, 1.0f);
        }
        gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner, 0, 0, 16, 16, 32, 32);
        gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner, 16, 0, 16, 16, 32, 32);
        gui.blit(FRAME_TEXTURE, whole_x_inner, whole_y_inner+whole_height_inner-16, 0, 16, 16, 16, 32, 32);
        gui.blit(FRAME_TEXTURE, whole_x_inner+whole_width_inner-16, whole_y_inner+whole_height_inner-16, 16, 16, 16, 16, 32, 32);
        gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y-11, 0, 0, 48, 16, 48, 32);
        gui.blit(ICON_TOP_BOTTOM, whole_x_inner+whole_width_inner/2-24, whole_y+whole_height_inner-7, 0, 16, 48, 16, 48, 32);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        return;

    }
    //获得String字符串中最长的元素的长度
    private int maxLengthInLinkedListString(LinkedList<String> list,Font font) {
        int return_value = 0;
        for (String info : list) {
            return_value = Math.max(font.width(Component.translatable(info)),return_value);
        }
        return return_value;
    }
    //获得Linkedlist<String>字符串中最长的元素的长度
    private int maxLengthInDoubleLinkedListString(LinkedList<LinkedList<String>> list,Font font) {
        int return_value = 0;
        for (LinkedList<String> info : list) {
            for (String info1 : info) {
                return_value = Math.max(font.width(Component.translatable(info1)),return_value);
            }
        }
        return return_value;
    }
    private int maxLengthInLinkedListComponent(LinkedList<Component> list,Font font) {
        int return_value = 0;
        for (Component info : list) {
            return_value = Math.max(font.width(info),return_value);
        }
        return return_value;
    }
    //绘制花形图案
    private void renderFlowerText(GuiGraphics gui,Font font,int x,int y) {
        //绘制属性词条
        //属性词条 绘制attached_attribute_rune_max-attached_attribute_rune个灰花❁,attached_attribute_rune
        //附加属性
        for (int i=0;i<attached_attribute_rune;i++) {
            gui.drawString(font, Component.literal("❁"), x, y, 0xFF5555);
            x += 8;
        }
        for (int i=0;i<attached_attribute_rune_max-attached_attribute_rune;i++) {
            gui.drawString(font, Component.literal("❁"), x, y, 0x555555);
            x += 8;
        }
    }
    //神话特殊-星星闪烁
    public void renderStars(GuiGraphics gui) {
        // 1.20.1 用构造函数而不是 fromNamespaceAndPath
        ResourceLocation STAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(Immortal.MODID, "textures/tooltip/weapon_side/7_star.png");

        // 绑定着色器与纹理
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, STAR_TEXTURE);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // 取出当前 GUI 的矩阵（1.20.1 的 BufferBuilder 需显式传入矩阵）
        Matrix4f pose = gui.pose().last().pose();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (StarParticle star : stars) {
            float alpha = star.alpha;
            float size  = star.size * 9f; // 贴图是 9x9
            float x     = star.x;
            float y     = star.y;

            // 每颗星各自的透明度
            RenderSystem.setShaderColor(1f, 1f, 1f, alpha);

            // 1.20.1 的写法：vertex(pose, ...).uv(...).endVertex()
            buffer.vertex(pose, x,         y + size, 0).uv(0f, 1f).endVertex();
            buffer.vertex(pose, x + size,  y + size, 0).uv(1f, 1f).endVertex();
            buffer.vertex(pose, x + size,  y,        0).uv(1f, 0f).endVertex();
            buffer.vertex(pose, x,         y,        0).uv(0f, 0f).endVertex();
        }

        // 1.20.1：end() -> RenderedBuffer，再用 BufferUploader.drawWithShader
        BufferBuilder.RenderedBuffer rendered = buffer.end();
        BufferUploader.drawWithShader(rendered);

        // 复位状态
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }
}
