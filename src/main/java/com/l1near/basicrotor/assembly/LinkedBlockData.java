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

    private final BlockState blockState;

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

}