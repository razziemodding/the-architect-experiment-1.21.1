package com.architect.archexp.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class SkeletalScale extends SwordItem {
    public SkeletalScale(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;

        if (!user.getWorld().isClient) {
            CommandManager manager = user.getServer().getCommandManager();
            manager.executeWithPrefix(user.getServer().getCommandSource(), "execute at " + entity.getNameForScoreboard() + " run summon minecraft:evoker_fangs ~ ~ ~");
            user.getItemCooldownManager().set(this, 45);
            stack.damage((int) (stack.getMaxDamage() * 0.013), user, EquipmentSlot.MAINHAND);
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
