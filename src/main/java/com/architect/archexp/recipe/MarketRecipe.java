package com.architect.archexp.recipe;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.util.ModTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;

public record MarketRecipe(Ingredient input, Ingredient currency, ItemStack result) implements Recipe<MarketRecipeInput> {

    @Override
    public boolean matches(MarketRecipeInput recipeInput, World world) {
        if (world.isClient) return false;

        //return input.test(input.getStackInSlot(0)) && currency.test(input.getStackInSlot(1));
        return input.test(recipeInput.getStackInSlot(0)) && currency.test(recipeInput.getStackInSlot(1));
    }

    @Override
    public ItemStack craft(MarketRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result.copy();
    }



    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MARKET_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MARKET_RECIPE_TYPE;
    }

    public int getInputCost(ItemStack input) {
        int cost = 0;
        if (!input.isEmpty()) {
            if (TheArchitectExperiment.MarketMap.containsKey(input.getItem()))
                cost = TheArchitectExperiment.MarketMap.get(input.getItem());
            else if (input.isIn(ModTags.Items.MARKET_FIRST_INPUT_VALID_EXTR))
                cost = 1;
            else if (input.isIn(ModTags.Items.CROPS))
                cost = 16;

        }

        return cost;
    }

    public int getCurrencyCost(ItemStack input) {
        int cost = 0;
        if (!input.isEmpty()) {
            if (TheArchitectExperiment.MarketCost.containsKey(input.getItem()))
                cost = TheArchitectExperiment.MarketCost.get(input.getItem());
            else if (input.isIn(ModTags.Items.MARKET_FIRST_INPUT_VALID_EXTR)) {
                if (input.isIn(ItemTags.ARMOR_ENCHANTABLE)) cost = 15;
                else cost = 10;
            } else if (input.isIn(ModTags.Items.CROPS)) cost = 1;
        }

        return cost;
    }

    //

    public static class Serializer implements RecipeSerializer<MarketRecipe> {
        public static final MapCodec<MarketRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("input").forGetter(MarketRecipe::input),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("currency").forGetter(MarketRecipe::currency),
                ItemStack.CODEC.fieldOf("result").forGetter(MarketRecipe::result)).apply(inst, MarketRecipe::new));

        public static final PacketCodec<RegistryByteBuf, MarketRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, MarketRecipe::input,
                        Ingredient.PACKET_CODEC, MarketRecipe::currency,
                        ItemStack.PACKET_CODEC, MarketRecipe::result,
                        MarketRecipe::new);

        @Override
        public MapCodec<MarketRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MarketRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
