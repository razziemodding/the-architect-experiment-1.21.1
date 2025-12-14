package com.architect.archexp.mixin;

import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.util.ModTags;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> { //todo:see if i can redo this so its whenevever another player renders a player with soul amulet that they then go invis

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    public void noSoulRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player && player.getInventory().contains(ModTags.Items.SOUL)) {
            ItemStack stack = ItemStack.EMPTY;
            for (int s = 0; 0 <= player.getInventory().size(); s++) {
                stack = player.getInventory().getStack(s);
                if (!stack.isEmpty() && stack.getItem().equals(ModItems.SOUL_AMULET)) {
                    break;
                }
            }

            if (!stack.isEmpty() && stack.get(ModComponents.SOUL_AMULET_ACTIVE).equals(true)) {
                ci.cancel();
            }
        }
    }
}
