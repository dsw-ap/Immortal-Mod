package com.tearhpi.immortal.screen.slot.enhancementtable;

import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WeaponEnhancementStoneSlot extends LockableSlot {
    public WeaponEnhancementStoneSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Supplier<Boolean> isLocked) {
        super(itemHandler, index, xPosition, yPosition,isLocked);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return (stack.getItem().equals(WeaponAddonRegister.WeaponEnhancementStone.get()) && (!isLocked.get()));
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
