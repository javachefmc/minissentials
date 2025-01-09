package com.javachefmc.minissentials.commands;

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
import net.minecraft.world.phys.Vec3;
import net.minidev.json.JSONObject;

public class SetGlobalWarp {
    /*


    Sets warps


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("setglobalwarp")
                .then(Commands.argument("name", StringArgumentType.word()).executes(SetGlobalWarp::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context){
        // Do something
//        Minissentials.chatToSender(context, "Setting global warp: " + context.getArgument("name", String.class));

        // Get current warp data

        JSONObject warps = MinissentialsData.getWorldData(MinissentialsData.WorldDataFileType.warps);


        // Serialize new warp data
        String name = context.getArgument("name", String.class);

//        WarpData warp = new WarpData();
//        warp.name = name;
//        warp.coordinates = new Vec3(0, 151, 0);
//        warp.dimension = WarpData.getDimension(context.getSource().getLevel().dimension());

        ServerPlayer player = context.getSource().getPlayer();
        JSONObject warp = new JSONObject();
        JSONObject warpcoords = new JSONObject();
        assert player != null;
        warpcoords.put("x", player.position().x);
        warpcoords.put("y", player.position().y);
        warpcoords.put("z", player.position().z);
        warp.put("coordinates", warpcoords);

//        if (warp.validate()){
            // Warp has all fields set


            if (!warps.containsKey(name)){
                // Key does not exist yet




            } else {
                Minissentials.chatToSender(context, "A warp called &b" + name + "&r already exists!");
            }


//        }

        return 1;
    }
}
