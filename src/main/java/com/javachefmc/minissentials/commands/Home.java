package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.javachefmc.minissentials.data.WarpData;
import com.javachefmc.minissentials.teleport.TeleportHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import static com.javachefmc.minissentials.teleport.TeleportHandler.getPlayerRelativeLevel;

public class Home {
    /*


    Teleport to home


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("home").executes(Home::runSimple)
                .then(Commands.argument("name", StringArgumentType.word()).executes(Home::run)));
    }

    private static int runSimple(CommandContext<CommandSourceStack> context){
        // Get player
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        // Get homes
        JsonObject homes = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);

        // TODO: THIS ISN'T WORKING
        if (homes.has("Home")){
            // Home exists

            // Get coordinates
            JsonObject coordinates = homes.getAsJsonObject("Home").getAsJsonObject("coordinates");
            double x = coordinates.get("x").getAsDouble();
            double y = coordinates.get("y").getAsDouble();
            double z = coordinates.get("z").getAsDouble();
            float rot_x = coordinates.get("rot_x").getAsFloat();
            float rot_y = coordinates.get("rot_y").getAsFloat();
            ServerLevel level = getPlayerRelativeLevel(player, coordinates.get("level").getAsString());
            
            // Attempt teleport
            TeleportHandler.teleportPlayer(player, level, x, y, z, rot_x, rot_y);
        } else {
            Minissentials.chatToSender(context, "You do not have a home named Home");
        }
        return 1;
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Get player
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        String name = context.getArgument("name", String.class); // TODO: Make case insensitive

        // Get coordinates
        WarpData c = new WarpData(player, name, MinissentialsData.PlayerDataFileType.homes);
        if (c.exists) {
            // Attempt teleport
            TeleportHandler.teleportPlayer(player, c.level, c.x, c.y, c.z, c.rot_x, c.rot_y);
        } else {
            Minissentials.chatToSender(context, "You do not have a home called &b" + name + "&r");
        }
        
        return 1;
    }
}
