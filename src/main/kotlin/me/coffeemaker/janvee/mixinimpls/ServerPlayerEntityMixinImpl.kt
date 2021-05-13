package me.coffeemaker.janvee.mixinimpls

import me.coffeemaker.janvee.Janvee
import me.coffeemaker.janvee.data.PlayerData
import me.coffeemaker.janvee.mixin.ServerPlayerEntityMixin
import net.minecraft.nbt.CompoundTag
import java.lang.UnsupportedOperationException
import kotlin.reflect.full.memberProperties

// Mixins HAVE to be written in java due to constraints in the mixin system.
class ServerPlayerEntityMixinImpl {

    companion object {
        @JvmStatic
        fun writeCustomDataToNbt(tag: CompoundTag, entity: ServerPlayerEntityMixin) {

            // Try to get the data, if it is not already created for this player then create it
            val janveeTag = if (tag.contains("Janvee")) tag.getCompound("Janvee") else CompoundTag()

            // If we are not currently storing the data in our system, store it now
            val data = Janvee.playerData[entity.uuid] ?: run {
                val newData = PlayerData()
                Janvee.playerData[entity.uuid] = newData
                newData
            }

            for (property in PlayerData::class.memberProperties) {
                when (val value = property.get(data)) {
                    is Int -> janveeTag.putInt(property.name, value)
                }
            }

            tag.put("Janvee", janveeTag)
        }

        @JvmStatic
        fun readCustomDataFromNbt(tag: CompoundTag, entity: ServerPlayerEntityMixin) {
            // Try to get the Janvee tag, if the data has not yet been initialised for this player, create it
            val janveeTag = tag.getCompound("Janvee") ?: CompoundTag()

            val data = Janvee.playerData[entity.uuid] ?: PlayerData().apply { Janvee.playerData[entity.uuid] = this }

            for (property in PlayerData::class.memberProperties) {
                data.values[property.name] = when (val value = property.get(data)) {
                    is Int -> value
                    is MutableMap<*, *> -> continue
                    else -> throw UnsupportedOperationException("Unsupported type: ${property.returnType}")
                }
            }

            tag.put("Janvee", janveeTag)
        }
    }
}