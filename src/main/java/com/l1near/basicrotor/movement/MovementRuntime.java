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

    private void updateRunning(MovementInput input) {

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

    private static final float BRAKING_FORCE = 0.5F;

    private void updateBraking(MovementInput input) {

        float speed =
                movementData.getSpeed();


        speed -= BRAKING_FORCE;


        if (speed <= 0.0F) {

            speed = 0.0F;

            movementData.setState(
                    MovementState.RETURNING
            );
        }


        movementData.setSpeed(speed);


        movementData.setLastRotation(
                movementData.getRotation()
        );


        movementData.setRotation(
                movementData.getRotation()
                        + speed
        );
    }

    private void updateReturning(MovementInput input) {

    }

    private void updateStopped(MovementInput input) {

    }

    private void updateStarting(MovementInput input) {

    }

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
    public void tick(MovementInput input) {

        updateState(input);

        switch (movementData.getState()) {
            case STARTING ->
                    updateStarting(input);
            case RUNNING ->
                    updateRunning(input);

            case BRAKING ->
                    updateBraking(input);

            case RETURNING ->
                    updateReturning(input);

            case STOPPED ->
                    updateStopped(input);
        }
    }

    private void updateState(MovementInput input) {

        MovementState currentState =
                movementData.getState();


        if (input.isPowered()) {

            if (currentState == MovementState.STOPPED) {

                movementData.setHomeRotation(
                        movementData.getRotation()
                );

                movementData.setState(
                        MovementState.STARTING
                );
            }

        } else {

            if (currentState == MovementState.RUNNING) {

                movementData.setState(
                        MovementState.BRAKING
                );
            }
        }
    }

}