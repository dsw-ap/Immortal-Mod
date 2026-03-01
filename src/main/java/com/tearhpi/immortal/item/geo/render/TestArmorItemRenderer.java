package com.tearhpi.immortal.item.geo.render;

import com.tearhpi.immortal.item.custom.armor.TestArmorItem;
import com.tearhpi.immortal.item.geo.model.TestArmorItemModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TestArmorItemRenderer extends GeoArmorRenderer<TestArmorItem> {
    public TestArmorItemRenderer() {
        super(new TestArmorItemModel());
    }
}
