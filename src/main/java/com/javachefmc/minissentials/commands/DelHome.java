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

public class DelHome {
    /*


    Removes home


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("delhome").executes(DelHome::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // TODO: UNTESTED

        ServerPlayer player = context.getSource().getPlayer();
        
        // Get current homes
        JsonObject homes = MinissentialsData.getPlayerData(player, MinissentialsData.PlayerDataFileType.homes);

        // Get arg
        String name = context.getArgument("name", String.class);

        // Check if warp exists
        if (homes.has(name)){
            // Serialize JSON of new warp file with warp removed
            homes.remove(name);
            // Store warp data
            MinissentialsData.setPlayerData(player, MinissentialsData.PlayerDataFileType.homes, homes);

            Minissentials.chatToSender(context, "&cDeleted home: &b" + name);
            Minissentials.log("Player deleted home: " + name);
        } else {
            Minissentials.chatToSender(context, "A home called &b" + name + "&r does not exist");
            return 0;
        }

        return 1;
    }
}
