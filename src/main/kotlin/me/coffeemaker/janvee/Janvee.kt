package me.coffeemaker.janvee

import me.coffeemaker.janvee.data.PlayerData
import java.util.*

@Suppress("unused")
fun init() {
    println("Hello Fabric world!")
}

class Janvee {
    companion object {
        val playerData: MutableMap<UUID, PlayerData> = mutableMapOf()
    }
}