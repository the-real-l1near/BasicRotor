package com.l1near.basicrotor.assembly.runtime;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.movement.MovementData;

public class AssemblyRuntime {

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Update
    public void update(
            LinkedAssembly assembly,
            MovementData movementData
    ) {

        switch (movementData.getState()) {

            case STOPPED -> updateStopped(assembly);

            case STARTING -> updateStarting(
                    assembly,
                    movementData
            );

            case RUNNING -> updateRunning(
                    assembly,
                    movementData
            );

            case BRAKING -> updateBraking(
                    assembly,
                    movementData
            );

            case RETURNING -> updateReturning(
                    assembly,
                    movementData
            );
        }
    }
    //Stopped
    private void updateStopped(
            LinkedAssembly assembly
    ) {

    }

    //Starting
    private void updateStarting(
            LinkedAssembly assembly,
            MovementData movementData
    ) {

    }

    //Running
    private void updateRunning(
            LinkedAssembly assembly,
            MovementData movementData
    ) {

    }

    //Braking
    private void updateBraking(
            LinkedAssembly assembly,
            MovementData movementData
    ) {

    }

    //Returning
    private void updateReturning(
            LinkedAssembly assembly,
            MovementData movementData
    ) {

    }

}