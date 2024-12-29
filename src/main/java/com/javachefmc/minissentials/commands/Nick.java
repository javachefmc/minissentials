package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minidev.json.JSONObject;

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
        JSONObject playerStats = MinissentialsData.getData(player, MinissentialsData.DataFileType.stats);
        String nick = playerStats.get("nick").toString();
        context.getSource().sendSuccess(() -> Component.literal("Your nick is " + nick), false);
        return 1;
    }

    private static int setNick(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("It looks like you are trying to change your nick..."), false);
        return 1;
    }
}
