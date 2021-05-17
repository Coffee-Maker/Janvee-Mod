package me.coffeemaker.janvee.entities

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.items.interfaces.ITierItem
import me.coffeemaker.janvee.items.interfaces.ITierItemEntity
import me.coffeemaker.janvee.items.weapons.throwing.ThrowingKnifeItem
import me.coffeemaker.janvee.packets.EntitySpawnPacket
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.network.Packet
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import kotlin.TODO as TODO1

class ThrowingKnifeEntity : ThrowingWeaponEntity<ThrowingKnifeEntity> {
    companion object : ITierItemEntity<ThrowingKnifeEntity> {
        override val entityName = "throwing_knife"
        override val instantiationFunction = { material: ToolMaterial, _: EntityType<*>, world: World -> ThrowingKnifeEntity(material, world) }
        override val entityTypes = mutableMapOf<ToolMaterial, EntityType<ThrowingKnifeEntity>>()
    }

    private val material: ToolMaterial

    constructor(material: ToolMaterial, owner: LivingEntity, world: World) : super(ThrowingKnifeEntity[material], owner, world) { this.material = material }
    constructor(material: ToolMaterial, world: World) : super(ThrowingKnifeEntity[material], world) { this.material = material }

    override fun getDefaultItem(): Item = ThrowingKnifeItem[material]
    override fun createSpawnPacket(): Packet<*> = EntitySpawnPacket.create(this, Janvee.ENTITY_SPAWN_PACKET_IDENTIFIER)
}