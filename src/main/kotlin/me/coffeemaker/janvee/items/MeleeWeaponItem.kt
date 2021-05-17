package me.coffeemaker.janvee.items

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.block.BlockState
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class MeleeWeaponItem(toolMaterial: ToolMaterial, attackDamage: Int, attackSpeed: Float, settings: Item.Settings) : ToolItem(toolMaterial, settings), Vanishable {
    private val attackDamage: Float = attackDamage.toFloat() + toolMaterial.attackDamage
    private val attributeModifiers: Multimap<EntityAttribute, EntityAttributeModifier> = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage.toDouble(), EntityAttributeModifier.Operation.ADDITION))
        .put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed.toDouble(), EntityAttributeModifier.Operation.ADDITION))
        .build()

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun canMine(state: BlockState?, world: World?, pos: BlockPos?, miner: PlayerEntity): Boolean = (this as SwordItem).canMine(state, world, pos, miner)

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun getMiningSpeedMultiplier(stack: ItemStack?, state: BlockState): Float = (this as SwordItem).getMiningSpeedMultiplier(stack, state)

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity?): Boolean = (this as SwordItem).postHit(stack, target, attacker)

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun postMine(stack: ItemStack, world: World?, state: BlockState, pos: BlockPos?, miner: LivingEntity?): Boolean = (this as SwordItem).postMine(stack, world, state, pos, miner)

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun isSuitableFor(state: BlockState): Boolean = (this as SwordItem).isSuitableFor(state)

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute?, EntityAttributeModifier?>? = (this as SwordItem).getAttributeModifiers(slot)
}