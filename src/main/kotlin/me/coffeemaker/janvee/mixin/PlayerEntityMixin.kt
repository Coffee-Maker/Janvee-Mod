package me.coffeemaker.janvee.mixin

import me.coffeemaker.janvee.Janvee
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.*
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import kotlin.math.ln

// Mixins HAVE to be written in java due to constraints in the mixin system.
@Mixin(PlayerEntity::class)
abstract class PlayerEntityMixin : LivingEntity(null, null) {

    @Inject(
        method = ["getMovementSpeed()V"],
        at = [At("HEAD")],
        cancellable = true
    )
    private fun getMovementSpeed(callbackInfo: CallbackInfoReturnable<Float>) {
        Janvee.playerData[uuid]?.agilityLvl?.let {
            callbackInfo.returnValue = getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED).toFloat() * (0.6f + (0.2f * ln(it.toFloat() + 1)))
        }
    }

    // This is the "flying speed" which is used when jumping
    @ModifyConstant(
        method = ["tickMovement()V"],
        constant = [Constant(floatValue = 0.02f)]
    )
    private fun walkingFlyingSpeed(oldValue: Float): Float =
        Janvee.playerData[uuid]?.agilityLvl?.let {
            0.015f + (0.01f * ln(it.toFloat() + 1))
        } ?: oldValue

    @Redirect(
        method = ["tickMovement()V"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"
        )
    )
    private fun redirectGetAttributeValue(entity: PlayerEntity, entityAttribute: EntityAttribute): Double = 0.0
}