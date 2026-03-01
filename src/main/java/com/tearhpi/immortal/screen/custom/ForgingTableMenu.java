package com.tearhpi.immortal.screen.custom;

import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.item.ModItems;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.screen.ModMenuTypes;
import com.tearhpi.immortal.screen.slot.enhancementtable.WeaponEnhancementProtectionScrollSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.WeaponEnhancementStoneSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.WeaponEnhancementSuccessScrollSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.weaponslot;
import com.tearhpi.immortal.screen.slot.forgingtable.WeaponForgingChanceScrollSlot;
import com.tearhpi.immortal.screen.slot.forgingtable.WeaponForgingResetScrollSlot;
import com.tearhpi.immortal.screen.slot.forgingtable.WeaponForgingStoneSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.Objects;

public class ForgingTableMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player player;
    public final ForgingTableBlockEntity forgingTableBlockEntity;
    public final ContainerData data;
    public ForgingTableMenu(int id, Inventory playerInv, @Nullable FriendlyByteBuf buf) {
        this(id, playerInv,
                Objects.requireNonNull(playerInv.player.level().getBlockEntity(buf.readBlockPos())), playerInv.player,
                new SimpleContainerData(4));
    }
    public ForgingTableMenu(int id, Inventory playerInventory, BlockEntity entity, Player player,
                             ContainerData data) {
        super(ModMenuTypes.FORGING_TABLE_MENU.get(),id);
        this.level = playerInventory.player.level();
        this.forgingTableBlockEntity = ((ForgingTableBlockEntity) entity);
        this.data = data;
        this.player = player;

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        this.forgingTableBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new weaponslot(handler,0,104,50, this::IsCrafting));
            this.addSlot(new WeaponForgingStoneSlot(handler,1,20,20, this::IsCrafting));
            this.addSlot(new WeaponForgingChanceScrollSlot(handler,2,20,38, this::IsCrafting));
            this.addSlot(new WeaponForgingResetScrollSlot(handler,3,104,70, this::IsCrafting));
        });
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level,forgingTableBlockEntity.getBlockPos()),player, ModBlocks.ForgingTable.get());
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            //System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 104 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 162));
        }
    }
    public boolean IsCrafting() {
        return this.data.get(0) > 0;
    }
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int MaxPixelsize = 162;
        return maxProgress != 0 && progress != 0 ? progress * MaxPixelsize / maxProgress : 0;
    }
}
