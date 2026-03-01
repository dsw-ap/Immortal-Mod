package com.tearhpi.immortal.screen.custom;

import com.tearhpi.immortal.block.ModBlocks;
import com.tearhpi.immortal.block.entity.custom.ForgingTableBlockEntity;
import com.tearhpi.immortal.block.entity.custom.GildedTableBlockEntity;
import com.tearhpi.immortal.screen.ModMenuTypes;
import com.tearhpi.immortal.screen.slot.OutputSlot;
import com.tearhpi.immortal.screen.slot.enhancementtable.weaponslot;
import com.tearhpi.immortal.screen.slot.forgingtable.WeaponForgingChanceScrollSlot;
import com.tearhpi.immortal.screen.slot.forgingtable.WeaponForgingResetScrollSlot;
import com.tearhpi.immortal.screen.slot.forgingtable.WeaponForgingStoneSlot;
import com.tearhpi.immortal.screen.slot.gildedtable.GoldSlot1;
import com.tearhpi.immortal.screen.slot.gildedtable.GoldSlot2;
import com.tearhpi.immortal.screen.slot.gildedtable.GoldSlot3;
import com.tearhpi.immortal.screen.slot.gildedtable.GoldSlot4;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.Objects;

public class GildedTableMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player player;
    public final GildedTableBlockEntity gildedTableBlockEntity;
    public final ContainerData data;
    public GildedTableMenu(int id, Inventory playerInv, @Nullable FriendlyByteBuf buf) {
        this(id, playerInv,
                Objects.requireNonNull(playerInv.player.level().getBlockEntity(buf.readBlockPos())), playerInv.player,
                new SimpleContainerData(7));
    }
    public GildedTableMenu(int id, Inventory playerInventory, BlockEntity entity, Player player,
                           ContainerData data) {
        super(ModMenuTypes.GILDED_TABLE_MENU.get(),id);
        this.level = playerInventory.player.level();
        this.gildedTableBlockEntity = ((GildedTableBlockEntity) entity);
        this.data = data;
        this.player = player;

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        this.gildedTableBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new weaponslot(handler,0,87,31, this::IsCrafting));
            this.addSlot(new GoldSlot1(handler,1,8,65, this::IsCrafting));
            this.addSlot(new GoldSlot2(handler,2,27,65, this::IsCrafting));
            this.addSlot(new GoldSlot3(handler,3,46,65, this::IsCrafting));
            this.addSlot(new GoldSlot4(handler,4,65,65, this::IsCrafting));
            this.addSlot(new OutputSlot(handler,5,149,31));
        });
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level,gildedTableBlockEntity.getBlockPos()),player, ModBlocks.GildedTable.get());
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
        int MaxPixelsize = 24;
        return maxProgress != 0 && progress != 0 ? progress * MaxPixelsize / maxProgress : 0;
    }
    public int getScaledGoldAmount(int gold_type) {
        /*
        case 2 -> GildedTableBlockEntity.this.GOLD_AMOUNT_1;
        case 3 -> GildedTableBlockEntity.this.GOLD_AMOUNT_2;
        case 4 -> GildedTableBlockEntity.this.GOLD_AMOUNT_3;
        case 5 -> GildedTableBlockEntity.this.GOLD_AMOUNT_4;
        case 6 -> GildedTableBlockEntity.this.GOLD_AMOUNT_MAX;
         */
        int gold_amount = this.data.get(gold_type);
        int max_gold_amount = this.data.get(6);
        int GoldAmountPixelsize = 44;
        return max_gold_amount != 0 && gold_amount != 0 ? gold_amount * GoldAmountPixelsize / max_gold_amount : 0;
    }
}
