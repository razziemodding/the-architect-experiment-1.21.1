package com.architect.archexp.item.custom;

import com.architect.archexp.damage_source.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;

public class CustomAxeItem extends AxeItem {
    public CustomAxeItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof LivingEntity) {
            DamageSource targeteddmg = new DamageSource(attacker.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.EXECUTIONER_TARGETED));
            DamageSource selfdmg = new DamageSource(attacker.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.EXECUTIONER_SELF));

            target.damage(targeteddmg, 12.0f);
            attacker.damage(selfdmg, 8.0f);
        }

        return super.postHit(stack, target, attacker);
    }
}
