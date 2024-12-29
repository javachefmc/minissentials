package com.javachefmc.minissentials.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;


public class WarpData {
    /*


    Temporary file that will be replaced with JSON file handler


     */
    public static CompoundTag setSpawn(IEntityDataSaver player, Vec3 pos, Vec2 rot) {
        CompoundTag nbt = player.getPersistentData();

        CompoundTag spawnPos = new CompoundTag();
        spawnPos.putDouble("X", pos.x);
        spawnPos.putDouble("Y", pos.y);
        spawnPos.putDouble("Z", pos.z);
        spawnPos.putFloat("rotX", rot.x);
        spawnPos.putFloat("rotY", rot.y);

        nbt.put("spawnPos", spawnPos);

//        nbt.putIntArray("spawnPos", new double[]{
//                (int) Math.round(pos.x),
//                (int) Math.round(pos.y),
//                (int) Math.round(pos.z)
//        });

        // sync data
        return spawnPos;
    }

}
