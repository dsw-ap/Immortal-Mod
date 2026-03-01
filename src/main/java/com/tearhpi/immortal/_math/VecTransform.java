package com.tearhpi.immortal._math;

import net.minecraft.world.phys.Vec3;

public class VecTransform {
    public static Vec3 rotataionTransform(Vec3 RawVec3,Vec3 Transforming){
        double x = RawVec3.x;
        double y = RawVec3.z;
        double z = RawVec3.y;
        double m = Transforming.x;
        double n = Transforming.y;
        double p = Transforming.z;
        double length = Math.sqrt(m*m + n*n);
        if (length != 0.0) {
            double x_ = -m*p*x/length-n*p*y/length+length*z;
            double y_ = n*x/length-m*y/length;
            double z_ = m*x+n*y+p*z;
            return new Vec3(x_,z_,y_);
        } else {
            if (p == 1.0){
                return new Vec3(x, z, y);
            } else if (p == -1.0) {
                return new Vec3(x, -z, -y);
            }
        }
        return null;
    }
}
