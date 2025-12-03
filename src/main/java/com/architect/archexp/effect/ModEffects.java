package com.architect.archexp.effect;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.effect.custom.GuiltEffect;
import com.architect.archexp.effect.custom.SoulPhasedEffect;
import com.architect.archexp.effect.custom.VoidTouchedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> VOID_TOUCHED = registerStatusEffect("void_touched",
            new VoidTouchedEffect(StatusEffectCategory.NEUTRAL, 0x301934));

    public static final RegistryEntry<StatusEffect> SOUL_PHASED = registerStatusEffect("soul_phased",
            new SoulPhasedEffect(StatusEffectCategory.BENEFICIAL, 0x83DEDA));

    public static final RegistryEntry<StatusEffect> GUILT = registerStatusEffect("guilt",
            new GuiltEffect(StatusEffectCategory.HARMFUL, 0x484343));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(TheArchitectExperiment.MOD_ID, name), effect);
    }

    public static void registerModEffects() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you ailments.");
    }
}
