package com.architect.archexp.item;

import com.architect.archexp.TheArchitectExperiment;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static ComponentType<Integer> ITEM_DELAY = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of(TheArchitectExperiment.MOD_ID, "item_delay"),
            ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static ComponentType<Boolean> WAIT_FOR_DELAY = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of(TheArchitectExperiment.MOD_ID, "wait_for_delay"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build());
    public static ComponentType<Boolean> SOUL_AMULET_ACTIVE = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of(TheArchitectExperiment.MOD_ID, "soul_amulet_active"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build());

    public static void registerComponents() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you intricacy.");
    }
}
