package com.javachefmc.minissentials.mixin;

import com.javachefmc.minissentials.Minissentials;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class OutgoingChatMessageMixin {
    @Inject(method="sendChatMessage", at=@At("HEAD"))
    public void sendChatMessageInjector(OutgoingChatMessage outgoingChatMessage, boolean bl, ChatType.Bound bound, CallbackInfo ci){
//      UNIT TEST
        //        Minissentials.log(outgoingChatMessage.toString());
    }

    // for OutgoingChatMessage.class
//    @Inject(method = "sendToPlayer", at = @At("HEAD"))
//    public void playerMessageInjector(ServerPlayer par1, boolean par2, ChatType.Bound par3, CallbackInfo ci){
//
//    }



}
