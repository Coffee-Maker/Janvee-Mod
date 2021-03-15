package me.coffeemaker.janvee.mixin

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.data.PlayerData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.network.ServerPlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

// Mixins HAVE to be written in java due to constraints in the mixin system.
@Mixin(ServerPlayerEntity::class)
abstract class ServerPlayerEntityMixin : PlayerEntity(null, null, 0f, null) {
    @Inject(
        method = ["writeCustomDataToNbt(Lnet/minecraft/nbt/CompoundTag;)V"],
        at = [At("RETURN")]
    )
    private fun writeCustomDataToNbt(tag: CompoundTag, callbackInfo: CallbackInfo) {

        // Try to get the data, if it is not already created for this player then create it
        val janveeTag = if (tag.contains("Janvee")) tag.getCompound("Janvee") else CompoundTag()

        // If we are not currently storing the data in our system, store it now
        val data = Janvee.playerData[uuid] ?: run {
            val newData = PlayerData.default()
            Janvee.playerData[uuid] = newData
            newData
        }

        for (t in data.map.keys)
            janveeTag.putInt(t, data.map[t] ?: 0)

        tag.put("Janvee", janveeTag)
    }

    @Inject(
        method = ["readCustomDataFromNbt(Lnet/minecraft/nbt/CompoundTag;)V"],
        at = [At("RETURN")]
    )
    private fun readCustomDataToNbt(tag: CompoundTag, callbackInfo: CallbackInfo) {
        // The data has not yet been initialised for this player, create it
        val janveeTag = if (tag.contains("Janvee")) tag.getCompound("Janvee") else CompoundTag()
        val data = Janvee.playerData[uuid] ?: PlayerData.default()
        for (t in janveeTag.keys) {
            data.map[t] = janveeTag.getInt(t)
        }
    }
}