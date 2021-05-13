package me.coffeemaker.janvee.mixin;

import com.mojang.authlib.GameProfile;
import me.coffeemaker.janvee.mixinimpls.ServerPlayerEntityMixinImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(
            method = "writeCustomDataToNbt(Lnet/minecraft/nbt/CompoundTag;)V",
            at = @At("RETURN")
    )
    private void writeCustomDataToNbt(CompoundTag tag, CallbackInfo callbackInfo) {
        ServerPlayerEntityMixinImpl.writeCustomDataToNbt(tag, this);
    }

    @Inject(
            method = "readCustomDataFromNbt(Lnet/minecraft/nbt/CompoundTag;)V",
            at = @At("RETURN")
    )
    private void readCustomDataFromNbt(CompoundTag tag, CallbackInfo callbackInfo) {
        ServerPlayerEntityMixinImpl.readCustomDataFromNbt(tag, this);
    }
}
