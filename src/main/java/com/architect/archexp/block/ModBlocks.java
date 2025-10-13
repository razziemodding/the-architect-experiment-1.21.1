package com.architect.archexp.block;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.block.custom.MarketBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block MARKETPLACE_BLOCK = registerBlock("marketplace", new MarketBlock(AbstractBlock.Settings.create()), true);


    public static Block registerBlock(String name, Block block, boolean makeItem) {
        if (makeItem) {
            registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, Identifier.of(TheArchitectExperiment.MOD_ID, name), block);
    }

    public static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(TheArchitectExperiment.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you structure.");
    }
}
