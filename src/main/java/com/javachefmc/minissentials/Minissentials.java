package com.javachefmc.minissentials;

import com.javachefmc.minissentials.chat.ChatHandler;
import com.javachefmc.minissentials.chat.MChatFormatting;
import com.javachefmc.minissentials.util.CommandRegistries;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Minissentials implements ModInitializer {

    public static final String MOD_ID = "minissentials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String LOGGER_PREFIX = "[" + MOD_ID + "] ";
    public static final Component CHAT_PREFIX = Component.literal("[Minissentials] ").withStyle(ChatFormatting.GOLD);

    public static final MChatFormatting DEFAULT_CHAT_COLOR = MChatFormatting.WHITE;

    @Override
    public void onInitialize() {

        // Register commands
        CommandRegistries.register();

        log("Minissentials loaded.");
    }

    public static void log(String message) {
        LOGGER.info(LOGGER_PREFIX + "{}", message);
    }

    public static void chatToSender(CommandContext<CommandSourceStack> context, String message) {
        // Format text
        Component formattedText = ChatHandler.formatText(message);
        // Add prefix
        formattedText.getSiblings().addFirst(CHAT_PREFIX);
        // Send chat
        context.getSource().sendSystemMessage(formattedText);
    }
    public static void chatToSender(ServerPlayer player, String message) {
        // Format text
        Component formattedText = ChatHandler.formatText(message);
        // Add prefix
        formattedText.getSiblings().addFirst(CHAT_PREFIX);
        // Send chat
        player.sendSystemMessage(formattedText);
    }

    public static void chatToEveryone(CommandContext<CommandSourceStack> context, String message) {
        Component formattedText = ChatHandler.formatText(message);

        ServerPlayer player = context.getSource().getPlayer();
        List<ServerPlayer> players = context.getSource().getLevel().players();

        // TODO: THIS DOESN'T WORK

//        PlayerChatMessage playerChatMessage = PlayerChatMessage.unsigned(Objects.requireNonNull(player).getUUID(), message);

//        PlayerChatMessage playerChatMessage = PlayerChatMessage.system(message);

//        player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(playerChatMessage), false, ChatType.bind(ChatType.CHAT, player));

        CommandSourceStack commandSourceStack = context.getSource();
        PlayerList playerList = commandSourceStack.getServer().getPlayerList();
//        playerList.broadcastChatMessage(formattedText, commandSourceStack, ChatType.bind(ChatType.SAY_COMMAND, commandSourceStack));


        // This actually sends in the log
        context.getSource().getServer().sendSystemMessage(formattedText);

        for (ServerPlayer this_player : players) {
            this_player.sendSystemMessage(formattedText);
        }
    }
}
