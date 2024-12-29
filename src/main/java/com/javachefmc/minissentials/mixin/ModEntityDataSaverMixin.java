package com.javachefmc.minissentials.mixin;

import com.javachefmc.minissentials.data.IEntityDataSaver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {
    /*


    Mixin injects into Entity.class
    Adds functionality for reading and writing custom NBT data to the entity


     */
    private CompoundTag persistentData;

    @Override
    public CompoundTag getPersistentData() {
        if (this.persistentData == null){
            this.persistentData = new CompoundTag();
        }

        return persistentData;
    }

    // Write custom NBT data
    // Fabric "writeNbt" in "saveSelfNbt" -> Mojmap "saveWithoutId" in "saveAsPassenger"
    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    protected void injectWriteMethod(CompoundTag compoundTag, CallbackInfoReturnable info) {
        if(persistentData != null) {
            compoundTag.put("MinissentialsData", persistentData);
        }
    }

    // Read custom NBT data
    // Fabric "readNbt" -> Mojmap "load"
    @Inject(method = "load", at = @At("HEAD"))
    protected void injectReadMethod(CompoundTag compoundTag, CallbackInfo ci) {
        if(compoundTag.contains("MinissentialsData", 10)) {
            persistentData = compoundTag.getCompound("MinissentialsData");
        }
    }

}
