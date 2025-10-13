package com.architect.archexp.block.entity;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.block.ModBlocks;
import com.architect.archexp.block.entity.custom.MarketBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<MarketBlockEntity> MARKET_ENTITY = register("market_entity", MarketBlockEntity::new, ModBlocks.MARKETPLACE_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.of(TheArchitectExperiment.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }

    public static void registerBlockEntities() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you systems.");
    }
}
