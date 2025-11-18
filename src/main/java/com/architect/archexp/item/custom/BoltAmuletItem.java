package com.architect.archexp.item.custom;

import com.architect.archexp.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoltAmuletItem extends Item {
    public BoltAmuletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!hand.equals(Hand.OFF_HAND)) {
            return TypedActionResult.fail(this.getDefaultStack());
        }

        Vec3d lookD = user.getRotationVector();
        Vec3d vel = new Vec3d(lookD.x * 1.1, 0.005, lookD.z * 1.1); //make go 7 blocks

        user.setVelocity(vel.x, vel.y, vel.z);
        user.velocityModified = true;
        user.getItemCooldownManager().set(this, 60); //increase cd

        if (!world.isClient) {
            ServerWorld server = (ServerWorld) world;
            world.playSound(null, user.getBlockPos(), ModSounds.BOLT_AMULET_USE, SoundCategory.PLAYERS, 1f, 1f);

            server.spawnParticles(ParticleTypes.ITEM_COBWEB,
                    user.getX(), user.getY(), user.getZ(), 5, 0.5, 0.5, 0.5, 100);
            server.spawnParticles(ParticleTypes.DUST_PLUME,
                    user.getX(), user.getY(), user.getZ(), 10, 0.5, 0.5, 0.5, 0.01);
            server.spawnParticles(ParticleTypes.SMALL_GUST,
                    user.getX(), user.getY(), user.getZ(), 5, 0.5, 0.5, 0.5, 10);
        }
        return TypedActionResult.success(user.getEquippedStack(EquipmentSlot.OFFHAND));
    }
}
