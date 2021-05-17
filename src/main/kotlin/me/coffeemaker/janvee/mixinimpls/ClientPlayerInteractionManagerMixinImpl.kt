package me.coffeemaker.janvee.mixinimpls

import me.coffeemaker.janvee.items.interfaces.IModifyAttackRange
import net.minecraft.client.MinecraftClient

class ClientPlayerInteractionManagerMixinImpl {
    companion object {
        @JvmStatic
        fun getReachDistance(client: MinecraftClient): Float? = (client.player?.inventory?.mainHandStack?.item as? IModifyAttackRange)?.range
    }
}