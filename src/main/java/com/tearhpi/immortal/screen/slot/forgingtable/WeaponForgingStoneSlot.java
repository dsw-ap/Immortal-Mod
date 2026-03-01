package com.tearhpi.immortal.screen.slot.forgingtable;

import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.screen.slot.enhancementtable.LockableSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WeaponForgingStoneSlot extends LockableSlot {
    public WeaponForgingStoneSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Supplier<Boolean> isLocked) {
        super(itemHandler, index, xPosition, yPosition,isLocked);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return (stack.getItem().equals(WeaponAddonRegister.WeaponForgingStone.get()) && (!isLocked.get()));
    }
    @Override
    public int getMaxStackSize() {
        return 999;
    }
    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 999;
    }
}
