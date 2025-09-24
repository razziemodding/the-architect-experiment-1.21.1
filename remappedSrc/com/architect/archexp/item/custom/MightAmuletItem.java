package com.architect.archexp.item.custom;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.sound.ModSounds;
import net.fabricmc.fabric.mixin.client.particle.ParticleManagerMixin;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.*;
import net.minecraft.server.command.ParticleCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MightAmuletItem extends Item {
    public MightAmuletItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) { //todo: make work half second later
        if (hand.equals(Hand.OFF_HAND)) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));

            if (!world.isClient()) {
                TheArchitectExperiment.LOGGER.info("sound");
                world.playSound(null, user.getBlockPos(), ModSounds.MIGHT_AMULET_USE, SoundCategory.PLAYERS, 0.9f, 0.9f);

                ((ServerWorld) world).spawnParticles(ParticleTypes.ENCHANT, user.getX(), user.getY() + 2.5, user.getZ(), 100, 0.3, 0.2, 0.3, 8.0);
            }
            return ActionResult.SUCCESS;
        } else return ActionResult.FAIL;
    }
}
