package com.architect.archexp.mixin;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.util.ModComponents;
import com.architect.archexp.item.ModItems;
import com.architect.archexp.util.ModTags;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin { //todo:see if i can redo this so its whenevever another player renders a player with soul amulet that they then go invis
    @Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
    private void hideArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                           int light, AbstractClientPlayerEntity player,
                           ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        //TheArchitectExperiment.LOGGER.info("render mixin");
        if (player.getInventory().contains(ModTags.Items.SOUL) ) {
            //TheArchitectExperiment.LOGGER.info("render item");
            ItemStack stack = null;
            for (int s = 0; 0 <= player.getInventory().size(); s++) {
                stack = player.getInventory().getStack(s);
                if (!stack.isEmpty() && stack.getItem().equals(ModItems.SOUL_AMULET)) {
                    break;
                }
            }
            if (Boolean.TRUE.equals(stack.get(ModComponents.SOUL_AMULET_ACTIVE))) {
                ci.cancel();

            }
        }
    }
}
