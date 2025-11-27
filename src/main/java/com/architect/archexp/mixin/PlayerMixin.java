package com.architect.archexp.mixin;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.effect.ModEffects;
import com.architect.archexp.effect.ModEffects;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.util.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private final LivingEntity player = this;

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public void onHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        //TheArchitectExperiment.LOGGER.info("mixin");
        if (player instanceof PlayerEntity curPlayer) {
            if (curPlayer.getHealth() - amount < 11) {
                if (!curPlayer.getWorld().isClient) {
                    CommandManager manager = curPlayer.getServer().getCommandManager();
                    //TheArchitectExperiment.LOGGER.info("particle");
                    manager.executeWithPrefix(curPlayer.getServer().getCommandSource(), "particle dust{color:[255.0,0.0,0.0],scale:1} " +
                            curPlayer.getX() + " " + (curPlayer.getY() + 0.800) + " " + curPlayer.getZ() + " 0.3 0.8 0.3 0.1 15 force");
                }
            }

            if (curPlayer.getInventory().contains(ModTags.Items.SOUL)) {
                int slot = curPlayer.getInventory().getSlotWithStack(ModItems.SOUL_AMULET.getDefaultStack());
                ItemStack soulItem = curPlayer.getInventory().getStack(slot);

                if (soulItem.get(ModComponents.SOUL_AMULET_ACTIVE).equals(true)) {
                    //TheArchitectExperiment.LOGGER.info("is active in mixin");
                    TheArchitectExperiment.removeSoulEffects(curPlayer, soulItem);

                    curPlayer.getItemCooldownManager().set(soulItem.getItem(), 360); //45-second cooldown
                }
            } else if (curPlayer.hasStatusEffect(ModEffects.SOUL_PHASED)) {
                TheArchitectExperiment.removeSoulEffects(curPlayer);
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "RETURN"))
    public void checkHealth(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof PlayerEntity curPlayer  && curPlayer.getHealth() - amount < 11) {
            if (!curPlayer.getWorld().isClient) {
                CommandManager manager = curPlayer.getServer().getCommandManager();
                //TheArchitectExperiment.LOGGER.info("particle");
                manager.executeWithPrefix(curPlayer.getServer().getCommandSource(), "particle dust{color:[255.0,0.0,0.0],scale:1} " +
                        curPlayer.getX() + " " + (curPlayer.getY() + 0.800) + " " + curPlayer.getZ() + " 0.3 0.8 0.3 0.1 15 force");
            }
        }
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    public void dropItemMixin(ItemStack item, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> ci) {
        //TheArchitectExperiment.LOGGER.debug("drop item mixin");
        if (item.getItem().equals(ModItems.SOUL_AMULET)) {
            TheArchitectExperiment.removeSoulEffects(player, item);
            PlayerEntity user = (PlayerEntity) player;

            user.getItemCooldownManager().set(item.getItem(), 360);
        } else if (item.getItem().equals(ModItems.HEALTH_AMULET)) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
        }
    }
}