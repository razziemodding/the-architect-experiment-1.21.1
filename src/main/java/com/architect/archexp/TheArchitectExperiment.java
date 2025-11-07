package com.architect.archexp;

import com.architect.archexp.block.ModBlocks;
import com.architect.archexp.recipe.ModRecipes;
import com.architect.archexp.screen.ModScreens;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TheArchitectExperiment implements ModInitializer {
	public static final String MOD_ID = "archexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Map<Item, Integer> MarketMap;
	public static Map<Item, Integer> MarketCost;

	@Override
	public void onInitialize() {
		/*
		todo: uhhh idk put stuff here when you think of it and just say it was from you
			make building item akin to health amulet
	finsih,. amarektpalce. DONE!!


		todo: weapons -
			skeletal scale -
		 */

		MarketMap = Map.of(
				Items.STRING, 20,
				Items.GLASS_BOTTLE, 64,
				Items.APPLE, 5
		);
		MarketCost = Map.of(
				Items.STRING, 1,
				Items.GLASS_BOTTLE, 3,
				Items.APPLE, 1
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