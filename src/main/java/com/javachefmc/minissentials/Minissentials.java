package com.javachefmc.minissentials;

import com.javachefmc.minissentials.util.ModRegistries;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Minissentials implements ModInitializer {

    public static final String MOD_ID = "minissentials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String prefix = "[" + MOD_ID + "] ";

    @Override
    public void onInitialize() {

        // Registration
        ModRegistries.registerCommands();

        log("Minissentials loaded.");
    }

    public static void log(String message) {
        LOGGER.info(prefix + message);
    }
}
