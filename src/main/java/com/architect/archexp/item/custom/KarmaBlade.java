package com.architect.archexp.item.custom;

import com.architect.archexp.damage_source.ModDamageSources;
import com.architect.archexp.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKeys;

public class KarmaBlade extends SwordItem {
    public KarmaBlade(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof PlayerEntity player && !player.hasStatusEffect(ModEffects.GUILT)) {
            DamageSource damage = new DamageSource(attacker.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.KARMIC_SIN));
            //TheArchitectExperiment.LOGGER.info("nop gulkt");
            target.damage(damage, 4);
        }

        return super.postHit(stack, target, attacker);
    }
}
