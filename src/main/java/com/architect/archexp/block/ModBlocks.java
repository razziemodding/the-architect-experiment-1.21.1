package com.architect.archexp.block;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.block.custom.MarketBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    //public static final Block MARKETPLACE_BLOCK = registerBlock("marketplace", new MarketBlock(AbstractBlock.Settings.create().hardness(15f).strength(-1.0F, 3600000.0F)), true);

    public static final Block OMNI_VOID_BLOCK = registerBlock("omni_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(-1.0F, 3600000.0F)
                    .dropsNothing()), true);
    public static final Block VOID_BLOCK = registerBlock("void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(2.5F, 3.5F)
                    .requiresTool()
                    .allowsSpawning(Blocks::always)
            ), true
    );
    public static final Block GRAY_VOID_BLOCK = registerBlock("gray_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(2.5F, 3.5F)
                    .requiresTool()
                    .allowsSpawning(Blocks::always)
            ), true
    );
    public static final Block WHITE_VOID_BLOCK = registerBlock("white_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(4.0F, 6.0F)
                    .requiresTool()
                    .luminance(state -> 15)
            ), true
    );
    public static final Block BLUE_VOID_BLOCK = registerBlock("blue_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(4.0F, 6.0F)
                    .requiresTool()
                    .luminance(state -> 15)
            ), true
    );
    public static final Block ORANGE_VOID_BLOCK = registerBlock("orange_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(4.0F, 6.0F)
                    .requiresTool()
                    .luminance(state -> 15)
            ), true
    );
    public static final Block PURPLE_VOID_BLOCK = registerBlock("purple_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(4.0F, 6.0F)
                    .requiresTool()
                    .luminance(state -> 15)
            ), true
    );
    public static final Block RED_VOID_BLOCK = registerBlock("red_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(4.0F, 6.0F)
                    .requiresTool()
                    .luminance(state -> 15)
            ), true
    );
    public static final Block YELLOW_VOID_BLOCK = registerBlock("yellow_void_block",
            new Block(AbstractBlock.Settings
                    .create()
                    .strength(4.0F, 6.0F)
                    .requiresTool()
                    .luminance(state -> 15)
            ), true
    );
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
