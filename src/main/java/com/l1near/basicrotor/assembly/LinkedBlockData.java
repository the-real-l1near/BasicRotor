package com.l1near.basicrotor.assembly;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.world.level.block.state.BlockState;

public class LinkedBlockData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private BlockState blockState;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public LinkedBlockData(BlockState blockState) {
        this.blockState = blockState;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public BlockState getBlockState() {
        return blockState;
    }

    //Setters
    public void setBlockState(
            BlockState blockState
    ) {
        this.blockState = blockState;
    }
}