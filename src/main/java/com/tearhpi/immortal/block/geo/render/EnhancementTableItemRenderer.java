package com.tearhpi.immortal.block.geo.render;

import com.tearhpi.immortal.block.geo.model.EnhancementTableItemModel;
import com.tearhpi.immortal.item.custom.geoitem.EnhancementTableItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class EnhancementTableItemRenderer extends GeoItemRenderer<EnhancementTableItem> {
    public EnhancementTableItemRenderer() {
        super(new EnhancementTableItemModel());
    }
}
