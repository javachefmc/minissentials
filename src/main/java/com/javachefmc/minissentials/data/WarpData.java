package com.javachefmc.minissentials.data;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WarpData {
    public String name;
    public Vec3 coordinates;
    public Object dimension;

    public enum Dimension{
        OVERWORLD,
        NETHER,
        END
    }

    public static Dimension getDimension(ResourceKey<Level> dimension){

        return Dimension.OVERWORLD;
    }

    public boolean validate(){
        if (name != null && coordinates != null && dimension != null) {
            return true;
        }
        return false;
    }
}
