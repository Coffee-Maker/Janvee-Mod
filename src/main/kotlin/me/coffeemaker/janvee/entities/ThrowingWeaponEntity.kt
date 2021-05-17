package me.coffeemaker.janvee.entities

import me.coffeemaker.janvee.items.weapons.throwing.ThrowingWeaponItem
import me.coffeemaker.janvee.runIfServer
import net.minecraft.entity.EntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.particle.BlockStateParticleEffect
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ThrowingWeaponEntity<T : ThrownItemEntity> : ThrownItemEntity {
    constructor(entityType: EntityType<out ThrownItemEntity>, owner: LivingEntity, world: World) : super(entityType, owner, world)
    constructor(entityType: EntityType<out ThrownItemEntity>, world: World) : super(entityType, world)

    private val particleEffect by lazy { ItemStackParticleEffect(ParticleTypes.ITEM, this.stack) }

    override fun onEntityHit(entityHitResult: EntityHitResult) {
        super.onEntityHit(entityHitResult)
        entityHitResult.entity.damage(DamageSource.thrownProjectile(this, this.owner), (this.defaultItem as ThrowingWeaponItem).throwingDamage)

        runIfServer(this.world) { world ->
            if(this.random.nextFloat() > (this.defaultItem as ThrowingWeaponItem).impactBreakChance) {
                world.spawnEntity(ItemEntity(this.world, this.x, this.y, this.z, ItemStack(this.defaultItem, 1)))
            } else {
                world.spawnParticles(
                    particleEffect,
                    this.x, this.y, this.z,
                    8,
                    0.04, 0.04, 0.04,
                    0.08
                )
            }

            this.discard()
        }
    }

    override fun onCollision(hitResult: HitResult) {
        super.onCollision(hitResult)
    }

    override fun onBlockHit(blockHitResult: BlockHitResult) {
        super.onBlockHit(blockHitResult)

        runIfServer(this.world) { world ->
            if(this.random.nextFloat() > (this.defaultItem as ThrowingWeaponItem).impactBreakChance) {
                world.spawnEntity(ItemEntity(this.world, blockHitResult.pos.x, blockHitResult.pos.y, blockHitResult.pos.z, ItemStack(this.defaultItem, 1)))
            } else {
                world.spawnParticles(
                    particleEffect,
                    this.x, this.y, this.z,
                    8,
                    0.04, 0.04, 0.04,
                    0.08
                )
            }

            world.spawnParticles(
                BlockStateParticleEffect(
                    ParticleTypes.BLOCK,
                    this.world.getBlockState(BlockPos(blockHitResult.blockPos.x,blockHitResult.blockPos.y, blockHitResult.blockPos.z))
                ),
                blockHitResult.pos.x, blockHitResult.pos.y, blockHitResult.pos.z,
                8,
                0.04, 0.04, 0.04,
                0.08
            )

            this.discard()
        }
    }
}