package com.l1near.basicrotor.movement;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.util.Mth;

public class MovementRuntime {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final MovementData movementData;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public MovementRuntime(MovementData movementData) {
        this.movementData = movementData;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Tick
    public void tick() {

        movementData.setLastRotation(
                movementData.getRotation()
        );

        movementData.setSpeed(
                movementData.getSpeed()
                        + movementData.getAcceleration()
        );

        movementData.setSpeed(
                Mth.clamp(
                        movementData.getSpeed(),
                        -movementData.getMaxSpeed(),
                        movementData.getMaxSpeed()
                )
        );

        movementData.setSpeed(
                movementData.getSpeed()
                        * movementData.getFriction()
        );

        movementData.setRotation(
                movementData.getRotation()
                        + movementData.getSpeed()
        );

    }

}