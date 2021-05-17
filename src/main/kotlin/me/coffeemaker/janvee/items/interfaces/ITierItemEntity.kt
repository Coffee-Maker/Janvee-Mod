package me.coffeemaker.janvee.items.interfaces

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.identifierName
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

interface ITierItemEntity<T : Entity> {
    val entityName: String
    val instantiationFunction: (ToolMaterial, EntityType<*>, World) -> T
    val entityTypes: MutableMap<ToolMaterial, EntityType<T>>

    fun registerVariants() {
        for (material in ToolMaterials.values()) {
            this.entityTypes[material] = Registry.register(
                Registry.ENTITY_TYPE,
                Identifier(Janvee.MODID, "${material.identifierName}_$entityName"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC) { entityType: EntityType<*>, world -> this.instantiationFunction(material, entityType, world) }
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
            )
        }
    }

    operator fun get(material: ToolMaterial): EntityType<T> = this.entityTypes[material] ?: throw IllegalStateException("Entities not initialized.")
}