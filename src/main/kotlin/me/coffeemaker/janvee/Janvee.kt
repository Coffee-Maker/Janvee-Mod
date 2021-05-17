package me.coffeemaker.janvee

import me.coffeemaker.janvee.data.PlayerData
import me.coffeemaker.janvee.registration.EntityRendererRegistration
import me.coffeemaker.janvee.registration.ItemRegistration
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.*

@Suppress("unused")
fun init() {
    ItemRegistration.registerItems()
    ItemRegistration.registerItemFunctionalities()
}

@Suppress("unused")
fun initClient() {
    EntityRendererRegistration.registerRenderers()

    ClientPlayNetworking.registerGlobalReceiver(Janvee.ENTITY_SPAWN_PACKET_IDENTIFIER) { client, _, byteBuf, _ ->
        val entityType = Registry.ENTITY_TYPE.get(byteBuf.readVarInt())
        val uuid = byteBuf.readUuid()
        val entityID = byteBuf.readVarInt()
        val position = byteBuf.readVec3d()
        val pitch = byteBuf.readAngle()
        val yaw = byteBuf.readAngle()

        val world = client.world
        check(world != null) { "Tried to spawn entity in null world." }

        entityType.create(client.world)?.let {
            it.updateTrackedPosition(position)
            it.setPos(position.x, position.y, position.z)
            it.pitch = pitch
            it.yaw = yaw
            it.setEntityId(entityID)
            it.uuid = uuid
            world.addEntity(entityID, it)
        }
    }
}


class Janvee {
    companion object {
        val MODID = "janvee"
        val ENTITY_SPAWN_PACKET_IDENTIFIER = Identifier(MODID, "entity_spawn_packet")

        val playerData: MutableMap<UUID, PlayerData> = mutableMapOf()
    }
}