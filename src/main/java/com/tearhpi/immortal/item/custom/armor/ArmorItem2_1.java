package com.tearhpi.immortal.item.custom.armor;

import com.tearhpi.immortal.item.custom.CustomArmorItem;
import com.tearhpi.immortal.item.geo.render.Test2ArmorItemRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class ArmorItem1_3 extends CustomArmorItem {
    public ArmorItem1_3(ArmorMaterial material, Type type,
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
