package com.tearhpi.immortal.entity.capability;


import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.LinkedList;

public class Chatbox {
    public int Chatbox_id = -1;
    public int Chatbox_num = -1;
    public int Chatbox_countdown = 0;
    public static int Chatbox_countdown_MAX = 10;

    public Chatbox() {}

    public void set(int id, int num,int countdown) {
        this.Chatbox_id = id;
        this.Chatbox_num = num;
        this.Chatbox_countdown = countdown;
    }
    public void continuePage() {
        this.Chatbox_num += 1;
    }
    public void removeCountdown() {
        if (Chatbox_countdown > 0) {
            Chatbox_countdown--;
        }
    }
    public void reset(){
        this.Chatbox_id = -1;
        this.Chatbox_num = -1;
        this.Chatbox_countdown = 0;
    }
    public int getID() {
        return this.Chatbox_id;
    }
    public int getNum() {
        return this.Chatbox_num;
    }
    public int getCountdown() {
        return this.Chatbox_countdown;
    }

    public void readAdditionalSaveData(CompoundTag p_38716_) {
        if (p_38716_.contains("chatbox_id",99)) {
            this.Chatbox_id = p_38716_.getInt("chatbox_id");
        }
        if (p_38716_.contains("chatbox_num",99)) {
            this.Chatbox_num = p_38716_.getInt("chatbox_num");
        }
        if (p_38716_.contains("chatbox_countdown",99)) {
            this.Chatbox_countdown = p_38716_.getInt("chatbox_countdown");
        }
    }

    public void addAdditionalSaveData(CompoundTag p_38720_,int id, int num, int countdown) {
        p_38720_.putInt("chatbox_id", id);
        p_38720_.putInt("chatbox_num", num);
        p_38720_.putInt("chatbox_countdown", countdown);
    }
}
