package com.architect.archexp;

import com.architect.archexp.block.ModBlocks;
import com.architect.archexp.effect.ModEffects;
import com.architect.archexp.recipe.ModRecipes;
import com.architect.archexp.screen.ModScreens;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TheArchitectExperiment implements ModInitializer {
	public static final String MOD_ID = "archexp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static Map<Item, Integer> MarketMap;
	public static Map<Item, Integer> MarketCost;

	/*
      todo:
      	weapons -
      		skeletal scale
        other items -
            potential building tool
        make kai a cool item
    */
	@Override
	public void onInitialize() {
		//TheArchitectExperiment.LOGGER.debug("debyug on");

		MarketMap = Map.of(
				Items.STRING, 20,
				Items.GLASS_BOTTLE, 32,
				Items.APPLE, 5,
				ModItems.STEAM_HAPPY, 1
		);
		MarketCost = Map.of(
				Items.STRING, 5,
				Items.GLASS_BOTTLE, 3,
				Items.APPLE, 1,
				ModItems.STEAM_HAPPY, 1
		);
		//LOGGER.info(MarketMap.toString());
		//LOGGER.info(MarketCost.toString());


		ModItems.registerModItems();
		ModSounds.registerModSounds();
		ModComponents.registerComponents();
		ModBlocks.registerModBlocks();
		ModScreens.registerScreenHandlers();
		ModRecipes.registerRecipes();
		ModEffects.registerModEffects();
	}


	public static void removeSoulEffects(LivingEntity entity, ItemStack amulet) {
		entity.removeStatusEffect(StatusEffects.INVISIBILITY);
		entity.removeStatusEffect(StatusEffects.SPEED);
		entity.removeStatusEffect(StatusEffects.REGENERATION);
		entity.removeStatusEffect(ModEffects.SOUL_PHASED);

		if (!amulet.get(ModComponents.SOUL_AMULET_PLR_SPEED_LENGTH).equals(0) || !amulet.get(ModComponents.SOUL_AMULET_PLR_SPEED_AMP).equals(0)) {
			//TheArchitectExperiment.LOGGER.info("has speed effect");
			int dur = amulet.get(ModComponents.SOUL_AMULET_PLR_SPEED_LENGTH);
			int amp = amulet.get(ModComponents.SOUL_AMULET_PLR_SPEED_AMP);
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, dur, amp));
			amulet.set(ModComponents.SOUL_AMULET_PLR_SPEED_LENGTH, 0);
			amulet.set(ModComponents.SOUL_AMULET_PLR_SPEED_AMP, 0);
		}

		amulet.set(ModComponents.SOUL_AMULET_ACTIVE, false);
	}

	public static void removeSoulEffects(LivingEntity entity) {
		entity.removeStatusEffect(StatusEffects.INVISIBILITY);
		entity.removeStatusEffect(StatusEffects.SPEED);
		entity.removeStatusEffect(StatusEffects.REGENERATION);
		entity.removeStatusEffect(ModEffects.SOUL_PHASED);
	}

	public static void clearSoulComponents(ItemStack amulet) {
		amulet.set(ModComponents.SOUL_AMULET_ACTIVE, false);
		amulet.set(ModComponents.SOUL_AMULET_PLR, "");
		amulet.set(ModComponents.SOUL_AMULET_PLR_SPEED_LENGTH, 0);
		amulet.set(ModComponents.SOUL_AMULET_PLR_SPEED_AMP, 0);
	}

}