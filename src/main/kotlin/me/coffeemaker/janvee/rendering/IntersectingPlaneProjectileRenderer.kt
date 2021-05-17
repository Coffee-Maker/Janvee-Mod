package me.coffeemaker.janvee.rendering

import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

class IntersectingPlaneProjectileRenderer(private val texture: Identifier, private val length: Int, private val width: Int, context: EntityRendererFactory.Context) : EntityRenderer<Entity>(context) {
    override fun render(
        projectileEntity: Entity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        lightUV: Int
    ) {
        matrixStack.push()

        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, projectileEntity.prevYaw, projectileEntity.yaw)))
        //matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(g, projectileEntity.prevPitch, projectileEntity.pitch)))
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-projectileEntity.pitch))

        /*val shake = persistentProjectileEntity.shake as Float - g
        if (shake > 0.0f) {
            val t = -MathHelper.sin(shake * 3.0f) * shake
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(t))
        }*/

        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(45.0f))
        matrixStack.scale(1.0f / 16.0f, 1.0f / 16.0f, 1.0f / 16.0f)

        val vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(getTexture(projectileEntity)))
        val entry = matrixStack.peek()
        val modelSpace = entry.model
        val orientation = entry.normal

        for (u in 0..3) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f))
            MeshUtility.addVertex(modelSpace, orientation, vertexConsumer, -width / 2, 0, -length / 2, 0.0f, 1.0f, 0, 1, 0, lightUV)
            MeshUtility.addVertex(modelSpace, orientation, vertexConsumer, -width / 2, 0, length / 2, 0.0f, 0.0f, 0, 1, 0, lightUV)
            MeshUtility.addVertex(modelSpace, orientation, vertexConsumer, width / 2, 0, length / 2, 1.0f, 0.0f, 0, 1, 0, lightUV)
            MeshUtility.addVertex(modelSpace, orientation, vertexConsumer, width / 2, 0, -length / 2, 1.0f, 1.0f, 0, 1, 0, lightUV)
        }

        matrixStack.pop()

        super.render(projectileEntity, f, g, matrixStack, vertexConsumerProvider, lightUV)
    }

    override fun getTexture(entity: Entity): Identifier = this.texture
}