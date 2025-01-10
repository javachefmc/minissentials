package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minidev.json.JSONObject;

import static com.javachefmc.minissentials.data.MinissentialsData.setPlayerData;

public class Nick {
    /*


    Sets/gets nickname


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("nick").executes(Nick::getNick)
                .then(Commands.argument("nickname", StringArgumentType.greedyString()).executes(Nick::setNick)));
    }

    private static int getNick(CommandContext<CommandSourceStack> context){
        ServerPlayer player = context.getSource().getPlayer();
        JsonObject playerStats = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.stats);
        String nick = playerStats.get("nick").toString();

        if (!nick.isEmpty()){
            Minissentials.chatToSender(context, "Your nickname is " + nick);
        } else {
            Minissentials.chatToSender(context, "You do not have a nickname");
        }

        return 1;
    }

    private static int setNick(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        JsonObject playerStats = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.stats);

        // Get arg
        String nickname = context.getArgument("nickname", String.class);

        // Change the nick in player data
        playerStats.addProperty("nick", nickname);

        // Try to set the data
        if (setPlayerData(player, MinissentialsData.PlayerDataFileType.stats, playerStats)){
            Minissentials.chatToSender(context, "Changed nickname to " + nickname);
        } else {
            Minissentials.chatToSender(context, "An error occurred while setting your nickname.");
            return 0;
        }

        return 1;
    }
}
