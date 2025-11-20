package com.architect.archexp.mixin;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.damage_source.ModDamageSources;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.util.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private final LivingEntity player = this;

    //todo: make it so when a player respawns, they cannot see soul amulet users' armor
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public void onHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        //TheArchitectExperiment.LOGGER.info("mixin");
        if (player instanceof PlayerEntity curPlayer) {
            int slot = -1;
            boolean has = false;

            //TheArchitectExperiment.LOGGER.info("curPlayer");
            if (!curPlayer.getInventory().contains(ModTags.Items.SOUL)) {
                return;
            }

            for (int s = 0; 0 <= curPlayer.getInventory().size(); s++) {
                ItemStack stack = curPlayer.getInventory().getStack(s);
                if (!stack.isEmpty() && stack.getItem().equals(ModItems.SOUL_AMULET)) {
                    slot = s;
                    has = true;
                    break;
                }
            }

            //TheArchitectExperiment.LOGGER.info("has item");
            ItemStack handItem = curPlayer.getInventory().getStack(slot);
            //TheArchitectExperiment.LOGGER.info("post set");
            if (has && handItem.get(ModComponents.SOUL_AMULET_ACTIVE).equals(true)) {
                //TheArchitectExperiment.LOGGER.info("is active in mixin");
                TheArchitectExperiment.removeSoulEffects(curPlayer, handItem);


                curPlayer.getItemCooldownManager().set(handItem.getItem(), 360); //45 second cooldown
            }
            //TheArchitectExperiment.LOGGER.info(handItem.get(ModComponents.SOUL_AMULET_ACTIVE).toString());
        }
    }


    @Inject(method = "attack", at = @At("TAIL"))
    public void useDmgAxe(Entity target, CallbackInfo ci) {
        //TheArchitectExperiment.LOGGER.debug("die0");
        if (target instanceof LivingEntity) {
            DamageSource source = new DamageSource(this.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.SELF_DAMAGE_AXE_DAMAGE));
            if (player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(ModItems.SELF_DAMAGE_AXE)) {
                //TheArchitectExperiment.LOGGER.debug("axe detected");
                player.damage(source, 8f);
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
    @Override
    public @Nullable ItemEntity dropItem(ItemConvertible item) {
        return super.dropItem(item);
    }
}