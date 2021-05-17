package me.coffeemaker.janvee.registration

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.entities.ThrowingKnifeEntity
import me.coffeemaker.janvee.identifierName
import me.coffeemaker.janvee.rendering.IntersectingPlaneProjectileRenderer
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.util.Identifier

class EntityRendererRegistration {
    companion object {
        fun registerRenderers() {
            ThrowingKnifeEntity.entityTypes.forEach { (material, type) ->
                EntityRendererRegistry.INSTANCE.register(type) {
                    IntersectingPlaneProjectileRenderer(Identifier(Janvee.MODID, "textures/entity/${material.identifierName}_throwing_knife.png"), 12, 3, it)
                }
            }
        }
    }
}