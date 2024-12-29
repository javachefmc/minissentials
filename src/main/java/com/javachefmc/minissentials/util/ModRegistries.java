package com.javachefmc.minissentials.util;

import com.javachefmc.minissentials.commands.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    /*


    Registers commands, etc. with Fabric


     */
    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(MinissentialsInit::register);

        CommandRegistrationCallback.EVENT.register(Ping::register);

        CommandRegistrationCallback.EVENT.register(SetHome::register);
        CommandRegistrationCallback.EVENT.register(DelHome::register);
        CommandRegistrationCallback.EVENT.register(Home::register);

        CommandRegistrationCallback.EVENT.register(SetWarp::register);
        CommandRegistrationCallback.EVENT.register(DelWarp::register);
        CommandRegistrationCallback.EVENT.register(SetGlobalWarp::register);
        CommandRegistrationCallback.EVENT.register(DelGlobalWarp::register);
        CommandRegistrationCallback.EVENT.register(Warp::register);
        CommandRegistrationCallback.EVENT.register(Warps::register);

//        CommandRegistrationCallback.EVENT.register(Shutdown::register);
        CommandRegistrationCallback.EVENT.register(Nick::register);
        CommandRegistrationCallback.EVENT.register(DelNick::register);
    }
}
