package com.tearhpi.immortal.util;

import com.tearhpi.immortal.entity.custom.TrainerDummy;
import net.minecraft.world.entity.Entity;

public class OutPutDamageInfo {
    public static boolean outPutDamageInfo = true;
    public static boolean getOutPutDamageInfo(Entity entity) {
        if (entity instanceof TrainerDummy) {
            return true;
        }
        return false;
    }
}
