package com.javachefmc.minissentials.commands;

import com.google.gson.JsonObject;
import com.javachefmc.minissentials.Minissentials;
import com.javachefmc.minissentials.data.MinissentialsData;
import com.javachefmc.minissentials.data.WarpData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class SetWarp {
    /*


    Sets warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("setwarp")
                .then(Commands.argument("name", StringArgumentType.word()).executes(SetWarp::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Get current warp data
        JsonObject warps = MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);

        // Create new warp data
        String name = context.getArgument("name", String.class);
        String dimension = String.valueOf(WarpData.getDimension(context.getSource().getLevel().dimension()));

        // Get player
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;

        // Serialize coordinates
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("x", player.position().x);
        coordinates.addProperty("y", player.position().y);
        coordinates.addProperty("z", player.position().z);

        // TODO: Also include rotation data in warp

        // Serialize warp data
        JsonObject warp = new JsonObject();
        warp.add("coordinates", coordinates);
        warp.addProperty("dimension", dimension);
        warp.addProperty("creator", player.getStringUUID());
        warp.addProperty("created", "<timestamp>");

        if (!warps.has(name)){
            // Key does not exist yet

            warps.add(name, warp);
            MinissentialsData.setWorldData(MinissentialsData.WorldDataFileType.warps, warps);

            Minissentials.chatToSender(context, "Created global warp &b" + name + "&r at &e[" + coordinates + "]");

        } else {
            Minissentials.chatToSender(context, "A warp called &b" + name + "&r already exists!");
        }

        return 1;
    }
}
