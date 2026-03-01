package com.tearhpi.immortal.mixin;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.WandNormalAttackBullet;
import com.tearhpi.immortal.item.custom.WandItem;
import com.tearhpi.immortal.item.custom.WeaponItem;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.skill._WandNormalAttackSyncPacket;
import com.tearhpi.immortal.sound.ModSounds;
import com.tearhpi.immortal.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.PacketDistributor;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Final
    private static Logger LOGGER;
    protected int missTime;
    /**
     * @author TearH_Pi
     * @reason 修改玩家攻击逻辑
     */
    @Overwrite
    private boolean startAttack() {
        Minecraft mc = (Minecraft) (Object) this;
        Player player = mc.player;
        IImmortalPlayer immortalPlayer = (IImmortalPlayer) player;
        if (this.missTime > 0) {
            return false;
        } else if (mc.hitResult == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (mc.gameMode.hasMissTime()) {
                this.missTime = 10;
            }

            return false;
        } else if (mc.player.isHandsBusy()) {
            return false;
        } else {
            ItemStack itemstack = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
            Item itemInHand = player.getMainHandItem().getItem();
            if (!itemstack.isItemEnabled(mc.level.enabledFeatures())) {
                return false;
            } else {
                boolean flag = false;
                InputEvent.InteractionKeyMappingTriggered inputEvent = ForgeHooksClient.onClickInput(0, mc.options.keyAttack, InteractionHand.MAIN_HAND);
                if (!inputEvent.isCanceled()) {
                    switch (mc.hitResult.getType()) {
                        case ENTITY:
                            //未蓄满力无法攻击
                            if (player != null && player.getAttackStrengthScale(0F) < 1.0F) {
                                //player.displayClientMessage(Component.literal("攻击冷却中！"), true);
                                return false;// 阻止攻击
                            }
                            //非WeaponItem类武器无法攻击
                            //if (!player.getMainHandItem().is(ModTags.Items.ATTACKABLE)) {
                            if (!(itemInHand instanceof WeaponItem)) {
                                //player.displayClientMessage(Component.literal("你未手持剑类武器,无法攻击!"), true);
                                return false;
                            }
                            //武器中,远程武器改为发射子弹
                            if (itemInHand instanceof WandItem wandItem) {
                                System.out.println(immortalPlayer.getSkillInUse().IsInUseSkill);
                                if (immortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                                    if (player.getAttackStrengthScale(0F) >= 0.95F) {
                                        player.resetAttackStrengthTicker();
                                        ModNetworking.CHANNEL.sendToServer( new _WandNormalAttackSyncPacket());
                                    }
                                    return true;
                                }
                            }
                            //攻击
                            mc.gameMode.attack(mc.player, ((EntityHitResult) mc.hitResult).getEntity());
                            flag = true;
                            mc.player.swing(InteractionHand.MAIN_HAND);
                            break;
                        case BLOCK:
                            if (immortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                                if (player.getAttackStrengthScale(0F) >= 0.95F) {
                                    if (itemInHand instanceof WandItem wandItem) {
                                        player.resetAttackStrengthTicker();
                                        ModNetworking.CHANNEL.sendToServer(new _WandNormalAttackSyncPacket());
                                        return true;
                                    }
                                }
                            }
                            //非攻击类武器挖掘 特效部分
                            if (!(itemInHand instanceof WeaponItem)) {
                                mc.player.swing(InteractionHand.MAIN_HAND);
                            }
                            BlockHitResult blockhitresult = (BlockHitResult) mc.hitResult;
                            BlockPos blockpos = blockhitresult.getBlockPos();
                            if (!mc.level.isEmptyBlock(blockpos)) {
                                mc.gameMode.startDestroyBlock(blockpos, blockhitresult.getDirection());
                                if (mc.level.getBlockState(blockpos).isAir()) {
                                    flag = true;
                                    //return true;
                                }
                            }
                            //return false;
                            break;
                        case MISS:
                            //检测法杖类武器攻击
                            Item item = player.getMainHandItem().getItem();
                            if (item instanceof WandItem wandItem) {
                                //player.displayClientMessage(Component.literal("发射子弹"), true);
                                if (immortalPlayer.getSkillInUse().IsInUseSkill == -1) {
                                    if (player.getAttackStrengthScale(0F) >= 0.95F) {
                                        player.resetAttackStrengthTicker();
                                        ModNetworking.CHANNEL.sendToServer( new _WandNormalAttackSyncPacket());
                                    }
                                }
                                return true;
                            }
                            //if (mc.gameMode.hasMissTime()) {
                            //    this.missTime = 10;
                            //}
                            //mc.player.resetAttackStrengthTicker();
                            ForgeHooks.onEmptyLeftClick(mc.player);
                            //return false;
                            break;
                    }
                }

                if (inputEvent.shouldSwingHand()) {
                    mc.player.swing(InteractionHand.MAIN_HAND);
                }
                return flag;
            }
        }
    }
}
