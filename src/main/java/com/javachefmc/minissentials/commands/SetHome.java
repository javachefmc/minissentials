package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.javachefmc.minissentials.data.WarpData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class SetHome {
    /*


    Sets warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("sethome").executes(SetHome::runSimple)
                .then(Commands.argument("name", StringArgumentType.word()).executes(SetHome::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        // Get current home data
        JsonObject homes = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);

        // Serialize new warp data
        String name = context.getArgument("name", String.class);
        String dimension = String.valueOf(WarpData.getDimension(context.getSource().getLevel().dimension()));

        // Serialize coordinates
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("x", player.position().x);
        coordinates.addProperty("y", player.position().y);
        coordinates.addProperty("z", player.position().z);

        // TODO: Also include rotation data in warp

        // Serialize home data
        JsonObject home = new JsonObject();
        home.add("coordinates", coordinates);
        home.addProperty("dimension", dimension);
        home.addProperty("creator", player.getStringUUID());
        home.addProperty("created", "<timestamp>");

        if (!homes.has(name)){
            // Home does not exist yet

            homes.add(name, home);
            MinissentialsData.setPlayerData(player, MinissentialsData.PlayerDataFileType.homes, homes);

            Minissentials.chatToSender(context, "Set home &b" + name + "&r at &e[" + coordinates + "]");

        } else {
            Minissentials.chatToSender(context, "You already have a home called &b" + name + "&r!");
        }

        return 1;
    }

    private static int runSimple(CommandContext<CommandSourceStack> context){
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        // Get current home data
        JsonObject homes = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);

        // Serialize new warp data
        String dimension = String.valueOf(WarpData.getDimension(context.getSource().getLevel().dimension()));

        // Serialize coordinates
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("x", player.position().x);
        coordinates.addProperty("y", player.position().y);
        coordinates.addProperty("z", player.position().z);

        // TODO: Also include rotation data in warp

        // Serialize home data
        JsonObject home = new JsonObject();
        home.add("coordinates", coordinates);
        home.addProperty("dimension", dimension);
        home.addProperty("creator", player.getStringUUID());
        home.addProperty("created", "<timestamp>");

        if (!homes.has("Home")){
            // Home does not exist yet

            homes.add("Home", home);
            MinissentialsData.setPlayerData(player, MinissentialsData.PlayerDataFileType.homes, homes);

            Minissentials.chatToSender(context, "Your home has been set to &e[" + coordinates + "]");

        } else {
            Minissentials.chatToSender(context, "You already have a home called Home");
        }

        return 1;
    }
}
