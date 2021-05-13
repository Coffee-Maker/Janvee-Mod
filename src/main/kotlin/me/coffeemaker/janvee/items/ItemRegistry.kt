package me.coffeemaker.janvee.items

import me.coffeemaker.janvee.Janvee
import net.minecraft.item.ItemGroup

import net.fabricmc.fabric.api.item.v1.FabricItemSettings

import net.minecraft.item.Item
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class ItemRegistry {
    companion object {
        val STONE_DAGGER = Dagger(ToolMaterials.STONE, 3, -1.6f, FabricItemSettings().group(ItemGroup.COMBAT))

        fun registerItems(){
            Registry.register(Registry.ITEM, Identifier(Janvee.MODID, "stone_dagger"), STONE_DAGGER)
        }
    }
}