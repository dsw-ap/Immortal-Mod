package com.tearhpi.immortal.block.geo.render;

import com.tearhpi.immortal.block.custom.GildedTable;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import com.tearhpi.immortal.block.geo.model.EnhancementTableModel;
import com.tearhpi.immortal.block.geo.model.GildedTableModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GildedTableRenderer extends GeoBlockRenderer<GildedTableBlockEntity> {
    public GildedTableRenderer(BlockEntityRendererProvider.Context context) {
        super(new GildedTableModel());
    }
}
