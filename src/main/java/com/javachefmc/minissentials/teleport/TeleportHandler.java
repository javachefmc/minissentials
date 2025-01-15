package com.javachefmc.minissentials.teleport;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
    public static Boolean isSafe(ServerLevel serverLevel, double x, double y, double z){
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
    
    public static int teleportPlayer(ServerPlayer player, ServerLevel serverLevel, double x, double y, double z, float rot_x, float rot_y){
        // Get name
        String name = player.getScoreboardName();
        
        // Get if teleport location is safe
        if (isSafe(serverLevel, x, y, z)) {
            // Send chats and logs
            Minissentials.chatToSender(player, "Teleporting to &b" + name + "&r...");
            Minissentials.log("Teleporting player " + player.getName() + " to home " + name);
            
            // Teleport the player
            // TODO: Use function that sets correct dimension

            Minissentials.log("Warning: using temporary teleport function that does not handle dimensions");
            
            player.teleportTo(x, y, z);
            player.setXRot(rot_x);
            player.setYRot(rot_y);
            
        } else {
            // Not safe
            Minissentials.chatToSender(player, "&b" + name + "&r is not safe");
        }
        
        return 1;
    }
    
    public static JsonObject getCoordinateData(ServerPlayer player){
        // Serialize coordinates
        JsonObject coordinates = new JsonObject();
        // position
        coordinates.addProperty("x", player.position().x);
        coordinates.addProperty("y", player.position().y);
        coordinates.addProperty("z", player.position().z);
        // rotation
        coordinates.addProperty("rot_x", player.getXRot());
        coordinates.addProperty("rot_y", player.getYRot());
        // dimension
        coordinates.addProperty("dimension", player.level().toString());
        
        return coordinates;
    }
    
    public static JsonObject createWarpData(ServerPlayer player){
        JsonObject warp = new JsonObject();
        JsonObject coordinates = getCoordinateData(player);
        
        warp.add("coordinates", coordinates);
        warp.addProperty("creator", player.getStringUUID());
        warp.addProperty("created", "<timestamp>");
        
        return warp;
    }
}
