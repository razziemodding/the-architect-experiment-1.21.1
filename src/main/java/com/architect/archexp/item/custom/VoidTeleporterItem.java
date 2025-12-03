package com.architect.archexp.item.custom;

import com.architect.archexp.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
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
            ServerCommandSource source = user.getServer().getCommandSource();

            if (!currentDimension.getNamespace().equals("archexp")) {
                manager.executeWithPrefix(source, "execute in archexp:void run teleport " + user.getName().getString() + " 0 6 0");

                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (serverPlayer.getSpawnPointPosition() != null) {
                manager.executeWithPrefix(source, "execute in minecraft:overworld run teleport " + user.getName().getString() +
                        " " + serverPlayer.getSpawnPointPosition().getX() + " " + serverPlayer.getSpawnPointPosition().getY() + " " + serverPlayer.getSpawnPointPosition().getZ());
                user.getItemCooldownManager().set(this, 140); //7sec

                return TypedActionResult.success(user.getStackInHand(hand));
            } else {
                manager.executeWithPrefix(source, "execute in minecraft:overworld run teleport " + user.getName().getString() +
                        " " + serverPlayer.getWorld().getSpawnPos().getX() + " " + serverPlayer.getWorld().getSpawnPos().getY() + " " + serverPlayer.getWorld().getSpawnPos().getZ());
                user.getItemCooldownManager().set(this, 140); //7sec

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
            ServerCommandSource source = user.getServer().getCommandSource();
            if (entity instanceof ServerPlayerEntity targetPlayer && user.isSneaking() ) {
                if (!currentDimension.getNamespace().equals("archexp")) { /// in void
                    entity.addStatusEffect(new StatusEffectInstance(ModEffects.VOID_TOUCHED, 1200, 0, false, true));
                    manager.executeWithPrefix(source, "execute in archexp:void run teleport " + entity.getName().getString() + " 0 6 0");
                    user.getItemCooldownManager().set(this, 40); //2 sec

                    return ActionResult.SUCCESS;
                } else if (targetPlayer.getSpawnPointPosition() != null) { /// not in void, no bed
                    manager.executeWithPrefix(source, "execute in minecraft:overworld run teleport " + entity.getName().getString() +
                            " " + targetPlayer.getSpawnPointPosition().getX() + " " + targetPlayer.getSpawnPointPosition().getY() + " " + targetPlayer.getSpawnPointPosition().getZ());
                    user.getItemCooldownManager().set(this, 140); //7sec

                    return ActionResult.SUCCESS;
                } else { // bed
                    manager.executeWithPrefix(source, "execute in minecraft:overworld run teleport " + entity.getName().getString() +
                            " " + targetPlayer.getWorld().getSpawnPos().getX() + " " + targetPlayer.getWorld().getSpawnPos().getY() + " " + targetPlayer.getWorld().getSpawnPos().getZ());
                    user.getItemCooldownManager().set(this, 140); //7sec

                    return ActionResult.SUCCESS;
                }
            } else if (entity instanceof LivingEntity targetMob && user.isSneaking() ) { /// send someone else to void
                if (!currentDimension.getNamespace().equals("archexp")) {
                    manager.executeWithPrefix(source, "execute in archexp:void run teleport " + targetMob.getUuidAsString() + " 0 6 0");
                } else {
                    manager.executeWithPrefix(source, "execute in minecraft:overworld run teleport " + targetMob.getUuidAsString() +
                            " " + targetMob.getWorld().getSpawnPos().getX() + " " + targetMob.getWorld().getSpawnPos().getY() + " " + targetMob.getWorld().getSpawnPos().getZ());
                }
                return ActionResult.SUCCESS;
            } else return ActionResult.FAIL;
        } return super.useOnEntity(stack, user, entity, hand);
    }
}