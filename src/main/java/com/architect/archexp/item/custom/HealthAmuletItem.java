package com.architect.archexp.item.custom;

import com.architect.archexp.item.ModItems;
import com.architect.archexp.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HealthAmuletItem extends Item {
    public HealthAmuletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!hand.equals(Hand.OFF_HAND)) {
            return TypedActionResult.fail(this.getDefaultStack());
        }

        ItemStack handItem = user.getEquippedStack(EquipmentSlot.OFFHAND);
        if (handItem.getItem().equals(ModItems.HEALTH_AMULET)) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, 3, true, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 10, true, false));
            user.getItemCooldownManager().set(handItem.getItem(), 1200);

            if (!world.isClient) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                        user.getX(), user.getY() + 1, user.getZ(),
                        40, 0.2, 0.6, 0.2, 1);
                world.playSound(null, user.getBlockPos(), ModSounds.HEALTH_AMULET_USE, SoundCategory.PLAYERS, 1f, 1f);
            }
            return TypedActionResult.success(handItem);
        } else return TypedActionResult.fail(this.getDefaultStack());
    }
}
