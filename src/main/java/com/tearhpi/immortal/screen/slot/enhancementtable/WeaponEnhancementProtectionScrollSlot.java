package com.tearhpi.immortal.screen.slot.enhancementtable;

import com.tearhpi.immortal.util.ModTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WeaponEnhancementProtectionScrollSlot extends LockableSlot {

    public WeaponEnhancementProtectionScrollSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Supplier<Boolean> isLocked) {
        super(itemHandler, index, xPosition, yPosition,isLocked);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return (stack.is(ModTags.Items.WEAPON_ENHANCEMENT_PROTECTION_SCROLL) && (!isLocked.get()));
    }

}
