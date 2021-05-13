package me.coffeemaker.janvee.mixin;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(
            method = "Lnet/minecraft/server/PlayerManager;onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            at = @At("RETURN")
    )
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        System.out.println("OwO");
    }
}
