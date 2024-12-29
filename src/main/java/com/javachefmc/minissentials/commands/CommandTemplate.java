package com.javachefmc.minissentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandTemplate {
    /*


    This is a command template


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("commandtemplate").executes(CommandTemplate::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Do something

        return 1;
    }
}
