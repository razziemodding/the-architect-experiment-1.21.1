package com.architect.archexp.item.custom;

import com.architect.archexp.sound.ModSounds;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WindAmuletItem extends Item {
    public WindAmuletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) { //
        if (hand.equals(Hand.OFF_HAND)) {
            user.getItemCooldownManager().set(this, 200); //25 second coodlwon

            if (!world.isClient) {
                world.playSound(null, user.getBlockPos(), ModSounds.WIND_AMULET_USE, SoundCategory.PLAYERS, 1f, 1f);

                Vec3d lookD = user.getRotationVector();
                Vec3d vel = new Vec3d(lookD.x * 1.65, 0.65, lookD.z * 1.65);

                for (ServerPlayerEntity inRange : PlayerLookup.around(((ServerWorld) world), user.getPos(), 4)) {
                    //TheArchitectExperiment.LOGGER.info(String.valueOf(inRange));
                    if ( inRange != user) {
                        inRange.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 50, 255)); //glow slow fire jumpboost
                        inRange.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 50, 0)); //glow slow fire jumpboost
                        inRange.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 50, 0)); //glow slow fire jumpboost
                        inRange.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 50, 255)); //glow slow fire jumpboost
                        inRange.setVelocity(vel.x, vel.y, vel.z);
                        inRange.velocityModified = true;
                    }
                }
            }
            return TypedActionResult.success(user.getEquippedStack(EquipmentSlot.OFFHAND));
        }

        return TypedActionResult.fail(this.getDefaultStack());
    }
}
