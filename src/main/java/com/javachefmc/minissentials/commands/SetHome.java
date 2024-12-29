package com.javachefmc.minissentials.commands;

import com.javachefmc.minissentials.data.IEntityDataSaver;
import com.javachefmc.minissentials.data.WarpData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import static org.apache.commons.lang3.StringUtils.join;

public class SetHome {
    /*


    Sets your home


     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("sethome")
                .then(Commands.argument("name", MessageArgument.message()).executes(SetHome::run)));

//        dispatcher.register((Commands.literal("say").requires((commandSourceStack) -> {
//            return commandSourceStack.hasPermission(2);
//        })).then(Commands.argument("message", MessageArgument.message()).executes((commandContext) -> {
//            MessageArgument.resolveChatMessage(commandContext, "message", (playerChatMessage) -> {
//                CommandSourceStack commandSourceStack = (CommandSourceStack)commandContext.getSource();
//                PlayerList playerList = commandSourceStack.getServer().getPlayerList();
//                playerList.broadcastChatMessage(playerChatMessage, commandSourceStack, ChatType.bind(ChatType.SAY_COMMAND, commandSourceStack));
//            });
//            return 1;
//        })));
    }

    private static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        ServerPlayer player = context.getSource().getPlayer();

        Vec3 homePos = context.getSource().getPosition();


//        player.getEntityData().set();

        String command = context.getInput();
        String args = command.split(" ",2)[1];

        WarpData.setSpawn((IEntityDataSaver) player, player.position(), player.getRotationVector());

        context.getSource().sendSuccess(() -> Component.literal("Set home to " + player.position()), false);

        return 1;
    }
}
