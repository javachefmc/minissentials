package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

import static com.javachefmc.minissentials.Minissentials.chatToSender;

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

        chatToSender(context, "Initialized Minissentials for UUID " + player.getStringUUID());

        return 1;
    }
}
