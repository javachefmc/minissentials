package com.javachefmc.minissentials.data;

import com.javachefmc.minissentials.Minissentials;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.*;
import java.time.LocalDateTime;

public class MinissentialsData {
    /*


    Manages JSON data


     */
    public static String DATA_DIR = FabricLoader.getInstance().getGameDir() + "/minissentials_data";

    // Enum to avoid errors in typing file names
    public enum DataFileType {
        stats,
        warps,
        messages
    }

    public static File getDataFolder(ServerPlayer player){
        String UUID = player.getStringUUID();
        return new File(DATA_DIR + "/" + UUID);
    }

    public static File getDataFile(ServerPlayer player, DataFileType fileType){
        String UUID = player.getStringUUID();
        return new File(DATA_DIR + "/" + UUID + "/" + fileType + ".json");
    }

    public static JSONObject getData(ServerPlayer player, DataFileType fileType) {
        JSONObject data = new JSONObject();

        File dataFile = getDataFile(player, fileType);

        try {
            if (!dataFile.exists()) {
                player.sendSystemMessage(Component.literal("File does not exist: " + fileType.toString()));
            } else {
                data = (JSONObject) new JSONParser().parse(new FileReader(dataFile));
            }
        } catch(Exception e) {
            player.sendSystemMessage(Component.literal("Could not access the file: " + fileType.toString()));
        }

        return data;
    }

    public static boolean setData(ServerPlayer player, DataFileType fileType, JSONObject data) {

        // Check if data folder exists; create if not
        File dataFolder = getDataFolder(player);
        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        File dataFile = getDataFile(player, fileType);

        try {
            FileWriter file = new FileWriter(dataFile);
            file.write(data.toJSONString());
            file.close();
            return true;
        } catch (IOException e) {
//            e.printStackTrace();
            player.sendSystemMessage(Component.literal("An error occurred while saving your stats."));
            return false;
        }
    }

    public static void init(ServerPlayer player) {
        // Serialize data
        String UUID = player.getStringUUID();
        String username = player.getName().getString();

        Minissentials.log("Creating file for player " + username + " with UUID " + UUID);

        // Set up file for player
        File dataFolder = getDataFolder(player);

        /* Files:
        - warps
            - spawn
            - personal warps
        - stats
            - real name
            - nickname
            - last logged in time
            - last position
        - mail (inbox)
            - received time (server)
            - sender (uuid)
            - message
         */

        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        // Serialize JSON

        JSONObject stats = new JSONObject();
        stats.put("username", username);
        stats.put("nick", "");
        stats.put("last_login", LocalDateTime.now().toString());
        stats.put("first_login", LocalDateTime.now().toString());

        try {
            FileWriter file = new FileWriter(getDataFile(player, DataFileType.stats));
            file.write(stats.toJSONString());
            file.close();
        } catch (IOException e) {
//            e.printStackTrace();
            player.sendSystemMessage(Component.literal("An error occurred while saving your stats."));
        }
        Minissentials.log("JSON file created: " + stats);
        player.sendSystemMessage(Component.literal("Files created."));
    }

    public double[] getSpawnPos(){
        double[] spawnPos = new double[3];
        spawnPos[0] = 0;
        spawnPos[1] = 300;
        spawnPos[2] = 0;

        return spawnPos;
    }
}
