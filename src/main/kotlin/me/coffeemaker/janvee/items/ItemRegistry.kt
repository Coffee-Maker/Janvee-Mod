package me.coffeemaker.janvee.items

import me.coffeemaker.janvee.Janvee

import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class ItemRegistry {
    companion object {
        val CUSTOM_ARROW = CustomArrowItem()

        fun registerItems(){
            DaggerItem.registerTool()
            SpearItem.registerTool()
            Registry.register(Registry.ITEM, Identifier(Janvee.MODID, "custom_arrow"), CUSTOM_ARROW)
        }
    }
}