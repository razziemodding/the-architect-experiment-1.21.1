package com.architect.archexp.mixin;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.util.ModTags;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class GameInitializedMixin {
    @Inject(at = @At("TAIL"), method = "init()V")
    private void init(CallbackInfo info) {
        for (int i = 1; i <= 18; i++) {
            TheArchitectExperiment.MarketMap.put(Ingredient.fromTag(ModTags.Items.MARKET_FIRST_INPUT_VALID_EXTR).getMatchingStacks()[i], 5);
            TheArchitectExperiment.MarketCost.put(Ingredient.fromTag(ModTags.Items.MARKET_FIRST_INPUT_VALID_EXTR).getMatchingStacks()[i], 5);
            TheArchitectExperiment.LOGGER.info(TheArchitectExperiment.MarketMap.toString());
            TheArchitectExperiment.LOGGER.info(TheArchitectExperiment.MarketCost.toString());
        }
    }
}
