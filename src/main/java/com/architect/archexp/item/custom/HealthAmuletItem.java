package com.architect.archexp.item.custom;

import com.architect.archexp.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HealthAmuletItem extends Item {
    public HealthAmuletItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClient && entity instanceof PlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.OFFHAND).isOf(ModItems.HEALTH_AMULET)) {
                giveHealth(player);
            } else {
                removeHealth(player);
            }
        }
    }


    private static void giveHealth(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(32.0);
    }
    private static void removeHealth(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
    }
}
