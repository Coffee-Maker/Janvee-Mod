package me.coffeemaker.janvee.mixinimpls

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.items.interfaces.IMeleeWeapon
import me.coffeemaker.janvee.items.interfaces.IModifyKnockback
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.SwordItem
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.*
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.lang.NullPointerException
import kotlin.math.ln

abstract class PlayerEntityMixinImpl : LivingEntity(null, null) {
    companion object {
        @JvmStatic
        fun getMovementSpeed(player: PlayerEntity): Float = Janvee.playerData[player.uuid]?.agilityLvl?.let {
            player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED).toFloat() * (0.6f + (0.2f * ln(it.toFloat() + 1)))
        } ?: throw NullPointerException("Player data is not present for ${player.entityName}")

        // This is the "flying speed" which is used when jumping
        @JvmStatic
        fun walkingFlyingSpeed(player: PlayerEntity, oldValue: Float): Float =
            Janvee.playerData[player.uuid]?.agilityLvl?.let {
                0.015f + (0.01f * ln(it.toFloat() + 1))
            } ?: oldValue

        @JvmStatic
        fun getPlayerKnockback(baseKnockback: Float, inventory: PlayerInventory): Float = (inventory.mainHandStack.item as? IModifyKnockback)?.knockback ?: baseKnockback

        @JvmStatic
        fun fixSwordItemCheck(reference: Any): Boolean = reference is SwordItem && reference !is IMeleeWeapon
    }
}