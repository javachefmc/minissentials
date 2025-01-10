package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.Minissentials;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Homes {
    /*


    List warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("homes").executes(Homes::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Do something
        Minissentials.chatToSender(context, "Listing homes");
        return 1;
    }
}
