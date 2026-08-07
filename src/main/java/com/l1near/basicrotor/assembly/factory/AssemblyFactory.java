package com.l1near.basicrotor.assembly.factory;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.assembly.LinkedBlockData;
import com.l1near.basicrotor.assembly.session.LinkSession;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class AssemblyFactory {

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Create
    public LinkedAssembly create(
            Level level,
            LinkSession session
    ) {
        BlockPos originPos =
                session.getSelectedRotorPos();

        LinkedAssembly assembly =
                new LinkedAssembly(originPos);
        for (BlockPos blockPos : session.getSelectedBlocks()) {

            BlockPos relativePos =
                    blockPos.subtract(originPos);

            LinkedBlockData blockData =
                    new LinkedBlockData(
                            level.getBlockState(blockPos)
                    );

            assembly.addBlock(
                    relativePos,
                    blockData
            );
        }
        return assembly;
    }

}