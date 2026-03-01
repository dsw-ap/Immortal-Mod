package com.tearhpi.immortal.screen.slot.enhancementtable;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.Supplier;

public class LockableSlot extends SlotItemHandler {
    public final Supplier<Boolean> isLocked;
    public LockableSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition,Supplier<Boolean> isLocked) {
        super(itemHandler, index, xPosition, yPosition);
        this.isLocked = isLocked;
    }
    @Override
    public boolean mayPickup(Player player) {
        boolean isLocked = this.isLocked.get();
        return !isLocked; // 禁止取出物品
    }
}
