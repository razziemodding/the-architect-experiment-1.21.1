package com.architect.archexp.screen;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.screen.custom.MarketScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreens {
    public static final ScreenHandlerType<MarketScreenHandler> MARKETPLACE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(TheArchitectExperiment.MOD_ID, "marketplace_screen_handler"),
                    new ExtendedScreenHandlerType<>(MarketScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you visions.");
    }
}
