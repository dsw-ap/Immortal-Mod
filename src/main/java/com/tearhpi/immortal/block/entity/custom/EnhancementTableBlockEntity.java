package com.tearhpi.immortal.block.entity.custom;

import com.tearhpi.immortal.block.entity.ModBlockEntities;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.item.item_register.WeaponAddonRegister;
import com.tearhpi.immortal.screen.custom.EnhancementTableMenu;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Random;

import static com.tearhpi.immortal.item.custom.WeaponItem.*;

public class EnhancementTableBlockEntity extends BlockEntity implements MenuProvider, GeoBlockEntity {

    // 0: 武器, 1: 强化石, 2: 成功卷, 3: 保护卷
    public static final int WEAPON_SLOT = 0;
    public static final int ENHANCEMENT_STONE_SLOT = 1;
    public static final int ENHANCEMENT_SUCCESS_SCROLL_SLOT = 2;
    public static final int ENHANCEMENT_PROTECTION_SCROLL_SLOT = 3;

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

    public int punishment;
    public int success;
    public int progress = 0;
    public BlockState blockState;
    public int Max_progress = 35;
    public final int Show_Time = 30;

    protected final ContainerData data;

    public EnhancementTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EnhancementTable_BE.get(), pos, state);
        this.blockState = state;

        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> EnhancementTableBlockEntity.this.progress;
                    case 1 -> EnhancementTableBlockEntity.this.Max_progress;
                    case 2 -> EnhancementTableBlockEntity.this.success;
                    case 3 -> EnhancementTableBlockEntity.this.punishment;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int v) {
                switch (i) {
                    case 0 -> EnhancementTableBlockEntity.this.progress = v;
                    case 1 -> EnhancementTableBlockEntity.this.Max_progress = v;
                    case 2 -> EnhancementTableBlockEntity.this.success = v;
                    case 3 -> EnhancementTableBlockEntity.this.punishment = v;
                }
            }

            @Override
            public int getCount() { return 4; }
        };
    }

    // ===================== Tick（服务端） =====================
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        if (IsCrafting()) {
            progress++;
            if (progress == Max_progress) {
                success = WhetherSuccess();
            } else if (progress >= Max_progress + Show_Time) {
                end();
            }
            setChanged();
            setChanged(level, pos, state);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 7);
        }

        if (itemHandler.getStackInSlot(WEAPON_SLOT).getItem() instanceof WeaponItem) {
            setPunishment();
        }
    }

    public void Start_Crafting() {
        int[] consume = EnhancementMaterialCalculate();
        if (consume != null) {
            itemHandler.extractItem(ENHANCEMENT_STONE_SLOT, consume[2], false);
        }
        itemHandler.extractItem(ENHANCEMENT_SUCCESS_SCROLL_SLOT, 1, false);
        if (consume != null && consume[1] >= 10) {
            itemHandler.extractItem(ENHANCEMENT_PROTECTION_SCROLL_SLOT, 1, false);
        }
        progress++;
        setPunishment();
        if (level != null) {
            setChanged(level, worldPosition, blockState);
        }
        success = 0;
    }

    public void end() {
        this.progress = 0;
        this.success = 0;
    }

    public void setPunishment() {
        // 惩罚级 1无 2降级 3破碎 4归零
        int[] materials = EnhancementMaterialCalculate();
        if (materials == null) {
            this.punishment = 1;
            return;
        }
        int tier = materials[1];
        if (tier <= 4) {
            this.punishment = 1;
        } else if (tier <= 9) {
            this.punishment = 2;
        } else {
            this.punishment = 3;
        }
        Item item_protection_scroll = itemHandler.getStackInSlot(ENHANCEMENT_PROTECTION_SCROLL_SLOT).getItem();
        if (tier >= 10) {
            if (item_protection_scroll.equals(WeaponAddonRegister.WeaponEnhancementProtectionScroll_1.get())) {
                this.punishment = 4;
            } else if (item_protection_scroll.equals(WeaponAddonRegister.WeaponEnhancementProtectionScroll_2.get())) {
                this.punishment = 2;
            }
        }
    }

    // ===================== 菜单 =====================
    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new EnhancementTableMenu(id, inv, this, player, this.data);
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
        // 默认行为即可：Forge 会调用 handleUpdateTag / load
        // 这里保留方法以兼容潜在扩展
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("enhancement_table.machine.inventory", itemHandler.serializeNBT());
        tag.putInt("enhancement_table.machine.progress", progress);
        tag.putInt("enhancement_table.machine.maxprogress", Max_progress);
        tag.putInt("enhancement_table.machine.success", success);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("enhancement_table.machine.inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("enhancement_table.machine.inventory"));
        }
        progress = tag.getInt("enhancement_table.machine.progress");
        Max_progress = tag.getInt("enhancement_table.machine.maxprogress");
        // 原代码这里误写成读 maxprogress，已修正为 success
        success = tag.getInt("enhancement_table.machine.success");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        // 1.20.1 直接返回 saveWithoutMetadata()
        return this.saveWithoutMetadata();
    }

    // ===================== 其他逻辑 =====================
    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(Objects.requireNonNull(this.getLevel()), this.getBlockPos(), inv);
    }

    public boolean hasRecipe() {
        if (itemHandler.getStackInSlot(WEAPON_SLOT).getItem() instanceof WeaponItem) {
            int[] m = EnhancementMaterialCalculate();
            if (m != null) {
                return m[2] <= itemHandler.getStackInSlot(ENHANCEMENT_STONE_SLOT).getCount();
            }
        }
        return false;
    }

    /**
     * 返回值: [武器品级, 当前强化等级, 强化石数量, 金币数量]
     */
    public int[] EnhancementMaterialCalculate() {
        ItemStack weaponStack = itemHandler.getStackInSlot(WEAPON_SLOT);
        if (weaponStack.getItem() instanceof WeaponItem weaponItem) {
            int[] r = new int[4];
            int tier = getEnhanceLevel(weaponStack);  // 当前强化等级
            r[0] = weaponItem.weapon_level;
            r[1] = tier;
            r[2] = (int) Math.max(1, Math.pow(tier, 1.5) * (weaponItem.weapon_level / 3.0));  // 至少 1
            r[3] = (int) Math.max(50, 50.0 * tier * (tier + 1) * (weaponItem.weapon_level / 2.0));
            if (tier == 0) {
                r[2] = 1;
                r[3] = 50;
            }
            return r;
        }
        return null;
    }

    public int SuccessRate() {
        int[] calc = EnhancementMaterialCalculate();
        if (calc == null) return 0;

        int enhancement_level = calc[1];
        int ret;
        if (enhancement_level <= 20) {
            ret = switch (enhancement_level) {
                case 0 -> 100;
                case 1 -> 95;
                case 2 -> 90;
                case 3 -> 85;
                case 4 -> 80;
                case 5 -> 75;
                case 6 -> 70;
                case 7 -> 65;
                case 8 -> 60;
                case 9 -> 50;
                case 10 -> 45;
                case 11 -> 40;
                case 12 -> 37;
                case 13 -> 34;
                case 14 -> 31;
                case 15 -> 28;
                case 16 -> 25;
                case 17 -> 23;
                case 18 -> 21;
                case 19, 20 -> 20;
                default -> 100;
            };
        } else {
            ret = 20;
        }

        ItemStack scroll = itemHandler.getStackInSlot(ENHANCEMENT_SUCCESS_SCROLL_SLOT);
        if (!scroll.isEmpty()) {
            int add = 0;
            int cur = getEnhanceLevel(itemHandler.getStackInSlot(WEAPON_SLOT));
            if (scroll.getItem().equals(WeaponAddonRegister.WeaponEnhancementSuccessScroll_1.get())) {
                add = Math.max(0, 10 - 1 * cur);
            } else if (scroll.getItem().equals(WeaponAddonRegister.WeaponEnhancementSuccessScroll_2.get())) {
                add = Math.max(0, (int) (20 - 1.25 * cur));
            } else if (scroll.getItem().equals(WeaponAddonRegister.WeaponEnhancementSuccessScroll_3.get())) {
                add = Math.max(0, (int) (30 - 1.5 * cur));
            } else if (scroll.getItem().equals(WeaponAddonRegister.WeaponEnhancementSuccessScroll_4.get())) {
                add = Math.max(0, (int) (40 - 1.75 * cur));
            } else if (scroll.getItem().equals(WeaponAddonRegister.WeaponEnhancementSuccessScroll_5.get())) {
                add = Math.max(0, 50 - 2 * cur);
            }
            ret += add;
        }
        return Math.min(ret, 100);
    }

    public int WhetherSuccess() {
        // Java 17: nextInt(100)+1 -> 1..100（含 100）
        int random = new Random().nextInt(100) + 1;
        ItemStack itemStack = itemHandler.getStackInSlot(WEAPON_SLOT);

        if (random <= SuccessRate()) {
            if (itemStack.getItem() instanceof WeaponItem) {
                addEnhanceLevel(itemStack);
            }
            return 1; // 成功
        } else {
            // 惩罚级 1无 2降级 3破碎 4归零
            if (this.punishment == 2) {
                setEnhanceLevel(itemStack, Math.max(0, getEnhanceLevel(itemStack) - 1));
            } else if (this.punishment == 3) {
                itemHandler.extractItem(WEAPON_SLOT, 1, false);
            } else if (this.punishment == 4) {
                setEnhanceLevel(itemStack, 0);
            }
            return 2; // 失败
        }
    }

    // ===================== GeckoLib =====================
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // 1.20.1 / GeckoLib 4.x：保持接口，不做动画控制器的添加时可留空
        // 如需：controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE));
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // ===================== 杂项 =====================
    @Override
    public Component getDisplayName() {
        return Component.literal("Enhancement Table");
    }

    public boolean IsCrafting() {
        return this.progress > 0;
    }
}
