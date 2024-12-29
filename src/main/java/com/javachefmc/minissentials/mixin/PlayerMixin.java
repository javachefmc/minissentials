package com.javachefmc.minissentials.mixin;

import com.javachefmc.minissentials.Minissentials;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Shadow
    private GameProfile gameProfile;

    @Inject(method="getName", at=@At("RETURN"))
    public void getNameInjection(CallbackInfoReturnable<Component> cir){
        GameProfile profile = this.gameProfile;
        String UUID = profile.getId().toString();

        /*
        Original return: Component.literal(this.gameProfile.getName())
         */
        Minissentials.log("Player getName accessed");
        cir.setReturnValue(Component.literal("Injected Name"));
    }

    @Inject(method="getDisplayName", at=@At("RETURN"))
    public void getDisplayNameInjection(CallbackInfoReturnable<Component> cir){
        GameProfile profile = this.gameProfile;
        String UUID = profile.getId().toString();

        /*
        Original return: this.decorateDisplayNameComponent(mutableComponent)
         */
        Minissentials.log("Player getDisplayName accessed");
//        cir.setReturnValue(Component.literal("Injected Name"));
    }
}
