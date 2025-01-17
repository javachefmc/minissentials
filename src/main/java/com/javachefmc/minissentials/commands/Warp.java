package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.javachefmc.minissentials.teleport.TeleportHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import static com.javachefmc.minissentials.teleport.TeleportHandler.getPlayerRelativeLevel;

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

        // Get player
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        if (warps.has(name)){
            // Warp exists

            // Get coordinates
            JsonObject coordinates = warps.getAsJsonObject(name).getAsJsonObject("coordinates");
            double x = coordinates.get("x").getAsDouble();
            double y = coordinates.get("y").getAsDouble();
            double z = coordinates.get("z").getAsDouble();
            float rot_x = coordinates.get("rot_x").getAsFloat();
            float rot_y = coordinates.get("rot_y").getAsFloat();
            ServerLevel level = getPlayerRelativeLevel(player, coordinates.get("level").getAsString());
            
            // Attempt teleport
            TeleportHandler.teleportPlayer(player, level, x, y, z, rot_x, rot_y);
            
        } else {
            Minissentials.chatToSender(context, "A warp called &b" + name + "&r does not exist");
        }

        return 1;
    }
}
