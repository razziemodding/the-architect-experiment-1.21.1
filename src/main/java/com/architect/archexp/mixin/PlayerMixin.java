package com.architect.archexp.mixin;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.util.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow public abstract PlayerInventory getInventory();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //todo: make it so when a player respawns, they cannot see soul amulet users' armor
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public void onHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = this;
        //TheArchitectExperiment.LOGGER.info("mixin");
        if (target instanceof PlayerEntity player) {
            int slot = -1;
            boolean has = false;

            //TheArchitectExperiment.LOGGER.info("player");
            if (!player.getInventory().contains(ModTags.Items.SOUL)) {
                super.damage(source, amount);
                return;
            }

            for (int s = 0; 0 <= player.getInventory().size(); s++) {
                ItemStack stack = player.getInventory().getStack(s);
                if (!stack.isEmpty() && stack.getItem().equals(ModItems.SOUL_AMULET)) {
                    slot = s;
                    has = true;
                    break;
                }
            }

            //TheArchitectExperiment.LOGGER.info("has item");
            ItemStack handItem = player.getInventory().getStack(slot);
            //TheArchitectExperiment.LOGGER.info("post set");
            if (handItem.get(ModComponents.SOUL_AMULET_ACTIVE).equals(true) && has) {
                //TheArchitectExperiment.LOGGER.info("is active in mixin");
                target.removeStatusEffect(StatusEffects.INVISIBILITY);
                target.removeStatusEffect(StatusEffects.SPEED);
                target.removeStatusEffect(StatusEffects.REGENERATION);
                handItem.set(ModComponents.SOUL_AMULET_ACTIVE, false);
                player.getItemCooldownManager().set(handItem.getItem(), 360); //45 second cooldown
            }
        }

    }
}
