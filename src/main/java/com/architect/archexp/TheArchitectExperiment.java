package com.architect.archexp;

import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheArchitectExperiment implements ModInitializer {
	public static final String MOD_ID = "archexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		/*
		todo: uhhh idk put stuff here when you think of it and just say it was from you
		 */
		ModItems.registerModItems();
		ModSounds.registerModSounds();
		ModComponents.registerComponents();
	}
}