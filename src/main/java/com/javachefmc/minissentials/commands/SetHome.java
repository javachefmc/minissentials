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

        // Serialize new data data
        String name = context.getArgument("name", String.class);
        JsonObject home = TeleportHandler.createWarpData(player);

        if (!homes.has(name)){
            // Home does not exist yet
            homes.add(name, home);
            MinissentialsData.setPlayerData(player, MinissentialsData.PlayerDataFileType.homes, homes);
            Minissentials.chatToSender(context, "Set home &b" + name);
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
        JsonObject home = TeleportHandler.createWarpData(player);
        
        // TODO: this should probably overwrite
        if (!homes.has("Home")){
            // Home does not exist yet
            homes.add("Home", home);
            MinissentialsData.setPlayerData(player, MinissentialsData.PlayerDataFileType.homes, homes);
            Minissentials.chatToSender(context, "Your home has been set");
        } else {
            Minissentials.chatToSender(context, "You already have a home called Home");
        }

        return 1;
    }
}
