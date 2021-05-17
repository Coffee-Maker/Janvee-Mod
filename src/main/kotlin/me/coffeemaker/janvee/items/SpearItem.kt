package me.coffeemaker.janvee.items

import me.coffeemaker.janvee.items.interfaces.IMeleeWeapon
import me.coffeemaker.janvee.items.interfaces.IModifyAttackRange
import me.coffeemaker.janvee.items.interfaces.IModifyKnockback
import me.coffeemaker.janvee.items.interfaces.ITierItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterial

class SpearItem(toolMaterial: ToolMaterial) : SwordItem(toolMaterial, 1, -1.5f, FabricItemSettings().group(ItemGroup.COMBAT)), IModifyAttackRange, IModifyKnockback, IMeleeWeapon {
    companion object : ITierItem<SpearItem> {
        override val itemName = "spear"
        override val variants = mutableMapOf<ToolMaterial, SpearItem>()

        override fun getByMaterial(material: ToolMaterial): SpearItem = SpearItem(material)
    }

    override val knockback = 50f
    override val range = 6.5f
}