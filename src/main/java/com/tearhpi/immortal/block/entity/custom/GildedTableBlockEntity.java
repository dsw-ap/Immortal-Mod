package com.tearhpi.immortal.block.entity.custom;

import com.tearhpi.immortal.block.entity.ModBlockEntities;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.screen.custom.GildedTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Objects;

import static com.tearhpi.immortal.item.custom.WeaponItem.*;

public class GildedTableBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {

    public final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 7);
            }
        }
    };

    public int progress = 0;
    public BlockState blockState;
    public int Max_progress = 20;

    public static final int WEAPON_SLOT = 0;
    public static final int GOLD_SLOT1 = 1;
    public static final int GOLD_SLOT2 = 2;
    public static final int GOLD_SLOT3 = 3;
    public static final int GOLD_SLOT4 = 4;
    private static final int OUTPUT_SLOT = 5;

    public int GOLD_AMOUNT_1 = 0;
    public int GOLD_AMOUNT_2 = 0;
    public int GOLD_AMOUNT_3 = 0;
    public int GOLD_AMOUNT_4 = 0;
    public int GOLD_AMOUNT_MAX = 100;

    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    public GildedTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GildedTable_BE.get(), pos, state);
        this.blockState = state;

        this.data = new ContainerData() {
            @Override
            public int get(int idx) {
                return switch (idx) {
                    case 0 -> GildedTableBlockEntity.this.progress;
                    case 1 -> GildedTableBlockEntity.this.Max_progress;
                    case 2 -> GildedTableBlockEntity.this.GOLD_AMOUNT_1;
                    case 3 -> GildedTableBlockEntity.this.GOLD_AMOUNT_2;
                    case 4 -> GildedTableBlockEntity.this.GOLD_AMOUNT_3;
                    case 5 -> GildedTableBlockEntity.this.GOLD_AMOUNT_4;
                    case 6 -> GildedTableBlockEntity.this.GOLD_AMOUNT_MAX;
                    default -> 0;
                };
            }

            @Override
            public void set(int idx, int val) {
                switch (idx) {
                    case 0 -> GildedTableBlockEntity.this.progress = val;
                    case 1 -> GildedTableBlockEntity.this.Max_progress = val;
                    case 2 -> GildedTableBlockEntity.this.GOLD_AMOUNT_1 = val;
                    case 3 -> GildedTableBlockEntity.this.GOLD_AMOUNT_2 = val;
                    case 4 -> GildedTableBlockEntity.this.GOLD_AMOUNT_3 = val;
                    case 5 -> GildedTableBlockEntity.this.GOLD_AMOUNT_4 = val;
                    case 6 -> GildedTableBlockEntity.this.GOLD_AMOUNT_MAX = val;
                }
            }

            @Override
            public int getCount() {
                return 7;
            }
        };
    }

    // ===================== Tick（服务端） =====================
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        // 金料装载
        if (GOLD_AMOUNT_1 <= GOLD_AMOUNT_MAX - 10) {
            ItemStack itemstack = this.itemHandler.getStackInSlot(GOLD_SLOT1);
            if (itemstack.is(WeaponAddonRegister.GoldType1.get())) {
                itemHandler.extractItem(GOLD_SLOT1, 1, false);
                GOLD_AMOUNT_1 += 10;
                setChanged(level, pos, state);
            }
        }
        if (GOLD_AMOUNT_2 <= GOLD_AMOUNT_MAX - 10) {
            ItemStack itemstack = this.itemHandler.getStackInSlot(GOLD_SLOT2);
            if (itemstack.is(WeaponAddonRegister.GoldType2.get())) {
                itemHandler.extractItem(GOLD_SLOT2, 1, false);
                GOLD_AMOUNT_2 += 10;
                setChanged(level, pos, state);
            }
        }
        if (GOLD_AMOUNT_3 <= GOLD_AMOUNT_MAX - 8) {
            ItemStack itemstack = this.itemHandler.getStackInSlot(GOLD_SLOT3);
            if (itemstack.is(WeaponAddonRegister.GoldType3.get())) {
                itemHandler.extractItem(GOLD_SLOT3, 1, false);
                GOLD_AMOUNT_3 += 8;
                setChanged(level, pos, state);
            }
        }
        if (GOLD_AMOUNT_4 <= GOLD_AMOUNT_MAX - 5) {
            ItemStack itemstack = this.itemHandler.getStackInSlot(GOLD_SLOT4);
            if (itemstack.is(WeaponAddonRegister.GoldType4.get())) {
                itemHandler.extractItem(GOLD_SLOT4, 1, false);
                GOLD_AMOUNT_4 += 5;
                setChanged(level, pos, state);
            }
        }
        setChanged();

        if (IsCrafting()) {
            progress++;
            if (progress == Max_progress) {
                end();
            }
            setChanged();
            setChanged(level, pos, state);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 7);
        }
    }

    public void Start_Crafting() {
        ItemStack itemstack = this.itemHandler.getStackInSlot(WEAPON_SLOT);
        if (WeaponItem.getGildedLevel(itemstack) == 0) {
            this.GOLD_AMOUNT_1 -= 90;
        } else if (WeaponItem.getGildedLevel(itemstack) == 1) {
            this.GOLD_AMOUNT_2 -= 90;
        } else if (WeaponItem.getGildedLevel(itemstack) == 2) {
            this.GOLD_AMOUNT_3 -= 90;
        } else if (WeaponItem.getGildedLevel(itemstack) == 3) {
            this.GOLD_AMOUNT_4 -= 90;
        }

        progress++;
        if (level != null) {
            setChanged(level, worldPosition, blockState);
        }
    }

    public void end() {
        this.progress = 0;
        ItemStack itemStack = itemHandler.getStackInSlot(WEAPON_SLOT);
        if (getGildedLevel(itemStack) % 2 == 0) {
            addAttrRuneMax(itemStack);
        } else {
            addSkillRuneMax(itemStack);
        }
        addGildedLevel(itemStack);
        itemHandler.setStackInSlot(OUTPUT_SLOT, itemStack);
        itemHandler.extractItem(WEAPON_SLOT, 1, false);
    }

    public boolean IsCrafting() {
        return this.progress > 0;
    }

    // ===================== 菜单 =====================
    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new GildedTableMenu(id, inv, this, player, this.data);
    }

    @Override
    public Component getDisplayName() {
        // 如需显示“Gilded Table”可改为该文本
        return Component.literal("Gilded Table");
    }

    // ===================== Capability =====================
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    // ===================== 同步 & 存档（1.20.1 签名） =====================
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        // 使用默认行为（Forge 会走 load / handleUpdateTag）
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("gilded_table.machine.inventory", itemHandler.serializeNBT());
        tag.putInt("gilded_table.machine.progress", progress);
        tag.putInt("gilded_table.machine.maxprogress", Max_progress);
        tag.putInt("gilded_table.machine.gm1", GOLD_AMOUNT_1);
        tag.putInt("gilded_table.machine.gm2", GOLD_AMOUNT_2);
        tag.putInt("gilded_table.machine.gm3", GOLD_AMOUNT_3);
        tag.putInt("gilded_table.machine.gm4", GOLD_AMOUNT_4);
        tag.putInt("gilded_table.machine.gmmax", GOLD_AMOUNT_MAX);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("gilded_table.machine.inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("gilded_table.machine.inventory"));
        }
        progress = tag.getInt("gilded_table.machine.progress");
        Max_progress = tag.getInt("gilded_table.machine.maxprogress");
        GOLD_AMOUNT_1 = tag.getInt("gilded_table.machine.gm1");
        GOLD_AMOUNT_2 = tag.getInt("gilded_table.machine.gm2");
        GOLD_AMOUNT_3 = tag.getInt("gilded_table.machine.gm3");
        GOLD_AMOUNT_4 = tag.getInt("gilded_table.machine.gm4");
        GOLD_AMOUNT_MAX = tag.getInt("gilded_table.machine.gmmax");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    // ===================== 掉落 =====================
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(Objects.requireNonNull(this.getLevel()), this.getBlockPos(), inventory);
    }

    // ===================== GeckoLib =====================
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::precidate));
    }

    private PlayState precidate(AnimationState<GildedTableBlockEntity> state) {
        state.getController().setAnimation(RawAnimation.begin().then("ready", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    // 可选：用于时间同步（GeckoLib 提供的 RenderUtil）
    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // ===================== 额外校验 =====================
    public boolean IsCheckBoxActive() {
        ItemStack itemStack = itemHandler.getStackInSlot(WEAPON_SLOT);
        int glevel = getGildedLevel(itemStack);
        if (itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() != Items.AIR || this.progress > 0) {
            return false;
        }
        if (glevel == 0 && this.GOLD_AMOUNT_1 >= 90) return true;
        if (glevel == 1 && this.GOLD_AMOUNT_2 >= 90) return true;
        if (glevel == 2 && this.GOLD_AMOUNT_3 >= 90) return true;
        if (glevel == 3 && this.GOLD_AMOUNT_4 >= 90) return true;
        return false;
    }
}
