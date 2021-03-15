package me.coffeemaker.janvee.data

data class PlayerData(val values: MutableMap<String, Any> = mutableMapOf()) {
    val version: Int    by values.withDefault { 0 }
    val healthLvl: Int  by values.withDefault { 0 }
    val agilityLvl: Int by values.withDefault { 0 }
    val meleeLvl: Int   by values.withDefault { 0 }
    val rangedLvl: Int  by values.withDefault { 0 }
    val toolsLvl: Int   by values.withDefault { 0 }
}