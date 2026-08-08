package com.l1near.basicrotor.client.render;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.core.BlockPos;

public class VirtualBlockRenderState {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public final BlockPos relativePos;
    public final BlockModelRenderState modelState;

    public int lightCoords;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public VirtualBlockRenderState(BlockPos relativePos) {

        this.relativePos = relativePos;
        this.modelState = new BlockModelRenderState();
    }
}