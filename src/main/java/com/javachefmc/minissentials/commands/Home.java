package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.javachefmc.minissentials.teleport.TeleportHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

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

        if (homes.has("Home")){
            // Home exists

            // Get coordinates
            JsonObject coordinates = homes.getAsJsonObject("Home").getAsJsonObject("coordinates");
            double x = coordinates.get("x").getAsDouble();
            double y = coordinates.get("y").getAsDouble();
            double z = coordinates.get("z").getAsDouble();

            // Teleport to location if safe
            if(TeleportHandler.isSafe(new Vec3(x, y, z))) {
                Minissentials.chatToSender(context, "Teleporting home...");
                Minissentials.log("Teleporting player " + player.getName() + " home");
                player.teleportTo(x, y, z);
            } else {
                Minissentials.chatToSender(context, "Your home location is not safe");
            }
        } else {
            Minissentials.chatToSender(context, "You do not have a home named Home");
        }
        return 1;
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Get player
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        // Get homes
        JsonObject homes = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);
        String name = context.getArgument("name", String.class); // TODO: Make case insensitive

        if (homes.has(name)){
            // Home exists

            // Get coordinates
            JsonObject coordinates = homes.getAsJsonObject(name).getAsJsonObject("coordinates");
            double x = coordinates.get("x").getAsDouble();
            double y = coordinates.get("y").getAsDouble();
            double z = coordinates.get("z").getAsDouble();

            // Teleport to location if safe
            if(TeleportHandler.isSafe(new Vec3(x, y, z))) {
                Minissentials.chatToSender(context, "Teleporting to &b" + name + "&r...");
                Minissentials.log("Teleporting player " + player.getName() + " to home " + name);
                player.teleportTo(x, y, z);
            } else {
                Minissentials.chatToSender(context, "&b" + name + "&r is not safe");
            }
        } else {
            Minissentials.chatToSender(context, "You do not have a home called &b" + name + "&r");
        }
        return 1;
    }
}
