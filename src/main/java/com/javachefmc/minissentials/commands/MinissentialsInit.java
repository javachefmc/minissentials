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

public class MinissentialsInit {
    /*


    Temporary file for unit testing


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("minissentialsinit").executes(MinissentialsInit::run));
    }

    private static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        ServerPlayer player = context.getSource().getPlayer();

        // run initializer here

        MinissentialsData.init(player);

        context.getSource().sendSuccess(() -> Component.literal("Initialized Minissentials for UUID " + player.getStringUUID()), false);

        return 1;
    }
}
