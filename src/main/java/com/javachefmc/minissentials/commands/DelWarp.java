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

public class DelWarp {
    /*


    Removes warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("delwarp")
                .then(Commands.argument("name", StringArgumentType.word()).executes(DelWarp::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // TODO: UNTESTED
        
        // Get current warps
        JsonObject warps = MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);
        
        // Get arg
        String name = context.getArgument("name", String.class);
        
        // Check if warp exists
        if (warps.has(name)){
            // Serialize JSON of new warp file with warp removed
            warps.remove(name);
            // Store warp data
            MinissentialsData.setWorldData(MinissentialsData.WorldDataFileType.warps, warps);
            Minissentials.chatToSender(context, "&cDeleted warp: &b" + name);
            Minissentials.log("Server or player deleted warp: " + name);
        } else {
            Minissentials.chatToSender(context, "A warp called &b" + name + "&r does not exist");
            return 0;
        }
        
        return 1;
    }
}
