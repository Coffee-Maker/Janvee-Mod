package me.coffeemaker.janvee.mixin

import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Redirect

// THIS DOES NOT WORK YET!!!

@Mixin(InGameHud::class)
abstract class InGameHudMixin : DrawableHelper() {
    @Redirect(
        method = ["renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"
        )
    )
    fun getAttributeValue(entityAttribute: EntityAttribute): Double = 1.0
}