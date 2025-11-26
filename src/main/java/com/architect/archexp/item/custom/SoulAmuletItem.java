package com.architect.archexp.item.custom;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class SoulAmuletItem extends Item { //todo: fix effect not clearing if they shift click into an inventory
    public SoulAmuletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!hand.equals(Hand.OFF_HAND)) {
            return TypedActionResult.fail(this.getDefaultStack());
        }
        ItemStack handItem = user.getEquippedStack(EquipmentSlot.OFFHAND);

        if (!handItem.get(ModComponents.SOUL_AMULET_PLR).equals("") && !handItem.get(ModComponents.SOUL_AMULET_PLR).equals(user.getUuidAsString())) {
            TheArchitectExperiment.clearSoulComponents(handItem);

            return TypedActionResult.pass(handItem);
        }

        if (Objects.equals(handItem.get(ModComponents.SOUL_AMULET_ACTIVE), true)) {
            //TheArchitectExperiment.LOGGER.info("active");
            TheArchitectExperiment.removeSoulEffects(user, handItem);
            user.getItemCooldownManager().set(handItem.getItem(), 20); //45-second cooldown
            return TypedActionResult.success(handItem);
        } else {
            //TheArchitectExperiment.LOGGER.info("false");
            if (!world.isClient) {
                //TheArchitectExperiment.LOGGER.info("server");
                Vec3d lookD = user.getRotationVector();
                Vec3d vel = new Vec3d(lookD.x * 1.5, 0.1, lookD.z * 1.5);

                user.setVelocity(vel.x, vel.y, vel.z);
                user.velocityModified = true;
            }
        }

        if (user.hasStatusEffect(StatusEffects.SPEED)) {
            int dur = user.getStatusEffect(StatusEffects.SPEED).getDuration();
            int amp = user.getStatusEffect(StatusEffects.SPEED).getAmplifier();

            handItem.set(ModComponents.SOUL_AMULET_PLR_SPEED_LENGTH, dur);
            handItem.set(ModComponents.SOUL_AMULET_PLR_SPEED_AMP, amp);
        }

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 1, true, false));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, -1, 0, true, false));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, -1, 1, true, false));

        handItem.set(ModComponents.SOUL_AMULET_ACTIVE, true);
        handItem.set(ModComponents.SOUL_AMULET_PLR, user.getUuidAsString());
        //TheArchitectExperiment.LOGGER.info(handItem.get(ModComponents.SOUL_AMULET_ACTIVE).toString());

        if (!world.isClient) {
            ServerWorld server = (ServerWorld) world;
            world.playSound(null, user.getBlockPos(), ModSounds.SOUL_AMULET_USE, SoundCategory.PLAYERS, 1f, 1f);

            server.spawnParticles(ParticleTypes.SCULK_SOUL,
                    user.getX(), user.getY() + 1.2, user.getZ(),
                    15, 0, 0, 0, 0.2);
            server.spawnParticles(ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS,
                    user.getX(), user.getY() + 1, user.getZ(),
                    15, 0, 0, 0, 0.1);
        }

        return TypedActionResult.success(handItem);
    }


}