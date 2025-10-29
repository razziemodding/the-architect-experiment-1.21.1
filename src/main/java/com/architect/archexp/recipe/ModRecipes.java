package com.architect.archexp.recipe;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<MarketRecipe> MARKET_RECIPE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(TheArchitectExperiment.MOD_ID, "market"), new MarketRecipe.Serializer());
    public static final RecipeType<MarketRecipe> MARKET_RECIPE_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(TheArchitectExperiment.MOD_ID, "market"), new RecipeType<MarketRecipe>() {
                @Override
                public String toString() {
                    return "market";
                }
            });


    public static void registerRecipes() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you compounds.");
    }
}
