package com.tearhpi.immortal.task;

import com.tearhpi.immortal.entity.capability.IImmortalPlayer;
import com.tearhpi.immortal.networking.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.lang.reflect.Field;
import java.util.List;

//任务注册
public class ModMainTask {
    //主线
    public static _MainTask MainTask1 = new _MainTask(1, Component.translatable("MTask1_Name"), Component.translatable("MTask1_Description"),new Vec3(0.0,0.0,0.0),100,
            List.of(new ItemStack(Items.DIAMOND,64),new ItemStack(Items.GOLD_BLOCK,64),new ItemStack(Items.IRON_INGOT,64)));
    public static _MainTask MainTask2 = new _MainTask(2, Component.translatable("MTask2_Name"), Component.translatable("MTask2_Description"),new Vec3(0.0,0.0,0.0),100,
            List.of(new ItemStack(Items.GOLD_INGOT,64)));

    public static void EndOfMainTask(Player player) {
        //服务端执行
        if (player.level().isClientSide()) return;
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        int index = iImmortalPlayer.getMainTask().get();
        //获取任务 发奖励
        try {
            ModMainTask s = new ModMainTask();
            Field f = s.getClass().getDeclaredField("MainTask"+index);
            f.setAccessible(true);
            _MainTask mainTaskComplete = (_MainTask) f.get(s);
            getRewards(player,mainTaskComplete.Reward, mainTaskComplete.Reward_Coin, index);
        } catch (Exception e) {
            System.out.println("ERROR IN GETTING MAIN TASK");
        }
        iImmortalPlayer.setMainTask(index+1);
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new MainTaskSyncToClientPacket(iImmortalPlayer.getMainTask().get()));
        //继续接任务
        try {
            ModMainTask s = new ModMainTask();
            index++;
            Field f = s.getClass().getDeclaredField("MainTask"+index);
            f.setAccessible(true);
            player.sendSystemMessage(Component.translatable("Task_Accept"));
        } catch (Exception e) {
            player.sendSystemMessage(Component.translatable("MainTask_Complete_All"));
        }

    }

    public static void getRewards(Player player,List<ItemStack> items,int coin_amount,int id) {
        //服务端执行
        if (player.level().isClientSide()) return;
        //发放金币,物品
        //System.out.println("Getting rewards for " + items);
        for (ItemStack itemStack : items) {
            if (!player.getInventory().add(itemStack)) {
                player.drop(itemStack, false);
            }
        }
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getCoin().add(coin_amount);
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),new CoinSyncToClientPacket(iImmortalPlayer.getCoin().get()));
        //特殊任务的特殊奖励
        if (id == 0) {

        }
        //奖励通报
        player.sendSystemMessage(Component.translatable("Task_Complete"));
    }

    public static _MainTask getStaticMainTask(int index) {
        try {
            ModMainTask s = new ModMainTask();
            Field f = s.getClass().getDeclaredField("MainTask"+index);
            f.setAccessible(true);
            return (_MainTask) f.get(s);
        } catch (Exception e) {}
        return null;
    }
}
