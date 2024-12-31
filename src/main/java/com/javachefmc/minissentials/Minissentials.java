package com.javachefmc.minissentials;

import com.javachefmc.minissentials.util.CommandRegistries;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
        boolean isBold = false;
        boolean isItalic = false;
        boolean isUnderline = false;
        boolean isObfuscated = false;
        ChatFormatting defaultColor = ChatFormatting.WHITE;
        ChatFormatting currentColor = defaultColor; // Empty color is default

        Component tokenString = Component.literal("");
        Component currentToken = Component.literal("");

        // Step through each token
        for (String token : tokenizedText) {
            String firstTwo = token.length() < 2 ? "" : token.substring(0, 2);
            if (firstTwo.startsWith("&")){

                char code = firstTwo.charAt(1);
                ChatFormatting formatCode = ChatFormatting.getByCode(code);

                if (formatCode != null){
                    if (formatCode.isColor()) {
                        // This is a color code


                        // Set (reset) saved color
//                        currentColor = formatCode.getId();
                        currentColor = formatCode;


                        Minissentials.log("Encountered color code: (" + currentColor.getName() + ") for token (" + token + ")");

                        // TODO: remove formatting code from string
                        currentToken = Component.literal(token).withStyle(currentColor);

                    } else if (formatCode.isFormat()) {
                        // This is a formatting code
//                        Minissentials.log("Encountered formatting code");

                        Minissentials.log("Encountered formatting code: (" + formatCode.getName() + ") for token (" + token + ")");

                        // TODO: remove formatting code from string
//                        currentToken = Component.literal(token).withStyle();

                        currentToken = Component.literal(token).withStyle(currentColor).withStyle(formatCode);
                    } else {
                        // PROBABLY A RESET BUT LET'S CHECK ANYWAY
                        Minissentials.log("Got a code that isn't a color or format: " + formatCode.getName());

                        currentColor = defaultColor;
                        isBold = false;
                        isObfuscated = false;
                        isItalic = false;
                        isUnderline = false;

                        currentToken = Component.literal(token).withStyle(currentColor);
                    }

                } else {
                    Minissentials.log("This token has no format code");
                    // This is not a formatting code
                    // Preserve the & sign

                    currentToken = Component.literal(token).withStyle(currentColor);
                }
            } else {
                Minissentials.log("Something bad about to happen");
            }

            tokenString.getSiblings().add(currentToken);

            Minissentials.log(token);
        }






        Component prefix = Component.literal("[Minissentials] ").withStyle(ChatFormatting.RED);
        Component formattedMessage = Component.literal(formattedText).withStyle(ChatFormatting.GOLD);
//        Component formattedMessage = tokenString;

        // This appears to be the preferred way to chain components together
        prefix.getSiblings().add(formattedMessage);

        context.getSource().sendSuccess(() -> tokenString, false);
    }
}
