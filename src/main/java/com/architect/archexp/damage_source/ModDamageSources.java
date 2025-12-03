package com.architect.archexp.damage_source;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageSources {

    public static final RegistryKey<DamageType> EXECUTIONER_SELF =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(TheArchitectExperiment.MOD_ID, "executioner_self"));

    public static final RegistryKey<DamageType> EXECUTIONER_TARGETED =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(TheArchitectExperiment.MOD_ID, "executioner_targeted"));

    public static final RegistryKey<DamageType> KARMIC_SIN =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(TheArchitectExperiment.MOD_ID, "karmic_sin"));
}
