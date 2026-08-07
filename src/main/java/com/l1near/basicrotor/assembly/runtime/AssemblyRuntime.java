package com.l1near.basicrotor.assembly.runtime;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.assembly.LinkedBlockData;
import com.l1near.basicrotor.movement.MovementData;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class AssemblyRuntime {
    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private boolean virtualized;
    private boolean lastVirtualized;


    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public AssemblyRuntime() {
        this.virtualized = false;
        this.lastVirtualized = false;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Update
    public void update(
            Level level,
            LinkedAssembly assembly,
            MovementData movementData
    ) {
        boolean wasVirtualized = virtualized;

        switch (movementData.getState()) {

            case STOPPED ->
                    updateStopped();

            case STARTING ->
                    updateStarting();

            case RUNNING ->
                    updateRunning();

            case BRAKING ->
                    updateBraking();

            case RETURNING ->
                    updateReturning();
        }

        if (!wasVirtualized && virtualized) {
            virtualizeBlocks(
                    level,
                    assembly
            );
        }

        if (wasVirtualized && !virtualized) {
            restoreBlocks(
                    level,
                    assembly
            );
        }

        if (virtualized != lastVirtualized) {
            lastVirtualized = virtualized;
        }
    }


    private void updateStopped() {
        virtualized = false;
    }

    private void updateStarting() {
        virtualized = true;
    }

    private void updateRunning() {
        virtualized = true;
    }

    private void updateBraking() {
        virtualized = true;
    }

    private void updateReturning() {
        virtualized = true;
    }

    //Virtualize
    private void virtualizeBlocks(
            Level level,
            LinkedAssembly assembly
    ) {

        BlockPos originPos =
                assembly.getOriginPos();

        for (Map.Entry<BlockPos, LinkedBlockData> entry
                : assembly.getEntries()) {

            BlockPos relativePos =
                    entry.getKey();

            BlockPos worldPos =
                    originPos.offset(relativePos);

            level.setBlock(
                    worldPos,
                    Blocks.AIR.defaultBlockState(),
                    3
            );
        }
    }

    //Restore
    private void restoreBlocks(
            Level level,
            LinkedAssembly assembly
    ) {

        BlockPos originPos =
                assembly.getOriginPos();

        for (Map.Entry<BlockPos, LinkedBlockData> entry
                : assembly.getEntries()) {

            BlockPos relativePos =
                    entry.getKey();

            LinkedBlockData blockData =
                    entry.getValue();

            BlockPos worldPos =
                    originPos.offset(relativePos);

            level.setBlock(
                    worldPos,
                    blockData.getBlockState(),
                    3
            );
        }
    }

    //Getters
    public boolean isVirtualized() {
        return virtualized;
    }

}