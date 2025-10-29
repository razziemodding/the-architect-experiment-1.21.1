package com.architect.archexp;

import com.architect.archexp.block.ModBlocks;
import com.architect.archexp.recipe.ModRecipes;
import com.architect.archexp.screen.ModScreens;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.sound.ModSounds;
import com.architect.archexp.util.ModTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class TheArchitectExperiment implements ModInitializer {
	public static final String MOD_ID = "archexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Map<ItemStack, Integer> MarketMap;
	public static Map<ItemStack, Integer> MarketCost;

	@Override
	public void onInitialize() {
		/*
		todo: uhhh idk put stuff here when you think of it and just say it was from you
			make building item akin to health amulet


		todo: weapons -
			skeletal scale -
		 */

		MarketMap = Map.of(
				new ItemStack(Items.COBWEB), 20,
				new ItemStack(Items.GLASS_BOTTLE), 64,
				new ItemStack(Items.APPLE), 5
		);
		MarketCost = Map.of(
				new ItemStack(Items.STRING), 1,
				new ItemStack(Items.GLASS_BOTTLE), 3,
				new ItemStack(Items.APPLE), 1
		);
		LOGGER.info(MarketMap.toString());
		LOGGER.info(MarketCost.toString());


		ModItems.registerModItems();
		ModSounds.registerModSounds();
		ModComponents.registerComponents();
		ModBlocks.registerModBlocks();
		ModScreens.registerScreenHandlers();
		ModRecipes.registerRecipes();


	}


}