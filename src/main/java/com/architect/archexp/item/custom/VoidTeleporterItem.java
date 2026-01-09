package com.architect.archexp.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
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
        if (!world.isClient) {
            CommandManager manager = user.getServer().getCommandManager();
            ServerCommandSource source = user.getServer().getCommandSource();

            if (!currentDimension.getNamespace().equals("archexp")) {
                manager.executeWithPrefix(source, "execute in archexp:void run teleport " + user.getName().getString() + " 0 6 0");

                return TypedActionResult.success(user.getStackInHand(hand));
            } else {
                manager.executeWithPrefix(source, "execute at " + user.getNameForScoreboard() + " run title " + user.getNameForScoreboard() +
                        " actionbar [{\"text\":\"Forbidden.\",\"bold\":true,\"color\":\"dark_red\"}]");
                user.getItemCooldownManager().set(this, 140); //7sec

                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }
}