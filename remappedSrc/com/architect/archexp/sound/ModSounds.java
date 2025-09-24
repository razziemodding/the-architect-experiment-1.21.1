package com.architect.archexp.sound;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    private ModSounds() {

    }

    public static final SoundEvent MIGHT_AMULET_USE = registerSound("might_amulet_use");


    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(TheArchitectExperiment.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void registerModSounds() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you harmony.");
    }
}
