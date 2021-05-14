package me.coffeemaker.janvee.items

import me.coffeemaker.janvee.items.interfaces.IModifyAttackRange
import me.coffeemaker.janvee.items.interfaces.ITierTool
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials

class SpearItem(toolMaterial: ToolMaterial, attackDamage: Int) :
    SwordItem(toolMaterial, attackDamage, -1.5f, FabricItemSettings().group(ItemGroup.COMBAT)), IModifyAttackRange {

    override val range: Float
        get() = 8f

    companion object : ITierTool<SpearItem> {
        private val damage = mutableMapOf<ToolMaterial, Int>(
            ToolMaterials.WOOD to -1,
            ToolMaterials.STONE to 0,
            ToolMaterials.IRON to 1,
            ToolMaterials.GOLD to 0,
            ToolMaterials.DIAMOND to 2,
            ToolMaterials.NETHERITE to 3,
        )

        override val toolName: String
            get() = "spear"

        override val tools: MutableMap<ToolMaterial, SpearItem>
            get() = mutableMapOf()

        override fun getByMaterial(material: ToolMaterial): SpearItem = SpearItem(material, damage[material]!!)

    }
}