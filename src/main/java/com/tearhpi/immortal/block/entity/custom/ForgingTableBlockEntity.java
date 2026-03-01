package com.tearhpi.immortal.block.entity.custom;

import com.tearhpi.immortal.block.entity.ModBlockEntities;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.screen.custom.ForgingTableMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Random;

import static com.tearhpi.immortal.item.custom.WeaponItem.*;

public class ForgingTableBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {

    public static final int WEAPON_SLOT = 0;
    public static final int FORGING_STONE_SLOT = 1;
    public static final int FORGING_CHANCE_SCROLL_SLOT = 2;
    public static final int FORGING_RESET_SCROLL_SLOT = 3;

    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 7);
            }
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

    public int progress = 0;
    public BlockState blockState;
    public int Max_progress = 30;
    public double[] add_value;
    protected final ContainerData data;

    public ForgingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ForgingTable_BE.get(), pos, state);
        this.blockState = state;

        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> ForgingTableBlockEntity.this.progress;
                    case 1 -> ForgingTableBlockEntity.this.Max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int v) {
                switch (i) {
                    case 0 -> ForgingTableBlockEntity.this.progress = v;
                    case 1 -> ForgingTableBlockEntity.this.Max_progress = v;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    // ===================== Tick（服务端） =====================
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

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
        int use = get_use_count();
        this.add_value = getShowNum();
        itemHandler.extractItem(FORGING_STONE_SLOT, use, false);
        itemHandler.extractItem(FORGING_CHANCE_SCROLL_SLOT, 1, false);
        progress++;
        if (level != null) {
            setChanged(level, worldPosition, blockState);
        }
    }

    public void end() {
        this.progress = 0;
        ItemStack itemStack = itemHandler.getStackInSlot(WEAPON_SLOT);

        // 升级锻造等级
        addForgingLevel(itemStack);

        // 计算两条随机加成（沿用你原本“百分比 1..100”抽样思路）
        double[] range = getShowNum();
        Random rand = new Random();

        int r1 = rand.nextInt(100) + 1; // 1..100
        int add_number_1 = (int) (r1 * (range[1] - range[0]) + 100 * range[0]);

        int r2 = rand.nextInt(100) + 1; // 1..100
        int add_number_2 = (int) (r2 * (range[3] - range[2]) + 100 * range[2]);

        addForgingNum_1(itemStack, add_number_1);
        addForgingNum_2(itemStack, add_number_2);
    }

    public boolean IsCrafting() {
        return this.progress > 0;
    }

    // ===================== 菜单 =====================
    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ForgingTableMenu(id, inv, this, player, this.data);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Forging Table");
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
        tag.put("forging_table.machine.inventory", itemHandler.serializeNBT());
        tag.putInt("forging_table.machine.progress", progress);
        tag.putInt("forging_table.machine.maxprogress", Max_progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("forging_table.machine.inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("forging_table.machine.inventory"));
        }
        progress = tag.getInt("forging_table.machine.progress");
        Max_progress = tag.getInt("forging_table.machine.maxprogress");
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

    // ===================== 显示区间计算 =====================
    /**
     * 返回： [暴击最小, 暴击最大, 伤害最小, 伤害最大]
     */
    public double[] getShowNum() {
        double critical_hit_min = 0.0;
        double critical_hit_max = 3.0;
        double damage_min = 5.0;
        double damage_max = 10.0;

        // 卷轴加成
        ItemStack chanceScrollStack = this.itemHandler.getStackInSlot(FORGING_CHANCE_SCROLL_SLOT);
        if (chanceScrollStack.getItem().equals(WeaponAddonRegister.WeaponForgingChanceScroll_1.get())) {
            critical_hit_min += 1;
            critical_hit_max += 1;
            damage_min += 2;
            damage_max += 2;
        } else if (chanceScrollStack.getItem().equals(WeaponAddonRegister.WeaponForgingChanceScroll_2.get())) {
            critical_hit_min += 1.5;
            critical_hit_max += 1.5;
            damage_min += 3;
            damage_max += 3;
        } else if (chanceScrollStack.getItem().equals(WeaponAddonRegister.WeaponForgingChanceScroll_3.get())) {
            critical_hit_min += 3.0;
            critical_hit_max += 3.0;
            damage_min += 6;
            damage_max += 6;
        }

        return new double[] { critical_hit_min, critical_hit_max, damage_min, damage_max };
    }

    public boolean IsCheckBoxActive() {
        int materials_count = this.itemHandler.getStackInSlot(FORGING_STONE_SLOT).getCount();
        int use_count = get_use_count();
        ItemStack weapon = this.itemHandler.getStackInSlot(WEAPON_SLOT);
        return weapon.getItem() instanceof WeaponItem
                && materials_count >= use_count
                && this.progress == 0
                && getForgingLevel(weapon) <= 4;
    }

    public boolean IsResetActive() {
        ItemStack weapon = this.itemHandler.getStackInSlot(WEAPON_SLOT);
        ItemStack reset = this.itemHandler.getStackInSlot(FORGING_RESET_SCROLL_SLOT);
        return weapon.getItem() instanceof WeaponItem
                && reset.getItem().equals(WeaponAddonRegister.WeaponForgingResetScroll.get())
                && this.progress == 0;
    }

    public int get_use_count() {
        ItemStack weaponStack = this.itemHandler.getStackInSlot(WEAPON_SLOT);
        int lv = WeaponItem.getForgingLevel(weaponStack);
        return (lv + 1) * (lv + 1 + 0); // (n+1)*(n+1)
    }

    public void reset_num() {
        ItemStack weaponStack = this.itemHandler.getStackInSlot(WEAPON_SLOT);
        this.itemHandler.extractItem(FORGING_RESET_SCROLL_SLOT, 1, false);
        setForgingNum_1(weaponStack, 0);
        setForgingNum_2(weaponStack, 0);
        setForgingLevel(weaponStack, 0);
    }

    // ===================== GeckoLib =====================
    @Override
    public void registerControllers(software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar controllers) {
        // 如需动画：controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE));
    }

    private final software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
