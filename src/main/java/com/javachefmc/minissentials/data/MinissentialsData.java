package com.javachefmc.minissentials.data;

import com.javachefmc.minissentials.Minissentials;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.*;
import java.time.LocalDateTime;

public class MinissentialsData {
    /*


    Manages player JSON data


     */
    public static String PLAYERDATA_DIR = FabricLoader.getInstance().getGameDir() + "/minissentials/data/players";
    public static String WORLDDATA_DIR = FabricLoader.getInstance().getGameDir() + "/minissentials/data/world";
    public static String DATAFILE_EXTENSION = ".json";

    // Enum to avoid errors in typing file names
    public enum PlayerDataFileType {
        stats,
        warps,
        messages
    }
    public enum WorldDataFileType {
        warps
    }

    public static File getPlayerDataFolder(ServerPlayer player){
        String UUID = player.getStringUUID();
        return new File(PLAYERDATA_DIR + "/" + UUID);
    }

    public static File getPlayerDataFile(ServerPlayer player, PlayerDataFileType fileType){
        String UUID = player.getStringUUID();
        return new File(PLAYERDATA_DIR + "/" + UUID + "/" + fileType + DATAFILE_EXTENSION);
    }

    public static File getWorldDataFolder(){
        return new File(WORLDDATA_DIR);
    }

    public static File getWorldDataFile(WorldDataFileType fileType){
        return new File(WORLDDATA_DIR + "/" + fileType + DATAFILE_EXTENSION);
    }

    public static JSONObject getPlayerData(ServerPlayer player, PlayerDataFileType fileType) {
        JSONObject data = new JSONObject();

        File dataFile = getPlayerDataFile(player, fileType);

        try {
            if (!dataFile.exists()) {
                Minissentials.chatToSender(player, "File does not exist: " + fileType.toString());
            } else {
                data = (JSONObject) new JSONParser().parse(new FileReader(dataFile));
            }
        } catch(Exception e) {
            Minissentials.chatToSender(player, "Could not access the file: " + fileType.toString());
        }

        return data;
    }

    public static boolean setPlayerData(ServerPlayer player, PlayerDataFileType fileType, JSONObject data) {
        // Check if data folder exists; create if not
        File dataFolder = getPlayerDataFolder(player);
        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        File dataFile = getPlayerDataFile(player, fileType);

        try {
            FileWriter file = new FileWriter(dataFile);
            file.write(data.toJSONString());
            file.close();
            return true;
        } catch (IOException e) {
//            e.printStackTrace();
            Minissentials.chatToSender(player, "An error occurred while saving your stats.");
            return false;
        }
    }

    public static JSONObject getWorldData(WorldDataFileType fileType) {
        JSONObject data = new JSONObject();

        File dataFile = getWorldDataFile(fileType);

        try {
            if (!dataFile.exists()) {
                Minissentials.log("File does not exist: " + fileType.toString());
            } else {
                data = (JSONObject) new JSONParser().parse(new FileReader(dataFile));
            }
        } catch(Exception e) {
            Minissentials.log("Could not access the file: " + fileType.toString());
        }

        return data;
    }

    public static boolean setWorldData(WorldDataFileType fileType, JSONObject data) {
        // Check if data folder exists; create if not
        File dataFolder = getWorldDataFolder();
        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        File dataFile = getWorldDataFile(fileType);

        try {
            FileWriter file = new FileWriter(dataFile);
            file.write(data.toJSONString());
            file.close();
            return true;
        } catch (IOException e) {
//          e.printStackTrace();
            Minissentials.log("An error occurred while saving Minissentials world data");
            return false;
        }
    }

    public static void init(){
        Minissentials.log("Creating Minissentials folder structure");

        File dataFolder = getWorldDataFolder();

        /* Files:
        - warps
            - spawn
            - world-available warps
         */

        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        // Serialize JSON

        JSONObject data = new JSONObject();
        data.put("spawn", "[0,300,0]");

        // Try to write file
        try {
            FileWriter file = new FileWriter(getWorldDataFile(WorldDataFileType.warps));
            file.write(data.toJSONString());
            file.close();
        } catch (IOException e) {
//            e.printStackTrace();
            Minissentials.log("An error occurred while saving Minissentials world data.");
        }
        Minissentials.log(DATAFILE_EXTENSION + " file created: " + data);
//        Minissentials.chatToSender(player, "Files created.");
    }

    public static void initPlayer(ServerPlayer player) {
        // Serialize data
        String UUID = player.getStringUUID();
        String username = player.getName().getString();

        Minissentials.log("Creating file for player " + username + " with UUID " + UUID);

        // Set up file for player
        File dataFolder = getPlayerDataFolder(player);

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
            FileWriter file = new FileWriter(getPlayerDataFile(player, PlayerDataFileType.stats));
            file.write(stats.toJSONString());
            file.close();
        } catch (IOException e) {
//            e.printStackTrace();
            Minissentials.chatToSender(player, "An error occurred while saving your stats.");
        }
        Minissentials.log(DATAFILE_EXTENSION + " file created: " + stats);
        Minissentials.chatToSender(player, "Files created.");
    }

    public double[] getSpawnPos(){
        double[] spawnPos = new double[3];
        spawnPos[0] = 0;
        spawnPos[1] = 300;
        spawnPos[2] = 0;

        return spawnPos;
    }
}
