package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.Set;

public class Warps {
    /*


    List warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("warps").executes(Warps::run));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Get warps
        JsonObject warps = MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);

        // Get names of warps and chat to player
        Set<String> keys = warps.keySet();
        Minissentials.chatToSender(context, "Warps: &b" + keys.toString());
        Minissentials.chatToSender(context, "Do &7/warp &2<name>&f to teleport to a warp");
        Minissentials.chatToSender(context, "Do &7/setwarp &2<name>&f to create a warp for everyone");

        return 1;
    }
}
