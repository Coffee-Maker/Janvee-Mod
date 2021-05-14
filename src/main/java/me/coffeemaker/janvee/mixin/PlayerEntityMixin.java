package me.coffeemaker.janvee.mixin;

import me.coffeemaker.janvee.mixinimpls.PlayerEntityMixinImpl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "Lnet/minecraft/entity/player/PlayerEntity;getMovementSpeed()F",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getMovementSpeed(CallbackInfoReturnable<Float> callbackInfo) {
        callbackInfo.setReturnValue(PlayerEntityMixinImpl.getMovementSpeed((PlayerEntity) (Object) this));
    }

    @ModifyConstant(
            method = "tickMovement()V",
            constant = @Constant(floatValue = 0.02f)
    )
    public float walkingFlyingSpeed(float oldValue) {
        return PlayerEntityMixinImpl.walkingFlyingSpeed((PlayerEntity) (Object) this, oldValue);
    }

    @Redirect(
            method = "tickMovement()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"
            )
    )
    public double redirectGetAttributeValue(PlayerEntity entity, EntityAttribute entityAttribute) {
        return 0.0;
    }
}
