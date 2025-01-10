package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Warps {
    /*


    List warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("warps").executes(Warps::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Do something
//        Minissentials.chatToSender(context, "Listing warps");
        JsonObject warps = MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);


//        Set<String> keys = JsonObject.keySet();

        return 1;
    }
}
