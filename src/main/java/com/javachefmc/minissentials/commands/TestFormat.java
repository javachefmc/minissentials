package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.Minissentials;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class TestFormat {
    /*


    Teleport to home


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("testformat").executes(TestFormat::run)
                .then(Commands.argument("text", StringArgumentType.greedyString()).executes(TestFormat::sayFormatted)));
    }

    private static int run(CommandContext<CommandSourceStack> context) {
        // Do something
        Minissentials.chatToSender(context, "Testing formatting: &00&11&22&33&44&55&66&77&88&99&aa&bb&cc&dd&ee&ff&ll&mm&nn&oo&rr&00&11&22&33&44&55&66&77&88&99&aa&bb&cc&dd&ee&ff&kk&r&ll&r&mm&r&nn&r&oo&r");
        return 1;
    }

    private static int sayFormatted(CommandContext<CommandSourceStack> context) {

        // Get arg
        String text = context.getArgument("text", String.class);

        Minissentials.chatToSender(context, text);

        return 1;
    }
}
