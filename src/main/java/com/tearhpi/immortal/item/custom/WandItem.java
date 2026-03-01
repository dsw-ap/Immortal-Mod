package com.tearhpi.immortal.item.custom;

import com.tearhpi.immortal.attribute.WeaponAttributeAttack;
import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.entity.custom.WandNormalAttackBullet_Enforced;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_NormalAttack;
import com.tearhpi.immortal.networking.ModNetworking;
import com.tearhpi.immortal.networking.NormalAttackEnergySyncToClientPacket;
import com.tearhpi.immortal.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;

public class WandItem extends WeaponItem {
    private static final String TAG_WAND = "immortal_wand";
    private static final String TAG_CONSUMED = "consumed_energy";
    private static final String TAG_LAST_SECOND = "last_consumed_second";
    public WandItem(WeaponItemProperties p_41383_, int weapon_level, int weapon_type, WeaponAttributeAttack weaponAttributeAttack) {
        super(p_41383_, weapon_level, weapon_type, weaponAttributeAttack);
    }
    public @NotNull UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (interactionHand != InteractionHand.MAIN_HAND) return InteractionResultHolder.fail(itemstack);
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)player;
        if (iImmortalPlayer.getNormalAttackEnergy().get() <= 0){
            return InteractionResultHolder.fail(itemstack);
        }
        if (iImmortalPlayer.getSkillInUse().IsInUseSkill != -1) {
            return InteractionResultHolder.fail(itemstack);
        }
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemstack);
    }
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide) {
            Random random = new Random();
            for(int i=0;i<5;i++) {
                level.addParticle(ParticleTypes.ENCHANT,entity.position().x+random.nextFloat(-2,2),entity.position().y()+1+random.nextFloat(-2,2),entity.position().z()+random.nextFloat(-1,1),+random.nextFloat(-1,1), +random.nextFloat(-1,1), +random.nextFloat(-1,1));
            }
            return;
        }
        if (!(entity instanceof Player player)) return;
        int usedTicks = getUseDuration(stack) - remainingUseDuration;
        int secondIndex = usedTicks / 20;
        CompoundTag wand = stack.getOrCreateTagElement(TAG_WAND);
        int lastSecond = wand.getInt(TAG_LAST_SECOND);
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)player;
        if (iImmortalPlayer.getSkillInUse().IsInUseSkill != -1) {
            player.releaseUsingItem();
            return;
        }
        if (secondIndex > lastSecond) {
            player.playNotifySound(SoundEvents.BLAZE_SHOOT,SoundSource.PLAYERS, 1.0F, 1.0F);
            if (iImmortalPlayer.getNormalAttackEnergy().get() <= 0) {
                player.releaseUsingItem();
                return;
            }
            iImmortalPlayer.getNormalAttackEnergy().remove();
            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new NormalAttackEnergySyncToClientPacket(iImmortalPlayer.getNormalAttackEnergy().get()));
            wand.putInt(TAG_CONSUMED, wand.getInt(TAG_CONSUMED) + 1);
            wand.putInt(TAG_LAST_SECOND, secondIndex);
            if (iImmortalPlayer.getNormalAttackEnergy().get() <= 0) player.releaseUsingItem();
        }
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (level.isClientSide) {
            return;
        }
        if (!(entity instanceof Player player)) return;
        CompoundTag tag = stack.getTag();
        int consumed = 0;
        if (tag != null && tag.contains(TAG_WAND)) {
            CompoundTag wand = tag.getCompound(TAG_WAND);
            consumed = wand.getInt(TAG_CONSUMED);
            tag.remove(TAG_WAND);
            if (tag.isEmpty()) stack.setTag(null);
        }
        if (consumed == 0) return;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer)entity;
        Vec3 dir = player.getViewVector(1.0F).normalize();
        double speedPerTick = 1.0;
        Vec3 motion = dir.scale(speedPerTick);
        Vec3 start = player.getEyePosition().add(dir.scale(0.6)).add(0.0, -0.7, 0.0);
        ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
        int attribute = 0;
        if (held.getItem() instanceof WeaponItem weaponItem) attribute = weaponItem.weapon_attribute.getattributeInt();
        WandNormalAttackBullet_Enforced e = WandNormalAttackBullet_Enforced.spawn((ServerLevel) level, (ServerPlayer) player, attribute, start, motion,consumed);
        BoundEntityManager_NormalAttack.bind((ServerPlayer) player, e);
        player.swing(InteractionHand.MAIN_HAND);
    }
}
