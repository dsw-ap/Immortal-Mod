package com.tearhpi.immortal.item.custom.armor;

import com.tearhpi.immortal.item.custom.CustomArmorItem;
import com.tearhpi.immortal.item.geo.render.Test2ArmorItemRenderer;
import com.tearhpi.immortal.item.geo.render.TestArmorItemRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;


import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class Test2ArmorItem extends CustomArmorItem {
    public Test2ArmorItem(ArmorMaterial material, ArmorItem.Type type,
                          int armor_level, int armor_suit_number, Properties props) {
        // 若上游需要 Holder，可 wrapAsHolder；若上游直接要 ArmorMaterial，则把 super(...) 换成对应签名即可
        super(material, type, armor_level, armor_suit_number, props);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new Test2ArmorItemRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }
}
