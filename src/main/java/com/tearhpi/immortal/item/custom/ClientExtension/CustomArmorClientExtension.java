package com.tearhpi.immortal.item.custom.ClientExtension;

import com.tearhpi.immortal.client.itemrender.ArmorItemImageTooltipData;
import com.tearhpi.immortal.client.itemrender.ItemImageTooltipData;
import com.tearhpi.immortal.client.textrender.StarParticle;
import com.tearhpi.immortal.item.custom.CustomArmorItem;
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
public class CustomArmorClientExtension implements IClientItemExtensions {


    private final CustomArmorItem item;
    private final List<StarParticle> stars = new CopyOnWriteArrayList<>();
    private final Random random = new Random();


    public CustomArmorClientExtension(CustomArmorItem item) {
        this.item = item;
    }

    public Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        updateStars();
        Component name = Component.translatable(stack.getItem().getDescriptionId(stack));
        return Optional.of(new ArmorItemImageTooltipData(
                stack,
                name, // 使用原版名称获取逻辑
                item.Tooltip_width,
                item.Tooltip_height,
                item.Tooltip_x,
                item.Tooltip_y,
                item.armor_level,
                item.armor_suit_number,
                item.attributeModifiers,
                item.SkillInfo_passive,
                item.SkillInfo_active,
                item.SkillInfo_attach,
                item.SkillInfo_suit,
                item.getAttrRuneMax(stack), item.getAttrRune(stack),
                stars
        ));
    }

    public void updateStars() {
        synchronized (stars) {
            List<StarParticle> toRemove = new ArrayList<>();
            for (StarParticle star : stars) {
                if (star.isDead()) toRemove.add(star);
            }
            stars.removeAll(toRemove);
        }

        if (Minecraft.getInstance().level != null
                && stars.size() < 20
                && Minecraft.getInstance().level.getGameTime() % 2 == 0) {
            float startX = item.Tooltip_x + random.nextFloat() * Math.max(1, (item.Tooltip_width - 12));
            float startY = (float) (item.Tooltip_y - 3 + Math.min(random.nextFloat(), 0.8) * (item.Tooltip_height + 3));
            float vx = (random.nextFloat() - 1.0f) * 0.05f;
            float vy = (random.nextFloat()) * 0.05f;
            float size = 0.5f + random.nextFloat() * 1.5f;
            int lifetime = 420 + random.nextInt(280);
            stars.add(new StarParticle(startX, startY, vx, vy, size, lifetime));
        }

        // tick 一份拷贝，避免并发修改
        new ArrayList<>(stars).forEach(StarParticle::tick);
    }
}
