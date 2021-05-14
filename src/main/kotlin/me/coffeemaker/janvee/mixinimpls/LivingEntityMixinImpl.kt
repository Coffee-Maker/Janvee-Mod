package me.coffeemaker.janvee.mixinimpls

import me.coffeemaker.janvee.Janvee
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.Constant
import org.spongepowered.asm.mixin.injection.ModifyConstant
import kotlin.math.ln

class LivingEntityMixinImpl {

    companion object{
        // This is for us to control the velocity impulse you get when jumping and sprinting
        @JvmStatic
        fun jumpSpeedImpulse(oldValue: Float, entity: LivingEntity): Float =
            Janvee.playerData[entity.uuid]?.agilityLvl?.let {
                0.1f + (0.1f * ln(it.toFloat() + 1))
            } ?: oldValue
    }
}