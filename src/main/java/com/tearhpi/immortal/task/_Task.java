package com.tearhpi.immortal.task;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class _Task {
    public int id; //唯一id
    public Component Name;//名字
    public Component Description;//介绍
    public Vec3 Target;//目标
    public List<ItemStack> Reward;//奖励
    public _Task(int id, Component Name, Component Description,Vec3 Target, List<ItemStack> Reward) {
        this.id = id;
        this.Name = Name;
        this.Description = Description;
        this.Target = Target;
        this.Reward = Reward;
    }
}
