package me.coffeemaker.janvee.items.interfaces

import me.coffeemaker.janvee.Janvee
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.lang.UnsupportedOperationException

interface ITierTool<T> where T : Item {
    val toolName: String
    val tools: MutableMap<ToolMaterial, T>

    fun registerTool() {
        for (material in ToolMaterials.values()) {
            Registry.register(Registry.ITEM, Identifier(Janvee.MODID, getMaterialName(material) + "_" + toolName), get(material))
        }
    }

    abstract fun getByMaterial(material: ToolMaterial): T
    operator fun get(material: ToolMaterial): T = tools[material] ?: getByMaterial(material).apply { tools[material] = this }
}

fun getMaterialName(material: ToolMaterial): String {
    return when(material) {
        ToolMaterials.WOOD -> "wooden"
        ToolMaterials.STONE -> "stone"
        ToolMaterials.IRON -> "iron"
        ToolMaterials.GOLD -> "golden"
        ToolMaterials.DIAMOND -> "diamond"
        ToolMaterials.NETHERITE -> "netherite"
        else -> throw UnsupportedOperationException("Type is not supported")
    }
}