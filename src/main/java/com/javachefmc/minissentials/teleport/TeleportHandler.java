package com.javachefmc.minissentials.teleport;

import com.javachefmc.minissentials.Minissentials;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class TeleportHandler {
    /*


    Miscellaneous functions for handling teleportation


     */
    public static Boolean isSafe(Vec3 location) {
        // TODO: actually code this
        Minissentials.log("WARNING: Using placeholder for teleport location safing");
        return true;
    }

    public static Boolean isSafe(BlockPos location) {
        // TODO: actually code this
        Minissentials.log("WARNING: Using placeholder for teleport location safing");
        return true;
    }

    public Vec3 getSafeLocation(Vec3 location){
        if(isSafe(location)){
            return location;
        } else {
            // TODO: actually code this
            return location;
        }
    }
}
