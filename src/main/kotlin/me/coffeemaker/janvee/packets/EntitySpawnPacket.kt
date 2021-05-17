package me.coffeemaker.janvee.packets

import io.netty.buffer.Unpooled
import me.coffeemaker.janvee.writeAngle
import me.coffeemaker.janvee.writeVec3d
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.Entity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


class EntitySpawnPacket {
    companion object {
        fun create(entity: Entity, packetIdentifier: Identifier): Packet<*> {
            check(!entity.world.isClient) { "EntitySpawnPacket.create called on the logical client!" }

            val packetByteBuf = PacketByteBuf(Unpooled.buffer()).apply {
                writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.type))
                writeUuid(entity.uuid)
                writeVarInt(entity.id)
                writeVec3d(entity.pos)
                writeAngle(entity.pitch)
                writeAngle(entity.yaw)
            }

            return ServerPlayNetworking.createS2CPacket(packetIdentifier, packetByteBuf)
        }
    }
}