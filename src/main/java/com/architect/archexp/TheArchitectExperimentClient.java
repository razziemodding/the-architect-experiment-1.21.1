package com.architect.archexp;

import com.architect.archexp.screen.ModScreens;
import com.architect.archexp.screen.custom.MarketScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class TheArchitectExperimentClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreens.MARKETPLACE_SCREEN_HANDLER, MarketScreen::new);
    }
}
