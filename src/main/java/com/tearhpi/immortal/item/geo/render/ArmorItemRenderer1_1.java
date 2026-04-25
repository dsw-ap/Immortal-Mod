package com.tearhpi.immortal.item.geo.render;

import com.tearhpi.immortal.item.custom.armor.Test2ArmorItem;
import com.tearhpi.immortal.item.custom.armor.TestArmorItem;
import com.tearhpi.immortal.item.geo.model.Test2ArmorItemModel;
import com.tearhpi.immortal.item.geo.model.TestArmorItemModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class Test2ArmorItemRenderer extends GeoArmorRenderer<Test2ArmorItem> {
    public Test2ArmorItemRenderer() {
        super(new Test2ArmorItemModel());
    }
}
