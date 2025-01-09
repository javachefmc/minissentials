package com.javachefmc.minissentials.chat;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public class ChatHandler {

    public static final ChatFormatting DEFAULT_CHAT_COLOR = ChatFormatting.WHITE;
    private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)&[0-9A-FK-OR]");

    public static String[] toTokens(String string){
        return string.split("(?i)(?=&[0-9A-FK-OR])");
    }

    public static String stripFormatting(@Nullable String string) {
        return string == null ? null : STRIP_FORMATTING_PATTERN.matcher(string).replaceAll("");
    }

    public static Component formatText(String text) {

        String[] tokenizedText = ChatHandler.toTokens(text);

        // Initialize state of string prior to stepping through it
        boolean isObfuscated = false;
        boolean isBold = false;
        boolean isStrikethrough = false;
        boolean isItalic = false;
        boolean isUnderline = false;

        ChatFormatting currentColor = DEFAULT_CHAT_COLOR; // Empty color is default

        Component tokenString = Component.literal("");
        Component currentToken;

        // add prefix
//        Component prefix = Component.literal("[Minissentials] ").withStyle(ChatFormatting.GOLD);
//        tokenString.getSiblings().add(prefix);

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
                        currentColor = DEFAULT_CHAT_COLOR;
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

        return tokenString;
    }
}
