package com.architect.archexp.mixin;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.damage_source.ModDamageSources;
import com.architect.archexp.effect.ModEffects;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.util.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
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

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private final LivingEntity player = this;

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public void onHit(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof PlayerEntity curPlayer) { //soul invis remover if damage
            if (curPlayer.getInventory().contains(ModTags.Items.SOUL)) { //checks if they have soul
                int slot = 0;
                for (int s = 0; 0 <= curPlayer.getInventory().size(); s++) {
                    ItemStack stack = curPlayer.getInventory().getStack(s);
                    if (!stack.isEmpty() && stack.getItem().equals(ModItems.SOUL_AMULET)) {
                        slot = s;
                        break;
                    }
                }
                ItemStack soulItem = curPlayer.getInventory().getStack(slot);

                if (soulItem.get(ModComponents.SOUL_AMULET_ACTIVE).equals(true)) { //if it is active, make it not
                    TheArchitectExperiment.removeSoulEffects(curPlayer, soulItem);

                    curPlayer.getItemCooldownManager().set(soulItem.getItem(), 360); //45-second cooldown
                }
            } else if (curPlayer.hasStatusEffect(ModEffects.SOUL_PHASED)) { //if they have the effect
                TheArchitectExperiment.removeSoulEffects(curPlayer);
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "TAIL"))
    public void checkHealth(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof PlayerEntity curPlayer && curPlayer.getHealth() < 11) {
            if (!curPlayer.getWorld().isClient) { //particle spawner
                CommandManager manager = curPlayer.getServer().getCommandManager();
                manager.executeWithPrefix(curPlayer.getServer().getCommandSource(), "particle dust{color:[255.0,0.0,0.0],scale:1} " +
                        curPlayer.getX() + " " + (curPlayer.getY() + 0.800) + " " + curPlayer.getZ() + " 0.3 0.8 0.3 0.1 15 force");
            }
        }
    }

    @Inject(method = "attack", at = @At(value = "HEAD"))
    public void attackWithExecutioner(Entity target, CallbackInfo ci) {
        if (target.isAttackable()) {
            if (!target.handleAttack(this)) { //not attacking self?
                if (this.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(ModItems.SELF_DAMAGE_AXE)) { //have axe?
                    //ItemStack axe = this.getEquippedStack(EquipmentSlot.MAINHAND);
                    DamageSource targeteddmg = new DamageSource(this.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.EXECUTIONER_TARGETED));
                    DamageSource selfdmg = new DamageSource(this.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageSources.EXECUTIONER_SELF));

                    if (this.getAttackCooldownProgress(1) == 1.0) { //ready to attack?
                        target.damage(targeteddmg, 12.0f);
                        this.damage(selfdmg, 8.0f);
                    } else { //not ready to attack
                        target.damage(targeteddmg, 1.0f);
                        this.damage(selfdmg, 1.0f);
                    }
                }
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

    @Inject(method = "onKilledOther", at = @At("HEAD"))
    public void onKilledOtherMixin(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (other instanceof PlayerEntity othPlr) {
            if (!othPlr.hasStatusEffect(ModEffects.GUILT)) { //doesnt have guilt? increase damage dealt
                if (player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(ModItems.KARMA_BLADE)) {
                    double curHealth = player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
                    player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(curHealth - 2);
                }
                player.addStatusEffect(new StatusEffectInstance(ModEffects.GUILT, 86400, 0, true, false));
            } else if (othPlr.hasStatusEffect(ModEffects.GUILT)) { //has guilt
                othPlr.removeStatusEffect(ModEffects.GUILT);
                if (player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(ModItems.KARMA_BLADE)) {
                    double curHealth = player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
                    player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(curHealth + 2);
                }
            }
        }
    }
}