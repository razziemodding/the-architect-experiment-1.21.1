package com.architect.archexp.item.custom;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
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
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;

            if (!currentDimension.getNamespace().equals("archexp")) {
                manager.executeWithPrefix(user.getCommandSource(), "execute in archexp:void run teleport " + user.getName().getString() + " 0 6 0");
                user.getItemCooldownManager().set(this, 120); //15sec

                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (serverPlayer.getSpawnPointPosition() != null) {
                manager.executeWithPrefix(user.getCommandSource(), "execute in minecraft:overworld run teleport " + user.getName().getString() +
                        " " + serverPlayer.getSpawnPointPosition().getX() + " " + serverPlayer.getSpawnPointPosition().getY() + " " + serverPlayer.getSpawnPointPosition().getZ());
                user.getItemCooldownManager().set(this, 120); //15sec

                return TypedActionResult.success(user.getStackInHand(hand));
            } else {
                manager.executeWithPrefix(user.getCommandSource(), "execute in minecraft:overworld run teleport " + user.getName().getString() +
                        " " + serverPlayer.getWorld().getSpawnPos().getX() + " " + serverPlayer.getWorld().getSpawnPos().getY() + " " + serverPlayer.getWorld().getSpawnPos().getZ());
                user.getItemCooldownManager().set(this, 120); //15sec

                return TypedActionResult.success(user.getStackInHand(hand));
            }

        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) { //todo: store previous location and create effect that teleports them back
        Identifier currentDimension = user.getWorld().getRegistryKey().getValue();

        if (!user.getWorld().isClient) {
            CommandManager manager = user.getServer().getCommandManager();
            TheArchitectExperiment.LOGGER.info("pre-check: " + entity.toString());
            if (entity instanceof ServerPlayerEntity targetPlayer && user.isSneaking() ) {
                TheArchitectExperiment.LOGGER.info("found player");
                if (!currentDimension.getNamespace().equals("archexp")) {
                    manager.executeWithPrefix(user.getCommandSource(), "execute in archexp:void run teleport " + entity.getName().getString() + " 0 6 0");
                    user.getItemCooldownManager().set(this, 120); //15 sec

                    return ActionResult.SUCCESS;
                } else if (targetPlayer.getSpawnPointPosition() != null) {
                    manager.executeWithPrefix(user.getCommandSource(), "execute in minecraft:overworld run teleport " + entity.getName().getString() +
                            " " + targetPlayer.getSpawnPointPosition().getX() + " " + targetPlayer.getSpawnPointPosition().getY() + " " + targetPlayer.getSpawnPointPosition().getZ());
                    user.getItemCooldownManager().set(this, 120); //15sec

                    return ActionResult.SUCCESS;
                } else {
                    manager.executeWithPrefix(user.getCommandSource(), "execute in minecraft:overworld run teleport " + entity.getName().getString() +
                            " " + targetPlayer.getWorld().getSpawnPos().getX() + " " + targetPlayer.getWorld().getSpawnPos().getY() + " " + targetPlayer.getWorld().getSpawnPos().getZ());
                    user.getItemCooldownManager().set(this, 120); //15sec

                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }
}