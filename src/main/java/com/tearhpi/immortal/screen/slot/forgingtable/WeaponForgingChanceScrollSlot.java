package com.tearhpi.immortal.screen.slot.forgingtable;

import com.tearhpi.immortal.screen.slot.enhancementtable.LockableSlot;
import com.tearhpi.immortal.util.ModTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WeaponForgingChanceScrollSlot extends LockableSlot {
    public WeaponForgingChanceScrollSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Supplier<Boolean> isLocked) {
        super(itemHandler, index, xPosition, yPosition,isLocked);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return (stack.is(ModTags.Items.WEAPON_FORGING_CHANCE_SCROLL) && (!isLocked.get()));
    }
}
