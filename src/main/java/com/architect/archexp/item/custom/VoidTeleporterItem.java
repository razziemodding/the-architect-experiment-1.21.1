package com.architect.archexp.item.custom;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VoidTeleporterItem extends Item {
    public VoidTeleporterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Identifier currentDimension = user.getWorld().getRegistryKey().getValue();
        Identifier overworld = World.OVERWORLD.getValue();
        Identifier nether = World.NETHER.getValue();
        Identifier end = World.END.getValue();
        //ServerWorld voidD = (ServerWorld) TheArchitectExperiment.VOID;

        if (!currentDimension.getNamespace().equals("archexp")) {
           // user.teleport(world.getDimension()., 0, 6, 0)
        }
        return super.use(world, user, hand);
    }
}
