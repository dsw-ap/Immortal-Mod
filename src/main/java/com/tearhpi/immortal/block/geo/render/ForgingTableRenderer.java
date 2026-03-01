package com.tearhpi.immortal.block.geo.render;

import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.block.geo.model.EnhancementTableModel;
import com.tearhpi.immortal.block.geo.model.ForgingTableModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ForgingTableRenderer extends GeoBlockRenderer<ForgingTableBlockEntity> {
    public ForgingTableRenderer(BlockEntityRendererProvider.Context context) {
        super(new ForgingTableModel());
    }
}
