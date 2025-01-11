package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public class Homes {
    /*


    List homes


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("homes").executes(Homes::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Get player
        ServerPlayer player = context.getSource().getPlayer();

        // Get warps
        JsonObject homes = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);

        // Get names of warps and chat to player
        Set<String> keys = homes.keySet();
        Minissentials.chatToSender(context, "Your homes: &b" + keys.toString());
        Minissentials.chatToSender(context, "Do &7/home &for &7/home &2<name>&f to teleport to a home");
        Minissentials.chatToSender(context, "Do &7/sethome &for &7/sethome &2<name>&f to create a home");

        return 1;
    }
}
