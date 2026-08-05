package com.l1near.basicrotor.assembly;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class LinkedBlockData {

    /*
    ---------------------------
               Fields
    ---------------------------
    */

    private final BlockPos relativePos;

    private final BlockState blockState;

    /*
    ---------------------------
            Constructor
    ---------------------------
    */

    public LinkedBlockData(
            BlockPos relativePos,
            BlockState blockState
    ) {
        this.relativePos = relativePos;
        this.blockState = blockState;
    }

    /*
    ---------------------------
              Getters
    ---------------------------
    */

    public BlockPos getRelativePos() {
        return relativePos;
    }

    public BlockState getBlockState() {
        return blockState;
    }
}