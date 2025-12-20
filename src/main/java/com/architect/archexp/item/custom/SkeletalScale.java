package com.architect.archexp.item.custom;

import com.architect.archexp.damage_source.ModDamageSources;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKeys;
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
            manager.executeWithPrefix(user.getServer().getCommandSource(),
                    "execute as " + entity.getNameForScoreboard() + " at " + entity.getNameForScoreboard() + " run pal animatedcircle minecraft:small_gust ~ ~1 ~ 100 2 0 3.14 true false true 2.0 0.0 0.0 0.0 2.0 1.0 true 0");
            DamageSource source = new DamageSource(user.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.MAGIC));
            entity.damage(source, 4);
            user.getItemCooldownManager().set(this, 45);
            stack.damage((int) (stack.getMaxDamage() * 0.013), user, EquipmentSlot.MAINHAND);
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
