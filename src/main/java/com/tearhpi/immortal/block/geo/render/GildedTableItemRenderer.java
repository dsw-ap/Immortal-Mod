package com.tearhpi.immortal.block.geo.render;

import com.tearhpi.immortal.block.geo.model.EnhancementTableItemModel;
import com.tearhpi.immortal.block.geo.model.GildedTableItemModel;
import com.tearhpi.immortal.item.custom.geoitem.EnhancementTableItem;
import com.tearhpi.immortal.item.custom.geoitem.GildedTableItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GildedTableItemRenderer extends GeoItemRenderer<GildedTableItem> {
    public GildedTableItemRenderer() {
        super(new GildedTableItemModel());
    }
}
