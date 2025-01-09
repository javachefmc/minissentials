package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.chat.ChatHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class DelGlobalWarp {
    /*


    Sets warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("delglobalwarp")
                .then(Commands.argument("name", StringArgumentType.word()).executes(DelGlobalWarp::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Do something
        Minissentials.chatToSender(context, "Deleting global warp: " + context.getArgument("name", String.class));
        return 1;
    }
}
