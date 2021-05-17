package me.coffeemaker.janvee.items.weapons.throwing

import me.coffeemaker.janvee.runIfServer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolMaterial
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

abstract class ThrowingWeaponItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)

        runIfServer(world) {
            val entity = getEntity(world, user)
            entity.setItem(itemStack)
            entity.setProperties(user, user.pitch, user.yaw, 0.0f, 1.5f, 1.0f)
            println(world.spawnEntity(entity))
            println(entity.pos)
        }

        if (!user.abilities.creativeMode) {
            itemStack.decrement(1)
        }

        return TypedActionResult.success(itemStack, world.isClient())
    }

    abstract fun getEntity(world: World, user: PlayerEntity): ThrownItemEntity

    abstract val throwingDamage: Float
    abstract val impactBreakChance: Float
}