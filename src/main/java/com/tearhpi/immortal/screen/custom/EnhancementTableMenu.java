package com.tearhpi.immortal.screen.custom;

import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.block.entity.custom.EnhancementTableBlockEntity;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.screen.ModMenuTypes;
import com.tearhpi.immortal.screen.slot.OutputSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.WeaponEnhancementProtectionScrollSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.WeaponEnhancementStoneSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.WeaponEnhancementSuccessScrollSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.weaponslot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class EnhancementTableMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player player;
    public final EnhancementTableBlockEntity enhancementTable;
    public final ContainerData data;
    public EnhancementTableMenu(int id, Inventory playerInv,@Nullable FriendlyByteBuf buf) {
        this(id, playerInv,
                Objects.requireNonNull(playerInv.player.level().getBlockEntity(buf.readBlockPos())),
                playerInv.player,
                new SimpleContainerData(4));
        /*
        super(ModMenuTypes.ENHANCEMENT_TABLE_MENU.get(), id);
        BlockPos pos = buf != null ? buf.readBlockPos() : BlockPos.ZERO;
        this.level = playerInventory.player.level();
        this.enhancementTable = ((EnhancementTableBlockEntity) level.getBlockEntity(pos));
        this.player = playerInventory.player;
        this.data = new SimpleContainerData(4);

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        if (enhancementTable != null) {
            this.enhancementTable.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new weaponslot(handler,0,104,83, this::IsCrafting));
            this.addSlot(new WeaponEnhancementStoneSlot(handler,1,20,20, this::IsCrafting));
            this.addSlot(new WeaponEnhancementSuccessScrollSlot(handler,2,20,38, this::IsCrafting));
            this.addSlot(new WeaponEnhancementProtectionScrollSlot(handler,3,20,56, this::IsCrafting));
        });}

        addDataSlots(data);

         */
    }
    public EnhancementTableMenu(int id, Inventory playerInventory, BlockEntity entity,
                                Player player, ContainerData data) {
        super(ModMenuTypes.ENHANCEMENT_TABLE_MENU.get(),id);
        this.level = playerInventory.player.level();
        this.enhancementTable = ((EnhancementTableBlockEntity) entity);
        this.player = player;
        this.data = data;

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        this.enhancementTable.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new weaponslot(handler,0,104,83, this::IsCrafting));
            this.addSlot(new WeaponEnhancementStoneSlot(handler,1,20,20, this::IsCrafting));
            this.addSlot(new WeaponEnhancementSuccessScrollSlot(handler,2,20,38, this::IsCrafting));
            this.addSlot(new WeaponEnhancementProtectionScrollSlot(handler,3,20,56, this::IsCrafting));
        });
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level,enhancementTable.getBlockPos()),player, ModBlocks.EnhancementTable.get());
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
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84+56 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142+56));
        }
    }

    public int getCoin() {
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        return immortalPlayer.getCoin().get();
    }

    public boolean IsCheckBoxActive() {
        if (enhancementTable != null) {
            int[] materials = enhancementTable.EnhancementMaterialCalculate();
            if (materials != null) {
                if (enhancementTable.hasRecipe() && materials[3] <= ((IImmortalPlayer) player).getCoin().get() && this.data.get(0) == 0) {
                    return true;
                } else {
                    return false;
                }}
            return false;
        }
        return false;
    }

    public boolean IsCrafting() {
        return this.data.get(0) > 0;
    }
}
