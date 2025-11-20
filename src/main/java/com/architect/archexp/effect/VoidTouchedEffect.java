package com.architect.archexp.effect;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.BlockPos;

public class VoidTouchedEffect extends StatusEffect {

    private BlockPos playerReturnPos;
    private LivingEntity playerReturnEntity;

    protected VoidTouchedEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        this.playerReturnPos = entity.getBlockPos();
        this.playerReturnEntity = entity;
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
        CommandManager manager = playerReturnEntity.getServer().getCommandManager();
        TheArchitectExperiment.LOGGER.info("effect gone");
        manager.executeWithPrefix(playerReturnEntity.getServer().getCommandSource(), "execute in minecraft:overworld run teleport " + playerReturnEntity.getName().getString() +
                " " + playerReturnPos.getX() + " " + playerReturnPos.getY() + " " + playerReturnPos.getZ());
        TheArchitectExperiment.LOGGER.info(String.valueOf(playerReturnEntity.getBlockPos()));
    }
}
