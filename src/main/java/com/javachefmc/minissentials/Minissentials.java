package com.javachefmc.minissentials;

import com.javachefmc.minissentials.util.CommandRegistries;
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

    public static final int DEFAULT_COLOR = MChatFormatting.getByCode('f').getId();
    public static final MChatFormatting DEFAULT_FORMATTING = MChatFormatting.WHITE;

    @Override
    public void onInitialize() {

        // Register commands
        CommandRegistries.register();

        log("Minissentials loaded.");
    }

    public static void log(String message) {
        LOGGER.info(prefix + message);
    }

    public static void chatToSender(CommandContext<CommandSourceStack> context, String message) {

        String formattedText = MChatFormatting.stripFormatting(message);
        String[] tokenizedText = MChatFormatting.toTokens(message);

        // TODO: ALL OF THIS CODE NEEDS TO BE MOVED TO MCHATFORMATTING


        // Initialize state of string prior to stepping through it
        boolean isObfuscated = false;
        boolean isBold = false;
        boolean isStrikethrough = false;
        boolean isItalic = false;
        boolean isUnderline = false;

        ChatFormatting defaultColor = ChatFormatting.WHITE;
        ChatFormatting currentColor = defaultColor; // Empty color is default

        Component tokenString = Component.literal("");
        Component currentToken;

        // add prefix
        Component prefix = Component.literal("[Minissentials] ").withStyle(ChatFormatting.GOLD);
        tokenString.getSiblings().add(prefix);

        // Step through each token
        for (String token : tokenizedText) {
            String firstTwo = token.length() < 2 ? "" : token.substring(0, 2);

            // Remove formatting code from string
            token = MChatFormatting.stripFormatting(token);

            if (firstTwo.startsWith("&")){
                // This is a formatting code
                char code = firstTwo.charAt(1);
                ChatFormatting formatCode = ChatFormatting.getByCode(code);
                if (formatCode != null){
                    if (formatCode.isColor()) {
                        // Change current color
                        currentColor = formatCode;
                    } else if (formatCode.isFormat()) {
                        // Change current styling
                        switch (formatCode.getName()) {
                            case "obfuscated" -> isObfuscated = true;
                            case "bold" -> isBold = true;
                            case "strikethrough" -> isStrikethrough = true;
                            case "underline" -> isUnderline = true;
                            case "italic" -> isItalic = true;
                        }
                    } else {
                        // RESET CODE
                        currentColor = defaultColor;
                        isObfuscated = isBold = isStrikethrough = isItalic = isUnderline = false;
                    }
                } else {
                    // This starts with & but is not a formatting code
                }
            } else {
                // This doesn't start with &
            }

            // Set string color
            currentToken = Component.literal(token).withStyle(currentColor);

            // Set string style
            if (isObfuscated) currentToken = currentToken.copy().withStyle(ChatFormatting.OBFUSCATED);
            if (isBold) currentToken = currentToken.copy().withStyle(ChatFormatting.BOLD);
            if (isStrikethrough) currentToken = currentToken.copy().withStyle(ChatFormatting.STRIKETHROUGH);
            if (isUnderline) currentToken = currentToken.copy().withStyle(ChatFormatting.UNDERLINE);
            if (isItalic) currentToken = currentToken.copy().withStyle(ChatFormatting.ITALIC);

            // Add the token
            tokenString.getSiblings().add(currentToken);
        }

        // Send final chat
        context.getSource().sendSuccess(() -> tokenString, false);
    }
}
