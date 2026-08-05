package com.l1near.basicrotor.client.render;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.core.Direction;


public class RotorBlockEntityRenderState extends BlockEntityRenderState {

    /*
    ---------------------------
               Fields
    ---------------------------
    */
    public final BlockModelRenderState modelState = new BlockModelRenderState();

    public Direction facing = Direction.NORTH;
    public float rotation;


}