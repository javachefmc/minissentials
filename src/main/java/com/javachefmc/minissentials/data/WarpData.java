package com.javachefmc.minissentials.data;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

import static com.javachefmc.minissentials.teleport.TeleportHandler.getPlayerRelativeLevel;

/*

Serializes teleport-related data to avoid errors

 */

public class WarpData {
    public String name;
    public double x;
    public double y;
    public double z;
    public float rot_x;
    public float rot_y;
    public ServerLevel level;
    public boolean exists = false;
    
    // Sets common data for warp
    public WarpData(JsonObject coordinates){
        x = coordinates.get("x").getAsDouble();
        y = coordinates.get("y").getAsDouble();
        z = coordinates.get("z").getAsDouble();
        rot_x = coordinates.get("rot_x").getAsFloat();
        rot_y = coordinates.get("rot_y").getAsFloat();
        level = null;
    }

    // Sets warp given coordinates JSON and ServerPlayer
    public WarpData(JsonObject coordinates, ServerPlayer player){
        assert player != null;
        new WarpData(coordinates);
        level = getPlayerRelativeLevel(player, coordinates.get("level").getAsString());
    }

    // Sets warp given JsonObject to search, key name, and ServerPlayer
    public WarpData(JsonObject search, String name, ServerPlayer player){
        assert player != null;
        if (search.has(name)) {
            JsonObject coordinates = search.getAsJsonObject(name).getAsJsonObject("coordinates");
            new WarpData(coordinates, player);
        }
    }

    // Sets warp data from home
    public WarpData(ServerPlayer player, String name, MinissentialsData.PlayerDataFileType playerDataFileType){
        assert player != null;
        
        if (Objects.requireNonNull(playerDataFileType) == MinissentialsData.PlayerDataFileType.homes) {
            JsonObject homes = getHomes(player);

            new WarpData(getHomes(player), name, player);

            if(homes.has(name)) this.exists = true;
        }
    }

    // Sets warp data from public warp
    public WarpData(ServerPlayer player, String name, MinissentialsData.WorldDataFileType worldDataFileType){
        assert player != null;
        
        if (Objects.requireNonNull(worldDataFileType) == MinissentialsData.WorldDataFileType.warps) {
            JsonObject warps = getWarps();
            
            new WarpData(getWarps(), name, player);

            if(warps.has(name)) this.exists = true;
        }
    }
    
    public static JsonObject getHomes(ServerPlayer player){
        return MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);
    }
    
    public static JsonObject getWarps(){
        return MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);
    }

    
}
