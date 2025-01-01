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

        // Temporary bugfix that might stay here forever
        message = "&r" + message;

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
        Component currentToken = Component.literal("");

        Component prefix = Component.literal("[Minissentials] ").withStyle(ChatFormatting.GOLD);

        // add prefix
        tokenString.getSiblings().add(prefix);

        // TODO: PLEASE OPTIMIZE THIS LOGIC TO REMOVE REDUNDANT CODE

        // Step through each token
        for (String token : tokenizedText) {
            String firstTwo = token.length() < 2 ? "" : token.substring(0, 2);
            if (firstTwo.startsWith("&")){

                char code = firstTwo.charAt(1);
                ChatFormatting formatCode = ChatFormatting.getByCode(code);

                if (formatCode != null){
                    if (formatCode.isColor()) { // This is a color code
                        // Remove formatting code from string
                        token = MChatFormatting.stripFormatting(token);

                        // Change current color and set string color
                        currentColor = formatCode;
                        currentToken = Component.literal(token).withStyle(currentColor);

                        // Set string style
                        if (isObfuscated) currentToken = currentToken.copy().withStyle(ChatFormatting.OBFUSCATED);
                        if (isBold) currentToken = currentToken.copy().withStyle(ChatFormatting.BOLD);
                        if (isStrikethrough) currentToken = currentToken.copy().withStyle(ChatFormatting.STRIKETHROUGH);
                        if (isUnderline) currentToken = currentToken.copy().withStyle(ChatFormatting.UNDERLINE);
                        if (isItalic) currentToken = currentToken.copy().withStyle(ChatFormatting.ITALIC);

                    } else if (formatCode.isFormat()) { // This is a formatting code

                        // Remove formatting code from string
                        token = MChatFormatting.stripFormatting(token);

                        // Set string color
                        currentToken = Component.literal(token).withStyle(currentColor);

                        // Set styling
                        switch (formatCode.getName()) {
                            case "obfuscated" -> isObfuscated = true;
                            case "bold" -> isBold = true;
                            case "strikethrough" -> isStrikethrough = true;
                            case "underline" -> isUnderline = true;
                            case "italic" -> isItalic = true;
                        }

                        // Set string style
                        if (isObfuscated) currentToken = currentToken.copy().withStyle(ChatFormatting.OBFUSCATED);
                        if (isBold) currentToken = currentToken.copy().withStyle(ChatFormatting.BOLD);
                        if (isStrikethrough) currentToken = currentToken.copy().withStyle(ChatFormatting.STRIKETHROUGH);
                        if (isUnderline) currentToken = currentToken.copy().withStyle(ChatFormatting.UNDERLINE);
                        if (isItalic) currentToken = currentToken.copy().withStyle(ChatFormatting.ITALIC);

                    } else {
                        // MOST LIKELY A RESET CODE

                        currentColor = defaultColor;

                        isObfuscated = false;
                        isBold = false;
                        isStrikethrough = false;
                        isItalic = false;
                        isUnderline = false;

                        // Remove the formatting code from string
                        token = MChatFormatting.stripFormatting(token);

                        // Add current color (is set to defaultColor above)
                        currentToken = Component.literal(token).withStyle(currentColor);
                    }

                } else {
                    // This is not a formatting code
                    // Preserve the & sign

                    // Set string color
                    currentToken = Component.literal(token).withStyle(currentColor);

                    // Set string style
                    if (isObfuscated) currentToken = currentToken.copy().withStyle(ChatFormatting.OBFUSCATED);
                    if (isBold) currentToken = currentToken.copy().withStyle(ChatFormatting.BOLD);
                    if (isStrikethrough) currentToken = currentToken.copy().withStyle(ChatFormatting.STRIKETHROUGH);
                    if (isUnderline) currentToken = currentToken.copy().withStyle(ChatFormatting.UNDERLINE);
                    if (isItalic) currentToken = currentToken.copy().withStyle(ChatFormatting.ITALIC);
                }
            } else {
                // This should be the same situation as above

                // Set string color
                currentToken = Component.literal(token).withStyle(currentColor);

                // Set string style
                if (isObfuscated) currentToken = currentToken.copy().withStyle(ChatFormatting.OBFUSCATED);
                if (isBold) currentToken = currentToken.copy().withStyle(ChatFormatting.BOLD);
                if (isStrikethrough) currentToken = currentToken.copy().withStyle(ChatFormatting.STRIKETHROUGH);
                if (isUnderline) currentToken = currentToken.copy().withStyle(ChatFormatting.UNDERLINE);
                if (isItalic) currentToken = currentToken.copy().withStyle(ChatFormatting.ITALIC);
            }

            // Add the token
            tokenString.getSiblings().add(currentToken);
        }

        context.getSource().sendSuccess(() -> tokenString, false);
    }
}
