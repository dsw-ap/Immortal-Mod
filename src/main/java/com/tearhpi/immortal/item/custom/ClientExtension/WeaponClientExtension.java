package com.tearhpi.immortal.item.custom.ClientExtension;

import com.tearhpi.immortal.client.itemrender.ItemImageTooltipData;
import com.tearhpi.immortal.client.textrender.StarParticle;
import com.tearhpi.immortal.item.custom.WeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@OnlyIn(Dist.CLIENT)
public class WeaponClientExtension implements IClientItemExtensions {


    private final WeaponItem item;
    private final List<StarParticle> stars = new CopyOnWriteArrayList<>();
    private final Random random = new Random();


    public WeaponClientExtension(WeaponItem item) {
        this.item = item;
    }

    public Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        updateStars();
        Component name;
        if (WeaponItem.getEnhanceLevel(stack) == 0) {
            name = Component.translatable(stack.getItem().getDescriptionId(stack));
        } else {
            name = Component.literal("+" + WeaponItem.getEnhanceLevel(stack) + " ").append(Component.translatable(stack.getItem().getDescriptionId(stack)));
        }


        return Optional.of(new ItemImageTooltipData(
                stack,
                name,
                item.Tooltip_width,
                item.Tooltip_height,
                item.Tooltip_x,
                item.Tooltip_y,
                item.weapon_level,
                item.weapon_type,
                item.attributeModifiers,
                item.attributeModifiers_base,
                item.weapon_attribute,
                item.SkillInfo_passive,
                item.SkillInfo_active,
                item.SkillInfo_Attached,
                WeaponItem.getAttrRuneMax(stack), WeaponItem.getAttrRune(stack),
                WeaponItem.getSkillRuneMax(stack), WeaponItem.getSkillRune(stack),
                WeaponItem.getEnhanceLevel(stack),
                WeaponItem.getForgingLevel(stack),
                WeaponItem.getForgingNum_1(stack),
                WeaponItem.getForgingNum_2(stack),
                WeaponItem.getGildedLevel(stack),
                stars
        ));
    }

    private void updateStars() {
        synchronized (stars) {
            stars.removeIf(StarParticle::isDead);
        }
        if (stars.size() < 20 && Minecraft.getInstance().level != null
                && Minecraft.getInstance().level.getGameTime() % 2 == 0) {


            float startX = item.Tooltip_x + random.nextFloat() * (item.Tooltip_width - 12);
            float startY = (float) (item.Tooltip_y - 3 + Math.min(random.nextFloat(), 0.8) * (item.Tooltip_height + 3));
            float vx = (random.nextFloat() - 1.0f) * 0.05f;
            float vy = (random.nextFloat()) * 0.05f;
            float size = 0.5f + random.nextFloat() * 1.5f;
            int lifetime = 420 + random.nextInt(280);


            stars.add(new StarParticle(startX, startY, vx, vy, size, lifetime));
        }
        new ArrayList<>(stars).forEach(StarParticle::tick);
    }
}
