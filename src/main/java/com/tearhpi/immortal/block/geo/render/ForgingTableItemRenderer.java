package com.tearhpi.immortal.block.geo.render;

import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.block.geo.model.ForgingTableItemModel;
import com.tearhpi.immortal.block.geo.model.ForgingTableModel;
import com.tearhpi.immortal.item.custom.geoitem.ForgingTableItem;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ForgingTableItemRenderer extends GeoItemRenderer<ForgingTableItem> {
    public ForgingTableItemRenderer() {
        super(new ForgingTableItemModel());
    }
}
