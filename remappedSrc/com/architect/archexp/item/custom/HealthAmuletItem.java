package com.architect.archexp.item.custom;

import com.architect.archexp.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class HealthAmuletItem extends Item {
    public HealthAmuletItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        if (!world.isClient && entity instanceof PlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.OFFHAND).isOf(ModItems.HEALTH_AMULET)) {
                giveHealth(player);
            } else {
                removeHealth(player);
            }
        }
    }

    private static void giveHealth(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(28.0);
    }
    private static void removeHealth(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
    }
}
