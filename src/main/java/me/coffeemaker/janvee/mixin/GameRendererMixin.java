package me.coffeemaker.janvee.mixin;

import me.coffeemaker.janvee.mixinimpls.GameRendererMixinImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyConstant(
            method = "updateTargetedEntity(F)V",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getCameraPosVec(F)Lnet/minecraft/util/math/Vec3d;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;")
            ),
            constant = @Constant(doubleValue = 3.0D)
    )
    public double fixReachDistance(double oldValue) {
        return GameRendererMixinImpl.fixReachDistance(this.client);
    }

    @ModifyConstant(
            method = "updateTargetedEntity(F)V",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/BlockHitResult;createMissed(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/hit/BlockHitResult;")
            ),
            constant = @Constant(doubleValue = 9.0D)
    )
    public double fixSquaredReachDistance(double oldValue) {
        return GameRendererMixinImpl.fixSquaredReachDistance(this.client);
    }
}
