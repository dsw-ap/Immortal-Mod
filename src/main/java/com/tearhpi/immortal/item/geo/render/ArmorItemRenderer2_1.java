package com.tearhpi.immortal.item.geo.render;

import com.tearhpi.immortal.item.custom.armor.ArmorItem1_1;
import com.tearhpi.immortal.item.geo.model.ArmorItemModel1_1;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ArmorItemRenderer1_3 extends GeoArmorRenderer<ArmorItem1_1> {
    public ArmorItemRenderer1_3() {
        super(new ArmorItemModel1_1());
    }
}
