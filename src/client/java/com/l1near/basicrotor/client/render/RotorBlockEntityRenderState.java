package com.l1near.basicrotor.client.render;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.core.Direction;
import com.l1near.basicrotor.client.assembly.VirtualAssemblyData;
import java.util.ArrayList;
import java.util.List;


public class RotorBlockEntityRenderState extends BlockEntityRenderState {

    /*
    ---------------------------
               Fields
    ---------------------------
    */

    public final BlockModelRenderState modelState = new BlockModelRenderState();

    public Direction facing = Direction.NORTH;
    public float rotation;

    public VirtualAssemblyData virtualAssembly;
    public final List<VirtualBlockRenderState> virtualBlocks =
            new ArrayList<>();


}