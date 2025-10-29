package com.architect.archexp.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record MarketRecipeInput(ItemStack input, ItemStack currency) implements RecipeInput {
    public MarketRecipeInput(ItemStack input, ItemStack currency) {
        this.input = input;
        this.currency = currency;
    }


    @Override
    public ItemStack getStackInSlot(int slot) {
        ItemStack stackQuery = switch (slot) {
            case 0 -> input;
            case 1 -> currency;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };

        return stackQuery;
    }

    @Override
    public int getSize() {
        return 2;
    }
}
