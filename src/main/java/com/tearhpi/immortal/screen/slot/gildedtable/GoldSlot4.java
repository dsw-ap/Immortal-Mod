package com.tearhpi.immortal.screen.slot.gildedtable;

import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.screen.slot.enhancementtable.LockableSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class GoldSlot4 extends LockableSlot {
    public GoldSlot4(IItemHandler itemHandler, int index, int xPosition, int yPosition, Supplier<Boolean> isLocked) {
        super(itemHandler, index, xPosition, yPosition,isLocked);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return (stack.getItem().equals(WeaponAddonRegister.GoldType4.get()) && (!isLocked.get()));
    }
}
