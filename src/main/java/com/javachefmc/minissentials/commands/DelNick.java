package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minidev.json.JSONObject;

import static com.javachefmc.minissentials.data.MinissentialsData.setData;

public class DelNick {
    /*


    Deletes nickname


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("delnick").executes(DelNick::delNick));
    }

    private static int delNick(CommandContext<CommandSourceStack> context){
        ServerPlayer player = context.getSource().getPlayer();
        JSONObject playerStats = MinissentialsData.getData(player, MinissentialsData.DataFileType.stats);
        String nick = playerStats.get("nick").toString();

        playerStats.put("nick",""); // Changes the nick in data

        // Try to set the data
        if (setData(player, MinissentialsData.DataFileType.stats, playerStats)){
            Minissentials.chatToSender(context, "Removed nickname: " + nick);
        } else {
            Minissentials.chatToSender(context, "An error occurred while resetting your nickname.");
        }
        

        return 1;
    }
}
