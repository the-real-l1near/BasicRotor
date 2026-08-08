package com.l1near.basicrotor.assembly.persistence;

/*
---------------------------
Imports
---------------------------
*/

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SavedBlockData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Codec<SavedBlockData> CODEC =
            RecordCodecBuilder.create(
                    instance ->
                            instance.group(
                                    BlockPos.CODEC
                                            .fieldOf("relative_pos")
                                            .forGetter(
                                                    SavedBlockData::getRelativePos
                                            ),
                                    BlockState.CODEC
                                            .fieldOf("block_state")
                                            .forGetter(
                                                    SavedBlockData::getBlockState
                                            )
                            ).apply(
                                    instance,
                                    SavedBlockData::new
                            )
            );

    private final BlockPos relativePos;

    private final BlockState blockState;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public SavedBlockData(
            BlockPos relativePos,
            BlockState blockState
    ) {
        this.relativePos = relativePos;
        this.blockState = blockState;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public BlockPos getRelativePos() {
        return relativePos;
    }

    public BlockState getBlockState() {
        return blockState;
    }
}