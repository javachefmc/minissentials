package com.javachefmc.minissentials.data;

import net.minecraft.nbt.CompoundTag;

public interface IEntityDataSaver {
    /*


    Stand-in interface to access NBT data


     */
    CompoundTag getPersistentData();
}
