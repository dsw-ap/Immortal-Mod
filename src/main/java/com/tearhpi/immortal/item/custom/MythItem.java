package com.tearhpi.immortal.item.custom;

import com.tearhpi.immortal.client.textrender.RainbowColor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.DistExecutor;

public class MythItem extends Item {
    public MythItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Component getName(ItemStack stack) {
        //return RainbowColor.drawComponent(Component.translatable(this.getDescriptionId(stack)));
        Component base = Component.translatable(this.getDescriptionId(stack));
        return DistExecutor.unsafeRunForDist(
                () -> () -> com.tearhpi.immortal.client.textrender.RainbowColor.drawComponent(base),
                () -> () -> base
        );
    }


}
