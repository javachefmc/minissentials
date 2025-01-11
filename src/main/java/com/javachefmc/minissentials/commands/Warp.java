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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

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

        if (warps.has(name)){
            // Warp exists

            // Get coordinates
            JsonObject coordinates = warps.getAsJsonObject(name).getAsJsonObject("coordinates");
            Double x = coordinates.get("x").getAsDouble();
            Double y = coordinates.get("y").getAsDouble();
            Double z = coordinates.get("z").getAsDouble();

            // Teleport to location if safe
            if(TeleportHandler.isSafe(new Vec3(x, y, z))) {
                ServerPlayer player = context.getSource().getPlayer();
                Minissentials.chatToSender(context, "Warping to &b" + name + "&r...");
                Minissentials.log("Warping player " + player.getDisplayName().toString() + " to warp " + name);
                player.teleportTo(x, y, z);
            } else {
                Minissentials.chatToSender(context, "Warp &b" + name + "&r is not safe");
            }
        } else {
            Minissentials.chatToSender(context, "A warp called &b" + name + "&r does not exist");
        }

        return 1;
    }
}
