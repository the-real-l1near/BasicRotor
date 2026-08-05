package com.l1near.basicrotor.block;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.blockentity.RotorBlockEntity;
import com.l1near.basicrotor.registry.ModBlockEntities;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RotorBlock extends BaseEntityBlock {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final MapCodec<RotorBlock> CODEC = simpleCodec(RotorBlock::new);

    public RotorBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }
    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.FACING;

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RotorBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        return type == ModBlockEntities.ROTOR
                ? (lvl, pos, st, be) -> ((RotorBlockEntity) be).tick()
                : null;
    }
}