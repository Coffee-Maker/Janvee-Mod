package me.coffeemaker.janvee.items.interfaces

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.identifierName
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

interface ITierItem<T : Item> {
    val itemName: String
    val variants: MutableMap<ToolMaterial, T>

    fun registerVariants() {
        for (material in ToolMaterials.values()) {
            Registry.register(Registry.ITEM, Identifier(Janvee.MODID, material.identifierName + "_" + itemName), this[material])
        }
    }

    fun getByMaterial(material: ToolMaterial): T
    operator fun get(material: ToolMaterial): T = variants[material] ?: getByMaterial(material).apply { variants[material] = this }
}