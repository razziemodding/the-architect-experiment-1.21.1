package com.architect.archexp.damage_source;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageSources {

    public static final RegistryKey<DamageType> SELF_DAMAGE_AXE_DAMAGE =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(TheArchitectExperiment.MOD_ID, "self_damage_axe"));
}
