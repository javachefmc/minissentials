package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.javachefmc.minissentials.data.WarpData;
import com.javachefmc.minissentials.teleport.TeleportHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class Warp {
    /*


    Warp to saved warp


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("warp")
                .then(Commands.argument("name", StringArgumentType.word()).executes(Warp::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Get player
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        // Get arg
        String name = context.getArgument("name", String.class); // TODO: Make case insensitive

        // Get coordinates
        WarpData c = new WarpData(player, name, MinissentialsData.WorldDataFileType.warps);
        
        if (c.exists) {
            // Attempt teleport
            TeleportHandler.teleportPlayer(player, c.level, c.x, c.y, c.z, c.rot_x, c.rot_y);
        } else {
            Minissentials.chatToSender(context, "A warp called &b" + name + "&r does not exist");
        }

        return 1;
    }
}
