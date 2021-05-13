package me.coffeemaker.janvee

import me.coffeemaker.janvee.data.PlayerData
import me.coffeemaker.janvee.items.ItemRegistry
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.registry.CommandRegistry
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.server.command.CommandManager
import java.util.*

@Suppress("unused")
fun init() {
    println("Hello Fabric world!")
    ItemRegistry.registerItems()

    CommandRegistrationCallback.EVENT.register{dispatcher, dedicated ->
        dispatcher.register(CommandManager.literal("owo").executes{
            println(0)
            val health = (20 + PlayerData.defaultHealth).toDouble()
            val instance = it.source.player.attributes.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!
            val entityAttributeModifier = EntityAttributeModifier(PlayerData.healthUUID, "janvee:health", health, EntityAttributeModifier.Operation.ADDITION)
            println(instance.getModifiers())
            if (instance.hasModifier(entityAttributeModifier)) {
                instance.removeModifier(PlayerData.healthUUID)
                println(1)
            }
            println(instance.getModifiers())
            instance.addTemporaryModifier(entityAttributeModifier)
            println(instance.getModifiers())
            1
        })
    }
}

class Janvee {
    companion object {
        val MODID = "janvee"
        val playerData: MutableMap<UUID, PlayerData> = mutableMapOf()
    }
}