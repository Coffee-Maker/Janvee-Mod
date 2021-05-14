package me.coffeemaker.janvee.mixin;


import me.coffeemaker.janvee.mixinimpls.LivingEntityMixinImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // This is for us to control the velocity impulse you get when jumping and sprinting
    @ModifyConstant(
            method = "jump()V",
            constant = @Constant(floatValue = 0.2f)
    )
    private float jumpSpeedImpulse(float oldValue) {
        return LivingEntityMixinImpl.jumpSpeedImpulse(oldValue, (LivingEntity) (Object) this);
    }
}
