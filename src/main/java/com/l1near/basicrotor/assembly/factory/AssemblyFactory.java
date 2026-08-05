package com.l1near.basicrotor.assembly.factory;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import net.minecraft.core.BlockPos;

public class AssemblyFactory {

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Create
    public LinkedAssembly create(BlockPos originPos) {
        return new LinkedAssembly(originPos);
    }

}