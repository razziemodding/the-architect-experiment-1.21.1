package com.architect.archexp.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        Vec3d vel = new Vec3d(lookD.x * 1.5, 0.1, lookD.z * 1.5); //make go 7 blocks

        user.setVelocity(vel.x, vel.y, vel.z);
        user.velocityModified = true;
        user.getItemCooldownManager().set(this, 40);
        return TypedActionResult.success(user.getEquippedStack(EquipmentSlot.OFFHAND));
    }
}
