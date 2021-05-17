package me.coffeemaker.janvee.rendering

import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumer
import net.minecraft.util.math.Matrix3f
import net.minecraft.util.math.Matrix4f

class MeshUtility {
    companion object {
        fun addVertex(
            space: Matrix4f?,
            orientation: Matrix3f?,
            vertexConsumer: VertexConsumer,
            x: Int,
            y: Int,
            z: Int,
            u: Float,
            v: Float,
            normalX: Int,
            normalY: Int,
            normalZ: Int,
            lightUV: Int
        ) {
            vertexConsumer.vertex(space, x.toFloat(), y.toFloat(), z.toFloat())
                .color(255, 255, 255, 255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(lightUV)
                .normal(orientation, normalX.toFloat(), normalY.toFloat(), normalZ.toFloat())
                .next()
        }
    }
}