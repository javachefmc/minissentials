package com.javachefmc.minissentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import java.util.Collection;
import java.util.Iterator;

public class Shutdown {
    /*


    Graceful shutdown that disconnects players with a message. Work in progress and needs permissions


     */
    private static final SimpleCommandExceptionType ERROR_KICKING_OWNER = new SimpleCommandExceptionType(Component.translatable("commands.kick.owner.failed"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("shutdown").executes(Shutdown::run));
    }

    private static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        // First get OP level and see if they have permission

        // Then get all players and kick them
        CommandSourceStack commandSourceStack = context.getSource();
        PlayerList playerList = commandSourceStack.getServer().getPlayerList();
//        playerList.broadcastChatMessage(playerChatMessage, commandSourceStack, ChatType.bind(ChatType.SAY_COMMAND, commandSourceStack));



        context.getSource().sendSuccess(() -> Component.literal("Shutting down..."), false);

        kickPlayers(commandSourceStack, (Collection<ServerPlayer>) playerList, Component.translatable("multiplayer.disconnect.kicked"));

        // Then shut down server

        context.getSource().getServer().halt(false);
        return 1;
    }

    private static void kickPlayers(CommandSourceStack commandSourceStack, Collection<ServerPlayer> collection, Component component) throws CommandSyntaxException {
        int i = 0;
        Iterator var4 = collection.iterator();

        while(var4.hasNext()) {
            ServerPlayer serverPlayer = (ServerPlayer)var4.next();
            if (!commandSourceStack.getServer().isSingleplayerOwner(serverPlayer.getGameProfile())) {
                serverPlayer.connection.disconnect(component);
                commandSourceStack.sendSuccess(() -> {
                    return Component.translatable("commands.kick.success", new Object[]{serverPlayer.getDisplayName(), component});
                }, true);
                ++i;
            }
        }

        if (i == 0) {
            throw ERROR_KICKING_OWNER.create();
        }
    }
}
