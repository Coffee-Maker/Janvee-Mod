package me.coffeemaker.janvee.mixinimpls

import net.minecraft.client.MinecraftClient
import kotlin.math.pow

class GameRendererMixinImpl {
    companion object {
        const val ENTITY_REACH_REDUCTION = 1.5

        @JvmStatic
        fun fixReachDistance(client: MinecraftClient): Double = client.interactionManager!!.reachDistance.toDouble() - ENTITY_REACH_REDUCTION

        @JvmStatic
        fun fixSquaredReachDistance(client: MinecraftClient): Double = (client.interactionManager!!.reachDistance.toDouble() - ENTITY_REACH_REDUCTION).pow(2)
    }
}