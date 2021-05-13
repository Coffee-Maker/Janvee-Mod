package me.coffeemaker.janvee

import me.coffeemaker.janvee.data.PlayerData
import me.coffeemaker.janvee.items.ItemRegistry
import java.util.*

@Suppress("unused")
fun init() {
    println("Hello Fabric world!")
    ItemRegistry.registerItems()
}

class Janvee {
    companion object {
        val MODID = "janvee"
        val playerData: MutableMap<UUID, PlayerData> = mutableMapOf()
    }
}