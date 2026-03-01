package com.tearhpi.immortal.worlddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class BranchTaskWorldData extends SavedData {

    public int branch1_1 = 0;
    public int branch1_2 = 0;
    public int branch1_3 = 0;

    private static final String NAME = "immortal_branchTaskWorldData";

    public static BranchTaskWorldData load(CompoundTag tag) {
        BranchTaskWorldData data = new BranchTaskWorldData();
        data.branch1_1 = tag.getInt("branch1_1");
        data.branch1_2 = tag.getInt("branch1_2");
        data.branch1_3 = tag.getInt("branch1_3");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("branch1_1", branch1_1);
        tag.putInt("branch1_2", branch1_2);
        tag.putInt("branch1_3", branch1_3);
        return tag;
    }

    public static BranchTaskWorldData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                BranchTaskWorldData::load,
                BranchTaskWorldData::new,
                NAME
        );
    }
}
/** 写变量
 * data.worldStage++;
 * data.bossDefeated = true;
 * data.setDirty();
 * 读变量
 * ServerLevel level = (ServerLevel) player.level();
 * ImmortalWorldData data = ImmortalWorldData.get(level);
 * int stage = data.worldStage;
 * boolean killed = data.bossDefeated;
 */
