package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Warp {
    /*


    Warp to saved warp


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("warp")
                .then(Commands.argument("name", StringArgumentType.word()).executes(Warp::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){

        // Get warps
        JsonObject warps = MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);
        String name = context.getArgument("name", String.class);

        if (warps.has(name)) {
            JsonObject coordinates = warps.getAsJsonObject(name).getAsJsonObject("coordinates");
            Minissentials.log(coordinates.toString());
        }

        if (warps.has(name)){
            // Warp exists

            // TODO: CHECK IF WARP LOCATION IS SAFE
//            if(TeleportHandler.isSafe())
            Minissentials.chatToSender(context, "Warping to &b" + name + "&r...");



        } else {
            Minissentials.chatToSender(context, "A warp called &b" + name + "&r does not exist");
        }

        return 1;
    }
}
