package com.javachefmc.minissentials;

import com.javachefmc.minissentials.util.ModRegistries;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
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

    public static void chatToSender(CommandContext<CommandSourceStack> context, String message) {
        String formattedText = MChatFormatting.stripFormatting(message);
        String[] tokenizedText = MChatFormatting.toTokens(message);

        Component prefix = Component.literal("[Minissentials] ").withStyle(ChatFormatting.RED);
        Component formattedMessage = Component.literal(formattedText).withStyle(ChatFormatting.GOLD);

        // This appears to be the preferred way to chain components together
        prefix.getSiblings().add(formattedMessage);

        context.getSource().sendSuccess(() -> prefix, false);
    }
}
