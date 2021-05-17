package me.coffeemaker.janvee.mixin;

import me.coffeemaker.janvee.mixinimpls.PlayerEntityMixinImpl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow @Final private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "getMovementSpeed()F",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getMovementSpeed(CallbackInfoReturnable<Float> callbackInfo) {
        callbackInfo.setReturnValue(PlayerEntityMixinImpl.getMovementSpeed((PlayerEntity) (Object) this));
    }

    @ModifyConstant(
            method = "tickMovement()V",
            constant = @Constant(floatValue = 0.02f)
    )
    public float walkingFlyingSpeed(float oldValue) {
        return PlayerEntityMixinImpl.walkingFlyingSpeed((PlayerEntity) (Object) this, oldValue);
    }

    @Redirect(
            method = "tickMovement()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"
            )
    )
    public double redirectGetAttributeValue(PlayerEntity entity, EntityAttribute entityAttribute) {
        return 0.0;
    }

    @ModifyConstant(
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            constant = @Constant(floatValue = 0.4f),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;squaredDistanceTo(Lnet/minecraft/entity/Entity;)D"),
                    to = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/sound/SoundEvents;ENTITY_PLAYER_ATTACK_SWEEP:Lnet/minecraft/sound/SoundEvent;")
            )
    )
    public float applyDynamicKnockback(float oldValue) {
        return PlayerEntityMixinImpl.getPlayerKnockback(oldValue, inventory);
    }

    @Group(name = "PlayerEntity_fixSwordItemCheck")
    @Redirect(
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "CONSTANT",
                    args = "classValue=net/minecraft/item/SwordItem"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I")
            )
    )
    public boolean fixSwordItemCheckDev(Object reference, Class<SwordItem> clazz) {
        return PlayerEntityMixinImpl.fixSwordItemCheck(reference);
    }

    @Group(name = "PlayerEntity_fixSwordItemCheck")
    @Redirect(
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "CONSTANT",
                    args = "classValue=net/minecraft/class_1829"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I")
            )
    )
    public boolean fixSwordItemCheckProd(Object reference, Class<SwordItem> clazz) {
        return PlayerEntityMixinImpl.fixSwordItemCheck(reference);
    }
}
