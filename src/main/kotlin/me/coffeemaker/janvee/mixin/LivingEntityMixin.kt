package me.coffeemaker.janvee.mixin

import me.coffeemaker.janvee.Janvee
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Constant
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyConstant
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import kotlin.math.ln

@Mixin(LivingEntity::class)
abstract class LivingEntityMixin : Entity(null, null) {

    // This is for us to control the velocity impulse you get when jumping and sprinting
    @ModifyConstant(
        method = ["jump()V"],
        constant = [Constant(floatValue = 0.2f)]
    )
    private fun jumpSpeedImpulse(oldValue: Float): Float =
        Janvee.playerData[uuid]?.agilityLvl?.let {
            0.1f + (0.1f * ln(it.toFloat() + 1))
        } ?: oldValue

    @Inject(
        method = ["getMaxHealth()V"],
        at = [At("HEAD")],
        cancellable = true
    )
    private fun getMaxHealth(callbackInfo: CallbackInfoReturnable<Float>) {
        if (this is PlayerEntity){
            callbackInfo.returnValue = 1f
            Janvee.playerData[uuid]?.healthLvl?.toFloat()?.let {
                callbackInfo.returnValue = it + 3f
            }
        }
    }
}