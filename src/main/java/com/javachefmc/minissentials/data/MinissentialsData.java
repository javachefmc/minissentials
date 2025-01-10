package com.javachefmc.minissentials.data;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;

public class MinissentialsData {
    /*


    Manages player and world JSON data


     */
    public static String PLAYERDATA_DIR = FabricLoader.getInstance().getGameDir() + "/minissentials/data/players";
    public static String WORLDDATA_DIR = FabricLoader.getInstance().getGameDir() + "/minissentials/data/world";
    public static String DATAFILE_EXTENSION = ".json";

    // Enum to avoid errors in typing file names
    public enum PlayerDataFileType {
        stats,
        homes,
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

    public static JsonObject getJsonFromFile(File file){
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean createFileIfNotExist(File file){
        if(!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                return false;
//                throw new RuntimeException(e);
            }
        } else {
            return true;
        }
    }

    public static JsonObject getPlayerData(ServerPlayer player, PlayerDataFileType fileType) {
        File dataFile = getPlayerDataFile(player, fileType);

        JsonObject data = null;
        try {
            if (!dataFile.exists()) {
                Minissentials.chatToSender(player, "File does not exist: " + fileType.toString());
            } else {
                data = getJsonFromFile(dataFile);
            }
        } catch(Exception e) {
            Minissentials.chatToSender(player, "Could not access the file: " + fileType.toString());
        }

        return data;
    }

    public static boolean setPlayerData(ServerPlayer player, PlayerDataFileType fileType, JsonObject data) {
        // Check if data folder exists; create if not
        File dataFolder = getPlayerDataFolder(player);
        if(!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dataFile = getPlayerDataFile(player, fileType);

        try {
            FileWriter file = new FileWriter(dataFile);
            file.write(data.toString());
            file.close();
            return true;
        } catch (IOException e) {
//            e.printStackTrace();
            Minissentials.chatToSender(player, "An error occurred while saving your stats.");
            return false;
        }
    }

    public static JsonObject getWorldData(WorldDataFileType fileType) {
        File dataFile = getWorldDataFile(fileType);

        JsonObject data = null;
        try {
            if (!dataFile.exists()) {
                Minissentials.log("File does not exist: " + fileType.toString());
            } else {
                data = getJsonFromFile(dataFile);
            }
        } catch(Exception e) {
            Minissentials.log("Could not access the file: " + fileType.toString());
        }

        return data;
    }

    public static boolean setWorldData(WorldDataFileType fileType, JsonObject data) {
        // Check if data folder exists; create if not
        File dataFolder = getWorldDataFolder();
        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        File dataFile = getWorldDataFile(fileType);

        try {
            FileWriter file = new FileWriter(dataFile);
            file.write(data.toString());
            file.close();
            return true;
        } catch (IOException e) {
//          e.printStackTrace();
            Minissentials.log("An error occurred while saving Minissentials world data");
            return false;
        }
    }

    public static void init(){
        Minissentials.log("Creating Minissentials global folder structure");

        File dataFolder = getWorldDataFolder();

        /* Files:
        - warps
            - spawn
            - world-available warps
         */

        if(!dataFolder.exists()) { dataFolder.mkdirs(); }

        // Serialize JSON
        // TODO: THIS SPAWN TELEPORT FORMAT IS VERY BAD
        JsonObject data = new JsonObject();
        data.addProperty("spawn", "[0,300,0]");

        // Try to write file
        try {
            FileWriter file = new FileWriter(getWorldDataFile(WorldDataFileType.warps));
            file.write(data.toString());
            file.close();
            Minissentials.log(DATAFILE_EXTENSION + " file created: " + data);
        } catch (IOException e) {
//            e.printStackTrace();
            Minissentials.log("An error occurred while saving Minissentials world data.");
        }


    }

    public static void initPlayer(ServerPlayer player) {
        // Serialize data
        String UUID = player.getStringUUID();
        String username = player.getName().getString();

        Minissentials.log("Creating file for player " + username + " with UUID " + UUID);

        // Set up folder for player
        File dataFolder = getPlayerDataFolder(player);

        /* Files:
        - homes
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

        if(!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Set up files

        File statsFile = getPlayerDataFile(player, PlayerDataFileType.stats);
        File messagesFile = getPlayerDataFile(player, PlayerDataFileType.messages);
        File homesFile = getPlayerDataFile(player, PlayerDataFileType.homes);

        createFileIfNotExist(statsFile);
        createFileIfNotExist(messagesFile);
        createFileIfNotExist(homesFile);

        // Serialize JSON

        JsonObject stats = new JsonObject();
        stats.addProperty("username", username);
        stats.addProperty("nick", "");
        stats.addProperty("last_login", LocalDateTime.now().toString());
        stats.addProperty("first_login", LocalDateTime.now().toString());

        JsonObject messages = new JsonObject();
        JsonObject homes = new JsonObject();

        try {
            FileWriter statsFileWriter = new FileWriter(statsFile);
            statsFileWriter.write(stats.toString());
            statsFileWriter.close();

            FileWriter homesFileWriter = new FileWriter(homesFile);
            homesFileWriter.write(homes.toString());
            homesFileWriter.close();

            FileWriter messagesFileWriter = new FileWriter(messagesFile);
            messagesFileWriter.write(messages.toString());
            messagesFileWriter.close();

        } catch (IOException e) {
//            e.printStackTrace();
            Minissentials.chatToSender(player, "An error occurred while saving player files.");
        }

//        Minissentials.log(DATAFILE_EXTENSION + " file created: " + stats);
//        Minissentials.chatToSender(player, "Files created.");
    }

    public double[] getSpawnPos(){
        double[] spawnPos = new double[3];
        spawnPos[0] = 0;
        spawnPos[1] = 300;
        spawnPos[2] = 0;

        return spawnPos;
    }
}
