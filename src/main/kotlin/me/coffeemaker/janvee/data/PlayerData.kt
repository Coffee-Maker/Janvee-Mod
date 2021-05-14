package me.coffeemaker.janvee.data

import net.minecraft.entity.attribute.EntityAttributeInstance
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

data class PlayerData(var player: ServerPlayerEntity, val values: MutableMap<String, Any> = mutableMapOf(
    "version" to 0,
    "_healthLvl" to 0,
    "agilityLvl" to 0,
    "meleeLvl" to 0,
    "rangedLvl" to 0,
    "toolsLvl" to 0
)) {
    companion object {
        val healthUUID: UUID = UUID.fromString("235c9d8d-5398-4a3c-b5c6-a55caab58470")
        const val defaultHealth = 6
    }

    var version: Int by values
    private var _healthLvl: Int by values
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
    var agilityLvl: Int by values
    var meleeLvl: Int   by values
    var rangedLvl: Int  by values
    var toolsLvl: Int   by values
}