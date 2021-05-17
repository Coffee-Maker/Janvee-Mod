package me.coffeemaker.janvee

import io.netty.buffer.ByteBuf
import net.minecraft.client.world.ClientWorld
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.contracts.contract
import kotlin.math.floor

fun Double.floorToInt() = floor(this).toInt()
fun Float.floorToInt() = floor(this).toInt()

fun PacketByteBuf.writeVec3d(vec3d: Vec3d) {
    this.writeDouble(vec3d.x)
    this.writeDouble(vec3d.y)
    this.writeDouble(vec3d.z)
}

fun PacketByteBuf.readVec3d() = Vec3d(this.readDouble(), this.readDouble(), this.readDouble())

fun PacketByteBuf.writeAngle(angle: Float): ByteBuf = this.writeByte((angle * 256 / 360).floorToInt())

fun PacketByteBuf.readAngle() = (this.readByte() * 360) / 256.0f

inline fun runIfClient(world: World, block: (ClientWorld) -> Unit) {
    if (world.isClient) {
        block(world as ClientWorld)
    }
}

inline fun runIfServer(world: World, block: (ServerWorld) -> Unit) {
    if (!world.isClient) {
        block(world as ServerWorld)
    }
}

val ToolMaterial.identifierName
    get() = when (this) {
        ToolMaterials.WOOD -> "wooden"
        ToolMaterials.STONE -> "stone"
        ToolMaterials.IRON -> "iron"
        ToolMaterials.GOLD -> "golden"
        ToolMaterials.DIAMOND -> "diamond"
        ToolMaterials.NETHERITE -> "netherite"
        else -> throw java.lang.UnsupportedOperationException("Type is not supported")
    }