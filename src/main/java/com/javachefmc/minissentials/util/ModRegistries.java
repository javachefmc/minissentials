package com.javachefmc.minissentials.util;

import com.javachefmc.minissentials.commands.MinissentialsInit;
import com.javachefmc.minissentials.commands.Nick;
import com.javachefmc.minissentials.commands.SetHome;
import com.javachefmc.minissentials.commands.Shutdown;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    /*


    Registers commands, etc. with Fabric


     */
    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(SetHome::register);
        CommandRegistrationCallback.EVENT.register(MinissentialsInit::register);
//        CommandRegistrationCallback.EVENT.register(Shutdown::register);
        CommandRegistrationCallback.EVENT.register(Nick::register);
    }
}
