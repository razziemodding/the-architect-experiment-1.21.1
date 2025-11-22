package com.architect.archexp.block.custom;

import com.architect.archexp.screen.custom.MarketScreenHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MarketBlock extends Block {
    public static final MapCodec<MarketBlock> CODEC = MarketBlock.createCodec(MarketBlock::new);
    private static final Text TITLE = Text.translatable("container.archexp.market");

    public MarketBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<MarketBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerEntity) ->
                    new MarketScreenHandler(syncId, playerInventory), TITLE));
        }
        return ActionResult.SUCCESS;
    }
}
