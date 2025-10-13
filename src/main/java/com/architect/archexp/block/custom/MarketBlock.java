package com.architect.archexp.block.custom;

import com.architect.archexp.block.entity.custom.MarketBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MarketBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<MarketBlock> CODEC = MarketBlock.createCodec(MarketBlock::new);

    public MarketBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MarketBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof MarketBlockEntity marketBlockEntity) {
            if (!world.isClient) {
                player.openHandledScreen(marketBlockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }
}
