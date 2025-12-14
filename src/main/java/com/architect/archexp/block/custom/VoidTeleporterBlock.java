package com.architect.archexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidTeleporterBlock extends Block {
    public VoidTeleporterBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient) {
            CommandManager manager = world.getServer().getCommandManager();

            if (!world.getRegistryKey().getValue().getNamespace().equals("archexp")) {
                manager.executeWithPrefix(world.getServer().getCommandSource(),
                        "execute in archexp:void run teleport " +
                                entity.getName().getString() + " 120.5 137 488.5");
            } else {
                manager.executeWithPrefix(world.getServer().getCommandSource(),
                        "execute in minecraft:overworld run teleport " +
                                entity.getName().getString() + " " +
                                world.getSpawnPos().getX() + " " + world.getSpawnPos().getY() + " " + world.getSpawnPos().getZ());
            }
        }
    }
}
