package me.coffeemaker.janvee.data

import net.minecraft.entity.attribute.EntityAttributeInstance
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

data class PlayerData(var player: ServerPlayerEntity, val values: MutableMap<String, Any> = mutableMapOf()) {
    companion object {
        val healthUUID: UUID = UUID.fromString("235c9d8d-5398-4a3c-b5c6-a55caab58470")
        const val defaultHealth = 6
    }

    var version: Int    by values.withDefault { 0 }
    private var _healthLvl: Int  by values.withDefault { 0 }
    var healthLvl: Int
        get() = _healthLvl
        set(value){
            _healthLvl = value
            val health = (value - 20 + defaultHealth).toDouble()
            val instance = player.attributes.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!
            val entityAttributeModifier = EntityAttributeModifier(healthUUID, "janvee:health", health, EntityAttributeModifier.Operation.ADDITION)
            instance.tryRemoveModifier(healthUUID)
            instance.addPersistentModifier(entityAttributeModifier)
        }
    var agilityLvl: Int by values.withDefault { 0 }
    var meleeLvl: Int   by values.withDefault { 0 }
    var rangedLvl: Int  by values.withDefault { 0 }
    var toolsLvl: Int   by values.withDefault { 0 }
}