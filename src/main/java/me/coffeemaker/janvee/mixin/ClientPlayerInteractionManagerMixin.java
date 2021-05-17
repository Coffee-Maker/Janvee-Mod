package me.coffeemaker.janvee.mixin;

import me.coffeemaker.janvee.mixinimpls.ClientPlayerInteractionManagerMixinImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "getReachDistance()F",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getReachDistance(CallbackInfoReturnable<Float> callbackInfo) {
        Float distance = ClientPlayerInteractionManagerMixinImpl.getReachDistance(client);

        if(distance != null) {
            callbackInfo.setReturnValue(distance);
        }
    }
}
