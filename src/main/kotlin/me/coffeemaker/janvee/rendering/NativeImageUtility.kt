package me.coffeemaker.janvee.rendering

import net.minecraft.client.texture.NativeImage
import kotlin.math.roundToInt

class NativeImageUtility {
    companion object {
        fun overlayImage(image1: NativeImage, image2: NativeImage): NativeImage {
            val result = NativeImage(image1.width, image1.height, false).apply { copyFrom(image1) }
            for (x in 0..(image1.width)) {
                for (y in 0..(image1.height)) {
                    val color1 = image1.getPixelColor(x, y)
                    val color2 = image2.getPixelColor(x, y)
                    result.setPixelColor(x, y, NativeImage.getAbgrColor(
                        NativeImage.getAlpha(color1).coerceAtLeast(NativeImage.getAlpha(color2)),
                        overlayChannel(NativeImage.getBlue(color1), NativeImage.getBlue(color2), NativeImage.getAlpha(color2)),
                        overlayChannel(NativeImage.getGreen(color1), NativeImage.getGreen(color2), NativeImage.getAlpha(color2)),
                        overlayChannel(NativeImage.getRed(color1), NativeImage.getRed(color2), NativeImage.getAlpha(color2))
                    ))
                }
            }

            return result
        }

        private fun overlayChannel(channel1: Int, channel2: Int, alpha2: Int): Int =
            (channel1.toFloat() * (1f - alpha2.toFloat() / 255f) + channel2.toFloat() * alpha2.toFloat() / 255f).roundToInt()
    }
}