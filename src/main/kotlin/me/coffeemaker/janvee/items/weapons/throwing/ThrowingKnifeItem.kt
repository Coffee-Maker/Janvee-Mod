package me.coffeemaker.janvee.items.weapons.throwing

import me.coffeemaker.janvee.entities.ThrowingKnifeEntity
import me.coffeemaker.janvee.items.interfaces.ITierItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import kotlin.math.sqrt

class ThrowingKnifeItem(private val material: ToolMaterial) : ThrowingWeaponItem(FabricItemSettings().group(ItemGroup.COMBAT)) {
    companion object : ITierItem<ThrowingKnifeItem> {
        override val itemName = "throwing_knife"
        override val variants = mutableMapOf<ToolMaterial, ThrowingKnifeItem>()

        override fun getByMaterial(material: ToolMaterial): ThrowingKnifeItem = ThrowingKnifeItem(material)
    }

    override fun getEntity(world: World, user: PlayerEntity): ThrownItemEntity = ThrowingKnifeEntity(material, user, world).apply { this.owner = user }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!user.isCreative) {
            //user.itemCooldownManager.set(this, 20)
        }

        return super.use(world, user, hand)
    }

    override val throwingDamage = 1.0f + material.attackDamage * 3
    override val impactBreakChance = when (material) {
        ToolMaterials.WOOD -> 0.9f
        ToolMaterials.STONE -> 0.8f
        ToolMaterials.IRON -> 0.3f
        ToolMaterials.GOLD -> 0.95f
        ToolMaterials.DIAMOND -> 0.05f
        ToolMaterials.NETHERITE -> 0.0f
        else -> 0.0f
    }
}