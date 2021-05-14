package me.coffeemaker.janvee.items

import me.coffeemaker.janvee.items.interfaces.IModifyAttackRange
import me.coffeemaker.janvee.items.interfaces.ITierTool
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials

class DaggerItem(toolMaterial: ToolMaterial, attackDamage: Int) :
    SwordItem(toolMaterial, attackDamage, 0f, FabricItemSettings().group(ItemGroup.COMBAT)), IModifyAttackRange{

    companion object : ITierTool<DaggerItem> {
        private val damage = mutableMapOf<ToolMaterial, Int>(
            ToolMaterials.WOOD to -1,
            ToolMaterials.STONE to 0,
            ToolMaterials.IRON to 1,
            ToolMaterials.GOLD to 0,
            ToolMaterials.DIAMOND to 2,
            ToolMaterials.NETHERITE to 3,
        )

        override val toolName: String
            get() = "dagger"

        override val tools: MutableMap<ToolMaterial, DaggerItem>
            get() = mutableMapOf()

        override fun getByMaterial(material: ToolMaterial): DaggerItem = DaggerItem(material, damage[material]!!)
    }

    override val range: Float
        get() = 3f
}