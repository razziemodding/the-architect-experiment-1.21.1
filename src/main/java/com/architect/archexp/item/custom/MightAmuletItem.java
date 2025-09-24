package com.architect.archexp.item.custom;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.item.ModComponents;
import com.architect.archexp.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MightAmuletItem extends Item {
    public MightAmuletItem(Settings settings) {
        super(settings);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        boolean shouldTick = stack.get(ModComponents.WAIT_FOR_DELAY);
        while (shouldTick && entity instanceof PlayerEntity) {
            int lastVal = stack.get(ModComponents.ITEM_DELAY);
            stack.set(ModComponents.ITEM_DELAY, lastVal + 1 );

            if (stack.get(ModComponents.ITEM_DELAY) == 13) {
                stack.set(ModComponents.WAIT_FOR_DELAY, false);
                break;
            }
        }

        if (stack.get(ModComponents.ITEM_DELAY) == 13) {
            doEffects(world, (PlayerEntity) entity);
            stack.set(ModComponents.ITEM_DELAY, 0);
        }

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand.equals(Hand.OFF_HAND)) {
            ItemStack handItem = user.getEquippedStack(EquipmentSlot.OFFHAND);
            user.getItemCooldownManager().set(this, 600);

            if (!world.isClient) {
                world.playSound(null, user.getBlockPos(), ModSounds.MIGHT_AMULET_USE, SoundCategory.PLAYERS, 1f, 1f);
            }

            handItem.set(ModComponents.WAIT_FOR_DELAY, true);

            return TypedActionResult.success(handItem);
        } else return TypedActionResult.fail(this.getDefaultStack());
    }

    private void doEffects(World world, PlayerEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 140, 1, true, false));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1, true, false));
        if (!world.isClient()) {
            TheArchitectExperiment.LOGGER.info("sound");

            ((ServerWorld) world).spawnParticles(ParticleTypes.CRIMSON_SPORE,
                    user.getX(), user.getY() + 1.2, user.getZ(),
                    50, 0.125, 0.0, 0.125, 0.1);

            ((ServerWorld) world).spawnParticles(ParticleTypes.RAID_OMEN,
                    user.getX(), user.getY() + 1.2, user.getZ(),
                    15, 0.0, 0.0, 0.0, 0.005);
        }
    }
}
