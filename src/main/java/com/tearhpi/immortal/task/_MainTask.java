package com.tearhpi.immortal.task;


import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class _MainTask extends _Task {
    public int Reward_Coin;
    public _MainTask(int id, Component Name, Component Description, Vec3 Target,int Reward_Coin, List<ItemStack> Reward) {
        super(id, Name, Description, Target, Reward);
        this.Reward_Coin = Reward_Coin;
    }
}
