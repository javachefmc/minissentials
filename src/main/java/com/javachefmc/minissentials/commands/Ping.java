package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.chat.ChatHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Ping {
    /*


    Pong!


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("ping").executes(Ping::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        Minissentials.chatToSender(context, "Pong!");
        return 1;
    }
}
