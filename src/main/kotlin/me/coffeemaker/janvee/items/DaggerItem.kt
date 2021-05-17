package me.coffeemaker.janvee.items

import me.coffeemaker.janvee.items.interfaces.IMeleeWeapon
import me.coffeemaker.janvee.items.interfaces.IModifyAttackRange
import me.coffeemaker.janvee.items.interfaces.ITierItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterial

class DaggerItem(toolMaterial: ToolMaterial) : SwordItem(toolMaterial, 1, 0f, FabricItemSettings().group(ItemGroup.COMBAT)), IModifyAttackRange, IMeleeWeapon {
    companion object : ITierItem<DaggerItem> {
        override val itemName = "dagger"
        override val variants = mutableMapOf<ToolMaterial, DaggerItem>()

        override fun getByMaterial(material: ToolMaterial): DaggerItem = DaggerItem(material)
    }

    override val range = 3.5f
}