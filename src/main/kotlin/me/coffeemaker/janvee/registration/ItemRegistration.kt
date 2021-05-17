package me.coffeemaker.janvee.registration

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.entities.ThrowingKnifeEntity
import me.coffeemaker.janvee.items.CustomArrowItem
import me.coffeemaker.janvee.items.DaggerItem
import me.coffeemaker.janvee.items.SpearItem
import me.coffeemaker.janvee.items.weapons.throwing.ThrowingKnifeItem
import net.minecraft.block.DispenserBlock
import net.minecraft.block.dispenser.ProjectileDispenserBehavior
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.math.Position
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class ItemRegistration {
    companion object {
        fun registerItems() {
            DaggerItem.registerVariants()
            SpearItem.registerVariants()
            ThrowingKnifeItem.registerVariants()

            ThrowingKnifeEntity.registerVariants()

            Registry.register(Registry.ITEM, Identifier(Janvee.MODID, "custom_arrow"), CustomArrowItem())
        }

        fun registerItemFunctionalities() {
            ThrowingKnifeItem.variants.forEach { (material, knifeItem) ->
                DispenserBlock.registerBehavior(knifeItem, object : ProjectileDispenserBehavior() {
                    override fun createProjectile(world: World, position: Position, stack: ItemStack): ProjectileEntity = ThrowingKnifeEntity(material, world).apply { setPosition(position.x, position.y, position.z) }
                })
            }
        }
    }
}