package com.architect.archexp.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class PlayerListMixin {

    @Mutable
    @Shadow @Final private PlayerListHud playerListHud;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void cancelScoreboardDisplay(MinecraftClient client, CallbackInfo ci) {
        this.playerListHud = null;
    }

    @Inject(method = "renderPlayerList", at = @At(value = "HEAD"), cancellable = true)
    public void renderPlayerListMixin(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ci.cancel();
    }
}
