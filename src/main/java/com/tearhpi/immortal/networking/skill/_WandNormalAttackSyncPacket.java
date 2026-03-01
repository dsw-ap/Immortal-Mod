package com.tearhpi.immortal.networking.skill;

import com.tearhpi.immortal.entity.custom.WandNormalAttackBullet;
import com.tearhpi.immortal.entity.custom.zSpecialWandBullet.WandBullet1_1;
import com.tearhpi.immortal.event.removeboundbullet.BoundEntityManager_NormalAttack;
import com.tearhpi.immortal.item.custom.Weapon.Weapon_1_1;
import com.tearhpi.immortal.item.custom.WeaponItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;
// import your.mod.api.IImmortalPlayer; // 不需要就删

public class _WandNormalAttackSyncPacket {

    // —— 无载荷 —— //
    public _WandNormalAttackSyncPacket() {}

    public static void encode(_WandNormalAttackSyncPacket msg, FriendlyByteBuf buf) {
        // 无数据需要写入
    }

    public static _WandNormalAttackSyncPacket decode(FriendlyByteBuf buf) {
        return new _WandNormalAttackSyncPacket();
    }

    // —— 处理：客户端发到服务端（C → S） —— //
    public static void handle(_WandNormalAttackSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        // 仅处理 C→S
        if (ctx.getDirection() != NetworkDirection.PLAY_TO_SERVER) {
            ctx.setPacketHandled(true);
            return;
        }

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            ServerLevel level = (ServerLevel) player.level();
            SummonNormalAttack(level,player);
        });

        ctx.setPacketHandled(true);
    }
    public static void SummonNormalAttack(ServerLevel level, ServerPlayer player) {
        // 朝向 & 速度
        Vec3 dir = player.getViewVector(1.0F).normalize();
        double speedPerTick = 1.0;
        Vec3 motion = dir.scale(speedPerTick);

        // 生成起点：眼睛前 0.6 格并微微下移
        Vec3 start = player.getEyePosition().add(dir.scale(0.6)).add(0.0, -0.7, 0.0);

        // 从主手物品读取自定义武器属性
        ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
        int attribute = 0;
        if (held.getItem() instanceof WeaponItem weaponItem) {
            attribute = weaponItem.weapon_attribute.getattributeInt();
        }

        // 生成子弹并绑定
        if(held.getItem() instanceof Weapon_1_1) {
            var e = WandBullet1_1.spawn(level, player, attribute, start, motion);
            BoundEntityManager_NormalAttack.bind(player, e);
        } else {
            var e = WandNormalAttackBullet.spawn(level, player, attribute, start, motion);
            BoundEntityManager_NormalAttack.bind(player, e);
        }


        // 动作反馈
        player.swing(InteractionHand.MAIN_HAND);
        player.resetAttackStrengthTicker();
    }
}